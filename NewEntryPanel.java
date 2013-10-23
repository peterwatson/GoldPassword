package PassKee;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class NewEntryPanel extends JFrame
{
	private JTextField firstNameTextField;
	private JTextField secondNameTextField;
	private JTextField passwordTextField;
	private JTextField passwordRepeatTextField;
	private JLabel firstNameLabel;
	private JLabel secondNameLabel;
	private JLabel passwordLabel;
	private JLabel repeatPasswordLabel;
	private JButton generatePasswordButton;
	
	
	
	public NewEntryPanel()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();//Menu bar
		setJMenuBar(menuBar);//Set menu bar
		
		JMenu mnNewMenu = new JMenu("File");//Menu
		menuBar.add(mnNewMenu);//Add menu
		
		
		JMenuItem mntmOpenDatabase = new JMenuItem("Save Entry");//Menu Item
		mnNewMenu.add(mntmOpenDatabase);
		
		
		firstNameTextField = new JTextField();
		firstNameTextField.setColumns(10);
		
		secondNameTextField = new JTextField();
		secondNameTextField.setColumns(10);
		
		passwordTextField = new JPasswordField();
		
		passwordRepeatTextField = new JPasswordField();
		
		generatePasswordButton = new JButton("Generate");
		
		firstNameLabel = new JLabel("First Name");
		
		secondNameLabel = new JLabel("Last Name");
		
		passwordLabel = new JLabel("Password");
		
		repeatPasswordLabel = new JLabel("Repeat");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(17)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(firstNameLabel)
						.addComponent(secondNameLabel)
						.addComponent(passwordLabel)
						.addComponent(repeatPasswordLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(passwordRepeatTextField, Alignment.TRAILING)
						.addComponent(passwordTextField, Alignment.TRAILING)
						.addComponent(secondNameTextField, Alignment.TRAILING)
						.addComponent(firstNameTextField, Alignment.TRAILING))
					.addGap(43)
					.addComponent(generatePasswordButton)
					.addContainerGap(70, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(generatePasswordButton)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(firstNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(firstNameLabel))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(secondNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(secondNameLabel))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(passwordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(passwordLabel))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(passwordRepeatTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(repeatPasswordLabel))))
					.addContainerGap(108, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
		
		generateButtonGo();
		
	}
	
	public void generateButtonGo()
	{
		generatePasswordButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent aEx)
			{
				PasswordGeneratorPanel passGen = new PasswordGeneratorPanel();
				passGen.setVisible(true);
			}
		});
	}

}
