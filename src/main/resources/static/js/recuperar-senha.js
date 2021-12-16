$(document).on("click","#btn-send", function(event) {
    event.preventDefault();
    if($("#input-email").val() == "" || !$("#input-email").val().includes("@") || !$("#input-email").val().includes(".com")){
        alert("Verifique os campos");
    } else {
        $("#div-load").show();
        axios.post('send-recover-password', {
            email: $("#input-email").val()
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