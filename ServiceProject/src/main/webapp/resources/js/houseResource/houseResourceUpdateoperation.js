$(function() {
	var houseResourceId = getQueryString("houseResourceId");
	var getHousereSourceById = '/ServiceProject/houseresourceadmin/getHouseResourceInfoById?houseResourceId='
			+ houseResourceId;
	var modifyUrl='/ServiceProject/houseresourceadmin/modifyHouseResource';
	getHouseResourceInfo(houseResourceId);

	function getHouseResourceInfo(houseResourceId) {
		$.getJSON(getHousereSourceById, function(data) {
			if (data.success) {
				var houseResource = data.houseResource;
				$('#houseResource-name').val(houseResource.houseResourceName);
				$('#city-picker').val(houseResource.areaMsg);
				$('#houseResource-addr').val(houseResource.houseResourceAddr);
				$('#houseResource-phone').val(houseResource.phone);
				$('#houseResource-desc').val(houseResource.houseResourceDesc);
			}
		});
	}
	
	  $('#submit').click(function(){
			 var houseResource={};
			 houseResource.houseResourceId=houseResourceId;
			 houseResource.houseResourceName=$('#houseResource-name').val();
			 houseResource.areaMsg=$('#city-picker').val();
			 houseResource.houseResourceAddr=$('#houseResource-addr').val();
			 houseResource.phone=$('#houseResource-phone').val();
			 houseResource.houseResourceDesc=$('#houseResource-desc').val();
			 
			 var houseResourceImg= $('#houseResource-img')[0].files[0];
			 var formData = new FormData();
			 formData.append("houseResourceImg",houseResourceImg);
			 formData.append('houseResourceStr', JSON.stringify(houseResource));
			 var verifyCodeActual = $('#j_captcha').val();
			 if (!verifyCodeActual) {
					$.toast('请输入验证码！');
					return;
				}
			 formData.append('verifyCodeActual', verifyCodeActual);
			 $.ajax({
				 url:modifyUrl,
				 type : 'POST',
				 data : formData,
				 contentType : false,
				 processData : false,
				 cache : false,
				 success : function(data) {
						if (data.success) {
							$.toast('提交成功');
							window.location.href= '/ServiceProject/houseresourceadmin/houseresourcelist';
						} else {
							$.toast('提交失败！' + data.errMsg);
						}
						$('#captcha_img').click();
					}
			 });
			 
			 
			 
		  });

	$("#city-picker")
			.cityPicker(
					{
						toolbarTemplate : '<header class="bar bar-nav">\
		    <button class="button button-link pull-right close-picker">确定</button>\
		    <h1 class="title">选择地址</h1>\
		    </header>'
					});

});