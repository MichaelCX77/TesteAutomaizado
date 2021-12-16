/*      ### Statements ###   */

let cardDefaultFlag = false;
let preferences = {};

const toDetailTask = idTask => window.location = '/detail-task?idTask=' + idTask

/*      ### Main Functions ###   */

const verifyLengthCards = () => {
    $('.cards').each(function (index,element) {
        $(element).children().length < 5 ? $(element).css("height", "200px") : $(element).css("height", "fit-content")
    })
}
const setColorAlerts = () => {

    const compara_ids = (id1, id2) => {
        return new Promise((resolve) => {
            id1 == id2 ? resolve(true) : ""
        })
    }
    const setColor = (btn, task) => {

        const verificaData = (data) => {
            if (data == null) {
                return false
            }
            const dataAtual = new Date()
            const dataAlert = new Date(data.substring(0, 10))
            if (dataAtual >= dataAlert) {
                return true
            }
            return false
        }

        const setGreen = (btn) => {
            btn.removeClass("background_yellow background_red")
            btn.addClass("background_green")
        }

        if (task.flagAlert == "A") {
            let alertActive = false;
            if (task.flagAlertRed == "A" && verificaData(task.alertRedDate)) {
                btn.removeClass("background_yellow background_green")
                btn.addClass("background_red")
                alertActive = true;
            }
            if (task.flagAlertYellow == "A" && !alertActive && verificaData(task.alertYellowDate)) {
                btn.removeClass("background_green background_red")
                btn.addClass("background_yellow")
                alertActive = true;
            }
            if (!alertActive) {
                setGreen(btn)
            }
        } else {
            setGreen(btn)
        }
    }
    axios.get('get-listTasks')
    .then(resp => {
        const data = resp.data
        $(".task").each(function (index, element) {
            let elemento = $(element)
            let btn = elemento.children().eq(6)
            data.forEach(task => {
                compara_ids(task.id, elemento.attr("value"))
                .then(is_task => {
                    setColor(btn, task)
                });
            })
        })
        verifyLengthCards()
        setTimeout(() => {
            $("#div-load").hide();
        }, 500);
    })
    .catch((error) => {
        alert(error);
    })
}

const getPreferences = () => {
    axios.get('preferences')
    .then(resp => {
        preferences.refresh = resp.data.autoRefresh
        resp.data.autoRefresh == 'A' ? $("#check-auto-refresh").prop('checked', true) : ""
    })
    .catch((error) => {
        alert("Erro ao carregar preferências: " + error);
    })
}

const carregaCards = () => {
    $("#div-load").show();
    axios.get('get-nameUser').then(resp => {
        let nomeUser = resp.data
        if (nomeUser.includes(' ')) {
            nomeUser = nomeUser.substring(0, nomeUser.indexOf(' '))
        }
        $("#div-welcome").append(`<span>Bem-Vindo(a) ao PGA, ${nomeUser}!</span>`)
    })

    axios.get('get-cards').then(resp => {

        const dados = resp.data
        dados.forEach(function (element, index) {
            // Carrega Cards
            const header__card = header_card.replace('NOME-CARD', element.name)
            $('#row-cards').append(card)
            $('#row-cards').children().eq(index).attr("value", element.id);
            $('#row-cards').children().eq(index).append(header__card);

            axios.get('get-tasks', {params: {id: element.id}})
            .then(resp => {
                if (resp.data.length > 0) {
                    const dados = resp.data
                    // Carrega Tasks
                    dados.forEach(function (element, index2) {
                        let body__task = task.replace('<p>NOME-TASK</p>', `<p title="${element.name}">${limite_title_task(element.name)}</p>`)

                        body__task = body__task.replaceAll('LINK-TASK', element.id)
                        $('#row-cards').children().eq(index).append(body__task);
                        $('#row-cards').children().eq(index).children().eq(index2 + 1).attr("value", element.id);
                    })
                } else {
                    $('#row-cards').children().eq(index).append(model_card_default);
                }
            })
            .catch((error) => {
                console.log(error);
            })
        });
        setColorAlerts()
        $('#row-cards').append(create_div_new_card_add)
        getPreferences()
    })
    .catch((error) => {
        alert(error);
    })
}

/*      ### Screen Startup ###   */

carregaCards()

/*      ### Request Functions ###   */

const createCard = (event) =>{
    event.preventDefault();
    axios.post('create-card', {name: $("#input-card-name").val()})
    .then(resp => {
        $('#div-new-card-define-name').remove()
        $('#div-cancel-new-card').remove()

        const dados = resp.data
        const header__card = header_card.replace('NOME-CARD', dados.name)

        $('#row-cards').append(card)
        $('#row-cards').children().last().append(header__card);
        $('#row-cards').children().last().attr("value", dados.id)
        $('#row-cards').children().last().append(model_card_default)
        $('#row-cards').append(create_div_new_card_add)

    })
    .catch((error) => {
        alert(error);
    })
}

