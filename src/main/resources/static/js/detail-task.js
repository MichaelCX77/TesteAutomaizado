/*      ### Statements ###   */

let idTask;
let dateIni;
let dateFim;
let dateAlertYellow;
let dateAlertRed;
let annotation_oppening;
let textDesc;
let textDescannotation = new Map();

/*      ### Main Functions ###   */
const responseError = (error) => {

    if(error.response.status == 304){
        alert('Essa Task faz parte do histórico e não pode ser modificada.')
    } else {
        alert(error)
    }
}


const generateDesc = (text, modelSpan, limiteLines) => {

    const sufix = '</span>'

    if (text != null && text.includes('\n')) {
        let break_ = false;
        let array_string = text.split('\n')
        let text_final = "";

        array_string.forEach(function (element, i) {
            if (i > limiteLines && !break_) {
                break_ = true;
            } else if (!break_) {
                text_final = text_final + ((element == null || element == "") ? `${modelSpan}&nbsp${sufix}` : `${modelSpan}${element}${sufix}`)
            }
        })
        break_ ? text_final = text_final + `${modelSpan}...${sufix}` : ""
        return text_final;
    } else {
        return `${modelSpan}${text}${sufix}`;
    }
}

const setAnnotation = (annotation_original, order) => {

    let annotation_html = annotation.replace('DATA-ANNOTATION', formataData(new Date(annotation_original.date.substring(0, 10) +  " ")))
    let text = annotation_original.description;
    let text_final = generateDesc(text, '<span class="txt-description-annotation">', 15)
    annotation_html = annotation_html.replace('DESCRICAO', text_final)

    if(order != null && order == "P"){
        $(".div-body-bloco-annotations").prepend(annotation_html)
        $(".div-body-bloco-annotations").children().eq(0).attr("value", annotation_original.id)
    } else {
        $(".div-body-bloco-annotations").append(annotation_html)
        $(".div-body-bloco-annotations").children().last().attr("value", annotation_original.id)
    }
}

//Alteração de Dates de Alerts
const toggleDateAlert = (toggle, action) => {
    if(toggle == "Y" && action == "A") {
        $("#dt-alert-yellow").css("border", "1px solid rgb(41, 189, 194)")
        $("#dt-alert-yellow").css("pointer-events", "fill")
    } else if (toggle == "Y" && action == "I"){
        $("#dt-alert-yellow").css("border", "1px solid #ccc")
        $("#dt-alert-yellow").css("pointer-events", "none")
    }
    if(toggle == "R" && action == "A") {
        $("#dt-alert-red").css("border", "1px solid rgb(41, 189, 194)")
        $("#dt-alert-red").css("pointer-events", "fill")
    } else if (toggle == "R" && action == "I"){
        $("#dt-alert-red").css("border", "1px solid #ccc")
        $("#dt-alert-red").css("pointer-events", "none")
    }
}

//Alteração de Alerts
const toggleAlert = (silgaToggle, action) => {

    if(silgaToggle == "G" && action == "A"){
        $("label[for=btn-alert-yellow]").css("pointer-events", "fill")
        $("label[for=btn-alert-red]").css("pointer-events", "fill")
        $("#textarea-alert").css("pointer-events", "fill")
        $("#div-alerts").css("background-color", "rgb(255, 255, 255)")
        $("#btn-alert-on-off").prop('checked', true);
    } else if (silgaToggle == "G" && action == "I") {
        $("#div-alerts").css("background-color", "#ebebeb")
        $("#textarea-alert").css("pointer-events", "none")
        $("label[for=btn-alert-yellow]").css("pointer-events", "none")
        $("label[for=btn-alert-red]").css("pointer-events", "none")
        $(".input-dates-alert").css("pointer-events", "none")
        $(".input-dates-alert").css("border", "1px solid #ccc")
        $("#btn-alert-on-off").prop('checked', false);
    } 

    if(silgaToggle == "Y" && action == "A"){
        $("#btn-alert-yellow").prop('checked', true);
        toggleDateAlert(silgaToggle,action)
    } else if (silgaToggle == "Y" && action == "I") {
        $("#btn-alert-yellow").prop('checked', false);
        toggleDateAlert(silgaToggle,action)
    }

    if(silgaToggle == "R" && action == "A") {
        $("#btn-alert-red").prop('checked', true);
        toggleDateAlert(silgaToggle,action)
    } else if(silgaToggle == "R" && action == "I"){
        $("#btn-alert-red").prop('checked', false);
        toggleDateAlert(silgaToggle,action)
    }
}

