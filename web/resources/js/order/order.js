$(function(){
	var vueOrderList = new Vue({
		el:"#orderListContent",
		data:{
			pageBean:{}
		},
		methods:{
			initOrderData(){
				console.log("initOrderData...");
				var currentPage = HM.getParameter("currentPage");
				console.log(currentPage);
				HM.ajax("/order","method=getOrdersListByUid&currentPage="+currentPage,function(data,state,xhr){
					if(data.code == 1){
						console.log(data);
						vueOrderList.pageBean = data.obj;
						var splitPage = HM.page(data.obj,"/web/view/order/list.html");
						$("#page").html(splitPage);
					}else if(data.code == 2){
						alert(data.message);
						location.href="/web/login.html";
					}else{
						alert(data.message);
					}
				})
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
		    }
		},
		mounted:function(){
			this.initOrderData();
		}
	});
});
