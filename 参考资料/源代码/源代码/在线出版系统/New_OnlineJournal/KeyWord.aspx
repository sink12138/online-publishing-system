﻿<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="KeyWord.aspx.cs" Inherits="New_OnlineJournal.KeyWordTest" MaintainScrollPositionOnPostback="true"%>
<!DOCTYPE html>

<html>
<!-- head -->
<head>
<title>按关键字搜索</title>
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" /><!-- bootstrap-CSS -->
<link rel="stylesheet" href="css/bootstrap-select.css"><!-- bootstrap-select-CSS -->
<link href="css/font-awesome.css" rel="stylesheet" type="text/css" media="all" /><!-- Fontawesome-CSS -->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script type='text/javascript' src='js/jquery-2.2.3.min.js'></script>
<script type="text/javascript">
$(document).ready(function() {
    window.location.href = "#parentVerticalTab3";  //注意有个#
});   
</script>
    <script type="text/javascript">
        <!--
     
        function autoclick()
        {
            var hr = $("#bookmark");
            var anh = $(hr).offset().top;
            $("html,body").stop().animate({ scrollTop: anh }, 2000);
            location.hash = "#bookmark";
        }
        // -->
    </script> 
    <script>
        function e2() {
            $('#mail').attr('action', 'mailto:' + $('#to').val());
        }
    </script>
<!-- Custom Theme files -->
<!--theme-style-->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />	
<!--//theme-style-->
<!--meta data-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!--//meta data-->
<!-- online fonts -->
<link href="http://fonts.googleapis.com/css?family=Montserrat:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&amp;subset=latin-ext,vietnamese" rel="stylesheet">
<link href="http://fonts.googleapis.com/css?family=Oxygen:300,400,700&amp;subset=latin-ext" rel="stylesheet">
<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i&amp;subset=cyrillic,cyrillic-ext,greek,greek-ext,latin-ext,vietnamese" rel="stylesheet">
<!-- /online fonts -->
<style type="text/css">
    .css1 {
    width: 85%;
    padding: 10px;
    font-weight: normal;
    background: none;
    border: 1px solid #e4e4e4;
    color: #545454;
    outline: none;
    font-size: 14px;
    transition: 0.5s all;
    -webkit-transition: 0.5s all;
    -o-transition: 0.5s all;
    -ms-transition: 0.5s all;
    background: #fff;
    -moz-transition: 0.5s all;
}
</style>       
</head>
<!-- //head -->
<!-- body -->
<body >
<!--header-->
<header>
	<div class="container">
		<!--logo-->
			<div class="logo">
				<h1><a href="index.html">Online Journal</a></h1>
			</div>
		<!--//logo-->
		  <div class="w3layouts-login"> </div>    
		<div class="clearfix"></div>
    </div>
</header>

<!--//-->	
	<div class=" header-right">
		<div class="banner">
			 <div class="slider">
			    <div class="callbacks_container">
			      <ul class="rslides" id="slider">		       
					 <li>
			          	 <div class="banner1">
			           		<div class="caption">
					          	<h3><span>欢迎访问历史学会！</span></h3>					          	
			          		</div>
			          	</div>
			         </li>					 
			      </ul>
			  </div>
			</div>
		</div>
	</div>
	 

    <!--Vertical Tab-->
	<div class="categories-section main-grid-border" id="mobilew3layouts">
		<div class="container">
			<div class="category-list">
			  <div id="parentVerticalTab">
					<div class="agileits-tab_nav">
					<ul class="resp-tabs-list hor_1">
					  <a href="Index.aspx" style="text-decoration: none"><li><i class="icon fa fa-tag" aria-hidden="true"></i>按作者搜索</li></a>
					  <a href="Category.aspx#parentVerticalTab2" style="text-decoration: none"><li><i class="icon fa fa-tag" aria-hidden="true"></i>分类搜索</li></a>
						<li><i class="icon fa fa-tag" aria-hidden="true"></i>按关键字搜索</li>					
						<a href="Email.aspx#parentVerticalTab4" style="text-decoration: none"><li><i class="icon fa fa-envelope-o" aria-hidden="true"></i>向编辑发送电子邮件</li></a>
					</ul>
					</div>
                  <a id="auto" href="#bookmark"></a>
                  
					<div class="resp-tabs-container hor_1">
                      <!-- tab1 -->
						<div>
                            <div class="tabs-box">
