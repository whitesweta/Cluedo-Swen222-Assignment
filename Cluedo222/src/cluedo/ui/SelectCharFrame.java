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
	public SelectCharFrame(){
		super("Select Character");
		askForNumber();
		setLayout(new FlowLayout());
		group = new ButtonGroup();
		add(new JLabel("Choose a character:"));
		for(int i = 0; i < characters.size();i++){
			JRadioButton button = new JRadioButton(characters.get(i));
			button.setActionCommand(characters.get(i));
			group.add(button);
			add(button);
		}
		add(new JLabel("Please enter your name"));
		nameEntry = new JTextField(15);
		add(nameEntry);
		setMinimumSize(new Dimension(200, 325));
		JButton done = new JButton("Done");
		done.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a) {
				String chara = group.getSelection().getActionCommand();
				String name = nameEntry.getText();
				System.out.println(chara+name);
				}});
		add(done);
		setVisible(true);
	}
	
	private int askForNumber(){
		Object[] options = {3,4,5,6};
		Integer numPlayer = null;
		while(numPlayer == null){
		numPlayer = (Integer) JOptionPane.showInputDialog(null,"How many players?",
				"Number of Players",JOptionPane.PLAIN_MESSAGE,null,options,"3");
		}
		return numPlayer;
	}
	public static void main (String[] args){
		new SelectCharFrame();
	}
	
	
}
