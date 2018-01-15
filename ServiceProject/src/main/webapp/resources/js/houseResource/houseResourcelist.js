$(function() {

	getlist();
	
	
	function getlist(e){
		$.ajax({
			url:"/ServiceProject/houseresourceadmin/getHouseResourceList",
			type:"GET",
			
			success:function(data){
				if(data.success){
					handleUser(data.user);
					handlelist(data.HouseResourceList);
				}
			}
		});
	}
	
	
	function handleUser(data){
		$('#user-name').text(data.name);
	}
	
	function handlelist(data){
		var html='';
		data.map(function(item,index){
			html += '<div class="row row-houseResoure">  <div class="col-40">'
				+ item.houseResourceName + '</div><div class="col-40">'
				+ houseResoureStatus(item.enableStatus)
				+ '</div><div class="col-20">'
				+ gohouseResoure(item.enableStatus, item.houseResourceId) + '</div></div>'
		});
		$('.houseResource-wrap').html(html);
	}
	
	function houseResoureStatus(status){
		if(status==0){
			return '审核中';
		}else if(status==-1){
			return '房源非法';
		}else if(status==1){
			return '审核通过';
		}
	}
	
	function gohouseResoure(status,id){
		if(status==1){
			return ' <a href="/ServiceProject/houseresourceadmin/toHouseResourceManage?houseResourceId='+id+'">进入</a>';
		}else{
			return '';
		}
		
	}
	
});