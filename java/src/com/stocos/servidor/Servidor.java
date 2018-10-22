package com.stocos.servidor;

import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.simpleframework.http.Query;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerSocketProcessor;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

import com.stocos.services.IServico;
import com.stocos.services.ProdutoService;
import com.stocos.services.RedeCosmeticosService;
import com.stocos.services.SetorService;

public class Servidor implements Container {

	private static Servidor INSTANCE;

	public static Servidor getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Servidor();
		return INSTANCE;
	}

	private ContainerSocketProcessor servidor;
	private SocketAddress endereco;
	private Connection conexao;

	private static final int PORTA = 4567;

	private boolean isRunning = false;

	private List<ServerListener> svListeners;

	private Servidor() {
		svListeners = new ArrayList<>();
	}

	public void start() {
		if (isRunning)
			return;
		try {
			Container container = getInstance();
			servidor = new ContainerSocketProcessor(container);
			conexao = new SocketConnection(servidor);
			endereco = new InetSocketAddress(PORTA);
			conexao.connect(endereco);
			isRunning = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			svListeners.forEach(l -> l.onServerStart());
		}
	}

	public String getEndereco() {
		return "http://localhost:" + PORTA + "...";
	}

	public void stop() {
		if (!isRunning)
			return;
		try {
			conexao.close();
			servidor.stop();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conexao = null;
			servidor = null;
			isRunning = false;
			svListeners.forEach(l -> l.onServerStop());
		}
	}

	public void addServerListener(ServerListener sl) {
		svListeners.add(sl);
	}

	@Override
	public void handle(Request request, Response response) {
		try {
			Query query = request.getQuery();
			List<String> path = new ArrayList<>(Arrays.asList(request.getPath().getSegments()));
			StringBuilder bodySB = new StringBuilder();
			PrintStream body = new PrintStream(response.getPrintStream()) {
				@Override
				public void println(String s) {
					super.println(s);
					bodySB.append(s);
				}
			};

			svListeners.forEach(l -> l.onServerRequest(request));

			response.setValue("Content-Type", "application/json");
			response.setValue("Server", "StoCOS/1.0 (Simple 4.0)");
			response.setValue("Access-Control-Allow-Origin", "*");
			response.setDate("Date", System.currentTimeMillis());
			response.setDate("Last-Modified", System.currentTimeMillis());

			if (path.size() > 0) {
				String dir = path.remove(0);

				IServico servico = null;

				if (dir.equalsIgnoreCase("redecosmeticos"))
					servico = new RedeCosmeticosService();
				else if (dir.equalsIgnoreCase("produto"))
					servico = new ProdutoService();
				else if (dir.equalsIgnoreCase("setor"))
					servico = new SetorService();

				if (path.size() > 0 && servico != null) {
					String metodo = path.remove(0);
					if (metodo.equalsIgnoreCase("add")) {
						body.println(servico.add(query));
					} else if (metodo.equalsIgnoreCase("get")) {
						body.println(servico.get(query));
					} else if (metodo.equalsIgnoreCase("getall")) {
						body.println(servico.getAll(query));
					} else if (metodo.equalsIgnoreCase("remover")) {
						body.println(servico.remover(query));
					} else if (metodo.equalsIgnoreCase("alterar")) {
						body.println(servico.alterar(query));
					}
				}
			}

			svListeners.forEach(l -> l.onServerResponse(response, bodySB.toString()));

			body.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}