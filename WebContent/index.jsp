<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
    <title>Typosquatting Detector</title>
</head>
<body>
    <form method="get" action="search">
        <label for="input">Enter Web Address to Search: </label>
        <input type="text" name="input" id="input" />
        <input type="submit" value="Search"/>
    </form>
    <c:if test="${report != null}">
    	<form action="report.html" target="_blank">
		    <input type="submit" value="View Report"/>
		</form>
    </c:if>    
</body>
</html>