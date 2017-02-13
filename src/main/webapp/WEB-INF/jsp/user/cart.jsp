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
					<div class="panel-heading">购物车</div>
					<div class="panel-body">
						<h1>${message}</h1>
						<table class="table" id="mytable">
							<tr id="ftr">
								<th>名称</th>
								<th>单价</th>
								<th>数量</th>
								<th>操作</th>
							</tr>
						</table>

						<script type="text/javascript">
							var totalPrice = 0;
							function getCart() {
								$("#ftr").nextAll().remove()
								$
										.post(
												"user/get-cart",
												function(res) {
													if (null == res
															|| res.items == null) {
														$("#mytable")
																.append(
																		"<tr><th colspan='5'><h1>空荡荡的购物车～</h1><t/h><tr>");
														return;
													}

													totalPrice = 0;
													for (var i = 0; i < res.items.length; i++) {
														var str = "<tr title='"+res.items[i].itemTree.treeBrief+"'><td>"

																+ res.items[i].itemTree.treeName
																+ "</td><td>"
																+ res.items[i].itemTree.treePrice
																+ " 元"
																+ "</td><td>"
																+ "<button onclick='decrItem("
																+ res.items[i].itemTree.treeId
																+ ")'><i class='glyphicon glyphicon-minus'></i></button> "
																+ res.items[i].itemQuantity
																+ " <button onclick='addItem("
																+ res.items[i].itemTree.treeId
																+ ")'><i class='glyphicon glyphicon-plus'></i></button>"
																+ "</td><td>"
																+ "<button class='btn btn-danger btn-sm'onclick='deleteItem("
																+ res.items[i].itemTree.treeId
																+ ")'>删除</button>"
																+ "</td></tr>"
														$("#ftr").after(str);
														/* $("#mytable").append(
																str); */
														totalPrice += res.items[i].itemTree.treePrice
																* res.items[i].itemQuantity
													}

													var str = "<tr><td colspan='5'></td></tr><tr><th>总价</th><td colspan='1'>"
															+ totalPrice
															+ " 元"
															+ "</td><td><a class='btn btn-default btn-block' href='user/cart-clear'>取消</a></td><td  colspan='2'><a class='btn btn-warning btn-block' onclick='javascript:return p_del()' href='user/pay'>立即付款</a></td></tr>"
													$("#mytable").append(str);
												})
							}

							getCart();

							function addItem(id) {
								$.post("user/item-add/" + id, function(res) {
									if (res == "success") {
										getCart();
									}else{
										alert("超过库存啦～～～")
									}
								})

							}

							function decrItem(id) {
								$.post("user/item-decr/" + id, function(res) {
									if (res == "success") {
										getCart();
									}else{
										alert("不想要就直接点右边的删除吧～～～")
									}
								})
							}
							
							function deleteItem(id) {
								$.post("user/item-delete/" + id, function(res) {
									if (res == "success") {
										getCart();
										getCartItemQuantity();
									}
								})
							}
						</script>
					</div>
				</div>
			</div>
		</div>

		<p align="center">@TreeShop</p>
	</div>
</body>
<script type="text/javascript">
	function p_del() {
		var msg = "确定付款吗？（假的，别怕，放心点！！）";
		if (confirm(msg) == true) {
			return true;
		} else {
			return false;
		}
	}
	function getCartItemQuantity() {
		$.get("user/get-cart-number", function(res) {
			$("#cart-number").text(res);
		})
	}
</script>
</html>