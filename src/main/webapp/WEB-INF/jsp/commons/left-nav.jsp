<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<ul class="list-group">
	<a href="user/main" class="list-group-item">产品列表</a>
	<a href="user/cart" class="list-group-item" id="cart-a">购物车 <span class="badge"
		id="cart-number">${cart_quantity}</span></a>
	<a href="user/trade/0" class="list-group-item">我的订单</a>
	<a href="user/info" class="list-group-item">个人信息</a>
	<c:if test="${session_user.userName == 'yyf'}">
		<a href="user/tree/0" class="list-group-item">树苗管理</a>
		<a href="user/history/0" class="list-group-item">交易记录</a>
	</c:if>
</ul>