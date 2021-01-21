package vicw.utils.led;

import java.awt.*;

public class PolyGonLed extends Led {

 
/**
	 * 
	 */
	private static final long serialVersionUID = 8075558713529242083L;
static final public int
			  LEFT   = -1,                 // angle the display
			  RIGHT  = 1,
			  NORMAL = 0;
   private int tilt=NORMAL;

   //   protected int highLightCenterX,  highLightCenterY;        //    with locations 
   //   protected int v_HighLightSize, h_HighLightSize;
   //
   //   protected int x_Rim, y_Rim,
   //                      v_Size_Rim, h_Size_Rim;  // boundry (darker) outline of the LED
   //
   //   protected int x_On, y_On,
   //                      v_Size_On, h_Size_On;     // Location for painting the LED When enabled (ON)   
   //
   //   protected int x, y,
   //                      v_Size,h_Size;              // Location for painting the LED (upper right), size is the Diameter
   private Polygon Poly_Hlc;
   private Polygon Poly_Rim;
   private Polygon Poly_On;
   private Polygon Poly_P;

   public PolyGonLed (int x, int y,  int hsize, int vsize, Color ledColor){
	  this(x, y, hsize, vsize, ledColor, NORMAL);
   }   
   public PolyGonLed (int x, int y,  int hsize, int vsize, Color ledColor, int tilt){
	  super(x, y, hsize, vsize, ledColor);
	  this.tilt=tilt;
	  polyLedMetrics();
   }   
   public void paint(Graphics g){
	   if(on){
		 g.setColor(highLightColor.brighter());          // this is outer area "halo effect"
//         g.fillPolygon(px, py, px.length);
		 g.fillPolygon(Poly_P);
		 g.setColor(offColor);                           
	  } else {
		 g.setColor(offColor.darker());
	  }
//      g.fillPolygon(px_Rim, py_Rim, px_Rim.length);      // a darker ring defining the edge of the vicw.utils.led
	  if(on) g.fillPolygon(Poly_Rim);      // a darker ring defining the edge of the vicw.utils.led
	  else if(enhanced)  g.fillPolygon(Poly_Rim);      // a darker ring defining the edge of the vicw.utils.led
	  if(on) g.setColor(onColor); else g.setColor(offColor);
//      g.fillPolygon(px_On, py_On, px_On.length);          // the main body of the LED
	  g.fillPolygon(Poly_On);          // the main body of the LED

	  if(on) {                                           // make a light spot off center
		 g.setColor(highLightColor);
		 //g.fillPolygon(px_Hlc, py_Hlc, px_Hlc.length);
		 g.fillPolygon(Poly_Hlc);
	  }

   }   
   /*
	*    the graphics methods for polygons require an array of points instead of integers 
	*           the LedMetrics() method is overridden to adapt for multisided shapes.
	*     changes: 
	*          4/98 with the addition of Tilt LedMetrics was changed to polyLedMetrics to prevent it's
	*     being called from the constructor (before tilt was enabled).
	*           4/98 I found the Polygon class changed PolyGonLed to work with Polygon instead of int[]
	*/
   protected void polyLedMetrics(){
	 if(Poly_Hlc == null) Poly_Hlc = new Polygon();
	 setPolyArray(Poly_Hlc, highLightCenterX, highLightCenterY, h_HighLightSize, v_HighLightSize);

	 if(Poly_Rim == null) Poly_Rim = new Polygon();
	 setPolyArray(Poly_Rim, x_Rim, y_Rim, h_Size_Rim, v_Size_Rim);

	 if(Poly_On == null) Poly_On = new Polygon();
	 setPolyArray(Poly_On, x_On, y_On, h_Size_On, v_Size_On);

	 if(Poly_P == null) Poly_P = new Polygon();
	 setPolyArray(Poly_P, x, y, h_Size, v_Size);
   }   
   // setPolyArray is a support function for polygons LedMetrics()
   private void setPolyArray(Polygon p, int x, int y, int hSize, int vSize){
	   //       v_Size_On, h_Size_On  from Led
	  int offset =0;                       // the distance the tips are offset from a 4 sided retangle
	  if (vSize > hSize) offset = hSize/3; else  offset = vSize/3;
//      int hSize=0;
//      if(h_Size > offset) hSize = h_Size-offset;     // adjust width so polygon is contained inside expected retangle
//      int tilt = this.tilt*h_Size_On/2;

	  p.addPoint(x+offset+tilt,             y);
	  p.addPoint(x+hSize-offset+tilt,     y);
	  p.addPoint(x+hSize + (tilt/3 * 2),  y+offset);
	  p.addPoint(x+hSize - (tilt/3 * 2),  y+vSize-offset);
	  p.addPoint(x+hSize-offset-tilt,     y+vSize);
	  p.addPoint(x+offset-tilt,              y+vSize);
	  p.addPoint(x - (tilt/3 * 2),           y+vSize-offset);
	  p.addPoint(x  + (tilt/3 * 2),          y+offset);
	  //if(tilt == LEFT) p.translate(10,10);
	  //if(tilt == RIGHT) p.translate(-10,-10);
//      p.addPoint(x+offset,  y);
//      p.addPoint(x+hSize-offset,     y);
//      p.addPoint(x+hSize,               y+offset);
//      p.addPoint(x+hSize,               y+vSize-offset);
//     p.addPoint(x+hSize-offset,     y+vSize);
//      p.addPoint(x+offset,              y+vSize);
//      p.addPoint(x,                        y+vSize-offset);
//      p.addPoint(x,                        y+offset);
   }   
}