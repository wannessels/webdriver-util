var bootstrapModalAnimations = 0;
var bootstrapModalAnimationStart = function() {
	bootstrapModalAnimations++;
};
var bootstrapModalAnimationEnd = function() {
	bootstrapModalAnimations--;
};

$("[data-toggle='modal']").each(function() {
	var modalTarget = $(this).attr("data-target");
	if (typeof modalTarget == 'undefined') {
		modalTarget = $(this).attr("href");
	}
	registerEventHandlers($(modalTarget));
});

function registerEventHandlers(modal) {
	modal.on({
		show: bootstrapModalAnimationStart,
		shown: bootstrapModalAnimationEnd,
		hide: bootstrapModalAnimationStart,
		hidden: bootstrapModalAnimationEnd
	});
}