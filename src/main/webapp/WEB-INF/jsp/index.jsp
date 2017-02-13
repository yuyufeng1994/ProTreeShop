<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/jsp/commons/commons.jsp"%>
<style>
#showdiv{
	
	
}
.showdivc{
	position: fixed;
	z-index: 2;
	top:200px;
	right:600px;
	background-color:white; 
	display: none; 
}

</style>
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
					<div class="panel-heading">产品列表</div>
					<div class="panel-body  row" id="tree-list">
					<div id="showdiv" class="showdivc"></div>
					
					</div>
					<button class="btn btn-default btn-block" onclick="load()"
						id="load-button">加载更多</button>
				</div>
			</div>
		</div>
	</div>
	<p align="center">@TreeShop</p>
</body>
<script type="text/javascript">
	var no = -1;
	function load() {
		no++;
		$.get("user/tree-list/" + no,
						function(res) {
							if (res.length == 0) {
								$("#load-button").text("没有更多");
								$("#load-button").hide("normal");
							}
							for (var i = 0; i < res.length; i++) {
								str = "<div class='panel panel-default col-md-3 my-panel'>"
										+ "<div class='panel-body'>"
										+ "<img title='简介："+res[i].treeBrief+"'src='thumbnail/"+res[i].treePath+"' class='myimg'>"
										+ "<p>名称："
										+ res[i].treeName
										+ "</p><p>单价："
										+ res[i].treePrice
										+ "元</p><p>产地："
										+ res[i].treePlace
										+ "</p><p>库存："
										+ res[i].treeQuantity
										+ "</p><p>截止时间："
										+ res[i].sellEndTime
										+ "</p><button class='btn btn-default btn-block' onclick='addToCart("+res[i].treeId+",this)'>加入购物车</button>"
										+ "</div></div>"
								$("#tree-list").append(str);
							}
						})

	}
	function getCartItemQuantity() {
		$.get("user/get-cart-number", function(res) {
			$("#cart-number").text(res);
		})
	}
	load();
	
	
	function addToCart(id,mythis){
		
		$this = $(mythis);
		var div = $this.parent().parent().html();
		
		$p = $("#showdiv");
		
		$div = $("<div class='showdivc'></div>").insertAfter($p);
		
		$.post("user/add-cart/"+id,function(res){
			if(res == "success"){
				$div.css("opacity",100).css("top",$this.parent().offset().top).css("left",$this.parent().offset().left).css("width",$this.parent().parent().width()).css("height",$this.parent().parent().height()).show("fast");
				$div.html(div);
				$div.show("fast");
				$div.animate({ opacity: 0,top:$("#cart-a").offset().top,left:$("#cart-a").offset().left,width:100,height:100 }).hide("fast");
				getCartItemQuantity(); 
			}else{
				alert("没有库存啦～～～");
			}
		});
		
	
		
		
	}
	
</script>
</html>


