package com.stocos.persistencia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.stocos.entidades.RedeCosmeticos;

public class RedeCosmeticosDAO implements DAO<RedeCosmeticos, String> {

	@Override
	public RedeCosmeticos get(String chave) {
		String strNome = null;
		try (DataInputStream entrada = new DataInputStream(new FileInputStream(BancoDeDados.CAMINHO_REDECOSMETICOS))) {
			while ((strNome = entrada.readUTF()) != null) {
				String email = entrada.readUTF();
				String endereco = entrada.readUTF();
				String telefone = entrada.readUTF();
				RedeCosmeticos r = new RedeCosmeticos(strNome, endereco, email, telefone);
				if (chave.equals(strNome))
					return r;
			}
		} catch (EOFException e) {

		} catch (Exception e) {
			System.out.println("ERRO ao ler a Rede de Cosmeticos do disco rigido!");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void add(RedeCosmeticos r) {
		try (DataOutputStream saida = new DataOutputStream(
				new FileOutputStream(BancoDeDados.CAMINHO_REDECOSMETICOS, true))) {
			saida.writeUTF(r.getNome());
			saida.writeUTF(r.getEmail());
			saida.writeUTF(r.getEndereco());
			saida.writeUTF(r.getTelefone());
			if (r.getSetor() != null)
				saida.writeInt(r.getSetor().getId());
			else
				saida.writeInt(-1);
			saida.flush();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar a Rede de Cosmeticos no disco!");
			e.printStackTrace();
		}
	}

	@Override
	public void update(RedeCosmeticos r) {
		List<RedeCosmeticos> redes = getAll();
		int index = redes.indexOf(r);
		if (index != -1) {
			redes.set(index, r);
			saveToFile(redes);
		}
	}

	@Override
	public void delete(RedeCosmeticos r) {
		List<RedeCosmeticos> redes = getAll();
		int index = redes.indexOf(r);
		if (index != -1) {
			redes.remove(index);
			saveToFile(redes);
		}
	}

	@Override
	public List<RedeCosmeticos> getAll() {
		List<RedeCosmeticos> redes = new ArrayList<RedeCosmeticos>();
		String strNome;
		try (DataInputStream entrada = new DataInputStream(new FileInputStream(BancoDeDados.CAMINHO_REDECOSMETICOS))) {
			while ((strNome = entrada.readUTF()) != null) {
				String email = entrada.readUTF();
				String endereco = entrada.readUTF();
				String telefone = entrada.readUTF();
				RedeCosmeticos p = new RedeCosmeticos(strNome, endereco, email, telefone);
				redes.add(p);
			}
		} catch (EOFException e) {

		} catch (Exception e) {
			System.out.println("ERRO ao obter lista de Redes de Cosmeticos do disco rigido!");
			e.printStackTrace();
		}
		return redes;
	}

	private void saveToFile(List<RedeCosmeticos> redes) {
		try (DataOutputStream saida = new DataOutputStream(
				new FileOutputStream(BancoDeDados.CAMINHO_REDECOSMETICOS, false))) {
			for (RedeCosmeticos r : redes) {
				saida.writeUTF(r.getNome());
				saida.writeUTF(r.getEmail());
				saida.writeUTF(r.getEndereco());
				saida.writeUTF(r.getTelefone());
				saida.writeInt(r.getSetor().getId());
				saida.flush();
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar a lista no disco!");
			e.printStackTrace();
		}
	}

}
