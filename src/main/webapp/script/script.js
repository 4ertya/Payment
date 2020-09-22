$(document).ready(function(){
    $(".authorization-form").on("click", ".tab", function() {
        // Удаляем классы active
        $(".authorization-form").find(".active").removeClass("active");
    
        // Добавляем классы active
        $(this).addClass('active');
        $(".tab-form").eq($(this).index()).addClass("active")
    });
})

