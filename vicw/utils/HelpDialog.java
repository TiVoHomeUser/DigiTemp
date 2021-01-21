package vicw.utils;

/**
 * @author Victor Wheeler
 * @(#)HelpDialog.java June 2001,2017
 *
 * Copyright (c) 2017 Victor Wheeler myapps@vicw.us All Rights Reserved.
 * Copyright (c) 2001, 2010 Victor Wheeler vwheeler@compuserve.com All Rights Reserved.
 * Copyright (c) 1997 1998 vwheeler All Rights Reserved.
 * 
 * Help_Dialog displays The Strings from the StringArray text
 * 
 * March 5 2017
 * 	Removed the fixed property Font font now sets the Font from the Fram property parent 
 * 
 * October 15 2013
 * 	Addition of WindowListener to make the close dot on the frame work 
 * when run as a Java Application on Apple
 * 
 * February 2011 Changed to allow for transparent colors.
 * 		Non color constructors pass the default color.cyan instead of casting
 * 		 null to the Color counterparts. (Both Help and HelpDialog)
 * 
 * December 2010:
 *  - Separate String(s) that contain CR's into separate Label's
 *  
 *  - Added a TAB filter stringFormatter(string) to remove and expand tabs.
 *     remove all line feeds ( 'r' ) Removing the CR turned out to be a bad
 *     idea. CR's are used for new lines.
 *     NOTE: Tab Stops are every 5 chars.
 *  
 * December 2010:
 *  added a space before and after adding each line of text adding some
 *   "white space" between the text and borders.
 * 
 * December 2010:
 *  Used the title string in the top hidden label to force the size to be big
 *  enough to display the full label then it is hidden.
 * 
 * 
 * 
 */
import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.Color;
import java.awt.Event;
//import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.WindowEvent;

class HelpDialog extends java.awt.Dialog implements
		java.awt.event.ActionListener, java.awt.event.WindowListener
// java.awt.event.ItemListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8173695283933920494L;
	public static final String version = "1.02 build 20170305";
	//public static final String version = "1.01 build 20101214";
	private Button closeButton = null;
	private java.lang.String[] text = null;
	private Color backGroundColor = Color.cyan;
	private java.awt.Frame parent = null;

	/**
	 * Copyright (c) 1997, 1998 vwheeler All Rights Reserved.
	 * 
	 * @param parent
	 *            java.awt.Frame
	 * @param text
	 *            java.lang.String[] // Lines of text to be displayed
	 */
	public HelpDialog(java.awt.Frame parent, String[] text) {
		// this(parent, text, (Color) null);
		this(parent, text, Color.CYAN);
		// super(parent, "Help_Dialog", false);
		// this.parent = parent;
		// if (text == null) { // Not supposed to be however JIC
		// this.text[0] = new String("Error no Help Dialog available");
		// } else {
		// this.text = text;
		// }
		// initialize();
	}

	/**
	 * Copyright (c) 1997, 1998 vwheeler All Rights Reserved.
	 * 
	 * @param parent
	 *            java.awt.Frame
	 * @param text
	 *            java.lang.String[] // Lines of text to be displayed
	 */
	public HelpDialog(java.awt.Frame parent, String[] text, Color NewColor) {
		this(parent, (String) null, text, NewColor);
		// super(parent, "Help_Dialog", false);
		// this.parent = parent;
		// if (null != NewColor)
		// backGroundColor = NewColor;
		// if (text == null) { // Not supposed to be however JIC
		// this.text[0] = new String("Error no Help Dialog available");
		// } else {
		// this.text = text;
		// }
		// initialize();
	}

	/**
	 * Copyright (c) 1997, 1998, 2010 vwheeler All Rights Reserved.
	 * 
	 * @param parent
	 *            java.awt.Frame
	 * @param text
	 *            java.lang.String[] // Lines of text to be displayed
	 */
	public HelpDialog(java.awt.Frame parent, String title, String[] text) {
		// this(parent, title, text, (Color) null);
		this(parent, title, text, Color.CYAN);
		// super(parent, title, false);
		// this.parent = parent;
		// if (text == null) { // Not supposed to be however JIC
		// this.text[0] = new String("Error no Help Dialog available");
		// } else {
		// this.text = text;
		// }
		// initialize();
	}

	String title = null;

	/**
	 * Copyright (c) 1997, 1998 vwheeler All Rights Reserved.
	 * 
	 * @param parent
	 *            java.awt.Frame
	 * @param text
	 *            java.lang.String[] // Lines of text to be displayed
	 */
	public HelpDialog(java.awt.Frame parent, String title, String[] text,
			Color NewColor) {
		super(parent, title, false);
		this.parent = parent;
		if (null != NewColor) {
			backGroundColor = NewColor;
		}
		if (text == null) { // Not supposed to be however JIC
			this.text[0] = new String("Error no Help Dialog available");
		} else {
			this.text = text;
		}
		this.title = title;
		initialize();
	}

	/**
	 * Copyright (c) 1997, 1998 vwheeler All Rights Reserved.
	 * 
	 * @param ev
	 *            java.awt.event.ActionEvent
	 */
	public void actionPerformed(java.awt.event.ActionEvent ev) {
		if ((ev.getSource() == getCloseButton())) {
			stopHelpDialog();
		}
	}

	// public void dispose(){
	// super.dispose();
	// // System.exit(0);
	// }

	void stopHelpDialog() {
		// System.out.println("Hello from HelpDialog.stopHelpDialog()");
		this.setVisible(false);
		getCloseButton().removeActionListener(this);
		this.removeAll();
//		font = null;
		parent.dispose();
		dispose();
	}

	/**
	 * Copyright (c) 1997, 1998 vwheeler All Rights Reserved.
	 * 
	 * @param text
	 *            java.lang.String[]
	 * 
	 * adds the Strings to the Dialog object as Labels
	 * 
	 * December 2010: now handles Tabs and CR's Blank lines for LF's
	 * 
	 */
	private void addText(String[] text) {
		int beginIndex = 0;
		int endIndex = 1;
		
		
		for (int i = 0; i < text.length; i++) {
			beginIndex = 0; // moving pointers each line
			endIndex = 1;

			// Breakup lines containing CR's '\n' into individual new labels
			while (endIndex > 0) {
				endIndex = text[i].indexOf('\n', beginIndex);
				if (endIndex > 0) {
					add(new Label(" " 
							+ StringFormatter(text[i].substring(beginIndex,
									endIndex)) + " ")); // add extra space for the borders
				} else {	
						add( new Label(" "
							+ StringFormatter(text[i].substring(beginIndex))
							+ " ")); // add extra space for the borders					
				}
				beginIndex = endIndex + 1; // Next Sub string
			}

		} // Process each line in the array of strings
		return;
	}

	/**
	 * Copyright (c) 1997, 1998 vwheeler All Rights Reserved.
	 * 
	 * @return java.awt.Button
	 */
	private Button getCloseButton() {
		if (closeButton == null) {
			closeButton = new java.awt.Button();
			closeButton.setName("HelpClose");
			closeButton.setBackground(java.awt.Color.gray);
			closeButton.setForeground(java.awt.Color.black);
			closeButton.setActionCommand("HelpCloseFileButton");
			closeButton.setLabel("Dismiss");
		}
		return closeButton;
	}

	/**
	 * Copyright (c) 1997, 1998 vwheeler All Rights Reserved.
	 * 
	 * @return void
	 * @param evt
	 *            Event
	 */
	public void processEvent(AWTEvent evt) {

		if (evt.getID() == Event.WINDOW_DESTROY) {
			stopHelpDialog();
		}
	}

	/**
	 * Copyright (c) 1997, 1998, 2002 vwheeler All Rights Reserved.
	 */
