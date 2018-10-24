package com.stocos.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import com.stocos.servidor.ServerListener;
import com.stocos.servidor.Servidor;

public class TabelaPanel extends JTable implements ServerListener {
	private static final long serialVersionUID = 1L;

	private static TabelaPanel INSTANCE;

	public static TabelaPanel getInstance() {
		if (INSTANCE == null)
			INSTANCE = new TabelaPanel();
		return INSTANCE;
	}

	private String[] cabecalho = { "Tipo", "Dados" };

	private DefaultTableModel model;

	public TabelaPanel() {
		initComponents();
		Servidor.getInstance().addServerListener(this);
	}

	private void initModel() {
		model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int linha, int col) {
				return false;
			}
		};
		model.setColumnIdentifiers(cabecalho);
		setModel(model);
	}

	private void initAparencia() {
		setRowHeight(getRowHeight() + 20);
		setFillsViewportHeight(true);
		setBackground(new Color(240, 240, 240));
		setForeground(Color.BLACK);
		setFont(new Font("Console", Font.BOLD, 14));
		setGridColor(new Color(220, 220, 220));
		setShowGrid(true);
	}

	private void ajustarTamanhoColunas() {
		getColumnModel().getColumn(0).setMinWidth(150);
		getColumnModel().getColumn(0).setMaxWidth(150);
	}

	private void initComponents() {
		initModel();
		initAparencia();
		ajustarTamanhoColunas();
	}

	public void limpar() {
		model.setRowCount(0);
	}

	@Override
	public void onServerRequest(Request request) {
		String query = request.getPath() + "?" + request.getQuery().toString();
		String[] row = { "REQUEST", query };
		model.addRow(row);
	}

	@Override
	public void onServerStart(Servidor servidor) {
		limpar();
	}

	@Override
	public void onServerStop(Servidor servidor) {
	}

	@Override
	public void onServerResponse(Response response, String data) {
		String[] row = { "RESPONSE", data };
		model.addRow(row);
	}

}
