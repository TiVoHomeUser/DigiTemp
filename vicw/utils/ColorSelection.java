package vicw.utils;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;
/*
  	Jan 27 2017
   	changed logic for isUndecorated() in getAlphaSB() and getSBColor
   	     false = transparency available, true is not
    checked if(getAlphaSB().isEnabled()) before adding to panel.
    Resized panel and label GridLayout's accordingly for AlphaSB
   	
	Nov 25 2013
	Changed getSBColor() to check if Alpha is supported by the Host system.
	disabling Alpha if not.
	Note: 20170127 logic was reversed. A bug or did Java change something.
	
	Nov 24 2013
	 Restored Alpha Scrollbar and events
	 
	 
	Ver	1.0
	August 23
	Improved scrollbar functionalaty
	Replaced cross hair circle Color.white with Current Color at cross hair
	Addition of menubar for scrollbars
		menuitem Select
		menuitem Apply
		menuitem Cancel
	Scrollbars are now initalized to the defaultColor
	made color for crosshair lines the inverse of the current color
	Mouse click points and image adjusted for insets
	Paint modified for new dimensions
	
	August 25
	Addition of color test window displays the current color Note: the Window object
		is not affected by the Alpha value
		
	August 26
	Removed Alpha ScrollBar and assocated events
	Moved color test Window into scrollbar Frame as a Panel
	Changed scrollbar Frame constrants to GridBag
	addition of buttons Panel Apply, Close and Cancel Buttons to scrollbar Frame
	
	August 27
	Added Hex values to the color values displayed in the title bar. Values are stored as
		Hex color values for R G and B in the .cfg file this should simplify if the .cfg
		file is edited manualy.
 */
 /*
 					TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
 					
 	ScrollBar Frame is first drawn at the top left of the screen the re-drawn in the
 		Proper location, removing the setVisable(true) from getFsp() causes a flaw
 		 in the location calculations vertical size does not include the menubar

	Start with a lighter version with color boxes and an button for the advanced frame.
	
	check for and update the cross-hairs only
	
	done 8/26/2001 the Alpha value is not used by the awt Window or Frames need to remove the Alpha
		Scrollbar and values.
	
	The first time the scroll Bar Frame Apply button or menu is selected the Scroll Bar
		Frame does not come up to front. function works ok the second, third or more.

 */
