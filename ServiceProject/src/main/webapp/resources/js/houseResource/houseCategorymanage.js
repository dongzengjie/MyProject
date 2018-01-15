$(function() {

	var listUrl = '/ServiceProject/houseresourceadmin/queryhousecategorylist';
	var addURL = '/ServiceProject/houseresourceadmin/addhousecategory';
	var deleteUrl= '/ServiceProject/houseresourceadmin/deletehousecategory';
	/*var addURL = '/O2O/shopadmin/addproductcategorys';
	var deleteUrl= '/O2O/shopadmin/removeprodutcategory';*/
	
	getList();
	
	function getList(){
		$.getJSON(listUrl,function(data){
			if(data.success){
				var html='';
				data.houseCategoryList.map(function(item,index){
					html+='<div class="row row-house-category now">'
						+ '<div class="col-33 house-category-name">'
						+ item.houseCategoryName
						+ '</div>'
						+ '<div class="col-33">'
						+ item.priority
						+ '</div>'
						+ '<div class="col-33"> <a href="#" class="button delete" data-id="'
						+ item.houseCategoryId
						+ '">删除</a></div>'
						+ '</div>';

				});
				$('.category-wrap').html(html);
			}
		});
	}
	
	$('#new')
	.click(
			function() {
				var tempHtml = '<div class="row row-house-category temp">'
						+ '<div class="col-33"><input class="category-input category" type="text" placeholder="分类名"></div>'
						+ '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
						+ '<div class="col-33"><a href="#" class="button delete">删除</a></div>'
						+ '</div>';
				$('.category-wrap').append(tempHtml);

			});
	
	$('#submit').click(function(){
		var tempArr=$('.temp');
		var houseCategoryList=[];
		tempArr.map(function(index,item){
			var tempObj = {};
			tempObj.houseCategoryName=$(item).find('.category').val();
			tempObj.priority = $(item).find('.priority').val();
			if (tempObj.houseCategoryName && tempObj.priority) {
				houseCategoryList.push(tempObj);
			}
		});
		
		$.ajax({
			url:addURL,
			type:"POST",
			data:JSON.stringify(houseCategoryList),
			contentType : 'application/json',
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					getList();
				} else {
					$.toast('提交失败！');
				}
			}
		});
		
	});
	
	
	$('.category-wrap').on('click', '.row-house-category.now .delete',
			function(e) {
				var target = e.currentTarget;
				$.confirm('确定要删除吗?', function() {
					$.ajax({
						url : deleteUrl,
						type : 'POST',
						data : {
							houseCategoryId : target.dataset.id,
						
						},
						dataType : 'json',
						success : function(data) {
							if (data.success) {
								$.toast('删除成功！');
								getList();
							} else {
								$.toast('删除失败！');
							}
						}
					});
				});
			});
	
	$('.category-wrap').on('click', '.row-house-category.temp .delete',
			function(e) {
				
				$(this).parent().parent().remove();

			});
});