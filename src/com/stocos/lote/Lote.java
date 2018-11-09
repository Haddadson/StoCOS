package com.stocos.lote;

import java.time.LocalDateTime;
import java.util.UUID;

public class Lote {

	private UUID idProduto;
	private int quantidade;
	private LocalDateTime validade;

	public Lote(UUID idProduto, LocalDateTime validade, int quantidade) {
		setIdProduto(idProduto);
		setValidade(validade);
		setQuantidade(quantidade);
	}

	// GETTERS

	public UUID getIdProduto() {
		return idProduto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public LocalDateTime getValidade() {
		return validade;
	}

	// SETTERS

	public void setIdProduto(UUID idProduto) {
		this.idProduto = idProduto;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public void setValidade(LocalDateTime validade) {
		this.validade = validade;
	}

}
