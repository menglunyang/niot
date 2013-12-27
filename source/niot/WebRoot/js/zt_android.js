oneresult = Raphael('oneFound_SVG',700, 700);	
$.ajax({
			url:'respCode.action',
			cache:false,
			data:{code:111},
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
								
								oneresult.text(350, 50, "您查询的编码").attr({'font-family':'微软雅黑','font-size':'80px','fill':'#777'});
								oneresult.circle(350, 300, 180).attr({'fill':'#54A3F0','stroke':''});
								oneresult.text(350,300,"100%").attr({'font-family':'Lithos Pro','font-size':'180px','fill':'#FFFEFF'});
								oneresult.text(350,550,"属于"+result.data).attr({'font-family':'微软雅黑','font-size':'30px','fill':'#777'});
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



function drawPieChart(dataSource)
{
	$("#loading").css("display","none");
	$("#pieChartContainer").css("display","block");
	$(function () {
    	$("#pieChartContainer").dxPieChart({
        dataSource: dataSource,
        palette: 'mySuperPalette',
        size: {
            height: 700,
            width: 700
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
        		position:'top',
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
        },
        
       legend: {
            horizontalAlignment: 'center',
            verticalAlignment: 'top'
        }
    });
});
}