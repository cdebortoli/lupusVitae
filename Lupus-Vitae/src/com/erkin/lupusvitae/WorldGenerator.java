package com.erkin.lupusvitae;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.erkin.lupusvitae.utils.ImprovedNoise;
import com.erkin.lupusvitae.utils.WorldGeneratorHelper;

// 1 : Generate ground
// Ok we have altitude, but the type of terrain don't need to be independent of the altitude to have rocky mountains and montains with trees.
// 2 : Generate rivers. Blend with conditions of ground (see clamp method)
// 3 : Realtime generation for food. At each turn. How ?
public class WorldGenerator {
	
	private double seed;
	
	// Variable used for ridged multi fractal
	private boolean first = true;
	private double exponent_array[];
	/* “H” determines the fractal dimension of the roughest areas
	 * “lacunarity” is the gap between successive frequencies
	 	A multiplier that determines how quickly the frequency increases for each successive octave in a Perlin-noise function.
		The frequency of each successive octave is equal to the product of the previous octave's frequency and the lacunarity value.
		A similar property to lacunarity is persistence, which modifies the octaves' amplitudes in a similar way.
	 * “octaves” is the number of frequencies in the fBm
	 * “offset” raises the terrain from “sea level”
	 */
	private double H = 1.25;
	private double lacunarity = 2; 
	private double octaves = 8;
	private double offset = 1;
	private double gain = 3;
	private double scaleX = 3;
	private double scaleY = 3;

	
	public WorldGenerator(double seedParam)
	{
		this.seed = seedParam;
	}
	
	
	public float[][] generateWorld1(double size)
	{
		float result[][] = new float[(int)size][(int)size];
		first = true;

		int radius = WorldGeneratorHelper.getRadiusForSize(size);
		
		for(int x = 0; x < size; x++)
		{
			for(int y = 0; y < size; y++)
			{
				double resultHeight = ridgedMultifractal(WorldGeneratorHelper.normalizeForRidged(x, radius),
						WorldGeneratorHelper.normalizeForRidged(y, radius), 
						seed,
						H, 
						lacunarity,
						octaves,
						offset,
						gain,
						scaleX,
						scaleY);
				result[x][y] = (float)WorldGeneratorHelper.invertHeight(resultHeight,2);
			}
		}
		return result;
	}
	
	public float[][] generateSceneries(double size, double freq, double seedCoef)
	{
		float sceneries[][] = new float[(int) size][(int) size];
		for(int x = 0; x < size; x++)
		{
			for(int y = 0; y < size; y++)
			{
				//double height = ImprovedNoise.noise(x*freq,y*freq,0) + 0.5 * ImprovedNoise.noise(x*freq,y*freq,0) + 0.25 * ImprovedNoise.noise(x*freq,y*freq,0);
				double height = ImprovedNoise.noise(x*freq, y*freq, seed*seedCoef);
				sceneries[x][y] = (float)height;
				//System.out.println(String.valueOf(height));
			}
		}
		return sceneries;
	}
		
/* -------------------------------------
 * Private
 * ------------------------------------- */

	
	/* Ridged multifractal terrain model.
	*
	* Some good parameter values to start with:
	* H: 1.0
	* offset: 1.0
	* gain: 2.0
	*/
	private double ridgedMultifractal(double x, double y, double zSeed, double H, double lacunarity, double octaves, double offset, double gain, double xScale, double yScale)
	{
		x *= xScale;
		y *= yScale;
		
		double result, frequency, signal, weight;
		int i;
		/* precompute and store spectral weights */
		if ( first ) {
			/* seize required memory for exponent_array */
			exponent_array = new double[(int)octaves+1];
			frequency = 1.0;
			for (i=0; i<=octaves; i++) {
				/* compute weight for each frequency */
				exponent_array[i] = Math.pow( frequency, -H );
				frequency *= lacunarity;
			}
		first = false;
		}
		/* get first octave */
		signal = ImprovedNoise.noise(x,y,zSeed);
		/* get absolute value of signal (this creates the ridges) */
		if ( signal < 0.0 ) signal = -signal;
		/* invert and translate (note that “offset” should be  = 1.0) */
		signal = offset - signal;
		/* square the signal, to increase “sharpness” of ridges */
		signal *= signal;
		/* assign initial values */
		result = signal;
		weight = 1.0;
		for( i=1; i<octaves; i++ ) {
			/* increase the frequency */
			x *= lacunarity;
			y *= lacunarity;
	
			/* weight successive contributions by previous signal */
			weight = signal * gain;
			if ( weight > 1.0 ) weight = 1.0;
			if ( weight < 0.0 ) weight = 0.0;
			signal =  ImprovedNoise.noise(x,y,zSeed);
			if ( signal < 0.0 ) signal = -signal;
			signal = offset - signal;
			signal *= signal;
			/* weight the contribution */
			signal *= weight;
			result += signal *exponent_array[i];
		}
		return( result );
	} /* RidgedMultifractal() */
		
	
	
	/*
	 * DEBUG
	 */
	
	public static void main(String[] args) {
		WorldGenerator test = new WorldGenerator(2000	);
		double size = 1024;
		float[][] worldResult = test.generateWorld1(size);
		
		BufferedImage img = new BufferedImage((int)size, (int)size, BufferedImage.TYPE_INT_ARGB);

        
		for(int x = 0; x < worldResult.length; x++){
		
			for(int y = 0; y < worldResult[0].length; y++){

				double height = worldResult[x][y];
		        // Convert height to ARGB 
		        int R = 0, G = 0, B = 0;
				if (height < 0.1)
				{
					G = 50;
				}
				else if (height < 0.2) {
					G = 80;
				}
				else if (height < 0.3) {
					G = 100;
				}
				else if (height < 0.4) {
					G = 120;
				}
				else if (height < 0.5) {
					G = 140;
				}
				else if (height < 0.6) {
					G = 160;
				}
				else if (height < 0.7) {
					G = 200;
				}
				else if (height < 0.8) {
					G = 220;
				}
				else if (height < 0.9) {
					G = 240;
				}
				else if (height < 1) {
					B = 255;
					R = 20;
				}
				else if (height < 1) {
					B = 255;
					R = 40;
				}
		        else if (height < 1.1) {
					B = 255;
					R = 60;
		        }
	            else  if (height < 1.2) {
					B = 255;
					R = 80;        
				}
	            else if (height < 1.3) {
					B = 255;
					R = 100;
	            }
	            else if (height < 1.5) {
					B = 255;
					R = 120;
	            }
	            else if (height < 1.6) {
					B = 255;
					R = 140;
	            }
	            else if (height < 1.7) {
					B = 255;
					R = 160;
	            }
	            else if (height < 1.8) {
					B = 180;
					R = 20;
	            }
	            else if (height < 1.9) {
					B = 255;
					R = 255;
	            }
	            else  
	            {
					B = 255;
					R = 20;
				}
				int rgb =  Color.HSBtoRGB(0.0f,0.0f,(float) height);
				
				img.setRGB(x, y, rgb);
				//double height = worldResult[x][y];
				//img.setRGB(x, y, WorldGeneratorHelper.RGB(R, G, B));
				//System.out.println(String.valueOf(x)+"-"+String.valueOf(y)+"="+String.valueOf(height));
			}
		}

		File outputFile = new File("debug/ridgedMultifractal.png");
		try {
			ImageIO.write(img, "PNG", outputFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
