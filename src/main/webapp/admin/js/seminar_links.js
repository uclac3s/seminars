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
	// update link_arr if category is changed

	var link_arr = [
		{'category': 'career_dev', 'url': ''},
		{'category': 'bio', 'url': ''},
		{'category': 'eng', 'url': ''},
		{'category': 'eco', 'url': ''},
		{'category': 'chem_phys', 'url': ''},
		{'category': 'earth', 'url': ''},
		{'category': 'math_cs', 'url': ''},
		{'category': 'social', 'url': ''}
	];

	for (var i=0; i<link_arr.length; i++) {
		var key = link_arr[i]['category'];
		if (document.getElementById(key) != null) {
			link_arr[i]['url'] = document.getElementById(key).value;
			/*if(link_arr[i]['url'].length == 0) {
				alert(key + " is EMPTY !!");
				return;
			}*/
		}
	}

	var dat = JSON.stringify(link_arr);
	alert('Only non-empty url will be updated. Data: \n' + dat);

    $.ajax({
        type: "POST",
        url: "/admin/notesync",
        data: {"seminar_links": dat},
        dataType: "json",
        success: function (result) {
            alert(result["result"]);
        }
    });
}