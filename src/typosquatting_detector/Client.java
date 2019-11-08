package typosquatting_detector;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class Client implements Remote, ClientWork{
	
	public String communicate() {
		return "hello from Client";
	}
	
	public void go() throws RemoteException, NotBoundException, Exception {
		Service tester = (Service) Naming.lookup("//localhost/Test");
		System.out.println(tester.communicate());
	}

}
