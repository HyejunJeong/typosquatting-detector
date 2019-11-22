package typosquatting_detector;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface RemoteInterface extends Remote {
	
	public void addClient(Client iclient) throws RemoteException;
	
	public String pollURL() throws RemoteException;
	
	public LinkedList<Client> getClientList() throws RemoteException;
	
	public LinkedList<String> getURLQueue() throws RemoteException;
	
}