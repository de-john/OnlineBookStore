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
  public void setSessionContext(SessionContext ctx) {}

  public void ejbCreate  () throws CreateException {}


  public String addOrder(int userID, ArrayList items) 
  {
    Connection con=null;
    String orderID;
    PreparedStatement ps=null;

    try
    {

       Context  ctx  = new InitialContext();
       DataSource ds = (DataSource) ctx.lookup("oracle");
       con =ds.getConnection();
       
       // get highest order id + 1 for this order
       ps= con.prepareStatement("select orderID.nextval from dual");
       ResultSet  rs= ps.executeQuery();
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


       ps = con.prepareStatement("insert into orders values(?,?,sysdate,?,'n')");
       ps.setString(1,orderID);
       ps.setInt(2,userID);
       ps.setInt(3,totamt);
      
       ps.executeUpdate();
       ps.close();

       // insert into orderitems

       ps = con.prepareStatement("insert into orderitem values(?,?,?,?)");

       itr = items.iterator();
       while ( itr.hasNext())
       {
             item = (Item) itr.next();
             ps.setString(1,orderID);
             ps.setString(2, item.getIsbn());
             ps.setInt(3,item.getPrice());
             ps.setInt(4,item.getQty());

             ps.executeUpdate();
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
      {  if (ps != null ) ps.close();
         if (con != null) con.close();
      }
      catch(Exception ex) {}
    }     
    return null;

  } // end of addOrder



  public boolean cancelOrder(int orderID) 
  {
    Connection con=null;
    PreparedStatement ps=null;

    try
    {

       Context  ctx  = new InitialContext();
       DataSource ds = (DataSource) ctx.lookup("oracle");
       con =ds.getConnection();

       // delete from ORDERITEM table
       
       ps = con.prepareStatement("delete from orderitem where orderID = ?");
       ps.setInt(1,orderID);
           
       ps.executeUpdate();
       ps.close();

       // delete from ORDERS table

       ps = con.prepareStatement("delete from orders where orderID = ?");
       ps.setInt(1,orderID);
           
       int cnt = ps.executeUpdate();
       ps.close();

        return cnt == 1;
      
       
    }
    catch(Exception ex)
    {
       ex.printStackTrace();
    }
    finally
    {  
      try
      {  if (ps != null ) ps.close();
         if (con != null) con.close();
      }
      catch(Exception ex) {
          ex.printStackTrace();
      }
    }     
    return false;

  } // end of cancelOrder

} // end of OrderBean
