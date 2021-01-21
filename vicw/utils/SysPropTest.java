package vicw.utils;

/**
 * @author Victor Wheeler
 * @(#)SysPropTest.java June 2001
 * 
 *                      Copyright (c) 2001 Victor Wheeler
 *                      vwheeler@compuserve.com All Rights Reserved. Copyright
 *                      (c) 1997, 1998 2000 vwheeler@compuserve.com All Rights
 *                      Reserved.
 * 
 */
// class SysPropTest extends Comp_Edit {
public class SysPropTest {
	private String usrname; // User Account Name
	private String osarch; // OS architecture
	private String osname; // OS name
	private String osvers; // OS version
	private String jvendor; // Java vendor
	private String jversion; // Java version

	// Java enviroment stuff
	private String home; // Java installation directory
	private String class_version; // Java class format version number
	private String class_path; // Java class path
	private String lib_path; // List of paths to search when loading libraries
	private String io_tmpdir; // Default temp file path
	private String ext_dirs; // Path of extension directory or directories
	private String user_dirs; // User's current working directory

	// String uname;
	/**
	 * Copyright (c) 1997, 1998 vwheeler All Rights Reserved.
	 */
	public SysPropTest() {

		// xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
		try {
			home = System.getProperty("java.home");
			// System.out.println("Java installation directory: " + home);

			class_version = System.getProperty("java.class.version");
			// System.out
			// .println("Java class format version number: " + class_version);

			class_path = System.getProperty("java.class.path");
			// System.out.println("Java class path: " + class_path);

			lib_path = System.getProperty("java.library.path");
			// System.out.println("List of paths to search when loading
			// libraries: "
			// + lib_path);

			io_tmpdir = System.getProperty("java.io.tmpdir");
			// System.out.println("Default temp file path: " + io_tmpdir);

			ext_dirs = System.getProperty("java.exe.dirs");
			// System.out.println("Path of extension directory or directories: "
			// + ext_dirs);

			user_dirs = System.getProperty("user.dir");
			// System.out.println("User's current working directory: " +
			// user_dirs);

			usrname = System.getProperty("user.name");
			// System.out.println("User's account name: " + usrname);

			osarch = System.getProperty("os.arch");
			// System.out.println("OS architecture: " + osarch);

			osname = System.getProperty("os.name");
			// System.out.println("OS name: " + osname);

			osvers = System.getProperty("os.version");
			// System.out.println("OS version: " + osvers);

			jvendor = System.getProperty("java.vendor");
			// System.out.println("Java vendor: " + jvendor);

			jversion = System.getProperty("java.version");
			// System.out.println("Java version: " + jversion);
		} catch (java.security.AccessControlException e) {
			;
		}

		// xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

		// uname = System.getProperty("user.name");
		// System.out.println("User.Name = "+uname);

		// osarch = System.getProperty("os.arch");
		// System.out.println("OS architecture: " + osarch);

		// osname = System.getProperty("os.name");
		// System.out.println("OS name: " + osname);

		// osvers = System.getProperty("os.version");
		// System.out.println("OS version: " + osvers);

		// jvendor = System.getProperty("java.vendor");
		// System.out.println("Java vendor: " + jvendor);

		// jversion = System.getProperty("java.version");
		// System.out.println("Java version: " + jversion);

	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		try{
		java.util.Properties p = System.getProperties();
		java.util.Enumeration<?> k = p.keys();
		java.util.Enumeration<?> e = p.elements();
		int i = 0;

		while (e.hasMoreElements() && k.hasMoreElements()) {
			s.append(i++ + " " + k.nextElement().toString() + ":\t"
					+ e.nextElement().toString() + "\n");
		}
		} catch(java.security.AccessControlException e){
			s.append("\tJava Security Access Exception\n\tSystem Properties access not allowed");
			}
		return s.toString();
	}
	
	/**
	 * Copyright (c) 1997, 1998 vwheeler All Rights Reserved.
	 * 
	 * @return java.lang.String[]
	 */
	public String[] getSysInfoStrings() {
		String[] str = {
				"User's account name: " + usrname,
				"OS name:             " + osname,
				"OS version:          " + osvers,
				"OS architecture:     " + osarch };
		return str;
	}

	public String[] getJavaInfoStrings() {
		String[] str = {
				"Vendor:         " + jvendor,
				"Version:        " + jversion,
				"Class frmt ver: " + class_version,
				"Working dir:    " + user_dirs, "Install dir:    " + home,
				"Class path:     " + class_path,
				// "List of paths to search when loading libraries ",
				"Libraries Path: " + lib_path,
				// "Path of extension directory or directories     "
				"Extension path: " + ext_dirs,
				"Temp file path: " + io_tmpdir };
		return str;
	}
	
	
	/*
	 * 
	 * Compares the Java runtime version with major,minor int or minVersion
	 * String returns True if version is the same or greater should work up to
	 * version 9.9 unless Oracle breaks Sun's convention again
	 */

	public boolean checkJavaVersion(String minVersion) {	// like "7.6"
		if(minVersion.length() < 3){
			System.out.println("Version check " +minVersion+ " "+ minVersion.length() +" is string too short");
			return false; 
		}
		if(minVersion.charAt(1) != '.') return false;	// need to adjust sanity check in the future
		int major = Character.getNumericValue(minVersion.charAt(0));
		int minor = Character.getNumericValue(minVersion.charAt(2));
// System.out.println("String = "+major+" "+minor);
		return checkJavaVersion(major, minor);
	}

	public boolean checkJavaVersion(double majordotminor) {
		int major = (int) majordotminor;
		int minor = (int) ((majordotminor - major) * 10);
// System.out.println("double = "+major+" "+minor);
		return checkJavaVersion(major, minor);
	}

	public boolean checkJavaVersion(int major, int minor) {
// System.out.println("int "+major+" "+minor);
		String jversion = getJavaVersion(); // Should look like "1.8.0_05"
		int jmajor = Character.getNumericValue(jversion.charAt(2));
		int jminor = Character.getNumericValue(jversion.charAt(4));

		boolean retval = false; // Force positive check for true
		if (jmajor > (major))
			retval = true; // larger than the base version is good were	done
		else if (jmajor == major) // Could be need to check the minor number
			if (jminor >= minor) // Passed
				retval = true;
		return retval;
	}
			
	/*
	 * 	Returns the The Java Runtime version this program is running in as a String
	 *  used by checkJavaVersion
	 *  
	 */
			public String getJavaVersion(){
				return System.getProperty("java.version");
			}

}