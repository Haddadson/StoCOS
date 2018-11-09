package com.stocos.aplicacao;

import com.stocos.servidor.Server;

public class Aplicacao {

	public static void main(String[] args) throws Exception {
		Server.getInstance().start();
		System.in.read();
	}
}
