<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="head.jsp" />
<body>
<jsp:include page="navbar.jsp" />
<main class="container">	  
	  <h2>Code fragment - ${fragment.title}</h2>
	  
	    <div>
	    	<label>Language:</label>
	    	${fragment.lname}
	    </div>
	    <div>
	    	<label>Created at</label>
	    	<fmt:formatDate pattern="dd-MM-yyyy" value="${fragment.at}" />
	    </div>
	    <div>
	    	<label>Tags:</label>
	    	${fragment.tags}
	    </div>
	    <div>
	    	<label>Code:</label>
	    	${fragment.code}
	    </div>
	    
	    <div>
	    <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#show">view comments</button>
	    	<div id="show" class="collapse out">
	     		<c:forEach var="C" items="${comments}">
	     			<div class="block">
	     				<c:forEach var="U" items="${users}">
	     					 <c:if test="${U.id == C.user}">
	     					 	<div><label>User: </label>${U.username}</div>
	     					 </c:if>
	     				</c:forEach>
	     				<div><label>Comment:</label>${C.what}</div>
	     				<div><label>at:</label><fmt:formatDate pattern="dd-MM-yyyy" value="${C.at}" /></div>
	     				<c:if test="${sessionScope.login.id == C.user}">
	     					 	<a href="/MVCWorld/do/Fragment/deletecomment/${C.id}">delete comment</a>
	     				</c:if>
	     			
	     			</div>	
	     		</c:forEach>
	     	</div>
	    </div>
	    <c:if test="${!empty sessionScope.login.username}">
	    <div>
	    	<button type="button" class="btn btn-warning" data-toggle="collapse" data-target="#add">add comments</button>
	    	<div id="add" class="collapse out">
	     		<form class="block" action="/MVCWorld/do/Fragment/savecomment" method=post>
	     			<input type="hidden" name="userid" value="${sessionScope.login.id}">
	     			<input type="hidden" name="fragmentid" value="${fragment.id }">
	     			<div class="from-group">
	     				<label for="user">User</label>
    					<input type="text" class="form-control" name="user" value="${sessionScope.login.username}">
	     			</div>
	     			<div class="from-group">
	     				<label for="comment">Comment</label>
    					<input type="text" class="form-control" name="comment" placeholder="Enter a comment">
	     			</div>
	     			<div class="from-group">
	    				<input class="btn btn-default" type="submit" value="Save">
	    			</div>
	     		</form>
	     	</div>
	    </div> 
	    </c:if>
	  	
	     <div>
	    	<a href="/MVCWorld/do/Fragment/list">Back to list</a>
	    </div>
  </main>
</body>
