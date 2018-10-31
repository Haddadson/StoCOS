package com.stocos.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

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
		setFillsViewportHeight(false);
		setBackground(new Color(240, 240, 240));
		setForeground(Color.BLACK);
		setFont(new Font("Console", Font.BOLD, 14));
		setGridColor(new Color(220, 220, 220));
		setShowGrid(true);
	}

	public boolean isVazio() {
		return model.getRowCount() == 0;
	}

	private void configurarColunas() {
		getColumnModel().getColumn(0).setMinWidth(150);
		getColumnModel().getColumn(0).setMaxWidth(150);

		getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object obj, boolean selected, boolean hasFocus,
					int row, int column) {

				JLabel lbl = new JLabel((String) obj);
				lbl.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 3));
				lbl.setFont(new Font("Console", Font.BOLD, 15));

				if (obj.equals("REQUISIÇÃO")) {
					lbl.setIcon(new ImageIcon("res/icones/down-arrow.png"));
				} else if (obj.equals("RESPOSTA")) {
					lbl.setIcon(new ImageIcon("res/icones/up-arrow.png"));
				}
				return lbl;
			}
		});

	}

	private void initComponents() {
		initModel();
		initAparencia();
		configurarColunas();
	}

	public void limpar() {
		model.setRowCount(0);
	}

	@Override
	public void onServerRequest(Request request) {
		String query = request.getPath() + "?" + request.getQuery().toString();
		String[] row = { "REQUISIÇÃO", query };
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
		String[] row = { "RESPOSTA", data };
		model.addRow(row);
	}

}
