$(document).ready(() => {
    $('.divinfo').hide();
    buscarRedes();
    inicialiarBotaoAbrirModalAdicaoProdutos();
});

// Busca as redes de cosméticos no servidor e atualiza o input dropdown
function buscarRedes() {
    $.get('http://localhost:4567/redecosmeticos/getall', (data) => {
        if (data) {
            if (data.length > 0) {
                $('#listaderedes').empty();
                for (var i = 0; i < data.length; i++) {
                    var rede = data[i];
                    $('#listaderedes').append('<a class="list-group-item list-group-item-action" ' +
                        'id="list-home-list" data-toggle="list" href="#list-home" onclick="atualizarInfos(this.innerHTML);" role="tab" aria-controls="home">' +
                        rede.nome +
                        '</a>');
                }
            }
        }
    }).done(function (data) {

    }).fail(function () {

    }).always(function () {

    });
}

// Atualiza as informações da tabela e de capacidade, de acordo com a rede selecionada
function atualizarInfos(rede) {
    $('.nenhumarede').hide();
    $('.divinfo').show();
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
            atualizarGraficoCategoria(rede);
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
                $('#corpo-tabela').append('<tr><th scope="row"></th><td>Não há produtos cadastrados</td><td></td><td></td><td></td><td></td></tr>');
            }
        }
    }).done(function (data) {

    }).fail(function () {

    }).always(function () {

    });
}

// Adiciona Produtos
function inicialiarBotaoAbrirModalAdicaoProdutos() {
  $("#botao-modal-adicionar-produtos").click(() => {
     limparModalAdicionarProdutos();
    let rede = $('#listaderedes a.active').html();
    $('#marca').val(rede);
  });
}


$('#adicionarProdutos').click(() => {
    let nome = $('#nome').val();
    let marca = $('#marca').val();
    let categoria = $('#categoria').val();
    let volume = $('#volume').val();
    let quantidade = $('#quantidade').val();
    let rede = $('#listaderedes a.active').html();

    $.get('http://localhost:4567/produto/add?nomerede=' + rede +
        '&nome=' + nome + '&marca=' + marca + '&categoria=' + categoria + '&volume=' + volume + '&quantidade=' + quantidade, (data) => {
            if (data) {
                atualizarInfos(rede);
            }
        });
});

// Remove um produto
$('#removerProduto').click(() => {
    let rede = $('#listaderedes a.active').html();
    let idProduto = $('#idremover').val();
    let quantidade = $('#quantidaderemover').val();

    $.get('http://localhost:4567/produto/remover?nomerede=' + rede +
        '&idproduto=' + idProduto + '&quantidade=' + quantidade, (data) => {
            if (data) {
                atualizarInfos(rede);
            }
        });
});

// Muda a capacidade total
$('#mudarCapacidadeBtn').click(() => {
    let rede = $('#listaderedes a.active').html();
    let novaCapacidade = $('#novaCapacidade').val();
    $.get('http://localhost:4567/setor/alterar?nomerede=' + rede + '&novacapacidade=' + novaCapacidade, (data) => {
        if (data) {
            console.log(data);
            atualizarInfos(rede);
        }
    });
});

function getRandomColor() {
    var letters = '0123456789ABCDEF';
    var color = '#';
    for (var i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

function atualizarGraficoCategoria(rede) {
    $.get('http://localhost:4567/produto/getall?nomerede=' + rede, (data) => {
        console.log(data.length);
        if (data.length == 0) {
            $('#categoriasCanvas').hide();
        } else {
            $('#categoriasCanvas').show();

            var dados = [];
            for (let i = 0; i < data.length; i++) {
                if (!dados[data[i].categoria]) {
                    dados[data[i].categoria] = data[i].quantidade;
                } else {
                    dados[data[i].categoria] += data[i].quantidade;
                }
            }
            let categorias = [];
            let quantidades = [];
            let cores = [
                "#FF6384",
                "#36A2EB",
                "#FFCE56"
            ];
            for (let i in dados) {
                categorias.push(i);
                quantidades.push(dados[i]);
                cores.push(getRandomColor());
            }

            var ctx = document.getElementById('categoriasCanvas').getContext('2d');
            var myDoughnutChart = new Chart(ctx, {
                type: 'doughnut',
                data: {
                    datasets: [{
                        data: quantidades,
                        backgroundColor: cores,
                        hoverBackgroundColor: cores,
                    }],

                    labels: categorias,
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                }
            });
        }
    });
}

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

//Limpar modal Adicionar Produtos

function limparModalAdicionarProdutos(){
  $("#nome").val("");
  $("#marca").val("");
  $("#volume").val("");
  $("#quantidade").val("");
}
