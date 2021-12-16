const version = $('#input-version').val();

if(version == "minorVersion"){
    alert('Existe uma nova versão disponível, atualize para a versão mais recente para continuar utilizando a aplicação')
    axios.get('redirect-to-update-page')
    .catch((error) => {
        alert(error);
    })

}

$('#system-version').text('Version ' + $('#input-version').val())