//	private Font font = new Font("Courier", Font.BOLD, 14);

	//
	Label hiddenTitle; // Put the title as 1st line then hide it forces the

	// dialog to be at least the size of the label
	private void initialize() {
		this.setFont(parent.getFont());
		// setLayout(new GridLayout(text.length + 3, 1, 1, 1));
		// allow for the two spacers and Close button
		//setLayout(new GridLayout(0, 1, 1, 1));
		setLayout(new GridLayout(0, 1, 0, 0));
		// GridLayout(int rows, int cols, int hgap, int vgap)

		setBackground(backGroundColor);
		setForeground(new Color(255 - backGroundColor.getRGB()));

		if (getForeground().getRGB() < 127) {
			setForeground(getForeground().darker());
		} else {
			setForeground(getForeground().brighter());
		}

//		setFont(font);

		/*
		 * Blank line before text need a handle to hide it later add some extra
		 * chars for the menu bar buttons
		 */
		hiddenTitle = new Label("    " +title+"    "); // Makes minimum size
		//		hiddenTitle.setAlignment(Label.CENTER);
		add(hiddenTitle); // Becomes the Blank line before text

		addText(text); // Main body of text

		add(new Label("")); // Another blank line after text and before button
		add(getCloseButton(), getCloseButton().getName());
		getCloseButton().addActionListener(this);
		addWindowListener(this); // For the close dot running as an Application

		pack();
		// The window has now been auto-sized no need to see the extra text
		hiddenTitle.setVisible(false);

		return;

	}

	/**
	 * 
	 * @param s
	 *            String to filter
	 * 
	 * @return A new String with tabs expanded and special chars removed
	 * 
	 * Some systems display tabs and carriage returns differently this is a
	 * cheap attempt to remove these chars hopefully without breaking anything.
	 * 
	 */
	private String StringFormatter(String s) {
		StringBuffer sb = new StringBuffer();
		int currentloc = 0;
		int TabStop = 5; // most text is 8 default for help was about 4.5

		for (int ii = 0; ii < s.length(); ii++) {
			switch (s.charAt(ii)) {
			case '\t': // Tab
				if (TabStop > 0) {
					int tabfill = ((((int) (currentloc / TabStop) + 1) * TabStop))
							- currentloc;
					for (int j = 0; j < tabfill; j++) {
						sb.append(' ');
						currentloc++;
					}
				}
				break;
			case '\n': // Return
				// throw away each line is in it's own text field
				break;
			case '\r': // Line Feed
				add(new Label("")); // Add a blank line for each line feed
				break;
			default: // add all other char to the StringBuffer
				sb.append(s.charAt(ii));
				currentloc++;
			} // Switch
		}
		return sb.toString();
	}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		this.stopHelpDialog();

	}

	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
}