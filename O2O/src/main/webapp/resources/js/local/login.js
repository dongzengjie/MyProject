$(function(){
	var usertype=getQueryString('usertype');
	var loginurl='/O2O/local/checklogin';
	var loginCount=0;
	$('#bind').click(function(){
		
		var userName= $('#userName').val();
		var password= $('#password').val();
		var j_captcha=  $('#j_captcha').val();
		var needVerify=false;
		if(loginCount>=3){
			if(!j_captcha){
				$.toast("请输入验证码");
				return;
			}else {
				needVerify=true;
			}
		}
		
		
		$.ajax({
			url:loginurl,
			type:'post',
			data:{
				needVerify:needVerify,
				verifyCodeActual:j_captcha,
				userName:userName,
				password:password
			},
			success:function(data){
				if(data.success){
					$.toast("登陆成功");
					if(usertype==1){
						window.location.href='/O2O/frontend/index';
					}else if(usertype==2){
						window.location.href='/O2O/shopadmin/shoplist';
					}
				}else {
					$.toast('登陆失败'+data.errorMsg);
					$('#captcha_img').click();
					loginCount++;
					if(loginCount>=3){
						$('#verifyPart').show();
					}
					
				}
			}
		});
	});
	
});