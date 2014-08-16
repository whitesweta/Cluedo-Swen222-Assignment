package cluedo.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cluedo.other.Card;
import cluedo.other.Player;
import cluedo.other.Position;
import cluedo.tile.BoardTile;

/**
 * Represent the frame that holds the canvas and the board All the panels and
 * buttons are created here
 * 
 * @author Shweta Barapatre and Maria Libunao
 *
 */
@SuppressWarnings("serial")
public class CluedoFrame extends JFrame implements WindowListener,
		MouseListener, ActionListener {
	private CluedoCanvas canvas;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem item;
	private JPanel cardViewer;
	private Board board;
	private boolean createdcardviewer = false;
	JPanel toAdd;
	Boolean createdtoAdd = false;

	/**
	 * Construct a new frame with all the Jmenus and panels needed for the
	 * cluedo game. creates a new canvas which in turn creates a new board
	 */
	public CluedoFrame() {
		super("Cluedo");
		this.canvas = new CluedoCanvas(this);
		board = canvas.getBoard();
		canvas.addMouseListener(this);
		setLayout(new BorderLayout());
		add(canvas, BorderLayout.CENTER);// add canvas
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
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
		add(Panel, BorderLayout.SOUTH);
		setSize(canvas.getSizeOfTile() * 24, canvas.getSizeOfTile() * 25 + 500);
		setVisible(true); // make sure we are visible!
		new SelectCharFrame(board, canvas, this);
	}

	/**
	 * Diposes of current frame and creates a new one for when the player
	 * selects new game in menu
	 */
	public void newGame() {
		this.dispose();
		CluedoFrame frame = new CluedoFrame();
	}

	/**
	 * creates the panel that encloses the two other panels two be added to the
	 * bottom of the main panel that holds the canvas
	 * 
	 * @return the bottom panel to be added to main panel
	 */
	public JPanel createPanelToAdd() {

		if (!createdtoAdd) {
			toAdd = new JPanel();
			createdtoAdd = true;
		}
		toAdd.removeAll();
		toAdd.setLayout(new GridLayout());
		toAdd.add(createButtonPanel(), BorderLayout.PAGE_START);
		revalidate();
		toAdd.add(createCardPanel(), BorderLayout.AFTER_LAST_LINE);
		return toAdd;

	}

	/**
	 * creates a panel for the cards reads the cards in the hand of the current
	 * player and displays these on the panel
	 * 
	 * @return card viewer panel
	 */
	public JPanel createCardPanel() {
		if (!createdcardviewer) {
			cardViewer = new JPanel();
			createdcardviewer = true;
		}
		cardViewer.removeAll();
		cardViewer.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		if (board.state == board.PLAYING) {
			Player current = board.getCurrentPlayer();
			ArrayList<Card> playersCards = current.getCards();
			for (int i = 0; i < playersCards.size(); i++) {
				System.out.println("drawing cards");
				JLabel picLabel = new JLabel(new ImageIcon(playersCards.get(i)
						.getImage()));
				if (i <= 2) {
					c.gridx = i;
					c.gridy = 0;
					cardViewer.add(picLabel, c);
				} else {
					System.out.println(i);
					c.gridx = i - 3;
					c.gridy = 1;
					cardViewer.add(picLabel, c);
				}

			}
		}

		return cardViewer;
	}

	/**
	 * creates a panel for the buttons, and add what to do when each of the
	 * buttons are pressed
	 * 
	 * @return button panel
	 */
	public JPanel createButtonPanel() {
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(4, 1));
		JButton rollDiceButton = new JButton("Roll Dice");// The JButton name.
		rollDiceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				board.diceRolled();
			}
		});
		buttons.add(rollDiceButton);// Add the button to the JFrame.
		JButton makeSuggestion = new JButton("Make Suggestion");
		makeSuggestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				board.makeSuggestion();
			}
		});
		buttons.add(makeSuggestion);
		JButton makeAccusation = new JButton("Make Accusation");
		makeAccusation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				board.makeAccusation();
			}
		});
		buttons.add(makeAccusation);
		JButton endTurn = new JButton("End Turn");
		endTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				board.endTurn();
				createPanelToAdd();
				toAdd.revalidate();
			}
		});
		buttons.add(endTurn);
		return buttons;
	}

	/**
	 * when the mouse is clicked it selected the tile that has been clicked and
	 * moves the player to that tile using the move method in board
	 */
	@Override
	public void mousePressed(MouseEvent e) {

		BoardTile selectedTile = null;
		for (int i = 0; i < board.getTiles().length; i++) {
			for (int j = 0; j < board.getTiles()[i].length; j++) {
				BoardTile tile = board.getTiles()[i][j];

				Position pos = tile.getPosition();
				int size = canvas.getSizeOfTile();
				int posx = pos.getX() * size;
				int posy = pos.getY() * size;
				// System.out.println(pos.getX()+"x"+pos.getY()+"y");
				if (e.getX() >= posx && e.getX() <= posx + size
						&& e.getY() >= posy && e.getY() <= posy + size) {

					selectedTile = tile;
					board.move(selectedTile.getPosition());
				}
			}

		}

	}

	public JPanel getCardViewer() {
		return cardViewer;
	}

	/**
	 * asks for confirmation when the close window button is pressed
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		int result = JOptionPane.showConfirmDialog(this,
				"Do you want to quit this game?", "Quit",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	// IMPLEMENTED METHODS THAT ARE NOT USED
	@Override
	public void windowDeactivated(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
