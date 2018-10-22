package com.stocos.gui;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import com.stocos.servidor.ServerListener;
import com.stocos.servidor.Servidor;

public class TabelaPanel extends JTable implements ServerListener {
	private static final long serialVersionUID = 1L;

	private String[] cabecalho = { "Tipo", "Dados" };

	private DefaultTableModel model;

	public TabelaPanel() {
		initComponents();
		Servidor.getInstance().addServerListener(this);
	}

	private void initComponents() {
		model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int linha, int col) {
				return false;
			}
		};
		model.setColumnIdentifiers(cabecalho);
		setRowHeight(getRowHeight() + 20);
		setModel(model);
		setFillsViewportHeight(true);
	}

	@Override
	public void onServerRequest(Request request) {
		String query = request.getPath() + "?" + request.getQuery().toString();
		String[] row = { "REQUEST", query };
		model.addRow(row);
	}

	@Override
	public void onServerStart() {
		model.setRowCount(0);
	}

	@Override
	public void onServerStop() {
	}

	@Override
	public void onServerResponse(Response response, String data) {
		String[] row = { "RESPONSE", data };
		model.addRow(row);
	}

}
