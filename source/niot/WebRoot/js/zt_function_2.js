//drawTwoCircles(15,4,2,'街巷名/小区名编码','ecode','命名空间：15万','命名空间：4万','命名空间：2万')
//drawTwoCircles2(IDDataSource.code1.radius,IDDataSource.code2.radius,'cpc','ecode',IDDataSource.code1.amount,IDDataSource.code2.amount,'123')
IDDataSource = 
{
	CPC:
	{
		amount:"10^256",
		radius:120,
	},
	
	CID:
	{
		amount:"10^236",
		radius:115,
	},
	
	EPC:
	{
		amount:"10^153",
		radius:100,
	},
	
	Ecode:
	{
		amount:"10^77",
		radius:95,
	},
	
	Ucode:
	{
		amount:"10^38",
		radius:50,
	},
	
	DESC:
	{
		amount:"10^20",
		radius:45,
	},
	
	EAN_13:
	{
		amount:"10^12",
		radius:40,
	},

	Street:
	{
		amount:"10^9",
		radius:35,
	},

};

codeInputStatus = new Object;
codeInputStatus.currentRowID = ""; 

    $(document).ready(function() {
        $("#codeName_1").tagit();
        $("#codeName_2").tagit();

    });
    
	$("#codeName_1").tagit({
	    /*availableTags: ["0-9", "a-z", "A-Z", "0-5", "3-8", "f-m", "x-z","N-Q"],*/
	    tagLimit:1,
	    readOnly:false,
	});
	
	$("#codeName_2").tagit({
	   /* availableTags: ["0-9", "a-z", "A-Z", "0-5", "3-8", "f-m", "x-z","N-Q"],*/
	    tagLimit:1,
	    readOnly:false,
	});
	
	var twoCirclePaper = Raphael('circlesContainer',800, 345);
	
	
	function drawTwoCircles(AreaA, AreaB, AreaC,NameA, NameB, StateA, StateB, StateC)
	{
		twoCirclePaper.clear();
		
		var baseBigCilcleR = 120;
		
		var areaA = Number(AreaA);
		var areaB = Number(AreaB);
		var areaC = Number(AreaC);
		
		var rA = Math.pow(areaA,0.5);
		var rB = Math.pow(areaB,0.5);
		var rC = Math.pow(areaC,0.5)/2;
		
		if(rA<rB)
		{
			var temp = rB;
			rB = rA;
			rA = rB;
		}
		
		var distanceAB = baseBigCilcleR + baseBigCilcleR * rB / rA - 2 * baseBigCilcleR * rC / rA
		
		var orgAX = 400 - distanceAB / 2;
		var orgBX = 400 + distanceAB / 2;
		
		var baseSmallCilcleR = 150 * rB / rA;
		
		var anim1 = Raphael.animation({r: baseBigCilcleR, opacity: 1, fill:'#E5E350',stroke:''},800);
		var anim2 = Raphael.animation({r: baseSmallCilcleR, opacity: 0.8, fill:'#E76450',stroke:''},800);
		
		//draw two circles 
		twoCirclePaper.circle(orgAX, 180, 0).animate(anim1);
		twoCirclePaper.circle(orgBX, 180, 0).animate(anim2);
		
		 var anim3 = Raphael.animation({'font-size':'15px'},800);
		 
		 //write explaination for circles
		 twoCirclePaper.text(orgAX-1.4*baseBigCilcleR, 160, NameA+"\n"+StateA).attr({'font-family':'微软雅黑','font-size':'0px','fill':'#777'}).animate(anim3);
		 twoCirclePaper.text(orgBX+1.6*baseSmallCilcleR, 160, NameB+"\n"+StateB).attr({'font-family':'微软雅黑','font-size':'0px','fill':'#777'}).animate(anim3);
		 twoCirclePaper.text(orgAX+baseBigCilcleR, 80, "重叠区"+"\n"+StateC).attr({'font-family':'微软雅黑','font-size':'0px','fill':'#777'}).animate(anim3);
	}
	
	function drawTwoCircles2(RA, RB, NameA, NameB, StateA, StateB,StateC)
	{
		twoCirclePaper.clear();
		
		var rA = Number(RA);
		var rB = Number(RB);
		
		var orgAX = 380 - rA;
		var orgBX = 420 + rB;
		
		var anim1 = Raphael.animation({r: rA, opacity: 1, fill:'#E5E350',stroke:''},800);
		var anim2 = Raphael.animation({r: rB, opacity: 1, fill:'#E76450',stroke:''},800);
		
		//draw two circles 
		twoCirclePaper.circle(orgAX, 180, 0).animate(anim1);
		twoCirclePaper.circle(orgBX, 180, 0).animate(anim2);
		
		 var anim3 = Raphael.animation({'font-size':'15px'},800);
		 
		 //write explaination for circles
		twoCirclePaper.text(orgAX-1.4*rA, 160, NameA+"\n命名空间："+StateA).attr({'font-family':'微软雅黑','font-size':'0px','fill':'#777'}).animate(anim3);
		twoCirclePaper.text(orgBX+1.6*rB, 160, NameB+"\n命名空间："+StateB).attr({'font-family':'微软雅黑','font-size':'0px','fill':'#777'}).animate(anim3);
		twoCirclePaper.text(400, 280, "不冲突").attr({'font-family':'微软雅黑','font-size':'0px','fill':'#777'}).animate(anim3);
	}
	

	
	function updateCurrentInputRow(evt)
	{
		
		if (evt.toElement == null) /*firefox*/
		{
			codeInputStatus.currentRowID = evt.target.parentElement.parentElement.id;
		}
		else /*chrome*/
		{
			codeInputStatus.currentRowID = evt.toElement.parentElement.parentElement.id;
			
		}
	
	}
	
	
	function insertToRow_2(evt)
	{
		if (evt.toElement == null) /*firefox*/
		{
			var tagContent = evt.target.textContent;
			$("#"+codeInputStatus.currentRowID).tagit("createTag", tagContent);
		}
		else /*chrome*/
		{	
			var tagContent = evt.toElement.textContent;
			$("#"+codeInputStatus.currentRowID).tagit("createTag", tagContent);
		}

	}
	
	function drawSth()
	{
		
		$("#warning_2").css('display','none');
		 code1 = $("#codeName_1").tagit("assignedTags")[0];
		 code2 = $("#codeName_2").tagit("assignedTags")[0];

		if (code1 == null || code2 == null)
		{
			//do nothing
		}
		else if (code1 == code2)
		{
			$("#warning_2").css('display','inline-block');
		}
		else if (code1 == 'Street' && code2 == 'EAN_13' || code1 == 'EAN_13' && code2 == 'Street')
		{
			drawTwoCircles(15,4,0.5,'EAN_13','Street','命名空间：10^12','命名空间：10^9','命名空间：10^8');
		}
		else
		{
			var code1Radius = eval("IDDataSource."+code1+".radius");
			var code2Radius = eval("IDDataSource."+code2+".radius");
			
			var code1Amount = eval("IDDataSource."+code1+".amount");
			var code2Amount = eval("IDDataSource."+code2+".amount");
			drawTwoCircles2(code1Radius,code2Radius,code1,code2,code1Amount,code2Amount,'123');
		}
		
	}

	