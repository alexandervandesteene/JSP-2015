<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="head.jsp" />
<body>
<jsp:include page="navbar.jsp" />
<main class="container">
	  
	  <h2>List of Languages</h2>
	  
	  <a href="/MVCWorld/do/Language/new">New language</a>
	  <table class="table">
	  	<tr><th>id</th><th>Name</th><th>action</th></tr>
		<c:forEach var="R" items="${languages}">
			<tr>
				<td>${R.id}</td>
				<td>${R.name}</td>
			    <td><a href="/MVCWorld/do/Language/edit?id=${R.id}">edit</a> |
			        <a href="/MVCWorld/do/Language/delete?id=${R.id}">delete</a></td>
				</tr>
		</c:forEach>
	  </table>
		</main>
</body>