<div class="tab-grids">
                 <div id="tab1" class="tab-grid">  
		                      <div class="login-form">
                                  	
						<form id="signup1">
						</form>	
																							
						</div>	

                </div>
			</div>
		</div>
	<!-- script -->
		<script>
			$(document).ready(function() {
				$("#tab2").hide();
				$("#tab3").hide();
				$("#tab4").hide();
				$(".tabs-menu a").click(function(event){
					event.preventDefault();
					var tab=$(this).attr("href");
					$(".tab-grid").not(tab).css("display","none");
					$(tab).fadeIn("slow");
				});
			});
		</script>
                            <script type="text/javascript">
        <!--
     
        function autoclick()
        {
            
                var hr = $("#bookmark");
                var anh = $(hr).offset().top;
                $("html,body").stop().animate({ scrollTop: anh }, 2000);
                location.hash = "#bookmark";
        
        }
        // -->
    </script>                    
		              </div>
                        <!-- /tab1 -->
                        <!-- tab2 -->
						<div>
                            <div class="tabs-box">
<div class="tab-grids">
                 <div id="tab1" class="tab-grid">  
		                      <div class="login-form">	
						
																							
						</div>	

                </div>
			</div>
		</div>
	<!-- script -->
		<script>
			$(document).ready(function() {
				$("#tab2").hide();
				$("#tab3").hide();
				$("#tab4").hide();
				$(".tabs-menu a").click(function(event){
					event.preventDefault();
					var tab=$(this).attr("href");
					$(".tab-grid").not(tab).css("display","none");
					$(tab).fadeIn("slow");
				});
			});
		</script>                   
		              </div>
                        <!-- /tab2 -->                       
       
                        <!-- tab3 -->
				<div>
                            <div class="tabs-box">
<div class="tab-grids">
                 <div id="tab1" class="tab-grid">  
		                      <div class="login-form">	
						<form id="signup" runat="server">
                            <asp:ScriptManager ID="ScriptManager1" runat="server">
                                <Scripts>
                                    <asp:ScriptReference Path="~/css/bootstrap-select.css" />
                                </Scripts>
                                    </asp:ScriptManager>
						<ol>							
							<li>
                                <h4>请输入关键字</h4>								
							</li>
                            <li>
                                <div class="agileits-select">
                                    <asp:UpdatePanel ID="UpdatePanel1" runat="server" UpdateMode="Conditional">
                                                <ContentTemplate> 
                                                    <asp:textBox id="TextBox2" runat="server" Width="650" CssClass="css1" placeholder="Enter Key Word" onfocus="this.placeholder = '';" AutoPostBack="true"/>
                                                    <asp:button id="Button2" runat="server" CssClass="submit" Text="Search" onclick="Button2_Click" UseSubmitBehavior="False" CausesValidation="False"/>
                                                    <%--<input type="button" onclick='document.getElementById("Button2").click();' id="Button2" />--%>
                                                </ContentTemplate> 
                                        <Triggers>
                                            <asp:AsyncPostBackTrigger ControlID="Button2" />
                                        </Triggers>
                                        </asp:UpdatePanel>
                                    
                                    <br/>
                                    
                                                    </div>
                            </li>
                            <li>
                            	<h4>请选择文章</h4>
                            </li>
                            <li>                               
                                <div class="agileits-select">
                                
                                <asp:UpdatePanel ID="UpdatePanel2" runat="server" UpdateMode="Conditional">
                                <ContentTemplate>                             
                                <asp:DropDownList ID="article_list" CssClass="selectpicker show-tick" runat="server" Height="40px" Width="600px" AutoPostBack="true" OnSelectedIndexChanged="article_list_SelectedIndexChanged">
                                </asp:DropDownList>
                                </ContentTemplate>                                    
                                <Triggers>
                                    <asp:AsyncPostBackTrigger ControlID="Button2" />
                                </Triggers>
                                </asp:UpdatePanel>
                                </div>
                            </li>
                            <li>
                                <div class="mobile-right ">
                                    <h4>文章摘要</h4>
                                    <div class="mobile-rchge">
                                        <asp:UpdatePanel ID="UpdatePanel3" runat="server" ChildrenAsTriggers="False" UpdateMode="Conditional">
                                        <ContentTemplate>
                                        <asp:TextBox ID="TextBox1" CssClass="css1" runat="server" TextMode="MultiLine" Height="129px" ReadOnly="True" Width="580px" OnLoad="TextBox1_Load"></asp:TextBox>	
                                        </ContentTemplate>
                                            <Triggers>
                                                <asp:AsyncPostBackTrigger ControlID="article_list" EventName="SelectedIndexChanged" />
                                            </Triggers>
                                        </asp:UpdatePanel>	
                                    </div>
                                    <div class="clearfix"></div>
                                </div>
                            </li>
                            <li>
                                
                                                <asp:button runat="server" CssClass="submit" Text="Download" onclick="Button1_Click" UseSubmitBehavior="False"/>
                                 
                            </li>
				        </ol>
						</form>	
																							
						</div>	

                </div>
			</div>
		</div>
	<!-- script -->
		<script>
			$(document).ready(function() {
				$("#tab2").hide();
				$("#tab3").hide();
				$("#tab4").hide();
				$(".tabs-menu a").click(function(event){
					event.preventDefault();
					var tab=$(this).attr("href");
					$(".tab-grid").not(tab).css("display","none");
					$(tab).fadeIn("slow");
				});
			});
		</script>                   
		              </div>
                        <!-- /tab3 -->
                        
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>
	</div>
	<!--Plug-in Initialisation-->
	<script type="text/javascript">
    $(document).ready(function() {

        //Vertical Tab
        $('#parentVerticalTab').easyResponsiveTabs({
            type: 'vertical', //Types: default, vertical, accordion
            width: 'auto', //auto or any width like 600px
            fit: true, // 100% fit in a container
            closed: 'accordion', // Start closed if in accordion view
            tabidentify: 'hor_1', // The tab groups identifier
            activate: function(event) { // Callback function if tab is switched
                var $tab = $(this);
                var $info = $('#nested-tabInfo2');
                var $name = $('span', $info);
                $name.text($tab.text());
                $info.show();
            }
        });
    });
