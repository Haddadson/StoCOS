$(document).ready(() => {
    $('#filtrarProdutosDiv').hide();
    $('#filtrarAgendamentosDiv').hide();
    montarListaProdutos();

    // Tenta realizar o cadastro
    $('#agendar-venda').click(() => {
      obterQuantidadesProdutos();
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


function obterQuantidadesProdutos(){
  if (localStorage.getItem("agendamentos") === null) {
    let agendamentos = {
      "produtosExpedicao" : []
    };
    localStorage.setItem('agendamentos', JSON.stringify(agendamentos));
  }

  let agendamentosCadastrados = JSON.parse(localStorage.getItem("agendamentos"));
  let listaProdutos = $('#listaProdutos').children();
  let agendamentoExpedicao = {
    "nomeComprador" : $('#comprador').val(),
    "emailComprador" : $('#email').val(),
    "enderecoComprador" : $('#endereco').val(),
    "telefoneComprador" : $('#telefone').val(),
    "dataAgendamento" : formataData($('#data-agendamento').val()),
    "listaProdutos" : []
  };
  $(listaProdutos).each(function(index, e){
  	let qtd = $(e).attr('data-quantidade');
  	if(!nuloOuVazio(qtd)){
  		let produto = {
        "idProduto" : $(e).attr('data-id-produto'),
        "nomeProduto" : $(e).attr('data-nome-produto'),
        "quantidadeProduto" : qtd
      };
       agendamentoExpedicao.listaProdutos.push(produto);
    }
  });
  if(agendamentoExpedicao.listaProdutos.length > 0){
    agendamentosCadastrados.produtosExpedicao.push(agendamentoExpedicao);
    localStorage.setItem('agendamentos', JSON.stringify(agendamentosCadastrados));
    alert("Agendado com sucesso");
  } else {
    alert("Preencha a quantidade de pelo menos um produto!");
  }
  console.log(agendamentosCadastrados);
}

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
                    'data-quantidade="" data-id-produto="'+ produto.id + '" data-nome-produto="' + produto.nome + '"' +
                    ' id="list-home-list" data-toggle="list" href="#list-home"' +
                    ' role="tab" aria-controls="home"><div class="col-md-6">' + produto.nome +
                    '</div><div class="col-md-4"><input type="number" onkeyup = "atualizarItem(this)" min="0" class="qtd-item" placeholder="Quantidade"></input></div></div>');
                }
                $('#filtrarProdutosDiv').show();
            } else {
                $('#listaProdutos').html('<div class="list-group-item text-secondary">Nenhum produto cadastrado.</div>');
            }
        } else {
            $('#listaProdutos').html('<div class="list-group-item text-danger">Não foi possível obter os dados.</div>');
        }
    }).done(function (data) {

    }).fail(function ()   {
        $('#listaProdutos').html('<div class="list-group-item text-secondary">Erro ao comunicar com o servidor.</div>');
    }).always(function () {

    });
}

function atualizarItem(elemento){
  let produto = $(elemento).parents()[1];
  $(produto).attr('data-quantidade', $(elemento).val());
}

function itemEhValido(elemento){
  console.log(elemento);
  return !nuloOuVazio($(elemento).data('quantidade')) && parseInt($(elemento).data('quantidade')) > 0
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
