<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file = "/WEB-INF/views/common/header.jsp" %>

<div class="card m-2">
	<div class="card-header">
		게시물 목록
	</div>
	<div class="card-body">
	    <table class="table table-sm table-bordered">
	         <tr>
	            <th style="width:50px">번호</th>
	            <th style="width:200px">제목</th>
	            <th style="width:100px">글쓴이</th>
	            <th style="width:100px">날짜</th>
	         </tr>
	         
	         <c:forEach var="board" items="${boards}">
	            <tr>
	               <td>${board.bno}</td>
	               <td><a href="boardDetail?bno=${board.bno}">${board.btitle}</a></td>
	               <td>${board.mid}</td>
	               <td><fmt:formatDate value="${board.bdate}" pattern="yyyy-MM-dd"/></td>
	            </tr>
	         </c:forEach>
	         
	         <tr>
	         	<!-- 위에 번호, 제목, 글쓴이, 날짜 개수만큼 합치고 센터정렬  -->
	         	<td colspan="4" class="text-center">
	         		<div>
	         			<%-- [처음][이전] 1, 2, 3, 4, 5 [다음][맨끝]--%>
	         			<a class="btn btn-outline-primary btn-sm" href="boardList?pageNo=1">처음</a>
	         			<!-- 현재 그룹번호가 1이상일 경우에만 나타나게 함 -->
	         			<c:if test="${pager.groupNo>1}">
	         				<a class="btn btn-outline-info btn-sm" href="boardList?pageNo=${pager.startPageNo-1}">이전</a>
	         			</c:if>
	         			
	         			<c:forEach var="i" begin="${pager.startPageNo}" end="${pager.endPageNo}">
	         				<c:if test="${pager.pageNo!= i}">
	         					<a class="btn btn-link" href="boardList?pageNo=${i}">${i}</a>
	         				</c:if>
	         				<c:if test="${pager.pageNo== i}">
	         					<a class="btn btn-outline-danger" href="boardList?pageNo=${i}">${i}</a>
	         				</c:if>
	         			</c:forEach>
	         			
	         			<!-- 현재 그룹번호가 전체 그룹 수보다 작을 경우에만 나타나게 함 -->
	         			<c:if test="${pager.groupNo<pager.totalGroupNo}">
	         				<a class="btn btn-outline-info btn-sm" href="boardList?pageNo=${pager.endPageNo+1}">이후</a>
	         			</c:if>
	         			
	         			<a class="btn btn-outline-primary btn-sm" href="boardList?pageNo=${pager.totalPageNo}">맨끝</a>
	         		</div>
	         	</td>
	         </tr>
	      </table>
	      <div class="mt-2">
	      		<a href="boardWriteForm" class="btn btn-warning btn-sm">새 글 작성</a>
	      </div>
	</div>
</div>

<%@ include file = "/WEB-INF/views/common/footer.jsp" %>