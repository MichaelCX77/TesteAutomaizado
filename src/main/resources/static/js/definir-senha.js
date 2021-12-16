let idUser = "";
let codeUser = "";

const returnToIndex = () => window.location.href = "index"

const verify = () => {
    const url = window.location.href;
    let parameters = url.substring(url.indexOf("?")+1, url.length);

    if (parameters.includes("id") && parameters.includes("code")) {

        let array_parameters = parameters.split("&");
        let id = array_parameters[0].substring(array_parameters[0].indexOf("=")+1, array_parameters[0].length);
        let code = array_parameters[1].substring(array_parameters[1].indexOf("=")+1, array_parameters[1].length);

        axios.get('verify-data', {
            params: {
                id: id,
                code: code,
                date: new Date()
            }
        }).then(() => {
            idUser = id
            codeUser = code
        }).catch((error) => {
            alert(error.response.data)
            returnToIndex()
        })
    } else {
        alert("Parâmetros Inválidos")
        returnToIndex()
    }
}

verify()

$(document).on('click',"#btn-redefine", function(event){
    event.preventDefault();
    if($("#input-password").val() == "" || $("#input-password").val().length < 8){
        alert("A senha não pode ser nula e deve conter no mínimo 8 caracteres")
    } else if($("#input-password").val() != $("#input-confirm-password").val()){
        alert("As senhas não correspondem")
    } else {
        axios.post('definir-senha', {
            code: codeUser,
            user: {
                id: idUser,
                password: $("#input-password").val()
            }
        }).then(resp => {
            alert(resp.data)
            returnToIndex()
        }).catch((error) => {
            alert(error.response.data)
            returnToIndex()
        })
    }
})