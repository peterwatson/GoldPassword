package PassKee;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.*;



public class NewDatabasePanel extends JFrame
{
	
	private JTextField newDataPassTextField;//Text field instance variable
	private JButton okButton;//Button text field
	
	public NewDatabasePanel()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();//Menu bar
		setJMenuBar(menuBar);//Set menu bar
		
		JMenu mnNewMenu = new JMenu("File");//Menu instanece
		menuBar.add(mnNewMenu);//Add menu
		
		JMenuItem mntmOpenDatabase = new JMenuItem("Save Database");//Menu Item
		mnNewMenu.add(mntmOpenDatabase);
		
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));//Add layout to content pane

		okButton = new JButton("Ok");//Create new instance of JButton
		getContentPane().add(okButton);//Add button to content pane
		
		
		newDataPassTextField = new JTextField();//Create new Instance of JTextField
		getContentPane().add(newDataPassTextField);//Add text field to content pane
		newDataPassTextField.setColumns(24);//Set size of text field
		
		buttonOkGo();//Call button action method
	}
	
	public void buttonOkGo()//Action listener for OK button
	{
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent aE){
				String checkPassword = JOptionPane.showInputDialog("***re-type password***");//Display option pane
			}
		
		});
	}

	
}