const setAlerts = (data) => {

    if (data.alertYellowDate != null) {
        $("#dt-alert-yellow").val(data.alertYellowDate.substring(0, 10))
    }
    if (data.alertRedDate != null) {
        $("#dt-alert-red").val(data.alertRedDate.substring(0, 10))
    }
    if (data.flagAlert == 'A') {toggleAlert("G","A")
        if (data.flagAlertYellow == 'A') {toggleAlert("Y","A")} 
        else {toggleAlert("Y","I")}
        if (data.flagAlertRed == "A") {toggleAlert("R","A")} 
        else {toggleAlert("R","I")}} 
    else {toggleAlert("G","I")
        if (data.flagAlertYellow == 'A') {$("#btn-alert-yellow").prop('checked', true)}
        else {$("#btn-alert-yellow").prop('checked', false)}
        if (data.flagAlertRed == "A") {$("#btn-alert-red").prop('checked', true)}
        else {$("#btn-alert-red").prop('checked', false)}}
    $("#textarea-alert").val(data.descAlert);
}

const carregaDetailTask = () => {

    const url = window.location.href;
    let params = url.split('?');

    if (params[1].includes('idTask')) {
        $("#div-load").show();
        const tempIdTask = params[1].substring(7, params[1].length)

        axios.get('view-task-detail', {params: {id: tempIdTask}})
        .then(resp => {
            idTask = tempIdTask
            const data = resp.data

            //Carregando campos
            $("#title-task").text(data.name)
            $("#title-detail-task").text(`${data.name} - (${data.card.name})`)
            $("#title-card").text("Em - Card " + data.card.name)
            $("#btn-jira").attr("href", data.linkJira)
            $("#dt-ini-task").val(data.dateStart)
            $("#dt-fim-task").val(data.dateEnd)
            textDesc = data.description

            textDesc == null ? textDesc = "" : ""
            let text_final = generateDesc(textDesc,'<span class="txt-description-task">',4)
            $('#div-txt-desc').append(text_final)

            //Carregamento de Alerts
            setAlerts(data)

            //Carregamento de annotation
            axios.get('get-annotations', {params: {id: idTask}})
            .then(resp => {
                if (resp.data.length > 0) {
                    const dados = resp.data
                    dados.forEach(function (annotation_original, index) {
                        textDescannotation.set(annotation_original.id,annotation_original.description);
                        setAnnotation(annotation_original)
                    });
                } else {
                    $(".div-body-bloco-annotations").prepend(annotation_default)
                }
                setTimeout(() => {
                    $("#div-load").hide();
                }, 500);
            })
            .catch((error) => {
                $("#div-load").hide();
                alert(error)
            })
        })
        .catch((error) => {
            $("#div-load").hide();
            console.log(error);
        })
    } else {
        $("#div-load").hide();
        alert('ERROR - Parametros Inválidos')
    }
}

/*      ### Screen Startup ###   */

carregaDetailTask()

/*      ### Request Functions ###   */

const createAnnotation = (description) => {
    axios.post('create-annotation', {
        description: description,
        task: {id: idTask}
    }).then(resp => {
        let dados = resp.data

        $("#btn-new-annotation-confirm").parents().eq(2).remove()
        textDescannotation.set(dados.id,dados.description)
        setAnnotation(dados,"P")        
        // text_final = generateDesc(dados.description,'<span class="txt-description-annotation">',15)
        // let annotation_html = annotation.replace('DESCRICAO', text_final)
        // annotation_html = annotation_html.replace('DATA-ANNOTATION', formataData(new Date(dados.date.substring(0, 10))))
        // $(".div-body-bloco-annotations").prepend(annotation_html)
        // $(".div-body-bloco-annotations").children().eq(0).attr("value", dados.id)
    }).catch(responseError)
}

const deleteAnnotation = (annotation) => {
    axios.delete('delete-annotation', {params: {id: annotation.parents().eq(2).attr("value")}})
    .then(() => {
        annotation.parents().eq(2).remove()
        $('.div-body-bloco-annotations').children().length == 0 ? $('.div-body-bloco-annotations').prepend(annotation_default) : ""
    })
    .catch(responseError)
}

//Classe de estilo para focus da anotacao
const select_annotation = element => {element.addClass("selected_annotation")}
const unselect_annotation = element => {element.removeClass("selected_annotation")}

/*      ### Listeners and Actions ###   */

