$(function() {
	var shopId = getQueryString("shopId");
	var isEdit = shopId ? true : false;
	var initUrl = '/O2O/shopadmin/getshopinitinfo';
	var registshop = '/O2O/shopadmin/registshop';
	var shopInfoUrl = '/O2O/shopadmin/getshopbyid?shopId=' + shopId;
	var editshopUrl = '/O2O/shopadmin/modifyshop';



	if(isEdit){
		$('.titlename').html("店铺信息修改");
		$('.title').html("店铺信息修改");
		getshopInfo(shopId);
	}else{
		$('.title').html("店铺注册");
		$('.titlename').html("店铺注册");
		getShopInitInfo();
	}
	
	function getshopInfo(shopId) {
		$.getJSON(shopInfoUrl, function(data) {
			if (data.success) {
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				
				var shopCategory= '<option data-id="' + shop.shopCategory.shopCategoryId+ '">' +shop.shopCategory.shopCategoryName + '</option>';
				var tempAreaHtml='';
				data.areaList.map(function(item,index){
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
					+ item.areaName + '</option>';
					
				});

				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled','disabled')
				$('#shop-area').html(tempAreaHtml);
				$("#shop-area option[data-id='"+shop.area.areaId+"']").attr('selected','selected');
			}

		});

	}

	function getShopInitInfo() {
		$.getJSON(initUrl, function(data) {
			if (data.success) {
				var tempHtml = '';
				var tempAreaHtml = '';
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#shop-area').html(tempAreaHtml);
			}
		});
		
		/*$.ajax({
			url :initUrl,
			type:'GET',
			data:'json',
			success :function(data){
				if(data.success){
					var tempHtml = '';
					var tempAreaHtml = '';
					data.shopCategoryList.map(function(item, index) {
						tempHtml += '<option data-id="' + item.shopCategoryId
								+ '">' + item.shopCategoryName + '</option>';
					});
					data.areaList.map(function(item, index) {
						tempAreaHtml += '<option data-id="' + item.areaId + '">'
								+ item.areaName + '</option>';
					});
					$('#shop-category').html(tempHtml);
					$('#shop-area').html(tempAreaHtml);
				}
			}
	
		});*/
	}

	$('#submit').click(function() {
		var shop = {};
		if(isEdit){
			shop.shopId=shopId;
		}
		shop.shopName = $('#shop-name').val();
		shop.shopAddr = $('#shop-addr').val();
		shop.phone = $('#shop-phone').val();
		shop.shopDesc = $('#shop-desc').val();

		shop.shopCategory = {
			shopCategoryId : $('#shop-category').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};

		shop.area = {
			areaId : $('#shop-area').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		var shopImg = $('#shop-img')[0].files[0];
		var formData = new FormData();
		formData.append("shopImg", shopImg);
		formData.append('shopStr', JSON.stringify(shop));
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append('verifyCodeActual', verifyCodeActual);
		$.ajax({
			url :isEdit?editshopUrl: registshop,
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

})