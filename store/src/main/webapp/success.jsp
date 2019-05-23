
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <style>
        .content{
            background-color:#FFFFCC;
            width: 60%;
            margin: 0px auto;
            min-height: 200px;
        }
    </style>
    <script>
        var  num=5;
        setInterval(function(){
            num--;
            if(num<1){
                location.href="http://www.itheimashop.com:8020/web/view/order/info.html?oid=${oid}";
            }else{
                document.getElementById("time").innerHTML=num;
            }

        },1000);
    </script>
</head>
<body>
<div>
    <div style="margin-left: 200px;">
        <h3>黑马收银台</h3>
    </div>
    <div style="text-align: center;">
        <div class="content">
            <div id="info">
                ${msg}
            </div>
            <div >
                <span id="time">5</span>秒之后跳转商城页面!!!
            </div>
        </div>
    </div>
</div>
</body>
</html>

