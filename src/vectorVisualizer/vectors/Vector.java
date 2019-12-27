package vectorVisualizer.vectors;

import javafx.event.*;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import vectorVisualizer.math.Functions;
import vectorVisualizer.viewer.Menu;

public class Vector
{
	/**
	 * The magnitude of this vector
	 */
	private double magnitude;
	
	/**
	 * The direction of the vector if its tail is place at the origin
	 */
	private double[] direction;
	
	/**
	 * The int array of the components of the vector. 
	 */
	private double[] components;
	
	/**
	 * The Type of this vector (2d or 3d)
	 */
	private VectorType type;
	
	/**
	 * The ID aka number of the vector
	 */
	private int vectorID;
	
	/**
	 * The color of the vector
	 */
	private Color color;
	
	/**
	 * There are two types of vectors currently implemented, 2d and 3d
	 * @author pod
	 */
	public enum VectorType {
		twoD, threeD
	}
	
	/**
	 * Converts an array of integers to an array of doubles
	 * @param intArr The int array to convert
	 * @return a double array with the same values as the int array
	 */
	public static double[] intArrToDoubleArr(int[] intArr)
	{
		double[] arr = new double[intArr.length];
		
		for (int i = 0; i < intArr.length; i++)
			arr[i] = intArr[i];
		
		return arr;
	}
	
	/**
	 * Creates a new Vector object from the provided points
	 * PRECONDITION: point1 and point 2 have equal length which is >= 2 and <= 3
	 * @param p1 The int array of the coordinates of the first point
	 * @param p2 The int array of the coordinates of the second point
	 */
	public Vector(double[] p1, double[] p2)
	{
		//If the precondition is not satisfied, don't continue
		if (!(2 <= p1.length && p1.length <= 3) || !(2 <= p2.length && p2.length <= 3) || !(p1.length == p2.length))
			return;
		
		double[] c = new double[p1.length];
		
		for (int i = 0; i < p1.length; i++)
			c[i] = p2[i] - p1[i];
		
		components = c;
		
		vectorID = 0;
		
		init();
	}
	
	/**
	 * Creates a new Vector object from the provided points, ID, and color.
	 * PRECONDITION: point1 and point 2 have equal length which is >= 2 and <= 3
	 * @param p1 The int array of the coordinates of the first point
	 * @param p2 The int array of the coordinates of the second point
	 * @param ID The ID of the vector
	 * @param a The alpha value of the colour (darkness)
	 * @param r The red value
	 * @param g The green value
	 * @param b The blue value
	 */
	public Vector(double[] p1, double[] p2, int ID, float a, float r, float g, float b)
	{
		//If the precondition is not satisfied, don't continue
		if (!(2 <= p1.length && p1.length <= 3) || !(2 <= p2.length && p2.length <= 3) || !(p1.length == p2.length))
			return;
		
		double[] c = new double[p1.length];
		
		for (int i = 0; i < p1.length; i++)
			c[i] = p2[i] - p1[i];
		
		components = c;
		
		vectorID = ID;
		this.color = new Color(r, g, b, a);
		
		init();
	}
	
	/**
	 * Creates a new vector object from the provided components
	 * @param c The components of the vector.
	 */
	public Vector(double[] c)
	{
		components = c;
		init();
	}
	
	/**
	 * Creates a vector from components, an ID, and a color represented as a, r, g, and b
	 * @param c The components of the vector
	 * @param ID The ID of this vector
	 * @param a The alpha value of the colour (darkness)	
	 * @param r The red value				
	 * @param g The green value
	 * @param b The blue value
	 */
	public Vector(double[] c, int ID, int a, int r, int g, int b)
	{
		components = c;
		
		vectorID = ID;
		this.color = new Color(r, g, b, a);
		
		init();
	}
	
	/**
	 * Creates a vector object from components, an ID, and a color
	 * @param c The components of the vector to create	
	 * @param ID The ID of the vector.
	 * @param color The color of the vector
	 */
	public Vector(double[] c, int ID, Color color)
	{
		components = c;
		
		vectorID = ID;
		this.color = color;
		
		init();
	}
	
	/**
	 * Instantiates all variables, because Java is dumb and won't let me call different constructors not as the first line
	 */
	private void init()
	{
		if (components.length == 2)
			type = VectorType.twoD;
		else
			type = VectorType.threeD;
		
		magnitude = calculateMagnitude();
		calculateDirection();
	}
	
	/**
	 * Creates a vector object with all 0 values for magnitude, direction, and components
	 */
	public Vector()
	{
		magnitude = 0.0;
		direction = new double[] {0.0};
		components = new double[] {0.0, 0.0, 0.0};
	}
	
	/**
	 * Determines the magnitude of this vector
	 * @return the magnitude
	 */
	private double calculateMagnitude()
	{
		//Takes the square root of all the components squared and added together. 
		double sqrtInside = 0.0;
		
		for (int i = 0; i < components.length; i++)
			sqrtInside += Math.pow((components[i]), 2);
		
		return Math.sqrt(sqrtInside);
	}
	
