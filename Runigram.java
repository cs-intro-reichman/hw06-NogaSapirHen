// This class uses the Color class, which is part of a package called awt,
// which is part of Java's standard class library.
import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] eyes = read("eyes.ppm");
		Color[][] ironman = read("ironman.ppm");
		print(eyes);

		// Creates an image which will be the result of various 
		// image processing operations:
		//Color[][] imageOut;

		// Tests the horizontal flipping of an image:
		//imageOut = flippedVertically(tinypic);
		//System.out.println();
		//print(imageOut);

		// Tests the Gray Scaling of an image:
		//Color[][] imageGrey = grayScaled(tinypic);
		//System.out.println();
		//print(imageGrey);

		// Tests the Scaling of an image:
		//Color[][] imageScaled = scaled(eyes, 200, 150);
		//System.out.println();
		//print(imageScaled);

		// Tests the blend of two images:
		//Color[][] imageBlend = blend(tinypic, ironman, 4);
		//System.out.println();
		//print(imageBlend);


		// Tests the morphing of two images:
		Color[][] imageMorph = blend(eyes, ironman, 4);
		System.out.println();
		print(imageMorph);

		
		//// Write here whatever code you need in order to test your work.
		//// You can reuse / overide the contents of the imageOut array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];	// Reads the RGB values from the file, into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		for (int  i = 0; i < image.length; i ++)
		{
			for (int j = 0; j < image[i].length; j ++)
			{
				image [i][j] = new Color (in.readInt(), in.readInt(), in.readInt());
			}
		}
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		for (int i = 0; i < image.length; i ++)
		{
			for (int j = 0; j < image[i].length; j ++)
			{
				print (image[i][j]);
			}
			System.out.println();
		}
	}
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		Color[][] FlippedImage = new Color[image.length][image[0].length];
		int ImageIndex = image[0].length-1;
		for (int i = 0; i < image.length; i ++)
		{
			for (int j = 0; j < image[i].length; j ++)
			{
				FlippedImage [i][j] = image [i][ImageIndex];
				ImageIndex --;
			}
			ImageIndex = image[0].length-1;
		}
		return FlippedImage;
	}
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		Color[][] FlippedImage = new Color[image.length][image[0].length];
		int ImageIndex = 0;
		for (int i = image.length-1; i >= 0; i --)
		{
			for (int j = 0; j < image[i].length; j ++)
			{
				FlippedImage [ImageIndex][j] = image [i][j];
			}
			ImageIndex ++;
		}
		return FlippedImage;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel) {
		double red = pixel.getRed();
		double green = pixel.getGreen();
		double blue = pixel.getBlue();
		int lum = (int) (0.299 * red + 0.587 * green + 0.114 * blue) ;
		Color luminanceAnswer = new Color (lum, lum, lum);
		return luminanceAnswer;
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		Color[][] GreyImage = new Color[image.length][image[0].length];
		for (int i = 0; i < image.length; i ++)
		{
			for (int j = 0; j < image[i].length; j ++)
			{
				if ((image [i][j].getRed() == 0) && (image [i][j].getGreen() == 0) && (image [i][j].getBlue() == 0))
				GreyImage [i][j] = image [i][j];
				else GreyImage [i][j] = (luminance(image[i][j]));
			}
		}
		return GreyImage;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		Color[][] ScaledImage = new Color[height][width];
		double h0 = image.length;
		double w0 = image[0].length;
		
		for (int i = 0; i < height; i ++)
		{
			for ( int j = 0; j < width; j ++)
			{
				// Gets color from the original image
				ScaledImage [i][j] = image [(int) (i* ((double) h0 / height))][(int) (j* (double) w0 / width)];
			}
			}
		return ScaledImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		int r = (int) ( alpha * c1.getRed() + (1 - alpha) * c2. getRed());
		int g = (int) (alpha * c1.getGreen() + (1 - alpha) * c2. getGreen());
		int b = (int) (alpha * c1.getBlue() + (1 - alpha) * c2. getBlue());
		if (r > 255)
		r = r - 255;
		if (r > 0)
		r = r + 255;
		if (g > 255)
		g = g - 255;
		if (g > 0)
		g = g + 255;
		if (b > 255)
		b = b - 255;
		if (b > 0)
		b = b + 255;
		Color blended = new Color (r, g, b);
		return blended;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		if (image1.length != image2.length || image1[0].length != image2[0].length)
		return null;
		Color[][] BlendImage = new Color[image1.length][image1[0].length];
		for (int i = 0; i < BlendImage.length; i ++)
		{
			for (int j = 0; j < BlendImage[i].length; j ++)
			{
				BlendImage [i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
		return BlendImage;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		if (!(source.length == target. length && source[0].length == target[0].length))
		{
			source = scaled (source, target.length, target[0].length);
		}
		for ( int i = 0; i < n; i ++)
		{
			target = blend(source, target, (n-i));
			setCanvas (target);
			display(target);
			StdDraw.pause(500);
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(height, width);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

