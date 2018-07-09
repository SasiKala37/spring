<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%        
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setDateHeader("Expires", -1);
%>
	<div align="center">
		<h1>Welcome to Flipcart</h1>
	<%	
	String userName=request.getSession().getAttribute("userName").toString();
	out.print(userName);
	%>
		<form action="LogoutHttpServlet" method="post">
		<input type="submit" value="LogOut">
		</form>

	</div>
</body>
</html>