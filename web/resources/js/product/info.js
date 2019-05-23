$(function(){
	var vueProduct = new Vue({
		el:"#infoContent",
		data:{
			product:{
				"market_price": 1,
                "pdate": "2018-11-02 00:00:00",
                "pdesc": "TEST 小米 4c 标准版TTTTTT",
                "pid": "1",
                "pimage": "resources/products/1/c_0001.jpg",
                "pname": "TTT 小米 4c 标准版",
                "shop_price": 1
			}
		},
		methods:{
			initProductData(){
				var pid = HM.getParameter("pid");
				if(pid != null){
					HM.ajax("/product","method=findProductById&pid="+pid,function (data,status,xhr){
						vueProduct.product = data.obj;
					})
				}
			},
			addCart(){
				console.log("info add cart....");
				location.href = "/web/view/cart/list.html";
			}
		},
		mounted:function(){
			this.initProductData();	
		}
	});
	
});
