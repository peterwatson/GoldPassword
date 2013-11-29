package PassKee;



import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;

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
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class NewEntryPanel extends JFrame implements ActionListener
{//Class start
	
	private static final long serialVersionUID = 1L;
	private JTextField UrlTextField;//URL text field instance variable
	private JTextField websiteTextField;//Web site name text field instance variable
	public JTextField passwordTextField;//
	private JTextField passwordRepeatTextField;//Repeat password text field instance variable
	private JLabel UrlLabel;//URL label instance variable
	private JLabel websiteLabel;//Web site name instance variable
	private JLabel passwordLabel;//First password label instance variable
	private JLabel repeatPasswordLabel;//Repeat password label instance variable
	private JButton generatePasswordButton;//Generate password button instance variable
	private String firstPassword;//First password instance variable
	private String secondPassword;//Second password instance variable
	private String url;//URL instance variable
	private String website;//Web site name instance variable
	private Statement statement;//SQL statement instance variable
	private Connection connection;//SQL connection instance variable
	private ResultSet results;//Results instance variable
	private JMenuItem saveDatabase;//Menu item instance variable
	private FileInputStream input;//Input stream instance variable
	private FileOutputStream output;//Output stream instance variable
	private File file = new File("user.db");//Create and instantiate file object with reference to database
	private SecretKeySpec key;//Secret key instance variable
	private Cipher encrypt;//Encryption instance variable
	private CipherOutputStream cipherOut;//Cipher output stream instance variable

	private final int SIZE = 26;//Size of alphabet
	private char[] alphabet =//Store alphabet in char array
		{ 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
				'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	private char[] encryption = new char[SIZE];//Set encryption array to size of alphabet
	private char[] decryption = new char[SIZE];//Set decryption array to size of alphabet

	
	public NewEntryPanel()
		{//Constructor start
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Exit on close
			setBounds(100, 100, 450, 300);//Set panel size

			setAlwaysOnTop(true);//Set always on top

			JMenuBar menuBar = new JMenuBar();// Menu bar
			setJMenuBar(menuBar);// Set menu bar

			JMenu mnNewMenu = new JMenu("File");// Menu
			menuBar.add(mnNewMenu);// Add menu

			saveDatabase = new JMenuItem("Save Entry");// Menu Item
			mnNewMenu.add(saveDatabase);

			UrlTextField = new JTextField();//URL text field object
			UrlTextField.setColumns(10);//Set URL text field size

			websiteTextField = new JTextField();//Web site text field object
			websiteTextField.setColumns(10);//Web site text field set size

			passwordTextField = new JPasswordField();//Instantiate password text field

			passwordRepeatTextField = new JPasswordField();//Instantiate password repeat text field

			generatePasswordButton = new JButton("Generate");//Instantiate generate button

			UrlLabel = new JLabel("URL");//Create and instantiate label for URL

			websiteLabel = new JLabel("Site Name");//Instantiate  label for site name

			passwordLabel = new JLabel("Password");//Instantiate password label
                
                repeatPasswordLabel = new JLabel("Repeat");//Instantiate password repeat label
                
                GroupLayout groupLayout = new GroupLayout(getContentPane());//Group layout object instance
                groupLayout.setHorizontalGroup(//Set the components along the horizontal axis
                        groupLayout.createParallelGroup(Alignment.LEADING)//Create and return parallel group with leading alignment
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addGap(17)
                                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)//Create and return parallel group with trailing alignment
                                                .addComponent(UrlLabel)//Add URL label
                                                .addComponent(websiteLabel)//Add web site label
                                                .addComponent(passwordLabel)//Add password label
                                                .addComponent(repeatPasswordLabel))//Password repeat label
                                        .addPreferredGap(ComponentPlacement.UNRELATED)//Add unrelated gap to component
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
                getContentPane().setLayout(groupLayout);//Add layout to content pane
                
                saveDatabase.addActionListener(this);//Add action listener to save database
                
                generatePasswordButton.addActionListener(this);//Add action listener to generate button
                
                
                ceaserEncryption();//Pass encryption method to class constructor
               
		}//Constructor end

	public void setFirstPassword()
		{
			firstPassword = passwordTextField.getText();//Assign variable to text field input
		}

	public void setSecondPassword()
		{
			secondPassword = passwordRepeatTextField.getText();//Assign variable to text field input
		}

	public void setWebsiteName()
		{
			website = websiteTextField.getText();//Assign variable to text field input
		}

	public void setUrl()
		{
			url = UrlTextField.getText();//Assign variable to text field input
		}

	public String getFirstPassword()
		{
			return firstPassword;//Return first password
		}

	public String getSecondPassword()
		{
			return secondPassword;//Return second password
		}

	public String getUrl()
		{
			return url;//Return URL
		}

	public String getWebsiteName()
		{
			return website;//Return web site name
		}

	public void updateDatabase()
		{//Method start
			
			try
				{//Start try
					
					statement = connection.createStatement();//Create SQL statement object
					statement.executeUpdate("CREATE TABLE IF NOT EXISTS data (URL string, Site string, Password string)");//Create table
					statement.executeUpdate("INSERT INTO DATA VALUES('"+getUrl()+"','"+getWebsiteName()+
																				"','"+ getFirstPassword()+"')");//Insert data into tables
					results = statement.executeQuery("SELECT * FROM data");//Retrieve all input and add to results
					
				}//End try
			
			catch (SQLException sqlEx)
			
				{//Start catch
					
					System.out.println("***Unnable to execute query***");//Print error message
					sqlEx.printStackTrace();//Print error on stack
					System.exit(1);//Exit with error code 1
					
				}//End catch
			
		}//Method end
	
	public void databaseConnection()
		{// Method start
			try
				{//Start try
					
					Class.forName("org.sqlite.JDBC");//Create a database connection
					connection = DriverManager
							.getConnection("jdbc:sqlite:user.db");
				}//End try
			
			catch (ClassNotFoundException cnfEx)
				{//Start catch
					
					System.out.println("***Unnable to load driver***");//Print error message
					System.exit(1);//Exit with error code 1
					
				}//End catch
			
			catch (SQLException sqlEx)
				{//Start catch
					
					System.out.println("***Cannot connect to database***");
					System.exit(1);
					
				}//End catch
			
		}// Method end

	public void encryptDatabase()
		{//Method start
			
			try
				{//Start try
					
					input = new FileInputStream(file);//Instantiate input and add db file instance to constructor
					file = new File(file.getAbsolutePath() + ".enc");//New file instance that retrieves database file
					output = new FileOutputStream(file);//Instantiate output to retrieve the output bytes of the db file
					byte k[] = NewDatabasePanel.masterPassword.get(0)
							.getBytes();//Retrieve master password from runtime memory in array list
					key = new SecretKeySpec(k, algorithm().split("/")[0]);//Create secret key using the master password
					encrypt = Cipher.getInstance(algorithm());//Implements transformation algorithm for encryption
					encrypt.init(Cipher.ENCRYPT_MODE, key);//Initialise cipher with key
					cipherOut = new CipherOutputStream(output, encrypt);//New cipher input object
					byte[] buf = new byte[1024];//New byte array object
					int read;//Initialise read integer

					while ((read = input.read(buf)) != -1)//Run the length of the buffer
						cipherOut.write(buf, 0, read);
					input.close();//Close input stream
					cipherOut.flush();//Flush cipher stream
					cipherOut.close();//Close cipher stream

				}//End try
			
			catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException fnfex)
				{//Start catch
					
					fnfex.printStackTrace();
					
				}//End catch
			
		}//Method end

	public String algorithm()
		{
			return "DES/ECB/PKCS5Padding";//Secret key algorithm
		}

	public void deleteDatabase()// Delete old database
		{
			File file = new File("user.db");//File object with a reference to the first db file 
			file.delete();//Delete database 
		}

	public void actionPerformed(ActionEvent e)
		{//Method start
			
			if (e.getSource() == saveDatabase)
				{//Start if
					
					setUrl();// Run set URL method
					setWebsiteName();// Run set web site name method
					setFirstPassword();// Run set password method

					databaseConnection();// Run database connection method
					updateDatabase();// Run update database method
					encryptDatabase();// Run encrypt database method
					deleteDatabase();// Run delete database method
				
					System.out.println(NewDatabasePanel.masterPassword.get(0));//Print password for reference

					MainPanel.newEntryPanelClose();//Close panel
					
				}//End if

			if (e.getSource() == generatePasswordButton)
				
				{//Start if

					System.out.println("Encryption order = " + new String(encryption));//Print encryption for reference
					System.out.println("Decryption order = " + new String(decryption));//Print decryption for reference
					String text = "GREGS_CEASER_CYPHER_EXAMPLE";//String to be encrypted

					System.out.println(text);//Print secret to be encrypted
					text = encryption(text);//Encrypt text
					
					passwordTextField.setText(text);//Add generated cipher text to first text field
					passwordRepeatTextField.setText(text);//Add generated cipher text to second text field

					System.out.println(text);//Print encrypted cipher text
					text = decryption(text);//Decrypt cipher text
					System.out.println(text);//Print decrypted text for reference

				}//End if

		}//Method end

	public void ceaserEncryption()
		{

			for (int i = 0; i < SIZE; i++)
				encryption[i] = alphabet[(i + 3) % SIZE];//Shift letters by 3 places
			for (int i = 0; i < SIZE; i++)
				decryption[encryption[i] - 'A'] = alphabet[i];//Reverse shift for decryption

		}

	// Encryption
	public String encryption(String secret)
		{
			char[] message = secret.toCharArray();//Array for message
			for (int i = 0; i < message.length; i++)//Loop for encryption
				if (Character.isUpperCase(message[i]))//If true there is a letter to be changed
					message[i] = encryption[message[i] - 'A'];//Use the letter as index
			return new String(message);//Return message as new string object
		}

	public String decryption(String secret)
		{
			char[] cipher = secret.toCharArray();//The cipher text array
			for (int i = 0; i < cipher.length; i++)//Loop for decryption
				if (Character.isUpperCase(cipher[i]))//If true there is letter to be changed
					cipher[i] = decryption[cipher[i] - 'A'];//Use the letter as index
			return new String(cipher);//Return cipher as new string
		}
}//Class end


