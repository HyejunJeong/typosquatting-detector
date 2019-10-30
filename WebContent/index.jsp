<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <title>Typosquatting Detector</title>
</head>
<body>
    <form method="get" action="search">
        <label for="input">Enter Web Address to Search: </label>
        <input type="text" name="input" id="input" />
        <input type="submit" value="Search">
    </form>
    <%
        if(request.getAttribute("output") != null) {
            out.println(request.getAttribute("output"));
        }
    %>
</body>
</html>