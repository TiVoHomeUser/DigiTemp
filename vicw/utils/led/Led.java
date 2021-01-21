package vicw.utils.led;

/**
 * @author Victor Wheeler myJavaUtils@vicw.net
 * 
 *                      Simple little LED class 1998 VWHEELER
 *
 *          a modular class that attempts to mimic a real Light Emitting Diode
 *            (anything done with hard ware can be emulated in software)
 *          
 *
 */

/**
 *             Led is an abstract class the paint method and a constructor that calls
 *             super(int x, int y,  int vsize, int hsize, Color ledColor) require implementation
 *             in the instantiated class
 *
 *             Led initializes a basic shape (rectangle or round) Led for display with shadowing
 *                and highlight with the associated Colors
 *
 *
 */
import java.awt.*;
import java.io.Serializable;
/**
 * 
 * @author Victor Wheeler
 *
 */
public abstract class Led implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4530976558491800692L;
	protected Color onColor = Color.red; //null; // color when LED is Active
	protected Color offColor = null; // color for unlit LED;

	protected Color highLightColor = null; // the bright spot for the center
	protected boolean enhanced = true; // allows removing the enhanced borders
	protected int highLightCenterX, highLightCenterY; // with locations
	protected int v_HighLightSize, h_HighLightSize;

	protected boolean on = false; // Default to UN-LIT LED
	protected int x_Rim, y_Rim, v_Size_Rim, h_Size_Rim; // boundry (darker)
														// outline of the LED

	protected int x_On, y_On, v_Size_On, h_Size_On; // Location for painting the
													// LED When enabled (ON)

	protected int x, y, v_Size, h_Size; // Location for painting the LED (upper
										// right), size is the Diameter

	/**
	 * public void paint(Graphics g){
	 * 
	 * if(on){ g.setColor(highLightColor.brighter()); // this is outer area
	 * "halo effect" g.fillOval(x, y, h_Size, v_Size);
	 * 
	 * g.setColor(offColor); } else { g.setColor(offColor.darker()); }
	 * g.fillOval(x_Rim, y_Rim, h_Size_Rim, v_Size_Rim); // a darker ring
	 * defining the edge of the vicw.utils.led
	 * 
	 * if(on) g.setColor(onColor); else g.setColor(offColor); g.fillOval(x_On,
	 * y_On, h_Size_On, v_Size_On); // the main body of the LED
	 * 
	 * if(on) { // make a light spot off center g.setColor(highLightColor);
	 * g.fillOval(highLightCenterX, highLightCenterY, h_HighLightSize,
	 * v_HighLightSize); } }
	 */
	@SuppressWarnings("unused")
	/**
	 * Default constructor not allowed.
	 */
	private Led() { // If Led was declared static a default constructor would be
					// called resulting an init() error
		System.out.println("Constructor with no arguments is not allowed");
		System.exit(0);
	}

	/**
	 * 
	 * @param x
	 *            horizontal starting location on panel for display
	 * @param y
	 *            Vertical starting location on panel for display
	 * @param hsize
	 *            Horizontal size of Led
	 * @param vsize
	 *            Vertical size of Led
	 * @param ledColor
	 *            base Color for the led
	 */
	public Led(int x, int y, int hsize, int vsize, Color ledColor) {
//System.out.println("Hello from constructor for Led(int,int,int,int,Color)");
		// Location horz , vert, Diameter , Color
		this.x = x; // to enhance the halo effect the vicw.utils.led grows when
					// on
		this.y = y;
		// setSize(hsize, vsize);
							// compute the vicw.utils.led enhancements bright
							// spots and shadows
			v_Size = vsize;		// System.out.print(" v = "+v);
			h_Size = hsize;		// System.out.println("  h = "+h);
			onColor = ledColor;
		// setOnColor(ledColor);
//System.out.println("end of constructer LED");
	}

	// constructor that makes a vicw.utils.led of unknown size, Led.reSize() is
	// called to define
	// the size of the vicw.utils.led when it is needed for display.
	/**
	 * Makes a default unknown size Use Led.reSize() when the Led is needed for
	 * display
	 * @param ledColor base Color for the led
	 */
	public Led(Color ledColor) {
		this(0, 0, 0, 0, ledColor);
	}

	/**
	 * 
	 * @return the Leds color used for the Highlighted areas
	 * 
	 */
	Color getHighLightColor() {
		return highLightColor;
	}

	/**
	 * 
	 * @return the Leds Color when OFF (inactive)
	 * 
	 */
	Color getOffColor() {
		return offColor;
	}

	/**
	 * 
	 * @return Leds Color when ON (active)
	 * 
	 */
	Color getOnColor() {
		return onColor;
	}

	/**
	 * 
	 * @param c
	 *            Color displayed for the inactive (OFF) Led
	 */
	void setOffColor(Color c) {
		offColor = c;
	}

	/**
	 * 
	 * @param c
	 *            Color displayed for the active (ON) Led
	 */
	public void setOnColor(Color c) {
//System.out.println("Hello from LED.setOnColor");
		onColor = c;
//System.out.println("from setOnColor calling update()");
		update();
//		if(enhanced){
//			this.offColor = c.darker();
//			this.highLightColor = lighten(c.brighter());
//				// make an even brighter color for hotspots
//		} else {
//			highLightColor = offColor;
		}
	
	/**
	 *  Called internally to refresh the computed members
	 */
	private void update(){
		if(enhanced){
			offColor = onColor.darker();
			this.highLightColor = lighten(onColor.brighter());
				// make an even brighter color for hotspots
		} else {
			highLightColor = onColor;
		}
		
		ledMetrics();
	}

	/**
	 * 
	 * @return true the Led is ON (active) false the Led is OFF (inactive)
	 * 
	 *         Note: Unlike real LEDs that do not report if they are on or off
	 *         this is a bonus for the calling program
	 * 
	 */
	public boolean isOn() { // true LED is active, false LED is off
		if (true == on) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Computes the size and locations of the Led's associated display areas
	 * 
	 * saves calculated data in member variables computing the Led's locations
	 * only once keeping the overhead in paint to a minimum
	 * 
	 */
	protected void ledMetrics() {
		// First compute the locations to draw the Led enhancements (ovals or
		// Rectangles)
		int x_Halo = 0, y_Halo = 0;

		if (5 > h_Size) { // prevent invalid computed values, enhancements
			x_Rim = x_On = x; // would not be visible any way. 2 is the absolute
			h_Size_Rim = h_Size_On = h_Size; // minimum value that will compute
												// and run
		} else { // Lets get fancy with some shading
			x_Halo = (int) ((h_Size / 10.0d) + 0.9d); // adjust halo size
														// allowing for larger
														// leds

			x_Rim = x + (int) x_Halo / 2; // rim is the darker more defined
											// boundry for the LED
			h_Size_Rim = h_Size - x_Halo; // this is the second ring in the
											// vicw.utils.led

			x_On = x_Rim + (int) x_Halo / 2; // the bright area of the
												// vicw.utils.led.
			h_Size_On = h_Size_Rim - x_Halo; // the third ring from the outside
		} /* endif */

		if (5 > v_Size) { // prevent invalid computed values, enhancements
			y_Rim = y_On = y; // would not be visible any way. 2 is the absolute
			v_Size_Rim = v_Size_On = v_Size; // minimum value that will compute
												// and run
		} else { // Lets get fancy with some shading
			y_Halo = (int) ((v_Size / 10.0d) + 0.9d); // adjust halo size
														// allowing for larger
														// leds

			y_Rim = y + (int) y_Halo / 2; // rim is the darker more defined
											// boundry for the LED
			v_Size_Rim = v_Size - y_Halo; // this is the second ring in the
											// vicw.utils.led

			y_On = y_Rim + (int) y_Halo / 2; // the bright area of the
												// vicw.utils.led.
			v_Size_On = v_Size_Rim - y_Halo; // the third ring from the outside
		} /* endif */

		// System.out.println(" x_halo = "+x_Halo+" y_Halo = "+ y_Halo);
		// System.out.println("    x = "+x+ "      y=" +y+ "       v_size="+
		// v_Size+"  h_Size="+ h_Size);
		// System.out.println("x_rim = "+x_Rim+"  y_rim="
		// +y_Rim+"   h_size_rim="+ h_Size_Rim+" v_size_rim="+ v_Size_Rim);
		// System.out.println(" x_on = "+x_On+ "  y_on =" +y_On+
		// "    h_size_on="+ h_Size_On+"    v_size_on="+ v_Size_On);

		// HighLight values are calculated here to limit the overhead in paint
		// these values
		// are used to enhance a portion of the vicw.utils.led making it look
		// more 3d with the
		// g.fillOval(highLightCenterX, highLightCenterY, v_HighLightSize,
		// h_HighLightSize);
		int x_HighLightSize = (int) h_Size / 3 + 1;
		int y_HighLightSize = (int) v_Size / 3 + 1;
		highLightCenterX = x + (int) h_Size / 2 - x_HighLightSize / 2;

		if (v_Size == h_Size) { // adjust for round or square leds
			highLightCenterY = y + (int) v_Size / 2 - y_HighLightSize / 2 - 2;
			h_HighLightSize = x_HighLightSize + 2;
			v_HighLightSize = y_HighLightSize - 2;
		} else { // Highlight follows Led's outline
			highLightCenterY = y + (int) v_Size / 2 - y_HighLightSize / 2;
			h_HighLightSize = x_HighLightSize;
			v_HighLightSize = y_HighLightSize;
		}
		// verify valid limits of calculations no negative numbers
		if (0 > highLightCenterX)
			highLightCenterX = 0;
		if (0 > highLightCenterY)
			highLightCenterY = 0;
		if (0 > v_HighLightSize)
			v_HighLightSize = 0;
		if (0 > h_HighLightSize)
			h_HighLightSize = 0;

		// System.out.println("highLightColor = " + highLightColor.toString());
		// System.out.println("onColor =        " + onColor.toString());
		// System.out.println("offColor =       " + offColor.toString());
	}

	/**
	 * The normal default colors (red,blue,green,cyan ...) do not lighten at all
	 * this function lightens any color with a value of zero to a default of 120
	 * thus making the spot lighter for most colors even black
	 */
	protected Color lighten(Color c) { // java.awt.Color.brighter() does not
										// lighten primary colors
		int blue = c.getBlue();
		int green = c.getGreen();
		int red = c.getRed();
		if (0 == blue)
			blue = 120; // lighten colors with 0 values only
		if (0 == green)
			green = 120;
		if (0 == red)
			red = 120;
		return new Color(red, green, blue);
	}

	/**
	 * Turns the LED OFF (inactive)
	 * 
	 */
	public void off() { // turn LED off
		on = false;
	}

	/**
	 * Turns the led ON (active)
	 * 
	 */
	public void on() { // turn LED on (active)
		on = true;
	}

	/**
	 * Basically the only difference between the the shape of the Leds is which
	 * graphic method is called (Square and Round leds)
	 * 
	 * LED does not call paint on it's own this method is to be called from the
	 * object that contains this LED,
	 * 
	 */
	/*
	 * TODO hmmm how do java beans manage this? that is something to do at a
	 * future time when I'm working with Beans
	 */
	abstract public void paint(Graphics g);

	/**
	 * @deprecated
	 * use setOnColor(Color ledColor)
	 * 
	 * @param ledColor
	 *            Sets the Color for the led when ON Then computes the Colors
	 *            used when the led is OFF and the highlight Color.
	 * 
	 */

	public void setColor(Color ledColor) {		
		setOnColor(ledColor);
	}
	
	public void enhancedLED(boolean e){
		enhanced = e;
		update();		
	}
	/**
	 * 
	 * @param enable
	 *            true turns the Led ON (enabled) false turns the Led OFF
	 *            (disabled)
	 * 
	 */

	public void setEnable(boolean enable) { // another bonus (real Leds don't do
											// this without additional
											// circuitry)
		if (true == enable) {
			on();
		} else {
			off();
		}
	}

	/**
	 * 
	 * @param diameter
	 *            Sets the size of the led Horizontal and Vertical is set to
	 *            Diameter
	 * 
	 *            I was not going to include, Real Leds don't change shape ...
	 *            why not go for it!
	 * 
	 */
	public void setSize(int diameter) {
		setSize(diameter, diameter);
	}

	/**
	 * 
	 * @param h
	 *            sets the Horizontal size of the Led
	 * @param v
	 *            sets the Vertical size of the Led
	 */
	public void setSize(int h, int v){	// call direct for rectangles
		v_Size = v;						// System.out.print(" v = "+v);
		h_Size = h;						// System.out.println("  h = "+h);
		update();
		//this.ledMetrics(); // compute the vicw.utils.led enhancements bright
							// spots and shadows
	}
}	//	public class Led 

