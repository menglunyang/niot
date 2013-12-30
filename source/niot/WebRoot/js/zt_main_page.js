
function switchMainToContainer1()
{

	$("#platformTitle").fadeOut();
	$(".container_main").fadeOut();
	$(".ltittle").fadeOut(function(){
		$(".container_1").fadeIn();
	});

}

function switchMainToContainer2()
{
	$("#platformTitle").fadeOut();
	$(".container_main").fadeOut();
	$(".ltittle").fadeOut(function(){
		$(".container_2").fadeIn();
	});
}

function switchMainToContainer3()
{
	$("#platformTitle").fadeOut();
	$(".container_main").fadeOut();
	$(".ltittle").fadeOut(function(){
		$(".container_3").fadeIn();
	});
}

function hideIdTable()
{
	$("#idTable").slideUp('slow');
}
function showIdTable()
{
	$("#idTable").slideDown('slow');
}
