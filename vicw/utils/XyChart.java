package vicw.utils;
/**
 * 
 * Plot a graph of X Y Data
 * 
 */

// import java.util.Date;

// import java.awt.Color;
/*
 * Currently designed around DyApplet to give a graphical display of temperature
 * 
 * 20161228
 *  - Found the reason for strings not changing
 *    start(Stage) in the Object overrides the local properities with the Class defaults I assigned a
 *    handel xyc to the Object as is done with main access the Object's properities with the pointer 
 * 
 * 20161227
 *  - Converted X data to string
 *  - Constructor with the 2 label Strings for X and Y 
 * 
 * 20141224
 *  - xyChart
 *  - final now used for Axis and LineChart like used in example not sure why final is needed
 *  - Changed to strings for the time (x axis) instead of doubles giving a clearer representation

 */
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
// import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
// Requires JRE 7.6

	public class XyChart extends Application {

		private String xLabelStr = null; // "Default Time";
		private String yLabelStr = null; // "Default Temperature";
		private String lineChartTitle = null; // "Default DigiTemp";
		private String stageTitle = null; // "Default 3 Digit Dy";
		private String seriesName = null; // "Default Tempurature Log";
		private	CategoryAxis xAxis=null;
		private	NumberAxis yAxis = null;

		private String md[] =  null; //{1,2,3,4,5,6,7,8,9,10,11,12};
		private double md1[] = null; //{23,14,15,24,34,36,22,45,43,17,29,25};
		private static XyChart xyc=null;

//		/*
//		 *  
//		 *  
//		 */
//		public XyChart(Date[] x, double[] y, String[] s, String tzs){
//			this(x,y,s,tzs,"X Data","Y Data");
//		}
		
		/*
		 *
		 * 
		 */
		@SuppressWarnings("deprecation")
		public void initXyChart(Date[] x, double[] y, String[] s, String tzs, String xs, String ys){
			xyc=this;		// Get a handle
//System.out.println("Hello from DyAppletwXyChart branch of VicW.XyChart(Date[], double[], String, String) running as an App");
			xyc.xLabelStr = new String(xs);
			xyc.yLabelStr = new String(ys);
			xyc.lineChartTitle = new String("DigiTemp");
			xyc.stageTitle = new String("3 Digit Dy");
			seriesName = new String("Tempurature Log");
			xyc.md1=y;
//System.out.println("xLabelStr = "+ xyc.xLabelStr + " yLabelStr = " + xyc.yLabelStr);			

			xyc.md=new String[x.length];
			for(int i=0; i < x.length; i++){	// Parse the time out of Dates
				xyc.md[i]= new String(x[i].toLocaleString());//+" "+(x[i].getHours()) +":"+ (x[i].getMinutes()));
//System.out.println("Added " + x[i].toString() +" as "+ xyc.md[i]);
			}
		}
		
		public void init(){
			System.out.println("Yep Init() is called ");
			// start();
		}
		
public void start(){
//System.out.println("Hello from XyChart.start()");
	if (xyc == null){ xyc=this;	// xyc is delcared in main() reference for an applet 
	}
	//if(xyc.xyc.md.length > xyc.xyc.md1.length){
	if(xyc.md.length > xyc.md1.length){
		System.err.println("Oops the number of X and Y values are not equal");
		return;
	} else {
//System.out.println("Launch StartArgs");
		launch();
	}
}	// start()

	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@Override
	public void start(Stage stage) throws Exception {
//System.out.println("Hello from start Stage");
//System.out.println("Hello from Stage with xLabelStr = "+ xyc.xLabelStr + " yLabelStr = " + xyc.yLabelStr);			
		
		
try{
//System.out.println("Start of Try");
		
//System.out.print("Check for null ");
		if (xyc == null) {
//System.out.println(" NULL");
//System.out.println("xyc is null in start(Stage)");
			Platform.exit();
		} else {
//System.out.println("Not Null");

//System.out.println("md=" + xyc.md);
			stage.setTitle(xyc.stageTitle);
			xyc.xAxis = new CategoryAxis();
			xyc.yAxis = new NumberAxis();
//System.out.println("xLabelStr = "+ xyc.xLabelStr + " yLabelStr = " + xyc.yLabelStr);			
			xyc.xAxis.setAutoRanging(true);
			xyc.xAxis.setManaged(true);
			
			xyc.yAxis.setAutoRanging(true);
			xyc.yAxis.setForceZeroInRange(false);

			// creating the chart
//System.out.println("LineChart");

			LineChart<String, Number> lineChart = new LineChart<String, Number>
			(xyc.xAxis, xyc.yAxis);
			
			lineChart.setTitle(xyc.lineChartTitle);
//System.out.println("Stage.getTitle = "+lineChart.getTitle()+" lineChartTitle = "+xyc.lineChartTitle);
			// defining a series
			XYChart.Series series = new XYChart.Series();
			series.setName(xyc.seriesName);
			xyc.xAxis.setLabel(xyc.xLabelStr);
			xyc.yAxis.setLabel(xyc.yLabelStr);

			// populating the series with data
//			if (xyc.xyc.md == null) {
			if (xyc.md == null) {
//				System.out.println("NULL");
			} else {
				for (int i = 0; i < xyc.md.length; i++) {
					series.getData().add(
//							new XYChart.Data((xyc.xyc.md[i]), xyc.md1[i]));
							new XYChart.Data((xyc.md[i]), xyc.md1[i]));
				}

				Scene scene = new Scene(lineChart, 800, 600);
				lineChart.getData().add(series);

				stage.setScene(scene);
			}
			stage.show();
//System.out.println("Exit from  start stage");
		}
} catch(Exception e){
	new Help("XYChart.start(Stage) exception", e.toString());
	System.err.println("Start Stage exception " + e.getMessage());
}

	}	// start(Stage)
	     
	/*
	 * 	Default constructor is needed for both main and Launch()
	 *  beeing called by launch is probably why all the local properities are hidden
	 *  Might be better to chand the added constructors to initilazing methodes 
	 * 
	 */
	     public XyChart(){	// Called by both Main and Launch()
	    // Just for main	
	    	 System.out.println("Now how did we get here to the default constructor");
	     }
			
	    public static void main(String[] args) {
	    	//if(args == null) args = new String[]  {"no","values"};
	    	xyc = new XyChart();
//System.out.println("main1 md="+xyc.md);
	    	String[] d =  {"1","2","3","4","5","6","7","8","9"};
	    	double[] d1 = {1,2,3,4,5,6,7,8,9};
	    	//double[][] ttmm = {{1,2},{2,3}};
	    	xyc.md = d;
	    	xyc.md1 = d1;
//System.out.println("main2 md="+xyc.md);
	    	xyc.md1[5]=0;
	        //launch(args);
//System.out.println("Launch()");
	    	launch();
//System.out.println("Done");
	    }
	    
	    /*
	     * Trying to reuse this class
	     * Just cant seem to destroy it for reuse
	     * 
	     */
	public void destroy() {
		xyc.xAxis = null;
		xyc.yAxis = null;
		try {
			xyc.stop();
			try {
				xyc.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("xyc.destroy() with Exception " + e);
		}
		xyc = null;
		Platform.exit();
	}

	}	//  class XyChart

	
