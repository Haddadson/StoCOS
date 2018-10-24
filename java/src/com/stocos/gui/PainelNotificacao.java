package com.stocos.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

import com.stocos.servidor.ServerListener;
import com.stocos.servidor.Servidor;

public class PainelNotificacao extends JPanel implements ServerListener {
	private static final long serialVersionUID = 1L;

	public static PainelNotificacao getInstance() {
		if (INSTANCE == null)
			INSTANCE = new PainelNotificacao();
		return INSTANCE;
	}

	private JTextField status;
	private JLabel flagLbl;

	private static final Color COR_DESLIGADO = new Color(60, 60, 60);
	private static final Color COR_LIGADO = new Color(60, 140, 60);

	private static PainelNotificacao INSTANCE;

	public PainelNotificacao() {
		initComponents();
		Servidor.getInstance().addServerListener(this);
	}

	private void initComponents() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(BorderFactory.createEtchedBorder());

		flagLbl = new JLabel();
		flagLbl.setOpaque(true);
		flagLbl.setBorder(BorderFactory.createEtchedBorder());
		flagLbl.setBackground(COR_DESLIGADO);
		flagLbl.setSize(new Dimension(30, 30));
		flagLbl.setPreferredSize(new Dimension(30, 30));
		flagLbl.setMinimumSize(new Dimension(30, 30));
		flagLbl.setMaximumSize(new Dimension(30, 30));

		status = new JTextField(30);
		status.setEditable(false);
		status.setText("O Servidor está desligado.");
		status.setBackground(new Color(60, 60, 60));
		status.setForeground(Color.WHITE);
		status.setFont(new Font("Console", Font.BOLD, 15));
		add(status);
	}

	@Override
	public void onServerStart(Servidor servidor) {
		status.setBackground(COR_LIGADO);
		status.setText("O Servidor está ligado em: " + servidor.getEndereco());
	}

	@Override
	public void onServerStop(Servidor servidor) {
		status.setBackground(COR_DESLIGADO);
		status.setText("O Servidor está desligado.");
	}

	@Override
	public void onServerRequest(Request request) {
	}

	@Override
	public void onServerResponse(Response response, String data) {
	}
}
