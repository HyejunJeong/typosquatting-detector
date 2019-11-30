package typosquatting_detector;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.healthmarketscience.rmiio.RemoteInputStream;

public interface Server extends Remote {
	
	public void registerClient(String ikey, Client iclient) throws RemoteException;
	
	public void deregisterClient(String ikey) throws RemoteException;
	
	public String pollURLQueue() throws RemoteException;
	
	public boolean URLQueueIsEmpty() throws RemoteException;
	
	public void assignWork(String iurl) throws RemoteException;
	
	public void sendFile(RemoteInputStream ristream) throws IOException;
			
}