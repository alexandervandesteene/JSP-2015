<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="container">
  
  <div class="navbar navbar-default navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <button class="btn btn-success navbar-toggle"
                data-toggle="collapse"
                data-target=".navbar-collapse">
           <span class="glyphicon glyphicon-menu-hamburger"></span>
        </button>
        
        <div id="logo">
 			<a href='/MVCWorld'><h4>The MVC World</h4></a>
 				<c:if test="${! empty sessionScope.login.username}">
 					<h5>${sessionScope.login.username}</h5>
 				</c:if>
 		</div>
      
      <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav navbar-right">
          <li class="nav ${(empty current) ? 'active' : ''}"><a href=".">Home</a></li>
          <li class="nav ${(current == 'Language') ? 'active' : ''}"><a href="/MVCWorld/do/Language">Languages</a></li>
          <li class="nav ${(current == 'Fragment') ? 'active' : ''}"><a href="/MVCWorld/do/Fragment">Fragments</a></li>
          <!--  <li class="nav ${(param.current == 'user') ? 'active' : ''}"><a href="/MVCWorld/do/User">Users</a></li>-->
          <li class="nav">
          <c:if test="${empty sessionScope.login.username}">
 					<a href="/MVCWorld/do/User/login">Log in</a>
 		  </c:if>
		  <c:if test="${! empty sessionScope.login.username}">
 					<a href="/MVCWorld/do/User/doLogout">Log out</a>
 		  </c:if> 
 		  </li>
        </ul>
    </div>
  </div>
 </div>
 </div>
</header>