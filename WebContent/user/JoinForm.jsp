<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="../layout/header.jsp" %>
<div class="container">
	<form action="/blog/user?cmd=join" method="post">
	  <div class="form-group">
	    <input type="text" name="username" class="form-control" placeholder="Enter Username" required/>
	  </div>
	  <div class="form-group">
	    <input type="password" name="password" class="form-control" placeholder="Enter Password" required/>
	  </div>
	  <div class="form-group">
	    <input type="text" name="email" class="form-control" placeholder="Enter email" required/>
	  </div>
	  <div class="form-group">
	    <input type="text" name="address" class="form-control" placeholder="Enter Address" required/>
	  </div>
	  <button type="submit" class="btn btn-primary">회원가입</button>
	</form>
</div>

</body>
</html>
</body>
</html>