package vicw.utils.led;

import java.awt.*;

//public class Dy extends SevenSegLed{        // No reason to extend from SevenSegLed,
public class Dy extends Led { // No reason to extend from SevenSegLed,
								/**
	 * 
	 */
	private static final long serialVersionUID = 5973226943454939272L;
	// however that is the purpose of this program
	private SevenSegLed Disp[];
	private RetLed Sign = null;
	private double value = 0;
	private Color bgc = null; // Color behind the 7 segment display(s)

	/**
	 * @param x
	 *  Horizontal location upper left corner
	 *  
	 * @param y
	 *  Vertical location upper left corner
	 *  
	 * @param hsize
	 *  Horizontal Size total of all cells
	 *  
	 * @param vsize
	 *  Vertical Size for all seven segment displays
	 * 
	 * @deprecated As of version xx, replaced by Dy(int x, int y, int hsize, int vsize, int places)
	 *             
	 * @see #Dy(int x, int y, int hsize, int vsize, int places)
	 */
	public Dy(int x, int y, int hsize, int vsize) {
		this(x, y, hsize, vsize, Color.red);
	}
	
	/**
	 * 
	/**
	 * @param x
	 *  Horizontal location upper left corner
	 *  
	 * @param y
	 *  Vertical location upper left corner
	 *  
	 * @param hsize
	 *  Horizontal Size total of all cells
	 *  
	 * @param vsize
	 *  Vertical Size for all seven segment displays
	 *  
	 * @param places
	 * Number of digits to make the display
	 * 
	 * Defaults for the Led's color is Red
	 * 
	 */
	public Dy(int x, int y, int hsize, int vsize, int places){
		this(x,y,hsize,vsize,Color.RED,places);
	}

	/**
	 * @deprecated As of version xx, replaced by Dy(int x, int y, int hsize, int
	 *             vsize, Color fg, int places)
	 * @see #Dy(int x, int y, int hsize, int vsize, Color fg, int places)
	 */
	public Dy(int x, int y, int hsize, int vsize, Color fg) {
		this(x, y, hsize, vsize, fg, 9);
	}

	public Dy(int x, int y, int hsize, int vsize, Color fg, int places) {
		this(x, y, hsize, vsize, fg, null, places);
		// this(x, y, hsize, vsize, fg, Color.BLACK, places);
	}

	/**
	 * @deprecated As of version 1.0, replaced by Dy(int x, int y, int hsize, int vsize, Color fg, Color bg, int places)
	 * @see #Dy(int x, int y, int hsize, int vsize, Color fg, Color bg, int places)
	 */
	public Dy(int x, int y, int hsize, int vsize, Color fg, Color bg) {
		this(x, y, hsize, vsize, fg, bg, 9);
	}

	public Dy(int x, int y, int hsize, int vsize, Color fg, Color bg, int places) {
		super(x, y, hsize, vsize, fg);
		
		if(bg == null) bgc = getOffColor().darker();
		else this.bgc = bg;
		
		int h_size = hsize / (places + 1); // compute cell size + additional
											// cell for minus sign
		int v_size = vsize;
//System.out.println("creating a 7segLed array");
		Disp = new SevenSegLed[places];
		// Sign = new SevenSegLed(x,y, h_size, v_size,c);
		// Sign.setValue(16); // minus sign
		// Sign.setEnable(false);
//System.out.println("Instantiateing the "+ Disp.length + " ssl's");
		for (int i = 0; i < Disp.length; i++) {
			// Disp[i] = new SevenSegLed(x+(i*h_size),y, h_size, v_size,c);
			Disp[i] = new SevenSegLed(x + h_size + (i * h_size), y, h_size,
					v_size, fg);
		}
//System.out.println("Adding the RetLed sign");		
		Sign = new RetLed(x, y
				+ (int) ((v_size - Disp[0].segmentSize.height) / 2),
				Disp[0].segmentSize.width, Disp[0].segmentSize.height, fg);
		//setBackGroundColor(bgc);
//System.out.println("Done with constructor DY");
	}

	public void reset() {
		clear();
		value = 0;
	}

	public void clear() {
		// this.value = 0; Removed use reset()
		for (int i = 0; i < Disp.length; i++) {
			Disp[i].setValue(0);
			// if (Disp[i].isPeriod())
			// Disp[i].setValue('.'); // toggle peroid off
			Disp[i].setPeriod(false);
			Disp[i].off();
		}
		setSign(false);
		compress();
	}

	public void setSign(boolean v) {
		if (v)
			Sign.on();
		else
			Sign.off();
	}
	
	/**
	 * (non-Javadoc)
	 * @see vicw.utils.led.Led#setOnColor(java.awt.Color)
	 */
	public void setOnColor(Color c){
		for(int i=0; i< Disp.length; i++)
			Disp[i].setOnColor(c);
		Sign.setOnColor(c);
		super.setOnColor(c);
	}
	
	public void setOffColor(Color c){
		for(int i=0; i< Disp.length; i++)
			Disp[i].setOffColor(c);
		Sign.setOffColor(c);
		super.setOffColor(c);
	}

	/**
	 * 
	 * @param bg
	 * 	Color displayed behind the LEDs and the Minus sign
	 * 
	 */
	public void setBackGroundColor(Color bg){
		bgc = bg;
		for (int i = 0; i < Disp.length; i++){
			Disp[i].setBackGroundColor(bg);
		}
	}
	public void enhancedLED(boolean e){
		for (int i =0; i < Disp.length; i++)
			Disp[i].enhancedLED(e);
		Sign.enhanced = e;
	}


	/*
	 * Blanks the leading zero's
	 */
	private void compress() {
		Sign.setEnable((this.value < 0));
		for (int i = 0; i < Disp.length; i++)
			if (Disp[i].getValue() == 0)
				Disp[i].setEnable(false);
			else
				break;
	}

