package cluedo.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import cluedo.main.Main;

public class CluedoFrame extends JFrame implements MouseListener, ActionListener{
	private CluedoCanvas canvas;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem item;
	private Board board;
	
	public CluedoFrame(Board board){
		super("Cluedo");
		this.board = board;
		canvas = new CluedoCanvas(board);
		
		final int DICE_BUTTON_LOCATION_X = 30;  // location x 
		final int  DICE_BUTTON_LOCATION_Y = canvas.getSizeOfTile()*25+30;   // location y 
		//private static final int BUTTON_SIZE_X = 140;      // size height
		//private static final int BUTTON_SIZE_Y = 50;       // size width
		setLayout(new BorderLayout());
		add(canvas,BorderLayout.CENTER);// add canvas
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
				newGame();
			}
		});
		menu.add(item);
		add(createButtonPanel(),BorderLayout.SOUTH);
		setSize(canvas.getSizeOfTile()*24,canvas.getSizeOfTile()*25+createButtonPanel().getHeight());
		setVisible(true); // make sure we are visible!
		new SelectCharFrame(board);
	}
	
	public void newGame(){
		Board b = new Board();
		canvas = new CluedoCanvas(b);
		board = b;
		new SelectCharFrame(b);
	}

	public JPanel createButtonPanel(){
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(4,1));
		JButton rollDiceButton = new JButton("Roll Dice");//The JButton name.
		rollDiceButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
				board.diceRolled();
			}
			});
		buttons.add(rollDiceButton);//Add the button to the JFrame.
		JButton makeSuggestion = new JButton("Make Suggestion");
		makeSuggestion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
				board.makeSuggestion();
			}
			});
		buttons.add(makeSuggestion);
		JButton makeAccusation = new JButton("Make Accusation");
		makeAccusation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
				//make accusation
			}
			});
		buttons.add(makeAccusation);
		JButton endTurn = new JButton("End Turn");
		endTurn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
				board.endTurn();
			}
			});
		buttons.add(endTurn);		
		return buttons;
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


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}




}
