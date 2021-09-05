<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file = "/WEB-INF/views/common/header.jsp" %>

<div class="card m-2">
	<div class="card-header">
		FileUpload & FileDownload
	</div>
	<div class="card-body">
		<div class="card">
			<div class="card-header">
				Form태그를 이용한 FileUpload
			</div>
			<div class="card-body">
				<form method="post" enctype="multipart/form-data" action="fileupload">
					<div class="form-group">
					  <label for="desc">File Title</label>
					  <input type="text" class="form-control" id="title" name="title" placeholder="제목">
					</div>
					<div class="form-group">
					  <label for="desc">File Description</label>
					  <input type="text" class="form-control" id="desc" name="desc" placeholder="설명">
					</div>
					<div class="form-group">
					  <label for="attach">Example file input</label>
					  <input type="file" class="form-control-file" id="attach" name="attach" multiple>
					</div>
					<button class="btn btn-link btn-sm">Form 파일 업로드</button>
					<a href="javascript:fileupload()" class="btn btn-link btn-sm">AJAX 파일 업로드</a>
				</form>
			</div>
			<script>
				function fileupload(){
					const title = $("#title").val();
					const desc = $("#desc").val();
					/*const attac = $("#attach")[0] querySelector("#attach")와 같음 */
					//multiple이어도 아니어도 첫 번째 파일을 뜻함
					//파일을 선택안하고 버튼을 누르게 되면 undefined
					const attach = document.querySelector("#attach").files[0];
					console.log(attach);
					
					//Multipart/form-data
					const formData = new FormData();
					formData.append("title", title);
					formData.append("desc", desc);
					//if(attach){}//attach가 있을 경우에만 쓰려면 
					formData.append("attach", attach);
					
					//Ajax로 서버로 전송
					//fileupload는 redirect:/가 들어있는데 ajax는 redirect안
					$.ajax({
						url : "fileuploadAjax",
						method : "POST",
						data: formData,
						cache : false,
						processData : false, // ajax가 가공하지 않고 파일 데이터 있는 그대로 전송해
						contentType: false //formData안에 속성(?)이 세 개인데 셋 다 별도의 contentType을 갖고 있음 					
					}).done((data)=>{
						console.log(data);
						if(data.result == "success"){window.alert("성공");}
						
					});
				}
			</script>
		</div>
		<div class="card">
			<div class="card-header">
				File Download
			</div>
			<div class="card-body">
				<a href="filedownload?fileNo=1" class="btn btn-link btn-sm">파일 다운로드</a>
				<hr/>
				<img src="filedownload?fileNo=1" width="200px"/>
			</div>
		</div>
	</div>
</div>

<%@ include file = "/WEB-INF/views/common/footer.jsp" %>