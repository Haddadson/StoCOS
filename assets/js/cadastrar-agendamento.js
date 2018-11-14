$(document).ready(() => {
    $('#filtrarProdutosDiv').hide();
    $('#filtrarAgendamentosDiv').hide();
    montarListaProdutos();

    // Tenta realizar o cadastro
    $('#cadastrar-agendamento').click(() => {
        let produto = $('#produto').data('id-produto');
        let quantidade = $('#quantidade').val();
        let dataAgendamento = $('#data-agendamento').val();
        let dataVencimento = $('#data-vencimento').val();
        let idRede = "883c657a-8ce1-4ef1-bf47-1d9481afbd5e"; //Lojas Rede //TODO: Obter Rede logada

        dataVencimento = formataData(dataVencimento);
        if(!nuloOuVazio(produto) || !nuloOuVazio(quantidade) || !nuloOuVazio(dataAgendamento) || !nuloOuVazio(dataVencimento)){
          let lote = {
            "status" : 1,
            "id-rede" : idRede,
            "id-produto" : produto,
            "quantidade" : quantidade,
            "data-validade" : dataVencimento,
            "data-entrega" : "0000-01-01",
            "data-agendamento" : dataAgendamento
          }
          $.ajax({
            type: 'POST',
            url: 'http://localhost:4567/lote/add',
            data: JSON.stringify(lote),
            success: function (data) { if(data){$("#status-cadastro").text("Agendado com sucesso!")} montarListaAgendamentos(); },
            contentType: "text/plain",
            dataType: 'json'
          });
        } else {
          //TODO: melhorar aviso
          alert("Preencha todos os campos");
        }
    });

    // Filtra os produtos da lista
    $("#filtrarProdutos").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $("#listaProdutos div").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

    // Filtra os agendamentos da lista
    $("#filtrarAgendamentos").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $("#listaAgendamentos div").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

});


// Monta a lista de produtos cadastradas
function montarListaProdutos() {
    $('#listaProdutos').html('<div class="list-group-item text-secondary">Conectando...</div>');
    var lista = $("#listaProdutos");

    $.get("http://localhost:4567/produto/getAll", (data) => {
        if (data) {
            if (data.length > 0) {
                $('#listaProdutos').empty();
                for (var i = 0; i < data.length; i++) {
                    var produto = data[i];
                    $('#listaProdutos').append('<div class="list-group-item list-group-item-action"'+
                    'data-id-produto="'+ produto.id + '" data-nome-produto="' + produto.nome + '"' +
                    ' id="list-home-list" data-toggle="list" href="#list-home" onclick="atualizarInfos(this);"' +
                    ' role="tab" aria-controls="home">' + produto.nome + '</div>');
                }
                $('#filtrarProdutosDiv').show();
            } else {
                $('#listaProdutos').html('<div class="list-group-item text-secondary">Nenhum produto cadastrado.</div>');
            }
        } else {
            $('#listaProdutos').html('<div class="list-group-item text-danger">Não foi possível obter os dados.</div>');
        }
    }).done(function (data) {

    }).fail(function () {
        $('#listaProdutos').html('<div class="list-group-item text-secondary">Erro ao comunicar com o servidor.</div>');
    }).always(function () {

    });
}

// Monta a lista de agendamentos cadastradas
function montarListaAgendamentos() {
    $('#listaAgendamentos').html('<div class="list-group-item text-secondary">Conectando...</div>');
    var lista = $("#listaAgendamentos");
    var redesCadastradas = [];

    $.get("http://localhost:4567/produto/getAll", (data) => {
        if (data) {
            if (data.length > 0) {
                $('#listaAgendamentos').empty();
                for (var i = 0; i < data.length; i++) {
                    var agendamento = data[i];
                    $('#listaAgendamentos').append('<div class="list-group-item list-group-item-action">'
                    + agendamento.dataAgendamento + ' ' + agendamento.idProduto + '</div>');
                }
                $('#filtrarAgendamentosDiv').show();
            } else {
                $('#listaAgendamentos').html('<div class="list-group-item text-secondary">Nenhuma rede Cadastrada.</div>');
            }
        } else {
            $('#listaAgendamentos').html('<div class="list-group-item text-danger">Não foi possível obter os dados.</div>');
        }
    }).done(function (data) {

    }).fail(function () {
        $('#listaAgendamentos').html('<div class="list-group-item text-secondary">Erro ao comunicar com o servidor.</div>');
    }).always(function () {

    });
}

//Atualiza as informações do formulário ao escolher produto
function atualizarInfos (produtoClicado){
  $("#produto").val('');
  let nomeProduto = $(produtoClicado).data("nome-produto");
  let idProduto = $(produtoClicado).data("id-produto");
  $("#produto").val(nomeProduto);
  $("#produto").data("id-produto", idProduto);

}
