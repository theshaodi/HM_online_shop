$(function(){
	var vueOrder = new Vue({
		el:"#orderInfoContent",
		data:{
			order:{
                "oid": "000000000000000000",
                "orderViewList": [
                    {
                        "count": 1,
                        "pid": "33",
                        "pimage": "resources/products/1/c_0033.jpg",
                        "pname": "联想（ThinkPad） 轻薄系列E450C(20EH0001CD)TTTTTTTTTTTTT",
                        "shop_price": 4199,
                        "subTotal": 4199
                    },
                    {
                        "count": 1,
                        "pid": "10",
                        "pimage": "resources/products/1/c_0010.jpg",
                        "pname": "华为 Ascend Mate7TTTTTTTTTTTTTTT",
                        "shop_price": 2599,
                        "subTotal": 2599
                    }
                ],
                "ordertime": "2019-05-09 12:33:58",
                "state": 1,
                "total": 6798,
                "uid": "98a64e59ffee4e26a7306bdaae8c2341"
            }
		},
		methods:{
			initInfoData(){
				console.log("initInfoData...");
				var oid = HM.getParameter("oid");
				if(oid){
					HM.ajax("/order","method=getOrdersByOid&oid="+oid,function (data,state,xhr){
						if(data.code == 1){
							console.log(data);
							vueOrder.order = data.obj;
						}else if(data.code == 2){
							alert(data.message);
							location.href="/web/login.html";
						}else{
							console.log(data.message);
						}
					})
				}
			},
			getState(state){
		        switch(state){
		            case 0:
		                return "未付款";
		            case 1:
		                return "已付款";
		            case 2:
		                return "已发货";
		            case 3:
		                return "已完成";
		            default:
		                return "未知";
		        }
		    },
		   // 定义pay函数
			pay(){
			    // alert("跳转到支付宝");
					var oid = HM.getParameter("oid");
					var params = $("#orderForm").serialize();
					HM.ajax("/order","method=pay&oid="+oid+"&"+params,function (data,state,xhr){
						if(data.code == 1){
							$("#payResult").html(data.obj);
						}else{
							console.log(data.message);
						}
					})
			}
		},
		mounted:function(){
			this.initInfoData();
		}
	});
});
