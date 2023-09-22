//*tabsNav
$(".navItem").click(function(){
    var val = $(this).attr('data-tab-target');
    console.log(val);

    $('.navItem').removeClass('active');
    $(this).addClass('active');

    $(".tabContainer ").attr('data-active-tab', val);

});

//sidebarNav

$("#sidebarNav ul li").on("click", function() {
    var $this = $(this),
        $links = $("#sidebarNav ul li");

    $links.removeClass("selected");
    $this.addClass("selected");
});


