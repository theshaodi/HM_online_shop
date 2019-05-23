/*包含页面*/
$(function(){
    $.get("/web/header.html",function (data) {
        $("#header").html(data);
    });
    $.get("/web/foot.html",function (data) {
        $("#footer").html(data);
    });
});