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
					<div class="panel-heading">交易记录</div>
					<div class="panel-body">
						<table class="table mytable">
							<tr>
								<th>交易编号</th>
								<th>交易时间</th>
								<th>交易金额</th>
								<th>交易用户</th>
								<th>交易状态</th>
								<th>操作</th>
							</tr>
							<c:forEach items="${page.content}" var="t">
								<tr>
									<td>${t.tradeId}</td>
									<td>${t.tradeTime}</td>
									<td>${t.totalPrice} 元</td>
									<td>${t.user.userName}</td>
									<td>${t.tradeState}</td>
									<td><button class="btn btn-info btn-sm"
											onclick="show(this)">查看详情</button>
										<div class="detail-div">
											<table class="table" id="mytable">
												<tr>
													<th>名称</th>
													<th>单价</th>
													<th>数量</th>
												</tr>
												<c:forEach items="${t.items}" var="m">
													<tr>
														<td>${m.itemTree.treeName }</td>
														<td>${m.itemTree.treePrice} 元</td>
														<td>${m.itemQuantity}</td>
													</tr>
												</c:forEach>
											</table>

										</div></td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="5">
									<nav>
										<ul class="pagination">
											<li><a href='user/history/0'>首页</a></li>
											<c:if test="${page.number > 0}">
												<li><a href='user/history/${page.number - 1}'>&laquo;上一页</a>
												</li>
											</c:if>
											<li class="disabled"><c:if test="${page.number <= 0}">
													<a>&laquo;上一页</a>
												</c:if></li>
											<li class="disabled"><a>当前第 ${page.number + 1 } 页
													共${ page.totalPages} 页</a></li>
											<c:if test="${page.number < page.totalPages - 1}">
												<li><a href='user/history/${page.number + 1}'>下一页&raquo;</a>
												</li>
											</c:if>
											<c:if test="${page.number >= page.totalPages - 1}">
												<li class="disabled"><a>下一页&raquo;</a></li>
											</c:if>
											<li><a href='user/history/${page.totalPages - 1}'>末页</a></li>
										</ul>
									</nav>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<p align="center">@TreeShop</p>
</body>
<script type="text/javascript">
	function show(mythis) {
		$p = $(mythis).parent();
		$p.parent().parent().find(".detail-div").each(function() {
			$(this).hide();
		})
		$p.find(".detail-div").show("fast");
	}
</script>
</html>


