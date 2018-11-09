package com.stocos.controller;

import java.time.LocalDateTime;
import java.util.Map.Entry;
import java.util.UUID;

import org.json.JSONObject;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import com.stocos.lote.Lote;
import com.stocos.lote.LoteService;
import com.stocos.produto.Produto;
import com.stocos.produto.ProdutoService;
import com.stocos.redecosmeticos.RedeCosmeticos;
import com.stocos.redecosmeticos.RedeCosmeticosService;

public class Controller extends AbstractController {

	private RedeCosmeticosService redeService;
	private ProdutoService produtoService;
	private LoteService loteService;

	public Controller(Request req, Response res) {
		super(req, res);
		redeService = new RedeCosmeticosService();
		produtoService = new ProdutoService();
		loteService = new LoteService();
	}

	@Override
	public void handle() throws Exception {

		handleRedes();
		handleProduto();
		handleLote();

		notFound((req, res) -> "Pagina nao Encontrada");
	}

	private void handleRedes() throws Exception {
		get("/redecosmeticos/getall", (req, res) -> redeService.getAll());

		get("/redecosmeticos/getById", (req, res) -> {
			String uuid = req.getQuery().getOrDefault("id", "");
			return redeService.getById(uuid);
		});

		get("/redecosmeticos/getByAtributo", (req, res) -> {
			Entry<String, String> entry = req.getQuery().entrySet().iterator().next();
			String atr = entry.getKey();
			String val = entry.getValue();
			return redeService.getByAtributo(atr, val);
		});

		get("/redecosmeticos/delete", (req, res) -> {
			UUID uuid = UUID.fromString(req.getQuery().get("id"));
			redeService.delete(uuid);
			return new JSONObject().toString();
		});

		post("/redecosmeticos/add", (req, res) -> {
			try {
				JSONObject json = new JSONObject(req.getContent());
				String nome = json.getString("nome");
				double capacidade = json.getDouble("capacidade");
				if (nome == null || nome.isEmpty())
					throw new Exception("O nome nao pode ser nulo");
				RedeCosmeticos rede = new RedeCosmeticos(nome, capacidade);
				rede.setEmail(json.has("email") ? json.getString("email") : "");
				rede.setEndereco(json.has("endereco") ? json.getString("endereco") : "");
				rede.setTelefone(json.has("telefone") ? json.getString("telefone") : "");
				redeService.add(rede);
				return new JSONObject().put("status", "OK").toString();
			} catch (Exception e) {
				return new JSONObject().put("Erro", e.getMessage()).toString();
			}
		});

		post("/redecosmeticos/update", (req, res) -> {
			try {
				JSONObject json = new JSONObject(req.getContent());
				String nome = json.getString("nome");
				double capacidade = json.getDouble("capacidade");
				if (nome == null || nome.isEmpty())
					throw new Exception("O nome nao pode ser nulo");
				RedeCosmeticos rede = new RedeCosmeticos(nome, capacidade);
				rede.setEmail(json.has("email") ? json.getString("email") : "");
				rede.setEndereco(json.has("endereco") ? json.getString("endereco") : "");
				rede.setTelefone(json.has("telefone") ? json.getString("telefone") : "");
				redeService.update(UUID.fromString(json.getString("id")), rede);
				return new JSONObject().put("status", "OK").toString();
			} catch (Exception e) {
				return new JSONObject().put("Erro", e.getMessage()).toString();
			}
		});
	}

	private void handleProduto() throws Exception {
		get("/produto/getall", (req, res) -> produtoService.getAll());

		get("/produto/getById", (req, res) -> {
			String uuid = req.getQuery().getOrDefault("id", "");
			return produtoService.getById(uuid);
		});

		get("/produto/getByAtributo", (req, res) -> {
			Entry<String, String> entry = req.getQuery().entrySet().iterator().next();
			String atr = entry.getKey();
			String val = entry.getValue();
			return produtoService.getByAtributo(atr, val);
		});

		get("/produto/delete", (req, res) -> {
			UUID uuid = UUID.fromString(req.getQuery().get("id"));
			produtoService.delete(uuid);
			return new JSONObject().toString();
		});

		post("/produto/add", (req, res) -> {
			try {
				JSONObject json = new JSONObject(req.getContent());
				String rede = json.getString("rede");
				String nome = json.getString("nome");
				if (rede == null || nome == null || rede.isEmpty() || nome.isEmpty())
					throw new Exception("O nome e a rede nao podem ser nulos");
				Produto p = new Produto(rede, nome, json.getDouble("volume"));
				p.setCategoria(json.has("categoria") ? json.getString("categoria") : "");
				p.setMarca(json.has("marca") ? json.getString("marca") : "");
				produtoService.add(p);
				return new JSONObject().put("status", "OK").toString();
			} catch (Exception e) {
				return new JSONObject().put("Erro", e.getMessage()).toString();
			}
		});

		post("/produto/update", (req, res) -> {
			try {
				JSONObject json = new JSONObject(req.getContent());
				String rede = json.getString("rede");
				String nome = json.getString("nome");
				if (rede == null || nome == null || rede.isEmpty() || nome.isEmpty())
					throw new Exception("O nome e a rede nao podem ser nulos");
				Produto p = new Produto(rede, nome, json.getDouble("volume"));
				p.setCategoria(json.has("categoria") ? json.getString("categoria") : "");
				p.setMarca(json.has("marca") ? json.getString("marca") : "");
				produtoService.update(UUID.fromString(json.getString("id")), p);
				return new JSONObject().put("status", "OK").toString();
			} catch (Exception e) {
				return new JSONObject().put("Erro", e.getMessage()).toString();
			}
		});
	}

	private void handleLote() throws Exception {
		get("/lote/getall", (req, res) -> loteService.getAll());

		get("/lote/getById", (req, res) -> {
			String uuid = req.getQuery().getOrDefault("id", "");
			return loteService.getById(uuid);
		});

		get("/lote/getByAtributo", (req, res) -> {
			Entry<String, String> entry = req.getQuery().entrySet().iterator().next();
			String atr = entry.getKey();
			String val = entry.getValue();
			return loteService.getByAtributo(atr, val);
		});

		get("/lote/delete", (req, res) -> {
			UUID uuid = UUID.fromString(req.getQuery().get("id"));
			loteService.delete(uuid);
			return new JSONObject().toString();
		});

		post("/lote/add", (req, res) -> {
			try {
				JSONObject json = new JSONObject(req.getContent());
				LocalDateTime validade = LocalDateTime.parse(json.getString("validade"));
				Lote lote = new Lote(UUID.fromString(json.getString("idproduto")), validade, json.getInt("quantidade"));
				loteService.add(lote);
				return new JSONObject().put("status", "OK").toString();
			} catch (Exception e) {
				return new JSONObject().put("Erro", e.getMessage()).toString();
			}
		});

		post("/lote/update", (req, res) -> {
			try {
				JSONObject json = new JSONObject(req.getContent());
				UUID id = UUID.fromString(json.getString("id"));
				LocalDateTime validade = LocalDateTime.parse(json.getString("validade"));
				Lote lote = new Lote(UUID.fromString(json.getString("idproduto")), validade, json.getInt("quantidade"));
				loteService.update(id, lote);
				return new JSONObject().put("status", "OK").toString();
			} catch (Exception e) {
				return new JSONObject().put("Erro", e.getMessage()).toString();
			}
		});
	}

}
