<STYLE type="text/css">
ul.fldList{margin:0px; padding:0px; list-style:none;}
ul.fldList li{
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/fldList-left.png);
	background-repeat: no-repeat;
	background-position: left top;
	height: 30px; padding-left:11px;
	
}

ul.fldList li span{
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/fldList-right.png);
	background-repeat: no-repeat;
	background-position: right top;
	height: 30px; line-height:26px;
	display:block; padding-right:11px;
	font-family:Arial, Helvetica, sans-serif; font-size:12px;
	text-align:center;
}
ul.fldList li.d1{ width:917px; margin:7px 3px 0px 3px; clear:both;}
ul.fldList li.d2{width:450px; margin:7px 3px 7px 3px;}

.BlText12{font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#121212;}
.rowText12{font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#045ddf;}
.mRowClr{background:url(h25-Bg.gif) repeat-x top left; border-top:1px solid #f3f4f4;}
.mRowText{height:30px; font-family:Arial, Helvetica, sans-serif; font-size:17px; color:#08498b;}

.atlRw03{background-color:#d5e4f3;}
.atlRw05{background-color:#B0CAEC;}
.atlRw07{background-color:#6799e1;}

.crvBox2-ltBg{
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tt-left.png);
	background-repeat: no-repeat;
	background-position: left top;
	height: 30px; width:10px;
	
}
.crvBox2-ttBg{
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tt-bg.png);
	background-repeat: repeat-x;
	background-position: left top;
	height: 30px;
	font-family:Arial, Helvetica, sans-serif; font-size:15px; font-weight:normal;  color:#000000; line-height:25px;
}
.crvBox2-rtBg{
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tt-right.png);
	background-repeat: no-repeat;
	background-position: right top;
	height: 30px; width:40px;
	
}
.crvBox2-ttBg img{float:left; margin-right:8px;}

.crvBox2-top {
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-tbg.png); background-repeat: repeat-x; background-position: left top; height: 10px; width:100%;	
}
.crvBox2-botom {
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-btbg.png); background-repeat: repeat-x; background-position: left bottom; height: 17px; width:100%;	
}
.crvBox2-left {
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-lbg.png); background-repeat: repeat-y; background-position: left top;  width:10px;	
}
.crvBox2-right {
	background-image: url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/crvBox2-rbg.png); background-repeat: repeat-y; background-position: right top;  width:16px;	
}
.crvBox2-bg{background:#ffffff;}
.fltRight{float:right;}
.fltLeft{float:left;}

.avText{font-family:Tahoma; font-weight:normal; color:#166f0a; font-size:13px; padding:0px; margin:0px;}
.clearFix{margin:0px; padding:0px; clear:both;}

/*.cm_new_button{	 
	FONT-SIZE: 12px; FONT-FAMILY: Arial, Helvetica, sans-serif;
	 border:1px solid #333333;
	 color:#ffffff;
	background:url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/buttonN_bg.jpg) left top repeat-x;
	padding:0px .5em .1em;
	width:auto;
	overflow:visible;
	height:26px;
	 
}*/
.ActionMenuFontColor{
COLOR: #0E3892; TEXT-DECORATION: none

}

ul.flexmenu{ font: normal 12px Arial, Helvetica, sans-serif; margin: 0; padding: 0; position: absolute; left: 0; top: 0; list-style-type: none;  border-bottom-width: 0; visibility: hidden;  display: none; width: 160px;}
ul.flexmenu li{position: relative;}
ul.flexmenu li a{display: block; width: 160px; color: #000000; background-color:#ffffff; border-bottom:1px dotted #e8e8e8; text-decoration: none; padding: 4px 5px; font-family:Arial, Helvetica, sans-serif; font-size:12px;
}
ul.flexmenu li a.t32{border-bottom:none; text-decoration: none; padding: 0px; font-family:Arial, Helvetica, sans-serif; font-size:11px; background:url(<%=request.getContextPath()%>/static<%=Constants.STATIC_KEY%>/images/actionMenu_arrow.gif) no-repeat 4px 6px; padding:3px 0 3px 15px; }
ul.flexmenu li a.t32:hover{background-color:#e8f0fa;}

OPTION.yellow {
	background-color: yellow;
	color: black
}

OPTION.green {
	background-color: green;
	color: black
}

OPTION.gray {
	background-color: gray;
	color: black
}

OPTION.orange {
	background-color: orange;
	color: black
}

#sortable {
}

#sortable li {
}

#colorbox, #cboxOverlay, #cboxWrapper{position:absolute; top:0; left:0; z-index:9999; overflow:hidden;}
#cboxOverlay{position:fixed; width:100%; height:100%;}
#cboxMiddleLeft, #cboxBottomLeft{clear:left;}
#cboxContent{position:relative;}
#cboxLoadedContent{overflow:auto;}
#cboxTitle{margin:0;}
#cboxLoadingOverlay, #cboxLoadingGraphic{position:absolute; top:0; left:0; width:100%; height:100%;}
#cboxPrevious, #cboxNext, #cboxClose, #cboxSlideshow{cursor:pointer;}
.cboxPhoto{float:left; margin:auto; border:0; display:block;}
.cboxIframe{width:100%; height:100%; display:block; border:0;}

#cboxOverlay{background:url(overlay.png) repeat 0 0;}
#colorbox{}
#cboxTopLeft{width:21px; height:21px; background:url(controls.png) no-repeat -101px 0;}
#cboxTopRight{width:21px; height:21px; background:url(controls.png) no-repeat -130px 0;}
#cboxBottomLeft{width:21px; height:21px; background:url(controls.png) no-repeat -101px -29px;}
#cboxBottomRight{width:21px; height:21px; background:url(controls.png) no-repeat -130px -29px;}
#cboxMiddleLeft{width:21px; background:url(controls.png) left top repeat-y;}
#cboxMiddleRight{width:21px; background:url(controls.png) right top repeat-y;}
#cboxTopCenter{height:21px; background:url(border.png) 0 0 repeat-x;}
#cboxBottomCenter{height:21px; background:url(border.png) 0 -29px repeat-x;}
#cboxContent{background:#fff; overflow:hidden;}
.cboxIframe{background:#fff;}
#cboxError{padding:50px; border:1px solid #ccc;}
#cboxLoadedContent{margin-bottom:28px;}
#cboxTitle{position:absolute; bottom:4px; left:0; text-align:center; width:100%; color:#949494;}
#cboxCurrent{position:absolute; bottom:4px; left:58px; color:#949494;}
#cboxSlideshow{position:absolute; bottom:4px; right:30px; color:#0092ef;}
#cboxPrevious{position:absolute; bottom:0; left:0; background:url(controls.png) no-repeat -75px 0; width:25px; height:25px; text-indent:-9999px;}
#cboxPrevious:hover{background-position:-75px -25px;}
#cboxNext{position:absolute; bottom:0; left:27px; background:url(controls.png) no-repeat -50px 0; width:25px; height:25px; text-indent:-9999px;}
#cboxNext:hover{background-position:-50px -25px;}
#cboxLoadingOverlay{background:url(loading_background.png) no-repeat center center;}
#cboxLoadingGraphic{background:url(loading.gif) no-repeat center center;}
#cboxClose{position:absolute; bottom:0; right:0; background:url(controls.png) no-repeat -25px 0; width:25px; height:25px; text-indent:-9999px;}
#cboxClose:hover{background-position:-25px -25px;}

.cboxIE #cboxTopLeft,
.cboxIE #cboxTopCenter,
.cboxIE #cboxTopRight,
.cboxIE #cboxBottomLeft,
.cboxIE #cboxBottomCenter,
.cboxIE #cboxBottomRight,
.cboxIE #cboxMiddleLeft,
.cboxIE #cboxMiddleRight {
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#00FFFFFF,endColorstr=#00FFFFFF);
}

.cboxIE6 #cboxTopLeft{background:url(ie6/borderTopLeft.png);}
.cboxIE6 #cboxTopCenter{background:url(ie6/borderTopCenter.png);}
.cboxIE6 #cboxTopRight{background:url(ie6/borderTopRight.png);}
.cboxIE6 #cboxBottomLeft{background:url(ie6/borderBottomLeft.png);}
.cboxIE6 #cboxBottomCenter{background:url(ie6/borderBottomCenter.png);}
.cboxIE6 #cboxBottomRight{background:url(ie6/borderBottomRight.png);}
.cboxIE6 #cboxMiddleLeft{background:url(ie6/borderMiddleLeft.png);}
.cboxIE6 #cboxMiddleRight{background:url(ie6/borderMiddleRight.png);}

.cboxIE6 #cboxTopLeft,
.cboxIE6 #cboxTopCenter,
.cboxIE6 #cboxTopRight,
.cboxIE6 #cboxBottomLeft,
.cboxIE6 #cboxBottomCenter,
.cboxIE6 #cboxBottomRight,
.cboxIE6 #cboxMiddleLeft,
.cboxIE6 #cboxMiddleRight {
    _behavior: expression(this.src = this.src ? this.src : this.currentStyle.backgroundImage.split('"')[1], this.style.background = "none", this.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src=" + this.src + ", sizingMethod='scale')");
}

