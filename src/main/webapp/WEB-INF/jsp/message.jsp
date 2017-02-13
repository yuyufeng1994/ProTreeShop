<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/commons/commons.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/commons/nav.jsp"%>
	<div class="container">
		<div class="row">
			<div class="col-md-2">
			</div>
			<div class="col-md-10">
				<div class="panel panel-default main-panel">
					<div class="panel-heading">消息</div>
					<div class="panel-body">${message}</div>
				</div>
			</div>
		</div>
	</div>
	<p align="center">@TreeShop</p>
</body>
</html>


