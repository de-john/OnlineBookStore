package obs;
import java.sql.*;
import java.util.*;


public class User
{
 private int userId;
 private String userName;
 private String pwd;
 private String email;
 private String address;
 private String phone;

 private boolean logged = false;

 public int getUserid()
 { return userId; }

 public void setUname(String userName)
 { this.userName = userName; }

 public String getUserName()
 { return userName; }

 public void setPwd(String pwd)
 { this.pwd= pwd; }

 public String getPwd()
 { return pwd; }

 public void setEmail (String email)
 { this.email = email; }

 public String getEmail()
 { return email; }

 public void setAddress(String address)
 { this.address = address; }

 public String getAddress()
 { return address; }


 public void setPhone(String phone)
 { this.phone = phone; }

 public String getPhone()
 { return phone; }


 public void login()
 {
 Connection con = null;
 PreparedStatement prepStatement = null;
 try
 {
 con = getConnection();
 prepStatement = con.prepareStatement("select userId,email,phone,address from users where userName = ? and pwd= ?");
 prepStatement.setString(1,userName);
 prepStatement.setString(2,pwd);

 ResultSet rs = prepStatement.executeQuery();

 logged = false;

 if ( rs.next())
 { userId = rs.getInt("userid");
 email = rs.getString("email");
 address = rs.getString("address");
 phone = rs.getString("phone");
 logged = true;
 }
 
 }
 catch(Exception ex)
 {
 System.out.println( ex.getMessage());
 }
 finally
 {
 clean(con,prepStatement);
 } 

 } // end of isValid



 public String updateProfile(String newpwd) 
 {
 Connection con = null;
 PreparedStatement prepStatement= null;

 try
 {
 con = getConnection();
 String cmd = "update users set email=?, phone = ? , address =? ";
 
 if (! newpwd.equals(""))
 cmd += " , pwd = '" + newpwd + "'";
 
 cmd = cmd + " where userId = ?";

 prepStatement = con.prepareStatement(cmd);
 prepStatement.setString(1,email);
 prepStatement.setString(2,phone);
 prepStatement.setString(3,address);
 prepStatement.setInt(4,userId);

 int cnt = prepStatement.executeUpdate();
 if ( cnt==1 ) 
 {
 if ( ! newpwd.equals("") )
 pwd = newpwd; 
 return null;
 }
 else
 return "Invalid User. Unable to update profile.";
 
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

 } // end of updateProfile



 public String registerUser()
 {
 Connection con = null;
 PreparedStatement prepStatement = null;

 try
 {
 con = getConnection();
 userId = getNextUserid(con);
 prepStatement = con.prepareStatement("insert into users values (?,?,?,?,?,?)");
 prepStatement.setInt(1,userId);
 prepStatement.setString(2,userName);
 prepStatement.setString(3,pwd);
 prepStatement.setString(4,email);
 prepStatement.setString(5,address);
 prepStatement.setString(6,phone);
 prepStatement.executeUpdate();
 logged = true;
 return null;
 }
 catch(Exception ex)
 {
 return ex.getMessage();
 }
 finally
 { clean(con,prepStatement); }
 }

 public boolean isLogged()
 {
	return logged;
	
 }

 int j = 1;
 public int getNextUserid(Connection con) throws Exception
 {
	//int j = 0;
 //PreparedStatement ps = null;
 //ps = con.prepareStatement("select nvl( max(userid),0) + 1 from users");
 //ResultSet rs = ps.executeQuery();
 //rs.next();
 int nextuserid = j;
 j++;
 //rs.close();

 return nextuserid; 
 
 }
 
 
 public void clean(Connection con, PreparedStatement prepStatement)
 { 
 try
 { if ( prepStatement != null ) prepStatement.close();
 if ( con != null) con.close();
 }
 catch(Exception ex)
 { System.out.println(ex.getMessage()); }
 }

 public Connection getConnection() throws Exception
 {
 Class.forName("com.mysql.jdbc.Driver"); 

 java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","SQLDEVSERVER");
 return con;
 } 

}