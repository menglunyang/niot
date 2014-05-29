//ztStr = "{status:2,Data:[{codeName:'cpc',probability:12},{codeName:'eCode',probability:88}]}"
oneresult = Raphael('oneFound_SVG',870, 300);

function sendReqCode()
{
	$("#warning_1").css('display','none');
	$("#noFound").css("display","none");
	$("#errorFound").css("display","none");
	$("#oneFound").css("display","none");
	$("#pieChartContainer").css("display","none");
	
	reqCode = $("#reqCode").val();
	
	if (reqCode == "")
	{
		$("#warning_1").css('display','inline-block');
	}
	else
	{
		$.ajax({
			url:'respCode.action',
			cache:false,
			data:{code:reqCode},
			dataType:'json',
			beforeSend:function(){
							oneresult.clear();
							$("#errorFound").css("display","none");
							$("#noFound").css("display","none");
							//$("#oneFound").css("display","none");
							$("#oneFound_SVG").css("display","none");
							$("#pieChartContainer").css("display","none");
							$("#loading_1").css("display","block");
						},
			success:function(result){
							console.log(result);
							$("#loading_1").css("display","none");
							if (result.status == 'error'){
								$("#errorStatement").text(result.statement);
								$("#errorFound").css("display","block");
								//alert("error");
							}
							else if (result.status == 0){
								$("#noFound").css("display","block");
							}
							else if (result.status == 1){
								//$("#oneFoundName").text(result.data);
								//$("#oneFound").css("display","block");
								
								$("#oneFound_SVG").css("display","block");
								var extraData = eval("(" +result.extraData+ ")");
								var fullName = eval("extraData."+result.data+".fullName");
    							var codeNum = eval("extraData."+result.data+".codeNum");
    							
    							console.log(extraData);
    							console.log(fullName);
    							console.log(codeNum);
								
								oneresult.text(380, 10, "您要查询的编码").attr({"font-family":"Microsoft Yahei","font-size":"20px","fill":"#777"});
								oneresult.circle(380, 130, 100).attr({'fill':'#54A3F0','stroke':''});
								oneresult.text(380,130,"100%").attr({'font-family':'Lithos Pro','font-size':'100px','fill':'#FFFEFF'});
								oneresult.text(380,250,"属于"+result.data).attr({'font-family':'Microsoft Yahei','font-size':'20px','fill':'#777'});
								oneresult.text(380,270,fullName).attr({'font-family':'Microsoft Yahei','font-size':'15px','fill':'#777'});
								oneresult.text(380,290,codeNum).attr({'font-family':'Microsoft Yahei','font-size':'15px','fill':'#777'});
							}
							else if (result.status >1){
								var pieData = eval(result.data);
								var totalNum = pieData.length;
								var extraData = eval("(" +result.extraData+ ")");
								for (i=0;i<totalNum;i++)
									{
										pieData[i].probability = Number(pieData[i].probability);
									}
								$("#pieChartContainer").css("display","block");
								drawPieChart(pieData,extraData);
							}
							data1=result;
						},
			error:"alert('1')"
		});
	}
}

function noFound()
{
	$("#loading").css("display","none");
	$("#noFound").css("display","block");
}

var myPalette = ['#FC5944', '#F5A349', '#EAE450', '#84E956', '#00EAD3', '#00C1E5','2693E4','6D4EE2','C649E1','FD4497'];
DevExpress.viz.core.registerPalette('mySuperPalette', myPalette);

function drawPieChart(dataSource,extraData)
{
	$("#loading").css("display","none");
	$("#pieChartContainer").css("display","block");
	$(function () {
    	$("#pieChartContainer").dxPieChart({
        dataSource: dataSource,
        palette: 'mySuperPalette',
        size: {
            height: 320,
            width: 600
        },
        series: {

      //  type:'doughnut',

       /* type:'doughnut',*/
        	

            argumentField: 'codeName',
            valueField: 'probability',
            label: {
                visible: true,
                format: 'percent',
                precision: 1,
                font:{size:3,weight: 700,color:'#777'},
                connector: { visible: true },
            }
        },
        title: {text:'Collision Ratio',
        		position:'leftTop',
                font: {
			                color: 'steelblue',
			                family: 'Microsoft YaHei',
			                opacity: 0.75,
			                size: 18,
			                weight: 400
			            }
        },
        tooltip: { 
        	enabled: true,
        	font:{size:15},
        	customizeText: 
        		function (argumentText) {
    						var fullName = eval("extraData."+argumentText.argument+".fullName");
    						var codeNum = eval("extraData."+argumentText.argument+".codeNum");
    						console.log(fullName);
    						console.log(codeNum);
    						console.log(fullName+"\n"+codeNum);
       						return fullName+"\n"+codeNum;
   						}
        },
      animation: {
            duration: 3000,
            easing: 'linear'
        }
    });
});
}



dataSource = [
    { codeName: 'CPC', probability: 0.6 },
    { codeName: 'uCode', probability: 0.1 },
    { codeName: 'eCode', probability: 0.03 },
    { codeName: 'WC', probability: 0.07 },
    { codeName: 'WFXXF', probability: 0.15 },
    { codeName: 'CNNIC', probability: 0.025 },
    { codeName: 'FFFF', probability: 0.025 }
];

function RFIDInput()
{
	console.log("RFIDInput pressed");
	$("#RFID_loading").attr("src","./images/loading.gif");
	$("#RFID").toggle();
	if($("#RFID").css("display") == "block")
	{
		console.log($("#RFID").css("display"));
		RFIDQuerry();
	}
	else
	{
		console.log($("#RFID").css("display"));
		RFIDInfo.RFIDRequest.abort();
		clearTimeout(RFIDInfo.RFIDTimeout);
		RFIDInfo.querryCount = 1
		
	}
}

var RFIDInfo = new Object();
RFIDInfo.querryCount = 1;
RFIDInfo.RFIDRequest = null;
RFIDInfo.RFIDTimeout = null;

function RFIDQuerry()
{
	RFIDInfo.RFIDRequest = $.ajax({
		url:'RFIDInput.action',
		cache:false,
		data:{InputType:"RFID"},
		dataType:'json',
		beforeSend:function(){
					},
		success:function(result){
						console.log(result.code);
						if (result.code == null && RFIDInfo.querryCount < 6)
							{
								console.log(RFIDInfo.querryCount);
								RFIDInfo.RFIDTimeout = setTimeout("RFIDQuerry()",0);;
								RFIDInfo.querryCount++;
							}
						else if(result.code == null && RFIDInfo.querryCount == 6)
						{
								console.log(RFIDInfo.querryCount);
								//setTimeOut("RFIDQuerry()",5000);;
								$("#RFID_loading").attr("src","./images/warning.png");								
								RFIDInfo.querryCount = 1;
						}
						else
						{
							console.log(RFIDInfo.querryCount);
							$("#RFID_loading").attr("src","./images/checkmark.png");
							$("#reqCode").val(result.code);
							sendReqCode();
							RFIDInfo.querryCount = 1;
							setTimeout("RFIDInput()",3000);
						}
					},
		error:"alert('1')"
	});
}



function switchToContainer1()
{
	$(".container_2")[0].style.display = "none";
	$(".container_3")[0].style.display = "none";
	$(".container_1")[0].style.display = "block";
}

function switchToContainer2()
{
	$(".container_1")[0].style.display = "none";
	$(".container_3")[0].style.display = "none";
	$(".container_2")[0].style.display = "block";
}

function switchToContainer3()
{
	$(".container_1")[0].style.display = "none";
	$(".container_2")[0].style.display = "none";
	$(".container_3")[0].style.display = "block";
}