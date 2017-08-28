<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>emplist</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" 
		href="css/style.css" />
	</head>
	<body>
		<div id="wrap">
			<div id="top_content"> 
				<div id="header">
					<div id="rightheader">
						<c:import url="../logo.jsp"></c:import>
					</div>
					<div id="topheader">
						<h1 id="title">
							<a href="#">main</a>
						</h1>
					</div>
					<div id="navigation">
						<c:import url="../menu.jsp"></c:import>
					</div>
				</div>
				<div id="content">
					<p id="whereami">
					</p>
					<h1>
						Welcome!
					</h1>
					<table class="table">
						<tr class="table_header">
							<td>
								资费ID
							</td>
							<td>
								资费名称
							</td>
							<td>
								基本时长
							</td>
							<td>
								基本费用
							</td>
							<td>
								单位费用
							</td>
							<td>
								创建时间
							</td>
							<td>
								开通时间
							</td>
							<td>
								状态
							</td>
							<td>
								操作
							</td>
						</tr>
						<c:forEach items="${costs}" var="c">
						<tr class="row1">
							<td>
								${c.costId}
							</td>
							<td>
								${c.name }
							</td>
							<td>
								${c.baseDuration }
							</td>
							<td>
								${c.baseCost }
							</td>
							<td>
								${c.unitCost }
							</td>
							<td>
								${c.creatime }
							</td>
							<td>
								${c.startime }
							</td>
							<td>
								<c:if test="${c.status==0}">开通</c:if>
		 						<c:if test="${c.status==1}">暂停</c:if>

							</td>
							<td>
								<input type="button" value="启用">&nbsp;
								<input type="button" value="修改" onclick="location.href='toUpdateCost.do?id=${c.costId}';">&nbsp;
								<input type="button" value="删除">
							</td>
						</tr>
						</c:forEach>
					</table>
					<p>
						<!--
						  当前:/netctoss/findCost.do
						  目标:/netctoss/toAddCost.do
						 -->
						<input type="button" class="button" value="增加" onclick="location='toAddCost.do'"/>
					</p>
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