package com.stocos.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.stocos.servidor.Servidor;

public class Janela extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;

	private static Janela INSTANCE;

	private TabelaPanel tabelaPanel;

	public static Janela getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Janela();
		return INSTANCE;
	}

	private Janela() {
		super("Servidor - StoCOS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLookAndFeel();
		initComponents();
		pack();
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		addWindowListener(this);
	}

	public TabelaPanel getTabelaPanel() {
		return tabelaPanel;
	}

	private static void setLookAndFeel() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					return;
				}
			}
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initComponents() {
		JPanel painelPrincipal = new JPanel(new BorderLayout());
		setContentPane(painelPrincipal);
		painelPrincipal.add(new JScrollPane(tabelaPanel = new TabelaPanel()), BorderLayout.CENTER);
		painelPrincipal.add(new PainelControle(), BorderLayout.SOUTH);
		painelPrincipal.setPreferredSize(new Dimension(450, 200));
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		Servidor.getInstance().stop();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

}
