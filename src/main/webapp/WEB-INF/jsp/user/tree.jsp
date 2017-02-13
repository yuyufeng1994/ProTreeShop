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
					<div class="panel-heading">
						树苗管理 &nbsp;&nbsp;&nbsp;<a class="btn btn-info btn-sm"
							style="float: right; margin-top: -6px;" href="user/tree-save"><i
							class="glyphicon glyphicon-plus"></i> 增加</a>
					</div>
					<div class="panel-body">

						<table class="table mytable">
							<tr>
								<th>图片</th>
								<th>名称</th>
								<th>品种</th>
								<th>产地</th>
								<th>价格(RMB)</th>
								<th>销售时间</th>
								<th>库存</th>
								<th>简介</th>
								<th>操作</th>
							</tr>
							<c:forEach items="${page.content}" var="t">
								<tr>
									<td><img src="thumbnail/${t.treePath}" width="50"
										height="50"></td>
									<td>${t.treeName }</td>
									<td>${t.treeType }</td>
									<td>${t.treePlace }</td>
									<td>${t.treePrice }</td>
									<td><fmt:formatDate value="${t.sellStartTime }"
											pattern="yyyy-MM-dd" />至<fmt:formatDate
											value="${t.sellEndTime }" pattern="yyyy-MM-dd" /></td>
									<td>${t.treeQuantity }</td>
									<td>${t.treeBrief }</td>
									<td><a
										href="user/tree-save?id=${t.treeId }&pageNo=${page.number}"
										class="btn btn-sm btn-warning" >修改</a> <a
										href="user/tree-delete?id=${t.treeId }&pageNo=${page.number}"
										class="btn btn-danger btn-sm" onclick="javascript:return p_del()">删除</a></td>

								</tr>

							</c:forEach>
							<tr>
								<td colspan="10">
									<nav>
										<ul class="pagination">
											<li><a href='user/tree/0'>首页</a></li>
											<c:if test="${page.number > 0}">
												<li><a href='user/tree/${page.number - 1}'>&laquo;上一页</a>
												</li>
											</c:if>
											<li class="disabled"><c:if test="${page.number <= 0}">
													<a  >&laquo;上一页</a>
												</c:if></li>
											<li class="disabled"><a>当前第 ${page.number + 1 } 页
													共${ page.totalPages} 页</a></li>
											<c:if test="${page.number < page.totalPages - 1}">
												<li><a href='user/tree/${page.number + 1}'>下一页&raquo;</a>
												</li>
											</c:if>
											<c:if test="${page.number >= page.totalPages - 1}">
												<li class="disabled"><a >下一页&raquo;</a></li>
											</c:if>
											<li><a href='user/tree/${page.totalPages - 1}'>末页</a></li>
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
function p_del() {
	var msg = "确定删除吗？";
	if (confirm(msg)==true){
		return true;
	}else{
		return false;
	}
}
</script>
</html>


