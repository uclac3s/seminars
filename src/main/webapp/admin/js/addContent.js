// JavaScript Document

var days = ["monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"];
var months = ["january", "february", "march", "april", "may", "june", "july", "august", "september", "october", "november", "december"];

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

function numDate(dateStr) {

	var parts = dateStr.split(",");
	var date = parts[1].trim().toLowerCase();
	var time = parts[3].trim().toLowerCase();

	var num = 0;
	var dateparts = date.split(" ");
	var month = months.indexOf(dateparts[0].trim()) + 1;
	var day = Number(dateparts[1].trim());
	var year = Number(parts[2].trim());
	var timeparts = time.split("-");
	var start = timeparts[0].split(":");
	var hour = Number(start[0]);
	if(timeparts[0].indexOf("pm") >= 0 && hour < 12) {
		hour += 12;	
	}
	num = year * 1000000 + month * 10000 + day * 100 + hour;
	return num;
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
	var timeFormat = /^(0?[0-9]|1[012])(:[0-5]\d)[APap][mM]$/;
	if(timeFormat.test(start) == false) {
		return "Please check the format of date, especially START TIME (hh:mmAM or hh:mmPM)!";
	}
	
	return "";
}

function Reset() {
	document.getElementById("title").value = '';
	document.getElementById("speaker").value = '';
	document.getElementById("date").value = '';
	document.getElementById("room").value = '';
	document.getElementById("remark").value = '';
}

function checkFields() {
	var title = null;
	if (document.getElementById("title") != null) {
		title = document.getElementById("title").value;
		if(title.length == 0) {
			alert("Title is EMPTY !!");
			return null;
		}
	}

	var category = null;
	if (document.getElementById("category") != null) {
		category = document.getElementById("category").value;
		if(category.length == 0) {
			alert("Category is EMPTY !!");
			return null;
		}
	}

	var speaker = null;
	if (document.getElementById("speaker") != null) {
		speaker = document.getElementById("speaker").value;
		if(speaker.length == 0) {
			alert("Speaker is EMPTY !!");
			return null;
		}
	}

	var date = null;
	if (document.getElementById("date") != null) {
		date = document.getElementById("date").value;
		if(date.length == 0) {
			alert("Date is EMPTY !!");
			return null;
		}
		var message = checkDate(date);
		if(message.length > 0) {
			alert(message);
			return null;
		}
	}

	var room = null;
	if (document.getElementById("room") != null) {
		room = document.getElementById("room").value;
		if(room.length == 0) {
			alert("Room is EMPTY !!");
			return null;
		}
	}

	var remark = null;
	if (document.getElementById("remark") != null) {
		remark = document.getElementById("remark").value;
		if(remark.length > 1000) {
			alert("Remark is Too Long !!");
			return null;
		}
	}

	var timestamp = 0;
	try {
		timestamp = numDate(date);
	} catch(err) {
		alert(err.message);	
	}

	return {
		"category": category,
		"title": title,
		"speaker": speaker,
		"date": date,
		"room": room,
		"remark": remark,
		"timestamp":timestamp
	};
}

function Submit(isDelete) {
	var seminar = checkFields();
	if ((seminar == undefined) || (seminar == null)) {
		return;
	}

	var seminar_dat = JSON.stringify(seminar);
	// alert(seminar_dat);

  $.ajax({
		type: "POST",
		url: "/admin/notesync",
		data: {"insert": seminar_dat, "delete": isDelete},
		dataType: "json",
		success: function (result) {
			alert(result["result"]);
		}
  });
}
