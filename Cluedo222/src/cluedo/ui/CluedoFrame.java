//TESTING A THING. DELETE THIS
package cluedo.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
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
	private JPanel cardViewer;
	int number = 0;
	Board board;
	Boolean createdcardviewer = false;
	public CluedoFrame(){
		super("Cluedo");
		this.canvas = new CluedoCanvas(this);
		board = canvas.board;
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
		setSize(canvas.getSizeOfTile()*24,canvas.getSizeOfTile()*25+cardViewer.getHeight()+180);
		setVisible(true); // make sure we are visible!
		new SelectCharFrame(board, canvas);
	}
	
	public void newGame(){
		CluedoFrame frame = new CluedoFrame();
	}

	private CluedoCanvas getCanvas() {
		return canvas;
	}

	public JPanel createPanelToAdd(){
		JPanel toAdd = new JPanel();
		toAdd.setLayout(new GridLayout());
		toAdd.add(createButtonPanel(),BorderLayout.PAGE_START);
		toAdd.add(createCardPanel(),BorderLayout.AFTER_LAST_LINE);
		return toAdd;
		
	}
	
	public JPanel createCardPanel(){
		if(!createdcardviewer){
		cardViewer = new JPanel();
		createdcardviewer = true;
		}
		cardViewer.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		//cardViewer.setMaximumSize(new Dimension(78*3,250));
		if(board.state == board.PLAYING){
		Player current = board.getCurrentPlayer();
		ArrayList <Card> playersCards = current.getCards();
		//for(Card card:playersCards){
			for(int i=0; i<playersCards.size();i++){
			JLabel picLabel = new JLabel(new ImageIcon(playersCards.get(i).getImage()));
			if(i<=2){
				c.gridx=i;
				c.gridy=0;
				cardViewer.add(picLabel,c);
			}
			else{
				System.out.println(i);
				c.gridx=i-3;
				c.gridy=1;
				cardViewer.add(picLabel,c);
			}
			
		}
	}

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

		BoardTile selectedTile = null;
		for (int i = 0; i < board.getTiles().length; i++) {
			for (int j = 0; j < board.getTiles()[i].length; j++) {
				BoardTile tile = board.getTiles()[i][j];
				
				Position pos = tile.getPosition();
				int size= canvas.getSizeOfTile();
				int posx = pos.getX()*size;
				int posy = pos.getY()*size;
				//System.out.println(pos.getX()+"x"+pos.getY()+"y");
				if(e.getX()>=posx&&e.getX()<=posx+size&&e.getY()>=posy&&e.getY()<=posy+size){

					selectedTile=tile;
					board.move(selectedTile.getPosition());
				}
			}
		
		}

	}
	
	
	

	public JPanel getCardViewer() {
		return cardViewer;
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
