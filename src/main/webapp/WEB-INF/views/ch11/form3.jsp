<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file = "/WEB-INF/views/common/header.jsp" %>

<div class="card m-2">
	<div class="card-header">
		DTO 객체의 필드값을 양식의 드롭다운리스트(checkbox 태그)로 세팅
	</div>
	<div class="card-body">
		<form method="get" action="form3">
			<c:forEach var="language" items="${languageList}" varStatus="status">
				<span>
				  <input type="checkbox" id="lang${status.count}" name="lang" value="${language}"
				  	<c:forEach var="temp" items="${member.mlanguage}">
				  		<c:if test="${temp == language}">checked</c:if>
				  	</c:forEach>
				  >
				  <label class="form-check-label" for="lang${status.count}">${language}</label>
				</span>
			</c:forEach>
			<button class="btn btn-outline-success btn-sm">제출</button>
		</form>
		<form:form modelAttribute="member" method="post" action="form3" class="mt-3"><!-- method(post)와 action은 생략 가능 -->
			<div class="form-check form-check-inline">
				<form:checkboxes items="${languageList}" path="mlanguage" class="ml-2 mr-2"/>
			</div>
			<button class="btn btn-outline-success btn-sm">제출</button>
		</form:form>
		
		<form:form modelAttribute="member" method="post" action="form3" class="mt-3"><!-- method(post)와 action은 생략 가능 -->
			<div class="form-check form-check-inline">
				<form:checkboxes items="${skillList}" path="mskill" itemValue="code" itemLabel="label" class="ml-2 mr-2"/>
			</div>
			<button class="btn btn-outline-success btn-sm">제출</button>
		</form:form>
		<div class="card m-2">
	
		<div class="card-header">
			경무
		</div>
		<div class="card-body">
		<form:form modelAttribute="member" method="post" action="form3">
	
				<div>
					<form:checkboxes items="${skillList}" path="mskill"
						itemValue="code" itemLabel="label"
					/>
				</div>
				<button class="btn btn-success btn-sm">제출</button>
			</form:form>
		</div>
</div>
		
<%-- 		<form method="get" action="form3">
			<c:forEach var="language" items="${languageList}" varStatus="status">
				<div class="form-check form-check-inline">
				  <input class="form-check-input mr-2 ml-2" type="checkbox" id="lang${status.count}" name="lang" value="${language}"
				  	<c:forEach var="temp" items="${member.mlanguage}">
				  		<c:if test="${temp == language}">checked</c:if>
				  	</c:forEach>
				  >
				  <label class="form-check-label" for="lang${status.count}">${language}</label>
				</div>
			</c:forEach>
		</form>
		<form:form modelAttribute="member"><!-- method(post)와 action은 생략 가능 -->
			<div class="form-check form-check-inline">
				<form:checkboxes items="${languageList}" path="mlanguage" class="ml-2"/>
			</div>
		</form:form> --%>
	</div>
</div>

<%@ include file = "/WEB-INF/views/common/footer.jsp" %>