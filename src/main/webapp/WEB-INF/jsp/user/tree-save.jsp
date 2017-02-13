<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/commons/commons.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/commons/nav.jsp"%>
	<script src="resource/cxselect/jquery.cxselect.min.js"></script>
	<div class="container">
		<div class="row">
			<div class="col-md-2">
				<%@ include file="/WEB-INF/jsp/commons/left-nav.jsp"%>
			</div>
			<div class="col-md-10">
				<div class="panel panel-default main-panel">
					<div class="panel-heading">树苗管理-增加</div>
					<div class="panel-body">
						<form class="form-inline" method="post"
							action="user/tree-save-sub" enctype="multipart/form-data">
							<table class="table">
								<tr>
									<th>图片</th>
									<td><img width="100" height="100"
										src="thumbnail/${t.treePath }" class="img-rounded"> <input
										class="form-control" type="file" name="file"> <input
										name="treePath" value="${t.treePath }" type="hidden">
										<input name="pageNo" value="${pageNo}" type="hidden">
									</td>

									<td></td>
								</tr>
								<tr>
									<th>名称</th>
									<td><input type="hidden" name="treeId"
										value="${t.treeId  }"> <input class="form-control"
										type="text" required="required" name="treeName"
										value="${t.treeName }"></td>
									<td></td>
								</tr>
								<tr>
									<th>品种</th>
									<td><select class="form-control" name="treeType"
										id="treeType">
											<option value="常绿乔木">常绿乔木</option>
											<option value="常绿灌木">常绿灌木</option>
											<option value="落叶乔木">落叶乔木</option>
									</select> <script type="text/javascript">
										var retype = "${t.treeType}"
										if (retype != null && retype != "") {
											$("#treeType").val(retype)
										}
									</script></td>

									<td></td>
								</tr>
								<tr>
									<th>产地</th>
									<td>
										<div id="element_id">
											<select class="province form-control" name="treePlace">
												<c:if test="${t!=null}">
													<option value="${t.treePlace}" selected>${t.treePlace}</option>
												</c:if>
												<c:if test="${t==null}">
													<option value="浙江省" selected>浙江省</option>
												</c:if>
											</select>
										</div>
									</td>
									<td></td>
								</tr>
								<tr>
									<th>价格</th>
									<td><input class="form-control" type="text"
										required="required" name="treePrice" value="${t.treePrice}"></td>
									<td></td>
								</tr>
								<tr>
									<th>销售时间</th>
									<td><input class="form-control" type="date"
										name="sellStartTime" id="sellStartTime"> 至 <input
										class="form-control" type="date" name="sellEndTime"
										id="sellEndTime"> <script type="text/javascript">
											$("#sellStartTime").val('<fmt:formatDate value="${t.sellStartTime}" pattern="yyyy-MM-dd" />');
											$("#sellEndTime").val('<fmt:formatDate value="${t.sellEndTime}" pattern="yyyy-MM-dd" />');
										</script></td>
									<td></td>
								</tr>
								<tr>
									<th>库存</th>
									<td><input class="form-control" type="number" value="${t.treeQuantity}"
										required="required" name="treeQuantity"
										></td>
									<td></td>
								</tr>
								<tr>
									<th>简介</th>
									<td><textarea name="treeBrief" class="form-control"
											rows="1" cols="50">${t.treeBrief }</textarea></td>
									<td></td>
								</tr>
								<tr>
									<th></th>
									<td><input class="btn btn-primary" type="submit"
										value="提交">&nbsp;&nbsp;&nbsp;&nbsp; <a
										href="javascript:self.location=document.referrer;"
										class="btn btn-default">返回</a></td>
									<td></td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<p align="center">@TreeShop</p>
</body>
<script type="text/javascript">
	$('#element_id').cxSelect({
		url : 'resource/cxselect/cityData.min.json', // 如果服务器不支持 .json 类型文件，请将文件改为 .js 文件
		selects : [ 'province' ], // 数组，请注意顺序
		emptyStyle : 'none'
	});
</script>
</html>


