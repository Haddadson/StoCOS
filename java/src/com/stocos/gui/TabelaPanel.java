package com.stocos.gui;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.simpleframework.http.Request;

import com.stocos.servidor.ServerListener;
import com.stocos.servidor.Servidor;

public class TabelaPanel extends JTable implements ServerListener {
	private static final long serialVersionUID = 1L;

	private String[] cabecalho = { "Método", "Cliente", "Query" };

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
		setModel(model);
		setFillsViewportHeight(true);
	}

	@Override
	public void onServerRequest(Request request) {
		String method = request.getMethod();
		String address = request.getClientAddress().toString();
		String query = request.getQuery().toString();
		request.getClientAddress().toString();
		String[] row = { method, address, query };
		model.addRow(row);
	}

	@Override
	public void onServerStart() {
		model.setRowCount(0);
	}

	@Override
	public void onServerStop() {
	}

}