	// public int getValue(){
	public double getValue() {
		return this.value;
	}

	public void paint(Graphics g) {
		g.setColor(bgc); // The outer rectangle (only visible around '-' sign
		g.fillRect(x - 1, y - 1, h_Size + 2, v_Size + 2);
		// g.setColor(Color.YELLOW);
		Sign.paint(g);
		// g.setColor(Color.BLUE);
		for (int i = 0; i < Disp.length; i++)
			Disp[i].paint(g);
	}

	// public void resize(){
	// System.out.println("Dy.java resize");
	// }

	public void setEnable(boolean ena) {
		super.setEnable(ena);
		for (int i = 0; i < Disp.length; i++)
			Disp[i].setEnable(ena);
	}

	/*
	 * 
	 * @param String
	 * 
	 * @returns true if String is a valid double
	 */
	public boolean isNumeric(String s) {
		try {
			Double.parseDouble(s);
		} catch (java.lang.NumberFormatException e) {
			return false;
		}
		return true;
	}

	public void setError(boolean v) {
		clear();
		if (v) {
			for (int i = 0; i < Disp.length; i++) {
				Disp[i].setValue('-');
			}
		} else {
			setValue(value);
		}
	}

	public void setValue(String ss) {
		//System.out.println("SetValue(\"" + ss + "\") Disp.length ="
		//		+ Disp.length + " ss.length() = " + ss.length());
		clear();
		if (isNumeric(ss)) {
			this.value = Double.parseDouble(ss);
			// System.out.println("value = "+value);
		} else {
			setError(true);
			return;
		}
		int displ = 0; // Disp.length;
		int i = 0;
		if (ss.charAt(i) == '-') {
			setSign(true);
			i++;
		}
		while (displ < Disp.length && i < ss.length()) {
			//System.out.println("setValue(\"" + ss + "\") Disp[" + displ
			//		+ "] = ss.charAt(" + i + ") = " + ss.charAt(i));
			Disp[displ].setValue(ss.charAt(i));
			if (ss.charAt(i) != '.')
				displ++;
			i++;
		}
	}

	// public void setValue(char c) {
	// Disp[Disp.length - 1].setValue(c);
	// }

	public void setValue(double v) { // shift numbers right
		// System.out.println("Received value of " + v + " as a double");
		String ss = new String(Double.toString(v));
		// System.out.println("toString() " + ss + " ss.length()= " + ss.length());
		this.setValue(ss);
		// this.value=v;
		// for(int i=0; i < ss.length(); i++){
		// System.out.println("Setting value at position "+i+" to "+ss.charAt(i));
		// //if(ss.charAt(i)=='.') setValue('.');
		// this.setValue(ss.charAt(i));
		// }
		//
		//
		// this.value=v;
		// if(v < 0) v=v*-1; //work with positive values
		//
		// int num=0;
		//
		// // Double dd = new Double(v);
		// // System.out.println("Double = "+dd.toString());
		// // String ss = new String(dd.toString());
		//
		// // String ss = new String(Double.toString(v));
		// //
		// System.out.println("Length of String \""+ss+"\" is "+ss.toString().length());
		// // if(-1 != ss.indexOf('.'))
		// // System.out.println("The peroid is found at position " +
		// ss.indexOf('.'));
		// // else System.out.println("This is a whole number");
		//
		// for(int i=Disp.length-1; i >=0; i--){
		// // if(i == ss.indexOf('.')) setValue('.');
		// num= (int) v%10;
		// if(v > 0) setValue(num, i); else Disp[i].setEnable(false);
		// v=(int)(v/10);
		// }
		// for(int i = 0; i <ss.length(); i++){ // Left to Right
		// if(i < Disp.length) Disp[i].setValue(ss.charAt(i));
		// }

		// for (int i = ss.length()-1; i >= 0; i--){ // Right to Left
		// if(i < Disp.length) Disp[i].setValue(ss.charAt(i));
	}

	public void setValue(int v) { // shift numbers right
		// System.out.println("Value received = "+v+" this.value = "+this.value);
		// if (v == 17) {Disp[Disp.length-1].setValue(v); return;} // special
		// number for period
		int absValue;
		if (v < 0) {
			absValue = v * -1;
		} else {
			absValue = v;
		}

		if (absValue > 0xF) {
			// System.out.println("Passing "+v+" to setValue((Double) v)");
			setValue((double) v); // More that one digit
		} else {
			this.value = this.value * 10; // accumulate total value.
			// System.out.println("Shifting this.value = "+this.value);
			// System.out.print("Math = "+this.value);
			if (this.value < 0) { // always work with the same signs
				// System.out.print(" - " + absValue);
				this.value = this.value - absValue;
			} else {
				// System.out.print(" + " + absValue);
				this.value = this.value + absValue;
			}
			// System.out.println(" = "+this.value);

			for (int i = 0; i < Disp.length - 1; i++) {
				if (Disp[i + 1].isPeriod()) {
					Disp[i + 1].setValue('.'); // toggle period off
					Disp[i].setValue('.');
				}
				setValue(Disp[i + 1].getValue(), i);
			}
			setValue(absValue, Disp.length - 1);
		}
		compress();
	}

	/**
	 * 
	 * @param value
	 *            0...15
	 * @param location
	 *            segment mumber 0...8
	 * 
	 */
	private void setValue(int value, int location) {
		if (location < Disp.length) {
			if (0xF >= value && value >= 0) {
				Disp[location].setValue(value);
			}
		}
	}

	public Dimension getSize() {
		// System.out.println("h_Size = " + h_Size+2 + " V_Size = " + v_Size+2);
		return new Dimension(h_Size+2, v_Size+2);
	}

}