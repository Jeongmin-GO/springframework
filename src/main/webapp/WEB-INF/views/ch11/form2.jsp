<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file = "/WEB-INF/views/common/header.jsp" %>

<div class="card m-2">
	<div class="card-header">
		DTO 객체의 필드값을 양식의 드롭다운리스트(select 태그)로 세팅
	</div>
	<div class="card-body">
		<%-- <form method="post" action="form2">
			<div class="form-group">
			  <label for="mtype">Type</label>
			  <select class="form-control" id="mtype" name="mtype">
			  	<c:forEach var="type" items="${typeList}">
				<option value="${type}" 
				<c:if test="${member.mtype == type}">selected
				</c:if>>
				${type}
				</option>
				</c:forEach>
			
				<!-- <option>일반회원</option>
				<option>기업회원</option>
				<option>헤드헌터회원</option> -->
			  </select>
			</div>
			
			<div class="form-group">
			  <label for="mjob">Job</label>
			  <select class="form-control" id="mjob" name="mjob">	
			 		<option value="">---선택하세요---</option>
			    	<c:forEach var="job" items="${jobList}">
					<option value="${job}" 
						<c:if test="${member.mjob == job}">selected
						</c:if>>
						${job}
					</option>
				</c:forEach>
<!-- 			 		<option value="학생">학생</option>
				 		<option value="개발자">개발자</option>
				 		<option value="디자이너">디자이너</option> -->	    	
			  </select>
			</div>
						
			<div class="form-group">
			  <label for="mcity">City</label>
			  <select class="form-control" id="mcity" name="mcity">	
			  	<c:forEach var="city" items="${cityList}">
			  		<option value="${city.code}"
			  			<c:if test="${member.mcity== city.code}">selected</c:if>>${city.label}</option>
			  	</c:forEach>
			 		<!-- <option value="1">서울</option>   	
			 		<option value="2">부산</option>   	
			 		<option value="3">제주</option>  -->  	
			  </select>
			</div>
			<button type="submit" class="btn btn-primary">제출</button>
		</form>  --%>
		
		<form:form method="post" modelAttribute="member" action="form2">
			<div class="form-group">
				<label for="mtype">Type</label>
				<!-- id는 path와 같은 값으로 기본 설정됨 -->
				<form:select path="mtype" items="${typeList}" class="form-control"/>
			</div>
			<div class="form-group">
				<label for="mjob">Job</label>
				<!-- id는 path와 같은 값으로 기본 설정됨 -->
				<form:select path="mjob" class="form-control">
			 		<option value="">---선택하세요---</option>	
			 		<form:options items="${jobList}"/>				
				</form:select>
			</div>
			
			<div class="form-group">
				<label for="mcity">City</label>
				<!-- id는 path와 같은 값으로 기본 설정됨 -->
				<form:select path="mcity" items="${cityList}" itemValue="code" itemLabel="label" class="form-control"/>
			</div>
			<button type="submit" class="btn btn-primary">제출</button>
		</form:form>
		
		
		
	</div>
</div>

<%@ include file = "/WEB-INF/views/common/footer.jsp" %>