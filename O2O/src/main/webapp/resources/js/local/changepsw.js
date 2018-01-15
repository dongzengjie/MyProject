$(function(){
	var changeurl='/O2O/local/changepassword';
	var usertype=getQueryString('usertype');
	
	$('#bind').click(function(){
		var userName= $('#userName').val();
		var password= $('#password').val();
		var j_captcha=  $('#j_captcha').val();
		var newpassword=$('#newpassword').val();
		var configpassword=$('#configpassword').val();
		if(newpassword !=configpassword){
			$.toast('两次输入的密码不一致');
			return;
		}
		$.ajax({
			url:changeurl,
			type:'post',
			data:{
			
				verifyCodeActual:j_captcha,
				userName:userName,
				password:password,
				newpassword:newpassword
				
			},
			success:function(data){
				if(data.success){
					$.toast('修改成功');
					if(usertype==1){
						window.location.href='/O2O/frontend/index';
					}else if(usertype==2){
						window.location.href='/O2O/shopadmin/shoplist';
					}
				}else {
					$.toast('修改失败'+data.errorMsg);
				}
			}
		});
		
		
	});
		
	
});