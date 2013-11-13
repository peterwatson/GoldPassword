package PassKee;

import java.awt.*;
import javax.swing.*;
import com.jgoodies.*;
import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import java.awt.event.*;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class NewEntryPanel extends JFrame implements ActionListener
{
	private JTextField UrlTextField;
	private JTextField websiteTextField;
	private JTextField passwordTextField;
	private JTextField passwordRepeatTextField;
	private JLabel UrlLabel;
	private JLabel websiteLabel;
	private JLabel passwordLabel;
	private JLabel repeatPasswordLabel;
	private JButton generatePasswordButton;
	private String firstPassword;
	private String secondPassword;
	private String url;
	private String website;
	private Statement statement;
	private Connection connection;
	private ResultSet results;
	private JMenuItem saveDatabase;
	
	public NewEntryPanel()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();//Menu bar
		setJMenuBar(menuBar);//Set menu bar
		
		JMenu mnNewMenu = new JMenu("File");//Menu
		menuBar.add(mnNewMenu);//Add menu
		
		
		saveDatabase = new JMenuItem("Save Entry");//Menu Item
		mnNewMenu.add(saveDatabase);
		
		
		UrlTextField = new JTextField();
		UrlTextField.setColumns(10);
		
		websiteTextField = new JTextField();
		websiteTextField.setColumns(10);
		
		passwordTextField = new JPasswordField();
		
		passwordRepeatTextField = new JPasswordField();
		
		generatePasswordButton = new JButton("Generate");
		
		UrlLabel = new JLabel("URL");
		
		websiteLabel = new JLabel("Site Name");
		
		passwordLabel = new JLabel("Password");
		
		repeatPasswordLabel = new JLabel("Repeat");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(17)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(UrlLabel)
						.addComponent(websiteLabel)
						.addComponent(passwordLabel)
						.addComponent(repeatPasswordLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(passwordRepeatTextField, Alignment.TRAILING)
						.addComponent(passwordTextField, Alignment.TRAILING)
						.addComponent(websiteTextField, Alignment.TRAILING)
						.addComponent(UrlTextField, Alignment.TRAILING))
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
								.addComponent(UrlTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(UrlLabel))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(websiteTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(websiteLabel))
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
		
		saveDatabase.addActionListener(this);
		
		generateButtonGo();
		
	}
	
	public void setFirstPassword()
	{ firstPassword = passwordTextField.getText(); }
	
	public void setSecondPassword()
	{ secondPassword = websiteTextField.getText(); }
	
	public String getFirstPassword()
	{ return firstPassword; }
	
	public String getSecondPassword()
	{ return secondPassword; }
	
	public void setUrl()
	{ url = UrlTextField.getText(); }
	
	public String getUrl()
	{ return url; }
	
	public void setWebsiteName()
	{ website = websiteTextField.getText(); }
	
	public String getWebsiteName()
	{ return website; }
	
	public void updateDatabase()
	{
		try
		{
			statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS data (id string, name string, password string)");
			statement.executeUpdate("insert into data values('"+getUrl()+"', '"+getWebsiteName()+"', '"+getFirstPassword()+"')");
			results = statement.executeQuery("SELECT * FROM data");
		}
		catch(SQLException sqlEx)
	    {
	    	System.out.println("***Unnable to execute query***");
	    	sqlEx.printStackTrace();
	    	System.exit(1);
	    }
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
	public void printDetails()
	{
		try
	    {
	    	System.out.println();
	    	
	    	while(results.next())
	    	{
	    		System.out.println("URL: "
	    							+ results.getString(1));
	    		System.out.println("Site Name: "
	    							+results.getString(2));
	    		System.out.println("Password: "
	    							+results.getString(3));
	    	}
	    }
	    catch(SQLException sqlEx)
	    {
	    	System.out.println("***Error retrieving data***");
	    	sqlEx.printStackTrace();
	    	System.exit(1);
	    }
		try
	    {
	    	connection.close();
	    }
	    catch(SQLException sqlEx)
	    {
	    	System.out.println("***Unnable to disconnect***");
	    	sqlEx.printStackTrace();
	    	System.exit(1);
	    }
	}

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
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == saveDatabase)
		{
			setUrl();
			getWebsiteName();
			setFirstPassword();
			
			databaseConnection();
			updateDatabase();
			printDetails();
		}
		
	}

}
