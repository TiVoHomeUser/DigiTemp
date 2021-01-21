package vicw.utils.led;

/*
 *                      Simple little LED class 1998 VWHEELER
 *
 *          a modular class that mimics a real Light Emitting Diode
 *          
 *
 */
import java.awt.*;

public class RoundLed extends Led {

	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;

	public RoundLed(int x, int y, int size) { // Location horz , vert, diameter
		this(x, y, size, Color.red); // Default's to standard RED Led when no
										// color is given
	}

	public RoundLed(int x, int y, int size, Color ledColor) { // Location horz ,
																// vert,
																// Diameter ,
																// Color
		super(x, y, size, size, ledColor);
	}

	// public static void main(String args[]){
	//
	// RoundLed rl = new RoundLed(5,5,10,Color.RED);
	// rl.on();
	// rl.init();
	// Frame f = new Frame();
	//
	// f.addWindowListener(new java.awt.event.WindowAdapter() {
	// public void windowClosing(java.awt.event.WindowEvent e) {
	// System.exit(0);
	// };
	// });
	//
	// f.add(rl);
	// f.pack();
	// // wm.f.setSize(200, 200 + 22);
	// System.out.println("top inset ="+wm.f.getInsets().top);
	// f.setSize(rl.wsize.width,rl.wsize.height+f.getInsets().top);
	// f.setVisible(true);
	// }
	/*
	 * LED does not call paint on it's own this method is to be called from the
	 * object that contains this LED,, hmmm how do java beans manage this? that
	 * is something to do at a future time when I'm working with Beans
	 */
	public void paint(Graphics g) {

		if (on) {
			g.setColor(highLightColor.brighter()); // this is outer area
													// "halo effect"
			g.fillOval(x, y, h_Size, v_Size);

			g.setColor(offColor);
		} else {
			g.setColor(offColor.darker());
		}

		// a darker ring defining the edge of the vicw.utils.led
		if(enhanced) g.fillOval(x_Rim, y_Rim, h_Size_Rim, v_Size_Rim);

		if (on)
			g.setColor(onColor);
		else
			g.setColor(offColor);
		g.fillOval(x_On, y_On, h_Size_On, v_Size_On); // the main body of the
														// LED

		if (on) { // make a light spot off center
			g.setColor(highLightColor);
			g.fillOval(highLightCenterX, highLightCenterY, h_HighLightSize,
					v_HighLightSize);
		}
	}
}