package com.itheima.servlet;

import com.google.gson.Gson;
import com.itheima.common.ProductConst;
import com.itheima.domain.Product;
import com.itheima.service.AdminService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.Result;
import com.itheima.utils.UUIDUtils;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Project: ${PACKAGE_NAME}
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-29 21:25
 * @Description:
 * @Version: 1.0
 */
@WebServlet(urlPatterns = "/fileuploadAndproduct")
public class FileUploadServlet extends HttpServlet {

    private AdminService AS = BeanFactory.newInstance(AdminService.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        Map<String, String[]> parameterMap = getParameterMap(request);
        Product product = new Product();
        try {
            BeanUtils.populate(product,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(product);
        product.setPid(UUIDUtils.getUUID());
        product.setPflag(ProductConst.PFLAG);
        product.setPdate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        boolean b = AS.addProduct(product);
        if(b){
            Result res = new Result(Result.SUCCESS,"添加商品成功");
            response.getWriter().print(JSONObject.fromObject(res));
        }else{
            Result res = new Result(Result.FAILS,"添加商品失败");
            response.getWriter().print(JSONObject.fromObject(res));
        }
    }

    private Map<String, String[]> getParameterMap(HttpServletRequest request){
        Map<String, String[]> m = new HashMap<>();
        try {
            //创建磁盘工厂对象
            DiskFileItemFactory itemFactory = new DiskFileItemFactory();
            //创建Servlet的上传解析对象,构造方法中,传递磁盘工厂对象
            ServletFileUpload fileUpload = new ServletFileUpload(itemFactory);
            /*
             * fileUpload调用方法 parseRequest,解析request对象
             * ⻚面可能提交很多内容 文本框,文件,菜单,复选框 是为FileItem对象 * 返回集合,存储的文件项对象
             */
            List<FileItem> list = fileUpload.parseRequest(request);
            for(FileItem item : list){
                //判断普通项还是附件项
                if(item.isFormField()){
                //取出普通项的name属性值 name="username"
                String name = item.getFieldName();
                //取出普通的填写的值
                String value = item.getString("utf-8");
                m.put(name,new String[]{value});
                }else {
                    //附件项
                    // 对文件名进行重命名,采用UUID+"_"+文件名
                    String fileName = UUIDUtils.getUUID().replace("-","")+"_"+item.getName();
                    // 读取文件数据，并存到磁盘
                    // 注意配置文件中的路径含有中文，中文应转为unicode字符
                    String uploadDir = ResourceBundle.getBundle("upload").getString("uploadDir");
                    // 构建本地存储的文件对象
                    File uploadFile = new File(uploadDir,fileName);

                    // 从上传文件项获取字节输入流，然后写前端资源路径下
                    InputStream inputStream = item.getInputStream();
                    FileOutputStream fos = new FileOutputStream(uploadFile);
                    // 通过IO工具存入磁盘
                    IOUtils.copy(inputStream,fos);

                    // 存入数据库的路径，是从resources目录开始的相对路径
                    String path = uploadFile.getAbsolutePath();
                    String saveDbPath = path.substring( path.indexOf("resources"));
                    m.put("pimage",new String[]{saveDbPath});
                    // 关闭流
                    IOUtils.closeQuietly(fos);
                    IOUtils.closeQuietly(inputStream);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return m;
    }
}
