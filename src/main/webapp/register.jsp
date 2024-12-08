<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:if test="${employeedata==null}">
<form action="add" method="post">
</c:if>

<c:if test="${employeedata!=null}">
<form action="update" method="post">
</c:if>
<input type="hidden" name="id" value="${employeedata.getId()}"/>
<label>name</label><input type="text" name="txtName" value="${employeedata.getName()}"/><br>
<label>Salary</label><input type="number" name="txtSalary" value="${employeedata.getSalary()})"/><br>
<input type="submit" value="Register"/><br>
<a href="home">Home</a>

</form>
</form>
</body>
</html>