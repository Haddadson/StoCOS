package com.stocos.controller;

import org.json.JSONObject;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import com.stocos.lote.LoteService;
import com.stocos.produto.ProdutoService;
import com.stocos.redecosmeticos.RedeCosmeticosService;
import com.stocos.servico.DefaultServicoImpl;

public class Controller extends AbstractController {

	private ProdutoService produtoService = new ProdutoService();
	private RedeCosmeticosService redeService = new RedeCosmeticosService();
	private LoteService loteService = new LoteService();

	public Controller(Request req, Response res) {
		super(req, res);
	}

	@Override
	public void handle() throws Exception {
		rotasPadrao("produto", produtoService);
		rotasPadrao("redecosmeticos", redeService);
		rotasPadrao("lote", loteService);

		// Rotas especificas:
		get("/produto/getByRede", (req, res) -> produtoService.getByRede(req.getQuery()));
		get("/redecosmeticos/getOcupacao", (req, res) -> redeService.getOcupacao(req.getQuery()));
	}

	private <O> void rotasPadrao(String item, DefaultServicoImpl<O> sv) {
		get("/" + item + "/getall", (req, res) -> sv.getAll());
		get("/" + item + "/getById", (req, res) -> sv.getById(req.getQuery()));
		get("/" + item + "/getByAtributo", (req, res) -> sv.getByAtributo(req.getQuery().entrySet().iterator().next()));
		get("/" + item + "/delete", (req, res) -> sv.delete(req.getQuery()));
		post("/" + item + "/add", (req, res) -> sv.add(new JSONObject(req.getContent())));
		post("/" + item + "/update", (req, res) -> sv.add(new JSONObject(req.getContent())));
	}
}
