package com.stocos.persistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.stocos.entidades.Estoque;
import com.stocos.entidades.Setor;

public class BancoDeDados {

	public static String CAMINHO_PRODUTO = "data/produto.dat";
	public static String CAMINHO_SETOR = "data/setor.dat";
	public static String CAMINHO_REDECOSMETICOS = "data/redecosmeticos.dat";

	public static RedeCosmeticosDAO redeDAO = new RedeCosmeticosDAO();
	public static ProdutoDAO produtoDAO = new ProdutoDAO();
	public static SetorDAO setorDAO = new SetorDAO();

	// Carrega o conteudo dos arquivos .dat para os objetos do estoque
	public static void init() {
		iniciarArquivos();
		List<Setor> setores = setorDAO.getAll();
		System.out.println(setores);
		setores.forEach(s -> Estoque.getInstance().adicionarSetor(s));
	}

	// Cria os arquivos .dat caso nao existam
	private static void iniciarArquivos() {
		try {
			if (!Files.exists(Paths.get(CAMINHO_PRODUTO))) {
				new File(CAMINHO_PRODUTO).createNewFile();
			}
			if (!Files.exists(Paths.get(CAMINHO_SETOR))) {
				new File(CAMINHO_SETOR).createNewFile();
			}
			if (!Files.exists(Paths.get(CAMINHO_REDECOSMETICOS))) {
				new File(CAMINHO_REDECOSMETICOS).createNewFile();
			}
		} catch (IOException e) {
			System.err.println("Erro ao criar os arquivos!");
		} catch (Exception e) {
			System.err.println("Erro: " + e.getMessage());
		}
	}
}