	/**
	 * Calculates the magnitude of a vector in component form
	 * @param c1 The components of the vector
	 * @return The magnitude of the vector
	 */
	private double calculateMagnitude(double[] c1)
	{
		//Takes the square root of all the components squared and added together. 
		double sqrtInside = 0.0;
		
		for (int i = 0; i < c1.length; i++)
			sqrtInside += Math.pow((c1[i]), 2);
		
		return Math.sqrt(sqrtInside);
	}
	
	/**
	 * Calculates the direction of the vector if its tail is placed at the origin
	 * @return the angle between the x-axis and the vector in degrees
	 */
	private void calculateDirection()
	{
		//The three different angles
		double angleAlpha; //x-axis angle
		double angleBeta; //y-xis angle
		double angleGamma; //z-axis angle
		
		//The vectors representing the axes
		double[] xVector = new double[] {1.0, 0.0};
		double[] yVector = new double[] {0.0, 1.0};
		double[] zVector = new double[] {0.0, 0.0};
		
		//If we're using a 3d vector, then we need to make the axes 3d
		if (this.getType() == VectorType.threeD)
		{
			xVector = new double[] {1.0, 0.0, 0.0};
			yVector = new double[] {0.0, 1.0, 0.0};
			zVector = new double[] {0.0, 0.0, 1.0};
		}
		
		//Calculate the angle between the axes and this vector
		angleAlpha = angleBetweenVectors(this.components, xVector);
		angleBeta = angleBetweenVectors(this.components, yVector);
		angleGamma = angleBetweenVectors(this.components, zVector);
		
		//Set our direction as 2d or 3d depending on the type.
		if (this.getType() == VectorType.threeD)
			direction = new double[] {angleAlpha, angleBeta, angleGamma};
		else
			direction = new double[] {angleAlpha, angleBeta};
	}
	
	/**
	 * Finds the angle between vector v1 and v2
	 * @param v1 A vector v1
	 * @param v2 A vector v2
	 * @return The angle between vectors
	 */
	public static double angleBetweenVectors(Vector v1, Vector v2)
	{
		return Math.acos(Functions.dProduct(v1, v2) / (v1.getMagnitude() * v2.getMagnitude()));
	}
	
	/**
	 * Finds the angle between two vector components, c1 and c2
	 * @param c1 The components of the first vector
	 * @param c2 The components of the second vector
	 * @return The angle between vectors
	 */
	private double angleBetweenVectors(double[] c1, double[] c2)
	{
		return Math.acos(Functions.dProduct(c1, c2) / (calculateMagnitude(c1) * calculateMagnitude(c2)));
	}
	
	/**
	 * @return The unit vector of this vector, which is a vector in the same direction but magnitude 1
	 */
	public Vector getUnitVector()
	{
		double[] c = new double[3];
		
		c[0] = this.components[0] / this.magnitude;
		c[1] = this.components[1] / this.magnitude;
		c[2] = this.components[2] / this.magnitude;
		
		Color newColor = Color.BLACK;
		
		Vector vector = new Vector(c, vectorID, newColor);
		
		return vector;
	}
	
	/**
	 * @return The ID of this vector.
	 */
	public int getID()
	{
		return vectorID;
	}
	
	/**
	 * @return The components of the vector
	 */
	public double[] getComponents()
	{
		return components;
	}
	
	/**
	 * @return The Type of the vector (2d or 3d)
	 */
	public VectorType getType()
	{
		return type;
	}
	
	/**
	 * @return The magnitude of this vector
	 */
	public double getMagnitude()
	{
		return magnitude;
	}
	
	/**
	 * @return The x, y, and z directions of this vector
	 */
	public double[] getDirection()
	{
		return direction;
	}
	
	/**
	 * @return The color of the vector
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * Converts the vector to a String format. 
	 * ex. <a, b, c>
	 */
	public String toString()
	{
		String output = "";
		
		output = "<" + components[0] + ", " + components[1];
		
		if (type == VectorType.threeD)
			output += ", " + components[2];
		
		output += ">";
		
		return output;
	}
	
	/**
	 * @param menu The menu object of the current instance
	 * @return A stackpane object to be placed into the menu
	 */
	public StackPane display(Menu menu)
	{
		//Create the gridpane for the vector info
		GridPane info = new GridPane();
		
		info.setGridLinesVisible(true);
		
		//The checkbox to select this vector when viewing its info or doing math
		CheckBox select = new CheckBox();
		
		//The name and ID of this vector
		Text name = new Text();
		name.setText("Vector" + vectorID);
		name.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 20));
		
		//We need to pass our current vector, and this was the easiest way since using this inside of the setOnAction method would instead grab the EventHandler object
		Vector thisVector = this;
		
		//When we select the vector, make it selected. Simple as that.
		select.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			/**
			 * Sets the vector as selected depending on the state of the checkbox
			 */
			public void handle(ActionEvent arg0)
			{
				if (select.isSelected())
					menu.addSelectedVector(thisVector);
				else
					menu.removeSelectedVector(thisVector);
			}
			
		});
		
		//Make the background of our vector object the same colour as it is on the graph.
		Background background = new Background(new BackgroundFill[] {new BackgroundFill(color, null, null)});
		
		info.setBackground(background);
		
		info.add(name, 0, 0);
		info.add(select, 1, 0);
		
		//Create our pane, add everything to it, then return it
		StackPane pane = new StackPane();
		pane.getChildren().add(info);
		return pane;
	}

}
