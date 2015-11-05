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
        	<h1 class=\"title\">Weekly Seminar Links Updates</h1>\
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

function Submit() {

	var link_map = {
		'career_dev': '',
		'bio': '',
		'eng': '',
		'eco': '',
		'chem_phys_earth': '',
		'math_cs': '',
		'social': ''
	};

	for (var key in link_map) {
     if (link_map.hasOwnProperty(key)) {
			 if (document.getElementById(key) != null) {
					link_map[key] = document.getElementById(key).value;
					if(link_map[key].length == 0) {
						alert(key + " is EMPTY !!");
						return;
					}
			 }
     }
  }

	var dat = JSON.stringify(link_map);
	alert(dat);

           $.ajax({
                type: "POST",
                url: "/admin/notesync",
                data: {"seminar_links": dat},
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