package typosquatting_detector;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface ServerInterface extends Remote {
	
	public LinkedList<String> getQueue() throws RemoteException;
	
	public String pollURL() throws RemoteException;
	
}
