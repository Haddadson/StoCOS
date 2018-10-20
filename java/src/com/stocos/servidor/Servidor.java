package com.stocos.servidor;

import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.json.JSONObject;
import org.simpleframework.http.Query;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerSocketProcessor;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class Servidor implements Container {

	public static void iniciar() {
		try {
			int porta = 4567;
			Container container = new Servidor();
			ContainerSocketProcessor servidor = new ContainerSocketProcessor(container);
			Connection conexao = new SocketConnection(servidor);
			SocketAddress endereco = new InetSocketAddress(porta);
			conexao.connect(endereco);
			System.out.println("Tecle ENTER para interromper o servidor...");
			System.in.read();
			conexao.close();
			servidor.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handle(Request request, Response response) {
		System.out.println(request);
		try {
			Query query = request.getQuery();
			PrintStream body = response.getPrintStream();

			long time = System.currentTimeMillis();
			response.setValue("Content-Type", "application/json");
			response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
			response.setValue("Access-Control-Allow-Origin", "*");
			response.setValue("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
			response.setDate("Date", time);
			response.setDate("Last-Modified", time);

			if ("GET".equals(request.getMethod())) {
				JSONObject resObj = new JSONObject();
				resObj.put("status", "OK");

				JSONObject rede1 = new JSONObject();
				rede1.put("nome", "Avon");
				resObj.append("resultados", rede1);

				JSONObject rede2 = new JSONObject();
				rede2.put("nome", "Sephora");
				resObj.append("resultados", rede2);

				JSONObject rede3 = new JSONObject();
				rede3.put("nome", "Loreal");
				resObj.append("resultados", rede3);

				JSONObject rede4 = new JSONObject();
				rede4.put("nome", "Beauty");
				resObj.append("resultados", rede4);

				body.println(resObj.toString());

			} else if ("POST".equals(request.getMethod())) {

			}

			body.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}