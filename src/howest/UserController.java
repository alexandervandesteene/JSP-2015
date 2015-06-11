package howest;

import java.io.Console;

import howest.TController;
import howest.TRecord;
import howest.TSQL;

public class UserController extends TController {
	
	public void doRequest(String request, int id) {
		System.out.println("UserController.doRequest, with action = " + request);
		
		if ((request.isEmpty()) || (request.equalsIgnoreCase("login"))) {
			// list all languages
			this.forward("/login.jsp");

			
		} else if (request.equalsIgnoreCase("doLogin")){
			if(this.tryLogin()){
				this.setVar("message", "login ok");
				this.forward("/redirect.jsp");
			}else{
				this.setVar("message", "wrong username or password");
				this.forward("/login.jsp");
			
			}
		
			
		} else if (request.equalsIgnoreCase("doLogout")){
			// delete a record
			// when done show the list again
			this.removeSession("login");
			this.forward("/redirect.jsp");
			
		} else {
			this.forward("/redirect.jsp");
		}
		
	}

	private boolean tryLogin() {
		TRecord aUser= new TSQL(this,"select * from users" + " where username=? and password=?")
		.setValue(this.getParam("username", "?"))
		.setValue(this.getParam("password", "?")).getRecord();
		
		if(aUser.get("id")==null)
		{
			return false;
		}else{
			System.out.println(aUser.get("username"));
			this.setSession("login", aUser);
			return true;
		}
		
	}

}
