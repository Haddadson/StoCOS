$(document).ready(() => {
    $('#dadosExpedicao').hide();
    montarLista();

    // Filtra os itens da tabela
    $("#filtrar").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $("#listaRedes a").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

});

$(document).on('click', '#confirmarExpedicao', function (e) {
    let identificador = $('#identificadorLote').data('identificador');
    $.ajax({
        type: 'GET',
        url: "http://localhost:4567/lote/getById?id=" + identificador,
        success: function (data) {
            let lote = data[0];
            lote['status'] = 2;
            $.ajax({
                type: 'POST',
                url: 'http://localhost:4567/lote/update',
                data: JSON.stringify(lote),
                success: function (data) {
                    alert("Expedição confirmada com sucesso.");
                    location.reload();
                },
                contentType: "text/plain",
                dataType: 'json'
            });
        }
    });

});

$(document).on('click', '#cancelarExpedicao', function (e) {
    let identificador = $('#identificadorLote').data('identificador');
    $.ajax({
        type: 'GET',
        url: "http://localhost:4567/lote/getById?id=" + identificador,
        success: function (data) {
            let lote = data[0];
            lote['status'] = 3;
            $.ajax({
                type: 'POST',
                url: 'http://localhost:4567/lote/update',
                data: JSON.stringify(lote),
                success: function (data) {
                    alert("Cancelamento realizado com sucesso.");
                    location.reload();
                },
                contentType: "text/plain",
                dataType: 'json'
            });
        }
    });

});


$(document).on('click', '.list-group-item', function (e) {
    $('#dadosExpedicao').show()
    let id_lote = $(this).attr("data-id-lote");
    let id_rede = $(this).attr("data-id-rede");
    var rede;
    var lote;
    var prod;
    $.ajax({
        async: false,
        type: 'GET',
        url: "http://localhost:4567/redecosmeticos/getById?id=" + id_rede,
        success: function (data) {
            $('#nomeRede').html("<h5><div>Rede: " + data[0]['nome'] + "</div></h5>");
        }
    });
    $.ajax({
        async: false,
        type: 'GET',
        url: "http://localhost:4567/lote/getById?id=" + id_lote,
        success: function (data) {
            lote = data;
            $('#dataEntrega').html("<div>Data da Entrega prevista: " + data[0]['data-agendamento'] + "</div>");
            $('#dataValidade').html("<div>Data de Validade: " + data[0]['data-validade'] + "</div>");
            $('#quantidadeProd').html("<div>Quantidade: " + data[0]['quantidade'] + "</div>");
            $('#idLote').html('<div id="identificadorLote" data-identificador='
                + data[0]['id']
                + '>Identificador: '
                + data[0]['id']
                + '</div>');
        }
    });
    $.ajax({
        async: false,
        type: 'GET',
        url: "http://localhost:4567/produto/getById?id=" + lote[0]['id-produto'],
        success: function (data) {
            $('#nomeProd').html("<div>Produto: " + data[0]['nome'] + "</div>");

        }
    });

});

function criarDiv(id_rede, lote) {
    $.get("http://localhost:4567/redecosmeticos/getById?id=" + id_rede, (dados_rede) => {
        console.log(dados_rede)
        if (dados_rede) {
            $('#listaRedes').append('<a class="list-group-item list-group-item-action" ' +
                'id="list-home-list'
                + lote.id
                + '" data-toggle="list" href="#list-home" role="tab" aria-controls="home" data-id-lote='
                + lote.id
                + ' data-id-rede='
                + id_rede + '>'
                + dados_rede[0].nome
                + ": Data de Agendamento - "
                + lote['data-agendamento'] + '</a>');

        }
    }).done(function (data) {

    }).fail(function () {
        $('#listaRedes').html('<div class="list-group-item text-secondary">Erro ao comunicar com o servidor.</div>');
    }).always(function () {

    });
}

// Monta a lista de redes cadastradas
function montarLista() {
    $('#listaRedes').html('<div class="list-group-item text-secondary">Conectando...</div>');
    var lista = $("#listaRedes");
    var redesCadastradas = [];
    $.get("http://localhost:4567/lote/getall", (data) => {
        if (data) {
            if (data.length > 0) {
                $('#listaRedes').empty();
                for (var i = 0; i < data.length; i++) {
                    var lote = data[i];
                    if (lote['status'] == 1) {
                        var id_rede = lote['id-rede'];
                        criarDiv(id_rede, lote);
                    }
                }
                $('#filtrarDiv').show();
            } else {
                $('#listaRedes').html('<div class="list-group-item text-secondary">Nenhum lote cadastrado.</div>');
                $('#dadosExpedicao').empty();
            }
        } else {
            $('#listaRedes').html('<div class="list-group-item text-danger">Não foi possível obter os dados.</div>');
            $('#dadosExpedicao').empty();
        }
    }).done(function (data) {

    }).fail(function () {
        $('#listaRedes').html('<div class="list-group-item text-secondary">Erro ao comunicar com o servidor.</div>');
        $('#dadosExpedicao').empty();
    }).always(function () {

    });
}

function atualizarStatus(id, cor, msg) {
    $(id).html(
        '<p class="text-' + cor + '">' + msg + '</p>'
    );
}
