<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath }/plugins/jquery-3.2.1.min.js"></script>
<title>Insert title here</title>
<style type="text/css">
	.panel{
		margin:0 auto;
		text-align: center;
	}
	.myselector{
		width:300px;
		height:40px;
	}
</style>
</head>
<body>
<div class="panel">
	<select class="myselector" id="provinceSelector">
		<option value="">请选择省份...</option>	
	</select>
	<select class="myselector"  id="citySelector">
		<option value="">请选择城市...</option>	
	</select>
	<select class="myselector"  id="districtSelector">
		<option value="">请选择城区...</option>	
	</select>
</div>
<script type="text/javascript">
	//TODO 利用$.ajax API 实现某一省数据
	$(function(){
		initProvince();		
		
		$("#provinceSelector").change(function(){
			// 获取选中的省编号
			var provinceCode = $(this).find("option:selected").val();

			//TODO 利用$.get API 实现某一省下的所有市数据加载
			selectProvince(provinceCode);
		});
		
		$("#citySelector").change(function(){
			// 获取选中的省编号
			var cityCode = $(this).find("option:selected").val();

			//TODO 利用$.post API 实现某一市下的所有区数据加载
			selectCity(cityCode);
		});
	});
	function initProvince(){
		$.ajax({
			url : "spring/select/province",
			type : "post",
			data : {},
			dataType : "json",
			success : function(json){
				for(var i = 0;i<json.code.length;i++){
					$("#provinceSelector").append("<option value='"+json.code[i]+"'>"+json.name[i]+"</option>");
				}
			}
		});
	}
	function selectProvince(provinceCode){
		var param = {provinceCode:provinceCode};
		$.get({
			url : "spring/select/city",
			data : param,
			type : "json",
			success : function(data){
				var temp = eval("("+data+")");
				for(var i = 0;i < temp.code.length;i++){
					$("#citySelector").append("<option value='"+temp.code[i]+"'>"+temp.name[i]+"</option>");
				}
			}
		});
	}
	function selectCity(cityCode){
		$.post("spring/select/district",
				{cityCode:cityCode},
				function(data){
					for(var i = 0;i<data.code.length;i++){
						$("#districtSelector").append("<option value='"+data.code[i]+"'>"+data.name[i]+"</option>");
					}
				},"json");
	}
</script>
</body>
</html>