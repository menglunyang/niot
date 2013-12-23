
    $(document).ready(function() {
        $("#valueRange_1").tagit();
        $("#valueRange_2").tagit();
        $("#valueRange_3").tagit();
        $("#rothers").tagit();
    });
    
	$("#valueRange_1").tagit({
	    availableTags: ["0-9", "a-z", "A-Z", "0-5", "3-8", "f-m", "x-z","N-Q"],
	});
	
	$("#valueRange_2").tagit({
	    availableTags: ["0-9", "a-z", "A-Z", "0-5", "3-8", "f-m", "x-z","N-Q"],
	});
	
	$("#valueRange_3").tagit({
	    availableTags: ["0-9", "a-z", "A-Z", "0-5", "3-8", "f-m", "x-z","N-Q"],
	});
	
	$("#rothers").tagit({
	    availableTags: ["0-9", "a-z", "A-Z", "0-5", "3-8", "f-m", "x-z","N-Q"],
	});
	
	
//ztStr = "{status:2,Data:[{codeName:'cpc',probability:12},{codeName:'eCode',probability:88}]}"

pageStatus = new Object();
pageStatus.rowNum = 3;
pageStatus.currentRowID = ""; 

rulDesign = new Object();
 

function updateCurrentRow(evt)
{
	
	if (evt.toElement == null) /*firefox*/
	{
		pageStatus.currentRowID = evt.target.parentElement.parentElement.id;
	}
	else /*chrome*/
	{
		pageStatus.currentRowID = evt.toElement.parentElement.parentElement.id;
		
	}

}

function insertToRow(evt)
{
	if (evt.toElement == null) /*firefox*/
	{
		var tagContent = evt.target.textContent;
		$("#"+pageStatus.currentRowID).tagit("createTag", tagContent);
	}
	else /*chrome*/
	{	
		var tagContent = evt.toElement.textContent;
		$("#"+pageStatus.currentRowID).tagit("createTag", tagContent);
	}
/*	var tagContent = evt.toElement.textContent;
	$("#"+pageStatus.currentRowID).tagit("createTag", tagContent);*/
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
				//var valueRange = $("#"+"valueRange_"+i).val();
				var valueRange = $("#"+"valueRange_"+i).tagit("assignedTags");
				rulDesign.valueRange = rulDesign.valueRange+startIndex + "-" + endIndex + ":"+ valueRange+";";
				//console.log(rulDesign.valueRange);
			}
		}
	if ($("#rothers").tagit("assignedTags") != "")
		{
			rulDesign.valueRange = rulDesign.valueRange+"others:"+$("#rothers").tagit("assignedTags");
		}
	
	if (1!=1){
		//发送前进行必要的检测
	}
	else{
		console.log(rulDesign.valueRange);
		$.ajax({
			url:'respRul.action',
			cache:false,
			data:rulDesign,
			dataType:'json',
			beforeSend:function(){
							$("#barChartContainer").css("display","none");
							$("#noFound_3").css("display","none");
							$("#errorFound_3").css("display","none");
							$("#oneFound_3").css("display","none");
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
