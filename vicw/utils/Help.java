package vicw.utils;

/**
 * @author Victor Wheeler
 * @(#)Help.java June 2001
 *
 * Copyright (c) 2001, 2013, 2017 Victor Wheeler vwheeler@compuserve.com All Rights Reserved.
 *
 *
 *	Creates the frame and starts the Help_Dialog display thread
 */

/**
 * 20170314
 *  Removed missed debug print statment in constructor (String, String, Font)
 *  
 * 2017 13 04
 *  Added support abstantiating with different Font
 * 	New constructor that accepts a Font.
 * 
 * 2013 11 14
 * Catch IllegalComponentStateException when Color has transparency component
 *
 * 2013 10 16 ver 1.1b
 *  moved version info out of Main to static final VERSION
 *  some code cleanup moved the padding spaces to HelpDialog 
 * 
 * * February 2011 Changed to allow for transparent colors.
 * 		Non color constructors pass the default color.cyan instead of casting
 * 		 null to the Color counterparts. (Both Help and HelpDialog)
 * 
 * 
 *  03/05/2002  removed duplicate code from similar constructors
 *
 *	Removed Thread... start() run() ...
 *
 *	Update 12/2010:
 *		Added test cases to Main
 *		 - for the new title size management
 *		 - Test for the new Tab conversions 
 *
 *
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
//import java.awt.event.WindowEvent;
//import java.awt.event.WindowListener;

public class Help extends Frame { //implements WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7365440431661165220L;
	private static final double build = 1.3; 
//	public static final String VERSION = "PopUpHelp V1.1b  2001,2002,2009,2010,2013 � Victor Wheeler myapps@vicw.us CVS";
	public static final String VERSION = "PopUpHelp V"+build+"  2001,2002,2009,2010,2013,2017 (c) Victor Wheeler myapps@vicw.us CVS";
	private String[] text = null;
	private String title = "Help";
	private Font font = null;
	private Color HelpColor = Color.CYAN;
	public HelpDialog d = null;

	/**
	 * Copyright (c) 1997, 1998, 2002 vwheeler All Rights Reserved. constructor
	 * when only one line of text is required.
	 * 
	 * @param str Sting to be displayed in this window.
	 * 
	 */
	public Help(String str) {
		this(str, (Font) null);
	}
	public Help(String str, Font f) {
		this(str, Color.CYAN, f);
	}
	public Help(String str, Color c){
		this(str, c, (Font) null);
	}

	/**
	 * Copyright (c) 1997, 1998, 2002 vwheeler All Rights Reserved. constructor
	 * when only one line of text is required with color.
	 * 
	 * @param str String to be displayed in this window
	 * @param c Color for the background of this window
	 *            java.lang.String
	 */
	public Help(String str, Color c, Font f) {
		super();
		if (str == null)
			str = new String("Unknown text");
		HelpColor = c;
		text = new String[1]; // ready string to pass to init
		text[0] = new String(str);
		font = f;
		initialize();
	}

	/**
	 * Copyright (c) 1997, 1998, 2002 vwheeler All Rights Reserved. constructor
	 * when only one line of text is required with title.
	 * 
	 * @param title String to be displayed on this window
	 * @param str to be displayed in this window
	 * 
	 */
	public Help(String title, String str) {
		this(title, str, (Font) null);
		
	}
	
	public Help(String title, String str, Font f) {
		this(title, str, Color.CYAN, f);
		// System.err.println("Yep called with "+f);
	}

	/**
	 * Copyright (c) 1997, 1998, 2002 vwheeler All Rights Reserved. constructor
	 * when only one line of text is required with color and title.
	 * 
	 * @param text
	 *            java.lang.String
	 */
	public Help(String title, String str, Color c) {
		this(title, str, c, (Font) null);
	}
	
	public Help(String title, String str, Color c, Font f) {
		super();
		this.title = title;
		HelpColor = c;
		if (str == null)
			str = new String("Unknown text");
		//int p = 0;
		//int q = str.length() - 1;
		text = new String[1]; // ready string to pass to init
		text[0] = new String(str);
		font = f;
		initialize();
	}

	/**
	 * Copyright (c) 1997, 1998, 2002 vwheeler All Rights Reserved.
	 * 
	 * @param text
	 *            java.lang.String[]
	 * 
	 *            Constructor for multiple lines of text
	 */
	public Help(String title, String[] helpText) {
		//this(title, helpText, (Color) null);
		this(title, helpText, Color.CYAN);
	}

	/**
	 * Copyright (c) 1997, 1998, 2002 vwheeler All Rights Reserved.
	 * 
	 * @param text
	 *            java.lang.String[]
	 * 
	 *            Constructor for multiple lines of text with color and title
	 */
	public Help(String title, String[] helpText, Color c) {
		this(title, helpText, c, (Font) null);
	}
	
	public Help(String title, String[] helpText, Color c, Font f) {
		super();
		this.title = title;
		text = helpText;
		HelpColor = c;
		font = f;
		initialize();
	}

	/**
	 * Copyright (c) 1997, 1998, 2002 vwheeler All Rights Reserved.
	 * 
	 * @param text
	 *            java.lang.String[]
	 * 
	 *            Constructor for multiple lines of text and title
	 */
	public Help(String[] helpText) {
		//this(helpText, (Color) null);
		this(helpText, Color.CYAN);
	}

	/**
	 * Copyright (c) 1997, 1998, 2002 vwheeler All Rights Reserved.
	 * 
	 * @param text
	 *            java.lang.String[]
	 * 
	 *            Constructor for multiple lines of text and color
	 */
	public Help(String[] helpText, Color c) {
		super();
		text = helpText;
		HelpColor = c;
		initialize();
	}

	/**
	 * Copyright (c) 1997, 1998, 2002 vwheeler All Rights Reserved.
	 */
	
	public static void main(java.lang.String[] argv) {
		SysPropTest sysInfo = new SysPropTest();
	    String[] str = new String[sysInfo.getSysInfoStrings().length + sysInfo.getJavaInfoStrings().length+5];
		int j = 0;

		str[j++] = new String("\t\t"+Help.VERSION);
		str[j++] = new String("");
		str[j++] = new String("\t------------- Local System Information -------------");
		
		for (int i = 0; i < sysInfo.getSysInfoStrings().length; i++) {
			str[j++] = new String("\t\t" + sysInfo.getSysInfoStrings()[i]);
		}

		str[j++] = new String("");
		str[j++] = new String(
				"\t----------------- Java Information -----------------");

		for (int i = 0; i < sysInfo.getJavaInfoStrings().length; i++)
			str[j++] = new String("\t\t" + sysInfo.getJavaInfoStrings()[i]);

		Help h = new Help(" " + VERSION ,
				str, new Color(0,255,255,200), Font.decode("Dialog BOLD 24"));

		new Help("All System information", sysInfo.toString());
		
		// force close the Main thread
		h.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosed(java.awt.event.WindowEvent e) {
				System.exit(0);
			};
		});

