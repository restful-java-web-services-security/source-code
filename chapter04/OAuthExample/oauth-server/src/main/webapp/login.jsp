<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<html>
<head>
    <title>Login</title>
</head>
<body >
<h2>Authentication Server</h2>
<br><br>
<%

if (request.getParameter("client_id") != null) {
   String redirect = request.getParameter("redirect_uri");
   if (request.getParameter("login") != null) {
      out.println("<p><font color=\"red\">This is a remote login from</font>:<b>" + redirect + "</b></p>");
   }
   else
   {
      out.println("<p><b>"  + "https://localhost:8443/oauth-client/discsList.jsp" + "</b> <font color=\"red\">is requesting permission to access your data.</font></p>");
   }
}
%>


<form action="<%= request.getAttribute("OAUTH_FORM_ACTION")%>" method=post>
    <p><strong>User Name: </strong>
    <input type="text" name="j_username" size="30">
    <p><p><strong>Password: </strong>
    <input type="password" size="20" name="j_password">
    <p><p>
    <input type="submit" value="Accept">
    <input type="reset" value="Reset">
</form>
</body>
</html>