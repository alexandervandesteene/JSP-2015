package howest;

import howest.TController;
import howest.TRecord;
import howest.TSQL;

public class FragmentController extends TController {

	public void doRequest(String action, int id) {
		System.out.println("FragmentController.doRequest, with action = " + action);
		
		if ((action.isEmpty()) || (action.equalsIgnoreCase("list"))) {
			// list all Fragments
			this.doList();
			this.forward("/fragment-list.jsp");

			
		} else if (action.equalsIgnoreCase("search")){
			// list all fragments with title or tags containing param-"q"	
			this.doSearch();
			this.forward("/fragment-list.jsp");
		
			
		} else if (action.equalsIgnoreCase("new")){
			// make new record -> show empty form	
			this.setVar("fragment", new TRecord());
			this.getLanguages();
			this.forward("/fragment-edit.jsp");
		
			
		} else if (action.equalsIgnoreCase("delete")){
			// delete a fragment with id
			// when done show the list again
			this.doDelete(id);
			this.doList();
			this.forward("/fragment-list.jsp");
			
			
		} else if (action.equalsIgnoreCase("save")){
			// update or insert into the database, resp. on i != 0 or == 0
			// when done show the list again
			this.doSave(id);
			this.doList();
			this.forward("/fragment-list.jsp");
			
			
		} else if (action.equalsIgnoreCase("view")){
			// show record from fragments
			this.doGet(id);
			this.doComments(id);
			this.doUser();
			this.forward("/fragment-view.jsp");
			
			
		} else if (action.equalsIgnoreCase("edit")){
			// get record + show in form
			this.doGet(id);
			this.getLanguages();
			this.forward("/fragment-edit.jsp");

		}else if (action.equalsIgnoreCase("savecomment")){
			// get record + show in form
			this.doSaveComment();
			this.doList();
			this.forward("/fragment-list.jsp");

		}else if (action.equalsIgnoreCase("deletecomment")){
			// get record + show in form
			this.doDelComment(id);
			this.doList();
			this.doGet(id);
			this.doComments(id);
			this.doUser();
			this.forward("/fragment-view.jsp");

		}else{
			this.forward("/error.jsp");
		}
		
		
		
	}
	
	void getLanguages() {
		this.setVar("languages",
			new TSQL(this, "select id, name from languages order by name")
				.getList());
	}
	
	void doGet(int id) {
		this.setVar("fragment", 
				new TSQL(this, "select id, title, at, tags, code, language from fragments where id = ?")
			    	.setValue(id)
			    	.getRecord());
	}
	
	void doDelete(int id) {
		new TSQL(this, "delete from fragments where id = ?")
	    	.setValue(id)
	    	.execute();
	}
	
	void doSave(int id) {
		if (id == 0) {
			new TSQL(this, "insert into fragments (title, at, tags, code, language,user) " +
					" values (?, now(), ?, ?, ?,?)")
				.setValue(this.getParam("title", ""))
				.setValue(this.getParam("tags", ""))
				.setValue(this.getParam("code", ""))
				.setValue(this.getParam("language", 0))
				.setValue(this.getParam("user", ""))
				.execute();
		} else {
			new TSQL(this, "update fragments set title = ?, tags = ?, code = ?, language = ? where id = ?")
			.setValue(this.getParam("title", ""))
				.setValue(this.getParam("tags", ""))
				.setValue(this.getParam("code", ""))
				.setValue(this.getParam("language", 0))
				.setValue(id)
				.execute();
		}
	}
	
	void doList() {
		this.setVar("fragments",
				  new TSQL(this, "select F.id, title, at, tags, code, L.name as lname " +
						" from fragments F " +
						" left outer join languages L on L.id = F.language" +
						" order by at desc limit 15")
				    .getList() 
				);
	}
	
	void doSearch() {
		String q = "%" + this.getParam("q", "") + "%";
		
		this.setVar("fragments",
				  new TSQL(this, "select F.id, title, at, tags, code, L.name as lname " +
						" from fragments F " +
						" left outer join languages L on L.id = F.language" +
						" where (title like ? or tags like ?)" +
						" order by name")
					.setValue(q)
					.setValue(q)
				    .getList() 
				);
	}
	void doComments(int id)
	{
		this.setVar("comments", new TSQL(this, "select * from comments "
				+ "where fragment=? order by `at` desc").setValue(id).getList());
	}
	void doUser()
	{
		this.setVar("users", new TSQL(this, "select * from users ").getList());
	}
	void doSaveComment()
	{
		new TSQL(this, "insert into comments (user, fragment, at, what) " +
				" values (?,?,now(),?)")
			.setValue(this.getParam("userid", ""))
			.setValue(this.getParam("fragmentid", ""))
			.setValue(this.getParam("comment", ""))
			.execute();
	}
	void doDelComment(int id)
	{
		new TSQL(this, "delete from comments where id = ?")
    	.setValue(id)
    	.execute();
	}
		
	/* aanmaken van de table comments
	 * CREATE TABLE `comments` (
		`id` int(11) NOT NULL AUTO_INCREMENT primary key,
		`user` int(11),
		`fragment` int(11),
		`at` date,
		`what` text DEFAULT ''
		);
	 */
}
