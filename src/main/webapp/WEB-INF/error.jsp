<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>error</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css"
			href="css/style.css" /><!-- 站在浏览器的角度去看网页和图片的相对关系 -->
		<style>
		  body{
			  background-color:#79B124;
		  }
		</style>
		<script>
		  	var timer;
			//启动跳转的定时器
			function startTimes(){
				timer=window.setInterval(showSecondes,1000);
			}
			var i=5;
			function showSecondes(){
				if(--i>0){//如果i>1
					document.getElementById("seconds").innerHTML=i;
				}else{//否则
					window.clearInterval(timer);
					location.href="/netctoss/toLogin.do";
				}
			}
			//取消跳转
			function resetTimer(){
				if(timer!=null && timer!=undefined){
					window.clearInterval(timer);
					location.href="/netctoss/toLogin.do";
				}
			}
		</script>
	</head>
	<body onload="startTimes()">
		<h1 id="error">
			遇到错误，&nbsp;<span id="seconds">5</span>&nbsp;
			秒后将跳转  否则
			<a href="javascript:resetTimer()">返回</a>
		</h1>
	</body>
</html>