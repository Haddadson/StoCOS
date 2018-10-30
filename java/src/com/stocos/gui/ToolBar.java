package com.stocos.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import com.stocos.servidor.Servidor;

public class ToolBar extends JToolBar implements ActionListener {
	private static final long serialVersionUID = 1L;

	private static ToolBar INSTANCE;

	public static ToolBar getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ToolBar();
		return INSTANCE;
	}

	public ToolBar() {
		initComponents();
	}

	private void initComponents() {
		setOrientation(JToolBar.VERTICAL);
		setBorder(BorderFactory.createEtchedBorder());

		JToggleButton tbtn;
		ButtonGroup btnGroup = new ButtonGroup();

		tbtn = new JToggleButton(new ImageIcon("res/icones/play.png"));
		tbtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tbtn.setActionCommand("iniciar");
		tbtn.addActionListener(this);
		btnGroup.add(tbtn);
		add(tbtn);

		tbtn = new JToggleButton(new ImageIcon("res/icones/stop.png"));
		tbtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tbtn.setSelected(true);
		tbtn.setActionCommand("parar");
		tbtn.addActionListener(this);
		btnGroup.add(tbtn);
		add(tbtn);

		JButton btn;
		btn = new JButton(new ImageIcon("res/icones/settings.png"));
		btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		btn.setToolTipText("Mudar Porta");
		btn.setActionCommand("porta");
		btn.addActionListener(this);
		add(btn);

		add(Box.createHorizontalGlue());

		btn = new JButton(new ImageIcon("res/icones/clear.png"));
		btn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		btn.setToolTipText("Limpar Tabela");
		btn.setActionCommand("limpar");
		btn.addActionListener(this);
		add(btn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("iniciar".equals(e.getActionCommand())) {
			Servidor.getInstance().start();

		} else if ("parar".equals(e.getActionCommand())) {
			Servidor.getInstance().stop();

		} else if ("limpar".equals(e.getActionCommand())) {
			TabelaPanel.getInstance().limpar();

		} else if ("porta".equals(e.getActionCommand())) {
			try {
				int novaPorta = Integer.parseInt(JOptionPane.showInputDialog(this, "Insira a nova porta:"));
				Servidor.getInstance().setPorta(novaPorta);
				JOptionPane.showMessageDialog(this,
						"Nova porta configurada.\nAs alterações farão efeito quando \no servidor for iniciado novamente.",
						"Certo", JOptionPane.INFORMATION_MESSAGE);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Entrada inválida: Insira apenas numeros!", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
