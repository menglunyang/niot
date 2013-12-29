
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

function updateCurrentRow_2(evt)
{
	
	if (evt.toElement == null) /*firefox*/
	{
		var tempIndex = evt.target.id.indexOf("_")+1;
		var rowNum = evt.target.id[tempIndex];
		pageStatus.currentRowID = "valueRange_"+rowNum;
		//pageStatus.currentRowID = evt.target.parentElement.parentElement.id;
	}
	else /*chrome*/
	{
		var tempIndex = evt.toElement.id.indexOf("_")+1;
		var rowNum = evt.toElement.id[tempIndex];
		pageStatus.currentRowID = "valueRange_"+rowNum;
		
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
		//����ǰ���б�Ҫ�ļ��
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
								
								var extraData = eval("(" +result.extraData+ ")");
								var fullName = eval("extraData."+oneFoundData.codeName+".fullName");
    							var codeNum = eval("extraData."+oneFoundData.codeName+".codeNum");
								
    							console.log(fullName);
    							console.log(codeNum);
    							
								$("#oneFoundName_3").text(fullName+"  ("+codeNum+")");
								$("#oneFoundCollisionRatio_3").text(oneFoundData.CollisionRatio);
								$("#oneFound_3").css("display","block");
							}
							else if (result.status >1){
								console.log(result.data);
								var barData = eval(result.data);
								var extraData = eval("(" +result.extraData+ ")");
								//console.log(extraData);
								
								var totalNum = barData.length;
								for (i=0;i<totalNum;i++)
									{
										barData[i].CollisionRatio = Number(barData[i].CollisionRatio);
									}
								
								$("#barChartContainer").css("display","block");
								drawBarChart(barData, extraData);
							}
							data1=result;
						},
			error:"alert('1')"
		});	
	}
}

function drawBarChart(dataSource, extraData)
{
	$("#barChartContainer").dxChart({
    dataSource: dataSource,
    size: {
            height: 250,
            width: 700
        },
    equalBarWidth: {
        	width:40,
    },
    commonSeriesSettings: {
        argumentField: "codeName",
        type: "bar",
        hoverMode: "allArgumentPoints",
        selectionMode: "allArgumentPoints",
        label: {
            visible: true,
			argumentPrecision: 2,
            format: 'percent',
        },
        bar: {
                cornerRadius:3,
                width:5,
        },
        
    },
    series: [
        { valueField: "CollisionRatio", name: "Collision Ratio",color: '#e66450' }
    ],
    title: "Collision Ratio",
    legend: {
        verticalAlignment: "top",
        horizontalAlignment: "right"
	    },
	    pointClick: function (point) {
	        this.select();
	    },
	    
    valueAxis: {
      //  axisDivisionFactor: 15,
        visible: true,
        label: {
            format: 'percent',
            precision: 2
        }
    },
	tooltip: { 
    	enabled: true,
    	font:{size:15},
		customizeText: function (argumentText) {
    						var fullName = eval("extraData."+argumentText.argument+".fullName");
    						var codeNum = eval("extraData."+argumentText.argument+".codeNum");
       						return fullName+"\n"+codeNum;
   						}
     },
	    
	});
}