.theme_tb_data {
	FONT-SIZE: 8pt; FONT-FAMILY: Tahoma, Arial, Helvetica, sans-serif; BACKGROUND-COLOR: #E8F0FA
}
.listN0{height:18px; font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#363535;}
.listN01{width:155px; height:18px; font-family:Arial, Helvetica, sans-serif; font-size:12px; color:#363535;}

</STYLE>
<script language ="javascript">

var obj = false;
function checkHover() {
	if (!obj) {
             
		$('#actionListButtons').fadeOut('fast');
                 
                obj=false ;
	} //if
} //checkHover
$(document).ready(function() {
    $('a.showAction').hover(
        function(e) {
                var height = $('a.showAction').height();
                var width = $('a.showAction').width();
                leftVal=e.pageX-20+"px";
                topVal=e.pageY+10+"px"; 
		 $('#actionListButtons').css({left:leftVal,top:topVal}).fadeIn('fast');
		
	}, function() {
		//obj = $('#actionListButtons');
                 obj=false;
		setTimeout("checkHover()",3000);
	});
    
    $('#actionListButtons').hover(
        function() {
       	//obj=$(this);
       	obj=true;
	}, function() {
            obj=false;
              //$('#actionListButtons').fadeOut("slow");
              setTimeout("checkHover()",3000);
	});
         
        $("a.showAction").mousemove(function(e){
                obj=true;
                leftVal=e.pageX-20+"px";
                topVal=e.pageY+10+"px"; 
		$("#actionListButtons").css("top",(topVal)).css("left",(leftVal));
	});
   
    }); 
  function changeStyle(element) {
		element.className = "theme_tb_data";
	}
	function changeStyle1(element) {
		element.className = "ActionMenuFontColor";
	} 
</script>