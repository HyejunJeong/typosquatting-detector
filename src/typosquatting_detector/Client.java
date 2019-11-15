package typosquatting_detector;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Queue;
import java.util.LinkedList;

public class Client implements Remote, ClientWork{
	
	public String communicate() {
		return "Hello from Service";
	}
	
	public void go() throws RemoteException, NotBoundException, Exception {
		Service tester = (Service) Naming.lookup("//localhost/Test");
		Queue<String> list = new LinkedList<String>();
		for(String s: tester.communicate().split(" ")){
		      list.add(s);
		}
		System.out.println(list.toString());
		//list here to give to worker nde dispatcher
	}

}
