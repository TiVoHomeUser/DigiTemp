package vicw.utils.led;

import java.awt.*;

/*
 * Rectangular vicw.utils.led instantiates abstracted class Led as a square or retangular Led 
 *   as a square vicw.utils.led if only one value of size is given or both are the same.
 *
 */
public class RetLed extends Led {

	/**
	 * 
	 */

	private static final long serialVersionUID = 4599871450233140937L;

	public RetLed(int x, int y, int hsize, int vsize, Color ledColor) {
		super(x, y, hsize, vsize, ledColor);
	}

	public RetLed(int x, int y, int size, Color ledColor) {
		super(x, y, size, size, ledColor);
	}

	public void paint(Graphics g) {
		if (on) {
			g.setColor(highLightColor.brighter()); // this is outer area
													// "halo effect"
			g.fillRect(x, y, h_Size, v_Size);

			g.setColor(offColor);
		} else {
			g.setColor(offColor.darker());
		}
		if(enhanced) g.fillRect(x_Rim, y_Rim, h_Size_Rim, v_Size_Rim);
															// a darker ring
															// defining the edge
															// of the
															// vicw.utils.led

		if (on)
			g.setColor(onColor);
		else
			g.setColor(offColor);
		g.fillRect(x_On, y_On, h_Size_On, v_Size_On); // the main body of the
														// LED

		if (on) { // make a light spot off center
			g.setColor(highLightColor);
			g.fillRect(highLightCenterX, highLightCenterY, h_HighLightSize,
					v_HighLightSize);
		}

	}
}