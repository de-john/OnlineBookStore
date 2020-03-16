package obs.order;

import javax.ejb.*;
import javax.naming.*;
import javax.sql.*;
import java.sql.*;
import java.util.*;
import obs.*;



public class OrderBean implements SessionBean {

  public void ejbActivate() {}
  public void ejbRemove()   {}    // end of remove

  public void ejbPassivate() {}
  public void setSessionContext(SessionContext context) {}

  public void ejbCreate  () throws CreateException {}


  public String addOrder(int userID, ArrayList items) 
  {
    Connection con=null;
    String orderID;
    PreparedStatement prepStatement=null;

    try
    {

       Context  context  = new InitialContext();
       DataSource dataSource = (DataSource) context.lookup("oracle");
       con =dataSource.getConnection();

       // get highest order id + 1 for this order
       prepStatement= con.prepareStatement("select orderID.nextval from dual");
       ResultSet  rs= prepStatement.executeQuery();
       rs.next();

       orderID = rs.getString(1);

       rs.close();

       // get total amount

       int totamt =0;
       Item item;

       Iterator itr = items.iterator();
       while ( itr.hasNext())
       {
             item = (Item) itr.next();
             totamt +=  item.getPrice() * item.getQty();
       }


       prepStatement = con.prepareStatement("insert into orders values(?,?,sysdate,?,'n')");
       prepStatement.setString(1,orderID);
       prepStatement.setInt(2,userID);
       prepStatement.setInt(3,totamt);

       prepStatement.executeUpdate();
       prepStatement.close();

       // insert into orderitems

       prepStatement = con.prepareStatement("insert into orderitem values(?,?,?,?)");

       itr = items.iterator();
       while ( itr.hasNext())
       {
             item = (Item) itr.next();
             prepStatement.setString(1,orderID);
             prepStatement.setString(2, item.getIsbn());
             prepStatement.setInt(3,item.getPrice());
             prepStatement.setInt(4,item.getQty());

             prepStatement.executeUpdate();
       }


       return orderID;

    }
    catch(Exception ex)
    {
       ex.printStackTrace();
    }
    finally
    {
      try
      {  if (prepStatement != null ) prepStatement.close();
         if (con != null) con.close();
      }
      catch(Exception ex) {}
    }
    return null;

  } // end of addOrder



  public boolean cancelOrder(int orderID)
  {
    Connection con=null;
    PreparedStatement prepStatement=null;

    try
    {

       Context  context  = new InitialContext();
       DataSource dataSource = (DataSource) context.lookup("oracle");
       con =dataSource.getConnection();

       // delete from ORDERITEM table
       
       prepStatement = con.prepareStatement("delete from orderitem where orderID = ?");
       prepStatement.setInt(1,orderID);
           
       prepStatement.executeUpdate();
       prepStatement.close();

       // delete from ORDERS table

       prepStatement = con.prepareStatement("delete from orders where orderID = ?");
       prepStatement.setInt(1,orderID);
           
       int cnt = prepStatement.executeUpdate();
       prepStatement.close();

        return cnt == 1;
      
       
    }
    catch(Exception ex)
    {
       ex.printStackTrace();
    }
    finally
    {  
      try
      {  if (prepStatement != null ) prepStatement.close();
         if (con != null) con.close();
      }
      catch(Exception ex) {
          ex.printStackTrace();
      }
    }     
    return false;

  } // end of cancelOrder

} // end of OrderBean