$(document).on("click", ".btn-on-off", function (event) {
    
    let typeAlert = $(this).attr('name')
    let statusToggleBd = toggleMiniMsg($(this), event)

    const alertYellowDate = new Date($("#dt-alert-yellow").val() + " ")
    const alertRedDate = new Date($("#dt-alert-red").val() + " ")

    if (typeAlert == "flagAlert") {
        toggleAlert("G",statusToggleBd)
        axios.post('update-task', {
            id: idTask,
            flagAlert: statusToggleBd,
            alertYellowDate: alertYellowDate,
            alertRedDate: alertRedDate
        }).then(resp => {
            if (resp.data.flagAlertYellow == 'A' && statusToggleBd == "A") {toggleDateAlert("Y","A")}
            else {toggleDateAlert("Y","I")}
            if (resp.data.flagAlertRed == 'A' && statusToggleBd == "A") {toggleDateAlert("R","A")}
            else {toggleDateAlert("R","I")}
        })
        .catch(responseError)
    } else if (typeAlert == "flagAlertYellow") {
        toggleDateAlert("Y",statusToggleBd)
        axios.post('update-task', {
            id: idTask,
            flagAlertYellow: statusToggleBd,
            alertYellowDate: alertYellowDate,
            alertRedDate: alertRedDate
        })
        .catch(responseError)
    }
    else if (typeAlert == "flagAlertRed") {

        toggleDateAlert("R",statusToggleBd)
        axios.post('update-task', {
            id: idTask,
            flagAlertRed: statusToggleBd,
            alertYellowDate: alertYellowDate,
            alertRedDate: alertRedDate
        })
        .catch(responseError)
    }
})

$(document).on("click", "#btn-edit-desc", function () {
    $(this).hide("slow");
    $("#btn-edit-desc-confirm").show("slow");
    $("#div-txt-desc").hide("slow")
    $("#textarea-desc").val(textDesc)
    $("#textarea-desc").show("slow")
    $("#btn-edit-desc-cancel").show("slow");
})

$(document).on("click", "#btn-edit-desc-cancel", function () {
    $(this).hide("slow")
    $("#btn-edit-desc-confirm").hide("slow")
    $("#btn-edit-desc").show("slow")
    $("#div-txt-desc").show("slow")
    $("#textarea-desc").hide("slow")
    $("#textarea-desc").text("")
})

$(document).on("click", "#btn-edit-desc-confirm", function () {

    axios.post('update-task', {
        id: idTask,
        description: $("#textarea-desc").val()
    }).then(() => {
        $(this).hide("slow")
        $("#btn-edit-desc-cancel").hide("slow")
        $("#btn-edit-desc").show("slow")
        $('#div-txt-desc').children().remove()
        textDesc = $("#textarea-desc").val()
        
        let text_final = generateDesc(textDesc,'<span class="txt-description-task">',4)
        $('#div-txt-desc').append(text_final)
        $("#div-txt-desc").show("slow")
        $("#textarea-desc").hide("slow")
    })
    .catch(responseError)
})

$(document).on("click", ".input-dates-task", function () {
    dateIni = $("#dt-ini-task").val();
    dateFim = $("#dt-fim-task").val();
    $(this).css("border", "1px solid rgb(41, 189, 194)")
})

$(document).on("blur", ".input-dates-task", function () {

    const dateIniTask = $("#dt-ini-task").val()
    const dateFimTask = $("#dt-fim-task").val()
    const dateAlertYellow = $("#dt-alert-yellow").val()
    const dateAlertRed = $("#dt-alert-red").val()

    const validateDates = () => {
        if ((dateIniTask != "" && dateAlertYellow != "" && dateIniTask > dateAlertYellow) ||
        (dateIniTask != "" && dateAlertRed != "" && dateIniTask > dateAlertRed)) {
            alert("A data inicial não pode ser selecionada, pois existe um alerta com data anterior a data inicial especificada")
            $("#dt-ini-task").val(dateIni)
            return false;
        } else if ((dateFimTask != "" && dateAlertYellow != "" && dateFimTask < dateAlertYellow) ||
            (dateFimTask != "" && dateAlertRed != "" && dateFimTask < dateAlertRed)) {
            alert("A data inicial não pode ser selecionada, pois existe um alerta com data posterior a data final especificada")
            $("#dt-fim-task").val(dateFim)
        } else if (dateIniTask != "" && dateFimTask != "" && dateIniTask >= dateFimTask) {
            alert("A data final não pode ser menor que a data inicial")
            return false;
        } else {
            return true;
        }
    }

    if(validateDates()) {
        axios.post('update-task', {
            id: idTask,
            dateStart: new Date(dateIniTask + " "),
            dateEnd: new Date(dateFimTask + " ")
        }).then(resp => {
            $(this).css("border", "1px solid #ccc")
            $("#dt-ini-task").val(resp.data.dateStart)
            $("#dt-fim-task").val(resp.data.dateEnd)
        })
        .catch(responseError)
    }
})

