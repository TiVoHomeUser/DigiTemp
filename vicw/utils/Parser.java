package vicw.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Vector;

/**
 * @(#)Parser.java	1.0 2013/11/15
 * @author vwheeler
 * 
 * Copyright (c) 2001, 2013 Victor Wheeler vwheeler@compuserve.com All Rights Reserved.
 *
 */

 /*			Parser reads lines from a file inserting the first two words
 *		into a list of Key Value relations
 *		
 *		Two methods retrieve the value from the associated key the two 
 *			parameter method accepts a default return value if the key
 *			is not found
 *
 *			The single parameter method returns either the value or a zero length String
 *
 *
 *		Nov/15/2013
 *			New set property writeMode to enable modifying file without deleting it
 *
 *		Nov/14/2013
 *			Clean up of typos in comments, JavaDoc and prep for Java 7
 *
 *		Aug/03/2001
 *			if the File does not exist a new file of keys + default values will be
 *				created if the close() method is called. 
 *
 *			added close() clean up and creates the default file
 *
 *		Aug/26/2001
 *			additional method for Color values
 *
 *		Jan/19/2002
 *			Parser now in package parser
 *
 *		Jan/20/2002
 *			addition parameter for the default Comment String
 *
 *		Feb/3/2002
 *			color works with java constant colors like Red Blue Green Cyan ...
 *
 *		Feb/7/2002
 *			color now uses modifiers darker and brighter or lighter
 *
 *		Feb/26/2002
 *			Value Strings can now have spaces if inclosed with quotes
 *				limitation one space strings are null. Strings of two or more spaces are OK
 *					" " same as ""
 *					"  " is ok
 * ver 1.01
 *
 *		April/17/2003
 *			toString(Color c)
 *			Conversion to the English color label for Color static member values
 *
 *		April/17/2003
 *			getValuForKey double
 *
 *		April/17/2003 
 *			force  copyright author and version in the first three lines of output file
 *
 *		May/01/2003
 *			moved version to same line as app name for the forced copyright output
 *
 * ver 1.02
 *
 *		January 10 2009
 *			keyValueForKey Dimension
 *
 *		allowed use of "//" as key for comment only line (without =)
 *
 * ver 1.03
 * 
 *      January 12 2009 
 *      	keyValueForKey Date
 *      
 *      
 */
public class Parser {
	//
	private final String date_str = "Nov 15 2013";
	private final String cpyrt = "Copyright (c) 2001, 2003, 2009, 2013 Victor Wheeler parser@vicw.net All Rights Reserved.";

	private Vector<Options> optionList = new Vector<Options>();
	private boolean writeMode = false; // create the file and save default
	// parameters
	//
	// Program info stuff
	private double version = 1.03;
	private String app_name = "Options Parser " + version;

	/**
	 * 
	 * constructor takes the file name as a String
	 * 
	 * 
	 * @version 1.02, 20 Jan 2002
	 * @author Victor Wheeler
	 */
	public Parser(String fileName) {
		init(fileName);
	}

	//
	File file = null;

	private void init(String configurationFileName) {
		// Load a Vector form the file
		String s = null;
		file = new File(configurationFileName);
		try {
			if (file.length() > 0) {
				FileInputStream fin = new FileInputStream(file);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						fin));
				s = in.readLine();
				while (s != null) {
					if (s.length() > 0)
						optionList.add(new Options(s));
					s = in.readLine();
				}
				in.close();
			} // valid file
			else {
				// Enable write mode create a default parameter file.
				setWriteMode(true);
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) { // throw away
		}

