$(document).ready(() => {
    $('.divinfo').hide();
    buscarRedes();
});

// Busca as redes de cosméticos no servidor e atualiza o input dropdown
function buscarRedes() {
    $('#listaredes').append('<option>Avon</option>');
    $('#listaredes').append('<option>Loreal</option>');
}

// Chamada quando uma rede de cosméticos é selecionada no dropdown
function escolherRede() {
    var rede = $('#listaredes').val();
    if ($("#listaredes").prop('selectedIndex') > 0) {
        $('.nenhumarede').hide();
        $('.divinfo').show();
        atualizarInfos(rede);
    } else {
        $('.divinfo').hide();
        $('.nenhumarede').show();
    }
}

// Atualiza as informações da tabela e de capacidade, de acordo com a rede selecionada
function atualizarInfos(rede) {
    atualizarCapacidade(rede);
    atualizarTabela(rede);
}

function atualizarCapacidade(rede) {
    // Atualizar volumeTotal, volumeDisponivel e volumeOcupado
    var dados = {
        volumeTotal: 234,
        volumeOcupado: 203,
    }
    atualizarGraficos(dados);
}

function atualizarTabela(rede) {
    $('#corpo-tabela').empty();
    var produtos = [
        {
            'id': 123,
            'nome': 'Shampoo XYZ',
            'marca': 'Seda',
            'categoria': 'Shampoo',
            'volume': 12.07
        },
        {
            'id': 234,
            'nome': 'Rimmel ABC',
            'marca': 'Beauty',
            'categoria': 'Rimmel',
            'volume': 7.07
        },
        {
            'id': 234,
            'nome': 'Rimmel ABC',
            'marca': 'Beauty',
            'categoria': 'Rimmel',
            'volume': 7.07
        },
        {
            'id': 234,
            'nome': 'Rimmel ABC',
            'marca': 'Beauty',
            'categoria': 'Rimmel',
            'volume': 7.07
        },
        {
            'id': 234,
            'nome': 'Rimmel ABC',
            'marca': 'Beauty',
            'categoria': 'Rimmel',
            'volume': 7.07
        },
        {
            'id': 234,
            'nome': 'Rimmel ABC',
            'marca': 'Beauty',
            'categoria': 'Rimmel',
            'volume': 7.07
        }
    ]
    for (let i = 0; i < produtos.length; i++) {
        var produto = produtos[i];
        var id = produto['id'];
        var nome = produto['nome'];
        var marca = produto['marca'];
        var categoria = produto['categoria'];
        var volume = produto['volume'];
        $('#corpo-tabela').append(
            '<tr><th scope="row">' + id +
            '</th><td>' + nome +
            '</td><td>' + marca +
            '</td><td>' + categoria +
            '</td><td>' + volume +
            '</td></tr>');
    }
}

// Adiciona Produtos
$('#adicionarProdutos').click(() => {

});

// Remove um produto
$('#removerProduto').click(() => {

});

// Muda a capacidade total
$('#mudarCapacidadeBtn').click(() => {

});

function atualizarGraficos(dados) {
    var ocupado = (dados['volumeOcupado'] / dados['volumeTotal']) * 100.0;
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
