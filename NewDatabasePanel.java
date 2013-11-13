package PassKee;

import java.awt.*;
import javax.swing.*;

import java.sql.*;

import javax.swing.JFrame;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.*;



public class NewDatabasePanel extends JFrame implements ActionListener
{
	
	private JPasswordField firstPasswordTextField;
	private JPasswordField secondPasswordTextField;
	private JButton okButton;
	private JLabel lblNewLabel;
	private JLabel label;
	private String firstPassword;
	private String secondPassword;
	private Connection connection;
	
	private JOptionPane optionPaneErrorMessage;
	private JOptionPane optionPaneSuccessMessage;
	
	public NewDatabasePanel()//Constructor
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();//Menu bar
		setJMenuBar(menuBar);//Set menu bar
		
		JMenu mnNewMenu = new JMenu("File");//Menu instanece
		menuBar.add(mnNewMenu);//Add menu
		
		JMenuItem mntmOpenDatabase = new JMenuItem("Save Database");//Menu Item
		mnNewMenu.add(mntmOpenDatabase);
		firstPasswordTextField = new JPasswordField();
		firstPasswordTextField.setColumns(10);
		
		secondPasswordTextField = new JPasswordField();
		secondPasswordTextField.setColumns(10);
		
		okButton = new JButton("Submit");
		
		lblNewLabel = new JLabel("Password");
		
		label = new JLabel("Repeat");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(78)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(okButton)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(label)
								.addComponent(lblNewLabel))
							.addGap(12)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(firstPasswordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(secondPasswordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(176, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(24)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(firstPasswordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(secondPasswordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label))
					.addGap(18)
					.addComponent(okButton)
					.addContainerGap(126, Short.MAX_VALUE))
		);
		
		getContentPane().setLayout(groupLayout);//Add layout to content pane
		
		okButton.addActionListener(this);//Add action listener to button
		
		
		
	}//End constructor
	


	public void setFirstPassword()//Store first password from text field
	{ firstPassword = firstPasswordTextField.getText(); }
	
	public String getFirstPassword()//Return password
	{ return firstPassword; }
	
	public void setSecondPassword()//Store second password from text field
	{ secondPassword = secondPasswordTextField.getText(); }
	
	public String getSecondPassword()//Return second password
	{ return secondPassword; }
	
	public void checkPasswordsMatch()
	{//Method start
		
		if(getFirstPassword().equals(getSecondPassword()))//Check if passwords match
		{
			databaseConnection();//If true create and connect to database
			showSuccessOptionPane();
		}
		else
		{
			showErrorOptionPane();//Show error message
			
		}
	}//Method end

	
	public void databaseConnection()
	{//Method start
		 try
		    {
		    Class.forName("org.sqlite.JDBC");
		    // create a database connection
		    connection = DriverManager.getConnection("jdbc:sqlite:user.db");
		    }
		    catch(ClassNotFoundException cnfEx)
		    {
		    	System.out.println("***Unnable to load driver***");
		    	System.exit(1);
		    }
		    catch(SQLException sqlEx)
		    {
		    	System.out.println("***Cannot connect to database***");
		    	System.exit(1);
		    }
	}//Method end

	//public void executeQuery()
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == okButton)
		{
			setFirstPassword();
			
			setSecondPassword();
			
			checkPasswordsMatch();
			
			
		}
		
	}
	
	public void showErrorOptionPane()
	{
		optionPaneErrorMessage  = new JOptionPane("Passwords do not match!", JOptionPane.ERROR_MESSAGE);    
		JDialog dialog = optionPaneErrorMessage .createDialog("Error");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
	
	public void showSuccessOptionPane()
	{
		optionPaneSuccessMessage = new JOptionPane("Database has been created", JOptionPane.OK_OPTION);    
		JDialog dialog = optionPaneSuccessMessage.createDialog("Success");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	
	}
	
}

