package howest;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TController {

	private HttpServletRequest fHttpRequest;
	private HttpServletResponse fHttpResponse;
	private HttpSession fHttpSession;
	private Connection fConnection = null;

	TController() {

	}
	public void init(HttpServletRequest theRequest, HttpServletResponse theResponse, Connection theConnection) {
		this.fHttpRequest = theRequest;
		this.fHttpResponse = theResponse;
		this.fHttpSession = theRequest.getSession();
		this.fConnection = theConnection;
		
		this.fHttpResponse.setCharacterEncoding("UTF-8");		
	}
	
	public boolean needsLogin(){
		return false;
	}
	
	public void doRequest(String request, int id) {
		System.out.println("TController.doRequest(" + request + ")");
		this.forward("/index.jsp");
	}
	
	public void forward(String JSP) {
		try {
			RequestDispatcher aReqDispatcher = this.fHttpRequest.getRequestDispatcher(JSP);
			aReqDispatcher.forward(this.fHttpRequest, this.fHttpResponse);
		} catch(Exception e) {
			System.out.println("TController.forward to " + JSP + " failed\nError: " + e.toString());
		}
	}
	

	public void setSession(String theName, Object theObject) {
		this.fHttpSession.setAttribute(theName, theObject);
	}
	
	public Object getSession(String theName) {
		return this.fHttpSession.getAttribute(theName);
	}
	
	public void removeSession(String theName)
	{
		this.fHttpSession.removeAttribute(theName);
	}
	
	/* store in session for JSP */
	public void setVar(String theName, Object theObject) {
		this.fHttpRequest.setAttribute(theName, theObject);
	}
	
	/* Param handling */
	public String getParam(String theName, String theDefault) {
		String val = this.fHttpRequest.getParameter(theName);
		
		return (val == null) ? theDefault : val;
	}

	public int getParam(String theName, int theDefault) {
		String val = this.fHttpRequest.getParameter(theName);
		
		if (val == null) return theDefault;
		
		try { 
			return Integer.parseInt(val);
		} catch (NumberFormatException e) {
			return theDefault;
		}	
	}

	public long getParam(String theName, long theDefault) {
		String val = this.fHttpRequest.getParameter(theName);
		
		if (val == null) return theDefault;
		
		try { 
			return Long.parseLong(val);
		} catch (NumberFormatException e) {
			return theDefault;
		}	
	}

	public double getParam(String theName, double theDefault) {
		String val = this.fHttpRequest.getParameter(theName);
		
		if (val == null) return theDefault;
		
		try { 
			return Double.parseDouble(val);
		} catch (NumberFormatException e) {
			return theDefault;
		}	
	}
	
	public Date getParam(String theName, Date theDefault) {
		String val = this.fHttpRequest.getParameter(theName);
		
		if (val == null) return theDefault;
		
        SimpleDateFormat aDDMMYYYY = new SimpleDateFormat("dd-MM-yyyy");
        try {
        	return aDDMMYYYY.parse(val);
        } catch (Exception e) {
        	return theDefault;
        }
	}

	
	/* database connection */
	public Connection getConnection() {
		return this.fConnection;
	}
	

}
