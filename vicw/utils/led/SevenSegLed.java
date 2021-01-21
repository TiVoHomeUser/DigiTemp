package vicw.utils.led;

import java.awt.*;

/*
 *  Nov 2010 
 *  Added accessor setPeriod(boolean) sets period on or off
 *  Changed behavior of setValue('.') no longer "Toggles"  the period just sets it.
 *  
 */
public class SevenSegLed extends Led {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8974164584670428459L;
	public static final int LEFT = PolyGonLed.LEFT;
	public static final int RIGHT = PolyGonLed.RIGHT;
	public static final int NORMAL = PolyGonLed.NORMAL;
	private int tilt = NORMAL;

	private int value = 0;

	private Led[] Segment = new Led[7];
	private RoundLed Period;

	protected Dimension segmentSize;
	private Color bgc = null;

	public SevenSegLed(int x, int y, int hsize, int vsize, Color ledColor) {
		this(x, y, hsize, vsize, ledColor, NORMAL);
	}

	public
	SevenSegLed(int x, int y, int hsize, int vsize, Color ledColor, int tilt) {
		super(x, y, hsize, vsize, ledColor);
//System.out.println("Hello from SSL constructor");
		this.tilt = tilt;
		offColor = ledColor.darker();
		bgc = offColor.darker();
//System.out.println("Calling ssl_setSize()");
		ssl_setSize();
		// System.out.println( getClass().getName() + "Tilt = "+this.tilt);
//System.out.println("End of SSL constructor");
	}

	// getValue() another bonus for soft-ware Led display.
	public int getValue() {
		return this.value;
	}
 
	public void setPeriod(boolean v) {
		if (v == true)
			Period.on();
		else
			Period.off();
	}
	
	/* (non-Javadoc)
	 * @see vicw.utils.led.Led#setOnColor(java.awt.Color)
	 */
	@Override
	public void setOnColor(Color c) {
		for (int i = 0; i < Segment.length; i++)
			Segment[i].setOnColor(c);
		Period.setOnColor(c);
		super.setOnColor(c);
	}

	
	/* (non-Javadoc)
	 * @see vicw.utils.led.Led#setOffColor(java.awt.Color)
	 */
	@Override
	public void setOffColor(Color c) {
		for (int i = 0; i < Segment.length; i++)
			Segment[i].setOffColor(c);
		Period.setOffColor(c);
		super.setOffColor(c);
	}
	/**
	 * 
	 * @param c
	 *  Color displayed behind the LED's
	 */
	public void setBackGroundColor(Color c){
		bgc = c;
	}

	public boolean isPeriod() {
		return Period.isOn();
	}
	
	
	public void paint(Graphics g) {
		g.setColor(bgc);
		g.fillRect(x, y, h_Size, v_Size);
		// paint leds that are off under the Leds that are On forcing the corona
		// to fall on (paint over) the dark leds
		for (int i = 0; i < Segment.length; i++) {
			if (!Segment[i].isOn())
				Segment[i].paint(g); // Draw Led's that are off
		} /* endfor */

		for (int i = 0; i < Segment.length; i++) {
			if (Segment[i].isOn())
				Segment[i].paint(g); // Draw Led's that are on
		} /* endfor */
		Period.paint(g);
	}

	/* (non-Javadoc)
	 * @see vicw.utils.led.Led#setEnable(boolean)
	 * 
	 * supplement Led's setEnable(boolean) to adjust all seven Led members
	 * (segments) and the RoundLed Period
	 */
	@Override
	public void setEnable(boolean enable) {
		super.setEnable(enable); 
		if (enable)
			setValue(value);
		else{
			// Disable all segments
			for (int i = 0; i < Segment.length; i++)
				Segment[i].setEnable(false);
		}
		
		Period.setEnable(enable);
	}
	
	/* (non-Javadoc)
	 * @see vicw.utils.led.Led#enhancedLED(boolean)
	 * 
	 */
	@Override
	public void enhancedLED(boolean e){
		super.enhancedLED(e);		
		// enhanced = e; extra code this is done with the above call
		for (int i=0; i < Segment.length; i++){
			Segment[i].enhancedLED(e);
		}
		Period.enhancedLED(e);
	}

