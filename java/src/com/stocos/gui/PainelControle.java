package com.stocos.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import com.stocos.servidor.Servidor;

public class PainelControle extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTextField status;

	private JLabel flagLbl;
	private static final Color COR_DESLIGADO = new Color(220, 10, 10);
	private static final Color COR_LIGADO = new Color(10, 220, 10);

	public PainelControle() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		ButtonGroup btnGroup = new ButtonGroup();

		JToggleButton btn = new JToggleButton(new ImageIcon("res/icones/play.png"));
		btn.setActionCommand("iniciar");
		btn.addActionListener(this);
		btnGroup.add(btn);
		add(btn);

		btn = new JToggleButton(new ImageIcon("res/icones/stop.png"));
		btn.setSelected(true);
		btn.setActionCommand("parar");
		btn.addActionListener(this);
		btnGroup.add(btn);
		add(btn);

		status = new JTextField(30);
		status.setEditable(false);
		status.setText("O Servidor está desligado.");
		status.setBackground(new Color(60, 60, 60));
		status.setForeground(Color.WHITE);
		status.setFont(new Font("Console", Font.BOLD, 15));
		add(status);

		flagLbl = new JLabel();
		flagLbl.setOpaque(true);
		flagLbl.setBackground(COR_DESLIGADO);
		flagLbl.setSize(new Dimension(30, 30));
		flagLbl.setPreferredSize(new Dimension(30, 30));
		flagLbl.setMinimumSize(new Dimension(30, 30));
		flagLbl.setMaximumSize(new Dimension(30, 30));

		add(flagLbl);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("iniciar".equals(e.getActionCommand())) {
			try {
				status.setText("Iniciando o servidor...");
				Servidor.getInstance().init();
				status.setText("Escutando em " + Servidor.getInstance().getEndereco());
				flagLbl.setBackground(COR_LIGADO);
			} catch (Exception ex) {

			}
		} else if ("parar".equals(e.getActionCommand())) {
			try {
				status.setText("Parando o servidor...");
				Servidor.getInstance().stop();
				status.setText("O Servidor está desligado.");
				flagLbl.setBackground(COR_DESLIGADO);
			} catch (Exception ex) {

			}
		}

	}

}
