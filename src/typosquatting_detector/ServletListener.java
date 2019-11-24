package typosquatting_detector;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletListener implements ServletContextListener {
	
	private Registry registry;
	
	@Override
	public void contextInitialized(ServletContextEvent arg) {
		try {
			registry = LocateRegistry.createRegistry(1099);
			ServerImpl.init();
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
				registry.unbind("Server");
				UnicastRemoteObject.unexportObject(registry, true);
			} 
			catch (RemoteException | NotBoundException e) {
				e.printStackTrace();
			}
		} 
	}

}