	private void ssl_setSize() {
		/*
		 * The ratio for the length and width of the segments are computed from
		 * the length and width of the Seven Segment LED. the actual size is
		 * computed from the total number of lenths and widths that should add
		 * up to the perimeter. the vertical and horizontal lengths are then
		 * adjusted by a fudge factor to force the segments to fill the SSL.
		 */
		// I found that some "white space" is needed around the border of the
		// vicw.utils.led
		// so the size of the vicw.utils.led-segment and its position is
		// adjusted accordingly
		// and now extra space is needed for the period.

		int h_Size = this.h_Size - (this.h_Size / 10), v_Size = this.v_Size
				- (this.v_Size / 10); // adjustment, allows for a border of 1/10
										// size

		int x = this.x + (this.h_Size / 20), y = this.y + (this.v_Size / 20);
		// position just a little down and to the right

		double perimeter = (2.0 * (double) h_Size) + (2.0 * (double) v_Size);
		double ratio;
		// if (v_Size < h_Size) { // allows for fat leds
		// ratio = ((double) h_Size/ (double) v_Size) * 1.5;
		// } else {
		ratio = ((double) v_Size / (double) h_Size) * 1.5;
		// } /* endif */

		// double pw = 10.0 + (6.0*ratio ); // perimeter of widths
		double pw = 5 + (6.0 * ratio); // perimeter of widths with half width
										// spaceing
		int width = (int) (perimeter / pw);
		int length = (int) (perimeter / pw * ratio);
		// NOTE: working with double precision is required watch the casting by
		// the ()
		double hfudge = (double) (h_Size - (double) (length + width));
		double vfudge = (double) (v_Size - ((length * 2.0) + width));
		int hlength = (int) (length + hfudge);
		int vlength = (int) (length + (vfudge / 2.0));
		segmentSize = new Dimension(hlength, width); // used by display for
														// creating the minus
														// sign

		// 5___ 4_ 3_
		// 0 |6_| 4 5 |_6|__| 2
		// 1 |__| 3 0 1
		// 2
		int halfwidth = width / 2;
		Segment[0] = new PolyGonLed(x, y + halfwidth, width, vlength, onColor,
				tilt);
		Segment[1] = new PolyGonLed(x, y + vlength + halfwidth, width, vlength,
				onColor, tilt);
		Segment[2] = new PolyGonLed(x + halfwidth, y + vlength + vlength,
				hlength, width, onColor);
		Segment[3] = new PolyGonLed(x + hlength, y + vlength + halfwidth,
				width, vlength, onColor, tilt);
		Segment[4] = new PolyGonLed(x + hlength, y + halfwidth, width, vlength,
				onColor, tilt);
		Segment[5] = new PolyGonLed(x + halfwidth, y, hlength, width, onColor);
		Segment[6] = new PolyGonLed(x + halfwidth, y + vlength, hlength, width,
				onColor);
		Period = new RoundLed(this.x - halfwidth, this.y + this.v_Size - width,
				width, onColor);
	}

	public void setValue(char c) {
		switch (c) {
		case '0':
			setValue(0);
			break;
		case '1':
			setValue(1);
			break;
		case '2':
			setValue(2);
			break;
		case '3':
			setValue(3);
			break;
		case '4':
			setValue(4);
			break;
		case '5':
			setValue(5);
			break;
		case '6':
			setValue(6);
			break;
		case '7':
			setValue(7);
			break;
		case '8':
			setValue(8);
			break;
		case '9':
			setValue(9);
			break;
		case '-':
			setValue(16);
			break;
		case '.':
			Period.setEnable(true);
			// Period.setEnable(!Period.isOn());
			// setValue(17);
			break;
		default:
			setValue(-1); // forces all leds off
			break;
		} /* endswitch */
	}

