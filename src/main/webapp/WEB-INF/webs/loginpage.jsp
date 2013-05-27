<%@page language="java" isELIgnored="false" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

    <h1>Login</h1>  
  
    <div id="login-error">${error}</div>  
  
    <form action="../j_spring_security_check" method="post">  
  
        <p>  
            <label for="j_username">Username</label> <input id="j_username"  
                name="j_username" type="text" />  
        </p>  
  
        <p>  
            <label for="j_password">Password</label> <input id="j_password"  
                name="j_password" type="password" />  
        </p>  
  
        <input type="submit" value="Login" />  
  
    </form>  
  
</body>  
</html> 