<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file = "/WEB-INF/views/common/header.jsp" %>

<div class="card m-2">
	<div class="card-header">
		500.jsp
	</div>
	<div class="card-body">
		<p> 어떠한 이유때문에 서버가 처리하지 못함 </p>
		<p> 일시 서버 오류이므로 잠시 후 다시 시도해주세요. </p>
		<div>
			<a href="${pageContext.request.contextPath}/" class="btn btn-outline-danger btn-sm">홈으로 가기</a>
		</div>
	</div>
</div>

<%@ include file = "/WEB-INF/views/common/footer.jsp" %>