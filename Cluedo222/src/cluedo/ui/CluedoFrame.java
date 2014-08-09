package cluedo.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import cluedo.main.Main;

public class CluedoFrame extends JFrame implements MouseListener{
	private CluedoCanvas canvas;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem item;
	

	public CluedoFrame(Board board){
		super("Cluedo");
		canvas = new CluedoCanvas(board);
		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);// add canvas
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // pack components tightly together
		//setResizable(false);// prevent us from being resizeable
		addMouseListener(this);
		menuBar = new JMenuBar();
		menu = new JMenu("Game");
		menuBar.add(menu);
		setJMenuBar(menuBar);
		item = new JMenuItem(new AbstractAction("New Game") {
			@Override
			public void actionPerformed(ActionEvent e) {
				Board b = new Board();
				canvas = new CluedoCanvas(b);
				new SelectCharFrame(b);
			}
		});
		menu.add(item);
		setSize(canvas.getSizeOfTile()*24,canvas.getSizeOfTile()*25+200);
		setVisible(true); // make sure we are visible!
		new SelectCharFrame(board);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}




}
