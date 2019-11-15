package typosquatting_detector;
import java.io.Serializable;
import java.util.LinkedList;

public class Service implements ClientWork, Serializable {
	
	private String message;
	private LinkedList<String> typos;
	
	public Service(String message) {
		this.message = message;
	}
	
	public void setTypos(LinkedList<String> typos) {
		this.typos = typos;
	}
	
	public String communicate() {
		return this.message;
	}
	
}
