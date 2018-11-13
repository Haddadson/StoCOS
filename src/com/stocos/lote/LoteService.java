package com.stocos.lote;

import java.util.Collection;
import java.util.UUID;

import org.json.JSONObject;
import org.simpleframework.http.Query;

import com.stocos.dao.DefaultDaoImpl;
import com.stocos.produto.Produto;
import com.stocos.produto.ProdutoDao;
import com.stocos.redecosmeticos.RedeCosmeticos;
import com.stocos.redecosmeticos.RedeCosmeticosDao;
import com.stocos.servico.DefaultServicoImpl;

public class LoteService extends DefaultServicoImpl<Lote> {

	public LoteService() {
		super(new LoteDao());
	}

	// Calcula se e possivel adicionar o lote
	private boolean podeAdicionar(Lote lote) {
		// Obtem a rede do lote a ser adicionado
		RedeCosmeticosDao redeDao = new RedeCosmeticosDao();
		RedeCosmeticos rede = redeDao.getById(lote.getIdRede()).getValue();

		// Obtem o produto atrelado ao lote a ser adicionado
		ProdutoDao pDao = new ProdutoDao();
		Produto p = pDao.getById(lote.getIdProduto()).getValue();
		double total = p.getVolume() * lote.getQuantidade();

		// Obtem a lista de lotes da rede
		Collection<Lote> lotes = getDao().getByAtributo("id-rede", lote.getIdRede()).values();
		for (Lote l : lotes) {
			if (l.getStatus() == 3) // RETIRADO
				continue;
			Produto temp = pDao.getById(l.getIdProduto()).getValue();
			total += l.getQuantidade() * temp.getVolume();
			if (total > rede.getCapacidade())
				return false;
		}
		return true;
	}

	@Override
	public String add(JSONObject json) throws Exception {
		Lote lote = getDao().fromJson(json);
		if (lote.getStatus() != 1)
			throw new Exception("Erro ao adicionar lote! O status inicial deve ser 1." + lote.getStatus());
		else if (podeAdicionar(lote))
			return "" + getDao().create(lote);
		else
			throw new Exception("Erro ao adicionar lote: Capacidade insuficiente!");
	}

	// Modificar capacidade da rede caso o status tenha mudado para 2 - "ENTREGUE"
	@Override
	public String update(JSONObject json) throws Exception {
		UUID id = UUID.fromString(json.getString(DefaultDaoImpl.CAMPO_UUID));
		Lote lote = getDao().getById(id).getValue();
		Lote loteModificado = getDao().fromJson(json);
		if (lote.getStatus() <= loteModificado.getStatus()) {
			throw new Exception("Voce deve avancar o status do lote. Status atual: " + lote.getStatus());
		} else if (loteModificado.getStatus() == 2) { // ENTREGUE
			return "" + getDao().update(id, loteModificado);
		} else if (loteModificado.getStatus() == 3) { // AGENDADO PARA RETIRADA

		} else if (loteModificado.getStatus() == 4) { // RETIRADO

		}
		return new JSONObject().put("status", "O status informado e invalido. Envie um numero de 1 a 4").toString();
	}

	// Modificar capacidade da rede
	@Override
	public String delete(Query query) throws Exception {
		return "";
	}

}
