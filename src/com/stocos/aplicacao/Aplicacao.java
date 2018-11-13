package com.stocos.aplicacao;

import com.stocos.servidor.Server;

public class Aplicacao {
	public static void main(String[] args) {
		Server.getInstance().start();
	}
}
