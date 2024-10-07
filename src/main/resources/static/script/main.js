$(document).ready(function(){
  $("ul.nav li").click(function(){
    $(this).siblings().find("a").removeClass("active");
    $(this).find("a").addClass('active');
  });
});


