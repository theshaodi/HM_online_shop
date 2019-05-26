$(function(){
	var vueOrder = new Vue({
		el:"#orderInfoContent",
		data:{
			order:{}
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
							if(data.obj.state == 1){
								$("#name").val(data.obj.name).attr("disabled",true);
								$("#address").val(data.obj.address).attr("disabled",true);
								$("#telephone").val(data.obj.telephone).attr("disabled",true);
								$("input[name='pd_FrpId']").attr("disabled",true);
								$("#payButten").css("display","none");
							}
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
