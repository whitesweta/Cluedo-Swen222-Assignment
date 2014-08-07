package cluedo.ui;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class CluedoFrame extends JFrame implements MouseListener{
	private CluedoCanvas canvas;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem item;
	public CluedoFrame(){
		super("Cluedo");
		canvas = new CluedoCanvas(null);
		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);// add canvas
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // pack components tightly together
		setResizable(false);// prevent us from being resizeable
		addMouseListener(this);
		menuBar = new JMenuBar();
		menu = new JMenu("Game");
		menuBar.add(menu);
		setJMenuBar(menuBar);
		item = new JMenuItem("New Game", KeyEvent.VK_T);
		menu.add(item);
		setSize(550,580);
		setVisible(true); // make sure we are visible!
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
	
	public static void main(String[] args){
		new CluedoFrame();
	}

	

}
