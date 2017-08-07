function order() {
	data = {};
	$(".orderCount").each(function(index, el) {
		data[el.id]=el.value;
	});
	$.ajax({
		url:"/order",
		type:'POST',
		contentType:"application/json; charset=utf-8",
		dataType:"json",
		data:JSON.stringify(data),
		success:function(message) {
            if(message.success){
            	$("#ajaxResult").hide();
            	var orders = message.orders;
				for (var i = 0; i < orders.length; i++) {
					var tr = "<tr><td>"+orders[i].id+"</td><td>"+orders[i].productName+"</td><td>"+orders[i].count+"</td></tr>";
					$("#orders").append(tr);
					$("#"+orders[i].productId).val("");
				}
            	var productAmmount = message.productAmount;
            	for (var k in productAmmount ) {
            		$("#amount_"+k).html(productAmmount[k])
            	}
            }else{
            	$("#ajaxResult").html(message.errMsg);
                $("#ajaxResult").show();
            }
        },
        error:function(message) {
            $("#ajaxResult").html("Submit Failed!");
            $("#ajaxResult").show();
        }
	});
}