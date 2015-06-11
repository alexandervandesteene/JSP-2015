package howest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Date;

public class TSQL {


	private PreparedStatement fPrepared;
	private int fNextValue;

	
	public TSQL() {
		this.fNextValue = 1;
		this.fPrepared = null;
	}
	
	public TSQL(TController theController, String theSQL) {
		this.fNextValue = 1;
		try {
			this.fPrepared = theController.getConnection().prepareStatement(theSQL);

		} catch (SQLException e) {
			this.fPrepared = null;
			System.out.println("TSQL.new - prepare got an error: " + e.toString());
		}
	}
	
	public void close() {
		if (this.fPrepared != null) 
			try {
				this.fPrepared.close();
			} catch (Exception e) {
				System.out.println("TSQL.close: failed to close - " + sqlString(this.fPrepared) + "\n" + e.toString());
			}
		this.fPrepared = null;
	}
	
	

	public TSQL setValue(String theValue) {
		try {
			this.fPrepared.setString(this.fNextValue++, theValue);

		} catch (Exception e) {
			System.out.println("TSQL.setValue: failed for column: " +
					(this.fNextValue-1) + ", string: " + theValue);
		}
		return this;
	}

	public TSQL setValue(int theValue) {
		try {
			this.fPrepared.setInt(this.fNextValue++, theValue);

		} catch (Exception e) {
			System.out.println("TSQL.setValue: failed for column: " +
					(this.fNextValue-1) + ", int: " + theValue);
		}
		return this;
	}

	public TSQL setValue(Date theValue) {
		try {
			this.fPrepared.setDate(this.fNextValue++, theValue);
		} catch (Exception e) {
			System.out.println("TSQL.setValue: failed for column: " + 
					(this.fNextValue-1) + ", date: " + theValue);
		}
		return this;
	}
	

	/* get results from the databases -> select statement */
	public TResults getList() {
		TResults aList = new TResults();
		
		if (this.fPrepared == null)  {
			System.out.println("SQL.getResults: no SQL statement provided");
			return null;
		}

		try {
			ResultSet aRset = this.fPrepared.executeQuery();
			System.out.println("SQL.getResults: " + sqlString(this.fPrepared));
			aRset.beforeFirst(); // generates error if query failed

			// get types of all columns
			ResultSetMetaData aMeta = aRset.getMetaData();
			int aCount = aMeta.getColumnCount();
			int aColTypes[] = new int[aCount+1];
			for (int i=1; i<=aCount; i++)					
				aColTypes[i] = aMeta.getColumnType(i);

			// Loop through results, make records and put into list
			aRset.beforeFirst();
			Object anObject;
			int aNr = 0;
			while (aRset.next()) {
				TRecord aRecord = new TRecord();
				for (int i=1; i<=aCount; i++) { 					
					anObject = aRset.getObject(i);
					if (anObject != null) {
						aRecord.put(aMeta.getColumnLabel(i), anObject);
					}
				}
				aNr++;
				aList.add(aRecord);
			}			
			aRset.close();
			System.out.println("TSQL.getList: fetched " + aNr + " rows.");

		} catch(Exception e) {
			System.out.println("SQL.getResults: got an error from " + 
					sqlString(this.fPrepared) + "\n" + e.toString());
			return null;
		}
		this.close();
		return aList;
	}
	
	public TRecord getRecord() {
		TResults aResult = this.getList();
		if (aResult.size() > 0)
			return aResult.get(0);
		else {
			System.out.println("SQL.getRecord: no record fetched, returning empty record.");
			return new TRecord();
		}
	}
	
	/* execute a statement -> update, insert, delete, create */
	public TSQL execute() {
		if (this.fPrepared == null)  {
			System.out.println("TSQL.execute: no SQL statement provided");
			return this;
		}

		try {
			this.fPrepared.executeUpdate();
			System.out.println("TSQL.execute: " + sqlString(this.fPrepared));

			this.close();
			return this;

		} catch(Exception e) {
			System.out.println("TSQL.execute: got an error from " +
					sqlString(this.fPrepared) + "\n" + e.toString());
			return null;
		}
	}
	
	
	/* helper method to display a prepared statement */
	public static String sqlString(PreparedStatement theStatement) {
		String aMessage = theStatement.toString();
		int anI = aMessage.indexOf(":");
		if (aMessage.startsWith("com.") && (anI > 0)) {
			return aMessage.substring(anI+2);
		} else
			return aMessage;
	}



}
