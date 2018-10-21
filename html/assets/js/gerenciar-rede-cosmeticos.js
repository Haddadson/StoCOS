$(document).ready(() => {
    $('.divinfo').hide();
    buscarRedes();
});

// Busca as redes de cosméticos no servidor e atualiza o input dropdown
function buscarRedes() {
    $.get('http://localhost:4567/redecosmeticos/getall', (data) => {
        if (data) {
            if (data.length > 0) {
                $('#listaredes').empty();
                $('#listaredes').append('<option>Selecione...</option>');
                for (var i = 0; i < data.length; i++) {
                    var rede = data[i];
                    $('#listaredes').append('<option>' + rede.nome + '</option>');
                }
            }
        }
    }).done(function (data) {

    }).fail(function () {

    }).always(function () {

    });
}

// Chamada quando uma rede de cosméticos é selecionada no dropdown
function escolherRede() {
    if ($("#listaredes").prop('selectedIndex') > 0) {
        $('.nenhumarede').hide();
        $('.divinfo').show();
        atualizarInfos();
    } else {
        $('.divinfo').hide();
        $('.nenhumarede').show();
    }
}

// Atualiza as informações da tabela e de capacidade, de acordo com a rede selecionada
function atualizarInfos() {
    let rede = $('#listaredes').val();
    atualizarCapacidade(rede);
    atualizarTabela(rede);
}

function atualizarCapacidade(rede) {
    // Atualizar capacidade, volumeDisponivel e volumeOcupado
    $.get('http://localhost:4567/setor/get?nome=' + rede, (data) => {
        if (data && !data.status) {
            $('#capacidade').html(data.capacidade);
            $('#ocupacao').html(data.ocupacao);
            $('#volumeDisponivel').html(data.capacidade - data.ocupacao);
            var dados = {
                capacidade: data.capacidade,
                volumeOcupado: data.ocupacao,
            }
            atualizarGraficos(dados);
        }
    }).done(function (data) {

    }).fail(function () {

    }).always(function () {

    });
}

function atualizarTabela(rede) {
    $('#corpo-tabela').empty();
    var produtos = [];
    $.get('http://localhost:4567/produto/getAll?nomerede=' + rede, (data) => {
        if (data && !data.status) {
            for (var i = 0; i < data.length; i++) {
                produtos[i] = data[i];
            }
            if (data.length != 0) {
                for (let i = 0; i < produtos.length; i++) {
                    var produto = produtos[i];
                    var id = produto['id'];
                    var nome = produto['nome'];
                    var marca = produto['marca'];
                    var categoria = produto['categoria'];
                    var quantidade = produto['quantidade'];
                    var volume = produto['volume'];

                    $('#corpo-tabela').append(
                        '<tr><th scope="row">' + id +
                        '</th><td>' + nome +
                        '</td><td>' + marca +
                        '</td><td>' + categoria +
                        '</td><td>' + volume +
                        '</td><td>' + quantidade +
                        '</td></tr>');
                }
            } else {
                $('#corpo-tabela').append('<tr><th scope="row"></th><td>Não há produtos cadastrados</td><td></td><td></td><td></td></tr>');
            }
        }
    }).done(function (data) {

    }).fail(function () {

    }).always(function () {

    });
}

// Adiciona Produtos
$('#adicionarProdutos').click(() => {
    let nome = $('#nome').val();
    let marca = $('#marca').val();
    let categoria = $('#categoria').val();
    let volume = $('#volume').val();
    let quantidade = $('#quantidade').val();
    let rede = $('#listaredes').val();
    $.get('http://localhost:4567/produto/add?nomerede=' + rede +
        '&nome=' + nome + '&marca=' + marca + '&categoria=' + categoria + '&volume=' + volume + '&quantidade=' + quantidade, (data) => {
            if (data) {
                atualizarInfos();
            }
        });
});

// Remove um produto
$('#removerProduto').click(() => {
    let rede = $('#listaredes').val();
    let idProduto = $('#idremover').val();
    let quantidade = $('#quantidaderemover').val();

    $.get('http://localhost:4567/produto/remover?nomerede=' + rede +
        '&idproduto=' + idProduto + '&quantidade=' + quantidade, (data) => {
            if (data) {
                atualizarInfos();
            }
        });
});

// Muda a capacidade total
$('#mudarCapacidadeBtn').click(() => {

});

function atualizarGraficos(dados) {
    var ocupado = (dados['volumeOcupado'] / dados['capacidade']) * 100.0;
    var ctx = document.getElementById("capacidadeCanvas").getContext('2d');
    var MeSeChart = new Chart(ctx, {
        type: 'horizontalBar',
        data: {
            labels: [
                "Volume (%)",
            ],
            datasets: [
                {
                    label: "Ocupado (%)",
                    data: [ocupado],
                    backgroundColor: ["#669911"],
                    hoverBackgroundColor: ["#66A2EB"]
                }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                xAxes: [{
                    ticks: {
                        min: 0,
                        max: 100,
                    }
                }],
                yAxes: [{
                    stacked: true
                }]
            }
        }
    });
}


// Filtra um item na tabela
function filtrar() {
    var input, filter, table, tr, td, i;
    input = $('#filtrar');
    filter = input.val().toUpperCase();
    table = $('#tabela');
    tr = $('tr');
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName('td')[0];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = '';
            } else {
                tr[i].style.display = 'none';
            }
        }
    }
}
