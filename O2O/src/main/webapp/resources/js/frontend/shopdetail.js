$(function() {
	var loading = false;
	var shopId = getQueryString('shopId');
	var listshopdetailpageinfoURL = '/O2O/frontend/listshopdetailpageinfo?shopId='
			+ shopId;
	var listproductsbyshopURL = '/O2O/frontend/listproductsbyshop';
	var maxItems = 20;
	var pageSize = 10;
	var pageNum = 1;
	var productCategoryId = '';
	var productName = '';
	
	getlistshopdetailpageinfoURL();
	additems(pageSize,pageNum);
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		additems(pageSize, pageNum);
	});
	function additems(pageSize, pageIndex) {
		var url = listproductsbyshopURL + '?' + 'pageIndex=' + pageIndex
				+ '&pageSize=' + pageSize + '&shopId=' + shopId
				+ '&productCategoryId=' + productCategoryId + '&productName='
				+ productName;
		loading = true;
		$.getJSON(url, function(data) {
			if (data.success) {
				var productList = data.productList;
				maxItems = data.count;
				var html = '';
				productList.map(function(item, index) {
					html += '' + '<div class="card" data-product-id='
							+ item.productId + '>'
							+ '<div class="card-header">' + item.productName
							+ '</div>' + '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ item.imgAddr + '" width="44">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.productDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
							+ '更新</p>' + '<span>点击查看</span>' + '</div>'
							+ '</div>';
				});
				$('.list-div').append(html);
				var total = $('.list-div .card').length;
				if (total >= maxItems) {

					$('.infinite-scroll-preloader').hide();
				} else {
					$('.infinite-scroll-preloader').show();
				}
				pageNum += 1;
				loading = false;
				$.refreshScroller();
			}

		});
	}

	function getlistshopdetailpageinfoURL() {
		var url = listshopdetailpageinfoURL
		$.getJSON(url, function(data) {
			var shop = data.shop;
			$('#shop-cover-pic').attr('src', shop.shopImg);
			$('#shop-update-time').html(
					new Date(shop.lastEditTime).Format("yyyy-MM-dd"));
			$('#shop-name').html("店铺名称: " + shop.shopName);
			$('#shop-desc').html("店铺简介: " + shop.shopDesc);
			$('#shop-addr').html("店铺地址: " + shop.shopAddr);
			$('#shop-phone').html("联系电话: " + shop.phone);

			var productCategoryList = data.productCategoryList;

			var html =  '<a href="#" class="button" data-product-search-id="">全部类别 </a>';
				
			productCategoryList.map(function(item, index) {
				html += '<a href="#" class="button" data-product-search-id='
						+ item.productCategoryId + '>'
						+ item.productCategoryName + '</a>';
			});
			$("#shopdetail-button-div").html(html);
		});
	}
	$('.list-div').on('click','.card',function(e){
		var productId=e.currentTarget.dataset.productId;
		window.location.href = '/O2O/frontend/productdetail?productId='
			+ productId;
	});
	$('#search').on('change',function(e){
		productName=e.target.value;
		$('.list-div').empty();
		pageNum=1;
		additems(pageSize, pageNum);
		
	});
	$('#shopdetail-button-div').on(
			'click',
			'.button',
			function(e) {
				productCategoryId = e.target.dataset.productSearchId;
				if (productCategoryId) {
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						productCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					additems(pageSize, pageNum);
				}else{
					if ($(e.target).hasClass('button-fill')) {
						$(e.target).removeClass('button-fill');
						productCategoryId = '';
					} else {
						$(e.target).addClass('button-fill').siblings()
								.removeClass('button-fill');
					}
					$('.list-div').empty();
					pageNum = 1;
					additems(pageSize, pageNum);
				}
					
			});
	
	$.init();
});