	/*
	 * take int values 0..9 set the proper leds
	 */
	public void setValue(int v) {
		/*
		 * Setting all seven segments for each integer would require less
		 * performance overhead for now just turn them all off.
		 */
		// setEnable(false); // Oh-oh, avoid recursive calls
		Segment[0].setEnable(false);
		Segment[1].setEnable(false);
		Segment[2].setEnable(false);
		Segment[3].setEnable(false);
		Segment[4].setEnable(false);
		Segment[5].setEnable(false);
		Segment[6].setEnable(false);
		// 5___
		// 0 |6_| 4
		// 1 |__| 3
		// 2
		switch (v) {
		case 0:
			Segment[0].setEnable(true);
			Segment[1].setEnable(true);
			Segment[2].setEnable(true);
			Segment[3].setEnable(true);
			Segment[4].setEnable(true);
			Segment[5].setEnable(true);
			break;
		case 1:
			Segment[4].setEnable(true);
			Segment[3].setEnable(true);
			break;
		case 2:
			Segment[1].setEnable(true);
			Segment[2].setEnable(true);
			Segment[4].setEnable(true);
			Segment[5].setEnable(true);
			Segment[6].setEnable(true);
			break;
		case 3:
			Segment[2].setEnable(true);
			Segment[3].setEnable(true);
			Segment[4].setEnable(true);
			Segment[5].setEnable(true);
			Segment[6].setEnable(true);
			break;
		case 4:
			Segment[0].setEnable(true);
			Segment[3].setEnable(true);
			Segment[4].setEnable(true);
			Segment[6].setEnable(true);
			break;
		case 5:
			Segment[0].setEnable(true);
			Segment[2].setEnable(true);
			Segment[3].setEnable(true);
			Segment[5].setEnable(true);
			Segment[6].setEnable(true);
			break;
		case 6:
			Segment[0].setEnable(true);
			Segment[1].setEnable(true);
			Segment[2].setEnable(true);
			Segment[3].setEnable(true);
			Segment[5].setEnable(true);
			Segment[6].setEnable(true);
			break;
		case 7:
			Segment[3].setEnable(true);
			Segment[4].setEnable(true);
			Segment[5].setEnable(true);
			break;
		case 8:
			Segment[0].setEnable(true);
			Segment[1].setEnable(true);
			Segment[2].setEnable(true);
			Segment[3].setEnable(true);
			Segment[4].setEnable(true);
			Segment[5].setEnable(true);
			Segment[6].setEnable(true);
			break;
		case 9:
			Segment[0].setEnable(true);
			Segment[2].setEnable(true);
			Segment[3].setEnable(true);
			Segment[4].setEnable(true);
			Segment[5].setEnable(true);
			Segment[6].setEnable(true);
			break;
		case 10: // 'a'
			Segment[1].setEnable(true);
			Segment[2].setEnable(true);
			Segment[3].setEnable(true);
			Segment[4].setEnable(true);
			Segment[5].setEnable(true);
			Segment[6].setEnable(true);
			// A
			// Segment[0].setEnable(true);
			// Segment[1].setEnable(true);
			// Segment[3].setEnable(true);
			// Segment[4].setEnable(true);
			// Segment[5].setEnable(true);
			// Segment[6].setEnable(true);
			break;
		case 11: // 'b'
			Segment[0].setEnable(true);
			Segment[1].setEnable(true);
			Segment[2].setEnable(true);
			Segment[3].setEnable(true);
			Segment[6].setEnable(true);
			break;
		case 12: // 'c'
			Segment[1].setEnable(true);
			Segment[2].setEnable(true);
			Segment[6].setEnable(true);
			// C
			// Segment[0].setEnable(true);
			// Segment[1].setEnable(true);
			// Segment[2].setEnable(true);
			// Segment[5].setEnable(true);
			break;
		case 13: // 'd'
			Segment[1].setEnable(true);
			Segment[2].setEnable(true);
			Segment[3].setEnable(true);
			Segment[4].setEnable(true);
			Segment[6].setEnable(true);
			break;
		case 14: // 'e'
			// Segment[0].setEnable(true);
			// Segment[1].setEnable(true);
			// Segment[2].setEnable(true);
			// Segment[4].setEnable(true);
			// Segment[5].setEnable(true);
			// Segment[6].setEnable(true);
			// 'E'
			Segment[0].setEnable(true);
			Segment[1].setEnable(true);
			Segment[2].setEnable(true);
			Segment[5].setEnable(true);
			Segment[6].setEnable(true);
			break;
		case 15: // 'F'
			Segment[0].setEnable(true);
			Segment[1].setEnable(true);
			Segment[5].setEnable(true);
			Segment[6].setEnable(true);
			break;
		case 16: // '-'
			Segment[6].setEnable(true);
			v = this.value; // restore previous value for this object
			break;
		// case 17:
		// Period.setEnable(!Period.isOn());
		// v=this.value; // restore previous value of this object;
		// break;
		default: // leave all segments disabled for invalid values
			return;
			// break;
		} /* endswitch */
		this.value = v;
		return;
	}
}