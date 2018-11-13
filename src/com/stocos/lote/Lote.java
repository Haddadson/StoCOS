package com.stocos.lote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Lote {

	private UUID idProduto, idRede;
	private int quantidade, status; // Status:
									// 1 - AGENDADO PARA ENTREGA
									// 2 - ENTREGUE
									// 3 - AGENDADO PARA RETIRADA
									// 4 - RETIRADO

	private LocalDate agendamento, entrega;
	private LocalDateTime validade;

	public Lote(UUID idRede, LocalDate agendamento, LocalDate entrega, UUID idProduto, LocalDateTime validade,
			int quantidade, int status) {
		setIdProduto(idProduto);
		setIdRede(idRede);
		setDataValidade(validade);
		setQuantidade(quantidade);
		setDataAgendamento(agendamento);
		setDataEntrega(entrega);
		setStatus(status);
	}

	// GETTERS

	public int getStatus() {
		return status;
	}

	public UUID getIdRede() {
		return idRede;
	}

	public LocalDate getDataAgendamento() {
		return agendamento;
	}

	public LocalDate getDataEntrega() {
		return entrega;
	}

	public UUID getIdProduto() {
		return idProduto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public LocalDateTime getDataValidade() {
		return validade;
	}

	// SETTERS

	public void setStatus(int status) {
		this.status = status;
	}

	public void setIdRede(UUID idRede) {
		this.idRede = idRede;
	}

	public void setDataAgendamento(LocalDate agendamento) {
		this.agendamento = agendamento;
	}

	public void setDataEntrega(LocalDate entrega) {
		this.entrega = entrega;
	}

	public void setIdProduto(UUID idProduto) {
		this.idProduto = idProduto;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public void setDataValidade(LocalDateTime validade) {
		this.validade = validade;
	}

}
