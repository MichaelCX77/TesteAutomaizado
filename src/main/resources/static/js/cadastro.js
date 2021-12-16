$(document).on("click","#btn-cadastrar", function(event) {
    event.preventDefault();

    if($("#input-name").val() == "" || $("#input-email").val() == "" || $("#input-re").val() == "" || !$("#input-email").val().includes("@") || !$("#input-email").val().includes(".com")){
        alert("Verifique os campos");
    } else if($("#input-re").val().length > 6) {
        alert("O RE deve conter até 6 dígitos");
    } else {
        $("#div-load").show();
        axios.post('cadastrar', {
            name: $("#input-name").val(),
            email: $("#input-email").val(),
            registry: $("#input-re").val(),
            date: new Date()
        }).then(resp => {
            $("#div-load").hide();
            alert(resp.data);
            window.location = "/index"
        })
        .catch((error) => {
            $("#div-load").hide();
            alert(error.response.data);
        })
    }
})