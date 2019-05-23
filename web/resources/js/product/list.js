$(function(){
	var productList = new Vue({
		el:"#productList",
		data:{
			pageBean:{				// 分页对象
		        "currentPage": 1,	//当前页码
		        "list": [
		            {
		                "market_price": 1,
		                "pdate": "2018-11-02 00:00:00",
		                "pdesc": "小米 4c 标准版TTTTTT",
		                "pid": "1",
		                "pimage": "resources/products/1/c_0001.jpg",
		                "pname": "小米 4c 标准版",
		                "shop_price": 1
		            }
		        ],
		        "pageSize": 1,		// 每页默认记录
		        "totalCount": 1,	// 总记录
		        "totalPage": 1		// 总页数
		   }	
		},
		methods:{
			initProductListData(){
				var cid = HM.getParameter("cid");
				if(cid != null){
					HM.ajax("/product","method=getProductListById&cid="+cid,function (data,status,xhr){
						if(data.code == 1){
							console.log(data);
						}else{
							console.log(data.message);
						}
					})
				}
			}
		},
		mounted:function(){
			this.initProductListData();	
		}
	});
	
});
