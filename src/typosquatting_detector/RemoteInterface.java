package typosquatting_detector;

import java.io.File;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;


public interface RemoteInterface extends Remote {
	
	public void addClient(RemoteInterface iclient) throws RemoteException;
	
	public String pollURL() throws RemoteException;
	
	public LinkedList<RemoteInterface> getClientList() throws RemoteException;
	
	public LinkedList<String> getURLQueue() throws RemoteException;
	
	public void receiveFile(File file, String url) throws RemoteException;
	
	public void sendFile() throws RemoteException;
	
}