$(function(){
	var usertype=getQueryString('usertype');
	var bindurl='/O2O/local/bindlocalauth';

	$('#bind').click(function(){
		
		var userName= $('#userName').val();
		var password= $('#password').val();
		var j_captcha=  $('#j_captcha').val();
		if(!j_captcha){
			$.toast("请输入验证码");
			return;
		}
		
		$.ajax({
			url:bindurl,
			type:"POST",
			
			async:false,
			cache:false,
			data:{
				userName:userName,
				password:password,
				verifyCodeActual:j_captcha
			},
			success:function(data){
				if(data.success){
					$.toast('绑定成功');
					if(usertype==1){
						window.location.href='/O2O/frontend/index';
					}else if(usertype==2){
						window.location.href='/O2O/shopadmin/shoplist';
					}
				}else {
					
					$.toast('提交失败' + data.errorMsg);				
					$('#captcha_img').click();
				}
				
			}
		});
	});
	
});