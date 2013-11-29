package PassKee;

import javax.swing.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.event.*;


public class MainPanel extends JFrame implements ActionListener
{//Start class

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuItem newDatabaseItem;//New database item instance variable
	private JMenuItem newEntryItem;//New entry item instance variable
	private JMenuItem viewDatabaseItem;//View database item instance variable
	private static databaseLogin dataLogin;//Database login static variable
	private static NewDatabasePanel newDatabase;//New database static reference
	private static NewEntryPanel newEntry;//New entry static reference
	private ViewDatabasePanel viewDatabasePanel;

	
	public static void main(String[] args)
		{//Main start

			EventQueue.invokeLater(new Runnable()//Call run method
				{//Start event queue
					public void run()
					
						{//Method start
							try
								{//Start try
									
									MainPanel frame = new MainPanel();//Instantiate main panel
									frame.setVisible(true);//Set it to visible
									
								}//End try
							
							catch (Exception e)
							
								{//Start catch
									
									e.printStackTrace();//Print stack trace
									
								}//End catch
						}
				});//End event queue
			
		}//Main end

	public MainPanel()
		{//Constructor start

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Exit frame when closed
			setBounds(100, 100, 450, 300);//Set panel bounds

			JMenuBar menuBar = new JMenuBar();// Menu bar
			setJMenuBar(menuBar);// Set menu bar

			JMenu mnNewMenu = new JMenu("File");// Menu
			menuBar.add(mnNewMenu);// Add menu

			newDatabaseItem = new JMenuItem("Create New Database");// Menu item
			mnNewMenu.add(newDatabaseItem);// Add menu item

			newEntryItem = new JMenuItem("Create New Entry");// Menu Item
			mnNewMenu.add(newEntryItem);

			viewDatabaseItem = new JMenuItem("View Database");//View database item 
			mnNewMenu.add(viewDatabaseItem);//Add item to panel

			newDatabase = new NewDatabasePanel();//Reference to new database panel
			dataLogin = new databaseLogin();//Reference to database login
			viewDatabasePanel = new ViewDatabasePanel();//Instantiate view database panel
			newEntry = new NewEntryPanel();//Instantiate new entry panel

			newDatabaseItem.addActionListener(this);//Add action listener to new database item
			newEntryItem.addActionListener(this);//Add action listener to new entry item
			viewDatabaseItem.addActionListener(this);//Add action listener to view database item

		}//Constructor end

	public void actionPerformed(ActionEvent e)
		{//Method start

			if (e.getSource() == newDatabaseItem)//If new database item clicked
				
				{//Start if
					
					newDatabase.setVisible(true);//Set new database panel to visible
				
				}//End if

			if (e.getSource() == newEntryItem)//If new entry item clicked
				
				{//Start if
					
					newEntry.setVisible(true);//Set new entry panel to visible
					
				}//End if
			
			if (e.getSource() == viewDatabaseItem)//If view database item clicked
				
				{//Start if
					
					dataLogin.setVisible(true);//Set database login panel to visible
				
				}//End if

		}//Method end

	public static void newDatabasePanelCose()
		{
			newDatabase.setVisible(false);//Close new database panel
		}

	public static void newEntryPanelClose()
		{
			newEntry.setVisible(false);//Close new entry panel
		}

	public static void databaseLoginPanelClose()
		{
			dataLogin.setVisible(false);//Close database login panel
		}

}//End class
