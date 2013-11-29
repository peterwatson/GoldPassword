package PassKee;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class ViewDatabasePanel extends JFrame implements ActionListener
{//Class start

	private static final long serialVersionUID = 1L;
	private JMenuItem viewUserDetailsItem;//Instance variable for JMenu
	private JSplitPane splitPane;//JSplit pane instance variable
	private JTextArea rightArea;//Instance variable for right hand side of text area
	private JTextArea leftArea;//Instance variable for left side of text area
	private Connection connection;//Instance variable for SQL connection
	private Statement statement;//Instance variable for SQL statement
	private ResultSet results;//Instance variable for database results
	private JMenuItem copyToClipboard;
	private File file = new File("user.db.enc");//File object with reference to encrypted database
	private FileInputStream input;//Input stream instance variable
	private FileOutputStream output;//Output stream instance variable
	private SecretKeySpec key;//Secret key instance variable
	private Cipher decrypt;//Instance variable for decryption
	private CipherInputStream cipherInput;//Cipher input stream instance variable
	private String master;//Instance variable to store master password
	private String password;
	
	public ViewDatabasePanel()
		{//Method start

			setAlwaysOnTop(true);//Set panel always on top

			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Exit frame when user closes
			setBounds(100, 100, 450, 300);//Set size of frame

			JMenuBar menuBar = new JMenuBar();//Menu bar object
			setJMenuBar(menuBar);// Set menu bar

			JMenu mnNewMenu = new JMenu("File");//JMenu with file button
			menuBar.add(mnNewMenu);//Add JMenu to menu bar

			viewUserDetailsItem = new JMenuItem("Display Passwords");//Add view details option to menu bar
			mnNewMenu.add(viewUserDetailsItem);// Add menu item
			viewUserDetailsItem.addActionListener(this);//Add action listener to menu item

			copyToClipboard = new JMenuItem("Copy to Clip Board");
			mnNewMenu.add(copyToClipboard);
			copyToClipboard.addActionListener(this);
			
			splitPane = new JSplitPane();//Split pane object
			getContentPane().add(splitPane, BorderLayout.CENTER);//Add split pane to content pane
			splitPane.setDividerLocation(0.5);//Set divider location to centre
			splitPane.setResizeWeight(0.5);//Ensure 50 50 distribution of space
			
			rightArea = new JTextArea();//Text area object for right hand side
			splitPane.setRightComponent(rightArea);//Add text area to right hand side
			rightArea.setEditable(false);//Set the right hand are to un-editable
			
			leftArea = new JTextArea();//Text area object for left hand side
			splitPane.setLeftComponent(leftArea);//Add text area to left hand side
			leftArea.setEditable(false);//Set the left hand are to un-editable

		}

	public void actionPerformed(ActionEvent e)
		{//Method start
			
			if (e.getSource() == viewUserDetailsItem)
				{//Start if
					
					
					masterPaswwordDatabaseConnection();//Connect to master password db
					updateDatabase();//Retrieve password from db

					decryptDatabase();//Decrypt the encrypted database
					getUserData();//Retrieve data from the newly decrypted database
					deleteDatabase();//Delete the decrypted database after data has been retrieved
					
				}//End if
			
			if(e.getSource() == copyToClipboard)//If copy to clip board is clicked
				{//Start if
				
					StringSelection passwordContents = new StringSelection(password);//Pass password to constructor
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents (passwordContents, null);//Copy to clip board
					
				}//End if
			
		}//Method end

	public void getUserData()
		{//Method start
			try
				{//Start try
					
					connection = DriverManager//Establish connection to decrypted db
							.getConnection("jdbc:sqlite:user.db.enc.dec");
					
					Statement statement = connection.createStatement();//Create new SQL statement object
					statement.setQueryTimeout(30); // set timeout to 30 seconds

					ResultSet results = statement.executeQuery("SELECT * FROM data");//Store query data to results
					
					while (results.next())//Loop until length of results
						
						{//Start while
							
							String URL = results.getString("URL");//Store string from the URL column
							String name = results.getString("Site");//Store the string from the Site column
							password = results.getString("Password");//Store the string from the Password column
							
							
							
							leftArea.append("URL: "+URL + "\n");//Print the URL to the left hand text area
							rightArea.append("Website: "+name + "\n");//Print the web site name to the right hand text area
							rightArea.append("Password: ");
							for(int i = 0; i < password.length(); i++)
								{
									rightArea.append("*");
								}//Print the hidden password to the right hand text area

						}//End while
				}//End try
			
			catch (SQLException e)
				{//Start catch
					
					System.err.println(e.getMessage());//Print error message
					
				}//End catch
			
			finally
				{//Start finally
					
					try
						{//Start try
							
							if (connection != null) connection.close();//Close connection
							
						}//End try
					
					catch (SQLException e)
						{//Start catch
							
							System.err.println(e);//Can't close connection
							System.exit(1);//Terminate with error code 1
							
						}//End catch
					
				}//End finally

		}//Method end

	public void deleteDatabase()// Delete old database
		{
			File file = new File("user.db.enc.dec");//File object with a reference to the decrypted db file
			file.delete();//Delete file
		}

	

	public String algorithm()
		{
			return "DES/ECB/PKCS5Padding";//Encryption algorithm
		}

	public void decryptDatabase()
		{//Method start
			
			try
				{//Start try
					
					input = new FileInputStream(file);//Instantiate input and add db file instance to constructor
					file = new File(file.getAbsolutePath() + ".dec");//New file instance that retrieves decrypted db file
					output = new FileOutputStream(file);//Instantiate output to retrieve the output bytes of the db file
					byte k[] = master.getBytes();//Store master password as byte array
					key = new SecretKeySpec(k, algorithm().split("/")[0]);//Create secret key using the master password
					decrypt = Cipher.getInstance(algorithm());//Implements transformation algorithm for decryption
					decrypt.init(Cipher.DECRYPT_MODE, key);//Initialise cipher with key
					cipherInput = new CipherInputStream(input, decrypt);//New cipher input object 
					byte[] buffer = new byte[1024];//New byte array object
					int read = 0;//Initialise read integer
					
					while ((read = cipherInput.read(buffer)) != -1)//Run the length of the buffer
						// reading encrypted data
						output.write(buffer, 0, read); //Writing decrypted data
					cipherInput.close();//Close cipher stream
					output.flush();//Flush output stream
					output.close();//Close output stream

				}//End try
			
			catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException fnfex)
				{//Start catch
					
					fnfex.printStackTrace();//Print stack trace
					
				}//End catch
			
		}//End method

	public void masterPaswwordDatabaseConnection()
		{// Method start
			try
				{//Start try
					
					Class.forName("org.sqlite.JDBC");
					connection = DriverManager//Establish db connection
							.getConnection("jdbc:sqlite:/opt/master.db");
				}//End try
			
			catch (ClassNotFoundException cnfEx)
				{//Start catch
					
					System.out.println("***Unnable to load driver***");//Print error message
					System.exit(1);//Terminate program with error code 1
					
				}//End catch
			
			catch (SQLException sqlEx)
				{//Start catch
					
					System.out.println("***Cannot connect to database***");//Print error message
					System.exit(1);//Terminate program with error code 1
					
				}//End catch

		}// Method end

	public void updateDatabase()
		{//Start method
			
			try
				{//Start try
					
					statement = connection.createStatement();//Create SQL statement object
					results = statement.executeQuery("SELECT * FROM MASTER");//Query database
					master = results.getString("password");//Store master password
					System.out.println(master);//Print password to terminal for reference
					
				}//End try
			
			catch (SQLException sqlEx)
				{//Start catch
					
					System.out.println("***Unnable to execute query***");//Print error message
					sqlEx.printStackTrace();//Print stack trace
					System.exit(1);//Terminate program with exit code 1
					
				}//End catch
			
		}//Method end

}//Class end