//		new Help("","");  // Nada, zip, nothing 
//		
//		new Help("123456789012345678901234567890 Ruler",
//				new String[]{"1234567890123456789012345678901234567890",
//				"0\t1\t2\t3\t4\t5\t6\t7   |"});
		
//		new Help("Test with just a really, really long title string and now making it longer","srttxt");
		
//		new Help("Test with just the title string and Color", new Color(128, 0, 255, 205)); //Color.blue.brighter());
//
//		new Help("Test with a single string", "Testing");
//		
//		new Help("Test with a string string["+str.length+"]", str);
//
//		new Help("Test with a single string and Color","Testing",Color.green.brighter());
//		
//		new Help("Test with a string string["+str.length+"] and Color", str, Color.blue.brighter());
//		
//		new Help("CRLF and Tab test",
//				"First line of text with tabs and CRLF\n\r\tThis text is on the second line starting with a single tab\n\t\t3rd line starts with 2 tabs after a single cr");

	}

	/*
	 * Copyright (c) 1997, 1998, 2002 vwheeler All Rights Reserved.
	 */
	public void initialize() {
		// GridLayout(int rows, int cols, int hgap, int vgap)		
		setLayout(new GridLayout(10, 2, 2, 2)); 
		
		//setBackground(Color.cyan);
		//setBackground(HelpColor);
		try{		// Transparency color cause exception on some systems/versions
		setBackground(HelpColor);
		} catch(java.awt.IllegalComponentStateException e){
			HelpColor= new Color(HelpColor.getRed(),HelpColor.getBlue(),HelpColor.getGreen());
			setBackground(HelpColor);
		}

		if(font == null ) font= new Font("Courier", Font.BOLD, 14); // Default

		// System.out.println("Help with Font " + font.toString());

		setFont(font);
		//setFont(new Font("Courier", Font.BOLD, 24));
		
		// Removed un needed white space 2013 10 20
		//d = new HelpDialog(this, " " + title + " ", text, HelpColor);
		d = new HelpDialog(this, title , text, HelpColor);
//		d.setFont(font);
		d.setVisible(true);
//		d.addWindowListener(this);
		
	}

//	public void windowActivated(WindowEvent parm1) {
//		// System.out.println("Hello from windowActivated()");
//	}
//
//	public void windowClosed(WindowEvent parm1) {
//		//System.out.println("Hello from windowClosed()");
//	}
//
//	public void windowClosing(WindowEvent parm1) {
//		//System.out.println("Hello from windowClosing()");
//		// stopHelp();
//	}
//
//	public void windowDeactivated(WindowEvent parm1) {
//		//System.out.println("Hello from windowDeactivated()");
//	}
//
//	public void windowDeiconified(WindowEvent parm1) {
//		//System.out.println("Hello from windowDeiconified()");
//	}
//
//	public void windowIconified(WindowEvent parm1) {
//		//System.out.println("Hello from windowIconified()");
//	}
//
//	public void windowOpened(WindowEvent parm1) {
//		//System.out.println("Hello from windowOpened()");
//	}
	//
public void dispose(){
	// System.out.println("Hello from Help.dispose()");
	if(d != null) {
//		d.removeWindowListener(this);
		d = null;
	}
	//dispose();
	super.dispose();
}
	/* (non-Javadoc)
	 * @see java.awt.Window#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean arg0) {
		d.setVisible(arg0);
		// super.setVisible(arg0);
	}
}