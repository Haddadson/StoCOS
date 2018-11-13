// Verifica se o atributo está nulo ou vazio
function nuloOuVazio(valor) {
    return valor === undefined || valor === null || valor === "";
}

// Verifica se o vetor está nulo ou vazio
function nuloOuVazioVetor(vetor) {
    return vetor === undefined || vetor === null || vetor.length === 0;
}

// Formata datas para o padrão recebido no backend em Java
function formataData(data, hora){
    if(nuloOuVazio(data))
      return "";
    let dataHoraFormatada = "";
    dataHoraFormatada += (data + 'T');
    if(nuloOuVazio(hora))
      dataHoraFormatada += "00:00";
    else
      dataHoraFormatada += hora;
    return dataHoraFormatada;
}

// Obtem o dia, mês e ano da data informada em string
function obterDadosData(data){
  if(!nuloOuVazio(data)){
    let dadosData = data.split('-');
    return {
      "dia" : dadosData[2],
      "mes" : dadosData[1],
      "ano" : dadosData[0]
    };
  } else {
    return {};
  }

}
