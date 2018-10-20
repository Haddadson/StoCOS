$(document).ready(() => {
    $('#filtrarDiv').hide();
    montarLista();

    // Tenta realizar o cadastro
    $('#confirmar').click(() => {
        let nome = $('#nome').val();
        let capacidade = $('#capacidade').val();

        if (nome != '' && capacidade > 0) {

        }
    });

    // Filtra os itens da tabela
    $("#filtrar").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $("#listaRedes div").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

});

// Monta a lista de redes cadastradas
function montarLista() {
    $('#listaRedes').html('<div class="list-group-item text-secondary">Conectando...</div>');
    var lista = $("#listaRedes");
    var redesCadastradas = [];
    $.get("http://localhost:4567/redecosmeticos", (data) => {
        if (data) {
            if (data.status == 'OK') {
                if (data.resultados.length > 0) {
                    $('#listaRedes').empty();
                    for (var i = 0; i < data.resultados.length; i++) {
                        var rede = data.resultados[i];
                        console.log(i + '=' + rede.nome);
                        $('#listaRedes').append('<div class="list-group-item">' + rede.nome + '</div>');
                    }
                    $('#filtrarDiv').show();
                } else {
                    $('#listaRedes').html('<div class="list-group-item text-secondary">Nenhuma rede Cadastrada.</div>');
                }
            } else {
                $('#listaRedes').html('<div class="list-group-item text-danger">Erro - Status: ' + data.status + '</div>');
            }
        } else {
            $('#listaRedes').html('<div class="list-group-item text-danger">Não foi possível obter os dados.</div>');
        }
    }).done(function (data) {

    }).fail(function () {
        $('#listaRedes').html('<div class="list-group-item text-secondary">Erro ao comunicar com o servidor.</div>');
    }).always(function () {

    });

    // <div id="listaRedes" class="list-group"></div><div class="input-group"><div class="input-group-prepend"><span class="input-group-text" id="basic-addon1"><img src="assets/imgs/search.svg" width="25px"></span></div><input id="filtrar" onkeyup="filtrar()" type="text" class="form-control" placeholder="Filtrar"></div>
}

function atualizarStatus(id, cor, msg) {
    $(id).html(
        '<p class="text-' + cor + '">' + msg + '</p>'
    );
}
