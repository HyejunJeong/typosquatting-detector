package typosquatting_detector;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

public class Servlet extends HttpServlet {
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if(request.getParameter("input") != null && request.getParameter("input") != "") {
    		MasterNode mn = new MasterNode(request.getParameter("input"));
            request.setAttribute("output", mn.getTypos());
        }
        else {
            request.setAttribute("output", "No input received!");
        }
    	
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

}