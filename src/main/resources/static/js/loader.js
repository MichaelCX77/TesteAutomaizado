//Auto Loads

$('#div-header').load("fragments/header");
$('#nav-menu').load("fragments/itens-menu");

let menuflag = false;
const toggleMenu = () => {

    const openMenu = () => {
        $("#nav-menu").css('display', 'block')
        $('#div-cards').css('padding', '20px 43px')
        // $('.div-desc-comment').attr('class', 'div-desc-comment col-8')
        // $('.div-date-annotation > span').css('margin-left','-15px')
        // $('#div-annotation').attr('class', 'col-12 div-content div-detail-task')
        menuflag = true
    }
    
    const closeMenu = () => {
        $("#nav-menu").css('display', 'none')
        $('#div-cards').css('margin-left', '30px')
        // $('.div-desc-comment').attr('class', 'div-desc-comment col-9')
        // $('.div-date-annotation > span').css('margin-left','-5px')
        // $('#div-annotation').attr('class', 'col div-content div-detail-task')
        menuflag = false
    }

    menuflag ? closeMenu() : openMenu()
}

const actionMiniMsg = (mini__mmsg, time, event) => {

    $("body").append(mini__mmsg)
    $(".mini-msg").css("top", (event.clientY - 55) + "px");
    $(".mini-msg").css("left", (event.clientX - 10) + "px");
    $(".mini-msg").show("slow");

    setTimeout(() => {
        $(".mini-msg").hide("slow");
        setTimeout(() => {
            $(".mini-msg").remove()
        }, 500);
    }, time);
}

const toggleMiniMsg = (toggle,event) => {

    $(".mini-msg").remove();
    let mini__msg = "";
    const statusToogle = toggle.is(':checked')
    let statusToogleBd;
    if (statusToogle) {
        mini__msg = mini_msg.replace("MINI_MSG", "On");
        statusToogleBd = "A"
    } else {
        mini__msg = mini_msg.replace("MINI_MSG", "Off");
        statusToogleBd = "I"
    }
    actionMiniMsg(mini__msg, 1000,event)
    
    return statusToogleBd;
}

const formataData = data => {
    const adicionaZero = numero => {
        if (numero <= 9)
            return "0" + numero;
        else
            return numero;
    }
    let dataFormatada = (adicionaZero(data.getDate().toString()) + "/" + (adicionaZero(data.getMonth() + 1).toString()) + "/" + data.getFullYear())
    return dataFormatada;
}

$(document).on("click", "#btn-menu", function () {toggleMenu()})
$(document).on("click", "#btn-exit", function() {window.location = "logout"})