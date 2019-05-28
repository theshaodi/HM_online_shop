$(function(){
	// 构建vue对象
	var vueIndex = new Vue({
		el:"#productList",
		data:{
			hottestList:[],		// 热门列表
			newestList:[],		// 最新列表
		},
		methods:{
			initIndexData(){
				HM.ajax("/product","method=findHostAndNewProducts",function (data,status,xhr){
					console.log(data);
					if(data.code == 1){
						vueIndex.hottestList = data.obj.hostProducts;
						vueIndex.newestList = data.obj.newProducts;
					}else{
						console.log(data.message);
					}

				})
			}
		},
		mounted:function(){
			this.initIndexData();
		}
	});
});
