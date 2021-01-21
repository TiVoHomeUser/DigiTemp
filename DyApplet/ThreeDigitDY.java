
/*
The JDK 8 has 2 versions of netscape.javascript.JSObject.
One in the jfxrt.jar (the wrong one) and the second one in the plugin.jar (the correct one).

for NetBeans with java 8 to get rid of the pesky netscape.javascript.JSObject error
manually add / change

endorsed.classpath=\
    ${java.home}/lib/javaws.jar:\
    ${java.home}/lib/plugin.jar

to nbproject/project.properties file
*/
//package dyapplet;


import netscape.javascript.JSObject;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
// import java.awt.PopupMenu;
// import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
//import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
//import javax.swing.text.DateFormatter;

import vicw.utils.ColorSelection;
import vicw.utils.Help;
import vicw.utils.SelectColorInterface;
import vicw.utils.SelectTimeZoneInterface;
import vicw.utils.SysPropTest;
import vicw.utils.TimeZoneSelection;
import vicw.utils.XyChart;
import vicw.utils.led.Dy;
import vicw.utils.led.SevenSegLed;
import vicw.utils.SelectFont;
import vicw.utils.SelectFontInterface;
/**
 * 
<<<<<<< ThreeDigitDY.java
 * @author Victor Wheeler Copyright © 2010, 2013, 2014, 2015, 2016 2017 all rights reserved.
=======
 * @author Victor Wheeler Copyright © 2010, 2013, 2014, 2015, 2016, 2017 all rights reserved.
>>>>>>> 1.2.2.19
 * Import for CVS Nov 28 2013
 *
 */

/**
 * 20170319 Start of dyAppletWeb moved form eclipse to NetBeans
 * 
 * 20170314
 *  Some more clean-up changed default font size from 15 to 20
 *  removed call to instructions() through instructionsMousePressed(e)
 *  
 * 20170306
 *  User settings using GridBag to allow panel to be resized.
 *  
 * 20170305
 * 	Save Font now updating both directions
 * 
 * 20170304
 * 	Passing font for calls to Help(...)
 * 
 * 20170227
 *  More work on font also cleanup Selection menu removing forced invalid
 *  by including invalid=true for the 3 Color selections.
 *  
 *  Modified SelectColor attempting to allow cancle after select reverting to original color
 *  Not working with this update. 
 *  
 * 20170224
 * Global property Font font 
 * Font saved in cookie and parse from HTML page.
 * so far changing font is only seen in Last Update and the Info '?'
 * another Font popUpFont over-rides in pop-ups 
 * 
 * Unicode degree symbol defined for use globally as a char deg = '\u00B0'
 * 
 * 20170123
 * Moved working code for pop-up window out of mouseEntered(event) to a function histPuw(Point)
 * with a null check on puw making the event cleaner and possibly less overhead.
 * 
 * 20161228
 * XyChart update now accepts the nameing parameters
 * Passing label strings to XyChart including Temperature base ('C' of 'F')
 * Included TimeZone in XYChart Time lable
 * 
 * 20161227
 * Added hot labels to Instructions
 * 
 * 20160905
 * Validate that data exists in chart prevents null pointer exceptions when data does not exist
 * 
 * 20160614
 * TimeZone data list accepts mouse and key inputs
 * Removing debug, unused and tempoary code  from Vicw.utils.SelectTimeZone.
 * 
 * 20160613
 * SelectTimeZone working both tzdf and digi_Temp_TZ are now both updated
 * @todo Added COLOR to the panels to assist in layout remember to remove and select somthing more plesent.
 * On second thought maybe it is OK this way 
 * 
 * 20160610
 * Converted Hi / Lo from int to double
 * Added TimeZone to settings.
 * 
 * 20160609
 * SelectTimeZone using SelectTimeZoneInterface
 * a way for the user to select the data files Time Zone in a the new Java format
 * 
 * 20160601
 * Update to userSettingsInfo now displays if data has expired
 * 
 * 20160531
 *  Info now displays corrected time zone (EDT EST) for Daylight Savings time
 *  LUDT panel Time Zone corrected also
 *  
 * 20160527
 * fixed mising block in for version 20120829 in CookieManager getOneSingleBigCookie
 * 
 * NOTE ****** COOKIE Still broken ver 20131205 to 20160527
 * 
 * 20160102
 * 	New CVS Branch created
 * 20151228
 *  - Removed XYChart before versioning and for refresh of CVS build branch.
 * 20141226
 *  - Fixed file entry Selection Menu getFile removed ???? replaced with "file"
 *  - Calendar.month starts with 0 added 1 to month for xyChart data.
 *     Later changed using DateFormater to get the 3 char month string instead.
 *  - Changed date data for XYChart to include one reference to the year and month unless changed
 * 
 * 20141224
 *  - xyChart
 *  - final now used for Axis and LineChart like used in example not sure why final is needed
 *  - Changed to strings for the time (x axis) instead of doubles giving a clearer representation
 * 
 * 20141222
 *  - clean up debug output
 *  - moved mouse event pressed to clicked
 *  	pressed was causing duplicate events with button2 and 3
 *  
 * 
 * 20141207
 * 	- Year is no longer a fixed value, instead uses last modified time from the data file or local time
 *  as default.
 *  - Function isValid checks the last data time is within 15 minutes of the current time useful
 *  to gray out the display.
 *  - Display the data's Time Zone in the Last Update Time textfield
 *  
 * 
 * 20141201
 * 	Added parameter digi_Temp_TZ for the time zone of the data in the file
 *    this String replaces the the byte tz.
 *    String is converted to a TimeZone object in init()
 *      
 *  GetAppletinfo() now includes the Java Vendor and version
 * 
 * 
 * 
 * 20140424
 *  Fix override incompatible JSObject included with Java 8's JavaFx
 *  
 * 20140301
 * New properties
 *  - invalid 
 *  - ledInvalidColor and ledInvalidColor_str
 *  - tz	Local time offset used where temperature data was collected 0 = UST
 *   
 * Invalidates the display by changing the Led color to gray if one or more updates were not received.
 * May save the last update time and local / server time in cookie to test when the last update was to compare
 * with current time
 * Or use a TZ property and convert all times to UST.
 * 
 * Get the last 3 dates from file convert to UST
 * compute the update intervals from file or get interval from (new) html property or cookie
 * Compare the last date from cookie
 *  or local time converted to UST
 * 
 * 
 * 20131217 
 *  removed redundant function m_getParameter(String)
 *  removed unused int i in for loop replaced with existing mark if function separate(String s, char separator)
 *  removed unused Label Lh0 in inner Class Instructions
 *  
 * @ version 1.03b
 *  20131205
 *  Clean up selection menu Apply now does not close the menu
 *  Color selection is disabled when auto is true.
 *  
 * 
 *  20131204
 *  Added new parameter auto_color
 *  	over-rides the 3 user settings for colors and sets both displays
 *   red,blue or green depending on the temperature trend. instead of just the
 *   'C'/'f' display hysteresis is hard coded to .5 deg F may need to be adjusted
 *  
 * @version 1.03
 *  20131111
 *  Rework mousePressed/mouseCLicked Decided to use only mousedPressed
 *  Fixed the double menu caused by two mouse events with click on components
 *  Added '?' button for Instructions
 *  
 *  20131109
 *  Added and commented out fly-over information for Instruction class
 *  Added test in start() for initialized  Dythread before calling member functions  
 *  
 *  20131106
 *  Instruction class back to using Labels with some use of color
 *  
 *  20131031
 *  Initial instruction displayed by inner class on startup
 *  
 * @version 1.02
 *  20131028
 *  SelectionMenu dispose() now sets SelMenu null on exit
 *  
 *  MouseClicked 
 *  Replaced SelMenu.setAlwaysOnTop(true) with SelMenu.setVisible(true)
 *  
 *  MousePressed
 *  Removed additional the info pop-ups when the Ctrl key was pressed
 *  TODO MouseClicked and MousedPressed really are both really needed?
 *  
 *  20131016
 *  ctrl + alt + click Java / system info pop-up
 *  Requires ver 1.1b Help in vic.utils
 *   
 *  20130903
 *  Finished new cookie handler and published
 * 
 *  20120902
 *  Fixed oneBigCookie removing combined value key combo's
 *  
 *  Note both url (files) cookies are overridden in parser
 *  Fixed 20130903
 *  
 *  TODO acknowledge button for Alert
 *  	 enable user to select sound file
 *  
 *  08282013
 *  added alert button, cookie, and HTML parameter
 *  
 *  08272013
 *   sound a wav file for Alert
 *  
 * @version 1.01a 20110411
 *  Fix for error reading the file with only 2 readings
 * 
 * @version 1.01 20110201
 * 	Fly over information for Hi/Low 
 * 
 * @version 1.0, 20101230 
 * 
 * 01/08 toCelsius(double) rounds with 2 decimal places instead of raw 3 places.
 *  
 *  RC-2 12/20/2010
 *  
 *  Move configuration getters and setters (parseHTML and cookies) to separate
 *  Class
 * 
 *  Done: 12/20/2010
 *   Log High/low for the daily list 
 *   
 *  Done: 12/16/2010 Change Under temperature error message background color from 
 *  	 Red to Blue.brighter()
 *   
 * Known issues: Beta 1.3.20101215 (RC-1)
 * 
 * - 12/16
 * 	Done: 12/19/2010
 * 	 OVT and UT warnings disappear with page re-fresh
 *   Possible fix set session cookie destroyed with Dismiss.
 * 
 *  -Fixed 12/16
 *    Cannot bring up SelectionMenu after OVT or UT event without a restart.
 *     Using setAlWaysOnTop() was a bad Idea changed to use toFront()
 *   
 *  
 *Known issues: Beta 1.2.20101203
 *
 * - Fixed 12/12/2010
 * 	  Help not displaying CR's properly on some systems
 * 	    Change to vicw.utils.HelpDialog now separates lines with CR's 
 * 		 into new text fields.
 * 
 * - Fixed 12/14/2010
 * 	  Too many cookies need to limit 10 or less currently there are 12
 *     Status: 12/03
 *     	 Combined the 3 colors and removed current Temperature and
 *     Info Total Cookies is now down to 8.
 *     12/13 down to a Single cookie
 *     
 *     Status: 12/07  Removed in 2013
 *      Combined the 3 booleans Celsius, "enhancedLED" and "no_bg" into one 
 *      single string name=Booleans value="True,False,True" 
 *      Total is down to 6.
 * 
 * 
 *
 *Known issues: Beta 1.0.20101109
 *
 *	Copyright symbol broken "©" changed to (c) in copyright
 *
 * - Fixed 12.07.2010
 * 	  The hundredth digit on the display is truncated not rounded.
 *
 * - Fixed: 12/07/2010
 *	  The Display flashes when the page is moved (Double buffering should
 *	   fix this). I added double buffering on the graphics did not notice any
 *	   difference
 *
 * - Fixed: 12/05/2010
 *	  Enhanced not working properly also had a side effect on F/C where
 *		the color for the off led's was computed during instantiation 
 *      and refused to change. this required an Update to vicw.utils.led
 *		Both bugs were a dumb mistake I had several methods over-ridden with 
 *		Seven Segment and DY Display which iterated through each segment when
 *		these method was called by the parent constructor an Null pointer trap
 *		was thrown.
 *
 *
 * - Fixed Beta1.0.20101109 (Nov 9 2010) VW
 * 	  Nothing on the page gets auto refreshed, to see any updates use refresh
 *     on the browser.
 *     Fix by wrapping URL in URLConnection  and turning Cache off.
 *   
 * - Fixed 0.20101110 There is a small amount of white space between the DY and 'F'/'C' display,
 * 		by subtracting 1 from the DY width when computing the location for F/C.
 *
 *
 * - 20120607 Added HTML parameter bgInfoColor some OS (Windows 7) can not display transparent colors
 * 		TODO Add to options and cookies
 *      
 *
 *
 *
 *  @ToDo to become RC-1
 *   Input  DONE:
 *    - size from HTML frame
 *    - degrees
 *    - Start in F or C
 *    - Led Color
 *    - Background and LedOff Color (white on white looks nice)
 *    - Parse Title
 *    - upper and lower Limit ie Over Temp
 *    - Change display units F/C
 *    
 * - Done: Version 20101110 Date/Time of last update in title for display
 *
 * - Done: Version 20101109 Automatic page refresh
 *
 * - Done: Version 20101203 High and Low temperature alarm. possibly indicated by making the Displays flash?
 *
 * - Done: Cookies allowing user settings to be saved.
 *
 * - Done: Version 201011?? add a menu-bar and or buttons to change:
 *    " - Color selections
 *    " - Type 'C' or 'F'
 * - Done: About
 *
 * - Done: Version 20101129 Change the color of the F/C display Blue trending cooler, Green stable, RED trending warmer. Or possibly add an arrow showing the trend of the last 3 readings. 
 */

/**
 * @author Victor Wheeler Copyright © 2010, 2013 all rights reserved.
 * 
 */
