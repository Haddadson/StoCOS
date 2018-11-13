package com.stocos.produto;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.http.Query;

import com.stocos.lote.Lote;
import com.stocos.lote.LoteDao;
import com.stocos.servico.DefaultServicoImpl;

public class ProdutoService extends DefaultServicoImpl<Produto> {

	public ProdutoService() {
		super(new ProdutoDao());
	}

	public String getByRede(Query query) throws Exception {
		String id = query.get("id");
		Map<UUID, Lote> lotes = new LoteDao().getByAtributo("id-rede", id);
		JSONArray produtos = new JSONArray();
		Set<String> setProdutos = new HashSet<>();
		for (Lote l : lotes.values()) {
			setProdutos.add(getDao().toJson(getDao().getById(l.getIdProduto()).getValue()).toString());
		}
		setProdutos.forEach(p -> {
			produtos.put(new JSONObject(p));
		});
		return produtos.toString();
	}

}
