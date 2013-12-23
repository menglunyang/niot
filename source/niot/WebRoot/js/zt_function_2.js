
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
	
/*var t = Raphael('circlesContainer',800, 345);
var c = t.circle(400, 180, 40);*/