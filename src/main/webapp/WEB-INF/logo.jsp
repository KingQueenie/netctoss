<%@page pageEncoding="utf-8"%>
<!-- 
  EL默认的取值范围：page,request,session,application
  EL也可以从cookie中取值，语法为：cookie.key.value
 -->
<%-- <span>${cookie.adminCode.value}</span>--%>
<span>${adminCode }</span>
<a href="/netctoss/logout.do">[退出]</a>