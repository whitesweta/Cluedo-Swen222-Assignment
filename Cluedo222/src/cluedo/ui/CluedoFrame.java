package cluedo.ui;

import java.awt.BorderLayout;

import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Set;

import javax.swing.AbstractAction;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import cluedo.other.Card;
import cluedo.other.Player;
import cluedo.other.Position;
import cluedo.tile.BoardTile;

public class CluedoFrame extends JFrame implements MouseListener, ActionListener{
	private CluedoCanvas canvas;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem item;
	private Board board;
	private JPanel cardViewer;
	
	public CluedoFrame(Board board){
		super("Cluedo");
		this.board = board;
		canvas = new CluedoCanvas(board);
		canvas.addMouseListener(this);
		setLayout(new BorderLayout());
		add(canvas,BorderLayout.CENTER);// add canvas
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack(); // pack components tightly together
		
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
		//toAdd.setLayout(new FlowLayout(FlowLayout.LEFT));
		toAdd.setLayout(new BorderLayout());
		toAdd.add(createButtonPanel(),BorderLayout.WEST);
		toAdd.add(createCardPanel(),BorderLayout.EAST);
		return toAdd;
		
	}
	
	public JPanel createCardPanel(){
		cardViewer = new JPanel();
		cardViewer.setLayout(new GridLayout(1,3));
		//cards.setBackground(Color.black);
		
		if(board.state == board.PLAYING){
		Player current = board.getCurrentPlayer();
		Set <Card> playersCards = current.getCards();
		
		for(Card card: playersCards){
			JLabel picLabel = new JLabel(new ImageIcon(card.getImage()));
			cardViewer.add(picLabel);
		}
	}
		

		cardViewer.add(new JLabel(new ImageIcon("src/cluedo/ui/images/BALLROOM.jpg"))); 
		cardViewer.add(new JLabel(new ImageIcon("src/cluedo/ui/images/BILLIARD_ROOM.jpg")));
		cardViewer.add(new JLabel(new ImageIcon("src/cluedo/ui/images/CANDLESTICK.jpg")));
		//cards.add(new JButton("WHY"));
		return cardViewer;
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
		System.out.println("recognised clicked");
		BoardTile selectedTile = null;
		for (int i = 0; i < board.getTiles().length; i++) {
			for (int j = 0; j < board.getTiles()[i].length; j++) {
				BoardTile tile = board.getTiles()[i][j];
				
				Position pos = tile.getPosition();
				int size= canvas.getSizeOfTile();
				int posx = pos.getX()*size;
				int posy = pos.getY()*size;
				System.out.println(pos.getX()+"x"+pos.getY()+"y");
				if(e.getX()>=posx&&e.getX()<=posx+size&&e.getY()>=posy&&e.getY()<=posy+size){
					System.out.println("in here");
					selectedTile=tile;
					System.out.println(selectedTile+" selected tile");
					board.move(selectedTile.getPosition());
				}
			}
		
		}

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
