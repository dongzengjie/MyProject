$(function(){
	var houseId = getQueryString('houseId');
	
	var modifyUrl='/ServiceProject/houseresourceadmin/modifyhouse';
	var housecategoryinfo='/ServiceProject/houseresourceadmin/gethousebyid?houseId='+houseId;
	
	gethouseinfo();
	
	function gethouseinfo(houseId){
		$.getJSON(housecategoryinfo,function(data){
			if(data.success){
				var house=data.house;
				
				$('#house-name').val(house.houseName);
				$('#priority').val(house.priority);
				$('#normal-price').val(house.price);
				$('#house-desc').val(house.houseDesc);
				var tempHTML='';
				data.houseCategoryList.map(function(item,index){
					tempHTML+='<option data-id="' + item.houseCategoryId + '">'
					+ item.houseCategoryName + '</option>';
				});
				
				$('#category').html(tempHTML);
				$("#category option[data-id='"+house.houseCategory.houseCategoryId+"']").attr('selected','selected');
			}
		});
	}
	
	$('#submit').click(function(){
		var house={};
		var formData = new FormData();
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append('verifyCodeActual', verifyCodeActual);
		house.houseName=$('#house-name').val();
		house.priority=$('#priority').val();
		house.price=$('#normal-price').val();
		house.houseDesc=$('#house-desc').val();
		house.houseId=houseId;
		house.houseCategory={
				houseCategoryId : $('#category').find('option').not(
						function() {
							return !this.selected;
						}).data('id')
		};
		formData.append('houseStr', JSON.stringify(house));
		var houseimg=$('#small-img')[0].files[0];
		formData.append('houseimg', houseimg);
		$('.detail-img').map(
				function(index, item) {
					if ($('.detail-img')[index].files.length > 0) {
						formData.append('houseListImg' + index,
								$('.detail-img')[index].files[0]);
					}
				});
		
		$.ajax({
			url:modifyUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success: function(data){
				if (data.success) {
					$.toast('提交成功');
					window.location.href="/ServiceProject/houseresourceadmin/tohouselist";
					
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
});