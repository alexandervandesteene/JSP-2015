<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="head.jsp" />
<body>
<jsp:include page="navbar.jsp" />
<main class="container">
	  
	 <h2>add Language</h2>
	  <form action="/MVCWorld/do/Language/save" method=post>
	    <input type="hidden" name="id" value="${language.id}">
	    <div>
	    	<label for="name">Name</label>
	    	<input type="text" name="name" value="${language.name}">
	    </div>
	    <div>
	    	<input type="submit" value="Save">
	    </div>
	  </form>
</body>