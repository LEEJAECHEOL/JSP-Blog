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
	 	<button type="button" class="btn btn-info " onClick="goPopup();">주소검색</button>
	    <input type="text" name="address" id="address" class="form-control" placeholder="Enter Address" required readonly/>
	  </div>
	  <button type="submit" class="btn btn-primary">회원가입</button>
	</form>
</div>
<script>

function goPopup(){
	var pop = window.open("/blog/test/jusoPopup.jsp","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
	 
}

function jusoCallBack(roadFullAddr){
	document.querySelector('#address').value = roadFullAddr;
		
}

</script>
</body>
</html>
</body>
</html>