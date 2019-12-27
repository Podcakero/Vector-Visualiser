package vectorVisualizer.math;

import java.util.ArrayList;

import vectorVisualizer.vectors.Vector;
import vectorVisualizer.vectors.Vector.VectorType;

public class Functions 
{
	
	/**
	 * Returns a vector consisting of the addition of one of more vectors
	 * @param v1 Vectors to add together
	 * @return The vectors added together
	 */
	public static Vector addition(Vector... v1)
	{		
		double[] comps = new double[] {0.0, 0.0, 0.0};
		
		for (Vector vector : v1)
		{
			comps[0] += vector.getComponents()[0];
			comps[1] += vector.getComponents()[1];
			//If the vector is 3-d, then we need to add another component.
			if (vector.getType() == VectorType.threeD);
				comps[2] += vector.getComponents()[2];
		}
		
		return new Vector(comps);
	}
	
	/**
	 * Returns a vector consisting of the sutraction of one of more vectors
	 * @param v1 Vectors to subtract from each other
	 * @return The vectors subtracted from each other
	 */
	public static Vector subtraction(Vector... v1) 
	{
		double[] comps = new double[] {0.0, 0.0, 0.0};
		
		for (Vector vector : v1)
		{
			comps[0] -= vector.getComponents()[0];
			comps[1] -= vector.getComponents()[1];
			//If the vector is 3-d, then we need to subtract another component.
			if (vector.getType() == VectorType.threeD);
				comps[2] -= vector.getComponents()[2];
		}
		
		return new Vector(comps);
	}
	
	/**
	 * Returns vector v1 with each component multiplied by the scalar
	 * @param v1 The vector to multiply
	 * @param scalar The scalar to multiple by
	 * @return The vector scaled
	 */
	public static Vector scalarMult(Vector v1, double scalar)
	{
		double[] components = new double[] {0.0, 0.0};
		
		if (v1.getType() == VectorType.threeD)
			components = new double[] {0.0, 0.0, 0.0};
		
		for (int i = 0; i < v1.getComponents().length; i++)
			components[i] = v1.getComponents()[i] * scalar;
		
		return new Vector(components);
	}
	
	/**
	 * Returns the dot product of two or more vectors
	 * @param v1 The Vectors to dot product
	 * @return The dot product
	 */
	public static int dProduct(Vector... v1)
	{
		int int0 = 1;
		int int1 = 1;
		int int2 = 0;
		if (v1[0].getComponents().length == 3)
			int2 = 1;
		
		for (Vector vector : v1)
		{
			int0 *= vector.getComponents()[0];
			int1 *= vector.getComponents()[1];
			//If the vector is 3-d, then we need to multiply another component.
			if (vector.getType() == VectorType.threeD);
				int2 *= vector.getComponents()[2];
		}
		
		return int0 + int1 + int2;
	}
	
	/**
	 * Gets the dotProduct of the given vectors.
	 * @param vectors The vectors to dot product
	 * @return The dot product of 2 or more given vectors
	 */
	public static int dProduct(ArrayList<Vector> vectors)
	{
		int int0 = 1;
		int int1 = 1;
		int int2 = 0;
		if (vectors.get(0).getComponents().length == 3)
			int2 = 1;
		
		for (Vector vector : vectors)
		{
			int0 *= vector.getComponents()[0];
			int1 *= vector.getComponents()[1];
			//If the vector is 3-d, then we need to multiply another component.
			if (vector.getType() == VectorType.threeD);
				int2 *= vector.getComponents()[2];
		}
		
		return int0 + int1 + int2;
	}
	
	/**
	 * Gets the dot product of vectors based on given components
	 * @param c1 The components of the vectors
	 * @return The dot product of 2 or more vectors based on their components
	 */
	public static int dProduct(double[]... c1)
	{
		int int0 = 1; 
		int int1 = 1;
		int int2 = 0;
		if (c1[0].length == 3)
			int2 = 1;
		
		for (double[] c : c1)
		{
			int0 *= c[0];
			int1 *= c[1];
			if (c.length == 3)
				int2 *= c[2];
		}
		
		return int0 + int1 + int2;
	}
	
	/**
	 * Returns a vector that is the cross-product of two vectors. 
	 * Precondition: Vector is 3d
	 * @return
	 */
	public static Vector cProduct(Vector v1, Vector v2)
	{
		Vector cProduct = new Vector();	
		
		double[] components = new double[] {0.0, 0.0, 0.0};
		
		//Make a 2d array of the determinant of the vectors
		double[][] det1 = new double[][] {new double[] {v1.getComponents()[1], v1.getComponents()[2]}, new double[] {v2.getComponents()[1], v2.getComponents()[2]}};
		double[][] det2 = new double[][] {new double[] {v1.getComponents()[0], v1.getComponents()[2]}, new double[] {v2.getComponents()[0], v2.getComponents()[2]}}; 
		double[][] det3 = new double[][] {new double[] {v1.getComponents()[0], v1.getComponents()[1]}, new double[] {v2.getComponents()[0], v2.getComponents()[1]}};
		
		components[0] = determinant(det1);
		components[1] = -determinant(det2);
		components[2] = determinant(det3);
		
		cProduct = new Vector(components);
		
		return cProduct;
		
	}
	
	/**
	 * The unit vector is a vector of magnitude 1, but with the same direction
	 * @param v1 The vector whos unit vector is to be found
	 * @return The unit vector of vector v1
	 */
	public static Vector unitVector(Vector v1)
	{
		double[] components = new double[] {0.0, 0.0};
		
		if (v1.getType() == VectorType.threeD)
			components = new double[] {0.0, 0.0, 0.0};
		
		for (int i = 0; i < v1.getComponents().length; i++)
			components[i] = v1.getComponents()[i] / v1.getMagnitude();
		
		return new Vector(components);
	}

	/**
	 * Returns the projection of vector 1 onto vector 2
	 * @param v1 Vector 1
	 * @param v2 Vector 2
	 * @return Projv2v1
	 */
	public static Vector proj(Vector v1, Vector v2)
	{
		return scalarMult(unitVector(v2), comp(v1, v2));
	}
	
	/**
	 * Determines the component of the projection of v1 onto v2
	 * @param v1 Vector 1
	 * @param v2 Vector 2
	 * @return Compv2v1
	 */
	public static double comp(Vector v1, Vector v2)
	{
		return (dProduct(v1, v2) / v2.getMagnitude());
	}
	
	//Make recursive at some point. rn we shoud only ever be dealing in 2x2 and 3x3
	/**
	 * Returns the determinant of a 2x2 matrix ONLY
	 * @param arr a 2x2 matrix
	 * @return The determinant
	 */
	private static double determinant(double[][] arr)
	{
		return ((arr[0][0]*arr[1][1]) - (arr[0][1]*arr[1][0]));
	}
	
}
