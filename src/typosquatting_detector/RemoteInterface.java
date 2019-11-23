package typosquatting_detector;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface RemoteInterface extends Remote {
	
	public void addClient(RemoteInterface iclient) throws RemoteException;
			
	public String pollURL() throws RemoteException;
	
	public LinkedList<RemoteInterface> getClientList() throws RemoteException;
	
	public ConcurrentLinkedQueue<String> getURLQueue() throws RemoteException;
	
	public String getClientID() throws RemoteException;
	
}
