package vectorVisualizer.viewer;

import java.util.ArrayList;

import org.jzy3d.chart.AWTChart;
import org.jzy3d.colors.Color;
import org.jzy3d.javafx.JavaFXChartFactory;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.*;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import vectorVisualizer.vectors.Vector;

/**
 * The main class of the program, this initializes the Graph and Menu displays and handles the interconnect between them
 * @author Robert "Drew" Weimer
 */
public class Graph extends Application
{
	/**
	 * The collection of lines aka vectors on the graph
	 */
	private static ArrayList<CroppableLineStrip> lines = new ArrayList<CroppableLineStrip>();
	
	/**
	 * The collection of vectors on the graph
	 */
	private ArrayList<Vector> vectors = new ArrayList<Vector>();
	
	/**
	 * The primary chart aka graph that everything is displayed on
	 */
	private AWTChart chart;
	
	/**
	 * Required for JavaFX implementation
	 */
	private ImageView imageView;
	
	/**
	 * Required for JavaFX Implementation
	 */
	private JavaFXChartFactory factory;
	
	/**
	 * The primary stackpane which everything is added onto
	 */
	private StackPane pane;
		
	/**
	 * The primary run method.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		launch(args);
	}
	
	@Override
	/**
	 * Required by JavaFX. This is the initializer and handles the setup
	 */
	public void start(Stage stage) throws Exception
	{
		// Jzy3d
		//Create the graph
        factory = new JavaFXChartFactory();
        chart  = getDemoChart(factory, "offscreen");
        imageView = factory.bindImageView(chart);
        
        //Create the menu
        Stage menu = new Stage();
        Scene menuScene = new Scene(new Menu(this, vectors));
        menu.setScene(menuScene);
        menu.show();
	    
        // JavaFX
        //Create the JavaFX portion of the graph
        pane = new StackPane();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        pane.getChildren().add(imageView);

        factory.addSceneSizeChangedListener(chart, scene);
        
        stage.setWidth(500);
        stage.setHeight(500);
	}
	
	/**
	 * Gets the actual graph/chart based off the values given.
	 * @param factory
	 * @param toolkit
	 * @return
	 */
	private AWTChart getDemoChart(JavaFXChartFactory factory, String toolkit) 
	{
		AWTChart chart = (AWTChart) factory.newChart(Quality.Advanced, toolkit);
		
		//Creates an initial vector so the graph will display
	    CroppableLineStrip line = new CroppableLineStrip(); 
	    line.add(new Point(new Coord3d(0, 0, 0)));
	    line.add(new Point(new Coord3d(1, 1, 1)));
	    line.setWireframeColor(Color.BLACK);
	        
	    lines.add(line);
	    
        chart.getScene().getGraph().add(lines);
        return chart;
    }
	
	/**
	 * Updates the chart whenever we add a vector
	 */
	private void updateChart()
	{
		chart.getScene().getGraph().add(lines);
		pane.getChildren().remove(imageView);
		imageView = factory.bindImageView(chart);
		pane.getChildren().add(imageView);
	}
	
	/**
	 * Adds a shape--typically a vector--to the graph
	 * @param object The shape to add
	 */
	public void add(Shape object)
	{
		chart.getScene().getGraph().add(object);
		
		//chart.open("test", 600, 500);
	}
	
	/**
	 * Adds a vector to the graph with a start and end point
	 * @param origin The start point of the vector. typically 0,0,0
	 * @param end The end point of the vector
	 */
	public void addVector(int[] origin, int[] end)
	{
		CroppableLineStrip line = new CroppableLineStrip(); 
	    line.add(new Point(new Coord3d(origin[0], origin[1], origin[2])));
	    line.add(new Point(new Coord3d(end[0], end[1], end[2])));
	    
	    Color color = Color.color(vectors.size());
	    
	    line.setWireframeColor(color);
	    
	    lines.add(line);
	    
	    Vector vector = new Vector(Vector.intArrToDoubleArr(origin), Vector.intArrToDoubleArr(end), vectors.size(), color.a, color.r, color.g, color.b);
	    
	    vectors.add(vector);
	    
	    updateChart();
	}
	
	/**
	 * Adds a vector to the graph based on a vector object
	 * @param v The vector to add to the graph
	 */
	public void addVector(Vector v)
	{
		CroppableLineStrip line = new CroppableLineStrip();
		line.add(new Point(new Coord3d(0, 0, 0)));
		line.add(new Point(new Coord3d(v.getComponents()[0], v.getComponents()[1], v.getComponents()[2])));
		
		line.setWireframeColor(Color.BLACK);
		
		lines.add(line);
		
		vectors.add(v);
		
		updateChart();
	}
}
