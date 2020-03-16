// User Bean

package  obs;
import java.sql.*;
import java.util.*;
import javax.naming.*;
//import javax.rmi.*;



public class Customer
{
 

    private  String custname;
    private  String password;
    private  String email;
    private  String phoneno;

    private  Context context;

    public Context getJNDIContext()
    { 
       return  this.context;
    }

    public Customer() 
    {
       this.context = getInitialContext();
    }

    public  void setCustname(String custname)
    { this.custname = custname; }

    public String getCustname()
    {  return custname; }


    public  void setPhoneno (String phoneno)
    { this.phoneno = phoneno; }

    public String getPhoneno()
    {  return phoneno; }

    public  void setPassword(String password)
    { this.password= password; }

    public String getPassword()
    {  return this.password; }

    public  void setEmail (String email)
    { this.email = email; }

    public String getEmail()
    {  return email; }

     // returns true if uname and pwd are valid
    public  boolean isValid()
    {
     Connection con = null;
     PreparedStatement prepStatement = null;
     try
     {
      con = getConnection();
      prepStatement = con.prepareStatement("select phoneno, email from customers where custname = ? and pwd= ?");
      prepStatement.setString(1,custname);
      prepStatement.setString(2,password);

      ResultSet results = prepStatement.executeQuery();
      boolean found = false;

      if ( results.next())
     {     phoneno = results.getString("phoneno");
           email = results.getString("email");
           found = true;
     }
     return found;
  }
  catch(Exception ex)
  {
      System.out.println( ex.getMessage());
      return false;
   }
   finally
  {
       clean(con,prepStatement);
  } 

 } // end of isValid

 public String updatePassword(String newpassword) 
 {
   Connection con = null;
   PreparedStatement prepStatement= null;

   try
   {
     con = getConnection();
     prepStatement = con.prepareStatement("update customers set pwd = ? where custname = ?");
     prepStatement.setString(1,newpassword);
     prepStatement.setString(2,custname);

     int cnt = prepStatement.executeUpdate();
     if ( cnt==1 ) 
        return null;
     else
        return "Invalid Username!";
      
  }
  catch(Exception ex)
  {
      System.out.println( ex.getMessage());
      return ex.getMessage();
  }
  finally
  {
       clean(con,prepStatement);
  } 

 } // end of updatePassword

 public String  registerUser()
 {
    Connection con = null;
    PreparedStatement prepStatement = null;

    try
    {
        con = getConnection();
        prepStatement = con.prepareStatement("insert into customers values (?,?,?,?)");
        prepStatement.setString(1,custname);
        prepStatement.setString(2,password);
        prepStatement.setString(3,email);
        prepStatement.setString(4,phoneno);
        prepStatement.executeUpdate();
        return null;

    }
    catch(Exception ex)
    {
       return ex.getMessage();
    }
    finally
    {  clean(con,prepStatement); }
 }

        


 public  void clean(Connection con, PreparedStatement prepStatement)
 { 
   try
   { if ( prepStatement != null )  prepStatement.close();
     if ( con != null) con.close();
    }
    catch(Exception ex)
    { System.out.println(ex.getMessage()); }
 }

 public Connection getConnection() throws Exception
 {
   Class.forName("com.mysql.jdbc.Driver"); 
   // connect using Thin driver
   java.sql.Connection  con =   DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","SQLDEVSERVER");

   return con;
 }    

 
 public Context getInitialContext() 
 {
 
  String JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";

  try
  {
   Hashtable env = new Hashtable();
   env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
   env.put(Context.PROVIDER_URL,"t3://localhost:7001");
   return new InitialContext(env);
  }
  catch(Exception ex)
  { 
    System.out.println(ex.getMessage()); 
    return null;
  }

 }
  

} // end of bean
  
       
    
   




