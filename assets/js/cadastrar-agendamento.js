$(document).ready(() => {
    $('#filtrarProdutosDiv').hide();
    $('#filtrarAgendamentosDiv').hide();
    montarListaProdutos();
    montarListaAgendamentos();

    // Tenta realizar o cadastro
    $('#cadastrar-agendamento').click(() => {
        let produto = $('#produto').data('id-produto');
        let quantidade = $('#quantidade').val();
        let dataEntrega = $('#data-entrega').val();
        let dataVencimento = $('#data-vencimento').val();

        dataEntrega = formataData(dataEntrega);
        dataVencimento = formataData(dataVencimento);
        if(!nuloOuVazio(produto) || !nuloOuVazio(quantidade) || !nuloOuVazio(dataEntrega) || !nuloOuVazio(dataVencimento)){
          $.get('http://localhost:4567/lote/add?id-produto=' + produto + '&quantidade=' + quantidade +
              '&dataEntrega=' + dataEntrega + '&dataVencimento=' + dataVencimento, (data) => {
                  console.log('data=' + data);
                  montarListaProdutos();
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
    var redesCadastradas = [];
    $.get("http://localhost:4567/produto/getall", (data) => {
        if (data) {
            if (data.length > 0) {
                $('#listaProdutos').empty();
                for (var i = 0; i < data.length; i++) {
                    var produto = data[i];
                    console.log(produto);
                    $('#listaProdutos').append('<div class="list-group-item">' + produto.nome + '</div>');
                }
                $('#filtrarProdutosDiv').show();
            } else {
                $('#listaProdutos').html('<div class="list-group-item text-secondary">Nenhuma rede Cadastrada.</div>');
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

// Monta a lista de produtos cadastradas
function montarListaAgendamentos() {
    $('#listaAgendamentos').html('<div class="list-group-item text-secondary">Conectando...</div>');
    var lista = $("#listaAgendamentos");
    var redesCadastradas = [];
    $.get("http://localhost:4567/lote/getall", (data) => {
        if (data) {
            if (data.length > 0) {
                $('#listaAgendamentos').empty();
                for (var i = 0; i < data.length; i++) {
                    var agendamento = data[i];
                    console.log(agendamento);
                    $('#listaAgendamentos').append('<div class="list-group-item">' + agendamento.dataEntrega + ' ' + agendamento.idProduto + '</div>');
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
