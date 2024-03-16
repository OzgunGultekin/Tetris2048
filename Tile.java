import java.awt.Color; // the color type used in StdDraw
import java.awt.Font; // the font type used in StdDraw
import java.util.Random;

// A class used for modeling numbered tiles as in 2048
public class Tile {
   // Data fields: instance variables
   // --------------------------------------------------------------------------
   public int number; // the number on the tile
   public Color backgroundColor; // background (tile) color
   private Color foregroundColor; // foreground (number) color
   private Color boxColor; // box (boundary) color

   // Data fields: class variables
   // --------------------------------------------------------------------------
   // the value of the boundary thickness (for the boundaries around the tiles)
   private static double boundaryThickness = 0.004;
   // the font used for displaying the tile number
   private static Font font = new Font("Bank Gothic Light BT", Font.PLAIN, 14);
   
   
	    

//--------------------------------------------------------------------------
// Methods
 
  
   // the default constructor that creates a tile with 2 as the number on it
   
   
   
   public Tile() {
	   // set the number on the tile
	   int random = (int)(Math.random()*2)+1;
	   number = (int) Math.pow(2, random);
	   // set the colors of the tile
	   
	   backgroundColor = new Color(239, 202, 153);
	   foregroundColor = new Color(0,0,0);
	   boxColor = new Color(0, 0, 0);
	   
	   switch (number) {
	   case 4:
		   backgroundColor = new Color(244, 159, 10);
		   
		   
		}
	   
   }

   // a method for drawing the tile
   public void draw(Point position, double... sideLength) {
      // the default value for the side length (sLength) is 1
      double sLength;
      if (sideLength.length == 0) // sideLength is a variable-length parameter
         sLength = 1;
      else
         sLength = sideLength[0];
      // draw the tile as a filled square
      StdDraw.setPenColor(backgroundColor);
      StdDraw.filledSquare(position.getX(), position.getY(), sLength / 2);
      // draw the bounding box around the tile as a square
      StdDraw.setPenColor(boxColor);
      StdDraw.setPenRadius(boundaryThickness);
      StdDraw.square(position.getX(), position.getY(), sLength / 2);
      StdDraw.setPenRadius(); // reset the pen radius to its default value
      // draw the number on the tile
      StdDraw.setPenColor(foregroundColor);
      StdDraw.setFont(font);
      StdDraw.text(position.getX(), position.getY(), "" + number);
   }

   }
