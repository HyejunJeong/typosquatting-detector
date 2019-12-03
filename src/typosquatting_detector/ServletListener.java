package typosquatting_detector;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent arg) { 
		ServerImpl.init();
		ReportGenerator.init();
	}

}