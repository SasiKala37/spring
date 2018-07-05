<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LoginPage</title>
<style type="text/css">
body {
	color: #fff;
	background: #63738a;
	font-family: 'Roboto', sans-serif;
}

.form-control {
	height: 40px;
	box-shadow: none;
	color: #969fa4;
}

.form-control:focus {
	border-color: #5cb85c;
}

.form-control, .btn {
	border-radius: 3px;
}

.login-form {
	width: 400px;
	margin: 0 auto;
	padding: 30px 0;
}

.login-form h2 {
	color: #636363;
	margin: 0 0 15px;
	position: relative;
	text-align: center;
}

.login-form h2:before, .login-form h2:after {
	content: "";
	height: 2px;
	width: 30%;
	background: #d4d4d4;
	position: absolute;
	top: 50%;
	z-index: 2;
}

.login-form h2:before {
	left: 0;
}

.login-form h2:after {
	right: 0;
}

.login-form .hint-text {
	color: #999;
	margin-bottom: 30px;
	text-align: center;
}

.login-form form {
	color: #999;
	border-radius: 3px;
	margin-bottom: 15px;
	background: #f2f3f7;
	box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
	padding: 30px;
}

.login-form .form-group {
	margin-bottom: 20px;
}

.login-form input[type="checkbox"] {
	margin-top: 3px;
}

.login-form .btn {
	font-size: 16px;
	font-weight: bold;
	min-width: 140px;
	outline: none !important;
}

.login-form .row div:first-child {
	padding-right: 10px;
}

.login-form .row div:last-child {
	padding-left: 10px;
}

.login-form a {
	color: #fff;
	text-decoration: underline;
}

.login-form a:hover {
	text-decoration: none;
}

.login-form form a {
	color: #5cb85c;
	text-decoration: none;
}

.login-form form a:hover {
	text-decoration: underline;
}
</style>
</head>
<body>
	<div class="login-form">
	<center>
		<form action="LoginHttpServlet" method="post">
			<h2>Login</h2>
			<label>${errMessage}</label>
			<div class="form-group">
				<input type="text" class="form-control" name="userName"
					placeholder="User Name" required="required">
			</div>
			<div class="form-group">
				<input type="password" class="form-control" name="password"
					placeholder="Password" required="required">
			</div>
			<button type="reset" value="Reset">Reset</button>
			<div class="form-group">
				<button type="submit" class="btn btn-success btn-lg btn-block">Login
					Now</button>
			</div>
			<div class="text-center">New User
						 <a href="registerform.jsp">Register here</a>
					</div>
			<div class="text-center">
						 <a href="#">forgot password?</a>
					</div>
			
		</form>
		</center>
	</div>
</body>
</html>