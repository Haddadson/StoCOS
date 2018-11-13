package com.stocos.redecosmeticos;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.json.JSONObject;
import org.simpleframework.http.Query;

import com.stocos.dao.DefaultDaoImpl;
import com.stocos.lote.Lote;
import com.stocos.lote.LoteDao;
import com.stocos.produto.ProdutoDao;
import com.stocos.servico.DefaultServicoImpl;

public class RedeCosmeticosService extends DefaultServicoImpl<RedeCosmeticos> {

	public RedeCosmeticosService() {
		super(new RedeCosmeticosDao());
	}

	@Override
	public String update(JSONObject json) throws Exception {
		UUID uuid = UUID.fromString(json.getString(DefaultDaoImpl.CAMPO_UUID));
		Entry<UUID, RedeCosmeticos> entry = getDao().getById(uuid);
		if (entry != null) {
			return new JSONObject().put("status", getDao().update(uuid, getDao().fromJson(json))).toString();
		} else {
			return new JSONObject().put("status", "Rede nao encontrada").toString();
		}
	}

	public String getOcupacao(Query query) {
		ProdutoDao pDao = new ProdutoDao();
		LoteDao loteDao = new LoteDao();
		String idRede = query.get("idrede");
		double ocupacao = 0;
		Map<UUID, Lote> lotes = loteDao.getByAtributo("id-rede", idRede);
		for (Lote l : lotes.values()) {
			ocupacao += pDao.getById(l.getIdProduto()).getValue().getVolume() * l.getQuantidade();
		}
		return new JSONObject().put("resultado", ocupacao).toString();
	}
}
