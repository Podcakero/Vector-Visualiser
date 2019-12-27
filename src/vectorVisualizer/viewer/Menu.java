package vectorVisualizer.viewer;

import java.util.ArrayList;

import org.jzy3d.colors.Color;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.primitives.Shape;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import vectorVisualizer.math.Functions;
import vectorVisualizer.vectors.Vector;

public class Menu extends StackPane
{
	/**
	 * The arraylist of vectors. All the vectors
	 */
	private ArrayList<Vector> vectors;
	
	/**
	 * The arraylist of vectors currently selected by the user
	 */
	private ArrayList<Vector> selectedVectors;
	
	/**
	 * The grid which everything in the menu if placed onto
	 */
	private GridPane grid;
	
	/**
	 * The graph itself
	 */
	private Graph graph;
	
	/**
	 * The vertical box for the list of vectors
	 */
	private VBox vectorList;
	
	/**
	 * The vertical box for vectormath
	 */
	private VBox vectorMath;
	
	/**
	 * The vertical box for the info of the selected vector
	 */
	private VBox vectorInfo;
	
	/**
	 * Creates a new Menu object with the given graph and list of vectors
	 * @param graph The graph
	 * @param v Arraylist of vectors
	 */
	public Menu(Graph graph, ArrayList<Vector> v)
	{
		this.graph = graph;
		vectors = v;
		initMenu();
		
		selectedVectors = new ArrayList<Vector>();
	}

	/**
	 * Initialises the menu by initialising the grid and creating the different menu sections
	 */
	public void initMenu()
	{
		// TODO Auto-generated method stub
		initGrid();
		createMenu();
		
		grid.setGridLinesVisible(true);
		
		this.getChildren().add(grid);
	}
	
	/**
	 * Initialises the gridpane which each element is added to
	 */
	private void initGrid()
	{
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setPadding(new Insets(25, 25, 25, 25));
	}
	
