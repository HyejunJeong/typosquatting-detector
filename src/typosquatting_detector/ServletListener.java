package typosquatting_detector;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent arg) {
		// Debug
		System.out.println("hi from cl");
		
		try {
			LocateRegistry.createRegistry(1099);
		} 
		catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
