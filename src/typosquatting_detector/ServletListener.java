package typosquatting_detector;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletListener implements ServletContextListener {
	
	Registry registry;
	
	@Override
	public void contextInitialized(ServletContextEvent arg) {
		// Debug
		System.out.println("hi from cl");
		
		try {
			registry = LocateRegistry.createRegistry(1099);
		} 
		catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg) {
		// Close server
		if (registry != null) {
			try {
				registry.unbind("Test");
				UnicastRemoteObject.unexportObject(registry, true);
			} 
			catch (RemoteException | NotBoundException e) {
				e.printStackTrace();
			}
		} 
	}

}
