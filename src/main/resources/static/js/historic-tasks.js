/*      ### Statements ###   */
let ArrayPages = [];
let ArrayFilterPages = [];
let actualPage;
let lengtPages;
let isFilter = false;
let haveTasks = true;

$(document).on("dblclick", "tbody tr", function (event) {
        window.location.href = $(this).children("td").children("a").attr("href")
})

/*      ### Aux Functions ###   */

const loadArrayPages = (task,index) => {

        let isNewPage = (index)%10 == 0
        let firstPageEnabled
        
        if(index == 0){
                firstPageEnabled = ""
                $('#pg_anterior').removeAttr('href') 
                actualPage = 1
        } else {
                firstPageEnabled = "href=\"&nbsp;\""
        }

        isNewPage ? $('#div-pages').append(`<span><a ${firstPageEnabled} name="page">${Math.ceil((index+1)/10)}</a></span>`) : ""

        const objectTask = {
                page: Math.ceil((index+1)/10),
                task
        }
        if(isFilter){
                ArrayFilterPages.push(objectTask)
        } else {
                ArrayPages.push(objectTask)
        }
}
const setLineTableHistoric = (task, index) => {

        let lineHistoric = line_table_historic;
        lineHistoric = lineHistoric.replace('ID_TASK', task.id)
        lineHistoric = lineHistoric.replace('CARD_NAME', task.card.name)
        lineHistoric = lineHistoric.replace('CARD_INI', formataData(new Date(task.card.date.substring(0, 10) +  " ")))
        let url = window.location.href
        const prefix = url.substring(0, url.lastIndexOf("/"))
        lineHistoric = lineHistoric.replace('LINK-TASK', prefix + '/detail-task?idTask=' + task.id)
        lineHistoric = lineHistoric.replace('NAME-TASK', task.name)
        lineHistoric = lineHistoric.replace('TASK-INI', task.dateStart == null ? '--/--/--' : formataData(new Date(task.dateStart)))
        lineHistoric = lineHistoric.replace('TASK-END', task.dateEnd == null ? '--/--/--' : formataData(new Date(task.dateEnd)))
        
        task.description == null ? task.description = " " : ""

        let task_desc = task.description.length > 70 ? task.description.substring(0,70)+"..." :  task.description
        lineHistoric = lineHistoric.replace('DESCRIPTION', task_desc)

        if(index < 10 || index == undefined){
                $('tbody').append(lineHistoric)
        }
}
const btnAlterPage = (page) => {
        actualPage = page
        if(actualPage == 1){
                $('#pg_anterior').removeAttr('href') 
        } else {
                $('#pg_anterior').attr('href',"&nbsp;")
        }
        if(actualPage == lengtPages){
                $('#pg_proxima').removeAttr('href')
        } else {
                $('#pg_proxima').attr('href',"&nbsp;")
        }
}

const loadNewPage = (newPage) => {
        $('tbody').children().remove()

        if(isFilter){
                ArrayFilterPages.forEach(function(element) {
                        element.page == newPage ? setLineTableHistoric(element.task) : ""
                });
        } else {
                ArrayPages.forEach(function(element) {
                        element.page == newPage ? setLineTableHistoric(element.task) : ""
                });
        }

        btnAlterPage(newPage)
}

$(document).on("click","#div-pages [name=page]",function (event) {
        event.preventDefault();
        let newPage = $(this).text()

        if(newPage != actualPage){
                loadNewPage(parseInt(newPage))
                $('#div-pages [name=page]').attr('href','&nbsp')
                $(this).removeAttr('href')
                btnAlterPage(newPage)
        }
})

$(document).on("click","#div-pages [name=btnAlterPag]", function (event) {
        event.preventDefault()
        let txtBtn = $(this).text()

        if(actualPage < lengtPages && txtBtn == "Próxima" || actualPage > 1 && txtBtn == "Anterior"){
                $('#div-pages [name=page]').attr('href','&nbsp')
                txtBtn == "Anterior" ? loadNewPage(parseInt(actualPage)-1) : loadNewPage(parseInt(actualPage)+1)
                $('#div-pages [name=page]').each(function (index,element) {
                        $(element).text() == actualPage ? $(element).removeAttr('href') : ""
                })
        }
})

$(document).on('click','#btn-search', function (event) {

        const type_search = $('#select-type-search').val()
        const text_search = $('#input-search').val().toLowerCase()
        if(type_search != "select" && text_search != "" && haveTasks){
                
                $('tbody').children().remove()
                let tempArrayFilterPages = [];
                isFilter = true;

                ArrayPages.forEach(element => {
                        if(type_search == "card" && element.task.card.name.toLowerCase().includes(text_search)){
                                tempArrayFilterPages.push(element.task)
                        } else if (type_search == "task" && element.task.name.toLowerCase().includes(text_search)){
                                tempArrayFilterPages.push(element.task)
                        } else if (type_search == "description" && element.task.description.toLowerCase().includes(text_search)) {
                                tempArrayFilterPages.push(element.task)
                        }
                });
                
                tempArrayFilterPages.forEach(setLineTableHistoric)
                ArrayFilterPages = []

                $('#pg_proxima').remove()
                $('#div-pages span').remove()
                $('#div-pages').append('<span><a href="&nbsp;" id="pg_anterior" name="btnAlterPag">Anterior</a></span>')
                tempArrayFilterPages.forEach(loadArrayPages)
                $('#div-pages').append('<span><a href="&nbsp;" id="pg_proxima" name="btnAlterPag">Próxima</a></span>')
                lengtPages = Math.ceil(tempArrayFilterPages.length/10)
                btnAlterPage(1)
        } else if(!haveTasks){
                alert('Você ainda não possui nenhum histórico')
        } else {
                alert('Selecione um tipo de pesquisa e descreva o texto a ser pesquisado')
        }
})

/*      ### Request Functions ###   */

const loadHistoric = () => {
        $("#div-load").show();
        axios.get('get-TasksHistoryList')
        .then(resp => { 
                const dados = resp.data;

                if(resp.data.length == 0){
                        $('#div-default-historic').show();
                        haveTasks = false
                } else {
                        dados.forEach(setLineTableHistoric)
                        $('#div-pages').append('<span><a href="&nbsp;" id="pg_anterior" name="btnAlterPag">Anterior</a></span>')
                        dados.forEach(loadArrayPages)
                        $('#div-pages').append('<span><a href="&nbsp;" id="pg_proxima" name="btnAlterPag">Próxima</a></span>')
                        lengtPages = Math.ceil(dados.length/10)
                        btnAlterPage(1)
                }
                setTimeout(() => {
                        $("#div-load").hide();
                    }, 500);
        })
        .catch((error) => {alert(error)})
}

loadHistoric()