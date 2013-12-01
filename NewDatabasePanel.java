package PassKee;




import javax.swing.*;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.*;
import java.util.*;

public class NewDatabasePanel extends JFrame implements ActionListener
{//Start class

        
        private static final long serialVersionUID = 1L;
        private JTextField firstPasswordTextField; //Instance variable to store first password
        private JTextField secondPasswordTextField; //Instance variable to store second password
        public JButton okButton; //Instance variable for submission of passwords
        private JLabel lblNewLabel; //Instance variable for first password label
        private JLabel label; //Instance variable for second password label
        private String firstPassword; //Instance variable to store first password
        private String secondPassword; //Instance variable to store second password
        private Connection connection; //Instance variable to retrieve connection to database
        public static ArrayList <String> masterPassword = new ArrayList <>(); //Array list to store master password
        private JOptionPane optionPaneErrorMessage; //Instance variable to show error message
        private JOptionPane optionPaneSuccessMessage; //Instance variable to show success message
        private Statement statement; //Instance variable to execute and store SQL variables
        private ResultSet results; //Instance variable to store all returned data from database

        public NewDatabasePanel()// Constructor
                { //Constructor start
                        
                        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Close frame on exit
                        setBounds(100, 100, 450, 300); //Set frame size
                        setAlwaysOnTop(true);//Keep panel always on top

                        JMenuBar menuBar = new JMenuBar();// Menu bar object
                        setJMenuBar(menuBar);// Set menu bar

                        JMenu mnNewMenu = new JMenu("File");//JMenu object
                        menuBar.add(mnNewMenu);// Add JMenu

                        JMenuItem mntmOpenDatabase = new JMenuItem("Save Database");//Menu item object
                        mnNewMenu.add(mntmOpenDatabase);//Add menu item to JMenu
                        
                        firstPasswordTextField = new JPasswordField();//Initialise text field object
                        firstPasswordTextField.setColumns(10);//Set size of text field

                        secondPasswordTextField = new JPasswordField();//Initialise text field object
                        secondPasswordTextField.setColumns(10);//Set size of text field

                        okButton = new JButton("Submit");//Initialise submit button object

                        lblNewLabel = new JLabel("Password (8 Chatacters)");//Initialise JLabel object

                        label = new JLabel("Repeat");//Initialise JLabel object
                
                GroupLayout groupLayout = new GroupLayout(getContentPane());//Group layout object instance
                groupLayout.setHorizontalGroup(//Set the components along the horizontal axis
                        groupLayout.createParallelGroup(Alignment.LEADING)//Create and return parallel group with leading alignment
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addGap(78)//Add rigid gap
                                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)//Create and return parallel group with trailing alignment
                                                .addComponent(okButton)//Add submit button
                                                .addGroup(groupLayout.createSequentialGroup()
                                                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                                                .addComponent(label)//Add repeat password label
                                                                .addComponent(lblNewLabel))//Add first password label
                                                        .addGap(12)//Add rigid gap
                                                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                .addComponent(firstPasswordTextField, GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)//Add first password text field
                                                                .addComponent(secondPasswordTextField, GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))//Add second password text field
                                        .addContainerGap(176, Short.MAX_VALUE))//Add preferred gap
                );//End set horizontal group layout
                groupLayout.setVerticalGroup(//Set the components along the vertical axis
                        groupLayout.createParallelGroup(Alignment.LEADING)
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addGap(24)
                                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)//Create and return parallel group with baseline alignment
                                                .addComponent(firstPasswordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lblNewLabel))
                                        .addGap(18)
                                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(secondPasswordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label))
                                        .addGap(18)
                                        .addComponent(okButton)
                                        .addContainerGap(126, Short.MAX_VALUE))
                );//End set vertical group layout
                
                getContentPane().setLayout(groupLayout);//Add layout to content pane
                
                okButton.addActionListener(this);//Add action listener to button
        }//End constructor
        

        public void setFirstPassword()
                {
                        firstPassword = firstPasswordTextField.getText();//Store first password from text field
                }

        public String getFirstPassword()
                {
                        return firstPassword;// Return first password
                }

        public void setSecondPassword()
                {
                        secondPassword = secondPasswordTextField.getText();//Store second password from text field
                }

        public String getSecondPassword()
                {
                        return secondPassword;//Return second password
                }

        public void checkPasswordsMatch()
                {//Method start

                        if (getFirstPassword().equals(getSecondPassword())//Check if passwords and if they are of correct length
                                                && getFirstPassword().length() <= 8 && //Check passwords aren't empty
                                                		!getFirstPassword().isEmpty() &&
                                                				!getSecondPassword().isEmpty())
                                {//Start if
                                        databaseConnection();//Create and connect to database
                                        showSuccessOptionPane();//Show success message
                                }//End if
                        else
                                {//Start else
                                        showErrorOptionPane();// Show error message

                                }//End else
                
                }//Method end

        public void databaseConnection()
                {//Method start
                        try
                                {//Try start
                                        
                                        Class.forName("org.sqlite.JDBC");//Instance of the sqlite class
        
                                        connection = DriverManager
                                                        .getConnection("jdbc:sqlite:user.db");//Create database connection
                                }//Try end
                        
                        catch (ClassNotFoundException cnfEx)
                                {//Catch start
                                        
                                        System.out.println("***Unnable to load driver***");//Print error message
                                        System.exit(1);//Exit with status code 1
                                        
                                }//Catch end
                        
                        catch (SQLException sqlEx)
                                {//Catch start
                                        
                                        System.out.println("***Cannot connect to database***");//Print error message
                                        System.exit(1);//Exit with exit code 1
                                        
                                }//Catch end

                }// Method end

        public void actionPerformed(ActionEvent e)
                {//Method start

                        if (e.getSource() == okButton)//Check if button pressed
                                {//Start if
                                        
                                        setFirstPassword();//Set first password to string variable from text field

                                        setSecondPassword();//Set second password to string variable from text field

                                        checkPasswordsMatch();//Check if both passwords match

                                        masterPassword.add(getFirstPassword());//Add first password to array list

                                        //masterPaswwordDatabaseConnection();//Connect to database that stores master password

                                        updateDatabase();//Add master password to database
                                        
                                        MainPanel.newDatabasePanelCose();//Static reference to close panel
                                }//End if
                
                }//Method end

        public String getNext(int index)
                {
                        return masterPassword.get(index);//Return master password from array list
                }

        public void setMasterPassword(String element)
                {
                        masterPassword.add(element);//Add master password to array list
                }

        public void showErrorOptionPane()
                {
                        optionPaneErrorMessage = new JOptionPane("Passwords do not match!",
                                        JOptionPane.ERROR_MESSAGE);//Initialise option pane object
                        JDialog dialog = optionPaneErrorMessage.createDialog("Error");
                        dialog.setAlwaysOnTop(true);//Set option pane on top
                        dialog.setVisible(true);//Set option pane visible

                }

        public void showSuccessOptionPane()
                {
                        optionPaneSuccessMessage = new JOptionPane(
                                        "Database has been created", JOptionPane.INFORMATION_MESSAGE);//Initialise option pane object
                        JDialog dialog = optionPaneSuccessMessage.createDialog("Success");
                        dialog.setAlwaysOnTop(true);//Set option pane on top
                        dialog.setVisible(true);//Set option pane visible

                }

        public Connection getmasterPaswwordDatabaseConnection()
                {// Method start
                        try
                                {//Start try
                                        Class.forName("org.sqlite.JDBC");
                                        // create a database connection
                                        connection = DriverManager
                                                        .getConnection("jdbc:sqlite:/opt/master.db");//Establish connection to master password database
                                }//End try
                        
                        catch (ClassNotFoundException cnfEx)
                                {//Start catch
                                        
                                        System.out.println("***Unnable to load driver***");//Print error message
                                        System.exit(1);//Terminate program with status code 1
                                        
                                }//End catch
                        
                        catch (SQLException sqlEx)
                                {//Start catch
                                        
                                        System.out.println("***Cannot connect to database***");
                                        System.exit(1);//Terminate Program with
                                        
                                }//End catch
                        return connection;
                }// Method end

        public void updateDatabase()
                {//Method start
                	
                	Connection conn = null;
                    PreparedStatement pstmt = null;
                	
                			try
                                {//Start try
                                	conn = getmasterPaswwordDatabaseConnection();//Connect to database
                                	statement = connection.createStatement();//Create SQL statement object
                                    statement//Create table
                                                    .executeUpdate("CREATE TABLE IF NOT EXISTS MASTER (password string)");
                                	
                                    String query = "INSERT INTO MASTER(password) VALUES(?)";//Prepared query
                                	pstmt = connection.prepareStatement(query);//Create prepared statement object
                                	pstmt.setString(1, masterPassword.get(0));//Set designated parameters
                                	pstmt.executeUpdate();//Execute query
                                	results = statement.executeQuery("SELECT * FROM MASTER");
                                }//End try
                        
                        catch (SQLException sqlEx)
                                {//Start catch
                                        
                                        System.out.println("***Unnable to execute query***");//Print error messge
                                        sqlEx.printStackTrace();//Print error from stack
                                        System.exit(1);//Terminate program with status code 1
                                        
                                }//End catch
                			
                			finally
                			{//Start finally
                				 try
									{//Start try
										
										pstmt.close();//Close prepared statement
										conn.close();//Close connection
									}
                				 
								catch (SQLException e)
									
									{//Start catch
										
										e.printStackTrace();
										System.out.println("Could not disconnect!");//Print error
										System.exit(1);
									
									}//End catch
                			     
                			}//End finally
                			
                        
                }//Method end

}//End class
