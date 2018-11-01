package com.stocos.aplicacao;

import java.awt.EventQueue;

import com.stocos.gui.Janela;
import com.stocos.persistencia.BancoDeDados;

public class Aplicacao {
	public static void main(String[] list) {
		BancoDeDados.init();
		EventQueue.invokeLater(() -> Janela.getInstance().setVisible(true));
	}
}
