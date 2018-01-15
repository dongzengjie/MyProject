$(function() {

	getlist();
	
	function getlist(e) {
		$.ajax({
			url : "/O2O/shopadmin/getshopList",
			type : "GET",
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					handlelist(data.shopList);
					handleUser(data.user);
				}
			}

		});
	}
	function handleUser(data) {
		$('#user-name').text(data.name);
	}

	function handlelist(data) {
		var html = '';
		data.map(function(item, index) {
			html += '<div class="row row-shop">  <div class="col-40">'
					+ item.shopName + '</div><div class="col-40">'
					+ ShopStatus(item.enableStatus)
					+ '</div><div class="col-20">'
					+ goShop(item.enableStatus, item.shopId) + '</div></div>'

		});
		$('.shop-wrap').html(html);
	}
	function goShop(status,id){
		if(status==1){
			return ' <a href="/O2O/shopadmin/shopmanagement?shopId='+id+'">进入</a>';
		}else{
			return '';
		}
		
	}
	
	function ShopStatus(status){
		if(status==0){
			return '审核中';
		}else if(status==-1){
			return '店铺非法';
		}else if(status==1){
			return '审核通过';
		}
		
	}
})