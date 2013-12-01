package PassKee;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
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


public class databaseLogin extends JFrame implements ActionListener
{//Start class
        
        private static final long serialVersionUID = 1L;
        private FileInputStream input;//File input instance variable
        private FileOutputStream output;//File out put instance variable
        private File file = new File("user.db.enc");//File object and initialisation
        private SecretKeySpec key;//Secret key instance variable
        private Cipher decrypt;//Cipher instance variable
        private CipherInputStream cipherInput;//Cipher output instance variable
        private JTextField textField;//JTextField instance variable
        private String masterPasswordInput;//Master password instance variable
        private ViewDatabasePanel viewDatabase;//View database panel instance variable
        private JOptionPane optionPaneErrorMessage;//Option pane instance variable
        private JButton btnNewButton;//JButton instance variable
        private Connection connection;//SQL connection instance variable
        private Statement statement;//SQL statement instance variable
        private ResultSet results;//Result set instance variable
        private String master;//Master password instance variable
        private JLabel lblNewLabel;//JLabel instance variable
        
        public databaseLogin()
                {//Start constructor
        
        
        textField = new JPasswordField();//Initialise JPasswordField
        textField.setColumns(10);//Set size
       
        
        lblNewLabel = new JLabel("Enter Database Password");//Initialise JLabel
        setBounds(100, 100, 450, 300);//Set size of frame
        btnNewButton = new JButton("Submit");//Initialise submit button
        GroupLayout groupLayout = new GroupLayout(getContentPane());//Group layout object instance
        groupLayout.setHorizontalGroup(//Set the components along the horizontal axis
                groupLayout.createParallelGroup(Alignment.TRAILING)//Create and return parallel group with trailing alignment
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap(111, Short.MAX_VALUE)//Add preferred gap
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(btnNewButton)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(lblNewLabel)
                                                .addGap(57)
                                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addGap(98))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)//Create and return parallel group with leading alignment
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(37)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel))
                                .addGap(18)
                                .addComponent(btnNewButton)
                                .addContainerGap(150, Short.MAX_VALUE))//Add preferred gap
        );
        getContentPane().setLayout(groupLayout);//Add layout to content pane
        btnNewButton.addActionListener(this);//Add action listener to submit button
                
                }//End constructor

        public void decryptDatabase()
                {//Method start
                        try
                                {//Start try
                                        
                                        input = new FileInputStream(file);//Instantiate input and add db file instance to constructor
                                        file = new File(file.getAbsolutePath() + ".dec");//New file instance that retrieves database file
                                        output = new FileOutputStream(file);//Instantiate output to retrieve the output bytes of the db file
                                        byte k[] = master.getBytes();//Store master password as byte array
                                        key = new SecretKeySpec(k, algorithm().split("/")[0]);//Create secret key using the master password
                                        decrypt = Cipher.getInstance(algorithm());//Implements transformation algorithm for decryption
                                        decrypt.init(Cipher.DECRYPT_MODE, key);//Initialise cipher with key
                                        cipherInput = new CipherInputStream(input, decrypt);//New cipher input object
                                        byte[] buffer = new byte[1024];//New byte array object
                                        int read = 0;//Initialise read integer
                                        
                                        while ((read = cipherInput.read(buffer)) != -1)//Run the length of the buffer
                                                output.write(buffer, 0, read); // writing decrypted data
                                
                                        cipherInput.close();//Close stream
                                        output.flush();//Flush out put stream
                                        output.close();//Close out put

                                }//End try
                        
                        catch ( NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException fnfex)
                                {//Start catch
                                        
                                        fnfex.printStackTrace();//Print stack trace
                                        
                                }//End catch
                        
                }//Method end

        public String algorithm()
                {
                        return "DES/ECB/PKCS5Padding";//Secret key algorithm
                }

        public void setMasterPassword()
                {
                        masterPasswordInput = textField.getText();//Assign master password to text field input
                }

        public String getMasterPassword()
                {
                        return masterPasswordInput;//Return master password
                }

        public void checkPassword()
                {//Method start
                        
                        if (textField.getText().equals(master))
                                {//Start if
                                        
                                        decryptDatabase();//Run decrypt database method
                                        viewDatabase = new ViewDatabasePanel();//Instantiate view database panel
                                        viewDatabase.setVisible(true);//Set view database panel to visible
                                }//End if
                        else
                                {//Start else
                                        optionPaneErrorMessage = new JOptionPane("Passwords do not match!",
                                                        JOptionPane.ERROR_MESSAGE);
                                        JDialog dialog = optionPaneErrorMessage.createDialog("Error");
                                        dialog.setAlwaysOnTop(true);//Set option pane always on top
                                        dialog.setVisible(true);//Set option pane visible
                                }//End else

                }//Method end

        public void actionPerformed(ActionEvent e)
                {//Method start

                        if (e.getSource() == btnNewButton)//Check if submit button is pressed
                                {//Start if
                                        
                                        masterPaswwordDatabaseConnection();//Connect to database where the master password is stored
                                        updateDatabase();//Retrieve master password from database

                                        setMasterPassword();//Set master password from the first password text field
                                        checkPassword();//Compare input password to the password master password in the master database
                                        deleteDatabase();//Delete the decrypted database once password has been extracted

                                        MainPanel.databaseLoginPanelClose();//Close panel

                                }//End if

                }//Method end

        public void deleteDatabase()// Delete old database
                {
                        File file = new File("user.db.enc.dec");//File object with a reference to the decrypted database
                        file.delete();//Delete the decrypted database
                }

        public void masterPaswwordDatabaseConnection()
                {// Method start
                        try
                                {//Start try
                                        
                                        Class.forName("org.sqlite.JDBC");//Create sqlite class object
                                        //Connect to master password database
                                        connection = DriverManager
                                                        .getConnection("jdbc:sqlite:/opt/master.db");
                                }//End try
                        
                        catch (ClassNotFoundException cnfEx)
                                {//Start catch
                                        
                                        System.out.println("***Unnable to load driver***");//Print error message
                                        System.exit(1);//Terminate program with status code 1
                                }//End catch
                        
                        catch (SQLException sqlEx)
                                {//Start catch
                                        
                                        System.out.println("***Cannot connect to database***");//Print error message
                                        System.exit(1);//Terminate program with status code 1
                                        
                                }//End catch

                }// Method end

        public void updateDatabase()
                {//Method start
                        try
                                {//Start try
                                        
                                        statement = connection.createStatement();//Create SQL statement object
                                        results = statement.executeQuery("SELECT * FROM MASTER");//Query database
                                        master = results.getString("password");//Assign master to the returned master password
                                        
                                }//End try
                        
                        catch (SQLException sqlEx)
                                {//Start catch
                                        
                                        System.out.println("***Unnable to execute query***");
                                        sqlEx.printStackTrace();//Print error in stack
                                        System.exit(1);//Terminate program with status code 1
                                        
                                }//End catch
                
                }//End method

}//End class