const createTask = () => {
    let inputTaskName = $('#input-task-name')
    axios.post('create-task', {
        name: inputTaskName.val(),
        card: {
            id: inputTaskName.parents("div").eq(2).attr("value")
        }
    }).then(resp => {
        const dados = resp.data
        const current_card = inputTaskName.parents("div").eq(2);

        try {inputTaskName.parents("div").eq(1).remove()} catch (error) {}

        let task_html = task.replace('NOME-TASK', limite_title_task(dados.name))
        task_html = task_html.replaceAll('LINK-TASK', dados.id)
        current_card.append(task_html);
        current_card.children().last().attr("value", dados.id);
        setColorAlerts()
    })
    .catch((error) => {
        alert(error);
    })
}

const excludeTask = (task) => {
    axios.delete('remove-task', {params: {id: task.attr("value")}})
    .then(() => {
        const current_card = task.parent();
        task.remove();
        current_card.children().length == 1 ? current_card.append(model_card_default) : ""
        verifyLengthCards()
    })
    .catch((error) => {
        alert(error);
    })
}

const removeCard = (card) => {
    axios.delete('remove-card', {params: {id: card.attr("value")}})
    .then(() => {
        card.remove();
    })
    .catch((error) => {
        alert(error);
    })
}

/*      ### Aux Functions ###   */

const limite_title_task = name => {
    if (name.length > 31) {
        let title_limited = name.substring(0, 32)
        return title_limited
    }
    return name
}
const toggleToolsTask = (btnArrow) => {

    const openToolsTask = (btnArrow) =>{
        $(btnArrow).empty()
        $(btnArrow).prepend(img_arrox_left)
        $(btnArrow).prop('name', 'btn-arrow-left')
        $(btnArrow).siblings('.title-task').hide();
        $(btnArrow).siblings('.btn-task-action').show();
    }
    
    const closeToolsTask = (btnArrow) =>{
        $(btnArrow).empty()
        $(btnArrow).prepend(img_arrox_down)
        $(btnArrow).prop('name', 'btn-arrow-card');
        $(btnArrow).siblings('.btn-task-action').hide();
        $(btnArrow).siblings('.title-task').show();
    }

    const name = btnArrow.attr('name')
    if (name == 'btn-arrow-card') {
        openToolsTask(btnArrow)
    } else if (name == 'btn-arrow-left') {
        closeToolsTask(btnArrow)
    }
}
const createNewCard = () =>{
    $('#div-new-card').remove()
    $('#row-cards').append(create_new_card_define_name)
}
const cancelNewCard = () =>{
    $('#div-cancel-new-card').remove()
    $('#div-new-card-define-name').remove()
    $('#row-cards').append(create_div_new_card_add)
}

const addTask = (btnAddTask) => {
    const card_default = btnAddTask.parents("div").eq(1).children().last().attr('name')

    if (card_default == "body-card-default") {
        btnAddTask.parents("div").eq(1).children().last().remove()
        cardDefaultFlag = true
    } else {
        cardDefaultFlag = false
    }
    // Impede a inclusão de duas tarefas ao mesmo tempo
    $('[name=div-add-task]').remove()

    btnAddTask.parents("div").eq(1).append(new_task)
    $("#input-task-name").focus()
}

// const viewTask = (task,event) => {
//     event.preventDefault();
//     let isInputTask = task.children().eq(0).children().attr("id") == "input-task-name"
//     if (!isInputTask) {
//         window.location = `detail-task?idTask=${task.attr("value")}`
//     }
// }

const hideInputAddTask = (card_atual) => {
    $('[name=div-add-task]').remove()
    if (cardDefaultFlag) {card_atual.append(model_card_default)}
}

/*      ### Listeners and Actions ###   */

$(document).on("click", ".btn-arrow-right", function () {toggleToolsTask($(this))})
$(document).on("click", "#div-new-card", function () {createNewCard()})
$(document).on("click", "#btn-cancel-new-card", function () {cancelNewCard()})  
$(document).on("click", "[name=btn-add-task]", function () {addTask($(this))})
// $(document).on("click", ".title-task", function (event) {viewTask($(this).parent(),event)})
$(document).on("blur", ".btn-task-action", function () {toggleToolsTask($(this).siblings().last())})
$(document).on("blur", "#input-task-name", function () {hideInputAddTask($(this).parents("div").eq(2))})

$(document).on("click", "#btn-create-card", function (event) {
    const nome = $('#input-card-name').val()
    if (nome == "") {
        alert("O campo não pode estar vazio");
    } else if (nome.length > 18) {
        alert("Descreva um título com até 18 caracteres");
    } else {
        createCard(event)
    }
});

$(document).on("keyup", "#input-card-name", function (event) {

    var key = event.which || event.keyCode;
    if (key == 13) {

        const nome = $(this).val()
        if (nome == "") {
            alert("O campo não pode estar vazio");
        } else if (nome.length > 18) {
            alert("Descreva um título com até 18 caracteres");
        } else {
            createCard(event)
        }
    }
});

$(document).on("blur", "[name=btn-arrow-left]", function () {
    setTimeout(() => {
            if (!$(".btn-task-action").is(":focus")) {
                toggleToolsTask($(this))
            }
    }, 200);
});

