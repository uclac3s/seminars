<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
  UserService userService = UserServiceFactory.getUserService();
  User user = userService.getCurrentUser();
  String email = user.getEmail();
  // servlet will get the user info in the same way, so we do nothing if logged in
  // pageContext.setAttribute("user", user);
%>

<!DOCTYPE html>

<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Weekly Seminar Links updates</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/normalize.min.css">
        <link rel="stylesheet" href="css/main.css">
		<link rel="stylesheet" href="css/bootstrap.css">
        <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
        <script src="js/seminar_links.js"></script>
        <style type="text/css">
<!--
.STYLE1 {
	font-size: large;
	font-weight: bold;
}
.STYLE2 {font-size: 18px}
-->
        </style>
</head>

    <body onLoad="add_header(); add_footer();">
    <style type="text/css">
    #Video1
    {
     width:800px;
     border:2px solid blue;
     display:block;
     z-index:99;
    }
    #Video2
    {
     width:720px;
     border:2px solid red;
     z-index:100;
    }
    </style>
	<div id="HEADER">
	</div>
    <div id="includedContent">
    </div>
    <div class="main-container">
            <div class="main wrapper clearfix">
              <article>

				<div class="col-md-11 col-sm-11">

				<div class="STYLE2" style="float:left; margin-top:5px"> Career Development: &nbsp; &nbsp;</div>
				<textarea class="form-control" id="career_dev" style="width:65%;" rows="1" placeholder="" required></textarea>
				<br>
				<div class="STYLE2" style="float:left; margin-top:4px"> Biomedicine: &nbsp;&nbsp;</div>
				<textarea class="form-control" id="bio" style="width:65%;" rows="1" placeholder="" required></textarea>
				<br>
				<div class="STYLE2" style="float:left; margin-top:4px"> Engineering and MSE: &nbsp;&nbsp;</div>
				<textarea class="form-control" id="eng" style="width:65%;" rows="1" placeholder="" required></textarea>
				<br>
				<div class="STYLE2" style="float:left; margin-top:4px"> Economy and Business: &nbsp;&nbsp;</div>
				<textarea class="form-control" id="eco" style="width:65%;" rows="1" placeholder="" required></textarea>
				<br>
				<div class="STYLE2" style="float:left; margin-top:4px"> Chemistry/Physics/Earth Science: &nbsp;&nbsp;</div>
				<textarea class="form-control" id="chem_phys_earth" style="width:65%;" rows="1" placeholder="" required></textarea>
        <br>
        <div class="STYLE2" style="float:left; margin-top:4px"> Math and Computer Science: &nbsp;&nbsp;</div>
        <textarea class="form-control" id="math_cs" style="width:65%;" rows="1" placeholder="" required></textarea>
        <br>
        <div class="STYLE2" style="float:left; margin-top:4px"> Social Science: &nbsp;&nbsp;</div>
        <textarea class="form-control" id="social" style="width:65%;" rows="1" placeholder="" required></textarea>
        <br>

				<button type="button" style="margin: 5px 25px 5px 0px;width:20%" class="executor btn btn-primary" onClick="Submit();">Update links</button>

        </div>

				<div class="col-md-11 col-sm-11">
				<div class = "page-header">
				  <h1><strong>Specifications</strong></h1>
				  </div>
					<p>Put weekly seminar links on each textfield</p>
        	</div>

				<div class="col-md-11 col-sm-11">
				<div class = "page-header">
				  <h1><strong>Contact</strong></h1>
				  </div>
				</div>

             </article>
          <div id="ASIDE"></div>
      </div> <!-- #main -->
    </div> <!-- #main-container -->

        <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.11.1.min.js"><\/script>')</script>
        <script src="js/plugins.js"></script>
    </body>

    <div id="FOOTER">
	</div>

</html>
