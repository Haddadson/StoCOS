package com.stocos.persistencia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.stocos.entidades.Produto;

public class ProdutoDAO implements DAO<Produto, String> {

	@Override
	public void add(Produto p) {		
		try (DataOutputStream saida = new DataOutputStream(new FileOutputStream("data/produto.dat", true))) {
			saida.writeUTF(p.getNome());
			saida.writeUTF(p.getCategoria());
			saida.writeUTF(p.getMarca());
			saida.writeInt(p.getQuantidade());
			saida.writeDouble(p.getVolume());
			saida.flush();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o Produto '" + p.getNome() + "' no disco!");
			e.printStackTrace();
		}
	}

	@Override
	public Produto get(String chaveNome) {
		String strNome = null;
		try (DataInputStream entrada = new DataInputStream(new FileInputStream("data/produto.dat"))) {
			while ((strNome = entrada.readUTF()) != null) {
				String nome = strNome;
				String categoria = entrada.readUTF();
				String marca = entrada.readUTF();
				int quantidade = entrada.readInt();
				double volume = entrada.readDouble();
				Produto p = new Produto(nome, marca, categoria, quantidade, volume);
				if (chaveNome.equals(strNome))
					return p;
			}
		} catch (EOFException e) {

		} catch (Exception e) {
			System.out.println("ERRO ao ler o Produto do disco rígido!");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Produto p) {
		List<Produto> produtos = getAll();
		int index = produtos.indexOf(p);
		if (index != -1) {
			produtos.set(index, p);
			saveToFile(produtos);
		}
	}

	@Override
	public void delete(Produto p) {
		List<Produto> produtos = getAll();
		int index = produtos.indexOf(p);
		if (index != -1) {
			produtos.remove(index);
			saveToFile(produtos);
		}
	}

	@Override
	public List<Produto> getAll() {
		List<Produto> produtos = new ArrayList<Produto>();
		String strNome;
		try (DataInputStream entrada = new DataInputStream(new FileInputStream("data/produto.dat"))) {
			while ((strNome = entrada.readUTF()) != null) {
				String nome = strNome;
				String categoria = entrada.readUTF();
				String marca = entrada.readUTF();
				int quantidade = entrada.readInt();
				double volume = entrada.readDouble();
				Produto p = new Produto(nome, marca, categoria, quantidade, volume);
				produtos.add(p);
			}
		} catch (EOFException e) {

		} catch (Exception e) {
			System.out.println("ERRO ao obter lista de Produtos do disco rígido!");
			e.printStackTrace();
		}
		return produtos;
	}

	private void saveToFile(List<Produto> produtos) {
		try (DataOutputStream saida = new DataOutputStream(new FileOutputStream("data/produto.dat", false))) {
			for (Produto p : produtos) {
				saida.writeUTF(p.getNome());
				saida.writeUTF(p.getCategoria());
				saida.writeUTF(p.getMarca());
				saida.writeInt(p.getQuantidade());
				saida.writeDouble(p.getVolume());
				saida.flush();
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o Bens duraveis no disco!");
			e.printStackTrace();
		}
	}

}
