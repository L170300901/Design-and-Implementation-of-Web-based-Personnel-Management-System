<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- jquery -->
<script
	src="${pageContext.request.contextPath}/script/jquery/jquery-3.3.1.min.js"></script>


<!-- jquery-ui -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/script/jquery-ui/jquery-ui.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/script/jquery-ui/jquery-ui.structure.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/script/jquery-ui/jquery-ui.theme.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/script/jquery-ui/menubar.css" />
<script
	src="${pageContext.request.contextPath}/script/jquery-ui/jquery-ui.min.js"></script>

<!-- 주소검색 -->
<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>

<%-- 
<!-- jqgrid -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/script/jqgrid/css/ui.jqgrid.css" />
<script
	src="${pageContext.request.contextPath}/script/jqgrid/js/i18n/grid.locale-kr.js"></script>
<script
	src="${pageContext.request.contextPath}/script/jqgrid/js/jquery.jqGrid.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/script/jqgrid/plugins/ui.multiselect.css" />
<script src="http://code.jquery.com/jquery-migrate-1.2.1.js"></script>
--%>

<!-- timepicker -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/script/jquery-ui/jquery.timepicker.css" />
<script
	src="${pageContext.request.contextPath}/script/jquery/jquery.timepicker.min.js"></script>


<!-- fileUpload -->
<script
	src="${pageContext.request.contextPath}/script/jquery/jquery.form.min.js"></script>

<style>
/* 메뉴바 jqgrid 위로 보이게하기 */
.menu_ul {
	position: relative;
	z-index: 1000;
}

#menu {
	background-color: #79ABFF;
}

body {
	/*background-image:
		url("${pageContext.request.contextPath}/image/back.jpg");*/
	background-repeat: no-repeat;
	background-size: 100% 100%;
}
</style>