	/**
	 * Creates the different elements of the menu
	 */
	private void createMenu()
	{
		//Add vector
		VBox addVector = new VBox();
		
			//Box Text
			Text addVectorText = new Text("Add Vector");
			addVectorText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 20));
			addVector.getChildren().add(addVectorText);
			
			//X Values
			HBox xValBox = new HBox();
			Label x = new Label("x: ");
			x.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
			
			TextField xVal = new TextField();
				
			xValBox.getChildren().addAll(x, xVal);
			
			//Y Values
			HBox yValBox = new HBox();
			Label y = new Label("y: ");
			y.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
			
			TextField yVal = new TextField();
			
			yValBox.getChildren().addAll(y, yVal);
			
			//Z Values
			HBox zValBox = new HBox();
			Label z = new Label("z: ");
			z.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
			
			TextField zVal = new TextField();
			
			zValBox.getChildren().addAll(z, zVal);
			
			//Add Button
			Button addButton = new Button("Add Vector");
			
			addButton.setOnAction(new EventHandler<ActionEvent>()
			{
				@Override
				/**
				* Grab the values from the text field and create a lineStrip from the values and add it to the graph, and then clear the textfields
				*/
				public void handle(ActionEvent arg0)
				{
					if (vectors.size() < 10)
					{
						graph.addVector(new int[] {0, 0, 0}, new int[] {Integer.parseInt(xVal.getText()), Integer.parseInt(yVal.getText()), Integer.parseInt(zVal.getText())});
						updateVectorList();
						for (Vector v : selectedVectors)
							removeSelectedVector(v);
					}
				}
			
			});
			
		addVector.getChildren().addAll(xValBox, yValBox, zValBox, addButton);
		
		//Vector List
		vectorList = new VBox();
		
		Text vectorListText = new Text("Vectors");
		vectorListText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
		vectorList.getChildren().add(vectorListText);
		
		for (Vector v : vectors)
			vectorList.getChildren().add(v.display(this));
		
		//Vector Math
		vectorMath = new VBox();
		
		Text vectorMathText = new Text("Vector");
		vectorMathText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
		vectorMath.getChildren().add(vectorMathText);
		
		//Vector Info
		vectorInfo = new VBox();
		
		Text vectorInfoText = new Text("Vector");
		vectorInfoText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
		vectorInfo.getChildren().add(vectorInfoText);
		
		grid.add(addVector, 0, 0);
		grid.add(vectorList, 0, 1);
		grid.add(vectorMath, 1, 0);
		grid.add(vectorInfo, 1, 1);
	}
	
	/**
	 * Creates and fills all the buttons and placeholders for the VectorMath portion of the menu
	 */
	private void populateVectorMath()
	{
		vectorMath.getChildren().remove(0, vectorMath.getChildren().size());
		
		Text output = new Text("");
		
		//Unit Vector
		Button unitVector = new Button("unitVector");
		
		if (selectedVectors.size() < 1)
			unitVector.setDisable(true);
		else
			unitVector.setDisable(false);
		
		unitVector.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				graph.addVector(selectedVectors.get(0).getUnitVector());
				updateVectorList();
				removeSelectedVector(selectedVectors.get(0));
				
				System.out.println("Added vector");
			}
			
		});
		
		//Angle Between Two Vectors
		Button angle = new Button("Angle between vectors");
		
		if (selectedVectors.size() < 2)
			angle.setDisable(true);
		else
			angle.setDisable(false);
		
		angle.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				output.setText("" + Vector.angleBetweenVectors(selectedVectors.get(0), selectedVectors.get(1)));
			}
			
		});
		
		//Cross-product
		Button cProduct = new Button("Cross-Product");
		
		if (selectedVectors.size() < 2)
			cProduct.setDisable(true);
		else
			cProduct.setDisable(false);
		
		cProduct.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				output.setText("" + Functions.cProduct(selectedVectors.get(0), selectedVectors.get(1)));
			}
			
		});
		
		//Dot product
		Button dProduct = new Button("Dot-Product");
		
		if (selectedVectors.size() < 2)
			dProduct.setDisable(true);
		else
			dProduct.setDisable(false);
		
		dProduct.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				output.setText("" + Functions.dProduct(selectedVectors));
			}
			
		});
		
		Button plane = new Button("Create Plane");
		
		if (selectedVectors.size() < 2)
			plane.setDisable(true);
		else
			plane.setDisable(false);
		
		plane.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) 
			{
				Vector crossProduct = Functions.cProduct(selectedVectors.get(0), selectedVectors.get(1));
				
				if (crossProduct.getComponents()[2] == 0)
					output.setText("ERROR: Vertical planes are not supported");
				else
				{
					Mapper mapper = new Mapper() {
						/**
						 * Returns the equation of a plane using the cross-product of the vectors selected
						 */
						public double f(double x, double y)
						{
							return -(crossProduct.getComponents()[0]/crossProduct.getComponents()[2])*x - (crossProduct.getComponents()[1]/crossProduct.getComponents()[2])*y;
						}
					};
					
					Shape testSurface = Builder.buildOrthonormal(mapper, new Range(0, 10), 10);
					
					testSurface.setColor(Color.RED);
					testSurface.setFaceDisplayed(true);
					testSurface.setWireframeDisplayed(true);
				
					graph.add(testSurface);
				}
			}
			
		});
		
		//extra
		//Output Box
		HBox outputHBox = new HBox();
		Text outputText = new Text("Output: ");
		
		outputHBox.getChildren().addAll(outputText, output);
		
		vectorMath.getChildren().addAll(unitVector, angle, cProduct, dProduct, plane, outputHBox);
	}
	
	/**
	 * Updates the list of the vectors
	 */
	private void updateVectorList()
	{
		vectorList.getChildren().remove(0, vectorList.getChildren().size());
		
		Text vectorListText = new Text("Vectors");
		vectorListText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
		vectorList.getChildren().add(vectorListText);
		
		for (Vector v : vectors)
			vectorList.getChildren().add(v.display(this));
	}
	
	/**
	 * Updated the VectorInfo portion of the menu with the selected vector's info
	 */
	private void updateVectorInfo()
	{
		//VBox vectorInfo = new VBox();
		
		vectorInfo.getChildren().remove(0, vectorInfo.getChildren().size());
		
		for (int i = 0; i < selectedVectors.size(); i++)
		{
			GridPane mag = new GridPane();
			GridPane dir = new GridPane();
			
			//Magnitude
			mag.setGridLinesVisible(true);
			
			Text magText = new Text("Vector " + i + " Magnitude");
			magText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
			
			Text magVal = new Text("" + selectedVectors.get(i).getMagnitude());
			magVal.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
			
			mag.add(magText, 0, 0);
			mag.add(magVal, 1, 0);
			
			mag.setBackground(new Background(new BackgroundFill[] {new BackgroundFill(selectedVectors.get(i).getColor(), null, null)}));
			
			//Direction
			
			dir.setGridLinesVisible(true);
			
			Text dirText = new Text("Vector " + i + " Direction");
			dirText.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
			
			Text dirVal = new Text("x-direction: " + selectedVectors.get(i).getDirection()[0] + "\ny-direction" + selectedVectors.get(i).getDirection()[1] + "\nz-direction" + selectedVectors.get(i).getDirection()[2]);
			dirVal.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
			
			dir.add(dirText, 0, 0);
			dir.add(dirVal, 1, 0);
			
			dir.setBackground(new Background(new BackgroundFill[] {new BackgroundFill(selectedVectors.get(i).getColor(), null, null)}));
			
			vectorInfo.getChildren().addAll(mag, dir);
		}
		
	}
	
	/**
	 * Sets the current selected vector and updates VectorInfo and VectorMath to show
	 */
	public void addSelectedVector(Vector v)
	{
		selectedVectors.add(v);		
		
		updateVectorInfo();
		populateVectorMath();
	}
	
	/**
	 * Removes the given vector from the list of selected vectors
	 * @param v The vector to remove
	 */
	public void removeSelectedVector(Vector v)
	{
		selectedVectors.remove(v);
		
		updateVectorInfo();
		populateVectorMath();
	}
}
