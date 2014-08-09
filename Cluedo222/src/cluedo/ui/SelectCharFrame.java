package cluedo.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cluedo.other.Player;
import cluedo.other.Character;

@SuppressWarnings("serial")
public class SelectCharFrame extends JFrame{
	private List<JRadioButton> jrButtons = new ArrayList<JRadioButton>();
	private ButtonGroup group;
	private JTextField nameEntry;
	private Board board;
	private int numPlayer;

	public SelectCharFrame(Board b){
		super("Select Character");
		board = b;
		askForNumber(); //loop for this many times
		setLayout(new FlowLayout());
		group = new ButtonGroup();
		add(new JLabel("Choose a character:"));
		for(Character.Name chara : Character.Name.values()){
			JRadioButton button = new JRadioButton(chara.toString());
			jrButtons.add(button);
			button.setActionCommand(chara.toString());
			group.add(button);
			add(button);
		}
		jrButtons.get(0).setSelected(true);
		add(new JLabel("Please enter your name"));
		nameEntry = new JTextField(15);
		add(nameEntry);
		setMinimumSize(new Dimension(200, 325));
		JButton done = new JButton("Done");
		done.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				if(numPlayer == 0){return;}
				String chara = group.getSelection().getActionCommand();
				String name = nameEntry.getText();
				if(name.equals("")){
					JOptionPane.showMessageDialog(null,"Please enter your name!");
				}
				else{
					System.out.println(chara+name);
					board.addPlayer(new Player(new Character(Character.Name.valueOf(chara)),name));
					removeFromOptions(chara);
					nameEntry.setText("");
					numPlayer--;
					if(numPlayer == 0){
						setVisible(false);
						board.readyToStart();
					}
				}

				}});
		add(done);
		setVisible(true);
	}

	private void removeFromOptions(String buttonName){
		int i = 0;
		for(; i<jrButtons.size();i++){
			JRadioButton jrb = jrButtons.get(i);
			if(buttonName.equals(jrb.getText())){
				group.remove(jrb);
				this.remove(jrb);
				break;
			}
		}
		jrButtons.remove(i);
		jrButtons.get(0).setSelected(true);
		validate();
	}

	private void askForNumber(){
		Object[] options = {3,4,5,6};
		Integer num = null;
		while(num == null){
		num = (Integer) JOptionPane.showInputDialog(null,"How many players?",
				"Number of Players",JOptionPane.PLAIN_MESSAGE,null,options,"3");
		}
		numPlayer = num;
	}

}
