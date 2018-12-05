
$(document).ready(() => {
    obterCapacidadeTotal();
    obterVolumeTotal();
    obterAgendamentosNoMes();
    montarChartRedesMaisAtivas();
    montarChartRedesMenosAtivas();
    montarChartProdutosMaisFrequentes();
    quantidadeExpedicoesNoMes();
    obterPorcentagemExpedicoesCanceladas();
    obterPorcentagemExpedicoesConfirmadas();
});

function obterCapacidadeTotal() {
    $.ajax({
        type: 'GET',
        url: "http://localhost:4567/indicadores",
        success: function (res) {
            if (res) {
                let cap = JSON.parse(res['capacidade-total']);
                $('#capacidadeTotal').html(cap + '.00');
            }
        }
    });
}

function obterVolumeTotal() {
    $.ajax({
        type: 'GET',
        url: "http://localhost:4567/indicadores",
        success: function (res) {
            if (res) {
                let cap = JSON.parse(res['volume-total']);
                cap = Math.floor(cap);
                $('#volumeTotal').html(cap + '.00');
            }
        }
    });
}

function obterAgendamentosNoMes() {
    $.ajax({
        type: 'GET',
        url: "http://localhost:4567/indicadores",
        success: function (res) {
            if (res) {
                let agendamentos = JSON.parse(res['agendamentos-no-mes']);
                $('#agendamentosNoMes').html(agendamentos);
            }
        }
    });
}

function montarChartRedesMaisAtivas() {
    $.ajax({
        type: 'GET',
        url: "http://localhost:4567/indicadores",
        success: function (res) {
            if (res) {
                let arr = JSON.parse(res['redes-mais-lotes']);
                let nomes = [];
                let qnts = [];
                let cores = [];
                for (let i = 0; i < arr.length; i++) {
                    let rede = arr[i];
                    nomes.push(rede['nome']);
                    qnts.push(rede['qnt-lotes']);
                    cores.push('rgb('
                        + Math.floor(Math.random() * 200)
                        + ', '
                        + Math.floor(Math.random() * 200)
                        + ', '
                        + Math.floor(Math.random() * 200)
                        + ', 0.4)');
                }
                var ctx = document.getElementById('chartRedesMaisAtivas').getContext('2d');
                var chart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: nomes,
                        datasets: [{
                            label: nomes,
                            backgroundColor: cores,
                            data: qnts,
                        }],
                    },
                    options: {
                        title: {
                            display: true,
                            text: 'Redes mais Ativas'
                        },
                        scales: {
                            yAxes: [{
                                display: true,
                                ticks: {
                                    suggestedMin: 0,
                                    beginAtZero: true,
                                }
                            }]
                        }
                    }
                });
            }
        }
    });
}

function montarChartRedesMenosAtivas() {
    $.ajax({
        type: 'GET',
        url: "http://localhost:4567/indicadores",
        success: function (res) {
            if (res) {
                let arr = JSON.parse(res['redes-menos-lotes']);
                let nomes = [];
                let qnts = [];
                let cores = [];
                for (let i = 0; i < arr.length; i++) {
                    let rede = arr[i];
                    nomes.push(rede['nome']);
                    qnts.push(rede['qnt-lotes']);
                    cores.push('rgb('
                        + Math.floor(Math.random() * 200)
                        + ', '
                        + Math.floor(Math.random() * 200)
                        + ', '
                        + Math.floor(Math.random() * 200)
                        + ', 0.4)');
                }
                var ctx = document.getElementById('chartRedesMenosAtivas').getContext('2d');
                var chart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: nomes,
                        datasets: [{
                            label: nomes,
                            backgroundColor: cores,
                            data: qnts,
                        }],
                    },
                    options: {
                        title: {
                            display: true,
                            text: 'Redes menos Ativas'
                        },
                        scales: {
                            yAxes: [{
                                display: true,
                                ticks: {
                                    suggestedMin: 0,
                                    beginAtZero: true,
                                }
                            }]
                        }
                    }
                });
            }
        }
    });
}