public class ColorSelection extends Frame
 implements WindowListener, Runnable, MouseListener, MouseMotionListener, SelectColorInterface, AdjustmentListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -297440659533500689L;
	private static final double version = 1.12;
	private static final long build = 20170127;
	//private static final long build = 20131205;
	//private static final String CopyRite = "Copyrite � 2013 Victor Wheeler myApps@vicw.net build #"+build;
	private static final String CopyRite = "Copyrite (c) 2013, 2017 Victor Wheeler myApps@vicw.net build #"+build;
	
	private Color orginalColor = null;
	private Color selectedColor = null;
	private String titleString = "";
	/**
	 * 
	 */
	public ColorSelection()	{
		this(new Color(127,127,127,255));  // Color(r,g,b,a) int 0-255 a=alpha
	}
	/**
	 * 
	 * @param defaultColor
	 */
	public ColorSelection(Color defaultColor)	{
		orginalColor = defaultColor;
		init();
		start();
	}
	//
	private SelectColorInterface sci = null;
	
	/**
	 * 
	 * @param defaultColor
	 * @param sc
	 * @param ts
	 */
	public ColorSelection(Color defaultColor, SelectColorInterface sc, String ts){
		//this(defaultColor);
		this.sci = sc;
		this.titleString = ts;
		orginalColor = defaultColor;
		init();
		start();

	}
	
	/**
	 * 
	 * @param defaultColor
	 * @param sc
	 */
	public ColorSelection(Color defaultColor, SelectColorInterface sc){
		this(defaultColor);
		this.sci = sc;
	}
	/**
	 * 
	 * @param sc
	 */
	public void addSelectColorInterface(SelectColorInterface sc){
		this.sci=sc;
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args)	{
	  	ColorSelection applet = new ColorSelection();
	  	applet.addSelectColorInterface(applet);
	  	System.out.println(applet.toString());
	}
	//
	private Scrollbar greenSB;
	private Scrollbar blueSB;
	private Scrollbar redSB;
	private Scrollbar alphaSB;
	private Panel scrollBarPanel=null;
	private Frame fsp = null;
	//
	private Scrollbar getGreenSB(){
		if(greenSB == null){
			greenSB = new Scrollbar(Scrollbar.VERTICAL, orginalColor.getGreen(), 1, 0, 256);
			greenSB.setName("Green");
			greenSB.setBackground(Color.green);
		}
		return greenSB;
	}
	//
	private Scrollbar getBlueSB(){
		if(blueSB == null){
			blueSB =  new Scrollbar(Scrollbar.VERTICAL, orginalColor.getBlue(), 1, 0, 256);
			blueSB.setName("Blue");
			blueSB.setBackground(Color.blue);
		}
		return blueSB;
	}
	//
	private Scrollbar getRedSB(){
		if(redSB == null){
			redSB =   new Scrollbar(Scrollbar.VERTICAL, orginalColor.getRed(), 1, 0, 256);
			redSB.setName("Red");
			redSB.setBackground(Color.red);
		}
		return redSB;
	}
	
	//
 	private Scrollbar getAlphaSB(){
 		if(alphaSB == null){
 			alphaSB = new Scrollbar(Scrollbar.VERTICAL, orginalColor.getAlpha(), 1, 0, 256);
 			alphaSB.setName("Alpha");
 			// System.out.println("decorated = "+isUndecorated());
 			if (isUndecorated()){ // Host environment may not support Transparency
 				alphaSB.setEnabled(true);
 			}  else {
 				alphaSB.setEnabled(false);
 				alphaSB.setVisible(false);
 			}
 		}
 		return alphaSB;

 	}
 	
 	//
	private Color getSBColor(){
		if (isUndecorated()) // Host environment may not support Transparency
			return new Color(getRedSB().getValue(), getGreenSB().getValue(),
					getBlueSB().getValue(), getAlphaSB().getValue());

		else	//Alpha is not supported
			return new Color(getRedSB().getValue(), getGreenSB().getValue(),
					getBlueSB().getValue());
	}
	
	private Panel wtcd = null;		// test color display window
	private Panel getWtcd(){		//
		if(wtcd == null){
			wtcd = new Panel(); //getFsp()
		}
		wtcd.setVisible(true);
		return wtcd;
	}
	//
	private Frame getFsp(){  // Frame Scroll Panel
		if(fsp==null){
			fsp = new Frame();
			fsp.setSize(225,400);
			fsp.setMenuBar(smbMenuBar());
			fsp.setTitle(titleString);
			
		}
		fsp.setVisible(true); // for some reason the menubar is not included in the
							  // Frame's dimensions until the Frame is made visible
		return fsp;
	}
	//
	Menu closeSmb = null;
	MenuItem selectSmbClose = null;
	MenuItem selectSmbCancel = null;
	MenuItem selectSmbApply = null;
	MenuItem smbInfo = null;
	MenuBar smb = null;

	//
	private MenuBar smbMenuBar(){
		if(smb == null){
			smb = new MenuBar();
			closeSmb = new Menu("Menu");
			selectSmbApply = new MenuItem("Apply");
			selectSmbClose = new MenuItem("Select");
			selectSmbCancel = new MenuItem("Cancel");
			smbInfo = new MenuItem("Info");
			
			closeSmb.add(selectSmbApply);
			closeSmb.add(selectSmbClose);
			closeSmb.add(selectSmbCancel);
			closeSmb.add(smbInfo);
			smb.add(closeSmb);
		}
		return smb;
	}
	
	//
	private Panel getScrollBarPanel(){
		if(scrollBarPanel == null){
			scrollBarPanel = new Panel();
			if(getAlphaSB().isEnabled())
				scrollBarPanel.setLayout(new GridLayout(0,4,2,2)); // int rows, int cols, int hgap, int vgap
			else
				scrollBarPanel.setLayout(new GridLayout(0,3,2,2)); // int rows, int cols, int hgap, int vgap

		

			scrollBarPanel.add(getRedSB());
			scrollBarPanel.add(getGreenSB());
			scrollBarPanel.add(getBlueSB());
			if(getAlphaSB().isEnabled()) scrollBarPanel.add(getAlphaSB());
		}
		return scrollBarPanel;
	}
	
	private Panel scrollBarLabelPanel = null;
	
	private Panel getScrollBarLables() {
		if (scrollBarLabelPanel == null) {
			scrollBarLabelPanel = new Panel();
			if(getAlphaSB().isEnabled())
				scrollBarLabelPanel.setLayout(new GridLayout(0, 4, 2, 0));
			else
				scrollBarLabelPanel.setLayout(new GridLayout(0, 3, 2, 0));
			Label lr = new Label(getRedSB().getName());
			lr.setBackground(Color.RED);
			lr.setAlignment(Label.LEFT);
			Label lg = new Label(getGreenSB().getName());
			lg.setBackground(Color.GREEN);
			lg.setAlignment(Label.LEFT);
			Label lb = new Label(getBlueSB().getName());
			lb.setBackground(Color.BLUE);
			lb.setAlignment(Label.LEFT);
			scrollBarLabelPanel.add(lr);
			scrollBarLabelPanel.add(lg);
			scrollBarLabelPanel.add(lb);
			if(getAlphaSB().isEnabled()) {
				Label la = new Label(getAlphaSB().getName());
				la.setAlignment(Label.LEFT);
				scrollBarLabelPanel.add(la);
			}
		}
		return scrollBarLabelPanel;
	}
	//
	private void initListeners(){
		addWindowListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		
		getRedSB().addAdjustmentListener(this);
		getBlueSB().addAdjustmentListener(this);
		getGreenSB().addAdjustmentListener(this);
		getAlphaSB().addAdjustmentListener(this);
		
		getFsp().addWindowListener(this);

		selectSmbApply.addActionListener(this);
		getApplyButton().addActionListener(this);
		
		selectSmbClose.addActionListener(this);
		getSelectButton().addActionListener(this);
		
		selectSmbCancel.addActionListener(this);
		getCancelButton().addActionListener(this);
		
		smbInfo.addActionListener(this);
	}
	
	private void removeListeners(){
		
		smbInfo.removeActionListener(this);
		
		getCancelButton().removeActionListener(this);
		selectSmbCancel.removeActionListener(this);
		
		getSelectButton().removeActionListener(this);
		selectSmbClose.removeActionListener(this);

		getApplyButton().removeActionListener(this);
		selectSmbApply.removeActionListener(this);
		
		getFsp().removeWindowListener(this);

		getAlphaSB().removeAdjustmentListener(this);
		getGreenSB().removeAdjustmentListener(this);
		getBlueSB().removeAdjustmentListener(this);
		getRedSB().removeAdjustmentListener(this);

		removeMouseMotionListener(this);
		removeMouseListener(this);
		removeWindowListener(this);
	}
	private void init(){
		setSize(640, 480); // width, height



        GridBagLayout gbl = new GridBagLayout();
        getFsp().setLayout(gbl);
        
        
        GridBagConstraints gbc = new GridBagConstraints();
 		//gbc.insets = new Insets(3,3,3,3);
        gbc.weightx = 1.0;
        gbc.weighty =0.4;
		//
		gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER; //end row 
		getFsp().add(getWtcd(),gbc);
		//getWtcd().setBackground(Color.RED);

		gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty =2.0;
		getFsp().add(getScrollBarPanel(),gbc);
		//getScrollBarPanel().setBackground(Color.GREEN);
   
		gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty =0.1;
		getFsp().add(getScrollBarLables(),gbc);
		//getScrollBarLables().setBackground(Color.BLUE);
   
		gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty =0.1;
        getFsp().add(getButtons(),gbc);
        //getButtons().setBackground(Color.YELLOW);


		int locationOffset = getHeight()-getFsp().getHeight();
		if(locationOffset < 0) locationOffset = 0;
		getFsp().setLocation(getWidth(),locationOffset);
		
		getWtcd().setBackground(getSBColor());
		getFsp().setBackground(getSBColor()); // new
		setBackground(getSBColor()); // new

//		getWtcd().setSize(getFsp().getWidth(),locationOffset);
//		getWtcd().setLocation(getWidth(),0);
						
		initListeners();
		setVisible(true);
		
		// compute inital location for display of cross hair
	    int width  = getSize().width - (getInsets().left + getInsets().right);
		int height = getSize().height - (getInsets().top + getInsets().bottom);
		heightstep = 255.0/height;
		widthstep = 255.0/width;
 		pickedLocation((int)(getBlueSB().getValue()/widthstep+.5), (int)(getRedSB().getValue()/heightstep+.5));
		//
	}
	
	//
	private Panel buttons = null;
	private Button applyButton = null;
	private Button selectButton = null;
	private Button cancelButton = null;
	//
	private Button getApplyButton(){
		if(applyButton == null){
			applyButton = new Button("Apply");
		}
		return applyButton;
	}
	//
	private Button getSelectButton(){
		if(selectButton == null){
			selectButton = new Button("Select");
		}
		return selectButton;
	}
	//
	private Button getCancelButton(){
		if(cancelButton == null){
			cancelButton = new Button("Cancel");
		}
		return cancelButton;
	}
	//
	private Panel getButtons(){
		if(buttons == null){
			buttons = new Panel();
			buttons.setBackground(Color.lightGray);
			//setLayout(new GridLayout(1,3,2,1)); // int rows, int cols, int hgap, int vgap
			buttons.add(getApplyButton());
			buttons.add(getSelectButton());
			buttons.add(getCancelButton());
		}
		return buttons;

	}
	//
	public void windowActivated(WindowEvent parm1)
	{
		//System.out.println("windowActivated");
		// TODO: Add your code here
	}

	public void windowClosed(WindowEvent parm1)
	{
		//System.out.println("windowClosed");
		// TODO: Add your code here
	}

	public void windowClosing(WindowEvent parm1)
	{
		//System.out.println("windowClosing");
		close();
	}

	public void windowDeactivated(WindowEvent parm1)
	{
		//System.out.println("windowDeactivated");
		// TODO: Add your code here
	}

	public void windowDeiconified(WindowEvent parm1)
	{
		//System.out.println("windowDeiconified");
	   	//calculateImage();
	}

	public void windowIconified(WindowEvent parm1)
	{
		//System.out.println("windowIconified");
		// TODO: Add your code here
	}

	public void windowOpened(WindowEvent parm1)
	{
		//System.out.println("windowOpened");
		// TODO: Add your code here
	}
	private void close(){
		stop();
	}
	
	public void stop(){
		if(runner != null){
			runner=null;
			if(sci != null && selectedColor != null) sci.setNewColor(selectedColor);
			fsp.setVisible(false);
			setVisible(false);
			cleanUp();
			scrollBarPanel=null;
			fsp.dispose();
			fsp=null;
			dispose();
		}
	}
	
	//
	Thread runner;
	public void start(){
		runner = new Thread(this);
		runner.start();
		repaint();
	}
	
   /**
	 * Calculates and returns the image.  Halts the calculation and returns
	 * null if the Applet is stopped during the calculation.
	 */
	
	Dimension current_location = new Dimension(0,0);
	double heightstep=0; //redstep=0;
	double widthstep=0; //bluestep=0;
	//
	//
	private Image calculateImage() {
	    int width  = getSize().width - (getInsets().left + getInsets().right);
		int height = getSize().height - (getInsets().top + getInsets().bottom);
		heightstep = 255.0/height;
		widthstep = 255.0/width;
		//
		int pixels[] = new int[width * height];
		int c[] = new int[4];	// RED [0], GREEN [1], BLUE [2] ALPHA [3] 
		int index = 0;
		double red =0;
		double blue = 0;
		int green = getGreenSB().getValue();
		int alpha = getAlphaSB().getValue();
		//
		for(int j = 0; j < height; j++) {
			red=0;
			blue=0;
				red = j * heightstep;
		    for (int i = 0; i < width; i++) {
				blue = i * widthstep;
				c[0] = (int)red;
				c[1] = green;
				c[2] = (int)blue;
				c[3] = alpha;
				pixels[index++] = (
							(c[3] << 24) |
							(c[0] << 16) |
							(c[1] << 8)  |
							(c[2] << 0));
		    }
		}
		current_location = new Dimension(mouse_y,mouse_x);
		//current_location = new Dimension((int)(redSB.getValue() /  heightstep),(int)(blueSB.getValue() /  widthstep));
		//
		return createImage(new MemoryImageSource(width, height,
		       ColorModel.getRGBdefault(), pixels, 0, width));
	}
	//
	public void run() {
		Thread thisThread = Thread.currentThread();
	
		while (runner == thisThread) {
			if(!isValid()){
				repaint();
				this.validate();
			}
			try { Thread.sleep(300); }		// 1000 = 1 second
			catch (InterruptedException e) { }
		}
 	} 
	
	Image img;
	static String calcString = "Calculating...";

	public void paint(Graphics g) {
		int w = getSize().width-(getInsets().left+getInsets().right);
		int h = getSize().height-(getInsets().top+getInsets().bottom);

		if (img == null) {
		    super.paint(g);
		    g.setColor(Color.black);
		    FontMetrics fm = g.getFontMetrics();
		    int x = (w - fm.stringWidth(calcString))/2;
		    int y = h/2;
		    g.drawString(calcString, x, y);
		} else {
			//
			g.drawImage(img, getInsets().left, getInsets().top, w, h, this);
			//
			//g.setColor(Color.green.brighter());
			g.setColor(new Color(
					255-getRedSB().getValue(),
					255-getGreenSB().getValue(),
					255-getBlueSB().getValue(),
					255-getAlphaSB().getValue()
					)
			);
			
			// Horz Line
			g.drawLine(
						getInsets().left,
						current_location.width+getInsets().top,
						
						w+getInsets().left,
						current_location.width+getInsets().top);
						
			// Vert Line
			g.drawLine(
						current_location.height+getInsets().left,
					    getInsets().top,
					    
					    current_location.height+getInsets().left,
					    h+getInsets().top);
			//
			g.setColor(getSBColor());
			g.fillOval(
				current_location.height+getInsets().left-5,
				current_location.width+getInsets().top-5,
				10,
				10);
			g.setColor(Color.black);
			g.drawOval(
					current_location.height+getInsets().left-5,
					current_location.width+getInsets().top-5,
					10,
					10);
		}
	}
	
	//
	public void repaint(){
		//if(img == null)
			img = calculateImage();
	    synchronized(this) {
	       	if (img != null){
				Graphics g = getGraphics();
				try {
				    paint(g);
				} finally {
				    g.dispose();
				}
			} else {
				if(img == null) System.out.println("run()  image == null");
			}
		}
	}
	
	private int mouse_x=0;
	private int mouse_y=0;
	private void pickedLocation(int x, int y){
		mouse_x = x; mouse_y=y;
		current_location = new Dimension(y,x);
		getRedSB().setValue((int)(mouse_y * heightstep));
		getBlueSB().setValue((int)(mouse_x * widthstep));
		setTitle(
				" Red "+getRedSB().getValue()+", "+	//" ("+ Integer.toHexString(getRedSB().getValue())+"), "+
				" Green "+getGreenSB().getValue()+", "+	//" ("+ Integer.toHexString(getGreenSB().getValue())+"), "+
				" Blue "+getBlueSB().getValue()+", "	+ //	" ("+ Integer.toHexString(getBlueSB().getValue())+"), " +
				" Alpha "+getAlphaSB().getValue()+" " //	" ("+ Integer.toHexString(getAlphaSB().getValue())+") "
		);
		
		getWtcd().setBackground(getSBColor());
		getFsp().setBackground(getSBColor()); // new
		setBackground(getSBColor()); // new
		invalidate();
	}
	//
	public void mouseClicked(MouseEvent parm1)
	{
		pickedLocation(parm1.getX()-getInsets().left, parm1.getY()-getInsets().top);
		//
		if(parm1.getClickCount() > 1){
			selectedColor = null;
			if((parm1.getModifiers() & MouseEvent.BUTTON1_MASK) > 0){
				selectedColor = getSBColor();
			}
			close();
		}
		invalidate();
	}

	public void mouseEntered(MouseEvent parm1)
	{
		// TODO: Add your code here
	}

	public void mouseExited(MouseEvent parm1)
	{
		// TODO: Add your code here
	}
	//boolean drag=false;
	public void mousePressed(MouseEvent parm1)
	{
		//invalidate();
	}

	public void mouseReleased(MouseEvent parm1)
	{
		//invalidate();
	}

	public void mouseDragged(MouseEvent parm1) {
		pickedLocation(parm1.getX()-getInsets().left, parm1.getY()-getInsets().top);
		//pickedLocation(parm1.getX(), parm1.getY());
		//invalidate();
	}

	public void mouseMoved(MouseEvent parm1) {
	}
	
	private void cleanUp(){
		removeListeners();
		if(scrollBarPanel != null) scrollBarPanel.removeAll();
		if(fsp != null) fsp.removeAll();
		if(wtcd != null) wtcd.removeAll(); 
	}

	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == selectSmbClose
				|| ev.getSource() == getSelectButton()) {
			selectedColor = getSBColor();
			close();
		} else if (ev.getSource() == selectSmbCancel
				|| ev.getSource() == getCancelButton()) {
			selectedColor = null;
			close();
		} else if (ev.getSource() == selectSmbApply
				|| ev.getSource() == getApplyButton()) {
			selectedColor = getSBColor();
			sci.setNewColor(selectedColor);

			getWtcd().setBackground(selectedColor);
			getFsp().setBackground(selectedColor); // new
			setBackground(selectedColor); // new

			getFsp().toFront();
			getFsp().setVisible(true);
			getFsp().requestFocus();
		} else if(ev.getSource() == smbInfo)
			new Help("info", info());
	}
	
	/**
	 * 
	 */
	public void setNewColor(Color parm1) {
		// System.out.println("Color Selected = "+this.selectedColor.toString());
		System.exit(0);
	}

	public void adjustmentValueChanged(AdjustmentEvent parm1) {
		// 		if(parm1.getSource() == getGreenSB() || parm1.getSource() == getAlphaSB())
		//			img=null;  // not just crosshair, force rebuild entire image
 		pickedLocation((int)(getBlueSB().getValue()/widthstep+.5), (int)(getRedSB().getValue()/heightstep+.5));
 		invalidate();
	}
	
	public String info(){
		return new String("ColorSelection Version "+ version +" "+CopyRite);
	}
	
	public String toString(){
		return new String("ColorSelection " + version + " " + CopyRite + "\n" +
				super.toString());
	}
}