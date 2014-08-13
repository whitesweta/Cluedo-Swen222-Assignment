package cluedo.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cluedo.main.Main;
import cluedo.other.Card;
import cluedo.other.Player;

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
		setLayout(new BorderLayout());
		add(canvas,BorderLayout.CENTER);// add canvas
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // pack components tightly together
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
		
		JPanel Panel = createPanelToAdd();
		add(Panel,BorderLayout.SOUTH);
		setSize(canvas.getSizeOfTile()*24,(int) (canvas.getSizeOfTile()*25+3000));
		setVisible(true); // make sure we are visible!
		new SelectCharFrame(board);
	}
	
	public void newGame(){
		Board b = new Board();
		canvas = new CluedoCanvas(b);
		board = b;
		new SelectCharFrame(b);
	}

	public JPanel createPanelToAdd(){
		JPanel toAdd = new JPanel();
		toAdd.setLayout(new FlowLayout(FlowLayout.LEFT));
		toAdd.add(createButtonPanel());
		toAdd.add(createCardPanel());
		return toAdd;
		
	}
	
	public JPanel createCardPanel(){
		JPanel cards = new JPanel();
		cards.setLayout(new GridLayout(1,3));
		//cards.setBackground(Color.black);
		
		if(board.state==board.PLAYING){
		Player current = board.getCurrentPlayer();
		Set <Card> playersCards = current.getCards();
		
		for(Card card: playersCards){
			JLabel picLabel = new JLabel(new ImageIcon(card.getImage()));
			cards.add(picLabel);
		}
	}
		

		cards.add(new JLabel(new ImageIcon("src/cluedo/ui/images/BALLROOM.jpg"))); 
		cards.add(new JLabel(new ImageIcon("src/cluedo/ui/images/BILLIARD_ROOM.jpg")));
		cards.add(new JLabel(new ImageIcon("src/cluedo/ui/images/CANDLESTICK.jpg")));
		//cards.add(new JButton("WHY"));
		return cards;
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
				board.makeAccusation();
			}
			});
		buttons.add(makeAccusation);
		JButton endTurn = new JButton("End Turn");
		endTurn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
				board.endTurn();
				createCardPanel();
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