public class ThreeDigitDY 
extends Applet
implements MouseListener, Runnable {
// public static double versionCVS = 20160422;	// Last update to the CVS repository date yyyymmdd
//public static void main(java.lang.String[] argv){
//	ThreeDigitDY applet = new ThreeDigitDY();
//	do
//		try {
//			Thread.sleep((long) 5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	while (applet.running);
//	applet = null;
//
//	return;
//}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2892166462889381713L;
	private static final String cpyrightstr = " © Copyright 2010, 2011, 2012, 2014, 2016, 2017 Victor Wheeler myapps@vicw.us All rights reserved.\n";	
	private static final String build_str = "20170314"; // do not use non numeric values
	//private static final String build_str = "20151228"; // do not use non numeric values
	// private double build = Double.parseDouble(build_str);
	private static final String VERSION = "1.2.2.26 Build CVS XyChart Branch 2 OSX "+ build_str;
	boolean oldcookie = true;	// use to keep from messing up settings with changes

	private Dy DyT = null;
	private SevenSegLed FC = null;

        //@TODO
        private boolean webstart = false;    // Bypass html javascript processing

	// Auto color over-rides the user settings for the 3 set-able colors 
	// sets the background to white led color changes according to the current
	// temperature trend if the last 3 measurements have changed by 1 deg F
	// Red =  rising
	// Blue = cooling
	// Green = stable
	private boolean auto_color = true;
	private static final String auto_color_str = "Auto_Color";
	
	// ledOnColor = parseHTML("Led_Color", ledOnColor);
	private Color ledOnColor = Color.RED;
	public static final String ledOnColor_str = "Led_Color";

	// ledInvalidColor = parseHTML("Led_Invslid_Color", ledInvalidColor);
	private Color ledInvalidColor = Color.GRAY;
	public static final String ledInvalidColor_str = "Invalid_Color";
	//private boolean invalid_data = true;	// Make data invalid until new data is loaded.

	// @deprecated replaced with digi_Temp_TZ keeping around for cookie
	private byte tz = -1;					// Time Zone difference in hours from GMT 0..23
											// negative value = uninitialized
	public static final String tz_str = "Time_Zone";

	// bgInfoColor = parseHTML("BG_Info_Color", bgInfoColor);
	private Color bgInfoColor = Color.CYAN;
	public static final String bgInfoColor_str = "BG_Info_Color";

	// ledOffColor = parseHTML("Led_Off_Color", ledOffColor);
	private Color ledOffColor = ledOnColor.darker();
	public static final String ledOffColor_str = "Led_Off_Color";

	// FCOnColor = parseHTML("FCcolor", FCOnColor);
	private Color fcOnColor = ledOnColor;
	// public static final String fcOnColor_str = "Fc_Color";

	private Color fcLedOffColor = ledOffColor;
	// public static final String fcOffColor_str = "Fc_Led_Off_Color";

	// celsius = parseHTML("Celsius", celsius);
	private boolean celsius = false;
	public static final String celsius_str = "Celsius";

	// bgColor = parseHTML("Back_Ground_Color", bgColor);
	private Color bgColor = Color.WHITE;
	public static final String bgColor_str = "Back_Ground_Color";

	private final int  numOfDigits = 3;

	// private boolean verbose = false;
	// public static final String verbose_str = "Info";

	// sensor = parseHTML("sensor", sensor);
	private byte sensor = 0;
	public static final String sensor_str = "Sensor"; // sensor number 0 or 1

	// refresh_interval = parseHTML("refresh_interval", refresh_interval);
	// 120 (2 minutes) seconds, 1000 = 1 Second
	private long refresh_interval = 120000; // 2 Minutes 1000 = 1 second
	public static final String refresh_interval_str = "Refresh_Interval";
	
	// How long before the data is considered to be out of date
	private int outofdate = 15;	// Minutes before the data is considered to be out of date
	public static final String outofdate_str = "Data_Expires";
	
	
	// Display the Instructions text at start up
	private String info_str =	"Startup Help";
	private boolean info = true;	// allows turning annoying pop-up

	private String digiTempFile = "http://b3c-lc.com/b3c-lc.com/DigiTemp_files/DigiTemp.txt";
	//private String digiTempFile = "/Users/imac/Downloads/DigiTemp12-24-25.txt";
	public static final String digiTempFile_str = "Data_File";

	// The time zone that the data was collected in. Used to gray out invalid data
	// when the auto color is enabled.
	private String digi_Temp_TZ = "US/Alaska";	//"AST", "ADT", "America/Anchorage", "US/Alaska" ...
	//private String digi_Temp_TZ = "America/Detroit";		// Date String
	public static final String digi_Temp_TZ_str = "Data_TZ";
	private TimeZone tzdf=null;			// Time Zone of data in file

	// Default font  
	public static final String font_str = "System_Font";
	public static final String font_style_str = "font_style";
	public static final String font_size_str = "font_size";
	//private Font font = new Font("ZapfDingbats", Font.BOLD, 14);
	//private Font font = new Font(Font.MONOSPACED, Font.BOLD, 14);
	private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	//private Font font = new Font(Font.SERIF, Font.BOLD, 14);
	//private Font font = new Font(Font.DIALOG, Font.BOLD, 14);
	//private Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 14);

		

	
	private String alert_Audio_File = "http://b3c-lc.com/b3c-lc.com/DigiTemp_files/WarningSiren.wav";
	public static final String alert_Audio_File_str = "Alert_Audio_File";
	
	private boolean alert = false;
	public static final String alert_str = "Alert_Audio";
	
	// // current_Temp = getParameter("Current_Temp");
	private String current_Temp = "Err";
	// public static final String current_Temp_str = "Current_Temp";

	// no_bg = parseHTML("HideBackGround", no_bg);
	private boolean no_bg = true;
	public static final String no_bg_str = "HideBackGround";

	// enhancedLED = parseHTML("Enhanced", enhancedLED);
	private boolean enhancedLED = false;
	public static final String enhancedLED_str = "Enhanced";

	private double[] alarmThreshold = { 75, 60 };
	private static final String alarmThreshold_str = "Over_Under_Limit";

	private final String cookieNameStr = "DigitTempSettings";

	// Used in getCurrentTemp() to prevent multiple pop-up window for each trap
	private Help MURLE = null; // Help for MalformedURLException
	private Help IOE = null; // Help for IOException
	private Help ACE = null; // Help for AccessControlException

	// Used in reFreshDisplay()
	// private Help alert_over = null; // message about OVT this handle
	// private Help alert_under = null; // allows only one pop-up at a time
	private TemperatureAlert alert_over = null; // message about OVT this handle
	private TemperatureAlert alert_under = null; // allows only one pop-up at a
													// time
	private double hysteresis = .5;		//deg F used to determine the trends
	// Note: the value is hard coded in reFreshDisplay()

	// Used in mouseclicked for info screens
	private Help tempLogHelp = null;
	private Help infoHelp = null;
	private Help configHelp = null;
//	private String crlf = "/n";
	private static boolean firstrun = true; // stop instructions from re-appearing with refresh

//  Constructor not really needed currently	
//	public ThreeDigitDY(){
//		// System.out.println("Hello from "+this.getName()+" ThreeDigitDY()");
//	}

        public void ThreeDigitDY(){
       //     super();
        }
        
        public static void main(String[] args){
            ThreeDigitDY tddy = new ThreeDigitDY();
            tddy.webstart = true;      // Running as an Application
            // tddy.info();
            tddy.init();
        }
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.applet.Applet#getAppletInfo()
	 */

	public String getAppletInfo() {
		//System.out.println("Hello from "+this.getName()+" getAppletInfo()");

		String javaVersion =
				System.getProperty("java.vendor") +" "+ System.getProperty("java.version");
		
		return " ThreeDigitDyDisplay\n" 
				+ cpyrightstr
				+ " Version " + VERSION + "\n\n Java Version:\t" + javaVersion;
	}

	/*
	 *				Collect all temperatures with time stamps for this sensor
	 *	 to collect for the display chart
	 * 
	 */
	private class Chrtdata {
		public double temp = 0.0;			// temperature
		public Date t = null;				// and it's time
//		private DateFormat ft = null;
		
		Chrtdata(double temperature, String timedate){
//		     System.out.println("Date string = "+timedate+" <<< ");
//		     SimpleDateFormat ft = new SimpleDateFormat("MMM dd HH:mm:ss yyyy"); 
		     SimpleDateFormat ft = new SimpleDateFormat("MMM dd HH:mm:ss yyyy z"); 
//		     ft = new SimpleDateFormat("MMM dd HH:mm:ss yyyy"); 
		     
		      try { 
		         // t = ft.parse(timedate + " 2014 "); //("Apr 30 18:40:02");
//		   System.out.println("getID= "+tzc.getID()); 
//		   System.out.println("getDisplayName= "+tzc.getDisplayName()); 
//		   System.out.println("getID= "+tzc.getRawOffset()); 
		   
//		          t = ft.parse(timedate + " 2014 "+ tzc.getID()); //("Apr 30 18:40:02");
//		          System.out.println("t = "+ t); 
//		working          t = ft.parse(timedate + " 2014 "+ tzc.getDisplayName()); //("Apr 30 18:40:02");
		          t = ft.parse(timedate + " " + tzdf.getDisplayName()); //("Apr 30 18:40:02");
//		          System.out.println("t = "+ t); 
//		          t = ft.parse(timedate + " 2014 "+ tzc.getRawOffset()); //("Apr 30 18:40:02");
//		          System.out.println("t = "+ t); 
//		          t = ft.parse(timedate + " 2014 "); //("Apr 30 18:40:02");
//		          System.out.println("t = "+ t); 
		      } catch (ParseException e) { 
		          System.out.println("Unparseable using " + ft); 
		      }

			temp = temperature;
//			time = new String(timedate); // copy the string original may change
		}
		
		public String toString(){
			return new String(temp + "\t" + t + "\n");
		}
		
//		public String getTime(){
//			return ft.toString();
//		}
	}
	
	 private Vector<Chrtdata> cdv = new Vector<Chrtdata>();

	/**
	 * 
	 * Read the entire data file or URL
	 * 
	 * Requires access and
	 * 	- digiTempFile string initialized with the data file path and name.
	 *  - sensor is initialized with sensor number
	 * 
	 * @return The last 6 lines of the data file or "Err" on failure
	 * gets the filename or URL string from digiTempFile 
	 * 
	 * Effects 
	 *  - double[] hiLow updated
	 *  - Vector Chrtdata cdv loaded
	 *
	 *  
	 */
	private String[] getCurrentTemp() {
		// Security.setProperty("networkaddress.cache.ttl", "0");
		// Security.setProperty("networkaddress.cache.negative.ttl", "0");

		// The trap reading the file on the server was self induced. Last night
		// when I switched from using the File class to the URL class I got
		// lazy, I had the file name and path already loaded in the File object
		// and I decided to use it for creating the URL object after all a
		// String is a String, right? For some reason calling the File function
		// to return the String in the URL constructor is what was causing
		// the Security Trap.
		
		/*
		 * 
		 * URL url; URLConnection urlConn; DataInputStream dis;
		 * 
		 * url = new
		 * URL("http://webserver.our-intranet.com/ToDoList/ToDoList.txt");
		 * 
		 * // Note: a more portable URL: //url = new
		 * URL(getCodeBase().toString() + "/ToDoList/ToDoList.txt");
		 * 
		 * urlConn = url.openConnection(); urlConn.setDoInput(true);
		 * urlConn.setUseCaches(false);
		 * 
		 * dis = new DataInputStream(urlConn.getInputStream()); String s;
		 * 
		 * ========================================================
		 * 
		 * Depreciated. This method does not properly convert bytes to
		 * characters. As of JDK 1.1, the preferred way to read lines of text is
		 * via the BufferedReader.readLine() method. Programs that use the
		 * DataInputStream class to read lines can be converted to use the
		 * BufferedReader class by replacing code of the form: DataInputStream d
		 * = new DataInputStream(in);
		 * 
		 * with: BufferedReader d = new BufferedReader(new
		 * InputStreamReader(in));
		 * 
		 * See the general contract of the readLine method of DataInput. Bytes
		 * for this operation are read from the contained input stream.
		 * 
		 * Specified by: readLine in interface DataInput Returns: the next line
		 * of text from this input stream. Throws: IOException - if an I/O error
		 * occurs. See Also: BufferedReader.readLine(), FilterInputStream.in
		 * 
		 * 
		 * ========================================================
		 */
		
		String[] datastr = { "Err", "Err", "Err", "Err", "Err", "Err" };
		try {
			URL url;
			URLConnection urlConn;

			// DataInputStream dis;
			BufferedReader dis;
			if (digiTempFile.contains("://"))
				url = new URL(digiTempFile);
			else
				url = new URL("file:" + digiTempFile);
//				url = new URL(getCodeBase().toString() + digiTempFile);
				// url = new URL(getCodeBase().toString() + "DigiTemp.txt");
				//ToDo check that the file is read locally before deleting commented line above
//System.out.println("Codebase = " + getCodeBase().toString());
//System.out.println("URL = "+ url.toString());			
			urlConn = url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setUseCaches(false);
			
			// Year was not included in the text file
			// I'll get the year from the date the file was last modified.
			Date fd; // File Date needed to get year from
			try {
				fd = new Date(urlConn.getLastModified()); // date of connection
			} catch(Exception e){	// Don't care it failed probably missing connection or file
				fd = new Date();	// Use local time as a fail safe
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(fd);
			String fileYear = Integer.toString(cal.get(Calendar.YEAR));	// used to for the data collector

			// dis = new DataInputStream(urlConn.getInputStream());
			dis = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));

			// read all lines Keep the last 6
			String str;
			// while ((str = dis.readLine()) != null) {
			// // Order Data Target Array
			// // Read Order Location
			// datastr[4] = datastr[5]; // 1 Sensor(0) 3 4
			// datastr[5] = datastr[2]; // 2 Sensor(1) 3 5
			// datastr[2] = datastr[3]; // 3 Sensor(0) 2 2
			// datastr[3] = datastr[0]; // 4 Sensor(1) 2 3
			// datastr[0] = datastr[1]; // 5 Sensor(0) 1 0
			// datastr[1] = str; // 6 Sensor(1) 1 1
			// }
			
			cdv.clear(); // cdv used for xychart to graph all data
			
			double td = 0; // Temporary Double for conversions
			hiLow[0] = -99.99;
			hiLow[1] = 99.99;
			hiLow_s[0] = "------";
			hiLow_s[1] = "------";
			
			while ((str = dis.readLine()) != null) {
				datastr[5] = datastr[4]; // 1 Sensor(0) 3 4
				datastr[4] = datastr[3]; // 2 Sensor(1) 3 5
				datastr[3] = datastr[2]; // 3 Sensor(0) 2 2
				datastr[2] = datastr[1]; // 4 Sensor(1) 2 3
				datastr[1] = datastr[0]; // 5 Sensor(0) 1 0
				datastr[0] = str; // 6 Sensor(1) 1 1
				if (str.contains("Sensor " + sensor)) {
					// System.out.println("Data For Sensor 0");
					// System.out.println("["+str+"]");
					try {
						if (celsius)
							td = Double
									.valueOf(str.substring(
											str.indexOf("C: ") + 3,
											str.indexOf(" F:")));
						else
							td = Double.valueOf(str.substring(str
									.indexOf("F: ") + 3));

						if (hiLow[0] < td) {
							hiLow[0] = td;
							hiLow_s[0] = str
									.substring(0, str.indexOf("Sensor"));
						}
						if (hiLow[1] > td) {
							hiLow[1] = td;
							hiLow_s[1] = str
									.substring(0, str.indexOf("Sensor"));
						}
try{ 		// Collect all data to graph and for date computations
	cdv.add(new Chrtdata(td, str.substring(0, str.indexOf("Sensor ") ) + fileYear ));
	}
catch(Exception e){
	System.err.println("\t*** Exception getCurrentTemp() loading cdv with Chartdata ***\n"+e);
}
					} catch (NumberFormatException e) {
						// do nothing
					}

				}	// sensor
			}	// while read
			/*
			 * remove the comment on the next 3 lines for debug
			 */
			// for(int i=0; i < datastr.length; i++)
			// datastr[i]= "(" + i + ") "+ datastr[i];
			// new Help("ARRAY ORDER",datastr);

			// if (verbose) {
			// new Help("("+ sensor + ") The Last 6 lines ", datastr);
			// }

			dis.close();
			urlConn = null;
			url = null;
		} catch (MalformedURLException e1) {
			if (MURLE == null || MURLE.d == null) {
				MURLE = new Help("MalformedURLException " + sensor,
						e1.toString(), Color.RED);
			}
		} catch (IOException e) {
			if (IOE == null || IOE.d == null) {
				IOE = new Help("URL IOException for Sensor(" + sensor + ") ",
						e.toString(), Color.RED);
			}
		} catch (java.security.AccessControlException e) {
			if (ACE == null || ACE.d == null) {
				ACE = new Help("FilePermission " + sensor, e.toString(),
						Color.RED);
			}
		}
		return datastr;
	}

	/**
//	 * 
//	 * @param name
//	 *            Parameter tag to look for
//	 * 
//	 * @return Value associated with Parameter tag or Null if tag is not found
//	 *         in Cookie or HTML
//	 * 
//	 */
//	private String m_getParameter(String name) {
//
//		String retValue = ckmgr.getCookie(name);	// Cookie first
//		
//		if (retValue == null) {						// Then look in HTML file
//			retValue = getParameter(name);
//			// new
//			// Help("Getting value from system properties",name+'\n'+retValue);
//		}
//		return retValue;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.applet.Applet#getParameter(java.lang.String)
	 */
	//TODO why did I need the Over-ride and the indirect access through m_getParameter(String) ?
	//At O v e r r i d e
        @Override
	public String getParameter(String name) {
		//return super.getParameter(name);
		return applet.getParameter(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.applet.Applet#getParameterInfo()
	 */
//TOOD restore getParameteInfo 
//	public String[][] getParameterInfo() {
//		return new String[][]  userSettingsInfo();
//	}

	private String lastLineofText = "";
	private double[] temperatureLog; // Used to compute the trend

	private double[] hiLow = { -99.99, +99.99 }; // Track High and low values
	private String[] hiLow_s = { "----", "----" };

	/**
	 * 
	 * @param datastr
	 *            The last 6 lines from the DigiTemp file
	 * 
	 * @param sensor
	 *            currently only 2 sensors supported 0 or 1
	 * 
	 * @return - A string copied from the file containing the temperature for
	 *         the selected sensor. - "Err" if the input string cannot be
	 *         processed sensor out of range.
	 * 
	 *         - "--- -- --:--" Unable to parse the time
	 * 
	 *         Example: The last 2 lines of the Data file Nov 29 14:00:02 Sensor
	 *         0 C: 20.00 F: 68.00 Nov 29 14:00:03 Sensor 1 C: 19.94 F: 67.89
	 * 
	 *         String s[]{ "Nov 29 14:00:02 Sensor 0 C: 20.00 F: 68.00",
	 *         "Nov 29 14:00:03 Sensor 1 C: 19.94 F: 67.89"}
	 * 
	 *         Returns for getTempForSensor[s,1]; if celsius == false "67.89" ==
	 *         true "19.94"
	 * 
	 *         Note: Reloads the class member array temperatureLog
	 *         
	 *         
	 *      datastr with 6 entries looks like this   
	 *		Dec 24 12:20:03 Sensor 1 C: 19.19 F: 66.54
	 *		Dec 24 12:20:02 Sensor 0 C: 19.56 F: 67.21
	 *		Dec 24 12:00:03 Sensor 1 C: 19.19 F: 66.54
	 *		Dec 24 12:00:02 Sensor 0 C: 19.81 F: 67.66
	 *		Dec 24 11:40:03 Sensor 1 C: 19.25 F: 66.65
	 *		Dec 24 11:40:02 Sensor 0 C: 19.88 F: 67.78
	 * 
	 */
	// Dec 06 12:00:03
	// SimpleDateFormat dformat = new SimpleDateFormat("MMM dd HH:mm:ss yyyy");
	// Date last_time;

	
	private String getTempForSensor(String[] datastr, final byte sensor) {
//		String dstr = null;  // for collecting the date and times for graphing

//		for(int i=0; i< datastr.length; i++) {
//			System.out.println(i+" "+datastr[i]);
//		}
		
		String retString = "Err";
		if (sensor >= datastr.length) // 0 or 1 (need 2 lines for sensor 1)
			return retString;

		if (sensor < 0 || sensor > 1) // range check
			return retString;

		// Parse the Time.
		if (sensor == 0)
			lastLineofText = datastr[1];
		if (sensor == 1)
			lastLineofText = datastr[0];

		int st;
		st = lastLineofText.indexOf("Sensor");
		st = st - 3;
		if (st > 10)
			lastUpDateTime.setText(lastLineofText.substring(0, st - 1) + ' '+
					//tzc.getID()+' ');	// Short name EST
					tzdf.getDisplayName(tzdf.observesDaylightTime(),TimeZone.SHORT)+' '); // Short name EST or EDT
					//tzc.getDisplayName()+' '); //Long Time Zone Eastern Standard Time
		else
			lastUpDateTime.setText("--- -- --:--");

		for (int i = 0; i < datastr.length; i = i + 2) {
			st = 0;
			lastLineofText = datastr[5 - i - sensor];
			if (celsius) {
				st = lastLineofText.indexOf("C: "); // "C: 20.00 F: 68.00"
			} else {
				st = lastLineofText.indexOf("F: "); // "F: 67.89"
			}
			if (lastLineofText.length() < st + 8) { // "F: 67.89"
				//	return retString; // "Err" // Fix 4/11/2011 error first 2 readings
				retString = "Err"; // 4/11/2011 Fix first 2 readings
			} else {
				retString = lastLineofText.substring(st + 3, st + 8); // "F: 67.89"
																	  // --^-----^
			}
			
			try {
				// Is this block really nessary?
				if (Double.isNaN(temperatureLog[2])) {
					// First run JIC only 1 or 2 readings pad empty data
					// locations
					temperatureLog[1] = Double.valueOf(retString);
					temperatureLog[0] = Double.valueOf(retString);
				}
				temperatureLog[2] = temperatureLog[1];
				temperatureLog[1] = temperatureLog[0];
				temperatureLog[0] = (Double.valueOf(retString));

			} catch (NumberFormatException e) {
				// System.out.println("Number Format exception " + e.getMessage());
				// throw away
			}


//			//NEW
//						st = lastLineofText.indexOf("Sensor");
//						st = st - 3;
//						if (st > 10)
//							dstr = (lastLineofText.substring(0, st - 1) + ' ');
//						else
//							dstr = "--- -- --:--";
//			//END NEW
//
////TODO LAST EDIT			
				
//			try { 
//				last_time = dformat.parse(lastUpDateTime.getText()+" 2014"); 
//		     // System.out.println(last_time); 
//		    } catch (ParseException e) { 
//		        System.out.println("Unparseable date using " + lastUpDateTime.getText()); 
//		    }
//			System.out.println("Horray the new Date ="+ last_time.toString());
			 

//System.out.println("datastr= " + datastr.length + " "+ temperatureLog[0]+"\t"+lastLineofText+"\t>>"+dstr);
//
////dstr= lastLineofText.substring(0, st - 1) ;
//cdv.add(new Chrtdata(temperatureLog[0], dstr )); // Collect all for chart
////@SuppressWarnings("deprecation")
////java.util.Date d = new java.util.Date(lastUpDateTime.getText());
//// TODO End of LAST EDIT
////System.out.println("Date = "+ lastUpDateTime.getText());
		} // for datastr.length
//		lastUpDateTime.setText(dstr); // Display last time as the label
		return retString;
	}

	// The object we will use to write with instead of the standard screen
	// graphics
	private Graphics bufferGraphics;
	
	// The image that will contain everything that has been drawn on
	// bufferGraphics.
	private Image offscreen;
	
	// To get the width and height of the applet.
	private Dimension dim;

	private java.awt.Label lastUpDateTime;
	private Label instruction = null;
	
        private Applet applet = null;
	@Override
	public void init() {
		if(webstart) {                 // Running from main()
                    applet = new Applet();
System.out.println(applet.toString());
                    applet.add(new Panel());
                    //applet.init();
                    applet.setSize(200,400);
                    applet.validate();
System.out.println(applet.toString());
                }
                else {                         // Running Applet
                    applet = this;
                    parser(); // initialize saved / changeable settings                    
                }

		applet.setFont(font);		// effects LUDT, "?" 

		//	crlf = System.getProperty("line.separator");
		temperatureLog = new double[3];
		for (int i = 0; i < temperatureLog.length; i++) {
			temperatureLog[i] = Double.NaN;
		}

		lastUpDateTime = new java.awt.Label("  --- -- --:--  ");
		lastUpDateTime.setName("LUDT");
		add(lastUpDateTime);

		lastUpDateTime.setAlignment(Label.CENTER);

//		parser(); // initialize saved / changeable settings

//		if(tz < 0)
//			tz = get_TZ(digi_Temp_TZ);
		//System.out.println("TimeZone String= "+ digi_Temp_TZ);

		//tzdf = TimeZone.getTimeZone(digi_Temp_TZ);
		//moved to parseHTML()
		
		//System.out.println("Initizled TimeZone= "+ tzc.getDisplayName());
		setDisplays();
System.out.println(applet.toString());
		dim = applet.getSize();
		// Create an offscreen image to draw on
		// Make it the size of the applet, this is just perfect larger
		// size could slow it down unnecessary.
		offscreen = applet.createImage(dim.width, dim.height);
if(offscreen == null){
    System.out.println("NULL");
    applet.setVisible(true);
    offscreen = applet.createImage(200, 400);
    System.out.println(applet.toString());
    bufferGraphics =  offscreen.getGraphics();
} else {
    System.out.println(offscreen.toString());
}
		// by doing this everything that is drawn by bufferGraphics
		// will be written on the offscreen image.
		bufferGraphics = offscreen.getGraphics();
		//bufferGraphics.setFont(font);


		instruction = new Label("?");
		//instruction.setFont(font);
		instruction.setName("Info");
		//bu.setBackground(this.getBackground().darker());
		//super.add(instruction);
		applet.add(instruction);

		
		instruction.addMouseListener(this);
		lastUpDateTime.addMouseListener(this);
		addMouseListener(this);
		
//		addWindowListener(this);

		// Thread to Auto Update the display data
		Dythread = new Thread(this);

// Moved to reFreshDisplay to force ontop		
//		if(info){
//			Instructions i = new Instructions();
//			i.setVisible(true);
//		}
		
	}
	
/*
 * 	Take a human readable TimeZone string like "EST", "UST", +4 ... 
 *  convert and return a 0 ... 23 offset from UST
 *  	
 */
/*private byte get_TZ(String s){
	TimeZone tz = TimeZone.getTimeZone("America/Los_Angeles");
//	TimeZone tz = TimeZone.getTimeZone("PST");
	System.out.println("Timezone = "+ (int) tz.getRawOffset()/3600000);
	byte returnvalue = Calendar.ZONE_OFFSET;
	return returnvalue;
}
*/
	/**
	 * 
	 */
	// private boolean valid = false;
	private void parser() { // Get the user configurable data
		
		if (ckmgr == null) ckmgr = new CookieManger();

//		String t = null; // Temporary for processing the alert cookies		
//		t = ckmgr.getCookie("OVT");
//		if (t != null && t.length() > 20) {
//			alert_over = new TemperatureAlert("OVT", t);
//		}
//
//		t = ckmgr.getCookie("UT");
//		if (t != null && t.length() > 20) {
//			alert_under = new TemperatureAlert("UT", t);
//		}

		// Get the parameters from the HTML page first
		sensor = parseHTML(sensor_str, sensor); // 1st used as Key
		alarmThreshold = parseHTML(alarmThreshold_str, alarmThreshold);
		refresh_interval = parseHTML(refresh_interval_str, refresh_interval);

		// How long in minutes before data is condisered to be out of date
		outofdate = (int) parseHTML(outofdate_str, (long) outofdate);
		
		// current_Temp = parseHTML(current_Temp_str,"Err");

		// Update to Colors
		// First check these values are only obtained from the system properties
		// (HTML File)
		// NOTE: FRO = First Run Only
		// parameter is kept available from the calling HTML file
		
		
		// @TODO Transparency broken using signed jar with IE Windows
		// DO NOT USE
		//bgInfoColor = new Color(0, 255, 255, 205); // Test Cyan transparent
	    bgInfoColor =  parseHTML(bgInfoColor_str, bgInfoColor);
		bgInfoColor = Color.CYAN; // Override cookie for now.
		
		bgColor = parseHTML(bgColor_str, bgColor); // FRO
		ledOnColor = parseHTML(ledOnColor_str, ledOnColor); // FRO
		ledOffColor = parseHTML(ledOffColor_str, ledOffColor); // FRO
		// Obsolete fcOnColor = parseHTML(fcOnColor_str, fcOnColor);
		// Obsolete fcOffColor = parseHTML(fcOffColor_str, fcOffColor);
		fcLedOffColor = ledOffColor; // user settings for FC off Color REMOVED
		ledInvalidColor = parseHTML(ledInvalidColor_str, ledInvalidColor); // FRO
		tz = parseHTML(tz_str, tz); // 1st used as Key

		// logicals
		celsius = parseHTML(celsius_str, celsius); // FRO
		enhancedLED = parseHTML(enhancedLED_str, enhancedLED); // FRO
		no_bg = parseHTML(no_bg_str, no_bg); // FRO
		alert = parseHTML(alert_str, alert);  // FRO
		auto_color = parseHTML(auto_color_str, auto_color);
		
		digiTempFile = parseHTML(digiTempFile_str, digiTempFile);
		digi_Temp_TZ = parseHTML(digi_Temp_TZ_str, digi_Temp_TZ);
		if(tzdf==null) tzdf=TimeZone.getTimeZone(digi_Temp_TZ);
		info = parseHTML(info_str, info);
		//
		font =  new Font(parseHTML(font_str, font.getName()), (int) parseHTML(font_style_str, font.getStyle()), (int) parseHTML(font_size_str, font.getSize()));

		// verbose = parseHTML(verbose_str, false);

		//ckmgr.getColors(); // replaces above if cookie exists
		//ckmgr.getBooleans(); // replaces above booleans if cookie exists

		//TODO FORCED
//		if (digiTempFile == null)
//		digiTempFile = ("http://b3c-lc.com/b3c-lc.com//DigiTemp_files/DigiTemp.txt");
		// Oh no some how the file disappeared when cookie? Restored the check 
// ToDo verify default is working before removing above comment
		
		alert_Audio_File = parseHTML(alert_Audio_File_str, alert_Audio_File);

		ckmgr.getOneSingleBigCookie(); // New replaces/over-rides all of the above.


	// Get data from cookie if one is available
		ckmgr.gatherAndSaveCookieString(); // save data for first time callers
	}

	private String[] userSettingsInfo() {
		double hi = alarmThreshold[0];
		double low = alarmThreshold[1];
		if (celsius) {
			hi = toCelsius(hi);
			low = toCelsius(low);
		}
		
		//Adjust for large mumbers
		double oodint=outofdate;
		String oodstr="Minutes";
		if(outofdate > 59){
			oodint=outofdate/60.0;
			oodstr="Hours";
		}
		
		// Show if data is old or not
		String ood = "Expired";
		if (isCurrent(outofdate)) ood = "Valid";
//TODO XXXXXX	
//		Font nfont= new Font("ZapfDingbats", Font.BOLD, 28);
		//font = new Font(nfont.getName(),0,18);
//		font = new Font(nfont.getName(),nfont.getStyle(),nfont.getSize());
		String hinfo[] = {
				// getAppletInfo(),
				"\t" + sensor_str + ":\t\t\t\t" + sensor,
				"\t" + font_str + ":\t\t\t" + font,
				//"\t" + font_str + ":\t\t\t getFamily()="+font.getFamily() +",\tgetName()="+font.getName() + ",\tgetFontName="+font.getFontName() +",\tgetSize()="+font.getSize()+",\tGetStyle()="+font.getStyle(),
				"\t" + bgInfoColor_str +":\t\t\t" + bgInfoColor,
				"\t" + bgColor_str + ":\t\t" + bgColor,
				"\t" + ledOnColor_str + ":\t\t\t" + ledOnColor,
				"\t" + ledOffColor_str + ":\t\t\t" + ledOffColor,
				"\t" + auto_color_str + ":\t\t\t" + auto_color,
				"\t" + no_bg_str + ":\t\t" + no_bg,
				"\t" + celsius_str + ":\t\t\t\t" + celsius,
				// "\t"+fcOnColor_str+":\t\t\t\t\t" + fcOnColor,
				// "\t"+fcOffColor_str+":\t\t\t\t" + fcLedOffColor,
				"\t" + enhancedLED_str + ":\t\t\t\t" + enhancedLED,
				"\t" + alert_str + ":\t\t\t" + alert,	
				"\t" + info_str + ":\t\t\t" + info,				
				"\t" + refresh_interval_str + "l:\t\t" + refresh_interval
						+ " (" + (((int) (refresh_interval / 600.0)) / 100.0)
						+ " minutes) (1000 = 1 second)",
				"\t" + outofdate_str +":\t\t\t"+ oodint + " "+ oodstr+" From "+lastUpDateTime.getText()+"("+ood+")",
				"\t" + digiTempFile_str + ": ",
				"\t  " + digiTempFile,
				"\t  " + digi_Temp_TZ_str+ ":\t\t\t"+tzdf.getDisplayName(tzdf.observesDaylightTime(),TimeZone.LONG)+" ("+digi_Temp_TZ+")",
//"TZ.getID(" + tzc.getID()+")",
//"TZ.getDisplayName(true,TimeZone.Short) = " + tzc.getDisplayName(true,TimeZone.SHORT),
//"TZ.getDisplayName(true,TimeZone.Long) = " + tzc.getDisplayName(true,TimeZone.LONG),
//"TZ.getDisplayName(fales,TimeZone.Short) = " + tzc.getDisplayName(false,TimeZone.SHORT),
//"TZ.getDisplayName(fales,TimeZone.Long) = " + tzc.getDisplayName(false,TimeZone.LONG),
//"TZ.getDisplayName(TimeZone.Short) = " + tzc.getDisplayName(tzc.observesDaylightTime(),TimeZone.SHORT),
				"\t" + alert_Audio_File_str + ": ",
				"\t  " + alert_Audio_File,

				// "\t"+alarmThreshold_str+":\t\t\t" + alarmThreshold[0]+"/"+
				// alarmThreshold[1] + " Hystersis "+ hysteresis,
				"\t" + alarmThreshold_str + ":\t\t" + hi + "/" + low
						+ " Hystersis = " + hysteresis,

				"\t\t\t\t\t\t\t - OVT+hys   =\t "
						+ (((int) ((hi + hysteresis) * 100)) / 100.0),
				"\t\t\t\t\t\t\t - Under-hys =\t "
						+ (((int) ((low - hysteresis) * 100)) / 100.0),
				"\t\t\t\t\t\t\t - Current   =\t " + current_Temp }; //
		return hinfo;
	}

	private void setDisplays() {
		// Initial defaults
		int l_height = 75;
		int l_width = 50;
		int loc_x = 20;
		int loc_y = 40;

		if (DyT == null) {
			DyT = new Dy(loc_x, loc_y, (l_width * 3), l_height, Color.GRAY, //ledOnColor,
					bgColor, numOfDigits);
		}
		
		if(auto_color){
			DyT.setOnColor(Color.GREEN);
			DyT.setOffColor(Color.WHITE);
			DyT.setBackGroundColor(Color.WHITE);			
		} else {
			DyT.setOnColor(ledOnColor);
			DyT.setOffColor(ledOffColor);
			DyT.setBackGroundColor(bgColor);
		}
			DyT.enhancedLED(enhancedLED);
			
		if (FC == null) {
			FC = new SevenSegLed((loc_x + DyT.getSize().width) - 1,
			// -1 Adjust for a little white space
					(loc_y + (DyT.getSize().height / 2)), (l_width / 2),
					(DyT.getSize().height / 2), fcOnColor);
		}

		FC.setOnColor(fcOnColor);
		FC.setOffColor(fcLedOffColor);
		FC.setBackGroundColor(bgColor);
		FC.enhancedLED(enhancedLED);

		applet.setSize(100 + (numOfDigits * 50), 30 + (40 * numOfDigits));

		// Security.setProperty("networkaddress.cache.ttl", "0");

		// setName("This is the setName string");

		if (no_bg) {
			applet.setBackground(bgColor);

			DyT.setOffColor(bgColor);
			DyT.setBackGroundColor(bgColor);

			FC.setOffColor(bgColor);
			FC.setBackGroundColor(bgColor);
		}

		lastUpDateTime.setBackground(getBackground());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		/*
		 * // Wipe off everything that has been drawn before // Otherwise
		 * previous drawings would also be displayed.
		 * bufferGraphics.clearRect(0,0,dim.width,dim.width);
		 * bufferGraphics.setColor(Color.red);
		 * bufferGraphics.drawString("Bad Double-buffered",10,10); // draw the
		 * rect at the current mouse position // to the offscreen image
		 * bufferGraphics.fillRect(curX,curY,20,20); // draw the offscreen image
		 * to the screen like a normal image. // Since offscreen is the screen
		 * width we start at 0,0. g.drawImage(offscreen,0,0,this);
		 */

		bufferGraphics.clearRect(0, 0, dim.width, dim.width);
		DyT.paint(bufferGraphics);
		FC.paint(bufferGraphics);
		//g.drawImage(offscreen, 0, 0, this);
		g.drawImage(offscreen, 0, 0, applet);
		// DyT.paint(g);
		// FC.paint(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Container#update(java.awt.Graphics)
	 */
	@Override
	public void update(Graphics g) {
		paint(g); // helps in the double buffering of the graphics
		// super.update(g);
	}
	 
	
	/**
	 * 
	 * @param name
	 *            Keyword to search from the calling HTML page parameter list
	 * @param value
	 *            Default value returned when name is not found
	 * @return value for the keyword, value if keyword is not "True" or missing.
	 */
	private boolean parseHTML(String name, boolean value) {
		// Keeping around to get initial values from HTML file

		boolean returnvalue = value;
		String temp = getParameter(name);

//		if (temp != null) {
//			if (temp.equalsIgnoreCase("TRUE"))
//				returnvalue = true;
//			else if (temp.equalsIgnoreCase("FALSE"))
//				returnvalue = false;
//		}
		returnvalue = parseBoolean(temp, value);
		return returnvalue;
	}
	
	/**
	 * 
	 * @param String s
	 *			String "True" or "False" to attempt to convert 
	 *            
	 * @param boolean b
	 *			Default value if conversion fails
	 * 
	 * @return boolean true or false.
	 * 
	 */
	private boolean parseBoolean(String s, boolean b){
		boolean retvalue = b;

		// I want to presurve value b parsBoolean returs False for all except TRUE
		// retvalue = Boolean.parseBoolean(s); 
		if( s != null){
			if(s.equalsIgnoreCase("TRUE")) retvalue = Boolean.TRUE;
			else if(s.equalsIgnoreCase("FALSE")) retvalue = Boolean.FALSE;
		}
		return retvalue;
	}

	// /**
	// *
	// * @param name
	// * @param value
	// * @return
	// *
	// */
	// private Color parseHTML(String name, Color value) {
	// Color returnColor = value;
	//
	// String temp = m_getParameter(name);
	// if (temp == null){ // Nothing found use default
	// return value;
	// }
	//
	// // Now it is a string "RED", "Black" ...
	// // or RBG values Back_Ground_Color=java.awt.Color[r=0,g=255,b=0]
	// // or possibly a hex value FF8800 ...
	// // Note FF,FF,FF,FF as returned by getRGB is too long cut the alpha
	// // before converting to an integer.
	// if (temp.length() >= 6 && temp.length() <= 8) {
	// try { // see if it is a hex integer value
	// int colorValue=0;
	// if(temp.length() == 8)
	// colorValue = Integer.parseInt("0" + temp.substring(2,temp.length()), 16);
	// if(temp.length() == 6)
	// colorValue = Integer.parseInt("0" + temp, 16);
	// returnColor = new Color(colorValue);
	// } catch (NumberFormatException e) {
	// // throw away maybe it is a color name string
	// }
	// } else {
	// boolean brighter = false;
	// boolean darker = false;
	// int p = temp.lastIndexOf('.');
	//
	// if (p > 0 && p < temp.length()) {
	// String tmp = temp.substring(p + 1,
	// temp.length());
	// temp = temp.substring(0, p);
	// if (tmp.compareToIgnoreCase("darker") == 0)
	// darker = true;
	// if (tmp.compareToIgnoreCase("lighter") == 0)
	// brighter = true;
	// if (tmp.compareToIgnoreCase("brighter") == 0)
	// brighter = true;
	// }
	//
	// if (temp.compareToIgnoreCase("black") == 0)
	// returnColor = Color.black;
	// if (temp.compareToIgnoreCase("blue") == 0)
	// returnColor = Color.blue;
	// if (temp.compareToIgnoreCase("cyan") == 0)
	// returnColor = Color.cyan;
	// if (temp.compareToIgnoreCase("darkGray") == 0)
	// returnColor = Color.darkGray;
	// if (temp.compareToIgnoreCase("gray") == 0)
	// returnColor = Color.gray;
	// if (temp.compareToIgnoreCase("green") == 0)
	// returnColor = Color.green;
	// if (temp.compareToIgnoreCase("lightGray") == 0)
	// returnColor = Color.lightGray;
	// if (temp.compareToIgnoreCase("magenta") == 0)
	// returnColor = Color.magenta;
	// if (temp.compareToIgnoreCase("orange") == 0)
	// returnColor = Color.orange;
	// if (temp.compareToIgnoreCase("pink") == 0)
	// returnColor = Color.pink;
	// if (temp.compareToIgnoreCase("red") == 0)
	// returnColor = Color.red;
	// if (temp.compareToIgnoreCase("white") == 0)
	// returnColor = Color.white;
	// if (temp.compareToIgnoreCase("yellow") == 0)
	// returnColor = Color.yellow;
	//
	// if (brighter)
	// returnColor = returnColor.brighter();
	// if (darker)
	// returnColor = returnColor.darker();
	// }
	// return returnColor;
	// }

	/**
	 * 
	 * @param name
	 * @param value
	 * @return
	 * 
	 */
	private Color parseHTML(String name, Color value) {
		// Keeping around to get initial values from HTML file

		String temp = getParameter(name);
		if (temp == null) { // Nothing found use default
			return value;
		}
		return colorDecode(temp, value);
	}

	/**
	 * 
	 * @param color_str
	 * @param value
	 * @return
	 */
	private Color colorDecode(String color_str, Color value) {
		Color returnColor = value;
		// Now it is a string "RED", "Black" ...
		// or RBG values Back_Ground_Color=java.awt.Color[r=0,g=255,b=0]
		// or possibly a hex value FF8800 ...
		// Note FF,FF,FF,FF as returned by getRGB is too long cut the alpha
		// before converting to an integer.

		if (color_str.length() >= 6 && color_str.length() <= 8) {
			try { // see if it is a hex integer value
				int colorValue = 0;
				if (color_str.length() == 8)
					colorValue = Integer.parseInt(
							"0" + color_str.substring(2, color_str.length()),
							16);
				if (color_str.length() == 6)
					colorValue = Integer.parseInt("0" + color_str, 16);
				returnColor = new Color(colorValue);
			} catch (NumberFormatException e) {
				// throw away maybe it is a color name string
			}
		} else {
			boolean brighter = false;
			boolean darker = false;
			int p = color_str.lastIndexOf('.');

			if (p > 0 && p < color_str.length()) {
				String tmp = color_str.substring(p + 1, color_str.length());
				color_str = color_str.substring(0, p);
				if (tmp.compareToIgnoreCase("darker") == 0)
					darker = true;
				if (tmp.compareToIgnoreCase("lighter") == 0)
					brighter = true;
				if (tmp.compareToIgnoreCase("brighter") == 0)
					brighter = true;
			}

			if (color_str.compareToIgnoreCase("black") == 0)
				returnColor = Color.black;
			if (color_str.compareToIgnoreCase("blue") == 0)
				returnColor = Color.blue;
			if (color_str.compareToIgnoreCase("cyan") == 0)
				returnColor = Color.cyan;
			if (color_str.compareToIgnoreCase("darkGray") == 0)
				returnColor = Color.darkGray;
			if (color_str.compareToIgnoreCase("gray") == 0)
				returnColor = Color.gray;
			if (color_str.compareToIgnoreCase("green") == 0)
				returnColor = Color.green;
			if (color_str.compareToIgnoreCase("lightGray") == 0)
				returnColor = Color.lightGray;
			if (color_str.compareToIgnoreCase("magenta") == 0)
				returnColor = Color.magenta;
			if (color_str.compareToIgnoreCase("orange") == 0)
				returnColor = Color.orange;
			if (color_str.compareToIgnoreCase("pink") == 0)
				returnColor = Color.pink;
			if (color_str.compareToIgnoreCase("red") == 0)
				returnColor = Color.red;
			if (color_str.compareToIgnoreCase("white") == 0)
				returnColor = Color.white;
			if (color_str.compareToIgnoreCase("yellow") == 0)
				returnColor = Color.yellow;

			if (brighter)
				returnColor = returnColor.brighter();
			if (darker)
				returnColor = returnColor.darker();
		}
		return returnColor;
	}

	// /**
	// *
	// * @param name
	// * Keyword to search from the calling HTML page parameter list
	// * @param value
	// * Default value returned when name is not found
	// * @return value for the keyword, value if keyword is not "True" or
	// missing.
	// */
	// private int parseHTML(String name, int value) {
	//
	// int returnValue = value;
	//
	// try{
	// String temp = m_getParameter(name);
	//
	// if (temp != null) {
	// Double d = Double.parseDouble(temp);
	// returnValue = d.intValue();
	// }
	// } catch(Exception e){
	// // Throw away
	// }
	// return returnValue;
	//
	// }

	/**
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
//	private int[] parseHTML(String name, int[] value) {
//		String temp = getParameter(name);
//		return getHiLow(temp, value);
//	}
	
	private double[] parseHTML(String name, double[] value){
		String temp = getParameter(name);
		return getHiLow(temp, value);
	}

	private double[] getHiLow(String temp, double[] value) {
		double[] returnvalue = value;
		try {
			if (temp == null || temp.length() < 5) // Sanity check
				return returnvalue;
			int index = temp.indexOf('/');
			if (index > 0) {
				double ovr = Double
						.parseDouble(temp.substring(0, temp.indexOf('/')));
				returnvalue[0] = ovr;
				double under = Double.parseDouble(temp.substring(
						temp.indexOf('/') + 1, temp.length()));
				returnvalue[1] = under;
			}
		} catch (Exception e) {
			// Throw away
		}
		return returnvalue;
	}

	/**
	 * 
	 * @param name
	 *            Keyword to search from the calling HTML page parameter list
	 * @param value
	 *            Default value returned when name is not found
	 * @return value for the keyword, value if keyword is not "True" or missing.
	 */
	private long parseHTML(String name, long value) {
		String temp = getParameter(name);
		return getLong(temp, value);
	}

	private long getLong(String temp, long value) {
		long returnValue = value;

		try {

			if (temp != null) {
				Double d = Double.parseDouble(temp);

				returnValue = d.longValue();
			}
		} catch (Exception e) {
			// throw away
		}
		return returnValue;

	}

	/**
	 * 
	 * @param name
	 *            Key to match
	 * @param value
	 *            default value returned if Key is not found
	 * 
	 * @return searches for the byte value associated with the name from (in
	 *         order) Cookie System properties (calling HTML file or default
	 *         given with the calling value
	 * 
	 */
	private byte parseHTML(String name, byte value) {
		String temp = getParameter(name);
		return getByte(temp, value);
	}

	private byte getByte(String temp, byte value) {
		byte returnValue = value;
		try {
			if (temp != null) {
				byte b = Byte.parseByte(temp, 10);
				returnValue = b;
			}
		} catch (Exception e) {
			// throw away
		}

		return returnValue;
	}

	// /**
	// *
	// * @param name
	// * @param value
	// * Default value returned when name is not found
	// * @return the value if found in the HTML parameter list else the default
	// */
	// private double parseHTML_Value(String name, double value) {
	// try {
	// double newvalue = value;
	// parseHTMLString(name, Double.toString(newvalue));
	// return newvalue;
	// } catch (java.lang.NumberFormatException e) {
	// return value;
	// }
	// }
	//
	// /**
	// *
	// * @param name
	// * @param value
	// * Default value returned when name is not found
	// * @return the value if found in the HTML parameter list else the default
	// */
	// private Color parseHTML_Value(String name, Color value) {
	// Color.getColor(name, value);
	// return value;
	// }
	//
	//
	// /**
	// *
	// * @param name
	// * Keyword to search from the calling HTML page parameter list
	// * @param value
	// * Default value returned when name is not found
	// * @return String associated to the keyword String is empty "" if keyword
	// is
	// * not found.
	// */
	// private String parseHTMLString(String name, String value) {
	//
	// String temp = getParameter(name);
	//
	// if (null == temp)
	// return value;
	// else
	// return temp;
	// }

	/**
	 * 
	 * @param name
	 *            Keyword to search from the calling HTML page parameter list
	 * @param value
	 *            Default value returned when name is not found
	 * @return value for the keyword, value if keyword is not "True" or missing.
	 */
	private String parseHTML(String name, String value) {
		String temp = getParameter(name);
		return getString(temp, value);
	}

	private String getString(String temp, String value) {
		String returnValue = value;

		try {

			if (temp != null) {
				returnValue = temp;
			}
		} catch (Exception e) {
			// Throw away
		}

		return returnValue;
	}

	/*
	 * isCurrent checks for out-of-date data compairs the time string for the
	 * last temperature from the source The data has allready been collected in
	 * the Chrtdata class and converted to a Java Date object it will be the
	 * last object in the cdv Vector.
	 * 
	 * Create a Calendar object add the maximum elapesed time allowed then
	 * check with the current Time.
	 * 
	 * input:
	 *  Allowed minutes for expiration defaults to 15 with no value
	 * 
	 * Returns: true if data is in the valid number of minutes false if time is
	 * outside the alloted number of minutes or the time (Date object) is
	 * invalid.
	 * 
	 * Side effects: Changes class properity invalid_data
	 * 
	 */
//	private boolean isCurrent() { // override with 15 minute default
//		return isCurrent(15);
//
//	}

	private boolean isCurrent(int experation) {
		//invalid_data = true; // wtgbitn
		boolean valid_data = false;
		
		if (cdv.isEmpty()){ // no data or Vector not collected 
			// System.out.println("is Empty");
		return valid_data;
	}
		else {
			// System.out.println("Not empty");
			Date last = cdv.lastElement().t; // Last collected Date for the sensor
			Calendar cal = Calendar.getInstance();
			
			cal.setTime(last);
			// ToDo use real timezone data shold be entered with timez
			//cal.add(Calendar.HOUR, 4); // TimeZone
			cal.add(Calendar.MINUTE, experation);
//			Date newDate = cal.getTime(); // for Debugging output

			Date now = new Date();
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(now);
			// System.out.println("NewDate= "+newDate+" now= "+ now);

			if (cal.before(cal1)) { // Old
				//System.out.println(newDate + " Before " + now.toString());
				valid_data = false;
			} else { // New
				//System.out.println(newDate + "\n After \n" + now.toString());
				valid_data = true;
			}

		}
		return valid_data;	
	}	
	
	// private String avgStr;
	/**
	 * Updates data and refreshes the display
	 */
	private void reFreshDisplay() {

		String[] s = null;
		try {
			s = getCurrentTemp();
			current_Temp = getTempForSensor(s, sensor);
		} catch (Exception e) {
			new Help("getCurrentTemp trap " + sensor, e.toString(), Color.RED);
		}
		FC.setPeriod(!FC.isPeriod()); // Debug show that thread is alive

		// Round instead of truncate
		try {
			double d = Double.parseDouble(current_Temp);
			d = (d + 0.05); // round up
			DyT.setValue(d);
			// using doubles probably does not make any difference
			// double is conveted to a String anyway
			// DyT.setValue(Double.toString(d));
		} catch (NumberFormatException e) {
			// CurrentTemp string could be "ERR"
			DyT.setValue(current_Temp); // Old Truncated
		}

		if (celsius) {
			FC.setValue(12); // 12 = 'C'
			hysteresis = .28; // for Celsius (F-32) * 5 / 9
		} else {
			FC.setValue(15); // 15 = 'F'
			hysteresis = 0.5; // for Fahrenheit
		}

		// What is the trend avarage the preveous 2 readings compare to the current
		double avarage = ((temperatureLog[1] + temperatureLog[2]) / 2);
//		isCurrent();	// force sanity check
		if (isCurrent(outofdate)) {
			if (temperatureLog[0] > (avarage + hysteresis)) {
				fcOnColor = Color.RED; // getting warmer
			} else if (temperatureLog[0] < (avarage - hysteresis)) {
				fcOnColor = Color.BLUE; // getting colder
			} else {
				fcOnColor = Color.GREEN; // Stable
			}
		} else {
			fcOnColor = ledInvalidColor;
		}
		//		if (invalid_data) {
//			fcOnColor = ledInvalidColor;
//		} else if (temperatureLog[0] > (avarage + hysteresis)) {
//			fcOnColor = Color.RED; // getting warmer
//		} else if (temperatureLog[0] < (avarage - hysteresis)) {
//			fcOnColor = Color.BLUE; // getting colder
//		} else {
//			fcOnColor = Color.GREEN; // Stable
//		}

		FC.setOnColor(fcOnColor); // Update JIC any changed;
		if(auto_color) DyT.setOnColor(fcOnColor);
		
		// Over Under temperature checks
		double ovt = alarmThreshold[0];
		double under = alarmThreshold[1];
		if (celsius) { // Compare apples to apples
			ovt = toCelsius(ovt);
			under = toCelsius(under);
		}

		if (temperatureLog[0] > ovt) {
			if (alert_over == null || !alert_over.isAlive()) {
				alert_over = new TemperatureAlert("OVT",
						lastUpDateTime.getText(), temperatureLog[0]
								+ " OVER TEMPERATURE " + ovt, Color.RED);
			}
		}

		if (temperatureLog[0] < under) {
			if (alert_under == null || !alert_under.isAlive()) {
				alert_under = new TemperatureAlert("UT",
						lastUpDateTime.getText(), temperatureLog[0]
								+ " UNDER TEMPERATURE " + under,
						Color.BLUE.brighter());
			}
		}
		// if(temperatureLog[0] > ovt){
		//
		// if(alert_over == null || alert_over.d == null){
		// alert_over = new Help("*** WARNING WARNING ***", temperatureLog[0] +
		// " OVER TEMPERATURE "+ ovt, Color.RED);
		// }
		//
		// alert_over.setVisible(true);
		// alert_over.toFront();
		// //alert_over.setAlwaysOnTop(true); //Breaks settingsMenu to Front is
		// // Better any way
		// }
		// if(temperatureLog[0] < under ){
		// if(alert_under == null || alert_under.d == null){
		// alert_under = new Help("*** WARNING  WARNING  ***", temperatureLog[0]
		// + " UNDER TEMPERATURE "+under, Color.BLUE.brighter());
		// }
		// alert_under.setVisible(true);
		// alert_under.toFront();
		// // alert_under.setAlwaysOnTop(true);//Breaks settingsMenu to Front is
		// // Better any way
		// }

		// if (ta == null || !ta.isAlive()){
		// ta = new TemperatureAlert("*** WARNING  WARNING  ***",
		// temperatureLog[0] + " UNDER TEMPERATURE "+under,
		// Color.BLUE.brighter());
		// }
		applet.repaint();
		
		// Indtruction here to force on top first run 
			if(info && firstrun){
				// System.out.println("First run = "+firstrun);
				firstrun = false;
				if(inst == null){
					inst = new Instructions();
				} else{
					inst.setVisible(true);
				}
		}

	}

	/*
	 *	Celsius to Fahrenheit
	 *		(�C x 9/5) + 32 = �F
	 *
	 *	Fahrenheit to Celsius
	 *		(�F - 32) x 5/9 = �C
	 */
	double toCelsius(double fahrenheit) {
		// System.out.println("toCelsius " + fahrenheit);
		//return ((int) (((((fahrenheit - 32) * 5) / 9) * 1000)+.5)) / 1000.0;
		double returnval = ((int) (((((fahrenheit - 32) * 5) / 9) * 100)+.5)) / 100.0; // 2 decimal places
		//System.out.println("returning "+returnval);
		return returnval;
	}
	
	double toFahrenheit(double celsius){
		// System.out.println("toFahrenheit " + celsius);
		//return ((int) (((((celsius * 9) / 5) + 32) * 1000)+.5)) / 1000.0;
		double returnval = ((int) (((((celsius * 9) / 5) + 32) * 10)+.5)) / 10.0; // 1 decimal Place
		//System.out.println("returning "+returnval);
		return returnval;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.applet.Applet#resize(java.awt.Dimension)
	 */
	// @Override
	// public void resize(Dimension arg0) {
	// // new Help("resize","Dimension "+arg0);
	// // System.out.println("resize(" + arg0 + ")");
	// // int l_height = 75; // arg0.height-20; // 75;
	// // int l_width = 50; // (arg0.width-40)/6; //50;
	// // //int loc_x = 20;
	// // //int loc_y = 40;
	// // System.out.println("l_width = "+ l_width + " l_hight = " + l_height);
	// // //DyT = new Dy(loc_x, loc_y, (l_width * 3), l_height, ledColor,
	// // bgColor,
	// // // numOfDigits);
	// // FC.setSize(l_width, l_height);
	// // //FC = new SevenSegLed((loc_x + DyT.getSize().width),
	// // // (loc_y + (DyT.getSize().height / 2)), (l_width / 2),
	// // // (DyT.getSize().height / 2), FCcolor);
	// //
	// // // setSize(100 + (numOfDigits * 50), 30 + (40 * numOfDigits));
	// // // TODO Auto-generated method stub
	// super.resize(arg0);
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see java.applet.Applet#resize(int, int)
	// */
	// @Override
	// public void resize(int width, int height) {
	// // new Help("resize int int","width="+width+" height="+height);
	// // System.out.println("resize()");
	// // TODO Auto-generated method stub
	// super.resize(width, height);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.applet.Applet#start()
	 */
	@Override
	public void start() {
		// System.out.println("Hello from "+this.getName()+" start()");

		
		//	this.setVisible(true);

		if(Dythread != null)
		if (!Dythread.isAlive())
			Dythread.start();
		reFreshDisplay();
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.applet.Applet#stop()
	 */
	@Override
	public void stop() {
		// System.out.println("Hello from " + this.getName() + " stop()" + "size = " + getSize());

		// setMyCookies(); Does not work here.
		running = false;

		// Dythread is destroyed in destroy()
		// If Dythread is not stopped 
		// IllegalThreadStateException happens with stop()  / start()
		 if(Dythread != null){
		 Dythread.interrupt(); // Can Throw a null pointer exception
		 // System.out.println("Dythread forced interrupt");
		 } // else System.out.println("Dythread was Allready Null");
		 Dythread = null;

		applet.stop();
		//super.stop();
	}

	private SelectionMenu SelMenu = null;
	
	private boolean selMenu(){
		// boolean retvalue = false;
		if (SelMenu == null){ // || !SelMenu.isEnabled())
			SelMenu = new SelectionMenu(); // selectColorSelection();
		}
		SelMenu.setEnabled(true);
		SelMenu.setVisible(true);	// Always display, that's why where here.
		SelMenu.toFront();
		return true;		// Future use
	}
	/**
	 *  
	 * 
	 */
	//	public void mouseClicked(MouseEvent e) {
		public void mousePressed(MouseEvent e) {
		// Not used, Required by mouse listener
		// **  Moved these events to mouse clicked presed caused multible events
			
		//System.out.println("mouseClicked "+e.getComponent().getName());
			e.consume();
		//new XYChart(temperatureLog);
		//for(int i=0; i< temperatureLog.length; i++)
		//System.out.println(i+" "+temperatureLog[i]);
		//new Help("log",cdv.toString());
		
		//new XYChart(new double[]{0,56.1,56.2,60,60.2,65.3,70.5});
//		if(e.getComponent().getName() == instruction.getName())
//			System.out.println("instruction click");
//		else
//			System.out.println("mouseClicked "+e.getComponent().getName());

		//if(null == inst){ // current event is not instruction
		//} // not Instructions

		// if (e.isControlDown())
		// if(e.isAltDown()){ // Ctrl/Alt + click
		// systemInfo();
		// } else { // Ctrl + click
		// if (SelMenu == null) // || !SelMenu.isEnabled())
		// SelMenu = new SelectionMenu(); // selectColorSelection();
		// else {
		// if(null == SelMenu)
		// System.out.println("mouseClicked SelMenu is null");
		// else SelMenu.setVisible(true);
		// // SelMenu.setAlwaysOnTop(true);
		// }
		// } else // Just a click
		// reFreshDisplay();
		// e.consume();
	}
	
//	private Window puw;
//	private Panel infoPanel;
//	private Frame frame;
//	private Font popUpFont = new Font("Courier", Font.ITALIC, 14);
	// ********************************************************
//	private boolean helpinfo(){
//		Frame frame = new Frame();
//		Window puw = new Window(frame);
//		Panel infoPanel = new Panel();
//		java.awt.TextArea t = new java.awt.TextArea(
//				"\tControl Click\t:\tSettings\n"+
//				"\tCntrl+Alt click:\tSystem info\n"+
//				"\tRight Click\t:\n"+
//				"\tLeft  Click\t:"
//				);
//		Label l = new Label("Do not show this again");
//		Checkbox c = new Checkbox("Click Me");
//		Button b = new Button("Close");
//		c.setState(true);
//
//		
//		
//		
//		puw.setBackground(bgInfoColor);
//		infoPanel.add(t);
//		infoPanel.add(l);
//		infoPanel.add(c);
//		infoPanel.add(b);
//		infoPanel.setSize(500,300);
//		infoPanel.setVisible(true);
//		puw.setSize(infoPanel.getWidth(), infoPanel.getHeight());
//		puw.add(infoPanel);
//		puw.setVisible(true);
//		boolean state = c.getState();
//		int i =0;
//		while(c.getState() == state){
//			if(c.getState() != state )
//				System.out.println("State has changed");
//			else  i++;
//			if(i == 255) {
//				i=0;
//				l.setVisible(!l.isVisible());
//			}
//		}
//
//		puw.setVisible(false);
//	
//		return c.getState();
//	}
	// *******************************************************
	/*
	 * Displays a pop-up window with the last three readings
	 * 
	 */
	private Window puw;
	private Panel infoPanel;
	private static final char deg = '\u00B0';

	private void histPuw(Point ePoint) {
//	Font popUpFont = new Font("Courier", Font.ITALIC, 14);
	Font popUpFont = new Font("Courier New", Font.TRUETYPE_FONT, 18);
	Frame frame;
	char cf ='F';
	if(celsius==true) cf = 'C';

	
	if (puw == null) {
			frame = new Frame();
			puw = new Window(frame);
			infoPanel = new Panel();
			puw.setBackground(bgInfoColor);
			puw.add(infoPanel);
			
			Label l1 = new Label(
					"Last[3]: " + temperatureLog[0] + deg +cf + ", " + temperatureLog[1] + deg + cf + ", " + temperatureLog[2] + deg + cf,
					Label.LEFT);

			l1.setFont(popUpFont);
			int height = 0;
			int width = 0;

			height = height + l1.getFontMetrics(l1.getFont()).getHeight() + l1.getFontMetrics(l1.getFont()).getAscent();

			if (l1.getFontMetrics(l1.getFont()).stringWidth(l1.getText()) > width)
				width = l1.getFontMetrics(l1.getFont()).stringWidth(l1.getText());

			Label l2 = new Label(lastLineofText + " ", Label.CENTER);
			l2.setFont(popUpFont);

			height = height + l2.getFontMetrics(l2.getFont()).getHeight() + l2.getFontMetrics(l2.getFont()).getAscent();

			if (l2.getFontMetrics(l2.getFont()).stringWidth(l2.getText()) > width)
				width = l2.getFontMetrics(l2.getFont()).stringWidth(l2.getText());

			Label l3 = new Label("High: " + hiLow[0] + deg + cf + "  " + hiLow_s[0], Label.LEFT);
			l3.setFont(popUpFont);

			height = height + l3.getFontMetrics(l3.getFont()).getHeight() + l3.getFontMetrics(l3.getFont()).getAscent();

			if (l3.getFontMetrics(l3.getFont()).stringWidth(l3.getText()) > width)
				width = l3.getFontMetrics(l3.getFont()).stringWidth(l3.getText());

			Label l4 = new Label("Low:  " + hiLow[1] + deg + cf + "  " + hiLow_s[1], Label.LEFT);

			l4.setFont(popUpFont);

			height = height + l4.getFontMetrics(l4.getFont()).getHeight() + l4.getFontMetrics(l4.getFont()).getAscent();

			if (l4.getFontMetrics(l4.getFont()).stringWidth(l4.getText()) > width)
				width = l4.getFontMetrics(l4.getFont()).stringWidth(l4.getText());

			l1.setSize(width, l1.getHeight());
			l2.setSize(width, l2.getHeight());
			l3.setSize(width, l3.getHeight());
			l4.setSize(width, l4.getHeight());

			infoPanel.add(l2);
			infoPanel.add(l3);
			infoPanel.add(l4);
			infoPanel.add(l1);
			infoPanel.setSize(width + 2, height);

			// xxx e.translatePoint(this.getLocationOnScreen().x + 1,
			// xxx this.getLocationOnScreen().y + 10); // stops enter/exit
			// flicker
			// xxx // keeping the mouse outside infoPanel
			puw.setSize(infoPanel.getWidth(), infoPanel.getHeight());
			puw.add(infoPanel);
			// xxx puw.setLocation(e.getPoint());

			/*
			 * "Log " + temperatureLog[0] + "�, " + temperatureLog[1] + "�, " +
			 * temperatureLog[2] +"�", lastLineofText + "\n\tHigh: " + hiLow[0]
			 * + "� " + hiLow_s[0] + "\n\tLow:  " + hiLow[1] + "� " +
			 * hiLow_s[1])
			 */
		} // puw == null
		// Not sure how this would be called more than once JIC allow update of position
		puw.setLocation(ePoint);
		puw.setVisible(true);

	}

	public void mouseEntered(MouseEvent e) {
		// Mouse hovering over the Last Update Date Label
		if (e.getComponent().getName() == lastUpDateTime.getName()) {

			//System.out.println("\ngetPoint =\t" + e.getPoint());
			//System.out.println("OnScreen =\t" + e.getLocationOnScreen());
			
//			int x = 0; int y = 0;
//			if(e.getPoint().x < 5){
//				System.out.println("Came in from the left");
//				x=10;
//			} else {
//				System.out.println("Came in from the right ");
//				// x=-10;
//			}
//			if(e.getPoint().y < 5){
//				System.out.println("Came in from the top ");
//			} else {
//				System.out.println("Came in from the bottom");
//				// y=-10;
//			}
//			e.translatePoint(this.getLocationOnScreen().x + x,
//					this.getLocationOnScreen().y + y); // help to stops enter/exit flicker by

			
			e.translatePoint(e.getLocationOnScreen().x,
					e.getLocationOnScreen().y); // help to stops enter/exit flicker by
														// not capturing the mouse with this Panel
			//System.out.println("TgetPoint =\t" + e.getPoint());
			//System.out.println("TOnScreen =\t" + e.getLocationOnScreen());
			
			//histPuw(e.getLocationOnScreen());
			histPuw(e.getPoint());
		
		}		
		e.consume();
	}

		// Not used, Required by mouse listener
/*
 * 		public void mouseEntered(MouseEvent ev) {
			puw = new Window(frame);
			infoPanel = new Panel();
			try {
				StringParser sp = new StringParser(" Station #"
						+ number
						+ " "
						+ ((RMIStation) stations.elementAt(number - 1))
								.getInfo() + " ", ' ', 41);
				String[] s = sp.toStrings();
				int width = 0, height = 0;
				Label l;
				for (int i = 0; i < s.length; i++) {
					l = new Label(" " + s[i] + " ", Label.LEFT);
					l.setFont(popUpFont);
					height = height + l.getFontMetrics(l.getFont()).getHeight()
							+ l.getFontMetrics(l.getFont()).getAscent();
					if (l.getFontMetrics(l.getFont()).stringWidth(l.getText()) > width)
						width = l.getFontMetrics(l.getFont()).stringWidth(
								l.getText());
					infoPanel.add(l);
				}
				infoPanel.setSize(width, height);
				puw.setBackground(Color.cyan);
			} catch (RemoteException e) {
			}
			puw.setSize(infoPanel.getWidth(), infoPanel.getHeight());
			puw.add(infoPanel);
			ev.translatePoint(p.getLocationOnScreen().x + 1, p
					.getLocationOnScreen().y + 1); // +1 stops enter/exit
													// filcker
			puw.setLocation(ev.getPoint());
			puw.setVisible(true);
		}

 */

	public void mouseExited(MouseEvent e) {
		if (e.getComponent().getName() == lastUpDateTime.getName()) {
		if (puw != null) {
			puw.setVisible(false);
			puw.remove(infoPanel);
			infoPanel = null;
			puw = null;
		}
		//} else { 
		//	System.out.println("Exit "+arg0.getComponent().getName());
		}
		e.consume();
	}

	/*
	 * 
	 *  Instead of making these 2 objects individual mouse events just call function from
	 *  main event for now.
	 * 
	 */
	// Click on instruction "?"
//	public void instructionsMousePressed(MouseEvent e) {
//		instructions();
//
////		System.out.println("Instructions " + e);
////		switch (e.getButton()) {
////		case MouseEvent.BUTTON1: {
////			System.out.println("Button 1");
////			break;
////		}
////		case MouseEvent.BUTTON2: {
////			System.out.println("Button  2");
////			break;
////		}
////		case MouseEvent.BUTTON3: {
////			System.out.println("Button  3");
////			break;
////		}
////		default: {
////			System.out.println("default " + e.getButton());
////		}
////		}
//		//e.consume();
//	}

	private boolean history(){
		char cf ='F';
		if(celsius==true) cf = 'C';

		if (tempLogHelp == null || tempLogHelp.d == null) {
			tempLogHelp = new Help("Log " + temperatureLog[0] + deg + cf + ",  "
					+ temperatureLog[1] + deg + cf + ",  " + temperatureLog[2] + deg + cf,
					lastLineofText + "\n\tHigh: " + hiLow[0] + deg + cf + '\t'
							+ hiLow_s[0] + "\n\tLow:  " + hiLow[1] + deg + cf + '\t'
							+ hiLow_s[1], font);
		} else {
			tempLogHelp.setVisible(true);
		}
		return true;
	}
	
	private XyChart xychart = null;
	private int debug=2;
	private boolean	chart(){
		if(cdv.isEmpty()) new Help(" Empty ","No data to chart", font); // Validate there is something to process
		else			
			if(xychart == null ){			// in the future reuse may be possible
				System.out.println("new Lanuching in DyApplet version "+VERSION);
		
//		double d[] = {76,75,74,76,70,69,69,70,71};
//		double t[] = {1,2,3,5,5,6,7,8,9};
		//System.out.println("cdv="+cdv.toArray());
		//System.out.println("cdv="+cdv);
		double[] d = new double[cdv.size()];
		//double[] t = new double[cdv.size()];
		Date[] t = new Date[cdv.size()];
		String[] s = new String[cdv.size()];
		Calendar calendar = Calendar.getInstance();
		
//		int month = -1; int day = -1; int year = -1;  // Calendar.month starts with 0 for January
//		String monthstr = ""; // new SimpleDateFormat("MMM").format(calendar.getTime());
//System.out.println("Month = "+ monthstr);		
		calendar.setTime(cdv.get(0).t); // The time 
		TimeZone cdvtz = calendar.getTimeZone();	// The Local Time zone ?????
// TODO why am I using the local time zone?
		
			for (int i = 0; i < d.length; i++) { // collect the data
				d[i] = cdv.get(i).temp;			// the temperature reading
				calendar.setTime(cdv.get(i).t); // The time of reading
				t[i] = new Date((calendar.getTimeInMillis()));
				s[i] = new SimpleDateFormat("YYYY MMM dd hh:mm").format(calendar.getTime());
				
//				if (year != calendar.get( Calendar.YEAR )) {					// New year
//					// Update values
//					year = calendar.get( Calendar.YEAR );
//					month = calendar.get( Calendar.MONTH );
//					monthstr = new SimpleDateFormat("MMM").format(calendar.getTime());
//					day = calendar.get( Calendar.DAY_OF_MONTH );
//					// add data to X axis hh:mm day MMM YYYY
//					t[i] = (calendar.getTimeInMillis());
////					t[i] = (calendar.getTimeInMillis()+" "+calendar.get( Calendar.HOUR_OF_DAY ) + ":" +
////							calendar.get( Calendar.MINUTE ) + " " +
////							day + " " +
////							monthstr + " " +
////							year );
//				} else if (month != calendar.get( Calendar.MONTH )) {			// New month
//					// Update values
//					month = calendar.get( Calendar.MONTH );
//					monthstr = new SimpleDateFormat("MMM").format(calendar.getTime());
//					day = calendar.get( Calendar.DAY_OF_MONTH );
//					// add data to X axis hh:mm day MMM
//					t[i] = (calendar.getTimeInMillis());
////					t[i] = (calendar.getTimeInMillis()+" " + calendar.get( Calendar.HOUR_OF_DAY ) + ":" +
////						    calendar.get( Calendar.MINUTE ) + " " +
////							day + " " +
////						    monthstr);
//				} else if (day != calendar.get( Calendar.DAY_OF_MONTH )) {	// New Day
//					// Update value
//					day = calendar.get( Calendar.DAY_OF_MONTH );
//					// add data to X axis hh:mm day
//					t[i] = (calendar.getTimeInMillis());
////					t[i] = (calendar.getTimeInMillis()+" " +calendar.get( Calendar.HOUR_OF_DAY ) + ":" +
////						    calendar.get( Calendar.MINUTE ) + " " +
////							day);
//				} else {  // Time changed only
//					// Update not needed
//					// add data to X axis hh:mm
//					t[i] = (calendar.getTimeInMillis());
////					t[i] = (calendar.getTimeInMillis()+" " +calendar.get( Calendar.HOUR_OF_DAY ) + ":" +
////				            calendar.get( Calendar.MINUTE ));
//				}
if(debug==3) System.out.println(i+" = "+t[i]+ "\t" + s[i] + "\t"+d[i]+ "\t" + cdvtz.getDisplayName());			
//			t[i] = ((calendar.get(Calendar.HOUR_OF_DAY)) +":"+ (calendar.get(Calendar.MINUTE)));
//			t[i] = ((calendar.get(Calendar.HOUR_OF_DAY)) + (calendar.get(Calendar.MINUTE)/10));
//			t[i] = ((calendar.get(Calendar.HOUR_OF_DAY)*100) + calendar.get(Calendar.MINUTE));
			//t[i] = ((cdv.get(i).t.getHours())*100)+cdv.get(i).t.getMinutes();
//			
//			calendar.setTime(yourdate);
//			int hours = calendar.get(Calendar.HOUR_OF_DAY);

		} // All data is collected
		//for(int i = 0; i < cdv.size(); i++){
			// Chrtdata cd = cdv.get(i);
			//double t= cdv.get(i).temp;
			//String d = cdv.get(i).t.toString();
			
		//	xychart.add(cdv.get(i).temp,cdv.get(i).t);
		//}
		SysPropTest spt = new SysPropTest();	// needed to check Java Version
//		// String version = spt.getJavaVersion();
//			//System.out.println("String " + spt.checkJavaVersion("1.2"));
//			//System.out.println("Double " + spt.checkJavaVersion(8.4));
//			//System.out.println("Ints   " + spt.checkJavaVersion(8,6));
		double minJver = 7.6;
		if(spt.checkJavaVersion(minJver))
		try{ // launch the app
			char cf ='F';
			if(celsius==true) cf = 'C';
			xychart=new XyChart();
			xychart.initXyChart(t , d , s , cdvtz.getDisplayName(),"Time "+tzdf.getDisplayName(tzdf.observesDaylightTime(),TimeZone.SHORT), "Temperature    "+ cf);
							// Temperature, time in ms, Date Time String and timezone string
			xychart.init();
			System.out.println("XyChart initialized");
			xychart.start();
			System.out.println("XyChart done");
		    xychart.stop();
			System.out.println("XyChart stopped");
		    // xychart=null;	//destroy
		} catch(Exception e){
			new Help("XyChart trap", e.toString(),Color.RED);
		}
		else
			new Help("  Requires Java Run Time v  "+ minJver, " You have java "+ spt.getJavaVersion().charAt(2) +"." +  spt.getJavaVersion().charAt(4), Color.YELLOW, font);
//
//				
	} else {
		new Help("Second runs for XYChart not working yet", Color.YELLOW, font);
		System.out.println("Second run not on XYChart not ready yet");
//		xychart.start();
//		try{
//			xychart.stop();
//		}catch(Exception e){
//			//
//		}
//		xychart = null;
	}
	
	return true;
	}
	
	// Click on Last Update Time
	public void ludtMousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1: {
			//System.out.println("Button 1");
			history();
			break;
		}
		case MouseEvent.BUTTON2: // fall through
		case MouseEvent.BUTTON3: {
			chart();
			break;
		}
		default: {
//			System.out.println("default " + e.getButton());
		}
		}
		e.consume();
	}
	
//	public boolean CheckJavaVersion(String v){
//// @TODO fix to use input and move to utils
//		String version = getJavaVersion();
//		char major = version.charAt(2);
//		char minor = version.charAt(4);		
//
//System.out.println("Version is "+ version + " minor7 = "+major+" point6 = "+minor);		
//
//		if(major > '7') return true;  // greater than base version all is good were done
//			if(major < '7') return false;	// Less than base number were also done
//			if(minor < '6') return false;
//			return true;	// == major and => minor
//	}
//	
//	public String getJavaVersion(){
//		return System.getProperty("java.version");
//	}

	// Click on Last Update Time
	public void anywhereMousePressed(MouseEvent e) {
		//new Help("Button pressed", e.toString());
		//System.out.println("AnyWhere " + e);
		switch (e.getButton()) {
		case MouseEvent.BUTTON3:	// fall through
			if(e.getClickCount() > 1) selMenu(); // Macbook and FireFox converts cntrl+click to Button3 
		case MouseEvent.BUTTON2:{	// Left button pressed
			//System.out.println("Button 2 or 3");
			//System.out.println("Button "+e.getButton());
			settingInfo();
		}
			break;

		case MouseEvent.BUTTON1: {
			//System.out.println("Button "+e);
			//System.out.println("Button 1");
			if(e.isControlDown()){	// Control Click Apple Click on MAC
					selMenu();
				}
			else if(e.isAltDown()){ 	// Alt Click
				systemInfo();
			}
			else if(e.getClickCount() < 1)
				selMenu();
			else {
				info();
			}
		}
			break;
			
		default:
			//System.out.println("Unknown MouseEvent");
		}
	 e.consume();
	}

	public void mouseClicked(MouseEvent e) {
//	public void mousePressed(MouseEvent e) {
//	System.out.println("mousePressed "+e.getComponent().getName());
		// instruction "?"
		if (e.getComponent().getName() == instruction.getName()) {
			instructions(); //instructionsMousePressed(e);
		} else {
			// Last Update Time
			if (e.getComponent().getName() == lastUpDateTime.getName()) {
				ludtMousePressed(e);
			} else {
				// Anywhere
				anywhereMousePressed(e);
			}
		}
		e.consume();
	}
	
/*
  	public void mousePressed(MouseEvent e) {
		
		// Ctrl, Alt Anywhere
		if (e.isControlDown()) {
			if (e.isAltDown()) { // Ctrl/Alt + click
				systemInfo();
			} else { // Ctrl + click
				if (SelMenu == null) // || !SelMenu.isEnabled())
					SelMenu = new SelectionMenu(); // selectColorSelection();
				else {
					SelMenu.setVisible(true);
				}
			}
			//reFreshDisplay();
			e.consume();
			return;
		}

		// Right button or Button 3 Anywhere
		if (e.getButton() == MouseEvent.BUTTON2
				|| e.getButton() == MouseEvent.BUTTON3) {
//			if (e.getComponent().getName() == lastUpDateTime.getName()) {
//				reFreshDisplay(); // Click was on LastUpdateTime label
//
//			} else {
				settingInfo();
//			}
//			reFreshDisplay();
			e.consume();
			return;
		}

		// Check for the annoying events from the Panel at components location
		if (contains(instruction, e) || contains(lastUpDateTime, e)) {
			// System.out.println("Throw Away " + e.getComponent().getName());
			// Throw away both false clicks
			reFreshDisplay();
			e.consume();
			return;
		}

		// Check if click was on the request for Instructions the '?' Label
		if (e.getComponent().getName() == instruction.getName()) {
			// System.out.println("\tInfo Menu");
			if (null == inst) {
				inst = new Instructions();
			} else {
				inst.setVisible(true);
			}
			//reFreshDisplay();
			e.consume();
			return;
		}

		// Check if event is from LastUpDateTime
		if (e.getComponent().getName() == lastUpDateTime.getName()) {
			new Help("Click on date",e.toString());
			// System.out.println("\tlastUpDateTime");
			if (tempLogHelp == null || tempLogHelp.d == null) {
				tempLogHelp = new Help("Log " + temperatureLog[0] + "�, "
						+ temperatureLog[1] + "�, " + temperatureLog[2] + "�",
						lastLineofText + "\n\tHigh: " + hiLow[0] + "� "
								+ hiLow_s[0] + "\n\tLow:  " + hiLow[1] + "� "
								+ hiLow_s[1]);
			} else {
				tempLogHelp.setVisible(true);
			}
			//reFreshDisplay();
			e.consume();
			return;
		}

		// All other events (The Panel)
		// System.out.println("mousePressed " + e.getComponent().getName());
		info();
		reFreshDisplay();
		e.consume();
		return;
	}		
*/	
  	
  	
	/*
	 * For some reason using the mouse Press or Click on a Component triggers
	 * two events one on the Component and one on the Panel.
	 * 
	 * Checking the event's COmponent ID works for the first event the Second
	 * event has the Panels ID causing that event to be run.
	 * 
	 */
	@SuppressWarnings("unused")		// required by Mouselistener
	private boolean contains( Component c, MouseEvent e){
		return c.contains(e.getX()-c.getX(),e.getY()-c.getY());
	}
	
	
//
//						// if (infoHelp == null || infoHelp.d == null) {
//						// infoHelp = new Help(" Info: ThreeDigitDy " + sensor,
//						// getAppletInfo(),new Color(0,255,255,220));
//						// // getAppletInfo());
//						// } else {
//						// infoHelp.setVisible(true);
//						// }
//						// if (configHelp == null || configHelp.d == null) {
//						// configHelp = new Help(" User configurable settings
//						// Sensor("
//						// + sensor + ")", userSettingsInfo());
//						// } else {
//						// configHelp.setVisible(true);
//						// }
//						// // setMyCookies(); // For testing
//
	
	public void info() {
		// ToDo modify help to allow centering of the text.
		try {
			if (infoHelp == null || infoHelp.d == null) {

				infoHelp = new Help(" Info: ThreeDigitDy Sensor " + sensor,
						getAppletInfo(), bgInfoColor, font);
			} else {
				infoHelp.setVisible(true);
			}
		} catch (Exception e) {
			System.out.println("Exception from info()");
			new Help(" Exception Dy info() ", e.toString());
		}
	}
	
	public void systemInfo() {
		// System.out.println("Hello from "+this.getName()+" systemInfo()");

		try { // Security exception when running as an applet
			java.util.Properties p = System.getProperties();
			java.util.Enumeration<?> k = p.keys();
			java.util.Enumeration<?> e = p.elements();

			int i = 0;
			StringBuffer s = new StringBuffer();
			while (e.hasMoreElements() && k.hasMoreElements()) {
				s.append(i++ + "\t" + k.nextElement().toString() + ": \t"
						+ e.nextElement().toString() + "\n");
				// System.out.println("("+i++
				// +")
				// "+k.nextElement().toString()+"\t"+e.nextElement().toString());
				// if(i==18)try{
				// Thread.sleep(15000);
				// } catch (InterruptedException Ie){}
			}
			new Help(" System info ", s.toString(), font);
		} catch (java.security.AccessControlException e) {
			new Help("Java Security Access Exception", "\tSystem Properities \n"
					+ "\tPermission Denied", Color.ORANGE);
		}

		/*
		 * SysPropTest sysInfo = new SysPropTest(); String[] str = new
		 * String[sysInfo.getSysInfoStrings().length +
		 * sysInfo.getJavaInfoStrings().length+5]; int j = 0;
		 * 
		 * 
		 * str[j++] = new String("\t\t"+Help.VERSION);
		 * 
		 * str[j++] = new String(""); str[j++] = new String("\t-------------
		 * Local System Information -------------");
		 * 
		 * for (int i = 0; i < sysInfo.getSysInfoStrings().length; i++) {
		 * str[j++] = new String("\t\t" + sysInfo.getSysInfoStrings()[i]); }
		 * 
		 * str[j++] = new String(""); str[j++] = new String(
		 * "\t----------------- Java Information -----------------");
		 * 
		 * for (int i = 0; i < sysInfo.getJavaInfoStrings().length; i++)
		 * str[j++] = new String("\t\t" + sysInfo.getJavaInfoStrings()[i]);
		 * 
		 * new Help(" Local System info ", str, bgInfoColor);
		 */
	}
 
	public void settingInfo() {
		if (configHelp == null || configHelp.d == null) {
			configHelp = new Help(" User configurable settings Sensor("
					+ sensor + ")", userSettingsInfo(), bgInfoColor, font);
		} else {
			configHelp.setVisible(true);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		// Not used, Required by mouse listener
		e.consume();
	}

	private boolean running = true;
	private Thread Dythread = null;

	@SuppressWarnings("static-access")
	public void run() {
		// System.out.println("Hello from "+this.getName()+" Run()");

		while (running) {
			reFreshDisplay();
			try {
				Dythread.sleep(refresh_interval);
			} catch (InterruptedException e) {
				running = false;
				// e.printStackTrace();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.applet.Applet#destroy()
	 */
	@Override
	public void destroy() {
	// System.out.println("Hello from "+this.getName()+" Destroy()");
		running = false;
		lastUpDateTime.removeMouseListener(this);
		applet.remove(lastUpDateTime);
		applet.removeMouseListener(this);
		Dythread = null;		
		lastUpDateTime = null;
		//super.destroy();
		applet.destroy();
	}

	private class getLedOnColor implements SelectColorInterface {
		private ColorSelection CS;

		public getLedOnColor() {
			if (CS != null)
				CS.stop();
			CS = new ColorSelection(ledOnColor, this, "Led");

		}

		@Override
		public void setNewColor(Color c) {
			if(!c.equals(ledOnColor)){
				ledOnColor = c;
				fcOnColor = c;

				// setCookie(ledOnColor_str,Integer.toHexString(ledOnColor.getRGB()));
				// setCookie(fcOnColor_str,Integer.toHexString(fcOnColor.getRGB()));
				// setCookie(ledOnColor_str,ledOnColor);
				// setCookie(fcOnColor_str,fcOnColor);
				// setColors();
				ckmgr.gatherAndSaveCookieString();
				setDisplays();
			}
			reFreshDisplay();
		}

	}
	
	private class getLedOffColor implements SelectColorInterface {
		private ColorSelection CS;

		public getLedOffColor() {
			// if(ledOffColor == null) ledOffColor = ledColor.darker(); // the
			// default
			if (CS != null)
				CS.stop();

			CS = new ColorSelection(ledOffColor, this, ledOffColor_str);

		}

		@Override
		public void setNewColor(Color c) {
			if(!c.equals(ledOffColor)){
			ledOffColor = c;
			fcLedOffColor = c;
			// setCookie(ledOffColor_str,ledOffColor.toString());
			// setCookie(ledOffColor_str,
			// Integer.toHexString(ledOffColor.getRGB()));
			// setCookie(fcOffColor_str,
			// Integer.toHexString(fcOffColor.getRGB()));
			// setCookie(ledOffColor_str,ledOffColor);
			// setCookie(fcOffColor_str, fcOffColor);
			// setColors();
			ckmgr.gatherAndSaveCookieString();
			setDisplays();
		}
			reFreshDisplay();

		}

	}

	private class getBgColor implements SelectColorInterface {
		private ColorSelection CS;

		public getBgColor() {
			if (CS != null)
				CS.stop();
			CS = new ColorSelection(bgColor, this, "Background");
		}

		@Override
		public void setNewColor(Color c) {
			if(c == null) System.out.println("Color is NULL !!!!");
			if(!c.equals(bgColor)){
				bgColor = c;

				// setCookie(bgColor_str, Integer.toHexString(c.getRGB()));
				// setCookie(bgColor_str, Integer.toHexString(bgColor.getRGB()));
				// setCookie(bgColor_str, bgColor);

				// setColors();
				ckmgr.gatherAndSaveCookieString();
				setDisplays();
			}
			reFreshDisplay();
		}

	}
	
	
	class getTimeZone extends TimeZoneSelection implements SelectTimeZoneInterface {
		private static final long serialVersionUID = 11121L;
			
	
		public getTimeZone(TimeZone tz) {
			super(tz);
		}
		
		@Override
		public void setNewTimeZone(TimeZone tz) {
			// System.out.println("Hello from overridden setNewTimeZone("+tz.getID()+")");
			tzdf = tz;							// this is where the local var is updated
			digi_Temp_TZ = tzdf.getID();		// String for the cookie and display
			ckmgr.gatherAndSaveCookieString();	// Store the settings
			setDisplays();
			reFreshDisplay();		
		}
		
	}
	
	private class getFonts extends SelectFont implements SelectFontInterface {
		
		public getFonts(Font f) {
			super(f);
		}
		
		@Override
		public void setNewFont(Font f){
// System.out.println("font was   \t"+ font.toString());
			if(f != null) font = f;		// SelectFont returns the new Font, old Font or Null
// System.out.println("font is now\t"+ font.toString());
			ckmgr.gatherAndSaveCookieString();
			// setDisplays();
			reFreshDisplay();
		}
	}


//	/**
//	 * 
//	 * 
//	 */
//	private class YInstructions extends Frame implements WindowListener,
//			MouseListener, ItemListener {
//		
//		private static final long serialVersionUID = 8078522486936794662L;
//
//		private Panel p = null;
//		private Checkbox hide = null;
//		private Button close = null;
//		private TextArea ta = null;
//		
//		
//		public YInstructions(){
//			this.setTitle("\"User instructions\"");
//			p = new Panel();
//			hide = new Checkbox("Do not show again");
//			
//			close = new Button("Close");
//			
//			addWindowListener(this);
//			
//			ta = new TextArea(
//					" Mouse and Key combinations\n"
//							+ " \t- Control:     \tSettings menu\n"
//							+ " \t- Control+Alt: \tSystem info (With privileged access only)\n"
//							+ " \t- Right Button:\tDisplay user settings\n"
//							+ " \t- Left Button: \tinfo\n"
//							+ "\n Right click or Hoover over the Date for temperature history\n",
//					0, 0, TextArea.SCROLLBARS_NONE); 
//			
//			ta.setEditable(false);
//			setBackground(Color.WHITE);
//			p.setBackground(getBackground());
//			ta.setBackground(p.getBackground());
//
//			hide.setState(!info);
//			if(info) hide.setBackground(Color.BLUE); else hide.setBackground(p.getBackground());
//			
//			p.add(ta);
//					
//
//		
////			p.add( new Label(
////					" Mouse and Key combinations"+crlf+
////					" \t- Control:\t\tSettings menu"+crlf+
////					" \t- Control+Alt:\tSystem info"+crlf+
////					" \t- Right Button:\tDisplay user settings"+crlf+
////					" \t- Left Button:\tinfo"+crlf+
////					"\n Right click or Hoover over the Date for temperature history"+crlf
////						)); 
////			
//			p.add(hide);
//			p.add(close);
//			close.addMouseListener(this);
//
//			this.add(p);
//			this.setSize(575, 250);
//			//this.setSize(480, 250);
//			
//			this.setVisible(true);
//			setEnabled(true);
//		}
//
//		
//		public void dispose(){
//			p.setVisible(false);
//			
//			if(hide.getState() == info){	// Update cookies if needed
//				info = !(hide.getState());
//				if(null != ckmgr) ckmgr.gatherAndSaveCookieString();
//				else System.out.println("Closing Instruction ckmgr Not initialized");
//			}
//			
//			setEnabled(false);
//			removeWindowListener(this);
//			p.removeAll();
//			removeAll();
//			//removeMouseListener(this);
//			super.dispose();
//		}
//		public void windowActivated(WindowEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public void windowClosed(WindowEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public void windowClosing(WindowEvent e) {
//			dispose();
//			
//		}
//
//		public void windowDeactivated(WindowEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public void windowDeiconified(WindowEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public void windowIconified(WindowEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public void windowOpened(WindowEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public void mouseClicked(MouseEvent e) {			
//		}
//
//		public void mouseEntered(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public void mouseExited(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public void mousePressed(MouseEvent e) {
//			if(e.getSource()==close){
//				dispose();
//			}
//			
//		}
//
//		public void mouseReleased(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		public void itemStateChanged(ItemEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}

	private Instructions inst = null;
	private boolean instructions(){
		if (null == inst) {
			inst = new Instructions();
		} else {
			inst.setVisible(true);
		}
		return true;
	}

	/**
	 *	Info on hot keys 
	 *	Hot links require access to the inclosing class
	 *		selMenu()
	 *		systemInfo()
	 *		settingInfo()
	 *		info()
	 *		history()
	 * 
	 */
	private class Instructions extends Frame implements WindowListener,
			MouseListener, ItemListener {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 4239400725281445510L;
		
		private Panel p = null;
		private Checkbox hide = null;
		private Button close = null;
		Label lh1,lh2,l0,l1,l2,l3,l4,l5,l6,l7,lf1,lf2;
		
		private Label getLabel(String s){
			Label l = new Label(s);
			l.setAlignment(Label.LEFT);
			//l.setSize(50, 0);
			//l.setBackground(Color.WHITE);
			l.addMouseListener(this);
			return l;
		}
		
		public Instructions() {
			// System.out.println("Hello from "+this.getName()+" Instructions()");
			this.setTitle("\"User instructions\"");
			init();

			if (isAlwaysOnTopSupported())
				try { // Security exceptin when running as an applet in SandBox
					//this.setAlwaysOnTop(true);
					toFront();
				} catch (java.security.AccessControlException e) {
//					new Help("Java Security Access Exception",
//							"\tSet Always On Top\n" + "\tPermission Denied\n"
//									+ e, Color.ORANGE);
					// Throw away
				}
			this.setVisible(true);
		}
		
		public void init(){
			// System.out.println("Hello from "+this.getName()+" init() and firstrun="+firstrun);
			// this.setSize(460, 200);
			this.setSize(font.getSize()*40, font.getSize()*15);

//			GridBagConstraints gbc = new GridBagConstraints();
//			gbc.fill = GridBagConstraints.BOTH;
//			p = new Panel(new GridBagLayout());

			p = new Panel();
			p.setFont(font);
			p.setLayout(new GridLayout(0, 2, 0, 0));
			hide = new Checkbox("Do not show again");
			
			close = new Button("Close");
			
			addWindowListener(this);
			
			
			lh1 = new Label(" Mouse and Key ");
			lh1.setAlignment(Label.RIGHT); lh1.setBackground(Color.YELLOW);// lh1.setSize(450,0);
			lh2 = new Label("combinations ");
			lh2.setAlignment(Label.LEFT); lh2.setBackground(Color.YELLOW);

			l0 = getLabel(" - Control Click:");
			l1 = getLabel("Settings menu");
			l2 = getLabel(" - Alt Click:");
			l3 = getLabel("System info (privileged access only)");
			l4 = getLabel(" - Right Button:");
			l5 = getLabel("Display user settings");
			l6 = getLabel(" - Left Button:");
			l7 = getLabel("info");
			lf1 = getLabel(" - Right click or Hoover over Date");
			lf2 = getLabel("Display temperature history ");

			setBackground(Color.WHITE);
			p.setBackground(getBackground());

			hide.setState(!info);
			if(info) hide.setBackground(Color.CYAN); else hide.setBackground(p.getBackground());
								
			p.add(lh1);	p.add(lh2);
			p.add(l0);	p.add(l1);
			p.add(l2);	p.add(l3);
			p.add(l4);	p.add(l5);
			p.add(l6);	p.add(l7);
			p.add(lf1);	p.add(lf2);

		
			p.add(hide); p.add(close);
			close.addMouseListener(this);

			this.add(p);
			
			this.setVisible(true);
			setEnabled(true);
		}
		
		public void dispose(){
			// System.out.println("Hello from "+this.getName()+" dispose() Size = "+p.getSize());
			
			p.setVisible(false);
			
			if(hide.getState() == info){	// Update cookies if needed
				info = !(hide.getState());
				if(null != ckmgr) ckmgr.gatherAndSaveCookieString();
				// else System.out.println("Closing Instruction ckmgr Not initialized");
			}
			
			setEnabled(false);
			removeWindowListener(this);
			p.removeAll();
			removeAll();
			inst = null;
			//removeMouseListener(this);
			super.dispose();
		}
		
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void windowClosing(WindowEvent e) {
			dispose();
			
		}

		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() >= 2) {
				if (e.getComponent() == l0 || e.getComponent() == l1) { // Settings Menu
					selMenu();
				} else if (e.getComponent() == l2 || e.getComponent() == l3) { // System Info
					systemInfo();
				} else if (e.getComponent() == l4 || e.getComponent() == l5) { // Display User Settings
					settingInfo();
				} else if (e.getComponent() == l6 || e.getComponent() == l7) { // Info
					info();
				} else if (e.getComponent() == lf1 || e.getComponent() == lf2) { // History
					history();
				} else
					System.out.println(e);					// Unused Should never be seen
			}
		}

		public void mouseEntered(MouseEvent e) {
			//System.out.println("MouseEntered "+e);
//			if(		e.getComponent().getName()==l0.getName())
//				if(h==null)
//					h = new Help(l0.getText(),"Description for this option\nSecond line if needed\nThird line if needed",bgInfoColor);
//			h = new Help(e.getComponent().getName(),((Label)e.getComponent()).getText() );
//			if(		e.getComponent().getName()==l0.getName()) System.out.println("Enter Label L1");
//			else if(e.getComponent().getName()==l1.getName()) System.out.println("Enter Label L2");
//			else if(e.getComponent().getName()==l2.getName()) System.out.println("Enter Label L3");
//			else if(e.getComponent().getName()==l3.getName()) System.out.println("Enter Label L4");
//			else if(e.getComponent().getName()==l4.getName()) System.out.println("Enter Label L5");
//			else if(e.getComponent().getName()==l5.getName()) System.out.println("Enter Label L6");
//			else if(e.getComponent().getName()==l6.getName()) System.out.println("Enter Label L7");
//			else if(e.getComponent().getName()==l7.getName()) System.out.println("Enter Label L8");
//			else if(e.getComponent().getName()==lf1.getName()) System.out.println("Enter Label Lf1");
//			else if(e.getComponent().getName()==lf2.getName()) System.out.println("Enter Label Lf2");
//			else System.out.println("Enter Unknown ");
			//if(e.getSource()==l0.getName()) System.out.println("Label L9");
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
//				if(h != null){
//					h.dispose();
//					h=null;
//				}
			

//			if(		e.getComponent().getName()==l0.getName()) System.out.println("Exit Label L1");
//			else if(e.getComponent().getName()==l1.getName()) System.out.println("Exit Label L2");
//			else if(e.getComponent().getName()==l2.getName()) System.out.println("Exit Label L3");
//			else if(e.getComponent().getName()==l3.getName()) System.out.println("Exit Label L4");
//			else if(e.getComponent().getName()==l4.getName()) System.out.println("Exit Label L5");
//			else if(e.getComponent().getName()==l5.getName()) System.out.println("Exit Label L6");
//			else if(e.getComponent().getName()==l6.getName()) System.out.println("Exit Label L7");
//			else if(e.getComponent().getName()==l7.getName()) System.out.println("Exit Label L8");
//			else if(e.getComponent().getName()==lf1.getName()) System.out.println("Exit Label Lf1");
//			else if(e.getComponent().getName()==lf2.getName()) System.out.println("Exit Label Lf2");
//			else System.out.println("Exit Unknown ");
			
		}

		public void mousePressed(MouseEvent e) {
			if(e.getSource()==close){
				dispose();
			}
			
		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	/**
	 * 
	 */
	private class SelectionMenu extends Frame implements WindowListener,
			MouseListener, ItemListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7976604245491636990L;
		// Panel p;
		CheckboxGroup radioGroup = null;
		Checkbox enh;	// Enhanced LED
		Checkbox fnt;	// Fonts
		Checkbox bbg;	// Back Ground Color
		Checkbox loc;	// Led On Color
		Checkbox loffc;	// Led Off Color
		Checkbox cf;	// Celsius / fahrenheit 
		Checkbox alrt;	// Enable Audio alart
		Checkbox auto;	// Auto Color
		Button closeb;	// Close button
		Button apply;	// Apply button
		TextField tf;	// Temperature Threshold Field
		Checkbox dtf;	// Select Temperature File
		Checkbox dinfo;	// Start up help Info display
		TextField exp;	// Out of Date
		Checkbox tzcb;	// Time Zone
		Panel p = null;	

		public SelectionMenu() {
			init();
		}
		
		private void init(){
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;

			p = new Panel(new GridBagLayout());

			// p = new Panel();
			// using user difined fonts is disabled for now need to setup a better layout for changing fonts.
			p.setFont(font);
			this.setSize(font.getSize()*40, font.getSize()*15);
			//this.setSize(400, 150);

			// radioGroup = new CheckboxGroup();
			bbg = new Checkbox(bgColor_str, radioGroup, false);
			bbg.setBackground(bgColor);
			bbg.setEnabled(!auto_color);

			loc = new Checkbox(ledOnColor_str, radioGroup, false);
			loc.setBackground(ledOnColor);
			loc.setEnabled(!auto_color);

			loffc = new Checkbox(ledOffColor_str, radioGroup, false);
			loffc.setBackground(ledOffColor);
			loffc.setEnabled(!auto_color);
			
			enh = new Checkbox(enhancedLED_str);
			enh.setState(enhancedLED);
			
			fnt = new Checkbox(font_str);

			cf = new Checkbox(celsius_str);
			cf.setState(celsius);
			cf.addItemListener(this);
			
			alrt = new Checkbox(alert_str);
			alrt.setState(alert);
			
			auto = new Checkbox(auto_color_str);
			auto.setState(auto_color);
			auto.addItemListener(this);
			
			if(celsius){
				tf = new TextField(Double.toString( toCelsius(alarmThreshold[0])) + "/"
					+ Double.toString( toCelsius(alarmThreshold[1])));

			} else {
				tf = new TextField("   "+Double.toString(alarmThreshold[0]) + "/"
					+ Double.toString(alarmThreshold[1])+" ");
			}
			//tf.setSize(new Dimension(92,22));
			// Don't work 

			closeb = new Button("Close");
			closeb.addMouseListener(this);

			apply = new Button("Apply");
			apply.addMouseListener(this);

			dtf = new Checkbox("Temp File");
			dtf.setState(false);
			
			//" Data Expires in "
			//" Minutes"
					
			exp = new TextField( Integer.toString(outofdate));
			
			tzcb = new Checkbox("Time Zone");
			tzcb.setState(false);
			
			dinfo = new Checkbox(info_str);
			dinfo.setState(info);

			// Auto Color
//			gbc.anchor = GridBagConstraints.PAGE_START;
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 0;			gbc.gridy = 0;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			p.add(auto,gbc);

			// Led On Color
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 1;			gbc.gridy = 0;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			p.add(loc,gbc);
			
			// Led Off Color
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 2;			gbc.gridy = 0;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			p.add(loffc,gbc);
			
			// Background color
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 3;			gbc.gridy = 0;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			p.add(bbg,gbc);

			/* ROW 2 */
			// Enhanced Led
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 0;			gbc.gridy = 1;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			p.add(enh,gbc);
			
			// C/F
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 1;			gbc.gridy = 1;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			p.add(cf,gbc);
			
			// High / Low
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 2;			gbc.gridy = 1;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			p.add(tf,gbc);

			// Alert audio
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 3;			gbc.gridy = 1;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			p.add(alrt,gbc);
			
			/* ROW 3 */
			// Font
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 0;			gbc.gridy = 2;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			p.add(fnt,gbc);
			
			// DiGiTemp File
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 1;			gbc.gridy = 2;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			p.add(dtf,gbc);

			// Data Expires (minutes)
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 2;			gbc.gridy = 2;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			p.add(exp,gbc);

			// Display Info at startup (Startup Help)
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 3;			gbc.gridy = 2;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			p.add(dinfo,gbc);
			
			/* ROW 4 */
			// Time Zone
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 0;			gbc.gridy = 3;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			p.add(tzcb,gbc);

			// Apply
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 2;			gbc.gridy = 3;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			p.add(apply,gbc);
			
			// Cancel (Close)
			gbc.anchor = GridBagConstraints.PAGE_END;
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 3;			gbc.gridy = 3;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			p.add(closeb,gbc);

			//			p.add(loc);
			//			p.add(loffc);
			//			p.add(bbg);
			//			p.add(enh);
			//			p.add(fnt);
			//			p.add(cf);
			//			p.add(alrt);
			//			p.add(auto);
			//			p.add(tf);
			//			p.add(apply);
			//			p.add(closeb);
			//			p.add(dtf);
			//			p.add(exp);
			//			p.add(dinfo);
			//			p.add(tzcb);

			
			super.addWindowListener(this);
			this.add(p);
			this.setVisible(true);
			setEnabled(true);
		}

		public void windowActivated(WindowEvent arg0) {
			// Not used, Required by Windowlistener
		}

		public void windowClosed(WindowEvent arg0) {
			// Not used, Required by Windowlistener

		}

		public void windowClosing(WindowEvent arg0) {
			dispose();
		}

		public void windowDeactivated(WindowEvent arg0) {
			// Not used, Required by Windowlistener
		}

		public void windowDeiconified(WindowEvent arg0) {
			// Not used, Required by Windowlistener
		}

		public void windowIconified(WindowEvent arg0) {
			// Not used, Required by Windowlistener
		}

		public void windowOpened(WindowEvent arg0) {
			// Not used, Required by Windowlistener
		}

		boolean invalid = false;	// Selection changed update cookies
									// the two toggle buttons refresh here
		public void mouseClicked(MouseEvent e) {

			if (e.getSource() == closeb) {
				setVisible(false); // hide quickly
				invalid=false;					// Don't save changes
				dispose();
			} else {
				if (e.getSource() == apply) {

					if (enhancedLED != enh.getState()) { // changed state?
						enhancedLED = enh.getState();
						invalid = true; // setBooleans();
					}

					if (fnt.getState()){
						fnt.setState(false);
						new getFonts(font);
						invalid = true;

					}

					if (cf.getState() != celsius) { // changed state?
						celsius = cf.getState();
						invalid = true;
					}

					if (alrt.getState() != alert) { // Alert state changed
						alert = alrt.getState();
						invalid = true;
					}

					if (auto.getState() != auto_color) { // auto_color state changed
						auto_color = auto.getState();
						invalid = true;
					}

					if (tzcb.getState()) {
					    new getTimeZone(tzdf);
					    tzcb.setState(false);
						invalid = true;
					}

					if(!auto_color){					// Don't change for auto
						if (loc.getState()) {			// System.out.println("Led ON");
							new getLedOnColor();
							loc.setState(false);
							invalid = true;
						}
						
						if (loffc.getState()) {
							new getLedOffColor();
							loffc.setState(false);
							invalid = true;
						}

						if (bbg.getState()) {
							new getBgColor();
							bbg.setState(false);
							invalid = true;
						}
						
					}

					// Find Digitemp File
					if (dtf.getState()) {
						String temp = findFile(digiTempFile);
						if(temp != null){
							digiTempFile = temp;
							dtf.setState(false);
						}
					}

					if (dinfo.getState() != info) {
						info = dinfo.getState();
					}

					// Over and Under Limts.
					try {
						String temp = tf.getText();
						if (temp == null || temp.length() < 5) {
							; // invalid forget it
						} else {
							int index = temp.indexOf('/');
							if (index > 0) {
								double h = Double.parseDouble(temp.substring(0,
										temp.indexOf('/')));
								double l = Double.parseDouble(temp.substring(
										temp.indexOf('/') + 1, temp.length()));
								if (celsius) { // conversion required
									h = toFahrenheit(h);
									l = toFahrenheit(l);
								}
								alarmThreshold[0] =  h;
								alarmThreshold[1] =  l;
							}
						}
					} catch (Exception e1) {
						// Throw away
					}
					
					// Time to expire
					try{
						if(exp != null){
							int tmp = Integer.parseInt(exp.getText());
							outofdate = tmp; // Not executed if parseInt traps
						} else System.out.println("exp is NULL");
					} catch (Exception e2){
						//new Help("Out of date exception", e.toString());
						//Invalid data Throw away
					}
					// RBBC if( tf.getText().length() > 4 &&
					// tf.getText().contains("/") )
					// setCookie(alarmThreshold_str,
					// tf.getText());
					// alarmThreshold = parseHTML(alarmThreshold_str,
					// alarmThreshold);
					dispose(); // Save changes and close
				}
			}
		}


		public void dispose() {
			if (invalid) {
				//invalid=false;
				ckmgr.gatherAndSaveCookieString();
				reFreshDisplay();
			}
			
			setEnabled(false);
			this.removeAll();
			p.removeAll();
			SelMenu=null;		// Remove outer reverence to this object
			super.dispose();
		}

		public void mouseEntered(MouseEvent arg0) {
			// Not used, Required by Mouselistener
		}

		public void mouseExited(MouseEvent arg0) {
			// Not used, Required by Mouselistener

		}

		public void mousePressed(MouseEvent arg0) {
			// Not used, Required by Mouselistener

		}

		public void mouseReleased(MouseEvent arg0) {
			// Not used, Required by Mouselistener

		}

		/*
		 * 
		 * These are events that should change in real time before "Apply" or "Close" is pressed
		 *  
		 */
		public void itemStateChanged(ItemEvent e) {
			// Note: checkbox state does not change until mouse exits the 
			// checkbox field
			if (e.getSource() == auto) {				// should enable or disable color selections
					loc.setEnabled(!auto.getState());
					bbg.setEnabled(!auto.getState());
					loffc.setEnabled(!auto.getState());
					invalid = true;
			} 

			if (e.getSource() == cf) {					// User should see update to change thresholds 
				if(cf.getState()){ // Celsius
					tf.setText(Double.toString(toCelsius(alarmThreshold[0])) + "/"
						+ Double.toString(toCelsius(alarmThreshold[1])));

				} else {	// Fahrenheit
					tf.setText(Double.toString(alarmThreshold[0]) + "/"
						+ Double.toString(alarmThreshold[1]));
				}
				tf.setSize(new Dimension(92,22));
			}
			
//			if (e.getSource() == tzcb){
//				System.out.println("itemStateChanged("+e);
//			}
						
		}
		
		/**
		 * 
		 * 				File browse select and return a file name and path
		 * 
		 */		
		 private String findFile(String fileName){
			 String newFile = fileName;
			 	Frame fm = new Frame();
			 try{
				 //			 java.net.URI u = java.net.URI.create(fileName);			 
				 //new Help("URI",u.toString());
				 //	 java.io.File f = new java.io.File(u.toString());
				 java.io.File f = new java.io.File(fileName);

				 java.awt.FileDialog fd = new java.awt.FileDialog(fm,f.getPath());
				 fd.setVisible(true);
			 
				 if(fd.getFile() != null){
					 newFile = fd.getDirectory()+fd.getFile().toString();
				 }
				 else{
					 new Help("Returned Null", fileName, font);
				 }
				 fd.dispose();
			 } catch(Exception e){
				 new Help("URI Exception", e.toString());
			 }
			 fm.dispose();			 

			 //fd.setDirectory("/Users");
			 //fd.setFile(f.getName());
			 
			 return newFile;
		 }


	} // SelectionMenu

	/**
	 * 
	 * @author vicwmac
	 * 
	 *         Pop-up message that is able to survive the HTML page refresh.
	 *         Sets a cookie that the calling applet can check for then
	 *         re-create this message window.
	 * 
	 *         The cookie is distroyed when the dismiss button is pressed or
	 *         window close is selected on the frame.
	 * 
	 *         Note: The screen location and size is not saved.
	 * 
	 * ToDo 08/2013
	 *    Add an acknowledge button to ingnore future messages until recovered
	 *    Play an alert sound message. 
	 *    Save location of PU message
	 * 
	 */
	private class TemperatureAlert implements java.awt.event.WindowListener {

		private String m_id = null;
		private String m_event = null;
		private String m_time = null;
		private Color m_c = null;
		private Help h = null;

		TemperatureAlert(String id, String e_time, String event, Color c) {
			m_id = id;
			m_event = event;
			m_time = e_time;
			m_c = c;
			ckmgr.setSessionCookie(
					m_id,
					m_event + "," + m_time + ","
							+ Integer.toHexString(m_c.getRGB()));
			init();
		}

		/**
		 * 
		 * @param id
		 * @param message
		 * 
		 *            Can be used to restore the Alert message from a easly
		 *            stored string Note: The 3 indivual fields are sepereated
		 *            with comma charactures ',' in the order "event", "time",
		 *            "RGB hex values for Color"
		 * 
		 */
//		TemperatureAlert(String id, String message) {
//			if (message.length() < 12)
//				return;
//			m_id = id; // ID was the key and was not stored in the string
//
//			String[] s = separate(message, ','); // hard coded seperator
//			if (s.length == 3) { // must be 3 or something is wrong
//				m_event = s[0];
//				m_time = s[1];
//				m_c = colorDecode(s[2], Color.RED); // Convert RGB Hex values
//				init();
//			}
//		}

		private void init() {
			if (h == null) {
				h = new Help("*** WARNING " + m_time + " " + m_id + " ***",
						m_event, m_c, font);
				h.addWindowListener(this);
				if (alert) play();
			}
		}

		// ToDo parse sound file 		
		private void play(){
			try{
				// http://b3c-lc.com/b3c-lc.com/DigiTemp_files/WarningSiren.mp3

				URL url;
					if (alert_Audio_File.contains("://"))
						url = new URL(alert_Audio_File);
					else
						//url = new URL(getCodeBase().toString() + alert_Audio_File);
						url = new URL(applet.getCodeBase().toString() + alert_Audio_File);
				         AudioInputStream audioInputStream = javax.sound.sampled.AudioSystem.getAudioInputStream(url);
				        javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();//This plays the audio	
			} catch( Exception e ) {System.out.println(e);}
		}
		
		public boolean isAlive() {
			if (h != null) {
				// h.setVisible(true); // Became really, really
				// h.toFront(); // anoying on each refresh
				return (h.d != null);
			} else {
				return false;
			}
		}

		public void windowActivated(WindowEvent e) {
			// Unused needed for WindowListener
		}

		public void windowClosed(WindowEvent e) {
			h.removeWindowListener(this);
			h = null;
			// setSessionCookie(m_id,""); // remove cookie (almost)
			ckmgr.setCookie(m_id, "", 0, 0, -1, 0); // remove cookie 1 hour ago
		}

		public void windowClosing(WindowEvent e) {
			// Unused needed for WindowListener
		}

		public void windowDeactivated(WindowEvent e) {
			// Unused needed for WindowListener
		}

		public void windowDeiconified(WindowEvent e) {
			// Unused needed for WindowListener
		}

		public void windowIconified(WindowEvent e) {
			// Unused needed for WindowListener
		}

		public void windowOpened(WindowEvent e) {
			// Unused needed for WindowListener
		}

	}

	/*
	 * bgColor = parseHTML("Back_Ground_Color", bgColor); ledOnColor =
	 * parseHTML("Led_Color", ledOnColor); ledOffColor =
	 * parseHTML("Led_Off_Color", ledOffColor); FCOnColor = parseHTML("FCcolor",
	 * FCOnColor); no_bg = parseHTML("HideBackGround", no_bg); celsius =
	 * parseHTML("Celsius", celsius); enhancedLED = parseHTML("Enhanced",
	 * enhancedLED); sensor = parseHTML("sensor", sensor); refresh_interval =
	 * parseHTML("refresh_interval", refresh_interval); digitmpfile =
	 * parseHTML("Data_File", digitmpfile);
	 */
	// @SuppressWarnings("deprecation")
	// public void setMyCookies(){
	// try{
	// // Date date = new Date(); // ;expires=Thursday, 01-Jan-70 00:00:01 GMT
	// // date.setMinutes(date.getMinutes()+10);
	// // String datef = (date.toGMTString()); //
	// DateFormat.getDateInstance().format("GMT");
	//
	// JSObject.getWindow(this).eval("document.cookie ='" + "bgColor = " +
	// bgColor +"';"); //"; expires="+datef+"'");
	// JSObject.getWindow(this).eval("document.cookie ='" + "ledOnColor = " +
	// ledOnColor +"';");
	// JSObject.getWindow(this).eval("document.cookie ='" + "ledOffColor = " +
	// ledOffColor +"';");
	// JSObject.getWindow(this).eval("document.cookie ='" + "FCcolor = " +
	// FCOnColor +"';");
	// JSObject.getWindow(this).eval("document.cookie ='" + "HideBackGround = "
	// + no_bg +"';");
	// JSObject.getWindow(this).eval("document.cookie ='" + "Celsius = " +
	// celsius +"';");
	// JSObject.getWindow(this).eval("document.cookie ='" + "Enhanced = " +
	// enhancedLED +"';");
	// JSObject.getWindow(this).eval("document.cookie ='" + "sensor = " + sensor
	// +"';");
	// JSObject.getWindow(this).eval("document.cookie ='" +
	// "refresh_interval = " + refresh_interval +"';");
	// JSObject.getWindow(this).eval("document.cookie ='" + "Data_File = " +
	// digitmpfile +"';");
	// }catch(Exception e){
	// new Help(" SetCookie Exception ", e.getMessage());
	// }
	// }
	// private String escape(String value){
	// return value;
	// if(value == null) return value;
	//
	// StringBuffer sb = new StringBuffer();
	// for(int i=0; i< value.length(); i++){
	// switch(value.charAt(i)){
	// case '/':
	// sb.append('/'); // add an extra
	// break;
	// }
	// sb.append(value.charAt(i));
	// }
	//
	// return(sb.toString());
	// }
	//
	// private String unescape(String value){
	// return value;
	// if(value==null) return value;
	//
	// StringBuffer sb = new StringBuffer();
	//
	// for(int i=0; i<value.length(); i++){
	// if(value.charAt(i) == '/') i++; // remove the /
	// if(i < value.length()) sb.append(value.charAt(i));
	// }
	//
	//
	// return(sb.toString());
	// }
	
	// JSObject.getWindow does not accept "this" as a parameter from inner class 
	// this could be passed in a constructer. JIC using the methode keeps it current.
	private Applet gw(){
		return applet;
		//return this;
	}
	

	/*
	 * 
	 * Handeling of cookies was getting diffulcate now that I am handeling two
	 * verisons to prevent loss of data probably should externize
	 * this for simplicity just using an inner class for now.
	 *
	 * There is a problem with 2 versions running in same loaction corrupting each others cookies
	 * Don't do this with versions before 20120829
	 * 
	 */
	CookieManger ckmgr = null;
	private class CookieManger {
	
	private void setCookie(String name, String value, int years, int months,
			int hours, int seconds) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE, dd-MMM-yyyy HH:mm:ss 'GMT'");
		TimeZone tz = TimeZone.getTimeZone("GMT");
		tz.setID("GMT");
		sdf.setTimeZone(tz);

		java.util.Calendar c = java.util.Calendar.getInstance();
		// c.add(java.util.Calendar.MINUTE, 5);
		// c.add(Calendar.HOUR,72);
		// c.add(Calendar.YEAR,1);
		if (years != 0)
			c.add(Calendar.YEAR, years);
		if (months != 0)
			c.add(Calendar.MONTH, months);
		if (hours != 0)
			c.add(Calendar.HOUR, hours);
		if (seconds != 0)
			c.add(Calendar.SECOND, seconds);

		String expires = sdf.format(c.getTime());

		/*
		 * document.cookie = 'ppkcookie1=testcookie; expires=Thu, 2 Aug 2001
		 * 20:47:11 UTC; path=/'
		 * 
		 * 
		 * expires: The date and time when the cookie expires in Greenwich Mean
		 * Time (GMT). The format is: Wdy, dd-mmm-yyyy hh:mm:ss GMT. For
		 * example, "Mon, 16-07-2001 13:30:00 GMT." On the Tellme Platform, a
		 * cookie expires when the user's session ends, essentially when the
		 * user hangs up.
		 * 
		 * path: The subset of URLs to which the cookie applies. The cookie is
		 * sent if a substring of the requested URL matches the specified path.
		 * 
		 * domain: The domain in which the cookie is valid, beginning with a dot
		 * (.).
		 * 
		 * secure: A secure protocol is used to transfer the cookie data between
		 * the client and the server.
		 */

		// Assemble the string and encode the Value only
		StringBuffer cookie_str = new StringBuffer("document.cookie = '" + name
				+ " = ");
		try {
			cookie_str.append(URLEncoder.encode(value, "UTF-8") + "; expires="
					+ expires + ";'"); // ; path=/'");
			// cookie_str.append( URLEncoder.encode(value,"UTF-8")+"; expires="
			// +expires+ "; path=/'");
		} catch (UnsupportedEncodingException e1) { // Default incase encode
													// fails
			cookie_str.append(value + "; " + expires + ";'"); // path=/'");
			// cookie_str.append(value+"; " + expires + "; path=/'");
		}

		try {
			/*
			 *  ** WORKING->
			 * 
			 * JSObject.getWindow(this).eval("document.cookie ='" + name +" = "+
			 * value +"';"); // Sesion cookie
			 * JSObject.getWindow(this).eval("document.cookie ='" + name +" = "+
			 * value +"; expires="+ expires+"'"); // expires date
			 * 
			 * <-WORKING **
			 */
//TODO JSObject fix
			JSObject.getWindow(gw()).eval(cookie_str.toString());
			//JSObject.getWindow(gw()).eval(cookie_str.toString());
		} catch (Exception e) {
			// JSObject does not work when run in Eclipse
			// new Help("Exception JSO",e.toString());
			System.out.println("JSObject Exception 1\n"+e.toString()); // throw away
		}
// new Help("Saposed to save this cookie here", cookie_str.toString());
	}

	//
	// /**
	// *
	// * @param name
	// * @param value
	// */
	// private void setCookie(String name, boolean value){
	// if(value) setCookie(name, "True"); else setCookie(name, "False");
	// }

	/**
	 * 
	 * @param name
	 * @param value
	 */
	// RBBC private void setCookie(String name, long value){
	// RBBC setCookie(name, Long.toString(value, 10));
	// RBBC }

	/**
	 * 
	 * @param name
	 * @param value
	 */
	// RBBC private void setCookie(String name, int[] value){
	// RBBC StringBuffer sb = new StringBuffer();
	// RBBC for(int i=0 ; i < value.length; i++){
	// RBBC if(i==0) sb.append(Integer.toString(value[i]));
	// RBBC else sb.append('/' + Integer.toString(value[i]));
	// RBBC }
	// RBBC setCookie(name, sb.toString());
	// RBBC }

	// /**
	// *
	// * @param name
	// * @param value
	// */
	// private void setCookie(String name, Color value){
	// // String newValue;
	// // if(value.getRGB() == Color.BLACK.getRGB()) newValue = "Black"; else
	// // if(value.getRGB() == Color.RED.getRGB()) newValue = "RED"; else
	// // if(value.getRGB() == Color.GREEN.getRGB()) newValue = "Green"; else
	// // if(value.getRGB() == Color.BLUE.getRGB()) newValue = "Blue"; else
	// // if(value.getRGB() == Color.YELLOW.getRGB()) newValue = "Yellow"; else
	// // if(value.getRGB() == Color.BLACK.getRGB()) newValue = "Black"; else
	// // if(value.getRGB() == Color.BLACK.getRGB()) newValue = "Black"; else
	// // if(value.getRGB() == Color.BLACK.getRGB()) newValue = "Black"; else
	// // if(value.getRGB() == Color.BLACK.getRGB()) newValue = "Black"; else
	// // if(value.getRGB() == Color.BLACK.getRGB()) newValue = "Black"; else
	// // if(value.getRGB() == Color.BLACK.getRGB()) newValue = "Black"; else
	// //
	// setCookie(name, Integer.toHexString(value.getRGB()));
	// //setColors();
	// }

	// RBBC private void setColors(){
	// RBBC setCookie("Colors",
	// RBBC Integer.toHexString(ledOnColor.getRGB())+','+
	// RBBC Integer.toHexString(ledOffColor.getRGB())+','+
	// RBBC Integer.toHexString(bgColor.getRGB())
	// RBBC );
	// RBBC
	// RBBC }
//	/*
//	 * 
//	 *  @Deprecated use {@getOneSingleCookie() instead}
//	 *
//	 */
//	@Deprecated	private void XgetColors() {
//		String s = m_getParameter("Colors");
//		XgetColors(s);
//	}

//	/*
//	 * 
//	 *  @Deprecated use {@getOneSingleCookie() instead}
//	 *
//	 */
//	@Deprecated	private void XgetColors(String s) {
//		if (s == null || s.length() < 26)
//			return; // 12345678,01234567,90123456
//		String[] lcs = new String[3];
//
//		int fromIndex = 0;
//		int mark = s.indexOf(',');
//		if (mark > 0) {
//			lcs[0] = s.substring(fromIndex, mark);
//			ledOnColor = colorDecode(lcs[0], ledOnColor);
//			fromIndex = mark + 1;
//			mark = s.indexOf(',', fromIndex);
//		}
//
//		if (mark > 0) {
//			lcs[1] = s.substring(fromIndex, mark);
//			ledOffColor = colorDecode(lcs[1], ledOffColor);
//			fcLedOffColor = ledOffColor;
//			fromIndex = mark + 1;
//		}
//		if (mark < s.length()) {
//			lcs[2] = s.substring(fromIndex);
//			bgColor = colorDecode(lcs[2], bgColor);
//		}
//	}

	// private Color decodeColor(String s){
	// return Color.red;
	// }

	// /**
	// *
	// * @param name
	// * @param value
	// */
	// private void setCookie(String name, int value){
	// setCookie(name, Integer.toString(value, 10));
	// }

	// private boolean celsius = false;
	// private boolean enhancedLED = false;
	// private boolean no_bg = true;
	// RBBC private void setBooleans(){ //celsius, enhancedLED, no_bg
	// RBBC setCookie("Booleans",
	// RBBC Boolean.toString(celsius)+","+
	// RBBC Boolean.toString(enhancedLED)+"," +
	// RBBC Boolean.toString(no_bg));
	// RBBC }

//	/**
//	 * 
//	 *  @Deprecated use {@getOneSingleCookie() instead}
//	 *
//	 */
//	@Deprecated private void XgetBooleans() {
//		String s = m_getParameter("Booleans");
//		XgetBooleans(s);
//	}

//	// parse out the booleans from the cookie String s
//	/*
//	 * 
//	 *  @Deprecated use {@getOneSingleCookie() instead}
//	 *  
//	 *  @parameter s cookie sub string for booleans
//	 *  
//	 *  @return String array of all booleans
//	 *
//	 */
//	@Deprecated	private void XgetBooleans(String s) {
//		if (s == null || s.length() < 14)
//			return; // True,True,True
//
//		String[] bs = new String[4];
//		int fromIndex = 0;
//		int mark = s.indexOf(',');
//
//		if (mark > 0) {								// a deliminator was found 
//			bs[0] = s.substring(fromIndex, mark);	// sub string to the first ',' 
//			celsius = Boolean.parseBoolean(bs[0]);	//
//			fromIndex = mark + 1;					// skip the deliminator ','
//			mark = s.indexOf(',', fromIndex);		// find the next deliminator
//		}
//
//		if (mark > 0) {
//			bs[1] = s.substring(fromIndex, mark);
//			enhancedLED = Boolean.parseBoolean(bs[1]);
//			fromIndex = mark + 1;
//			mark = s.indexOf(',', fromIndex);
//		}
//
//		if (mark > 0) {
//			bs[2] = s.substring(fromIndex, mark);
//			no_bg = Boolean.parseBoolean(bs[2]);
//			fromIndex = mark + 1;
//			mark = s.indexOf(',', fromIndex);
//		} else {									// Old cookie with only 3 
//			if (mark < s.length()) {				// last one does not have ','
//				bs[2] = s.substring(fromIndex);
//				no_bg = Boolean.parseBoolean(bs[2]);
//				mark = s.length();					// Force bypass next value
//			}
//		}
//		
//		if (mark < s.length()) {					// last one does not have ','
//			bs[3] = s.substring(fromIndex);
//			alert = Boolean.parseBoolean(bs[3]);
//		}
//		 new
//		 Help(s,"Celsius = "+celsius+" enhancedLED= "+enhancedLED+
//				 " no Background= "+no_bg+ " Alert= "+alert);
//no_bg = true;
////TODO broken with new cookie
//	}
	

	/**
	 * 
	 * @param name
	 * @param value
	 */
	// RBBC private void setCookie(String name, byte value){
	// RBBC setCookie(name, Byte.toString(value));
	// RBBC }

	/**
	 * Get the URL decoded value for name from the users cookies
	 * 
	 * @param name
	 *            The handel to the cookie name/value pair
	 * 
	 * @return A String containg the UTF8 URL decoded value for the name or null
	 *         if the cookie is not found
	 * 
	 */
	private String getCookie(String name) {
		String retVal = null;
		String tmp = null;
		// String sep = System.getProperty("line.separator");
		try {
//TODO JSObject fix?
			//String cookie = ((String) netscape.javascript.JSObject.getWindow(gw()).eval(
			String cookie = ((String) JSObject.getWindow(gw()).eval(
					"document.cookie")) + ';';	// ";" Force at least 1 terminator
			
			if (cookie.contains((name + '='))) {
				// if(verbose) new Help("Looking for "+name+" in ", cookie);
				tmp = cookie.substring(cookie.indexOf((name + '=')),
						cookie.indexOf(";", cookie.indexOf((name + '='))));
				tmp = tmp.substring(tmp.indexOf("=") + 1, tmp.length());
				// if(verbose) new Help(" Found ",tmp);
				if (tmp.length() > 0)
					retVal = tmp;
			}
		} catch (Exception e) {
			// JSObject does not work when run in Eclipse
			System.out.println("JSObject Exception 2\n"+e); 
			// new Help(" getCookie Exception "+sensor, e.getMessage(),Color.RED);
			// throw away
		}

		if (retVal != null) {
			try {
				retVal = URLDecoder.decode(retVal, "UTF8");
			} catch (UnsupportedEncodingException e) {
				new Help("URLDecoder" + e.getLocalizedMessage(), Color.RED);
			}
			if(retVal.contains(build_str)) oldcookie = false;
			else oldcookie = true;
		}
		// ////
// new Help("String from document cookie",retVal);
		return retVal;

	}

	/*
	 * 
	 * radioGroup = new CheckboxGroup(); radio1 = new Checkbox("Red",
	 * radioGroup,false); radio2 = new Checkbox("Blue", radioGroup,true); radio3
	 * = new Checkbox("Green", radioGroup,false); add(okButton);
	 * add(wrongButton); add(nameField); add(radio1); add(radio2); add(radio3);
	 */

	// Color setLedColor(Color ledColor){
	// Color retValue = ledColor;
	// try{
	// ColorSelection Cs = new ColorSelection(ledColor);
	// Cs.addSelectColorInterface(Cs);
	// }catch(NullPointerException e ){
	// System.out.println("Event = " + e.toString());
	// }
	// return retValue;
	// }

	// Expire the old cookies remove this function and replace with the
	// setCookieX

	/**
	 * 
	 * @param name
	 *            Key
	 * @param value
	 *            Data
	 * 
	 *            Example: setSessionCookie("UserName","Victor"); results in a
	 *            session cookie 'UserName=Victor;' that is only aviable during
	 *            the current session.
	 * 
	 */
	private void setSessionCookie(String name, String value) {
            //@TODO     get cookies with webstart
            if(webstart) return;

		// SimpleDateFormat sdf = new
		// SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss 'GMT'");
		// TimeZone tz = TimeZone.getTimeZone("GMT");
		// tz.setID("GMT");
		// sdf.setTimeZone(tz);
		//
		// java.util.Calendar c = java.util.Calendar.getInstance();
		// // c.add(java.util.Calendar.MINUTE, 5);
		// // c.add(Calendar.HOUR,72);
		// // c.add(Calendar.YEAR,1);
		// c.add(Calendar.YEAR,-1); // make expire last year
		// //c.add(Calendar.HOUR,24);
		//
		// String expires = sdf.format(c.getTime());

		/*
		 * document.cookie = 'ppkcookie1=testcookie; expires=Thu, 2 Aug 2001
		 * 20:47:11 UTC; path=/'
		 * 
		 * 
		 * expires: The date and time when the cookie expires in Greenwich Mean
		 * Time (GMT). The format is: Wdy, dd-mmm-yyyy hh:mm:ss GMT. For
		 * example, "Mon, 16-07-2001 13:30:00 GMT." On the Tellme Platform, a
		 * cookie expires when the user's session ends, essentially when the
		 * user hangs up.
		 * 
		 * path: The subset of URLs to which the cookie applies. The cookie is
		 * sent if a substring of the requested URL matches the specified path.
		 * 
		 * domain: The domain in which the cookie is valid, beginning with a dot
		 * (.).
		 * 
		 * secure: A secure protocol is used to transfer the cookie data between
		 * the client and the server.
		 */

		// Assemble the string and encode the Value only
		StringBuffer cookie_str = new StringBuffer("document.cookie = '" + name
				+ " = ");
		try {
			cookie_str.append(URLEncoder.encode(value, "UTF-8") + ";'"); // expires=" +expires+ ";'");
																			// //
																			// ;
																			// path=/'");
			// cookie_str.append( URLEncoder.encode(value,"UTF-8")+"; expires="
			// +expires+ "; path=/'");
		} catch (UnsupportedEncodingException e1) {
			// Default in case encode fails keep it as is
			cookie_str.append(value + "; "); // + expires + ";'"); // path=/'");
			// cookie_str.append(value+"; " + expires + "; path=/'");
		}
// ToDo
// new Help("Setting the Sesson cookie = ",cookie_str.toString());

		try {
			/*
			 *  ** WORKING->
			 * 
			 * JSObject.getWindow(this).eval("document.cookie ='" + name +" = "+
			 * value +"';"); // Sesion cookie
			 * JSObject.getWindow(this).eval("document.cookie ='" + name +" = "+
			 * value +"; expires="+ expires+"'"); // expires date
			 * 
			 * <-WORKING **
			 */
//TODO JSObject fix
			//netscape.javascript.JSObject.getWindow(gw()).eval(cookie_str.toString());
			JSObject.getWindow(gw()).eval(cookie_str.toString());
		} catch (Exception e) {
			// JSObject does not work when run in Eclipse
			System.out.println("JSObject Exception 3\n"+e); 			// throw away
		}

	}
	
	// private void setAllCookies(){
	// // RBBC setCookie(sensor_str, sensor); // Conflict with first run
	// // setCookie(current_Temp_str,current_Temp);
	// // RBBC setCookie(alarmThreshold_str,alarmThreshold);
	//
	// // setCookie(bgColor_str, bgColor);
	// // setCookie(ledOnColor_str, ledOnColor);
	// // setCookie(ledOffColor_str, ledOffColor);
	// // RBBC setColors();
	// // RBBC setBooleans();
	// // setCookie(fcOnColor_str, fcOnColor);
	// // setCookie(fcOffColor_str, fcOffColor);
	// //setCookie(no_bg_str, no_bg);
	// //setCookie(celsius_str, celsius);
	// //setCookie(enhancedLED_str, enhancedLED);
	// // RBBC setCookie(refresh_interval_str, refresh_interval);
	// // RBBC setCookie(digiTempFile_str, digiTempFile);
	// // setCookie(verbose_str, verbose);
	// // RBBC setCookie("MyOneCookie"," ");
	//
	// setOneSingleBigCookie();
	// }
	/**
	 * 
	 * @return Combined string in the order sensor, alarmThreshold(over/under),
	 *         bgColor, ledOnColor, ledOffColor, no_bg, celsius, enhancedLED,
	 *         refresh_interval, digiTempFile
	 *         20130830 added 2 values boolean alert and alert_Audio_File
	 *         20130901 added build_str
	 *         20170224 added font_str
	 * 
	 */
	// private void setOneSingleBigCookie(){
	private void gatherAndSaveCookieString() {
		StringBuffer sb = new StringBuffer();
		sb.append(build_str);									// 0
		sb.append(',');
		sb.append(sensor);										// 1
		sb.append(',');
		sb.append(alarmThreshold[0]);							// 2
		sb.append('/');
		sb.append(alarmThreshold[1]);							// -
		sb.append(',');
		sb.append(Integer.toHexString(ledOnColor.getRGB()));	// 3
		sb.append(',');
		sb.append(Integer.toHexString(ledOffColor.getRGB()));	// 4
		sb.append(',');
		sb.append(Integer.toHexString(bgColor.getRGB()));		// 5
		sb.append(',');
		sb.append(Integer.toHexString(ledInvalidColor.getRGB()));	// new nnnn
		sb.append(',');
		sb.append(tz);			//Byte offset from UST			// new nnnn
		sb.append(',');
		sb.append(celsius);										// 6
		sb.append(',');
		sb.append(enhancedLED);									// 7
		sb.append(',');
		sb.append(no_bg);										// 8
		sb.append(',');
		sb.append(alert);										// 9
		sb.append(',');
		sb.append(refresh_interval);							// 10
		sb.append(',');
		sb.append(digiTempFile);								// 11
		sb.append(',');
		sb.append(alert_Audio_File);							// 12
		sb.append(',');
		sb.append(info);										// 13
		sb.append(",");
		sb.append(auto_color);									// 14
		sb.append(",");
		sb.append(outofdate);									// 15
		sb.append(",");
		sb.append(tzdf.getID());								// 16
		sb.append(",");
		sb.append(font.getName());								// 17
		sb.append(",");
		sb.append(font.getStyle());								// 18
		sb.append(",");
		sb.append(font.getSize());								// 19
		
		// new
//}
		// sb.append(',');
		// used to prevent lost of cookie data
		// 1,75/60,ff00ffff,ffffffff,ffffffff,false,true,true,true,180000,
		//		http://b3c-lc.com/b3c-lc.com//DigiTemp_files/DigiTemp.txt,
		//		http://b3c-lc.com/b3c-lc.com/DigiTemp_files/WarningSiren.wav,20130831
		//new Help("Saving " + sb.length() +" parameters", sb.toString());		
		setCookie(cookieNameStr, // Save cookie expires in 1 month
				sb.toString(), 0, 1, 0, 0);
	}

	private void getOneSingleBigCookie() {
		String t = ckmgr.getCookie(cookieNameStr); // A single string for all
		String[] s = separate(t, ',');
		double m_build = -1;
		if (s != null) {
			// new Help("there are "+s.length + " values",s);		
			int index = 0;
			if(s.length > 11){ // New style cookie Aug 2013
				try { m_build = Double.parseDouble(s[0]);
							// After the addition of build was the 12 parameters
				} catch(Exception e) {
					new Help("Cookie error m_build not a Double ... Delete the cookie", s[0], Color.YELLOW);
				}
				if(m_build < 4) {
					m_build = 20120829; // Just to get pass the first version of new cookie
				} else {
					index++;	// use new locations m_build allready read					
				}
			}
			// else new Help("Old Cookie "+s.length+" " + s[9],s);
			if (s.length > 9) {	// something is wrong if not at least 10 values
				sensor = getByte(s[index++], sensor); 					// [0/1]
				alarmThreshold = getHiLow(s[index++], alarmThreshold);	// [1/2]
				ledOnColor = colorDecode(s[index++],ledOnColor);		// [2/3]
				ledOffColor = colorDecode(s[index++],ledOffColor);		// [3/4]
				bgColor = colorDecode(s[index++],bgColor);				// [4/5]
				if (m_build >= 20140301){								// new 
					ledInvalidColor = colorDecode(s[index++],ledInvalidColor); // [2/3]
					getByte(s[index++], tz);
				}
				
				// All Versions
				celsius = parseBoolean(s[index++], celsius);			// [5/6]
				enhancedLED = parseBoolean(s[index++], enhancedLED);	// [6/7]
				no_bg = parseBoolean(s[index++], no_bg);				// [7/8]
				
				if (m_build >= 20120829){								// new
					alert = parseBoolean(s[index++], alert);			// [-/9]
				}
				
				// All versions
				getLong(s[index++], refresh_interval);					// [8/10]
				digiTempFile = getString(s[index++], digiTempFile);		// [9/11]
				
				if (m_build >= 20120829){
					alert_Audio_File = getString(s[index++], alert_Audio_File); //[12]
				}
				if (m_build >= 20131028){
					info = parseBoolean(s[index++], info);				// 13
				}
				if (m_build >= 20131204){
					auto_color = parseBoolean(s[index++],auto_color);	// 14
				}
				if (m_build >= 20141207){
					outofdate = (int) getLong(s[index++], (long) outofdate);	// 15
				}
				if (m_build >= 20160615){
					tzdf = TimeZone.getTimeZone(s[index++]);			// 16
				}
				if (m_build >= 20170224){								// 17, 18, 19
					font = new Font(
										getString(s[index++], font.getName()),
										(int) getLong(s[index++],font.getStyle()),
										(int) getLong(s[index++],font.getSize())
									);
				}
			} // else skip it corrupt values use program/session defaults
		}
	}

	/**
	 * 
	 * @param s
	 *            String to separate into individual strings
	 * 
	 * @param c
	 *            char that is used to separate strings.
	 * 
	 * @return Array of one or more sub strings without separation character
	 * 
	 */
	private String[] separate(String s, char separator) {
		String[] retvalue = null;
		if (s != null) {
			StringBuffer sb = new StringBuffer(s);
			int fromIndex = 0;
			int mark = 1;

			Vector<String> v = new Vector<String>();

			for (mark = 1; mark > 0;) {
				mark = sb.indexOf("" + separator, fromIndex);
				if (mark > 0){
					v.add(new String(sb.substring(fromIndex, mark)));
					fromIndex = mark + 1;
				} else { 								// Get the left over chars
					v.add(new String(sb.substring(fromIndex)));
				}
			}

			Iterator<String> i = v.iterator();
			retvalue = new String[v.size()];
			int idx = 0;
			while (i.hasNext() && idx < retvalue.length) {
				retvalue[idx++] = i.next();
			}
		}

		// new Help("retvalue length= ["+retvalue.length+"]", retvalue);
		return retvalue;
	}

	
} //End of class CookieManger


} // EOF
