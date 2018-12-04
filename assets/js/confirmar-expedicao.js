$(document).ready(() => {
  montarListaExpedicoes();

    $('#dadosExpedicao').hide();
  //  montarLista();

    // Filtra os itens da tabela
    $("#filtrar").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $("#listaExpedicoes a").filter(function () {
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
    $('#dadosExpedicao').show();
    let idExpedicao = $(this).attr("data-id-expedicao");
    let agendamentos = localStorage.getItem("agendamentos");
    let informacoesProduto;
    if (!nuloOuVazio(agendamentos)) {
      let agendamentosJSON = JSON.parse(agendamentos);
      let produtosExpedicao = agendamentosJSON.produtosExpedicao;
      for (var i = 0; i < produtosExpedicao.length; i++) {
        console.log(produtosExpedicao[i].idExpedicao);
        if(produtosExpedicao[i].idExpedicao == idExpedicao){
          informacoesProduto = produtosExpedicao[i];
        }
      }
    }

    console.log(informacoesProduto);
    $('#nomeComprador').html("<h5><div>Comprador: " + informacoesProduto.nomeComprador + "</div></h5>");
    $('#emailComprador').html("<div>E-mail: " + informacoesProduto.emailComprador + "</div>");
    $('#telefoneComprador').html("<div>Telefone: " + informacoesProduto.telefoneComprador + "</div>");
    $('#dataExpedicao').html("<div>Data da Expedição prevista: " + informacoesProduto.dataAgendamento + "</div>");
    for(let i = 0; i < informacoesProduto.listaProdutos.length; i++){
      let produto = informacoesProduto.listaProdutos;
      console.log(produto[i]);
      $('#listaProdutos').append('<div>Nome: ' + produto[i].nomeProduto +' Quantidade: ' + produto[i].quantidadeProduto);
    }
});

function montarListaExpedicoes(){
  let agendamentos = localStorage.getItem("agendamentos");
  if (!nuloOuVazio(agendamentos)) {
    let agendamentosJSON = JSON.parse(agendamentos);
    let produtosExpedicao = agendamentosJSON.produtosExpedicao;
    if(produtosExpedicao.length > 0 ) {
      for (var i = 0; i < produtosExpedicao.length; i++) {
        let data = produtosExpedicao[i];
        $('#listaExpedicoes').append('<a class="list-group-item list-group-item-action" ' +
            'id="list-home-list data-nome-comprador="' + data.nomeComprador + '" data-telefone-comprador="' + data.telefoneComprador
            + '" data-email-comprador="' + data.emailComprador +'" data-toggle="list" href="#list-home"'
            + 'role="tab" aria-controls="home" data-id-expedicao="' + data.idExpedicao + '"'
            + '> Id: ' + data.idExpedicao + ' - Comprador: '
            + data.nomeComprador
            + '<br> Data de Agendamento - '
            + data.dataAgendamento
            + '<br> Telefone - '
            + data.telefoneComprador
            +'</a>');
      }
    } else {
      $('#listaExpedicoes').html('<div class="list-group-item text-secondary">Nenhuma expedição agendada.</div>');
      $('#dadosExpedicao').empty();
    }
  }
}

// Monta a lista de redes cadastradas
function montarLista() {
    $('#listaExpedicoes').html('<div class="list-group-item text-secondary">Conectando...</div>');
    var lista = $("#listaExpedicoes");
    var redesCadastradas = [];
    $.get("http://localhost:4567/lote/getall", (data) => {
        $('#listaExpedicoes').empty();
        montarListaExpedicoes();
        if (data) {
            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    var lote = data[i];
                    if (lote['status'] == 1) {
                        var id_rede = lote['id-rede'];
                        criarDiv(id_rede, lote);
                    }
                }
                $('#filtrarDiv').show();
            } else {
                $('#listaExpedicoes').html('<div class="list-group-item text-secondary">Nenhuma expedição agendada.</div>');
                $('#dadosExpedicao').empty();
            }
        } else {
            $('#listaExpedicoes').html('<div class="list-group-item text-danger">Não foi possível obter os dados.</div>');
            $('#dadosExpedicao').empty();
        }
    }).done(function (data) {

    }).fail(function () {
        $('#listaExpedicoes').html('<div class="list-group-item text-secondary">Erro ao comunicar com o servidor.</div>');
        $('#dadosExpedicao').empty();
    }).always(function () {

    });
}

function atualizarStatus(id, cor, msg) {
    $(id).html(
        '<p class="text-' + cor + '">' + msg + '</p>'
    );
}
