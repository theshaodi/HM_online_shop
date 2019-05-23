$(function(){
	var vueCart = new Vue({
		el:"#cartContent",
		data:{
			total:1000,
			cartItemMap:[
	            {
	                "count": 1,
	                "product": {
	                    "pdate": "2015-11-02 00:00:00.0",
	                    "pdesc": "移动联通双4G手机 TTTTT",
	                    "pid": "11",
	                    "pimage": "resources/products/1/c_0014.jpg",
	                    "pname": "TTTTTTTTT TEST vivo X5Pro",
	                    "shop_price": 2298
	                },
	                "subTotal": 2298
	            },
	            {
	                "count": 1,
	                "product": {
	                    "pdate": "2015-11-02 00:00:00.0",
	                    "pdesc": "TTTTTTTT 华为 Ascend Mate7 ",
	                    "pid": "10",
	                    "pimage": "resources/products/1/c_0010.jpg",
	                    "pname": "华为 Ascend Mate7",
	                    "shop_price": 2599
	                },
	                "subTotal": 2599
	            }
	      ]
		},
		methods:{
			initCartData(){
				console.log("initCartData...");
				
			},
			// 移除购物项
			removeItem(pid){
			    console.log(pid);
			    if(confirm("确定要删除吗?")){
			    }
			},
			
			//清空购物车按钮添加事件
			clearItem(){
			    if(confirm("确定要清空吗?")){
			        // 发送ajax请求
			    }
			},
			
			// 提交订单
			submitOrder(){
				// 跳转到订单列表页面
			    location.href = "/web/view/order/list.html";
			}
		},
		mounted:function(){
			this.initCartData();
		}
	});
});
