<%@page language="java" contentType="text/html;  
charset=UTF-8"%>
<%
	request.getSession().invalidate();
%>
<%
	response.sendRedirect("protected/home.jsf");
%>
