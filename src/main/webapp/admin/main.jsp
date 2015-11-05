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
        <title>C3S Seminar Submission Form</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="css/normalize.min.css">
        <link rel="stylesheet" href="css/main.css">
		<link rel="stylesheet" href="css/bootstrap.css">
        <script src="js/vendor/modernizr-2.6.2-respond-1.1.0.min.js"></script>
        <script src="js/addContent.js"></script>
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
					<div class = "page-header">
						<h1 class="STYLE1">View Seminars submitted (current week)</h1>
					</div>
					<div class="STYLE2" style="float:left; margin-top:5px"><a href="export">All</a> &nbsp; &nbsp;</div>
					<div class="STYLE2" style="float:left; margin-top:5px"><a href="export?category=Biomedicine">Bio</a> &nbsp; &nbsp;</div>
					<div class="STYLE2" style="float:left; margin-top:5px"><a href="export?category=Engineering">Eng/MSE</a> &nbsp; &nbsp;</div>
					<div class="STYLE2" style="float:left; margin-top:5px"><a href="export?category=CS">CS/Math</a> &nbsp; &nbsp;</div>
					<div class="STYLE2" style="float:left; margin-top:5px"><a href="export?category=Chemistry">Chem/Phy/Earth</a> &nbsp; &nbsp;</div>
					<div class="STYLE2" style="float:left; margin-top:5px"><a href="export?category=Business">Bus/Eco/Law</a> &nbsp; &nbsp;</div>
					<div class="STYLE2" style="float:left; margin-top:5px"><a href="export?category=Career">Career</a> &nbsp; &nbsp;</div>
					<div class="STYLE2" style="float:left; margin-top:5px"><a href="export?category=Social">Social/Human</a> &nbsp; &nbsp;</div>
				</div>

				<div class="col-md-11 col-sm-11">
					<div class = "page-header">
						<h1 class="STYLE1">Add Seminar</h1>
					</div>
          		<div class="STYLE2" style="float:left; margin-top:4px"> Category: &nbsp;&nbsp;</div>
				<select class="category form-control" id="category" style="width:24%;">
				 	<option value=""></option>
  				<option value="Biomedicine">Biomedicine</option>
  				<option value="Engineering">Engineering/Materials Science</option>
  				<option value="CS">CS/Math</option>
  				<option value="Chemistry">Chemistry/Physics/Earth Science</option>
					<option value="Business">Business/Economics/Law</option>
					<option value="Career">Career Development</option>
					<option value="Social">Social Science/Humanities</option>
				</select>
				<br>
				<div class="STYLE2" style="float:left; margin-top:5px"> Seminar Title: &nbsp; &nbsp;</div>
				<textarea class="form-control" id="title" style="width:65%;" rows="1" placeholder="Enter Title Here!" required></textarea>
				<br>
				<div class="STYLE2" style="float:left; margin-top:4px"> Speaker: &nbsp;&nbsp;</div>
				<textarea class="form-control" id="speaker" style="width:35%;" rows="1" placeholder="Enter Speaker Here!" required></textarea>
				<br>
				<div class="STYLE2" style="float:left; margin-top:4px"> Date & Time: &nbsp;&nbsp;</div>
				<textarea class="form-control" id="date" style="width:50%;" rows="1" placeholder="Example: Monday, November 02, 2015, 4:00PM - 5:30PM" required></textarea>
				<br>
				<div class="STYLE2" style="float:left; margin-top:4px"> Room: &nbsp;&nbsp;</div>
				<textarea class="form-control" id="room" style="width:40%;" rows="1" placeholder="Example: MS 4000" required></textarea>
				<br>
				<div class="STYLE2" style="float:left; margin-top:4px"> Remark: &nbsp;&nbsp;</div>
				<textarea class="form-control" id="remark" style="width:54%;" rows="4" placeholder="This is optional!" required></textarea>
          		<br>

        <button type="button" style="margin: 5px 25px 5px 0px;width:20%" class="executor btn btn-primary" onClick="Reset();">Reset</button>
				<button type="button" style="margin: 5px 25px 5px 0px;width:20%" class="executor btn btn-primary" onClick="Submit();">Submit Seminar</button>

                </div>

				<div class="col-md-11 col-sm-11">
				<div class = "page-header">
				  <h1><strong>格式要求</strong></h1>
				  </div>
					<p>1. 我们使用seminar title作为unique ID, title名相同的seminar会按时间覆盖。</p>
					<p>2. 除Remark外，其他输入框不能为空。</p>
					<p>3. 输入的值最大长度为1000字节。</p>
					<p>4. seminar 时间必按照统一的格式（英文逗号隔开）： DayOfWeek, Month Day, Year, Time。 Example: Monday, November 02, 2015, 4:00PM - 5:30 PM. </p>
					<p>5. 成功提交后，除category之外的其他部分会自动清空。 </p>
        		</div>

				<div class="col-md-11 col-sm-11">
				<div class = "page-header">
				  <h1><strong>联系我们</strong></h1>
				  </div>
					<p> 高石 <a href="mailto:gasohi a.t. cs.ucla.edu"><img src="envelope.gif" alt="at"></a>, 顾家奇<a href="mailto:jiaqigu a.t. cs.ucla.edu"><img src="envelope.gif" alt="at"></a> </p>
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
