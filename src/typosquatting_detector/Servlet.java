package typosquatting_detector;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Servlet extends HttpServlet {
		
	private static final long serialVersionUID = 6248427955682198986L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if(request.getParameter("input") != null && request.getParameter("input") != "" && request.getParameter("input").indexOf(' ') == -1) {
			try {
				Server server = ServerImpl.getServer();
				server.setReportPath(getServletContext().getRealPath("/"));
				server.assignWork(request.getParameter("input"));
				request.setAttribute("report", "report.html");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
        }
        else {
            request.setAttribute("output", "No Input Received!");
        }
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
    
}