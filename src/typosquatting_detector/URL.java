package typosquatting_detector;

import java.io.Serializable;

public class URL implements Serializable {

	private static final long serialVersionUID = -8235186917411768728L;
	
	private String url;

	public URL(String iurl) {
		url = iurl;
	}
	
	public String getURL() {
		return url;
	}
	
	public void setURL(String iurl) {
		url = iurl;
	}
	
	public String toString() {
		return url;
	}
	
}
