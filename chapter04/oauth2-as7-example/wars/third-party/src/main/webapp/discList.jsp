<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<html>
<head>
    <title>DISCS LIST</title>
</head>
<body>
<h2>Compact Discs List</h2>
<%
java.util.List<String> discsList = com.packtpub.resteasy.example.oauth.CompactDiscsDatabaseClient.getCompactDiscs(request);
for (String cd : discsList)
{
   out.print("<p>");
   out.print(cd);
   out.println("</p>");

}
%>
<br><br>
</body>
</html>