<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="head.jsp" />
<body>
	  <jsp:include page="navbar.jsp" />
<main class="container">
	  <h2>Edit a code fragment</h2>
	  
	  <form action="/MVCWorld/do/Fragment/save" method=post>
	    <input type="hidden" name="id" value="${fragment.id}">
	    <input type="hidden" name="user" value="${sessionScope.login.id}">
	    <div>
	    	<label for="name">Name</label>
	    	<input type="text" name="title" id="title" value="${fragment.title}">
	    </div>
	    <div>
	    	<label for="language">Language</label>
	    	<select name="language" id="language">
	    	  <option value='0'>-</option>
	    	  <c:forEach var="L" items="${languages}">
	    	  	<option value="${L.id}" ${(L.id==fragment.language) ? 'selected' : ''}>${L.name}</option>
	    	  </c:forEach>
	    	</select>
	    </div>
	    <div>
	    	<label for="tags">Tags</label>
	    	<input type="text" name="tags" id="tags" value="${fragment.tags}">
	    </div>
	    <div>
	    	<label for="code">Code</label>
	    	<textarea name="code" id="code">${fragment.code}</textarea>
	    </div>
	    <div>
	    	<input type="submit" value="Save">
	    </div>
	  </form>
</main>
</body>
