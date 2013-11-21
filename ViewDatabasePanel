package PassKee;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class ViewDatabasePanel extends JFrame implements ActionListener {

	private JMenuItem viewUserDetailsItem;
	private JSplitPane splitPane;
	private JTextArea textArea;
	
	private Connection connection;
	private Statement statement;
	private ResultSet results;
	
	private File file = new File("user.db.enc");
	private FileInputStream input;
	private FileOutputStream output;
	private SecretKeySpec key;
	private Cipher decrypt;
	private CipherInputStream cipherInput;
	String master;
	 
	public ViewDatabasePanel()
	{
		
		setAlwaysOnTop(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();//Menu bar
		setJMenuBar(menuBar);//Set menu bar
		
		JMenu mnNewMenu = new JMenu("File");//Menu
		menuBar.add(mnNewMenu);//Add menu
		
		viewUserDetailsItem = new JMenuItem("Display Passwords");//Menu item
		mnNewMenu.add(viewUserDetailsItem);//Add menu item
		viewUserDetailsItem.addActionListener(this);
		
		splitPane = new JSplitPane();
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setEditable(true);
		splitPane.setRightComponent(textArea);
		
		}

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == viewUserDetailsItem)
		{
			//databaseConnection();
			masterPaswwordDatabaseConnection();
			updateDatabase();
			
			decryptDatabase();
			getUserData();
			deleteDatabase();
		}
	}

	public void getUserData()
	{
		 try
		    {
		      // create a database connection
		      connection = DriverManager.getConnection("jdbc:sqlite:user.db.enc.dec");
		      Statement statement = connection.createStatement();
		      statement.setQueryTimeout(30);  // set timeout to 30 sec.

		      ResultSet rs = statement.executeQuery("select * from data");
		      while(rs.next())
		      {
		        // read the result set
		        System.out.println("name = " + rs.getString("name"));
		        System.out.println("id = " + rs.getString("id"));
		        System.out.println("password = " + rs.getString("password"));
		      }
		    }
		 catch(SQLException e)
		    {
		      // if the error message is "out of memory", 
		      // it probably means no database file is found
		      System.err.println(e.getMessage());
		    }
		    finally
		    {
		      try
		      {
		        if(connection != null)
		          connection.close();
		      }
		      catch(SQLException e)
		      {
		        // connection close failed.
		        System.err.println(e);
		      }
		    }
		 
	}
	
	public void deleteDatabase()//Delete old database
	{
		File file = new File("user.db.enc.dec");
		file.delete();
	}//End method
	
	public void printDetails()
	{
		try
	    {
	    	System.out.println();
	    	
	    	while(results.next())
	    	{
	    		System.out.println("URL: "
	    							+ results.getString("id"));
	    		System.out.println("Site Name: "
	    							+results.getString("name"));
	    		System.out.println("Password: "
	    							+results.getString("password"));
	    		
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
	public String algorithm()
	{
		return "DES/ECB/PKCS5Padding";
	}
	public void decryptDatabase()
	{
		try
		{
			input = new FileInputStream(file);
			file = new File(file.getAbsolutePath()+".dec");
			
			output = new FileOutputStream(file);
			
			byte k[] = master.getBytes();
			key = new SecretKeySpec(k, algorithm().split("/")[0]);
			
			decrypt = Cipher.getInstance(algorithm());
			
			decrypt.init(Cipher.DECRYPT_MODE, key);
			
			cipherInput = new CipherInputStream(input, decrypt);
		
			 byte[] buf = new byte[1024];
	         int read=0;
	         while((read=cipherInput.read(buf))!=-1)  //reading encrypted data
	              output.write(buf,0,read);  //writing decrypted data
	         //closing streams
	         cipherInput.close();
	         output.flush();
	         output.close();
		
		}
		catch( NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException fnfex)
		{
			fnfex.printStackTrace();
		}
}
	public void masterPaswwordDatabaseConnection()
	{//Method start
		 try
		    {
		    Class.forName("org.sqlite.JDBC");
		    // create a database connection
		    connection = DriverManager.getConnection("jdbc:sqlite:/opt/master.db");
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
	public void updateDatabase()
	{
		try
		{
			statement = connection.createStatement();
			//statement.executeUpdate("CREATE TABLE IF NOT EXISTS master (password string)");
			results = statement.executeQuery("SELECT * FROM master");
			master = results.getString("password");
			System.out.println(master);
		}
		catch(SQLException sqlEx)
	    {
	    	System.out.println("***Unnable to execute query***");
	    	sqlEx.printStackTrace();
	    	System.exit(1);
	    }
	}

}
