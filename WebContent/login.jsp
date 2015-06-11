<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
       
<jsp:include page="head.jsp" />
	<body>
<jsp:include page="navbar.jsp" />
<main class="container">
	 <h2>Please log in</h2>
	
	 <c:if test="${! empty message }">
	 <h3 class="warning">${message}</h3>
	 </c:if>
	
	 <form action="/MVCWorld/do/User/doLogin" method=post class="form">
	 <div class="form-group">
	 <label for="name">Username</label>
	 <input type="text" name="username" value="">
	 </div>
	 <div class="form-group">
	 <label for="name">Password</label>
	 <input type="password" name="password" value="">
	 </div>
	 <input class="btn btn-primary" type="submit" value="Login">
	 </form>
</main>
</body>