$(document).on("click", "#btn-jira", function (event) {
    if (!event.ctrlKey) {
        event.preventDefault()
        $("#input-link-jira").val($(this).attr("href"))
        $("#div-link-jira").show("slow")
        setTimeout(() => {
            $("#input-link-jira").focus()
        }, 1000);

    } else {
        if ($(this).attr("href") == "") {
            alert("Nenhum link Jira foi definido")
            event.preventDefault()
        }
    }
})

$(document).on("blur", "#input-link-jira", function () {
    setTimeout(() => {
        if (!$("#btn-confirm-jira").is(":focus")) {
            $("#div-link-jira").hide("slow")
        }
    }, 200);
})

$(document).on("click", "#btn-confirm-jira", function () {
    axios.post('update-task', {
        id: idTask,
        linkJira: $("#input-link-jira").val()
    }).then(() => {
        $("#btn-jira").attr("href", $("#input-link-jira").val())
        $("#div-link-jira").hide("slow")
    })
    .catch(responseError)
})

$(document).on("blur", "#textarea-alert", function () {
    axios.post('update-task', {
        id: idTask,
        descAlert: $("#textarea-alert").val()
    }).catch(responseError)
})

$(document).on("blur", ".input-dates-alert", function () {
    let statusToggleBd;
    const restoreDate = () => {
        if ($(this).attr("id").includes("yellow")) {
            $("#dt-alert-yellow").val(dateAlertYellow);
        } else if ($(this).attr("id").includes("red")) {
            $("#dt-alert-red").val(dateAlertRed);
        }
    }
    const dateAlertYellow = $("#dt-alert-yellow").val();
    const dateAlertRed = $("#dt-alert-red").val();

    if ((dateAlertYellow != "" && dateAlertRed != "") && (dateAlertYellow >= dateAlertRed)) {
        alert("A data do alerta amarelo não pode ser >= que a data do alerta vermelho")
        restoreDate()
    } else {
        if ($(this).attr("id").includes("yellow")) {
            const statusToggle = $("#btn-alert-yellow").is(':checked')
            if (statusToggle) {statusToggleBd = "A"}
            else {statusToggleBd = "I"}

            axios.post('update-task', {
                id: idTask,
                flagAlertYellow: statusToggleBd,
                alertYellowDate: new Date($(this).val() + " ")
            }).catch(responseError)
        } else if ($(this).attr("id").includes("red")) {

            const statusToggle = $("#btn-alert-red").is(':checked')
            let statusToggleBd;

            if (statusToggle) {statusToggleBd = "A"}
            else {statusToggleBd = "I"}

            axios.post('update-task', {
                id: idTask,
                flagAlertRed: statusToggleBd,
                alertRedDate: new Date($(this).val() + " ")
            })
            .catch(responseError)   
        }
    }
})

$(document).on("click", ".input-dates-alert", function () {
    if ($(this).attr("id").includes("yellow")) {
        dateAlertYellow = $("#dt-alert-yellow").val();
    } else if ($(this).attr("id").includes("red")) {
        dateAlertRed = $("#dt-alert-red").val();
    }
})

$(document).on("click", "[name=btn-edit-annotation]", function () {
    annotation_oppening = true;
    $(this).hide("slow")
    $(this).siblings().eq(1).show("slow")
    $(this).siblings().eq(2).show("slow")
    $(this).siblings().eq(0).hide("slow")

    const idAnnotation =  $(this).parents().eq(2).attr("value")
    const textarea = $(this).parents().eq(1).siblings().eq(1).children().last();
    const span_annotation = $(this).parents().eq(1).siblings().eq(1).children()

    for (let [key, value] of textDescannotation) {
        if(key == idAnnotation) {
            textarea.val(value)
        }
    }
    span_annotation.hide("slow")
    setTimeout(() => {
        textarea.show("slow")
    }, 600)
})

