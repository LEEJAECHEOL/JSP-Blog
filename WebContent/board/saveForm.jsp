<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
	<form action="#" method="POST">
	
		<div class="form-group">
			<label for="title">Title:</label>
			<input type="text" class="form-control" placeholder="title" id="title" name="title">
		</div>
	
		<div class="form-group">
			<label for="content">Content:</label>
  			<div id="summernote"></div>
		</div>
	
		<button type="submit" class="btn btn-primary">글쓰기 등록</button>
	</form>
</div>
  <script>
    $(document).ready(function() {
        $('#summernote').summernote({
			placeholder:'글을 쓰세요.',
			tabsize:2,
			height:400
        });
    });
  </script>

</body>
</html>