package com.stocos.aplicacao;

import java.awt.EventQueue;
import java.util.List;

import com.stocos.entidades.Produto;
import com.stocos.gui.Janela;
import com.stocos.persistencia.ProdutoDAO;

public class Aplicacao {
	public static void main(String[] list) {
		EventQueue.invokeLater(() -> Janela.getInstance().setVisible(true));

		teste();
	}

	private static void teste() {
		ProdutoDAO dao = new ProdutoDAO();
		List<Produto> produtos = dao.getAll();
		produtos.forEach(p -> System.out.println(p.getNome()));
	}
}
