package howest;

import java.util.HashMap;


public class TRecord extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;


	public TRecord() {
	}
	
	
	public Object get(String theKey, Object theDefault) {
		Object anObject = this.get(theKey);
		return (anObject == null) ? theDefault : anObject;
	}


	public long get(String theKey, long theDefault) {
		Object anObject = this.get(theKey);
		if (anObject == null) 
			return theDefault;
		else {
			if (anObject.getClass().getName().equals("java.lang.Integer"))
				return (long) ((int)((Integer) anObject));
			else if (anObject.getClass().getName().equals("java.math.BigInteger"))
				return ((java.math.BigInteger) anObject).longValue();
			else if (anObject.getClass().getName().equals("java.math.BigDecimal"))
				return ((java.math.BigDecimal) anObject).longValue();
			else if (anObject.getClass().getName().equals("java.lang.String"))
				return asLong((String) anObject, theDefault);
			else
				return (Long) anObject;
		}
	}
	public int get(String theKey, int theDefault) {
		Object anObject = this.get(theKey);
		if (anObject == null) 
			return theDefault;
		else {
			if (anObject.getClass().getName().equals("java.lang.Long"))
				return (int) ((long)((Long) anObject));
			else if (anObject.getClass().getName().equals("java.math.BigInteger"))
				return ((java.math.BigInteger) anObject).intValue();
			else if (anObject.getClass().getName().equals("java.math.BigDecimal"))
				return ((java.math.BigDecimal) anObject).intValue();
			else if (anObject.getClass().getName().equals("java.lang.String"))
				return asNum((String) anObject, theDefault);
			else
				return (Integer) anObject;
		}
	}

	
	public String get(String theKey, String theDefault) {
		Object anObject = this.get(theKey);
		return (anObject == null) ? theDefault : (String) anObject.toString();
	}
	

	public double get(String theKey, double theDefault) {
		Object num = this.get(theKey);
		if (num == null)
			return theDefault;
       	if (num.getClass().getName().equals("java.math.BigDecimal"))
        	return ((java.math.BigDecimal)num).doubleValue();
		else if (num.getClass().getName().equals("java.lang.String"))
			return asDouble((String) num, theDefault);
       	else
       		return ((java.lang.Double)num);
	}
	
		
	public static int asNum(String theString, int theDefault) {
		try {
			return Integer.parseInt(theString);
		} catch(Exception e) {
			return theDefault;
		}
	}

	public static long asLong(String theString, long theDefault) {
		try {
			return Long.parseLong(theString);
		} catch(Exception e) {
			return theDefault;
		}
	}

	public static double asDouble(String theString, double theDefault) {
		try {
			return Double.parseDouble(theString);
		} catch(Exception e) {
			return theDefault;
		}
	}
	


}
