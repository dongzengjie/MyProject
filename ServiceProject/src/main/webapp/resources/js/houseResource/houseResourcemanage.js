$(function(){
	var houseResourceId = getQueryString("houseResourceId");
	var houseResourceUrl='/ServiceProject/houseresourceadmin/getHouseManageInfo?houseResourceId='+houseResourceId;
	$.getJSON(houseResourceUrl,function(data){
		if(data.redirect){
			window.location.href=data.url;
		}else{
			if(data.houseResourceId !=undefined && data.houseResourceId !=null){
				houseResourceId=data.houseResourceId;
			}
			$('#houseResourceInfo').attr('href','/ServiceProject/houseresourceadmin/toupdateHouseResource?houseResourceId='+houseResourceId);
		}
	});
		
	
});