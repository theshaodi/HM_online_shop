$(function(){
	var vueCart = new Vue({
		el:"#cartContent",
		data:{
			total:0,
			cartItemMap:[]
		},
		methods:{
			initCartData(){
				HM.ajax("/cart","method=getCart",function (data,status,xhr){
					console.log(data);
					if(data.code == 1){
						vueCart.cartItemMap = data.obj.cartItemMap;
						vueCart.total = data.obj.total;
					}else{
						console.log(data.message);
					}
				})

			},
			// 移除购物项
			removeItem(pid){
			    console.log(pid);
			    if(confirm("确定要删除吗?")){
						HM.ajax("/cart","method=removeCartItem&pid="+pid, function (data,status,xhr){
							if(data.code == 1){
								location.reload();
							}else{
								console.log(data.message);
							}
						})
			    }
			},

			//清空购物车按钮添加事件
			clearItem(){
			    if(confirm("确定要清空吗?")){
						HM.ajax("/cart","method=clearCart",function (data,status,xhr){
							if(data.code == 1){
								location.reload();
							}else{
								console.log(data.message);
							}
						})
			    }
			},

			// 提交订单
			submitOrder(){
				HM.ajax("/order","method=submitOrder",function (data,status,xhr){
					if(data.code == 1){
						console.log(data);
						// 跳转到订单列表页面
				    location.href = "/web/view/order/list.html";
					}else if(data.code == 2){
						alert(data.message);
						location.href = "/web/login.html";
					}else{
						alert(data.message);
						location.reload();
					}
				})
			}
		},
		mounted:function(){
			this.initCartData();
		}
	});
});
