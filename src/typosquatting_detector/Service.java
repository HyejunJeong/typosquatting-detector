package typosquatting_detector;
import java.io.Serializable;

public class Service implements ClientWork, Serializable {
	
	public String communicate() {
		return "Hello from Service";
	}

}