		// Force version information should be stored in the text file.
		// Later may have to import from older versions.
		getValueForKey("//");
		getValueForKey("//", "Created with " + app_name + " Version " + version
				+ " " + date_str);
		getValueForKey("// ", cpyrt);
		getValueForKey("//");

	}

	/* 
	* @param mode true enable write
	* 
	* enable (or disable) adding new data to the file;
	* Note: File will not be written if false at close
	* 
	*/
	public void setWriteMode(boolean mode){
		writeMode = mode;
	}

	/**
	 * 
	 * @param String
	 *          key for the value to lookup
	 * @param bolean
	 * 			Default Value to return when the key is not found
	 *  in the list
	 * 
	 * @param String
	 * 			Comment to add to the list on the key pair line 
	 * 
	 * @return boolean
	 * 			Value found in the list or the defaultValue  
	 * 
	 */
		public boolean getValueForKey(String key, boolean defaultValue,
				String comment) {
		boolean returnvalue = getValueForKey(key, defaultValue);
		if (writeMode)
			setCommentForKey(key, comment);
		return returnvalue;
	}

	/**
	 * 
	 * @param String
	 *          key for the value to lookup
	 * @param bolean
	 * 			Default Value to return when the key is not found
	 *  in the list
	 * 
	 * @return boolean
	 * 			Value found in the list or the defaultValue  
	 * 
	 */
	public boolean getValueForKey(String key, boolean defaultValue) {

		boolean returnValue = defaultValue; // undisturbed if all tests fail

		String testBoolean = getValueForKey(key, "" + defaultValue);

		if (testBoolean.equalsIgnoreCase("TRUE"))
			return true;
		if (testBoolean.equalsIgnoreCase("FALSE"))
			return false;

		if (testBoolean.equalsIgnoreCase("T"))
			return true;
		if (testBoolean.equalsIgnoreCase("F"))
			return false;

		if (testBoolean.equalsIgnoreCase("YES"))
			return true;
		if (testBoolean.equalsIgnoreCase("NO"))
			return false;

		if (testBoolean.equalsIgnoreCase("Y"))
			return true;
		if (testBoolean.equalsIgnoreCase("N"))
			return false;

		return returnValue;
	}

	/**
	 * 
	 * @param String
	 *      	key for the value to lookup
	 *      
	 * @param double
	 * 			defaultVale String to return if the key is not found in the
	 * list
	 * 
	 * @param String
	 * 			Comment to add to the list on the key pair line 
	 * 
	 * @return double
	 * 			Value found in the list or the defaultValue  
	 * 
	 */
	public double getValueForKey(String key, double defaultValue, String comment) {
		double returnvalue = getValueForKey(key, defaultValue);
		if (writeMode)
			setCommentForKey(key, comment);
		return returnvalue;
	}

	/**
	 * 
	 * @param String
	 *      	key for the value to lookup
	 *      
	 * @param double
	 * 			defaultVale String to return if the key is not found in the
	 * list
	 * 
	 * @return double
	 * 			Value found in the list or the defaultValue  
	 * 
	 */
	public double getValueForKey(String key, double defaultValue) {
		double returnValue = defaultValue; // undisturbed for
		// NumberFormatException

		try {
			double number = Double.parseDouble(getValueForKey(key, ""
					+ defaultValue));
			returnValue = number;
		} catch (NumberFormatException e) { /* throw away */
		} finally {
		}

		return returnValue;
	}

	/**
	 * 
	 * @param String
	 *            key for the value to lookup
	 * @param Date
	 *            defaultVale String to return if the key is not found in the
	 *            list
	 * 
	 */
	public Date getValueForKey(String key, Date defaultValue, String comment) {
		Date returnvalue = getValueForKey(key, defaultValue);
		if (writeMode)
			setCommentForKey(key, comment);
		return returnvalue;
	}

	//
	public Date getValueForKey(String key, Date defaultValue) {
		Date returnValue = defaultValue; // undisturbed

		// Set for MEDIUM date and SHORT time format in English US standard.
		DateFormat format = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
				DateFormat.SHORT, new Locale("en", "US", ""));
		// Date String from file or use Default (re-formatted)
		String dateString = getValueForKey(key, format.format(defaultValue)); // new
		// String("Nov 4, 2003 8:14 PM"));

		// Parse the date
		try {
			// System.out.println("Original string: " + dateString);
			Date date = format.parse(dateString);
			// System.out.println("Parsed date    : " + date.toString());
			returnValue = date;
		} catch (ParseException pe) {
			/* throw Away */
		}

		return returnValue;
	}

	/**
	 * 
	 * @param String
	 *            key for the value to lookup
	 * @param Dimension
	 *            defaultVale String to return if the key is not found in the
	 *            list
	 * 
	 */
	public Dimension getValueForKey(String key, Dimension defaultValue,
			String comment) {
		Dimension returnvalue = getValueForKey(key, defaultValue);
		if (writeMode)
			setCommentForKey(key, comment);
		return returnvalue;
	}

	//
	public Dimension getValueForKey(String key, Dimension defaultValue) {
		Dimension returnValue = defaultValue; // undisturbed for
		// NumberFormatException

		String s = new String(getValueForKey(key, "" + defaultValue.width + ","
				+ defaultValue.height));
		if (s.length() > 0) { // JIC sgetValueForKey should of returned defaults
			// for a null entry
			try {
				try {
					returnValue.width = Integer.parseInt((s.substring(0, s
							.indexOf(','))));
				} catch (NumberFormatException e) {
					/* throw away */
				}

			} catch (StringIndexOutOfBoundsException e) {
				/* throw away */
			}

			try {
				try {
					returnValue.height = Integer.parseInt(s.substring(s
							.indexOf(',') + 1, s.length()));
				} catch (StringIndexOutOfBoundsException e) {
					/* throw away */
				}

			} catch (NumberFormatException e) {
				/* throw away */
			}
		}
		return returnValue;
	}

	/**
	 * 
	 * @param String
	 *            key for the value to lookup
	 * @param double defaultVale String to return if the key is not found in the
	 *        list
	 * 
	 */
	public int getValueForKey(String key, int defaultValue, String comment) {
		int returnvalue = getValueForKey(key, defaultValue);
		if (writeMode)
			setCommentForKey(key, comment);
		return returnvalue;
	}

	//
	public int getValueForKey(String key, int defaultValue) {
		int returnValue = defaultValue; // undisturbed for NumberFormatException

		try {
			int number = Integer
					.parseInt(getValueForKey(key, "" + defaultValue));
			returnValue = number;
		} catch (NumberFormatException e) { /* throw away */
		}

		return returnValue;
	}

	/**
	 * 
	 * @param key
	 *            for the value to lookup
	 * @param defaultVale
	 *            String to return if the key is not found in the list
	 * 
	 */
	public String getValueForKey(String key, String defaultValue, String comment) {
		String returnvalue = getValueForKey(key, defaultValue);
		if (writeMode)
			setCommentForKey(key, comment);
		return returnvalue;
	}

	//
	public String getValueForKey(String key, String defaultValue) {

		if (writeMode) {
			if (defaultValue.lastIndexOf(' ') > 0) // Enclose in "" if spaces
				// are contained in value
				optionList.add(new Options(key, "\"" + defaultValue + "\"")); // Strings
			// with
			// Spaces
			// require
			// quotes
			else
				optionList.add(new Options(key, defaultValue));
		} else {
			String returnValue = getValueForKey(key);

			if (returnValue.length() == 0) {
				return new String(defaultValue);
			} else {
				return new String(returnValue);
			}
		}
		return defaultValue;
	}

	/**
	 * 
	 * @param key
	 *            for the value to lookup
	 * @param defaultVale
	 *            Color to return if the key is not found in the list
	 * 
	 */
	public Color getValueForKey(String key, Color defaultValue, String comment) {
		Color returnvalue = getValueForKey(key, defaultValue);
		if (writeMode)
			setCommentForKey(key, comment);
		return returnvalue;
	}

	//
	public Color getValueForKey(String key, Color defaultValue) {
		Color returnColor = defaultValue;

		if (writeMode) {
			// //ARGB exceeds the integer conversion for hex string remove the
			// Alpha component
			// StringBuffer colorString = new
			// StringBuffer(Integer.toHexString(defaultValue.getRGB()));
			// //
			// if(colorString.length() > 6)
			// colorString.replace(0,2,"  ");
			//
			// optionList.add(new Options(key, colorString.toString()));
			optionList.add(new Options(key, toString(defaultValue)));
		} else {

			String returnValue = getValueForKey(key);

			if (returnValue.length() != 0) {
				try {
					int colorValue = Integer.parseInt("0" + returnValue, 16);
					returnColor = new Color(colorValue);
				} catch (NumberFormatException e) {
					boolean brighter = false;
					boolean darker = false;
					int p = returnValue.lastIndexOf('.');

					if (p > 0 && p < returnValue.length()) {
						String tmp = returnValue.substring(p + 1, returnValue
								.length());
						returnValue = returnValue.substring(0, p);
						if (tmp.compareToIgnoreCase("darker") == 0)
							darker = true;
						if (tmp.compareToIgnoreCase("lighter") == 0)
							brighter = true;
						if (tmp.compareToIgnoreCase("brighter") == 0)
							brighter = true;
					}

					if (returnValue.compareToIgnoreCase("black") == 0)
						returnColor = Color.black;
					if (returnValue.compareToIgnoreCase("blue") == 0)
						returnColor = Color.blue;
					if (returnValue.compareToIgnoreCase("cyan") == 0)
						returnColor = Color.cyan;
					if (returnValue.compareToIgnoreCase("darkGray") == 0)
						returnColor = Color.darkGray;
					if (returnValue.compareToIgnoreCase("gray") == 0)
						returnColor = Color.gray;
					if (returnValue.compareToIgnoreCase("green") == 0)
						returnColor = Color.green;
					if (returnValue.compareToIgnoreCase("lightGray") == 0)
						returnColor = Color.lightGray;
					if (returnValue.compareToIgnoreCase("magenta") == 0)
						returnColor = Color.magenta;
					if (returnValue.compareToIgnoreCase("orange") == 0)
						returnColor = Color.orange;
					if (returnValue.compareToIgnoreCase("pink") == 0)
						returnColor = Color.pink;
					if (returnValue.compareToIgnoreCase("red") == 0)
						returnColor = Color.red;
					if (returnValue.compareToIgnoreCase("white") == 0)
						returnColor = Color.white;
					if (returnValue.compareToIgnoreCase("yellow") == 0)
						returnColor = Color.yellow;

					if (brighter)
						returnColor = returnColor.brighter();
					if (darker)
						returnColor = returnColor.darker();
				}
			}
		}
		return returnColor;
	}

	/**
	 * 
	 * @param c
	 *            the Color value to convert to a string or name
	 * @return Color name or the hex value
	 * 
	 */
	private String toString(Color c) { // Conversion to the English color label
		// for the static Color members

		// String sep = System.getProperty("line.separator");

		StringBuffer buffer = new StringBuffer();
		// buffer.append(sep);
		// buffer.append("c = ");
		// buffer.append(c);
		// buffer.append(sep);

		if (c.equals(Color.black))
			buffer.append(" Black ");
		else if (c.equals(Color.blue))
			buffer.append(" Blue ");
		else if (c.equals(Color.cyan))
			buffer.append(" Cyan ");
		else if (c.equals(Color.darkGray))
			buffer.append(" DarkGray ");
		else if (c.equals(Color.gray))
			buffer.append(" Gray ");
		else if (c.equals(Color.green))
			buffer.append(" Green ");
		else if (c.equals(Color.lightGray))
			buffer.append(" LightGray ");
		else if (c.equals(Color.magenta))
			buffer.append(" Magenta ");
		else if (c.equals(Color.orange))
			buffer.append(" Orange ");
		else if (c.equals(Color.pink))
			buffer.append(" Pink ");
		else if (c.equals(Color.red))
			buffer.append(" Red ");
		else if (c.equals(Color.white))
			buffer.append(" White ");
		else if (c.equals(Color.yellow))
			buffer.append(" Yellow ");
		else {
			buffer.append(Integer.toHexString(c.getRGB()));
			if (buffer.length() > 6)
				buffer.replace(0, 2, "  ");
		}

		// StringBuffer colorString = new
		// StringBuffer(Integer.toHexString(defaultValue.getRGB()));
		// //
		// if(colorString.length() > 6)
		// colorString.replace(0,2,"  ");

		return buffer.toString();
	}

	/**
	 * 
	 * @param key
	 *            for the value to lookup
	 * @return value or a zero length String
	 */
	public String getValueForKey(String key) {
		String returnValue = "";
		if (writeMode) {
			// if(key.equals(""))key="//";
			optionList.add(new Options(key));
		} else {
			Options ntf = new Options(key, ""); // key's stored uppercase white
			// space removed

			int idx = optionList.indexOf(ntf);
			if (idx >= 0) {
				if (optionList.elementAt(idx) instanceof Options) {
					returnValue = new String(((Options) optionList
							.elementAt(idx)).getValue());
				}
			}
		}
		return returnValue;
	}

	//
	/*
	 * private String getCommentForKey(String key) { Options ntf = new
	 * Options(key, ""); int idx = optionList.indexOf(ntf); if (idx >= 0) {
	 * Object o = optionList.elementAt(idx); if (o instanceof Options) { return
	 * ((Options) o).comment; } } return null; }
	 */
	//
	private void setCommentForKey(String key, String comment) {
		Options ntf = new Options(key, "");
		int idx = optionList.indexOf(ntf);
		if (idx >= 0) {
			Object o = optionList.elementAt(idx);
			if (o instanceof Options) {
				((Options) o).comment = new String(comment);
			}
		}
	}
	
	/*
	 * dump entire list
	 */
	public void close() {
		if (writeMode) {
			try {

				FileOutputStream fout = new FileOutputStream(file);
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
						fout));

				Enumeration<Options> e = optionList.elements();
				Options o = null;
				while (e.hasMoreElements()) {
					o = (Options) e.nextElement();
					// System.out.println("Next element = "+o.toString());
					out.write(o.toString());
					out.newLine();
				}
				out.flush();
				fout.close();
				System.out.println(" " + file.getName()
						+ " Not found creating a default");
			} catch (IOException e) {
			}
		} // if writeMode

		optionList.removeAllElements();
	}

	//
	public String toString(){
		return optionList.toString();
	}

	/*
	 * main for testing
	 */
	public static void main(String[] args) {
		Parser test = new Parser("configTest.txt");
		System.out.println(test.getValueForKey("mydate", new Date(),
				"\t//Examp: July 2, 1987 7:20 AM"));
		System.out.println(test.getValueForKey("ABooleanValue", true));
		System.out.println(test.getValueForKey("AIntValue", 1)); // Should only
		// see the
		// first
		// value
		System.out.println(test.getValueForKey("AIntValue", 2)); // when read
		// from the
		// file
		System.out.println(test.getValueForKey("AIntValue", 3));
		System.out.println(test.getValueForKey("AIntValue", 4));
		System.out.println(test.getValueForKey("Dimension", new Dimension(10,
				20), "// Strict format nn,nn NO SPACES between numbers")); // new
		// January
		// 2009

		System.out.println(test.getValueForKey("AStringValue", "MyString",
				"			// With Comments"));
		System.out.println(test.getValueForKey("JustAKeyString"));
		System.out.println("Red "
				+ test.getValueForKey("ColorTestRed", Color.red));
		System.out.println("Blue "
				+ test.getValueForKey("ColorTestBlue", Color.blue));
		System.out.println("Green "
				+ test.getValueForKey("ColorTestGreen", Color.green));
		System.out
				.println("0xF0F0F1 "
						+ test.getValueForKey("ColorTestHex", Color
								.decode("0xF0F0F1")));

		String jatest = test.getValueForKey("Jatest", "ThisIsATest",
				"\t // Hello testing no spaces");
		String jatest1 = test.getValueForKey("JaTest1", "This Is A Test",
				"\t // Hello testing1 no end spaces");
		String jatest2 = test.getValueForKey("JaTest2", " This Is A Test",
				"\t // Hello testing1 front space");
		String jatest3 = test.getValueForKey("JaTest3", "This Is A Test ",
				"\t // Hello testing1 back space");
		String jatest4 = test.getValueForKey("JaTest4", " This Is A Test ",
				"\t // Hello testing1 both front and back space");
		String jatest5 = test.getValueForKey("JaTest5", "",
				"\t // Nothing but air");
		String jatest6 = test.getValueForKey("JaTest6", " ",
				"\t // Nothing but 1 air");
		String jatest7 = test.getValueForKey("JaTest7", "  ",
				"\t // Nothing but 2 air");
		Dimension jatest8 = test.getValueForKey("Jatest8", new Dimension(123,
				456), "// Dimension");

		System.out.println("jatest  = >" + jatest + "<");
		System.out.println("jatest1 = >" + jatest1 + "<");
		System.out.println("jatest2 = >" + jatest2 + "<");
		System.out.println("jatest3 = >" + jatest3 + "<");
		System.out.println("jatest4 = >" + jatest4 + "<");
		System.out.println("jatest5 = >" + jatest5 + "<");
		System.out.println("jatest6 = >" + jatest6 + "<");
		System.out.println("jatest7 = >" + jatest7 + "<");
		System.out.println("jatest8 = >" + jatest8 + "<");
		System.out.println(test.toString());
		test.close();

	}

	/*
	 * inner class holding key vlaue options
	 */
	private class Options {
		private String key = "", value = "", comment = "";

		//
		// private Options(String key, String value, String comment){
		// this(key,value);
		// this.comment = new String(comment.trim());
		// }
		//
		private Options(String key, String value) {
			this.key = new String(key.trim());
			this.value = new String(value.trim());
		}

		//
		private Options(String st) { // let this class parse out the key and
			// value
			String tmp = new String(st.trim()); // remove any leading spaces
			tmp = tmp.replace('=', ' '); // I want to use key = value sometimes
			tmp = tmp.replace('\t', ' '); // Tabs can and will cause problems
			value = ""; // Default if only one parameter

			int idx = tmp.indexOf(' '); // should be the first separator if any
			if (idx > 0) {
				key = new String(tmp.substring(0, idx)); // first word is the
				// key
				if (idx < tmp.length()) {
					value = new String(tmp.substring(idx, tmp.length()).trim()); // removes
					// any
					// leading
					// spaces
				}
			} else { // no separator found so
				key = new String(tmp); // key is the value
			}
			if (value.length() > 0) { // parse out only one word + removes any
				// inner trailing
				//
				if (value.charAt(0) == '"') { // Possibly enclosed in quotes
					int qdx = value.indexOf('"', 1); // find ending quote
					if (qdx > 1) {
						idx = qdx - 1; // skip the second quote
						value = value.substring(1, value.length()); // removes
						// the first
						// quote
					}
				} else {
					idx = value.indexOf(' '); // for values not enclosed with
					// quotes
				}
				if (idx > 0) {
					if (idx < value.length()) {
						comment = value.substring(idx, value.length());
					}
					value = value.substring(0, idx);
				}
			}
		}

		//
		// private String parseKey(String str){
		// String returnValue = "";
		// if(str == null) return new String();
		// //
		// if(str.length() > 0){
		// String tmp1 = new String(str.trim());
		// String tmp2 = tmp1.toString().replace('\t',' ');
		// tmp2 = tmp2.toString().replace('=',' '); // tmp2 is duplicate of tmp1
		// with special chars removed
		// int spidx=0;
		// while(tmp2.charAt(spidx) == ' ') spidx++;
		// //
		// int idx = tmp2.indexOf(spidx,' ');
		// if(idx > 0){
		// key = new String(tmp2.substring(spidx,idx));
		// if(idx < tmp1.length()){
		// returnValue = new String(tmp1.substring(idx,tmp1.length()).trim());
		// // removes any leading spaces
		// }
		// }
		// }
		// return returnValue;
		// }
		// //
		// //
		// private String parseValue(String str){
		// String returnValue = "";
		// if(str == null) return new String();
		// if(str.length() > 0){
		// String tmp1 = new String(str.trim());
		// String tmp2 = tmp1.toString().replace('\t',' ');
		// tmp2 = tmp2.toString().replace('=',' '); // tmp2 is duplicate of tmp1
		// with special chars removed
		// int spidx=0;
		// while(tmp2.charAt(spidx) == ' ') spidx++;
		// //
		// int idx = tmp2.indexOf(spidx,' ');
		// if(idx > 0){
		// value = new String(tmp2.substring(spidx,idx));
		// if(idx < tmp1.length()){
		// returnValue = new
		// String(tmp1.substring(idx+spidx,tmp1.length()).trim()); // removes
		// any leading spaces
		// }
		// }
		// }
		// return returnValue;
		// }
		// //
		// public String getComment(){
		// return new String(comment);
		// }
		// //
		public String toString() {
			// if(comment.length() > 0)
			if (key.compareTo("//") == 0)
				return new String("//\t" + value + "\t" + comment);
			return new String(key + " = " + value + "\t" + comment);
			// return new String(key + " = " + value);
		}

		//
		public String getValue() {
			return new String(value);
		}

		//
		public boolean equals(Object o) {
			return (o instanceof Options)
					&& (key.equalsIgnoreCase(((Options) o).key));
		}
	} // end of Class Options
} // end of Class Parser
