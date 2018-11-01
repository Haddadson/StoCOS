package com.stocos.persistencia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.stocos.entidades.Produto;
import com.stocos.entidades.RedeCosmeticos;
import com.stocos.entidades.Setor;

public class SetorDAO implements DAO<Setor, String> {

	@Override
	public Setor get(String nomeRede) {
		String strNomeRede = null;
		try (DataInputStream entrada = new DataInputStream(new FileInputStream(BancoDeDados.CAMINHO_SETOR))) {
			while ((strNomeRede = entrada.readUTF()) != null) {
				if (nomeRede.equals(strNomeRede)) {
					RedeCosmeticosDAO redeDAO = new RedeCosmeticosDAO();
					RedeCosmeticos rede = redeDAO.get(strNomeRede);
					if (rede == null)
						throw new Exception("Rede nao encontrada!");
					double capacidade = entrada.readDouble();
					Setor setor = new Setor(rede, capacidade);
					ProdutoDAO produtoDAO = new ProdutoDAO();
					List<Produto> produtos = produtoDAO.getAll();
					for (Produto p : produtos) {
						if (p.getMarca().equals(strNomeRede)) {
							setor.getListaProdutos().add(p);
						}
					}
					return setor;
				}
			}
		} catch (EOFException e) {
			System.out.println("ERRO ao ler o arquivo do disco rigido!" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("ERRO: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void add(Setor s) {
		try (DataOutputStream saida = new DataOutputStream(new FileOutputStream(BancoDeDados.CAMINHO_SETOR, true))) {
			saida.writeUTF(s.getRedeCosmeticos().getNome());
			saida.writeDouble(s.getCapacidade());
			saida.flush();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o Setor no disco!");
			e.printStackTrace();
		}
	}

	@Override
	public void update(Setor p) {
		List<Setor> setores = getAll();
		int index = setores.indexOf(p);
		if (index != -1) {
			setores.set(index, p);
			saveToFile(setores);
		}
	}

	@Override
	public void delete(Setor s) {
		List<Setor> setores = getAll();
		int index = setores.indexOf(s);
		if (index != -1) {
			setores.remove(index);
			saveToFile(setores);
		}
	}

	@Override
	public List<Setor> getAll() {
		List<Setor> setores = new ArrayList<Setor>();
		try (DataInputStream entrada = new DataInputStream(new FileInputStream(BancoDeDados.CAMINHO_SETOR))) {
			while (entrada.available() > 0) {
				String strNomeRede = entrada.readUTF();
				RedeCosmeticos rede = BancoDeDados.redeDAO.get(strNomeRede);

				if (rede == null)
					throw new Exception("Rede nao encontrada!");

				double capacidade = entrada.readDouble();
				Setor setor = new Setor(rede, capacidade);

				List<Produto> produtos = BancoDeDados.produtoDAO.getProdutosByRede(strNomeRede);
				setor.getListaProdutos().addAll(produtos);
				setores.add(setor);
			}
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("ERRO ao obter lista de Setores do disco rigido: " + e.getMessage());
		}
		return setores;
	}

	private void saveToFile(List<Setor> setores) {
		try (DataOutputStream saida = new DataOutputStream(new FileOutputStream(BancoDeDados.CAMINHO_SETOR, false))) {
			for (Setor s : setores) {
				add(s);
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar o Setor no disco!");
			e.printStackTrace();
		}
	}

}
