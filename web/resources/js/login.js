	$(function(){
		// 构建vue对象
		var vueLogin = new Vue({
			el:"#loginContent",		// 根元素
			data:{					// 数据
				showMessage:""
			},
			methods:{				// 方法
				login(){
					console.log("submitLogin...");
					var a = $("#loginForm").serialize();
					console.log(a);
					HM.ajax("/user",a,function (data,status,xhr){
						if(data.code==1){
							alert(data.message);
							location.href = "/web/index.html";
						}else{
							alert(data.message);
						}
					})
				}
			},
		});
	});