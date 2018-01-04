function showMain() {
    $('.containerMain').removeClass('hide');
}

 function toggleOVInput(elem) {
        if ($(elem).val() != "NO_CHECK") {
            PF('ovInput').jq.removeClass("nd");
            PF('ovInput').jq.parent().prev().children().removeClass('nd');
        }else{
            PF('ovInput').jq.addClass("nd");
            PF('ovInput').jq.parent().prev().children().addClass('nd');
        }
    }

$(document).ready(function(){
   
});