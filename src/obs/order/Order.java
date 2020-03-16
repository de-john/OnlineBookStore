package obs.order;

import java.rmi.RemoteException;
import javax.ejb.EJBObject;
import java.util.*;

public interface Order extends EJBObject
{

  public String  addOrder(int userId, ArrayList items)
    throws RemoteException;
  public boolean cancelOrder(int ordId) throws RemoteException;
}
