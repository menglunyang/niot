//ztStr = "{status:2,Data:[{codeName:'cpc',probability:12},{codeName:'eCode',probability:88}]}"
oneresult = Raphael('oneFound_SVG',870, 300);

function sendReqCode()
{
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
								
								oneresult.text(150, 140, "您查询的编码").attr({'font-family':'微软雅黑','font-size':'20px','fill':'#777'});
								oneresult.circle(380, 140, 140).attr({'fill':'#54A3F0','stroke':''});
								oneresult.text(380,140,"100%").attr({'font-family':'Lithos Pro','font-size':'130px','fill':'#FFFEFF'});
								oneresult.text(580,140,"属于"+result.data).attr({'font-family':'微软雅黑','font-size':'20px','fill':'#777'});
							}
							else if (result.status >1){
								var pieData = eval(result.data);
								var totalNum = pieData.length;
								for (i=0;i<totalNum;i++)
									{
										pieData[i].probability = Number(pieData[i].probability);
									}
								$("#pieChartContainer").css("display","block");
								drawPieChart(pieData);
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

function drawPieChart(dataSource)
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
        tooltip: { enabled: true,
        customizeText: function (argumentText) {
       		return argumentText.argument;
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