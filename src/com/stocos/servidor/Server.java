package com.stocos.servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerSocketProcessor;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

import com.stocos.controller.Controller;

public class Server implements Container {

	private static Server INSTANCE;

	public static Server getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Server();
		return INSTANCE;
	}

	private int porta = 4567;
	private boolean isRunning = false;
	private ContainerSocketProcessor servidor;
	private SocketAddress endereco;
	private Connection conexao;

	private List<ServerListener> svListeners;

	private Server() {
		svListeners = new ArrayList<>();
	}

	public void porta(int p) {
		porta = p;
	}

	public int porta() {
		return porta;
	}

	public void start() {
		if (isRunning)
			return;
		try {
			Container container = getInstance();
			servidor = new ContainerSocketProcessor(container);
			conexao = new SocketConnection(servidor);
			endereco = new InetSocketAddress(porta());
			conexao.connect(endereco);
			isRunning = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			svListeners.forEach(l -> l.onServerStart());
		}
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
			isRunning = false;
			svListeners.forEach(l -> l.onServerStop());
		}
	}

	public void addServerListener(ServerListener sl) {
		svListeners.add(sl);
	}

	@Override
	public void handle(Request req, Response res) {
		try (PrintStream ps = new PrintStream(res.getOutputStream())) {
			svListeners.forEach(l -> l.onServerRequest(req));
			res.setValue("Content-Type", "application/json");
			res.setValue("Accept", "application/json");
			res.setValue("Server", "StoCOS/1.0 (Simple 4.0)");
			res.setValue("Access-Control-Allow-Origin", "*");
			res.setValue("Access-Control-Allow-Methods", "*");
			res.setDate("Date", System.currentTimeMillis());
			res.setDate("Last-Modified", System.currentTimeMillis());

			try {
				Controller controller = new Controller(req, res);
				controller.handle();
			} catch (Exception e) {
				ps.println("Houve um erro ao processar o servico: " + e.getMessage());
			}

			svListeners.forEach(l -> l.onServerResponse(req, res));

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
