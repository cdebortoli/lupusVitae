package com.erkin.lupusvitae.utils;

public class WorldGeneratorHelper {
	
	// Classic normalize
	public static double[] normalize2(double x, double y)
	{
		double result[] = new double[2];
		double s;
	
		s = 1.0/Math.sqrt(x * x + y * y);
		result[0] = x * s;
		result[1] = y * s;
		return result;
	}
	
	// Normalize -1/1
	public static int getRadiusForSize(double size)
	{
		return (int) (size/2);
	}
	
	public static double normalizeForRidged(double valToNorm, int radius)
	{
		return (valToNorm - radius)/radius;
	}
	
	// Color methods
	public static int RGB(int r, int g, int b)
	{
		    if (r < 0) r = 0; else if (r > 255) r = 255;
		    if (g < 0) g = 0; else if (g > 255) g = 255;
		    if (b < 0) b = 0; else if (b > 255) b = 255;
		    return 0xFF000000 | r << 16 | g << 8 | b;
	}

	public static int greyscale(double height) 
	{
	    // Convert height to ARGB as a simple black to white palette
	    int scaled = heightToByte(height);
	    return 0xFF000000 | scaled << 16 | scaled << 8 | scaled;
	}
	  
	public static int heightToByte(double height) 
	{
	    int scaled = (int) (height * 255);
	    return scaled;
	}
	
	public static double invertHeight (double height, double maxValue) 
	{
		return maxValue - height;
	}
}
