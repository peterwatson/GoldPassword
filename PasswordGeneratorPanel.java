package PassKee;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.*;
import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
public class PasswordGeneratorPanel extends JFrame
{
	private JMenuItem newDatabaseItem;
	private JMenuItem newEntryItem;
	private JCheckBox chckbxUpperCaseLetters;
	private JCheckBox chckbxLowerCaseLetters;
	private JCheckBox chckbxNumbers;
	private JPasswordField passwordField;
	private JButton btnGenerate;

	
	public PasswordGeneratorPanel()
	{
		
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	
	JMenuBar menuBar = new JMenuBar();//Menu bar
	setJMenuBar(menuBar);//Set menu bar
	
	JMenu mnNewMenu = new JMenu("File");//Menu
	menuBar.add(mnNewMenu);//Add menu
	
	newDatabaseItem = new JMenuItem("Create New Database");//Menu item
	mnNewMenu.add(newDatabaseItem);//Add menu item
	
	newEntryItem = new JMenuItem("Create New Entry");//Menu Item
	mnNewMenu.add(newEntryItem);
	
	chckbxUpperCaseLetters = new JCheckBox("Upper Case Letters");
	
	chckbxLowerCaseLetters = new JCheckBox("Lower Case Letters");
	
	chckbxNumbers = new JCheckBox("Numbers");
	
	passwordField = new JPasswordField();
	
	btnGenerate = new JButton("Generate");
	GroupLayout groupLayout = new GroupLayout(getContentPane());
	groupLayout.setHorizontalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
					.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(chckbxUpperCaseLetters)
							.addComponent(chckbxLowerCaseLetters)
							.addComponent(chckbxNumbers)))
					.addGroup(groupLayout.createSequentialGroup()
						.addGap(125)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(btnGenerate)))
				.addContainerGap(26, Short.MAX_VALUE))
	);
	groupLayout.setVerticalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
					.addComponent(btnGenerate)
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(chckbxUpperCaseLetters)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(chckbxLowerCaseLetters)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(chckbxNumbers)
						.addGap(96)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(50, Short.MAX_VALUE))
	);
	getContentPane().setLayout(groupLayout);
	
	
	
	}

}
