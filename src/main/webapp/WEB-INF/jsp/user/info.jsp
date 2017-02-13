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
				<%@ include file="/WEB-INF/jsp/commons/left-nav.jsp"%>
			</div>
			<div class="col-md-10">
				<div class="panel panel-default main-panel">
					<div class="panel-heading">个人信息</div>
					<div class="panel-body">
						<table class="table">
							<tr>
								<th>ID</th><td>${session_user.userId }</td>
							</tr>
							<tr>
								<th>用户名</th><td>${session_user.userName }</td>
							</tr>
							<tr>
								<th>注册时间</th><td>${session_user.userCreateTime }</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<p align="center">@TreeShop</p>
</body>
</html>


