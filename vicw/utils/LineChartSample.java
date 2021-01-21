package vicw.utils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class LineChartSample extends Application {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Line Chart Sample");
		//final NumberAxis xAxis = new NumberAxis();
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Number of Month");
		
		xAxis.setAutoRanging(true);
//		xAxis.setForceZeroInRange(false);

		yAxis.setAutoRanging(true);
		//yAxis.autosize();
		//yAxis.forceZeroInRangeProperty();
		yAxis.setForceZeroInRange(false);
		
		final LineChart<String,Number> lineChart = new LineChart<String,Number>(xAxis,yAxis);
		//final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
		lineChart.setTitle("Stock Monitoring, 2010");
		
		XYChart.Series series = new XYChart.Series();
		series.setName("My portfolio");
		
        series.getData().add(new XYChart.Data("a"+1, 23));
        series.getData().add(new XYChart.Data("b"+2, 14));
        series.getData().add(new XYChart.Data("c"+3, 15));
        series.getData().add(new XYChart.Data("d"+4, 24));
        series.getData().add(new XYChart.Data("e"+5, 34));
        series.getData().add(new XYChart.Data("f"+6, 36));
        series.getData().add(new XYChart.Data("g"+7, 22));
        series.getData().add(new XYChart.Data("h"+8, 45));
        series.getData().add(new XYChart.Data("i"+9, 43));
        series.getData().add(new XYChart.Data("j"+10, 17));
        series.getData().add(new XYChart.Data("k"+11, 29));
        series.getData().add(new XYChart.Data("l"+12, 25));
        
        Scene scene = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
        
        stage.setScene(scene);
        stage.show();

		
	}

	public static void main(String[] args) {
		System.out.println("Hello from LineChartSample main");
		launch(args);

	}

}
