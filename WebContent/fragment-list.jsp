<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<jsp:include page="head.jsp" />
	<body>
<jsp:include page="navbar.jsp" />
<main class="container">	  
	  <h2>List of Fragments</h2>
	  <a href="/MVCWorld/do/Fragment/new">New code fragment</a>
	  
	  <form action="/MVCWorld/do/Fragment/search">
	  	<input type=text name=q value="">
	  	<input type=submit name=action value="Search">
	  </form>
	  
	  <table class="table">
	  	<tr><th>Title</th><th>Created</th><th>action</th></tr>
		<c:forEach var="F" items="${fragments}">
			<tr><td><a href="/MVCWorld/do/Fragment/view/${F.id}">${F.title}</a></td>
			    <td><fmt:formatDate pattern="dd-MM-yyyy" value="${F.at}" /></td>
			    <td><a href="/MVCWorld/do/Fragment/edit/${F.id}">edit</a> |
			        <a href="/MVCWorld/do/Fragment/delete/${F.id}">delete</a></td>
			</tr>
		</c:forEach>
	  </table>
	  </main>
</body>