$(document).on("click", "[name=btn-edit-annotation-cancel]", function (event) {
    annotation_oppening = false;
    $(this).hide("slow")
    $(this).siblings().eq(2).hide("slow")

    const span_annotation = $(this).parents().eq(1).siblings().eq(1).children("span")
    const textarea = $(this).parents().eq(1).siblings().eq(1).children().last()

    textarea.hide("slow")
    setTimeout(() => {
        span_annotation.show("slow")
        textarea.val("")
    }, 600)
})

$(document).on("click", "[name=btn-edit-annotation-confirm]", function (event) {

    const annotation = $(this).parents().eq(2)
    const textarea = annotation.children().eq(1).children().last()

    if (textarea.val() == "") {
        let mini__msg = mini_msg.replace("MINI_MSG", "Digite Algo")
        actionMiniMsg(mini__msg, 2000,event)
    } else {
        axios.post('update-annotation', {
            id: annotation.attr('value'),
            description: textarea.val()
        }).then(resp => {
            let dados = resp.data
            for (let [key] of textDescannotation) {
                if(key == dados.id) {
                    textDescannotation.set(key, dados.description)
                }
            }
            annotation.children().eq(1).children("span").remove()
            let text_final = generateDesc(dados.description,'<span class="txt-description-annotation">', 15)

            $(this).hide("slow")
            $(this).siblings().eq(2).hide("slow")
            $(this).siblings().eq(1).show("slow")
            $(this).siblings().eq(0).show("slow")

            textarea.hide("slow")
            setTimeout(() => {
                annotation.children().eq(1).prepend(text_final)
                textarea.val("")
            }, 600)
            annotation_oppening = false;
        }).catch(responseError)
    }
})

$(document).on("click", "#btn-add-annotation", function () {
    //Verificando se já está ativo
    if ($('#textarea-new-annotation').attr("id") == null) {
        let nova_anotacao = new_annotation.replace("DATE-NEW-ANNOTATION", formataData(new Date()))
        $("#div-annotation-default") ? $("#div-annotation-default").remove() : ""
        $(this).parents().eq(1).siblings().eq(0).prepend(nova_anotacao)
    }
})

$(document).on("click", "#btn-new-annotation-cancel", function () {
    $(this).parents().eq(2).remove()
    $('.div-body-bloco-annotations').children().length == 0 ? $('.div-body-bloco-annotations').prepend(annotation_default) : ""
})

$(document).on("click", "#btn-new-annotation-confirm", function (event) {

    if ($("#textarea-new-annotation").val() == "") {
        let mini__msg = mini_msg.replace("MINI_MSG", "Digite Algo")
        actionMiniMsg(mini__msg, 2000,event)
    } else {
        createAnnotation($("#textarea-new-annotation").val())
    }
})

$(document).on("click", "[name=btn-remove-annotation]", function () {
    if (confirm("Tem certeza que quer excluir essa anotação?")) {deleteAnnotation($(this))}
})

$(document).on("mouseover", ".sub-body-annotation", function () {
    if (!$("[name=textarea-annotation]").is(":visible") && !annotation_oppening) {
        select_annotation($(this))
        let buttons_annotation = $(this).children().eq(2).children().children()
        buttons_annotation.eq(0).show()
        buttons_annotation.eq(1).show()
    }
})

$(document).on("mouseout", ".sub-body-annotation", function () {
    if (!$("[name=textarea-annotation]").is(":visible") && !annotation_oppening) {
        unselect_annotation($(this))
        let buttons_annotation = $(this).children().eq(2).children().children()
        buttons_annotation.hide();
    }
})

$(document).on("dblclick", "#title-task", function (event) {
    $(this).siblings().eq(0).val($(this).text())
    $(this).hide()
    $(this).siblings().eq(0).show()

    setTimeout(() => {
        $(this).siblings().eq(0).focus()
    }, 200);
})

$(document).on("keyup", "#input-title-task", function (event) {

    var key = event.which || event.keyCode;
    if (key == 13) {

        if ($(this).val() != null && $(this).val().length <= 40) {
            if($(this).val() != null){
                axios.post('update-task', {
                    id: idTask,
                    name: $(this).val()
                }).then(resp => {
                    const data = resp.data
        
                    $(this).hide()
                    $(this).siblings().eq(0).text(data.name)
                    $(this).siblings().eq(0).show()
                    $(this).val('')
                })
                .catch(responseError)
            }
        } else {
            alert('Digite um título com até 40 caracteres')
        }
    }
})