</script>
	<!-- //Categories -->


<!--phone-->
	
<!--//phone-->

	
<!-- Support content -->
	
<!-- //Support content -->

    
<!--offers-->
		
<!--//offers-->
    
<!--partners-->
<div class="w3layouts-partners"> </div>	
<!--//partners-->	
    
<!-- subscribe -->
	
<!-- //subscribe --> 
    
<!--footer-->
<footer>
	<div class="container-fluid"> </div>
	<div class="w3l-footer-bottom">
		<div class="container-fluid">
			<div class="col-md-4 w3-footer-logo">
				<h2><a href="index.html">历史学会</a></h2>
			</div>
			<div class="col-md-8 agileits-footer-class"> </div>
		<div class="clearfix"> </div>
	 	</div>
	</div>
</footer>
<!--//footer-->
    
<!-- for bootstrap working -->
		<script src="js/bootstrap.js"></script>
<!-- //for bootstrap working --><!-- Responsive-slider -->
    <!-- Banner-slider -->
<script src="js/responsiveslides.min.js"></script>
   <script>
    $(function () {
      $("#slider").responsiveSlides({
      	auto: true,
      	speed: 500,
        namespace: "callbacks",
        pager: true,
      });
    });
  </script>
    <!-- //Banner-slider -->
<!-- //Responsive-slider -->   
<!-- Bootstrap select option script -->
<script src="js/bootstrap-select.js"></script>
<script>
  $(document).ready(function () {
    var mySelect = $('#first-disabled2');

    $('#special').on('click', function () {
      mySelect.find('option:selected').prop('disabled', true);
      mySelect.selectpicker('refresh');
    });

    $('#special2').on('click', function () {
      mySelect.find('option:disabled').prop('disabled', false);
      mySelect.selectpicker('refresh');
    });

    $('#basic2').selectpicker({
      liveSearch: true,
      maxOptions: 1
    });
  });
</script>
<!-- //Bootstrap select option script -->
    
<!-- easy-responsive-tabs -->    
<link rel="stylesheet" type="text/css" href="css/easy-responsive-tabs.css " />
<script src="js/easyResponsiveTabs.js"></script>
<!-- //easy-responsive-tabs --> 
    <!-- here stars scrolling icon -->
			<script type="text/javascript">
				$(document).ready(function() {
					/*
						var defaults = {
						containerID: 'toTop', // fading element id
						containerHoverID: 'toTopHover', // fading element hover id
						scrollSpeed: 1200,
						easingType: 'linear' 
						};
					*/
										
					$().UItoTop({ easingType: 'easeOutQuart' });
										
					});
			</script>
			<!-- start-smoth-scrolling -->
			<script type="text/javascript" src="js/move-top.js"></script>
			<script type="text/javascript" src="js/easing.js"></script>
			<script type="text/javascript">
				jQuery(document).ready(function($) {
					$(".scroll").click(function(event){		
						event.preventDefault();
						$('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
					});
				});
			</script>
			<!-- start-smoth-scrolling -->
		<!-- //here ends scrolling icon -->
</body>
<!-- //body -->
</html>