function montarChartProdutosMaisFrequentes() {
    $.ajax({
        type: 'GET',
        url: "http://localhost:4567/indicadores",
        success: function (res) {
            if (res) {
                let arr = JSON.parse(res['produtos-mais-frequentes']);
                let nomes = [];
                let qnts = [];
                let cores = [];
                for (let i = 0; i < arr.length; i++) {
                    let produto = arr[i];
                    nomes.push(produto['nome']);
                    qnts.push(produto['frequencia']);
                    cores.push('rgb('
                        + Math.floor(Math.random() * 200)
                        + ', '
                        + Math.floor(Math.random() * 200)
                        + ', '
                        + Math.floor(Math.random() * 200)
                        + ', 0.4)');
                }
                var ctx = document.getElementById('chartProdutosMaisFrequentes').getContext('2d');
                var chart = new Chart(ctx, {
                    type: 'polarArea',
                    data: {
                        labels: nomes,
                        datasets: [{
                            label: nomes,
                            backgroundColor: cores,
                            data: qnts,
                        }],
                    },
                    options: {
                        title: {
                            display: true,
                            text: 'Produtos mais Frequentes'
                        },
                        scales: {
                            yAxes: [{
                                display: true,
                                ticks: {
                                    suggestedMin: 0,
                                    beginAtZero: true,
                                }
                            }]
                        }
                    }
                });
            }
        }
    });
}

function quantidadeExpedicoesNoMes(){
  let agendamentos = localStorage.getItem("agendamentos");
  let quantidadeItens = 0;
  let dataAtual = new Date();
  let mesAtual = dataAtual.getMonth() + 1;
  let anoAtual = dataAtual.getFullYear();
  if (!nuloOuVazio(agendamentos)) {
    let agendamentosJSON = JSON.parse(agendamentos);
    for (var i = 0; i < agendamentosJSON.produtosExpedicao.length; i++) {
      let mesAgendamento = agendamentosJSON.produtosExpedicao[i].dataAgendamento.split('-')[1];
      let anoAgendamento = agendamentosJSON.produtosExpedicao[i].dataAgendamento.split('-')[0];
      if(mesAgendamento == mesAtual && anoAgendamento == anoAtual){
        quantidadeItens++;
      }
    }
    $('#expedicoesNoMes').html(quantidadeItens);
  } else {
    $('#expedicoesNoMes').html("0");
  }
}

function obterPorcentagemExpedicoesCanceladas(){
  let agendamentos = localStorage.getItem("agendamentos");
  let quantidadeItensCancelados = 0;
  let porcentagemItensCancelados = 0.00;
  let dataAtual = new Date();
  let mesAtual = dataAtual.getMonth() + 1;
  let anoAtual = dataAtual.getFullYear();
  if (!nuloOuVazio(agendamentos)) {
    let agendamentosJSON = JSON.parse(agendamentos);
    for (var i = 0; i < agendamentosJSON.produtosExpedicao.length; i++) {
      let mesAgendamento = agendamentosJSON.produtosExpedicao[i].dataAgendamento.split('-')[1];
      let anoAgendamento = agendamentosJSON.produtosExpedicao[i].dataAgendamento.split('-')[0];
      if(mesAgendamento == mesAtual && anoAgendamento == anoAtual &&
        agendamentosJSON.produtosExpedicao[i].statusAgendamento == "cancelado"){
        quantidadeItensCancelados++;
      }
    }
    porcentagemItensCancelados = (quantidadeItensCancelados / agendamentosJSON.produtosExpedicao.length) * 100;
    $('#expedicoesCanceladas').html(porcentagemItensCancelados.toFixed(2) + " %");
  } else {
    $('#expedicoesCanceladas').html("0.00 %");
  }

}

function obterPorcentagemExpedicoesConfirmadas(){
  let agendamentos = localStorage.getItem("agendamentos");
  let quantidadeItensConfirmados = 0;
  let porcentagemItensConfirmados = 0.00;
  let dataAtual = new Date();
  let mesAtual = dataAtual.getMonth() + 1;
  let anoAtual = dataAtual.getFullYear();
  if (!nuloOuVazio(agendamentos)) {
    let agendamentosJSON = JSON.parse(agendamentos);
    for (var i = 0; i < agendamentosJSON.produtosExpedicao.length; i++) {
      let mesAgendamento = agendamentosJSON.produtosExpedicao[i].dataAgendamento.split('-')[1];
      let anoAgendamento = agendamentosJSON.produtosExpedicao[i].dataAgendamento.split('-')[0];
      if(mesAgendamento == mesAtual && anoAgendamento == anoAtual &&
        agendamentosJSON.produtosExpedicao[i].statusAgendamento == "confirmado"){
        quantidadeItensConfirmados++;
      }
    }
    porcentagemItensConfirmados = (quantidadeItensConfirmados / agendamentosJSON.produtosExpedicao.length) * 100;
    $('#expedicoesConfirmadas').html(porcentagemItensConfirmados.toFixed(2) + " %");
  } else {
    $('#expedicoesConfirmadas').html("0.00 %");
  }

}
