	$(function(){
		// 构建vue对象
		var vueRegister = new Vue({
			el:"#regContent",		// 根元素
			data:{					// 数据
			},
			methods:{				// 方法
				register(){
					var a = $("#regForm").serialize();
					console.log(a);
					HM.ajax("/user",a,function (data,status,xhr){
						console.log(data);
						if(data.code==1){
							alert(data.message);
							location.href="/web/login.html";
						}else{
							alert(data.message);
						}
					}
				  )
				}
			},
		});
	});