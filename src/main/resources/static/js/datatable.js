$(document).ready( function () {
	 var table = $('#Division').DataTable({
			"sAjaxSource": "/division",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			      { "mData": "code"},
		          { "mData": "name" }
			]
	 })
});

$(document).ready( function () {
	 var table = $('#user').DataTable({
			"sAjaxSource": "/user",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			      { "mData": "_id"},
		          { "mData": "name" }
			]
	 })
});

$(document).ready( function () {
	 var table = $('#Department').DataTable({
			"sAjaxSource": "/department",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			      { "mData": "_id"},
		          { "mData": "_Name_Deps" }
			]
	 })
});

$(document).ready( function () {
	 var table = $('#Operation').DataTable({
			"sAjaxSource": "/operation",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			      { "mData": "_id"},
		          { "mData": "_Opers" }
			]
	 })
});