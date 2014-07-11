<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<html>
<head>
    <title>Commerce Realm</title>
</head>
<body bgcolor="#CED8F6">
Welcome <b><%=request.getUserPrincipal().getName()%></b>!.
<h2>Realm Applications</h2>
<p><a href="https://localhost:8443/oauth-client/">Oauth-client</a></p>

<br>
<p><a href="https://localhost:8443/auth-server/j_oauth_logout">logout</a></p>
</body>
</html>