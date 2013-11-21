package PassKee;


import javax.swing.*;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.event.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;




public class PassKeeGUI extends JFrame {

	
	
	
	private JMenuItem newDatabaseItem;
	private JMenuItem newEntryItem;
	private JMenuItem viewDatabaseItem;
	private databaseLogin login = new databaseLogin();
	

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PassKeeGUI frame = new PassKeeGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PassKeeGUI() {
		
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
		

		viewDatabaseItem = new JMenuItem("View Database");
		mnNewMenu.add(viewDatabaseItem);
		

	
		
	
		NewEntryPanelShow();
		
		NewDatabasePanelShow();
		
		databaseLoginShow();
		
		//viewDatabasePanelShow();
		
		
	}
	
	public void NewDatabasePanelShow()
	{
		newDatabaseItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	
            		//databaseName = JOptionPane.showInputDialog( "Enter a database name" );//Open dialogue
            	
            	NewDatabasePanel newDatabase = new NewDatabasePanel();
            	newDatabase.setVisible(true);
            
            		

            }
        });
	}

	public void NewEntryPanelShow()
	{
		newEntryItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
            	
            	NewEntryPanel newEntry = new NewEntryPanel();
            	newEntry.setVisible(true);
            		

            }
        });
	}
	/*
	public void viewDatabasePanelShow()
	{
		viewDatabaseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
				
				
				ViewDatabasePanel viewDatabasePanel = new ViewDatabasePanel();
				viewDatabasePanel.setVisible(true);
				
				
				
			}
		
	});
}
*/
	public void databaseLoginShow()
	{
		viewDatabaseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
			
				
				login.setVisible(true);
				
				
			}
		
	});
}
	
}
