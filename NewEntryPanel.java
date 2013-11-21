package PassKee;

import java.awt.*;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import com.jgoodies.*;
import java.awt.EventQueue;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

import javax.swing.JFrame;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
	
	private FileInputStream input;
	private FileOutputStream output;
	private File file = new File("user.db");
	private SecretKeySpec key;
	private Cipher encrypt;
	private CipherOutputStream cipherOut;
	
	private NewDatabasePanel newDatabasePanel = new NewDatabasePanel();
	
	public NewEntryPanel()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		setAlwaysOnTop(true);
		
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
	{ secondPassword = passwordRepeatTextField.getText(); }
	
	public void setWebsiteName()
	{ website = websiteTextField.getText(); }
	
	public void setUrl()
	{ url = UrlTextField.getText(); }
	
	public String getFirstPassword()
	{ return firstPassword; }
	
	public String getSecondPassword()
	{ return secondPassword; }
	
	public String getUrl()
	{ return url; }
	
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
	
	public void encryptDatabase()
	{
		try
		{
			input = new FileInputStream(file);
			file = new File(file.getAbsolutePath()+".enc");
			output = new FileOutputStream(file);
			
			byte k[] = NewDatabasePanel.masterPassword.get(0).getBytes();//kEY
			key = new SecretKeySpec(k,algorithm().split("/")[0]);
			
			encrypt = Cipher.getInstance(algorithm());
			encrypt.init(Cipher.ENCRYPT_MODE,key);
			cipherOut = new CipherOutputStream(output, encrypt);
			
			byte[] buf = new byte[1024];
			int read;
			
			while((read = input.read(buf))!=-1)
				cipherOut.write(buf, 0, read);
			input.close();
			cipherOut.flush();
			cipherOut.close();
		
		}
		catch( NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException fnfex)
		{
			fnfex.printStackTrace();
		}
	}
	
	public String algorithm()
	{
		return "DES/ECB/PKCS5Padding";
	}
	
	public void deleteDatabase()//Delete old database
	{
		File file = new File("user.db");
		file.delete();
	}//End method
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == saveDatabase)
		{
			setUrl();//Run set URL method
			setWebsiteName();//Run set web site name method
			setFirstPassword();//Run set password method
			
			databaseConnection();//Run database connection method
			updateDatabase();//Run update database method
			encryptDatabase();//Run encrypt database method
			deleteDatabase();//Run delete database method
			//printDetails();
			System.out.println(NewDatabasePanel.masterPassword.get(0));
		}
		
	}

}
