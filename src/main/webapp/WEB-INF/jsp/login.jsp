<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/commons/commons.jsp"%>
<style>
body {
	background-image: url(resource/dist/image/login-bac.jpg);
	-moz-background-size: cover;
	-ms-background-size: cover;
	-webkit-background-size: cover;
	background-size: cover;
}
</style>
</head>
<body>

	<div class="row"  style="margin-top: 135px;">
		<div class='col-md-4'></div>
		<div class='col-md-3'>
			<h2 align="center" style="color:white">TreeShop树苗销售系统</h2>
			<div class="panel panel-default">
				<div class="panel-heading"><i class="glyphicon glyphicon-user"></i>  用户登录</div>
				<div class="panel-body">
					<c:if test="${message !=null }">
						<div class="alert alert-warning" role="alert">
							<i class="glyphicon glyphicon-warning-sign"></i> ${message}
						</div>
					</c:if>
					<form action="login-sub" method="post" class="form-horizontal">
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">用户名</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="inputEmail3"
									name="userName" required="required">
							</div>
						</div>
						<div class="form-group">
							<label for="inputEmail1" class="col-sm-3 control-label">密码</label>
							<div class="col-sm-9">
								<input class="form-control" id="inputEmail1" name="userPwd"
									type="password" required="required">
							</div>
						</div>

						<div class="form-group">
							<div class='col-sm-6'>
								<button class="btn btn-primary btn-block" type="submit">登录</button>
							</div>
							<div class='col-sm-6'>
								<a class="btn btn-default btn-block" href="register">立即注册</a>
							</div>
						</div>

					</form>
				</div>
			</div>

		</div>
	</div>
</body>
</html>