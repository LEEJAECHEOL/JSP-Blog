/**
 * 
 */
 
 function deleteById(id, userId){
	var data = {
		id : id,
		userId : userId
	};
	$.ajax({
		type : "POST",
		url : "http://localhost:8000/blog/board?cmd=delete",
		data : JSON.stringify(data),
		contentType : "application/json;charset=utf-8",
		dataType:"json"
	})
	.done(function(result){
		if(result.statusCode === 1){
			location.href = "board?cmd=list&page=0";
		}else {
			alert("게시글 삭제 실패");
		}
	});
}
function addReply(data){

	var replyItem = `<li id="reply-${data.id}" class="media">`;
	replyItem += `<div class="media-body">`;
	replyItem += `<strong class="text-primary">${data.username}</strong>`;
	replyItem += `<p>${data.content}.</p></div>`;
	replyItem += `<div class="m-2">`;

	replyItem += `<i onclick="deleteReply(${data.id})" class="material-icons">delete</i></div></li>`;

	$("#reply__list").prepend(replyItem);
}
function replySave(userId, boardId){
	var data = {
		userId : userId,
		boardId : boardId,
		content : document.querySelector('#content').value
	};
	$.ajax({
		type:"post",
		url:"/blog/reply?cmd=save",
		data : JSON.stringify(data),
		contentType:'application/json;charset=utf-8',
		dataType:'json'
		
	}).done(function (result){
		if(result.statusCode === 1){
			addReply(result.data);
			$("#content").val('');
		}else {
			alert("댓글쓰기 실패");
		}
	});
}
function deleteReply(id){
	var data = {id:id};
	console.log(data);
	$.ajax({
		type:"post",
		url:"/blog/reply?cmd=delete",
		data : JSON.stringify(data),
		contentType:'application/json;charset=utf-8',
		dataType:'json'
	}).done(function (result){
		$('#reply-'+id).remove();
	});
	
}