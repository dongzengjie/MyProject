$(function() {

	var listUrl = '/O2O/shopadmin/getproductcategorylist';
	var addURL = '/O2O/shopadmin/addproductcategorys';
	var deleteUrl= '/O2O/shopadmin/removeprodutcategory';

	getList();
	
	
	
	function getList() {
		$
				.getJSON(
						listUrl,
						function(data) {
							if (data.success) {
								var html = '';
								data.productcategorylist
										.map(function(item, index) {
											html += '<div class="row row-product-category now"> '
													+ '<div class="col-33 product-category-name">'
													+ item.productCategoryName
													+ '</div>'
													+ '<div class="col-33">'
													+ item.priority
													+ '</div>'
													+ '<div class="col-33"> <a href="#" class="button delete" data-id="'
													+ item.productCategoryId
													+ '">删除</a></div>'
													+ '</div>'

										});
								$('.category-wrap').html(html);
							}
						});
	}

	$('#new')
			.click(
					function() {
						var tempHtml = '<div class="row row-product-category temp">'
								+ '<div class="col-33"><input class="category-input category" type="text" placeholder="分类名"></div>'
								+ '<div class="col-33"><input class="category-input priority" type="number" placeholder="优先级"></div>'
								+ '<div class="col-33"><a href="#" class="button delete">删除</a></div>'
								+ '</div>';
						$('.category-wrap').append(tempHtml);

					});
	
	$('#submit').click(function(){
		var tempArr = $('.temp');
		var productCategoryList = [];
		tempArr.map(function(index, item) {
			var tempObj = {};
			tempObj.productCategoryName = $(item).find('.category').val();
			tempObj.priority = $(item).find('.priority').val();
			if (tempObj.productCategoryName && tempObj.priority) {
				productCategoryList.push(tempObj);
			}
		});
		$.ajax({
			url : addURL,
			type : 'POST',
			data : JSON.stringify(productCategoryList),
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
	

	$('.category-wrap').on('click', '.row-product-category.now .delete',
			function(e) {
				var target = e.currentTarget;
				$.confirm('确定要删除吗?', function() {
					$.ajax({
						url : deleteUrl,
						type : 'POST',
						data : {
							productCategoryId : target.dataset.id,
						
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
	
	$('.category-wrap').on('click', '.row-product-category.temp .delete',
			function(e) {
				
				$(this).parent().parent().remove();

			});

});