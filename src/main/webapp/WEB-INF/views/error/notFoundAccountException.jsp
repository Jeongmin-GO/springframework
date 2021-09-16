<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file = "/WEB-INF/views/common/header.jsp" %>

<div class="card m-2">
	<div class="card-header">
		계좌 이체 실패
	</div>
	<div class="card-body">
		<p> ${error}</p>
		<div>
			<a href="${pageContext.request.contextPath}/" class="btn btn-outline-danger btn-sm">홈으로 가기</a>
		</div>
	</div>
</div>

<%@ include file = "/WEB-INF/views/common/footer.jsp" %>