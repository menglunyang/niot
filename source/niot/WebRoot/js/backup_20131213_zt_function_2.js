//ztStr = "{status:2,Data:[{codeName:'cpc',probability:12},{codeName:'eCode',probability:88}]}"

pageStatus = new Object();
pageStatus.rowNum = 3;
pageStatus.currentRowID = ""; 

rulDesign = new Object();
 

function updateCurrentRow(evt)
{
	pageStatus.currentRowID = evt.toElement.id;
}

function insertToRow(evt)
{
	var tagContent = evt.toElement.textContent;
	var inputContent = $("#"+pageStatus.currentRowID).val();
	$("#"+pageStatus.currentRowID).val(inputContent+tagContent+", ");
}

function sendReqRul()
{
	rulDesign.len = $("#length").val();
	rulDesign.valueRange = "";
	for (var i =1; i<=pageStatus.rowNum; i++ )
		{
			if ($("#"+"startIndex_"+i).val() != "")
			{
				var startIndex = $("#"+"startIndex_"+i).val();
				var endIndex = $("#"+"endIndex_"+i).val();
				var valueRange = $("#"+"valueRange_"+i).val();
				rulDesign.valueRange = rulDesign.valueRange+startIndex + "-" + endIndex + ":"+ valueRange+";";
			}
		}
	if ($("#rothers").val() != "")
		{
			rulDesign.valueRange = rulDesign.valueRange+"others:"+$("#rothers").val();
		}
	
	if (1!=1){
		//发送前进行必要的检测
	}
	else{
		$.ajax({
			url:'respRul.action',
			cache:false,
			data:rulDesign,
			dataType:'json',
			beforeSend:function(){
							$("#loading_3").css("display","block");
						},
			success:function(result){
							$("#loading_3").css("display","none");
							if (result.status == 'error'){
								$("#errorStatement_3").text(result.statement);
								$("#errorFound_3").css("display","block");
								//alert("error");							
								}
							else if (result.status == 0){
								$("#noFound_3").css("display","block");
							}
							else if (result.status == 1){
								console.log("(" +result.data+ ")");
								var oneFoundData = eval("(" +result.data+ ")");
								$("#oneFoundName_3").text(oneFoundData.codeName);
								$("#oneFoundCollisionRatio_3").text(oneFoundData.CollisionRatio);
								$("#oneFound_3").css("display","block");
							}
							else if (result.status >1){
								console.log(result.data);
								var barData = eval(result.data);
								$("#barChartContainer").css("display","block");
								drawBarChart(barData);
							}
							data1=result;
						},
			error:"alert('1')"
		});	
	}
}

function drawBarChart(dataSource)
{
	$("#barChartContainer").dxChart({
    dataSource: dataSource,
    size: {
            height: 250,
            width: 700
        },
    commonSeriesSettings: {
        argumentField: "codeName",
        type: "bar",
        hoverMode: "allArgumentPoints",
        selectionMode: "allArgumentPoints",
        label: {
            visible: true,
			format: 'percent',
            precision: 1,
        },
    },
    series: [
        { valueField: "CollisionRatio", name: "Collision Ratio",color: 'forestgreen' }
    ],
    title: "Collision Ratio",
    legend: {
        verticalAlignment: "top",
        horizontalAlignment: "right"
	    },
	    pointClick: function (point) {
	        this.select();
	    }
	});
}