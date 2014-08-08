package cluedo.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;

@SuppressWarnings("serial")
public class SelectCharFrame extends JFrame{
	private List<String> characters = Arrays.asList("Miss Scarlet","Colonel Mustard","Mrs. White", "Reverend Green","Mrs.Peacock", "Professor Plum");
	private ButtonGroup group;
	private JTextField nameEntry;
	private int numPlayer;
	public SelectCharFrame(){
		super("Select Character");
		askForNumber(); //loop for this many times
		setLayout(new FlowLayout());
		group = new ButtonGroup();
		add(new JLabel("Choose a character:"));
		JRadioButton button = null;
		for(int i = 0; i < characters.size();i++){
			button = new JRadioButton(characters.get(i));
			button.setActionCommand(characters.get(i));
			group.add(button);
			add(button);
		}
		button.setSelected(true);
		add(new JLabel("Please enter your name"));
		nameEntry = new JTextField(15);
		add(nameEntry);
		setMinimumSize(new Dimension(200, 325));
		JButton done = new JButton("Done");
		done.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				String chara = group.getSelection().getActionCommand();
				String name = nameEntry.getText();
				if(name.equals("")){
					JOptionPane.showMessageDialog(null,"Please enter your name!");
				}
				else{
					System.out.println(chara+name);
					//make new player
					nameEntry.setText("");
					numPlayer--;
					//if numPlayer == 0 stop this
				}
				
				}});
		add(done);
		setVisible(true);
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
	public static void main (String[] args){
		new SelectCharFrame();
	}
	
	
}
