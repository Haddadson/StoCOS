package com.stocos.aplicacao;

import com.stocos.servidor.Servidor;

public class Aplicacao {

	private static void iniciarServidor() {
		new Thread(() -> Servidor.iniciar()).start();
	}

	public static void main(String[] list) {
		iniciarServidor();
	}
}
