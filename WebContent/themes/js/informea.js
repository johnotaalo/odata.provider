$(document).ready(function() {
	var ctrl = $('#updateStatus');
	$.ajax({
		url: updateURL,
		cache: false,
		dataType: "json",
	}).done(function(data) {
		console.log(data);
		if(data.needsUpdate) {
			ctrl.html('New version available: ' + data.remoteVersion);
			ctrl.removeClass('label-info').addClass('label-danger');
			$('#updateButton').removeClass('hidden');
		} else {
			ctrl.html('Toolkit is up to date');
			ctrl.removeClass('label-info').addClass('label-success');
		}
	}).error(function(data) {
		ctrl.removeClass('label-info').addClass('label-danger');
		ctrl.html('Error checking version!');
		console.log(data);
	});
});