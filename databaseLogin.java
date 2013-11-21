package PassKee;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;



public class databaseLogin extends JFrame implements ActionListener
{
	private FileInputStream input;
	private FileOutputStream output;
	private File file = new File("user.db.enc");
	private SecretKeySpec key;
	private Cipher decrypt;
	private CipherInputStream cipherInput;

	private NewDatabasePanel newDatabasePanel = new NewDatabasePanel();
	
	JPasswordField jp = new JPasswordField(24);
	private JTextField textField;
	private String masterPasswordInput;
	private ViewDatabasePanel viewDatabase;
	private JOptionPane optionPaneErrorMessage;
	private ViewDatabasePanel viewDatabasePanel;
	private JButton btnNewButton;
	 private String algo = "DES/ECB/PKCS5Padding";
	 private Connection connection;
	 private Statement statement;
	 private ResultSet results;
	 
	 String master;
	 
public databaseLogin()
{
	
	
	textField = new JTextField();
	textField.setColumns(10);
	
	JLabel lblNewLabel = new JLabel("Enter Database Password");
	setBounds(100, 100, 450, 300);
	btnNewButton = new JButton("Submit");
	GroupLayout groupLayout = new GroupLayout(getContentPane());
	groupLayout.setHorizontalGroup(
		groupLayout.createParallelGroup(Alignment.TRAILING)
			.addGroup(groupLayout.createSequentialGroup()
				.addContainerGap(111, Short.MAX_VALUE)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
					.addComponent(btnNewButton)
					.addGroup(groupLayout.createSequentialGroup()
						.addComponent(lblNewLabel)
						.addGap(57)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addGap(98))
	);
	groupLayout.setVerticalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addGap(37)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(lblNewLabel))
				.addGap(18)
				.addComponent(btnNewButton)
				.addContainerGap(150, Short.MAX_VALUE))
	);
	getContentPane().setLayout(groupLayout);
	btnNewButton.addActionListener(this);
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
public String algorithm()
{
	return "DES/ECB/PKCS5Padding";
}

public void setMasterPassword()
{
	masterPasswordInput = textField.getText();
}

public String getMasterPassword()
{
	return masterPasswordInput;
}

public void checkPassword()
{
	if(textField.getText().equals(master))
	{
		decryptDatabase();
		viewDatabase = new ViewDatabasePanel();
		viewDatabase.setVisible(true);
	}
	else
	{
		optionPaneErrorMessage  = new JOptionPane("Passwords do not match!", JOptionPane.ERROR_MESSAGE);    
		JDialog dialog = optionPaneErrorMessage .createDialog("Error");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
	
}

public void actionPerformed(ActionEvent e) {

	if(e.getSource() == btnNewButton)
	{
		masterPaswwordDatabaseConnection();
		updateDatabase();
		
		setMasterPassword();
		checkPassword();
		deleteDatabase();
	}
	
}
public void deleteDatabase()//Delete old database
{
	File file = new File("user.db.enc.dec");
	file.delete();
}//End method

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
