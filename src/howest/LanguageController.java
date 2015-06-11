package howest;

import howest.TController;
import howest.TRecord;
import howest.TSQL;

public class LanguageController extends TController {

	public boolean needsLogin()
	{
		return true;
	}
	public void doRequest(String action, int id) {
		System.out.println("LanguageController.doRequest, with action = " + action);
		
		if ((action.isEmpty()) || (action.equalsIgnoreCase("list"))) {
			// list all languages
			this.doList();
			this.forward("/language-list.jsp");

			
		} else if (action.equalsIgnoreCase("new")){
			// make new record -> show empty form	
			this.setVar("language", new TRecord());
			this.forward("/language-edit.jsp");
		
			
		} else if (action.equalsIgnoreCase("delete")){
			// delete a record
			// when done show the list again
			this.doDelete(id);
			this.doList();
			this.forward("/language-list.jsp");
			
			
		} else if (action.equalsIgnoreCase("save")){
			// update or insert into the database, resp. on i != 0 or == 0
			// when done show the list again
			this.doSave(id);
			this.doList();
			this.forward("/language-list.jsp");
			
			
		} else if (action.equalsIgnoreCase("edit")){
			// get record + show in form
			this.doGet(id);
			this.forward("/language-edit.jsp");

		}else{
			this.forward("/error.jsp");
		}
		
		
		
	}
	
	void doGet(int id) {
		this.setVar("language", 
				new TSQL(this, "select id, name from languages where id = ?")
			    	.setValue(id)
			    	.getRecord());
	}
	
	void doDelete(int id) {
		new TSQL(this, "delete from languages where id = ?")
	    	.setValue(id)
	    	.execute();
	}
	
	void doSave(int id) {
		if (id == 0) {
			new TSQL(this, "insert into languages (name) values (?)")
				.setValue(this.getParam("name", ""))
				.execute();
		} else {
			new TSQL(this, "update languages set name = ? where id = ?")
				.setValue(this.getParam("name", ""))
				.setValue(id)
				.execute();
		}
	}
	
	void doList() {
		this.setVar("languages",
				  new TSQL(this, "select id, name from languages order by name")
				    .getList() 
				);
	}
}
