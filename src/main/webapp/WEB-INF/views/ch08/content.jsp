<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ include file = "/WEB-INF/views/common/header.jsp" %>

<div class="card m-2">
	<div class="card-header">
		Session Support
	</div>
	<div class="card-body">
		<div class="card">
			<div class="card-header">
				세션 원리 : JSESSIONID 쿠키
			</div>
			<div class="card-body">
				<p>세션 객체 생성 -> JSESSIONID 쿠키 굽기 </p>
				<p> 브라우저 :JSESSIONID 쿠키 전송 -> 세션 객체 찾음 -> 세션 객체 이용</p>
				<a href="javascript:saveData()" class="btn btn-primary btn-sm">세션에 데이터 저장</a> 
				<a href="javascript:readData()" class="btn btn-primary btn-sm">세션 데이터 읽기</a> 
			</div>
			<script>
				function saveData(){
					$.ajax({
						url : "saveData",
						data : {name : "홍길동"}
					}).done((data)=>{
						console.log(data);
					});
				}
				
				function readData(){
					$.ajax({
						url : "readData"
					}).done((data)=>{
						console.log(data);
						console.log(data.name);
					});
					
				}
			</script>
		</div>
		
		<div class="card">
			<div class="card-header">
				form을 통한 login 처리 
			</div>
			<div class="card-body">
				<c:if test="${sessionMid == null}">
					<a href="login" class="btn btn-primary btn-sm">로그인 폼 요청</a> 
				</c:if>
				<c:if test="${sessionMid != null}">
					<a href="logout" class="btn btn-dark btn-sm">로그아웃</a> 
				</c:if>
			</div>
		</div>
		<div class="card">
			<div class="card-header">
				ajax를 통한 login 처리 
			</div>
			<div class="card-body">
				<c:if test="${sessionMid==null}">
					<form>
			          <div class="input-group">
			             <div class="input-group-prepend"><span class="input-group-text">mid</span></div>
			             <input id="mid" type="text" name="mid" class="form-control">
			             <span id="mid-error" class="error"></span>
			          </div>
			          <div class="input-group">
			             <div class="input-group-prepend"><span class="input-group-text">mpassword</span></div>
			             <input id="mpassword" type="password" name="mpassword" class="form-control">
			             <span id="mpassword-error" class="error"></span>
			          </div>
			       </form>
		       </c:if>
				<div class="mt-2">
					<c:if test="${sessionMid == null}">
						<a href="javascript:login()" class="btn btn-primary btn-sm">로그인</a> 
					</c:if>
					<c:if test="${sessionMid != null}">
						<a href="javascript:logout()" class="btn btn-dark btn-sm">로그아웃</a> 
					</c:if>
				</div>
				<script>
					function login(){
						let mid = $("#mid").val();
						let mpassword = $("#mpassword").val();
						$.ajax({
							url: "loginAjax",
							data : {mid, mpassword}, //{mid : mid , mpassword : mpassword},
							method : "post"
						}).done((data)=>{
							//로그인 성공 data = {reseult : "success"}
							//아이디 실패 data = {result : "wrongMid"}
							//비밀번호 실패 data = {result : "wrongMpassword"}
							const midError = $("#mid-error");
							const mpasswordError = $("mpassword-error");
							
							midError.html("");
							mpasswordError.html("");
							
							if(data.result == "success"){
								//위에 form과 동기화도 해야하고
								//위 c:if문을 실행하기 위해
								//현재 페이지 전체를 다시 서버에서 받아오도록 함 
								window.location.reload(); //ajax답지않음 -> 부분을 변경해줘야 하는데 전체를 바꿔서 
							}else if (data.result =="wrongMid"){
								midError.html("아이디가 잘못되었습니다.");
							}else if (data.result = "wrongMpassword"){
								mpasswordError.html("비밀번호가 잘못되었습니다.");
							}
						});
					}
					function logout(){
						$.ajax({
							url : "logoutAjax"
						}).done((data)=>{
							//data = {result : "success"}온다고 가정
							window.location.reload();
						});
					}
				</script>
			</div>
		</div>
		<div class="card">
			<div class="card-header">
				@SessionAttributes를 이용한 다단계 입력처리
			</div>
			<div class="card-body">
				<a href="inputStep1" class="btn btn-warning btn-sm">1단계 입력</a>
			</div>
		</div>
		
	</div>
</div>

<%@ include file = "/WEB-INF/views/common/footer.jsp" %>