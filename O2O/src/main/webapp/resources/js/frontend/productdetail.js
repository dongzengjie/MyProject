$(function() {
	var loading = false;
	var productId = getQueryString('productId');
	var url = '/O2O/frontend/getproductdetail?productId=' + productId;

	
			$.getJSON(
					url,
					function(data) {
						if (data.success) {
							var product = data.product;
							$('#product-img').attr('src', product.imgAddr);
							$('#product-time').text(
									new Date(product.lastEditTime)
											.Format("yyyy-MM-dd"));
							$('#product-name').text(product.productName);
							$('#product-desc').text(product.productDesc);
							var imgListHtml = '';
							product.productImgList.map(function(item, index) {
								imgListHtml += '<div> <img src="'
										+ item.imgAddr + '"/></div>';
							});
							
							
							if(product.normalPrice != undefined && product.promotionPrice != undefined){
								$('#price').show();
								$('#normalPrice').html('<del>'+'￥'+product.normalPrice+'</del>');
								$('#promotionPrice').text('￥'+product.promotionPrice);
							}else if(product.normalPrice != undefined && product.promotionPrice == undefined){
								$('#price').show();
								$('#normalPrice').html('<del>'+'￥'+product.normalPrice+'</del>');
							}else if(product.normalPrice == undefined && product.promotionPrice != undefined){
								$('#price').show();
								$('#promotionPrice').text('￥'+product.promotionPrice);
							}
							
							
							
							// 生成购买商品的二维码供商家扫描
							/*imgListHtml += '<div> <img src="/myo2o/frontend/generateqrcode4product?productId='
									+ product.productId + '"/></div>';*/
							$('.list-div').html(imgListHtml);
						}
					});
	$.init();
});