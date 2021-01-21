package vicw.utils;

/**
 * @author Victor Wheeler
 * @(#)SelectFont.java June 2001
 *
 * Copyright (c) 2001 Victor Wheeler vwheeler@compuserve.com All Rights Reserved.
 *
 *	Creates the frame and starts the Select Font display thread
 * 
 */
/*
 * 20170309
 *  Removed Pop Up Font while the pop-up display was nice it was getting in the way
 *  of selecting fonts from the list.
 * 
 * 20170308
 *  Entry into Font List text panel now updates selected item in List
 *  Mouse click on List was causing a research of the List and selecting the wrong font
 *  need a better way to find the best instead of the first match.
 *  
 * 20170302
 * added Panels and some content
 * more getters and setters
 * 
 */
// import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextField;
//PUF import java.awt.Window;
//PUF import java.awt.event.ItemEvent;
//PUF import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class SelectFont extends Thread implements WindowListener, KeyListener,
		MouseListener, TextListener, SelectFontInterface {
//PUF	public class SelectFont extends Thread implements WindowListener, ItemListener, KeyListener,
//PUF	MouseListener, TextListener, SelectFontInterface {

	public static final String VERSION = "20170309";
	public static final String cpyritinfo = "SelectFont Ver " + VERSION + " Copyright (c) 1997, 2017 Victor Wheeler myapps@vicw.net";
	public String getAppletInfo(){
		return cpyritinfo;
	}
	
	private Frame frame = null;
	private Panel fontNamePanel = null;
	private Panel fontStylePanel = null;
	private Panel fontSizePanel = null;
	private Panel samplePanel = null;
	private Panel buttonPanel = null;
	private Panel basePanel = null;

	// private String[] text = null;
	//	private String title = null;
	// private Color PanelColor = null;
	private Thread runner;

//	/**
//	 * Copyright (c) 1997, 1998 vwheeler All Rights Reserved. constructor when
//	 * only one line of text is required.
//	 * 
//	 * @param title
//	 *  Sets the title for this window
//	 */
//	public SelectFont(String title) {
//		this(title, Color.darkGray);
//		// super();
//		// this.title=title;
//		// if(this.title == null) this.title = new String(" ");
//		// initialize();
//		// start();
//	}

	/**
	 * Copyright (c) 1997, 1998, 2001 vwheeler All Rights Reserved. constructor
	 * when only one line of text is required.
	 * 
	 * @param text
	 *            java.lang.String
	 */
	private SelectFontInterface sfi = null;

	public SelectFont(Font f){
		oldFont = f;			// Save for later
		returnFont = f;

		initialize();
		start();	
	}

	/**
	 * Copyright (c) 1997, 1998 vwheeler All Rights Reserved.
	 * 
	 * @return Frame Parent frame for the Help_Dialog
	 */
	private Frame getPopUpWindow() {
		if (null == frame) {
			frame = new Frame(" Select Font Window ");
			// frame.setLayout(new GridLayout(1,1,20,20));
			// frame.setLayout(new GridBagLayout());
			frame.setBackground(Color.cyan);
			frame.setFont(new Font("Courier", Font.BOLD, 24));
		}
		return frame;
	}

	/**
	 * Copyright (c) 1997, 1998 vwheeler All Rights Reserved.
	 */
	public static void main(String argv[]) {
		// SelectFont sf = new SelectFont(" Select Fonts ");
		//new SelectFont(" Select Fonts ");
		//new SelectFont((Font) null);
		Font f = Font.decode(Font.SANS_SERIF+" boldItalic 12");
		System.out.println(f.toString());
		new SelectFont(f);
		

	}

	//	public Font getSelectedFont() {
	public void setNewFont(Font f) {
		System.err.println("To pass the selected Font to the inclosing program method\n\t"+
				super.getClass().getName()+".setNewFont(Font f) should be over-loaded ");
		if(returnFont == oldFont) 
			System.out.println("FYI Selection was canceled font returned\n\t "+f.toString());
		else
			System.out.println("FYI the font you selected\n\t"+f.toString());

		System.exit(0);
	}

	/*
	 * 
	 * 
	 */	
	public void initialize() {
		frame = getPopUpWindow();				
		frame.setSize(800, 800);
		frame.add(getBasePanel());
		
		addListeners();

	}
	
	
 private Dimension d1 = null;
 private Dimension d2 = null;

	private Panel getBasePanel(){
		if(basePanel == null){
			d1 = new Dimension(200,700);
			d2 = new Dimension(600,100);

			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			basePanel = new Panel(new GridBagLayout());
			basePanel.setBackground(Color.GRAY);
			// Font Name
			gbc.anchor = GridBagConstraints.PAGE_START;
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 0;			gbc.gridy = 0;
			gbc.gridwidth = 1;		gbc.gridheight = 2;
			gbc.ipadx = 50; //d1.height - 200;
			basePanel.add(getFontNamePanel(),gbc);

			// Font Style
			gbc.anchor = GridBagConstraints.PAGE_END;
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 1;			gbc.gridy = 0;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			gbc.ipadx = 25;
			basePanel.add(getFrontStylePanel(),gbc);

			//Font Size
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.weightx = 0.1;		gbc.weighty = 0.1;
			gbc.gridx = 2;			gbc.gridy = 0;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			basePanel.add(getFontSizePanel(),gbc);

			// Sample
			gbc.anchor = GridBagConstraints.PAGE_START;
			gbc.weightx = 0.0;		gbc.weighty = 0.0;
			gbc.gridx = 0;			gbc.gridy = 2;
			gbc.gridwidth = 2;		gbc.gridheight = 2;
			basePanel.add(getSamplePanel(),gbc);

			// Buttons
			gbc.anchor = GridBagConstraints.PAGE_END;
			gbc.weightx = 0.0;		gbc.weighty = 0.0;
			gbc.gridx = 2;			gbc.gridy = 2;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			basePanel.add(getButtonPanel(),gbc);
		}
		return basePanel;
	}
	
	private TextField fntf = new TextField(); //Label(" Font Name ");
	private Panel getFontNamePanel()
	{
		if (fontNamePanel == null)
		{
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;

			fontNamePanel = new Panel(new GridBagLayout());
			//fontNamePanel.setBackground(Color.LIGHT_GRAY);
			fontNamePanel.setPreferredSize(d1);


			Label tf = new Label(" Font ");
			tf.setText(" Font ");
			fntf.setEnabled(true);
			fntf.setText(returnFont.getName());
			fntf.addMouseListener(this);
			fntf.addKeyListener(this);
			//fntf.addActionListener(this);
			fntf.addTextListener(this);
			gbc.weightx = 0.1;		gbc.weighty = 0.1;
			gbc.gridx = 0;			gbc.gridy = 0;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			fontNamePanel.add(tf,gbc);
			
			gbc.weightx = 0.1;		gbc.weighty = 0.1;
			gbc.gridx = 0;			gbc.gridy = 1;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			fontNamePanel.add(fntf,gbc);

			gbc.weightx = 0.1;		gbc.weighty = 0.5;
			gbc.gridx = 0;			gbc.gridy = 2;
			gbc.gridwidth = 1;		gbc.gridheight = 2;
			gbc.ipady = 500;
			fontNamePanel.add(getFontList(),gbc);

		}
		return fontNamePanel;
	}
	
	private Panel getFontSizePanel(){
		if(fontSizePanel == null){
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;

			fontSizePanel = new Panel(new GridBagLayout());

			// fontSizePanel.setBackground(Color.RED);
			fontSizePanel.setPreferredSize(d1);
			
			gbc.weightx = 0.1;		gbc.weighty = 0.5;
			gbc.gridx = 0;			gbc.gridy = 0;
			gbc.gridwidth = 1;		gbc.gridheight = 1;

			Label psl = new Label(" Size ");
			fontSizePanel.add(psl,gbc);
			
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 0;			gbc.gridy = 1;
			gbc.gridwidth = 1;		gbc.gridheight = 4;
			//gbc.ipadx = 200;
			gbc.ipady = 500; //d1.height - 200;
			// System.out.println("Height = " + d1.height + " " + d1.getHeight());
			fontSizePanel.add(getPointSize(),gbc);
		}
		return fontSizePanel;
	}

	private Panel getFrontStylePanel(){
		if(fontStylePanel == null){
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			fontStylePanel = new Panel(new GridBagLayout());
			//fontStylePanel.setBackground(Color.GREEN);
			fontStylePanel.setPreferredSize(d1);
			//fontStylePanel.setLayout(new GridLayout(2, 1, 0, 0));

			Label stl = new Label(" Style ");
			gbc.anchor = GridBagConstraints.PAGE_START;
			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 0;			gbc.gridy = 0;
			gbc.gridwidth = 1;		gbc.gridheight = 1;
			fontStylePanel.add(stl,gbc);

			gbc.weightx = 0.5;		gbc.weighty = 0.5;
			gbc.gridx = 0;			gbc.gridy = 1;
			gbc.gridwidth = 1;		gbc.gridheight = 2;
			gbc.ipady = 500; //d1.height - 200;
			fontStylePanel.add(getStyle(),gbc);
		}
		return fontStylePanel;
		
	}
	
	private Panel getSamplePanel(){
		if(samplePanel == null){
			samplePanel = new Panel();
			// samplePanel.setFont(Font.decode("Dialog BOLD 40"));
			//samplePanel.setBackground(Color.BLUE);
			samplePanel.setPreferredSize(d2);
			samplePanel.setLayout(new GridLayout(1, 1, 0, 0));
			//Label sl = new Label(" Sample ");
			//samplePanel.add(sl);
			samplePanel.add(getSample());
		}
		return samplePanel;
	}

	private Panel getButtonPanel(){
		if(buttonPanel == null){
			buttonPanel = new Panel();
			//buttonPanel.setBackground(Color.YELLOW);
			buttonPanel.setPreferredSize(d1);
			buttonPanel.setLayout(new GridLayout(2, 0, 0, 0));
			buttonPanel.add(getOk());
			buttonPanel.add(getCancel());
		}
		return buttonPanel;
	}
	
	public boolean running() {
		return (this.runner != null);
	}

	//
	//
	//
	private void addListeners() {
		frame.addWindowListener(this);
		//		fontList.addItemListener(this);
		//		fontList.addMouseListener(this);
		//		fontList.addKeyListener(this);
	}

	private void removeListeners() {
		fontList.removeKeyListener(this);
//PUF		fontList.removeItemListener(this);
		fontList.removeMouseListener(this);
		frame.removeWindowListener(this);
	}

	public void stopDisplayFont() {
		runner = null;
		removeListeners();
		//if(returnFont != null)
		setNewFont(returnFont);
//PUF		if (popUpFonts != null)
//PUF			popUpFonts.close();
		fontNamePanel.remove(fontList);
		frame.remove(fontNamePanel);
		frame.dispose();
	}

	public void run() {
		frame.setVisible(true);
		Thread thisThread = Thread.currentThread();
		fontList.makeVisible(fontList.getSelectedIndex());

		while (runner == thisThread) {
			try {
				Thread.sleep(500); // 1000 = 1 second
			} catch (InterruptedException e) {
			}
			if (frame.isVisible() == false)
				break; // stopDisplayFont closed
		}
		stopDisplayFont();
	}

	/**
	 * Copyright (c) 1997, 1998 vwheeler All Rights Reserved.
	 */
	public void start() {
		runner = new Thread(this);
		runner.start();
		// do{
		// try{
		// sleep(10000);
		// } catch(InterruptedException e){}
		// }while(runner != null);
	}

	public void windowActivated(WindowEvent parm1) {
		// TODO: Add your code here
	}

	public void windowClosed(WindowEvent parm1) {
		System.out.println("Close Window");
		//stopDisplayFont();
		
	}

	public void windowClosing(WindowEvent parm1) {
		System.out.println("Closeing Window");
		stopDisplayFont();
	}

	public void windowDeactivated(WindowEvent parm1) {
		// TODO: Add your code here
	}

	public void windowDeiconified(WindowEvent parm1) {
		// TODO: Add your code here
	}

	public void windowIconified(WindowEvent parm1) {
		// TODO: Add your code here
	}

	public void windowOpened(WindowEvent parm1) {
		// TODO: Add your code here
	}

	/*
	 * // public void mouseEntered(MouseEvent ev){
	 * System.out.println("Station "+number+" Mouse entered"); if(number > 0 &&
	 * number <= numOfStations){ try{ Label l = new Label(" Station #"+ number
	 * +" "+ stations[number-1].getInfo()+" ",Label.CENTER);
	 * l.setFont(popUpFont); int width =
	 * l.getFontMetrics(l.getFont()).stringWidth(l.getText()); int height =
	 * l.getFontMetrics
	 * (l.getFont()).getHeight()+l.getFontMetrics(l.getFont()).getAscent();
	 * infoPanel.setSize(width,height); infoPanel.add(l);
	 * f.setBackground(Color.cyan); } catch(RemoteException e){}
	 * f.setSize(infoPanel.getWidth(),infoPanel.getHeight()); f.add(infoPanel);
	 * ev
	 * .translatePoint(p.getLocationOnScreen().x+1,p.getLocationOnScreen().y+1);
	 * // +1 stops enter/exit filcker f.setLocation(ev.getPoint());
	 * f.setVisible(true); } }
	 */

//PUF	private DisplayFont popUpFonts;

//PUF	public void itemStateChanged(ItemEvent parm1) {
//PUF		if (popUpFonts != null)
//PUF			popUpFonts.close();
//PUF		Font f = Font.decode(fontList.getSelectedItem());
//PUF		popUpFonts = new DisplayFont(f);
//PUF	}
	
	/*
	 * Updates returnFont
	 * Refresh the content of all displays
	 */
	private void refreshDisplays(){
		fntf.setText(getFontList().getSelectedItem());
		
		returnFont = Font.decode( getFontList().getSelectedItem().trim()+" "+
				getStyle().getSelectedItem().trim()+" "+
					getPointSize().getSelectedItem().trim());
		
		getSamplePanel().setFont(returnFont);
		getSample().setFont(returnFont);
		getSample().setText(returnFont.getFontName());
		//System.out.println("FontName()\t" + returnFont.getFontName());	// AmericanTypewriter-BoldItalic-Derived
		//System.out.println("Familyt() \t" + returnFont.getFamily());		// American Typewriter
		//System.out.println("Name()    \t" + returnFont.getName());		// AmericanTypewriter

	}
	
	public void mouseClicked(MouseEvent parm1) {
		if (parm1.getClickCount() > 1) {
			if ((parm1.getModifiers() & MouseEvent.BUTTON1_MASK) > 0) {
				refreshDisplays();						// Updates and computes returnFont from selected fields
				if (sfi != null)
					sfi.setNewFont(returnFont);
				runner = null; // Font selected, where outa here
				parm1.consume();
			}
		} else {
			if (parm1.getSource() == getCancel()) {		// Abort / Cancel
				returnFont = oldFont;
				runner = null;							// Stop program
				parm1.consume();
			}
			if (parm1.getSource() == getFontList()) {
				refreshDisplays();						// Updates and computes returnFont from selected fields
				parm1.consume();
			}
			if (parm1.getSource() == getPointSize()) {
				refreshDisplays();						// Updates and computes returnFont from selected fields
				parm1.consume();
			}
			if (parm1.getSource() == getStyle()) {
				refreshDisplays();						// Updates and computes returnFont from selected fields
				parm1.consume();
			}
			if (parm1.getSource() == getOk()) {			// Done single click should do it
				refreshDisplays();						// returnFont should all-ready be updated. JIC, FWTGBITN
				if(sfi != null) sfi.setNewFont(returnFont);
				runner = null;
				parm1.consume();
			}
			if (parm1.getSource() == fntf){
				keyevent=true;							// Another way to enter the Textbox
			}
		}
	}

	public void mouseEntered(MouseEvent parm1) {
		// TODO: Add your code here
	}

	public void mouseExited(MouseEvent parm1) {
		// TODO: Add your code here
	}

	Point mp = new Point(0, 0);

	public void mousePressed(MouseEvent parm1) {
		keyevent = false;	// Filter out non keyboard updates to textfields
		mp = new Point(parm1.getPoint());
		// if((parm1.getModifiers() & MouseEvent.BUTTON1_MASK) > 0){
		// if(popUpFonts != null) popUpFonts.close();
		// Font f = Font.decode(list.getSelectedItem());
		// popUpFonts = new DisplayFont(f);
		// }
	}

	public void mouseReleased(MouseEvent parm1) {
		keyevent=false;		// Filter out non keyboard updates to textfields
//PUF		if ((parm1.getModifiers() & MouseEvent.BUTTON2_MASK) > 0
//PUF				|| (parm1.getModifiers() & MouseEvent.BUTTON3_MASK) > 0) {
//PUF			if (popUpFonts != null)
//PUF				popUpFonts.close();
//PUF		}
	}
	

/*PUF	private class DisplayFont {
		//
		Window window;
		Panel popUpPanel;
		// Removed Oct 2010 VW Font popUpFont;// = new Font("Courier",
		// Font.ITALIC, 14);

		//
		public DisplayFont(Font font) {
			window = new Window(frame);
			popUpPanel = new Panel();
			Label popUpLabel = new Label("  " + font.getName() + "  ", Label.CENTER);
			popUpLabel.setFont(font);
			popUpPanel.add(popUpLabel);
			window.add(popUpPanel);
			int width = popUpLabel.getFontMetrics(popUpLabel.getFont()).stringWidth(popUpLabel.getText());
			int height = popUpLabel.getFontMetrics(popUpLabel.getFont()).getHeight() * 2;

			window.setSize(width, height);

			Point location = fontList.getLocationOnScreen();

			// int lwidth =
			// list.getFontMetrics(list.getFont()).stringWidth(list.getSelectedItem());
			// int lwidth = mp.x;
			// location.translate(lwidth+2,mp.y);
			location.translate(mp.x + 5, mp.y + 5);
			window.setLocation(location);
			window.setVisible(true);
		}

		//
		//
		protected void close() {
			if (popUpPanel != null) {
				window.setVisible(false);
				window.remove(popUpPanel);
				popUpPanel.removeAll();
				popUpPanel = null;
				window.dispose();
			}
		}
	} // Class DisplayFont
*/ //PUF	
	
	@Override
	public void keyTyped(KeyEvent e) {
		if ('\n' == e.getKeyChar()) {		// Th..th..th..thats All Folks
			returnFont = Font.decode(fontList.getSelectedItem());
			if (sfi != null)
				sfi.setNewFont(returnFont);
			runner = null; // Font selected, where outa here
		}
		// System.out.println(getClass().getName()+".keyTyped(\n\t"+e+" )");
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		// Not used required by KeyListener
		// System.out.println(getClass().getName()+".keyPressed(\n\t"+e+" )");
	}

	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getSource() == fntf) keyevent = true;	// Yes value was changed by keyboard
		// Not used required by KeyListener
		// System.out.println(getClass().getName()+".keyReleased(\n\t"+e+" )");
	}
	
	
	private List fontList = null;
	private List style = null;
	private List pointSize = null;
	private TextField sample = null;
	private Button ok = null;
	private Button cancel = null;
	private Font returnFont = null;
	private Font oldFont = null;

	
	private List getFontList(){
		if(fontList == null){
			fontList = new List();
			// fontList.setFont(new Font("Courier", Font.BOLD, 14));
			fontList.setMultipleMode(false);

			// Not sure which to use GAF uses '-' instead of spaces like Courier-Bold
			// Which to use depends on which Font.decode() is used.

			Font[] f = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();	// 1 Needed with GAF
				//  s[527]
				//		First=Serif
				//		Last=Zapfino
			String[] s = new String[f.length];											// 1 needed with GAF
			
			// or GAFN	some names have a space like "Courier New" 
			// String[] s = GraphicsEnvironment.										// 2 Nedded with GAFN
			//				getLocalGraphicsEnvironment().
			//					getAvailableFontFamilyNames();
				//	s[221]
				//		First=Al Bayan
				//		Last=Zapfino
				
			int index = 0;
			for (int i = 0; i < s.length; i++) {
				s[i] = new String(f[i].getFontName());									// 1 Needed with GAF
				fontList.add(s[i]);
				if(s[i].trim().equalsIgnoreCase(returnFont.getFontName().trim())){
						index=i;		// Should be the current font name string
					}
			}

			fontList.select(index);

//PUF			fontList.addItemListener(this);
			fontList.addMouseListener(this);
			fontList.addKeyListener(this);
			fontList.makeVisible(fontList.getSelectedIndex());
		}
		return fontList;
	}
	
	private List getPointSize(){
		if(pointSize == null){
			pointSize = new List();
			pointSize.setMultipleMode(false);
			
			int index = returnFont.getSize();

			int min = 3; int max = 40;		// Lets start with reasonable font sizes
			if(index < min)					// Allow user to use unreasonable size
				min = index;
			else
				if(index > max) max = index;

			for(int i = min; i <= max; i++){
				pointSize.add(" " + i + " ");
				if(i == index){
					pointSize.select(i-min);		// Select position of the current point size
													//	(loop may not of started at 0)
				}
			}
			
			pointSize.addKeyListener(this);
			pointSize.addMouseListener(this);
			pointSize.makeVisible(pointSize.getSelectedIndex());
		}
		return pointSize;
	}
	
	private List getStyle(){
		if(style == null){
			style = new List();
			style.add("Plain");			if(returnFont.isPlain())	style.select(style.getItemCount()-1);
			style.add("Bold");			if(returnFont.isBold())		style.select(style.getItemCount()-1);
			style.add("Italic");		if(returnFont.isItalic())	style.select(style.getItemCount()-1);
			style.add("BoldItalic");	if(returnFont.isBold() &&
											returnFont.isItalic())	style.select(style.getItemCount()-1);

			style.addKeyListener(this);
			style.addMouseListener(this);
			//style.select(1);
			style.makeVisible(style.getSelectedIndex());
		}

		return style;
	}
	
	
	private TextField getSample(){
		if(sample == null){
			//sample = new TextField(" Sample ");
			sample = new TextField(returnFont.getFontName());
			// sample.setFont(Font.getFont("Dialog Bold 40"));
		}
		return sample;
	}
	
	private Button getOk(){
		if(ok == null){
			ok = new Button(" OK ");
			ok.addKeyListener(this);
			ok.addMouseListener(this);
		}
		return ok;
	}
	
	private Button getCancel(){
		if(cancel == null){
			cancel = new Button(" Cancel ");
			cancel.addKeyListener(this);
			cancel.addMouseListener(this);
		}
		return cancel;
	}

	private Boolean keyevent = false;		// Filter out non keyboard updates to textfields set true by keyReleased

	@Override
	public void textValueChanged(TextEvent e) {
		//System.out.println(getClass().getName()+".textValueChanged(\n\t"+e+" )");
		if(keyevent){				// Fix for Mac OS local updates to the textfield would trigger textValueChanged events
			keyevent = false;
		if (e.getSource() == fntf) {
			int idx = getFontList().getItemCount();
			if (fntf.getText().length() > 0) {
				do {
					idx--;
					if (idx < 0) {
						System.err.println(this.getClass().getName() + ".textValueChange("+e+") OUT OF RANGE " + idx + " < 0");
						break;
					}
				} while ((idx > 0)
						&& (!getFontList().getItem(idx).toUpperCase().startsWith(fntf.getText().toUpperCase())));

				if (idx >= 0){
					getFontList().select(idx);
					getFontList().makeVisible(idx);
				}
			} // else do nothing Normal for an empty textfield
		}
		}
	}

	
}