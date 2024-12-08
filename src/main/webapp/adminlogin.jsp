<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="validateadmin" method=post>
<input type="email" name="txtEmail"/><br>
<input type="password" name="txtPassword"/><br>
<input type="submit"/> <a href="adminregister.jsp">sign up</a>
<%if(request.getAttribute("message")!=null){ %>
	<h4 style="colo:red"> <%=request.getAttribute("message") %>
<%} %>
</form>
</body>
</html>