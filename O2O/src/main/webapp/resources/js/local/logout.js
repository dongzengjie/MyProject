$(function(){
	$('#log-out').click(function(){
		$.ajax({
			url:'/O2O/local/logout',			
			type:'post',
			success:function(data){
				if(data.success){
					var usertype=$('#log-out').attr('usertype');
					window.location.href='/O2O/local/login?usertype='+usertype;
					
				}
			}
			
		});
	});
	
});