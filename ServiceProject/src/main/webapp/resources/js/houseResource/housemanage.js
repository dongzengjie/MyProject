$(function() {
	var gethouselist = '/ServiceProject/houseresourceadmin/gethouselistbyhouseresourceid?pageIndex=0&pageSize=999';
	var deleteUrl='/ServiceProject/houseresourceadmin/modifyhouse';
	function getList() {
		$.getJSON(gethouselist, function(data) {
			if (data.success) {
				var houseList = data.houseList;
				var tempHtml = '';
				houseList.map(function(item, index) {
					var textOp = "上架";
					var contraryStatus = 0;
					if (item.enableStatus == 0) {
						var textOp = "下架";
						var contraryStatus = 1;
					} else {
						var contraryStatus = 0;
					}

					tempHtml += '' + '<div class="row row-house">'
							+ '<div class="col-33">'
							+ item.houseName
							+ '</div>'
							+ '<div class="col-33">'
							+ item.priority
							+ '</div>'
							+ '<div class="col-33">'
							+ '<a href="#" class="edit" data-id="'
							+ item.houseId
							+ '" data-status="'
							+ item.enableStatus
							+ '">编辑</a>'
							+ '<a href="#" class="delete" data-id="'
							+ item.houseId
							+ '" data-status="'
							+ contraryStatus
							+ '">'
							+ textOp
							+ '</a>'
							+ '<a href="#" class="preview" data-id="'
							+ item.houseId
							+ '" data-status="'
							+ item.enableStatus
							+ '">预览</a>'
							+ '</div>'
							+ '</div>';
				});
				$('.house-wrap').html(tempHtml);
			}
			;
		});
	}
	;

	getList();

	function deleteItem(id, enableStatus) {
		var house = {};
		house.houseId = id;
		house.enableStatus = enableStatus;
		$.confirm('确定要修改吗?', function() {
			$.ajax({
				url : deleteUrl,
				type : 'POST',
				data : {
					houseStr : JSON.stringify(house),
					statusChange : true
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						$.toast('操作成功！');
						getList();
					} else {
						$.toast('操作失败！');
					}
				}
			});
		});
	}
	$('.house-wrap')
	.on(
			'click',
			'a',
			function(e) {
				var target = $(e.currentTarget);
				if (target.hasClass('edit')) {
					window.location.href = '/ServiceProject/houseresourceadmin/tohouseedit?houseId='
							+ e.currentTarget.dataset.id;
				} else if (target.hasClass('delete')) {
					deleteItem(e.currentTarget.dataset.id,
							e.currentTarget.dataset.status);
				} else if (target.hasClass('preview')) {
					//window.location.href = '/myo2o/frontend/productdetail?productId='
							+ e.currentTarget.dataset.id;
				}
			});
	
	$('#new').click(function() {
		window.location.href = '/ServiceProject/houseresourceadmin/tohouseadd';
	});
});