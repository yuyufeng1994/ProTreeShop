<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<nav class="navbar navbar-inverse" role="navigation">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="user/main">TreeShop树苗销售系统</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">

				<c:if test="${session_user.userName != null}">
					<ul class="nav navbar-nav navbar-right">
						<li><a href="user/info">用户名：${session_user.userName }</a></li>
						<li><a href="quit">退出</a></li>
					</ul>
				</c:if>
				<c:if test="${session_user.userName == null}">
					<ul class="nav navbar-nav navbar-right">
						<li><a href="login">登录</a></li>
						<li><a href="register">注册</a></li>
					</ul>
				</c:if>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
