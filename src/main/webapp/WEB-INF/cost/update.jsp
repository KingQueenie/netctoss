<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>update Cost</title>
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
								<a href="#">Main</a>
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
						update Cost info:
					</h1>
					<form action="UpdateCost.do" method="post">
						<table cellpadding="0" cellspacing="0" border="0"
							class="form_table">
							<tr>
								<td valign="middle" align="right">
									id:
								</td>
								<td valign="middle" align="left">						
									<input type="text" class="inputgri" value="${cost.costId }" readonly="readonly" name="costId" />
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									资费名称:
								</td>
								<td valign="middle" align="left">
									<input type="text" class="inputgri" value="${cost.name }" name="name" />
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									资费类型:
								</td>
								<td valign="middle" align="left">
									<input type="radio" class="inputgri" ${cost.costType==1?'checked':'' } value="1" name="costType" />包月
									<input type="radio" class="inputgri" ${cost.costType==2?'checked':'' } value="2" name="costType" />套餐
									<input type="radio" class="inputgri" ${cost.costType==3?'checked':'' } value="3" name="costType" />计时
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									基本时长:
								</td>
								<td valign="middle" align="left">
									<input type="text" class="inputgri" value="${cost.baseDuration }" name="baseDuration" />&nbsp;小时
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									基本费用:
								</td>
								<td valign="middle" align="left">
									<input type="text" class="inputgri" value="${cost.baseCost }" name="baseCost" />&nbsp;元
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									单位费用:
								</td>
								<td valign="middle" align="left">
									<input type="text" class="inputgri" value="${cost.unitCost }" name="unitCost" />&nbsp;元/小时
								</td>
							</tr>
							<tr>
								<td valign="middle" align="right">
									资费说明:
								</td>
								<td valign="middle" align="left">
									<textarea class="inputgri" name="descr">${cost.descr }</textarea>
								</td>
							</tr>
						</table>
						<p>
							<input type="submit" class="button" value="保存" />
							<input type="submit" class="button" value="取消"  onclick="history.back();"/>
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
