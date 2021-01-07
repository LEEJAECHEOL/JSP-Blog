<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<div class="container">

	<div class="m-2">
		<form class="form-inline d-flex justify-content-end" action="/blog/board">
			<input type="hidden" name="cmd" value="search" />
			<input type="hidden" name="page" value="0" />
			
			<input type="text" name="keyword" class="form-control mr-sm-2" placeholder="Search">			
			<button class="btn btn-primary m-1">검색</button>
		
		</form>
	</div>

	<div class="progress col-md-12 m-2">
		<div class="progress-bar" style="width: 70%"></div>
	</div>
	<c:choose>
	  	<c:when test="${requestScope.list!=null}">
			<c:forEach var="boardVO" items="${requestScope.list}">
				<div class="card col-md-12 m-2">
					<div class="card-body">
						<h4 class="card-title">${boardVO.title}</h4>
						<p>작성일자 : ${boardVO.createDate}</p>
						<p>조회수 : ${boardVO.readCount}</p>
						<a href="#" class="btn btn-primary">상세보기</a>
					</div>
				</div>
				<br />
			</c:forEach>
	  	</c:when>
	  	<c:otherwise>
	  		<h3>등록된 게시물이 없습니다.</h3>
	  	</c:otherwise>
  	</c:choose>
	
	<ul class="pagination justify-content-center">
		<li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
		<li class="page-item"><a class="page-link" href="#">Next</a></li>
	</ul>
</div>


</body>
</html>