<%@page pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>login</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css"
			href="css/style.css" />
	</head>

	<body>
		<div id="wrap">
			<div id="top_content">
					<div id="header">
						<div id="rightheader">
							<p>
								2009/11/20
								<br />
							</p>
						</div>
						<div id="topheader">
							<h1 id="title">
								<a href="#">main</a>
							</h1>
						</div>
						<div id="navigation">
						</div>
					</div>
				<div id="content">
					<p id="whereami">
					</p>
					<h1>
						login
					</h1>
					<form action="login.do" method="post">
						<table cellpadding="0" cellspacing="0" border="0"
							class="form_table">
							<tr>
								<td valign="middle" align="right">
									用户名:
								</td>
								<td valign="middle" align="left">
									<input type="text" class="inputgri" name="adminCode" value="${param.adminCode}" /><!-- 获取请求参数值 -->
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									密码:
								</td>
								<td valign="middle" align="left">
									<input type="password" class="inputgri" name="password" value="${param.password }" />
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									验证码:
								</td>
								<td valign="middle" align="left">
									<input type="text" class="inputgri" name="code" />
								</td>
								<!--路径后面增加了参数(随机数)，是为了欺骗浏览器，让它以为路径变了  -->
								<td><img src="createImg.do" onclick="this.src='createImg.do?x='+Math.random()" alt="验证码"></td>
							</tr>
						</table>
						<p>
						<!-- 
							1. 表单有onsubmit事件，点击submit按钮，就是为了出发这个事件，可以提交表单
							2. 表单还有submit()事件，调用此方法一样可以提交表单
							3. 在超链接上可以写js，但必须进行声明，语法:javaScript:js代码
							<a href="javascript:document.forms[0].submit()">登录</a>
						 -->
							<input type="submit" class="button" value="登录 &raquo;" />
							<span>${error }</span>
						</p>
					</form>
				</div>
			</div>
			<div id="footer">
				<div id="footer_bg">
					ABC@126.com
				</div>
			</div>
		</div>
	</body>
</html>