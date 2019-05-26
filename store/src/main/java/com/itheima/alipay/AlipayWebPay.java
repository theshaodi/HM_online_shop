package com.itheima.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.itheima.common.OrderConst;
import com.itheima.service.OrderService;
import com.itheima.service.impl.OrderServiceImpl;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author ：seanyang
 * @date ：Created in 2019/4/21$
 * @description ：支付宝支付工具类
 * @version: 1.0
 */
public class AlipayWebPay{
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。

    public static String alipay_public_key ="";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "";
    // 签名方式
    public static String sign_type = "";
    // 字符编码格式
    public static String charset = "";
    // 支付宝网关
    public static String gatewayUrl = "";

    static {
        ResourceBundle resource =  ResourceBundle.getBundle("alipay");
        app_id = resource.getString("app_id");
        merchant_private_key = resource.getString("merchant_private_key");
        alipay_public_key = resource.getString("alipay_public_key");
        notify_url = resource.getString("notify_url");
        return_url = resource.getString("return_url");
        sign_type = resource.getString("sign_type");
        charset = resource.getString("charset");
        gatewayUrl = resource.getString("gatewayUrl");
    }

    /**
     * 获取alipay网站支付的页面Body
     * @param out_trade_no 订单ID
     * @return
     * @throws Exception
     */
    public  static String getWebPayBody(String out_trade_no) throws Exception {
//        return "";
        // 获得初始化的AlipayClient
		// 向阿里支付接口发送任何请求（支付请求、查询交易状态请求）时必须构建的对象。
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, "json", charset, alipay_public_key, sign_type);

        // 设置请求参数
		// 发送支付请求时，必须构建的对象，该对象会包含商户订单ID、订单金额、标题及内容。
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        // 设置交易数据
        JSONObject jsonData = new JSONObject();
        jsonData.put("out_trade_no",out_trade_no);
        jsonData.put("product_code","FAST_INSTANT_TRADE_PAY");
        jsonData.put("total_amount",0.1);
        jsonData.put("subject","黑马商城");
        jsonData.put("body","黑马商城商品");
        alipayRequest.setBizContent(jsonData.toString());
        // 发送请求
        String  result= alipayClient.pageExecute(alipayRequest).getBody();
        System.out.println(result);
        return result;
    }
    /**
     * 数据验证
     * @param requestParams
     * @return
     */
    public static boolean signVerified(Map<String,String[]> requestParams){
        boolean isSignVerified = false;
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        try {
            isSignVerified = AlipaySignature.rsaCheckV1(params, alipay_public_key, charset, sign_type); //调用SDK验证签名
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return isSignVerified;
    }

    /**
     * 查询交易状态 （商户交易ID或支付宝交易ID，两者选一）
     * @param out_trade_no 商户交易ID
     * @param trade_no 支付宝交易ID
     */
    public static void queryTrade(String out_trade_no,String trade_no) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, "json", charset, alipay_public_key, sign_type);
        // 构建查询订单状态的请求对象
		// 发送查询交易状态请求时，必须构建的对象，需要提供商户订单ID
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();
        //设置请求参数
        JSONObject jsonData = new JSONObject();
        jsonData.put("out_trade_no",out_trade_no);
        jsonData.put("trade_no",trade_no);
        alipayRequest.setBizContent(jsonData.toString());
        try{
            //发起请求
            String result = alipayClient.execute(alipayRequest).getBody();
            System.out.println(result);
            JSONObject jsonResult = JSONObject.fromObject(result);
            JSONObject queryResult = jsonResult.getJSONObject("alipay_trade_query_response");
            if(queryResult.getString("code").equals("10000")){
                // 付款已成功
                OrderService orderService = new OrderServiceImpl();
                if(orderService.updateOrdersStateById(out_trade_no, OrderConst.PAID)){
                    System.out.println("状态更新成功！");
                }else{
                    System.out.println("状态更新失败！");
                }
            }
            System.out.println(result);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
