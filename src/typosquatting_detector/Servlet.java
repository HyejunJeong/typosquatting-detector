package typosquatting_detector;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet extends HttpServlet {
		
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if(request.getParameter("input") != null && request.getParameter("input") != "") {
    		Server mn;
			try {
				mn = new Server(request.getParameter("input"));
				request.setAttribute("output", mn.getTypos());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else {
            request.setAttribute("output", "No input received!");
        }
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
}
