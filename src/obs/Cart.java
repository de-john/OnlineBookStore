// Cart
package obs;
import java.util.*;
import java.sql.*;
import obs.*;
import javax.ejb.*;
import javax.naming.*;
import obs.order.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  




public class Cart
{
   ArrayList items = new ArrayList();
   
   public Item find(String isbn)
   {
     Iterator iterator = items.iterator();
     Item item;
     while ( iterator.hasNext())
     {
       item =  (Item) iterator.next();
       if ( item.getIsbn().equals(isbn))
       {
          return item;
       }
     }  // end of while

     return null;
   }
   
   // adds an item if not already existing
  // otherwise add 1 to quantity
   public void addItem(String isbn )
   {
     //check whether isbn is already present
     Item item =  find(isbn);
     if ( item != null)
       item.addQty(1);
     else
     {
       // get details from Books tables

         User  user  = new User();
         try (Connection con = user.getConnection()) {
             PreparedStatement prepStatement = con.prepareStatement("select title,price from books where isbn = ? ");
             prepStatement.setString(1, isbn);

             ResultSet results = prepStatement.executeQuery();
             if (results.next()) {
                 item = new Item(isbn, results.getString(1), results.getInt(2));
                 items.add(item);
             }

             results.close();
             prepStatement.close();

         } catch (Exception ex) {
             System.out.println(ex.getMessage());
         }
     }  // end of else
   }       

   public  ArrayList<Integer>  getItems()
   {  return items; }

   public  void removeItem(String isbn)
   {
       Item item = find(isbn);
       if  ( item != null)
           items.remove(item);
   } // end or removeItem

    public void clearAll()
    {
       items.clear();
   }

   public void updateQty(String isbn, int quantity)
   {
        Item  item = find(isbn);
        if ( item != null)
          item.setQty(quantity);
   } // end of updateQty()


  public boolean finalizeOrder(int userid,int total)
  {
     
	  try {
			Scanner sc=new Scanner(System.in);
			
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/test";
			String user="root";
			String pass="SQLDEVSERVER";
			Connection con=DriverManager.getConnection(url,user,pass);
			String sql="INSERT INTO `orders` (`ordid`, `userid`, `orddate`, `totamt`, `status`, `isbn`) VALUES (NULL, '"+userid+"', '"+now+"', '"+total+"', 'a', '2')";
			Statement statement=con.createStatement();
			statement.executeUpdate(sql);
			con.close();
			System.out.println("Record inserted");
			
			return true;
			
		} catch(Exception ex)
	   	{
		     System.out.println( ex.getMessage());
		     return false;
		 
		   }

  } // end of  finalizeOrder



  public boolean cancelOrder(int ordid) 
  {
     
   try
   {
      Context context = getInitialContext();
      // get access to bean

      OrderHome home = (OrderHome)  context.lookup("obs.order");
      Order order = home.create();

      return order.cancelOrder(ordid);
     
   }
   catch(Exception ex)
   {
     System.out.println( ex.getMessage());
     return false;
 
   }


  } // end of  finalizeOrder

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

}

   


