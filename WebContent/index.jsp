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
    <div id="test">
    </div>    
    <script>
    	let div = document.getElementById("test");
    	let newDiv = document.createElement("div");
    	newDiv.innerHTML = "does this work?";
    	div.appendChild(newDiv);
    </script>    
    <%
        if (request.getAttribute("output") != null) {
            out.println(request.getAttribute("output"));
        }
    %>    
    <c:if test="${report != null}">
    	<jsp:include page="${report}"/>
    </c:if>    
</body>
</html>