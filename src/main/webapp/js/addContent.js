// JavaScript Document

var days = "monday tuesday wednesday thursday friday saturday sunday";
var months = "january february march april may june july august september october november december";

function add_footer() {
	document.getElementById("FOOTER").innerHTML = " <div class='footer-container'>            <footer class='wrapper'> \
	Copyright © 2015 UCLA C3S. All Rights Reserved.            </footer>    </div> " ;
}

function add_header() {
	document.getElementById("HEADER").innerHTML = "	<div class=\"header-container\">\
    <header class=\"wrapper clearfix\">\
	<table border=\"0\" cellspacing=\"20px\" cellpadding=\"10px\">\
	<tr>\
	<td>	<img src=\"Logo2.png\" width=\"120px\" alt=\"ScAI\"> </td> \
	<td>    <div class=\"title-container\">\
        	<h1 class=\"title\">C3S Seminar Submission Form</h1>\
        	</div>\
	</td>\
	</tr>\
	</table>\
        <div class=\"nav-container\">\
            <nav>\
                <ul>\
                </ul>\
            </nav>\
        </div>\
    </header>\
	</div>";
}

function checkDate(dateStr) {

	var parts = dateStr.split(",");
	if(parts.length != 4) {
		return "Please check the format of date, especially the number of COMMA!";
	}
	
	var dayofweek = parts[0].trim().toLowerCase();
	var date = parts[1].trim().toLowerCase();
	var year = parts[2].trim();
	var time = parts[3].trim().toLowerCase();
	
	if(days.indexOf(dayofweek) == -1) {
		return "Please check the format of date, especially DAY OF WEEK!";
	}
	
	var dateparts = date.split(" ");
	if(dateparts.length != 2) {
		return "Please check the format of date, especially MONTH DAY!";
	}
	var month = dateparts[0].trim();
	if(months.indexOf(month) == -1) {
		return "Please check the format of date, especially MONTH!";
	}
	
	var timeparts = time.split("-");
	if(timeparts.length != 2) {
		return "Please check the format of date, especially TIME (only one dash allowed)!";
	}
	var start = timeparts[0].trim();
	var end = timeparts[0].trim();
	if((start.indexOf("am") < 0 && start.indexOf("pm") < 0) || (end.indexOf("am") < 0 && end.indexOf("pm") < 0)) {
		return "Please check the format of date, especially TIME (specify AM/PM)!";
	}
	
	return "";
}

function Submit() {
	
	var title = null;
	if (document.getElementById("title") != null) {
		title = document.getElementById("title").value;
		if(title.length == 0) {
			alert("Title is EMPTY !!");
			return;
		}
	} 
	
	var category = null;
	if (document.getElementById("category") != null) {
		category = document.getElementById("category").value;
		if(category.length == 0) {
			alert("Category is EMPTY !!");
			return;
			// window.alert("Category is EMPTY !!");
		}
	} 
	
	var speaker = null;
	if (document.getElementById("speaker") != null) {
		speaker = document.getElementById("speaker").value;
		if(speaker.length == 0) {
			alert("Speaker is EMPTY !!");
			return;
		}
	} 
	
	var date = null;
	if (document.getElementById("date") != null) {
		date = document.getElementById("date").value;
		if(date.length == 0) {
			alert("Date is EMPTY !!");
			return;
		}
		var message = checkDate(date);
		if(message.length > 0) {
			alert(message);
			return;
		}
	} 
	
	var room = null;
	if (document.getElementById("room") != null) {
		room = document.getElementById("room").value;
		if(room.length == 0) {
			alert("Room is EMPTY !!");
			return;
		}
	} 
	
	var remark = null;
	if (document.getElementById("remark") != null) {
		remark = document.getElementById("remark").value;
		if(remark.length > 1000) {
			alert("Remark is Too Long !!");
			return;
		}
	} 
	
	var seminar = {
		"category": category,
		"title": title, 
		"speaker": speaker,
		"date": date,
		"room": room, 
		"remark": remark
	}

	var seminar_dat = JSON.stringify(seminar);
	alert(seminar_dat);

           $.ajax({
                type: "POST",
                url: "/notesync",
                data: {"insert": seminar_dat},
                dataType: "json",
                success: function (result) {
                   alert(result);
                }
            });
	/*
	document.getElementById("title").value = '';
	document.getElementById("speaker").value = '';
	document.getElementById("date").value = '';
	document.getElementById("room").value = '';
	document.getElementById("remark").value = '';
	*/

}