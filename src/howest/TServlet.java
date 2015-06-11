package howest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	public Connection getConnection() {
		try {
			ServletContext aContext = this.getServletContext();
			String aDBUsername = aContext.getInitParameter("gDBUser");      // root or mysql
			String aDBPassword = aContext.getInitParameter("gDBPassword");	// xxx
			String gDBUrl = aContext.getInitParameter("gDBUrl");

			System.out.println("Router.getConnection: with user " + aDBUsername + " to " + gDBUrl);

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("driver loaded");
			Connection aCon = DriverManager.getConnection(gDBUrl,aDBUsername, aDBPassword);
			//Connection aCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/codelib", "root", "");
			System.out.println("Router.getConnection: Connected to the database");
			return aCon;

		} catch (Exception e) {
			System.out.println("Router.getConnection: Could not get a connection.");
			e.printStackTrace();
		}
		return null;
	}
	
    private TController makeController(String controllerName) {
    	 
    	System.out.println(
          "Router.makeController: trying to create " + controllerName);

    	try {
 	       Class<?> aClass = Class.forName( controllerName );
 	       return (TController) aClass.newInstance();

    	} catch (Exception e) {
        	try {
      	       Class<?> aClass = Class.forName( "howest." + controllerName );
      	       return (TController) aClass.newInstance();

         	} catch (Exception ee) {
      	       System.out.println(e.toString());
      	       return new TController();
         	}
 	   
    	}

    }
	
    public TServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String name = request.getPathInfo();
    	
    	// try to decode values for next 3: /do/controllername/action/id
    	// example: /do/User/edit/12 -> howest.UserController.doRequest("edit", 12)
    	String controllerName = "";
    	String action = "";
    	int id = 0;
    	
    	try {
        	String[] path = name.split("/");  
        	
	    	// decode  /controllername/action/id
	    	if (path.length >= 2) {
	    		controllerName = "howest." + path[1] + "Controller";
	    		request.setAttribute("current", path[1]);
	    		if (path.length >= 3) {
	    			action = path[2];
	    			if (path.length >= 4) {
	    				id = Integer.parseInt(path[3]);
	    			}
	    		}
	    	}
	    	
	    	// also allow action as post/get parameter
	    	String actionStr = request.getParameter("action");
    		if (actionStr != null) action = actionStr;
    		
	    	// also allow id as post/get parameter
	    	String idStr = request.getParameter("id");
    		if (idStr != null) id = Integer.parseInt(idStr); // can generate an exception !
    		
    	} catch (Exception e) {
    		// id is already 0 if parseInt fails.
    	}
    	
		TController aCtl = this.makeController(controllerName);
		
		if(aCtl.needsLogin())
		{
			System.out.println("Needs login!");
			Object login  = request.getSession().getAttribute("login");
			if(login == null)
			{
				System.out.println("no login found");
				aCtl = makeController("UserController");
				action = "login";
			}
			
		}
		
		aCtl.init(request, response, this.getConnection());
		aCtl.doRequest(action, id);		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request,  response);
	}

}
