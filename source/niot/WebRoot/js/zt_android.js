oneresult = Raphael('oneFound_SVG',870, 300);	
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
								
								oneresult.text(380, 20, "您查询的编码").attr({'font-family':'微软雅黑','font-size':'20px','fill':'#777'});
								oneresult.circle(380, 140, 100).attr({'fill':'#54A3F0','stroke':''});
								oneresult.text(380,140,"100%").attr({'font-family':'Lithos Pro','font-size':'100px','fill':'#FFFEFF'});
								oneresult.text(380,260,"属于"+result.data).attr({'font-family':'微软雅黑','font-size':'20px','fill':'#777'});
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