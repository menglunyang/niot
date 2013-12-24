
    $(document).ready(function() {
        $("#codeName_1").tagit();
        $("#codeName_2").tagit();

    });
    
	$("#codeName_1").tagit({
	    /*availableTags: ["0-9", "a-z", "A-Z", "0-5", "3-8", "f-m", "x-z","N-Q"],*/
	    tagLimit:1,
	    readOnly:true
	});
	
	$("#codeName_2").tagit({
	   /* availableTags: ["0-9", "a-z", "A-Z", "0-5", "3-8", "f-m", "x-z","N-Q"],*/
	    tagLimit:1,
	    readOnly:true
	});
	
	var twoCirclePaper = Raphael('circlesContainer',800, 345);
	
	
	function drawTwoCircles(AreaA, AreaB, AreaC,NameA, NameB, StateA, StateB, StateC)
	{
		twoCirclePaper.clear();
		
		var baseBigCilcleR = 150;
		
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