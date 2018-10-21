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
		try {
			Query query = request.getQuery();
			List<String> path = new ArrayList<>(Arrays.asList(request.getPath().getSegments()));
			PrintStream body = response.getPrintStream();

			response.setValue("Content-Type", "application/json");
			response.setValue("Server", "HelloWorld/1.0 (Simple 4.0)");
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
					} else if (metodo.equalsIgnoreCase("modificar")) {
						body.println(servico.remover(query));
					}
				}
			}

			body.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}