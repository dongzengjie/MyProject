$(function() {
	var productId = getQueryString('productId');
	var isEdit = productId ? true : false;
	var productCategoryinfoUrl = '/O2O/shopadmin/getproductcategorylist';
	var addproductURL = '/O2O/shopadmin/addproduct';
	var getproductinfoURL = '/O2O/shopadmin/getproductbyid?productId='
			+ productId
	var modifyprodyctURL = '/O2O/shopadmin/modifyproduct';

	if (isEdit) {
		getproductinfo(productId);
	} else {
		getProductinitInfo();
	}

	function getproductinfo(productId) {
		$.getJSON(getproductinfoURL, function(data) {
			if (data.success) {
				var product = data.product;
				$('#product-name').val(product.productName);
				$('#priority').val(product.priority);
				$('#normal-price').val(product.normalPrice);
				$('#promotion-price').val(product.promotionPrice);
				$('#product-desc').val(product.productDesc);
				
				var tempHTML='';
				data.productCategoryList.map(function(item,index){
					tempHTML+='<option data-id="' + item.productCategoryId + '">'
					+ item.productCategoryName + '</option>';
				});
				$('#category').html(tempHTML);
				$("#category option[data-id='"+product.productCategory.productCategoryId+"']").attr('selected','selected');
			}
		});
	}

	function getProductinitInfo() {
		$.getJSON(productCategoryinfoUrl, function(data) {
			if (data.success) {
				var tempHTML = "";
				data.productcategorylist.map(function(item, index) {
					tempHTML += '<option data-id="' + item.productCategoryId
							+ '">' + item.productCategoryName + '</option>';
				});
				$('#category').html(tempHTML);
			}
		});

	}

	$('#submit').click(
			function() {
				var product = {};
				var formData = new FormData();
				var verifyCodeActual = $('#j_captcha').val();
				if (!verifyCodeActual) {
					$.toast('请输入验证码！');
					return;
				}
				formData.append('verifyCodeActual', verifyCodeActual);
				if(isEdit){
					product.productId=productId;
				}
				product.productName = $('#product-name').val();
				product.priority = $('#priority').val();
				product.normalPrice = $('#normal-price').val();
				product.promotionPrice = $('#promotion-price').val();
				product.productDesc = $('#product-desc').val();
				product.productCategory = {
					productCategoryId : $('#category').find('option').not(
							function() {
								return !this.selected;
							}).data('id')
				};
				formData.append('productStr', JSON.stringify(product));

				var thumbnail = $('#small-img')[0].files[0];
				formData.append('thumbnail', thumbnail);
				$('.detail-img').map(
						function(index, item) {
							if ($('.detail-img')[index].files.length > 0) {
								formData.append('productImg' + index,
										$('.detail-img')[index].files[0]);
							}
						});

				$.ajax({
					url : isEdit?modifyprodyctURL:addproductURL,
					type : 'POST',
					data : formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
						if (data.success) {
							$.toast('提交成功');
						} else {
							$.toast('提交失败！' + data.errMsg);
						}
						$('#captcha_img').click();
					}

				});

			});

	$('.detail-img-div').on('change', '.detail-img:last-child', function() {
		if ($('.detail-img').length < 6) {
			$('#detail-img').append('<input type="file" class="detail-img">');
		}
	});

})