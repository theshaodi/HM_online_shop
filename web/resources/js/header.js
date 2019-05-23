	$(function(){
		var vueHeader = new Vue({
			el:"#headContent",		// 根元素
			data:{					// 数据
				isLogin:false,		// 是否登录
				username:"",		// 用户名
				categoryList:[		// 列表数据，demo数据
				 	{
			            "cid": "1",
			            "cname": "手机数码"
			        },
			        {
			            "cid": "2",
			            "cname": "电脑办公"
			        }
				],
				
			},
			methods:{				// 方法
				initHeaderData(){
					// 初始化数据
					var c = HM.cookieValue("user");
					if(c!=null){
						HM.ajax("/user","method=isLogin",function (data,status,xhr){
							if(data.code==1){
								vueHeader.username = HM.cookieValue("user");
								vueHeader.isLogin = true;
								console.log("用户"+vueHeader.username+"已登陆");
							}else{
								vueHeader.isLogin = false;
								vueHeader.username = "";
								// alert(data.message);
								// location.href = "/web/login.html";
							}
						});
					}
					HM.ajax("/category","method=findAll",function (data,status,xhr){
						if(data.code == 1){
							vueHeader.categoryList = data.obj;
						}else{
							console.log(data.message);
						}
					});
				},
				logOut(){
					console.log(vueHeader.username+"要登出了");
					
					//发送请求,退出登录
					HM.ajax("/user","method=logOut",function (data,status,xhr){
						if(data.code == 1){
							alert("用户"+vueHeader.username+"退出登陆");
							vueHeader.isLogin = false;
							vueHeader.username = "";
							location.href = "/web/index.html";
						}else{
							console.log(data.message);
						}
					})
				}
			},
			mounted:function(){
				this.initHeaderData();	
			}
		});
		
	});