$(document).on("keyup", "#input-task-name", function (event) {
    var key = event.which || event.keyCode;
    if (key == 13) {
        if ($('#input-task-name').val().length <= 40) {
            cardDefaultFlag = false;
            createTask()
        } else {
            alert('Digite um título com até 40 caracteres')
        }
    }
})

$(document).on("click", "[name=btn-task-exclude]", function () {
    if (confirm("Tem certeza que deseja remover esta tarefa?")) {excludeTask($(this).parent())}
})

$(document).on("click", "[name=btn-remove-card]", function () {
    if (confirm("Tem certeza que deseja remover esse card?")) {removeCard($(this).parents().eq(1))}
})


$(document).on('click','[name=btn-task-detail]', function(event){
    toDetailTask($(this).children('a').attr('value'))
})

$(document).on('click','a.title-task', function(event){
    toDetailTask($(this).attr('value'))
})

/*      ### Alert Updater ###   */

setInterval(() => {
    if(preferences.refresh == 'A'){
        setColorAlerts()
    }
}, 30000);

/*  Drag and Drop */


//  Rolagem Drag
let stopScrollDrag;

$(document).on("drag", ".task", function (event) {
    
    stopScrollDrag = true;

    if (event.originalEvent.clientY < 150) {
        stopScrollDrag = false;
        scroll(-1)
    }

    if (event.originalEvent.clientY > ($(window).height() - 150)) {
        stopScrollDrag = false;
        scroll(1)
    }
})

const scroll = function (step) {
    let scrollY = $(window).scrollTop();
    $(window).scrollTop(scrollY + step);
    if (!stopScrollDrag) {
        setTimeout(function () { scroll(step) }, 20);
    }
}

//  Eventos Drag And Drop

$(document).on("dragstart", ".task", function (event) {
    $(this).addClass('task-move')
    $(this).find('a.title-task').css('border','4px solid #daffe5');
    $(this).find('a.title-task').css('background-color','#daffe5');
    $(this).find('[name=btn-arrow-card]').css('border','4px solid #daffe5');
    event.originalEvent.dataTransfer.setData("TASK-ID", $(this).attr('value'));
})

$(document).on("drop", ".cards", function (event) {
    event.preventDefault();

    if($(this).attr('id') == undefined){
        const card_default = $(this).children().eq(1)
        if(card_default.attr('name') == "body-card-default"){
            card_default.remove()
        }
        const idTask = event.originalEvent.dataTransfer.getData("TASK-ID");
        // console.log(idTask)
        let task_anterior = $(`.task[value=${idTask}]`)
        let card_anterior = task_anterior.parent()

        $(this).append(task_anterior);
        card_anterior.children().length == 1 ? card_anterior.append(model_card_default) : ""

        axios.post('update-card', {
            id: idTask,
            card: {
                id: $(this).attr('value')
            }
        })
        .then(resp => {
            resp.data
        })
        .catch((error) => {
            alert(error);
        })
    }
})

$(document).on('dragend','.task', function(event){

    stopScrollDrag = true;
    $(this).removeClass('task-move')
    $(this).find('a.title-task').css('border','4px solid #ffffff')
    $(this).find('a.title-task').css('background-color','#ffffff');
    $(this).find('[name=btn-arrow-card]').css('border','4px solid #ffffff')
    $('.cards').removeClass('shadow-green')
    $('.cards').removeClass('shadow-red')
    verifyLengthCards()
})

$(document).on('dragover','.cards', function(event){
    event.preventDefault();
    if($(this).attr('id') == undefined){
        $(this).addClass('shadow-green')
    } else {
        $(this).addClass('shadow-red')
    }
})

$(document).on('dragleave','.cards', function(event){
    event.preventDefault();
    if($(this).attr('id') == undefined){
        $(this).removeClass('shadow-green')
    } else {
        $(this).removeClass('shadow-red')
    }
})

$(document).on("dblclick", ".title-card", function (event) {

    let input_card = $(this).siblings('input.input-title-card')
    
    input_card.val($(this).text())
    $(this).hide()
    input_card.show()

    setTimeout(() => {
        input_card.focus()
    }, 200);
})

$(document).on("keyup", ".input-title-card", function (event) {

    var key = event.which || event.keyCode;
    if (key == 13) {

        if ($(this).val() != null && $(this).val().length <= 18) {
            if($(this).val() != null){
                axios.post('update-card2', {
                    id: $(this).parents('div').eq(1).attr('value'),
                    name: $(this).val()
                }).then(resp => {
                    const data = resp.data
                    let title_card = $(this).siblings('span.title-card')
                    
                    $(this).val('')
                    $(this).hide()
                    title_card.text(data.name)
                    title_card.show()
                })
                .catch(responseError)
            }
        } else {
            alert('Digite um título com até 18 caracteres')
        }
    }
})

$(document).on('click','#check-auto-refresh', function (event) {

    const status = $(this).is(':checked') ? 'A' : 'I'
    preferences.refresh = status

    axios.post('update-preferences', {
        autoRefresh: status
    })
    .then()
    .catch((error) => {
        alert(error);
    })
})