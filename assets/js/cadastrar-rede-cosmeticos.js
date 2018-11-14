$(document).ready(() => {
    mascaraTelefone();
    $('#filtrarDiv').hide();
    montarLista();

    // Tenta realizar o cadastro
    $('#cadastrar').click(() => {
        let rede = new Object();
        rede.nome = $('#nome').val();
        rede.capacidade = parseInt($('#capacidade').val());
        rede.endereco = $('#endereco').val();
        rede.email = $('#email').val();
        rede.telefone = $('#telefone').val();
        $.ajax({
            type: 'POST',
            url: 'http://localhost:4567/redecosmeticos/add',
            data: JSON.stringify(rede),
            success: function (data) { alert('data: ' + data); },
            contentType: "text/plain; charset=utf-8",
            dataType: 'json'
        });
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
    $.get("http://localhost:4567/redecosmeticos/getall", (data) => {
        if (data) {
            if (data.length > 0) {
                $('#listaRedes').empty();
                for (var i = 0; i < data.length; i++) {
                    var rede = data[i];
                    console.log(rede);
                    $('#listaRedes').append('<div class="list-group-item">' + rede.nome + '</div>');
                }
                $('#filtrarDiv').show();
            } else {
                $('#listaRedes').html('<div class="list-group-item text-secondary">Nenhuma rede Cadastrada.</div>');
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

function mascaraTelefone() {
    $('#telefone').inputmask("(99)99999-9999", { autoclear: true });

    $("#telefone").on("blur", function () {
        var last = $(this).val().substr($(this).val().indexOf("-") + 1);

        if (last.length == 3) {
            var move = $(this).val().substr($(this).val().indexOf("-") - 1, 1);
            var lastfour = move + last;

            var first = $(this).val().substr(0, 9);

            $(this).val(first + '-' + lastfour);
        }
    });
}
