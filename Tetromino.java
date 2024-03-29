import java.util.Random;

// A class used for modeling tetrominoes with 3 out of 7 different types/shapes 
// as (I, O and Z)
public class Tetromino {
   // Data fields: instance variables
   // --------------------------------------------------------------------------
   private char type; // the type (shape) of the tetromino
   public Tile[][] tileMatrix; // the tiles on the tetromino shaped by its type
   // the position of the bottom left cell in the tile matrix is stored as
   // the position of the tetromino
   private Point bottomLeftCell = new Point();
   // the minimum bounded tile matrix without any empty row or column
   private Tile[][] minBoundedTileMatrix;
   // the position (the bottom left cell) of the minimum bounded tile matrix
   private Point minBoundedTileMatrixPosition = new Point();

   // Data fields: class variables
   // --------------------------------------------------------------------------
   public static int gridWidth, gridHeight; // the size of the game grid

   // Methods
   // --------------------------------------------------------------------------
   // A constructor for creating a tetromino with a given type (shape)
   public Tetromino(char type) {
      // set the type (shape) of the tetromino as the given type
      this.type = type;
      // determine the occupied (non-empty) tiles in the tile matrix
      Point[] occupiedTiles = new Point[4]; // tetromino -> 4 occupied tiles
      int n;
      if (type == 'I') {
         n = 4; // n = number of rows = number of columns in the tile matrix
         // shape of the tetromino I in its initial orientation
         occupiedTiles[0] = new Point(1, 0); // (column_index, row_index)
         occupiedTiles[1] = new Point(1, 1);
         occupiedTiles[2] = new Point(1, 2);
         occupiedTiles[3] = new Point(1, 3);
      } else if (type == 'O') {
         n = 2; // n = number of rows = number of columns in the tile matrix
         // shape of the tetromino O in its initial orientation
         occupiedTiles[0] = new Point(0, 0); // (column_index, row_index)
         occupiedTiles[1] = new Point(1, 0);
         occupiedTiles[2] = new Point(0, 1);
         occupiedTiles[3] = new Point(1, 1);
      } else if (type == 'Z'){  
         n = 3; // n = number of rows = number of columns in the tile matrix
         // shape of the tetromino Z in its initial orientation
         occupiedTiles[0] = new Point(0, 1); // (column_index, row_index)
         occupiedTiles[1] = new Point(1, 1);
         occupiedTiles[2] = new Point(1, 2);
         occupiedTiles[3] = new Point(2, 2);
      }
      else if(type == 'S') {
    	  n = 3;
    	  occupiedTiles[0] = new Point(2, 1); // (column_index, row_index)
          occupiedTiles[1] = new Point(1, 1);
          occupiedTiles[2] = new Point(1, 2);
          occupiedTiles[3] = new Point(0, 2);
      }
      else if (type == 'T') 
    	  {
    	  n = 3;
    	  occupiedTiles[0] = new Point(1, 1); // (column_index, row_index)
          occupiedTiles[1] = new Point(0, 2);
          occupiedTiles[2] = new Point(1, 2);
          occupiedTiles[3] = new Point(2, 2);
      }
      else if (type == 'L') 
	  {
	  n = 3;
	  occupiedTiles[0] = new Point(1, 0); // (column_index, row_index)
      occupiedTiles[1] = new Point(0, 0);
      occupiedTiles[2] = new Point(0, 1);
      occupiedTiles[3] = new Point(0, 2);
  }
      else//(type == 'J') 
	  {
	  n = 3;
	  occupiedTiles[0] = new Point(0, 0); // (column_index, row_index)
      occupiedTiles[1] = new Point(1, 0);
      occupiedTiles[2] = new Point(1, 1);
      occupiedTiles[3] = new Point(1, 2);
  }
     
      // create a matrix of numbered tiles based on the shape of the tetromino
      tileMatrix = new Tile[n][n];
      // create the four tiles (minos) of the tetromino and place these tiles
      // into the tile matrix
      for (Point tilePosition : occupiedTiles) { // using a for-each loop
         int colIndex = tilePosition.getX(), rowIndex = tilePosition.getY();
         // create a tile at the computed position
         tileMatrix[rowIndex][colIndex] = new Tile();
      }
      // initialize the position of the tetromino (the bottom left cell in the
      // tile matrix) with a random horizontal position above the game grid
      bottomLeftCell.setY(gridHeight - 1);
      Random random = new Random();
      bottomLeftCell.setX(random.nextInt(gridWidth - n + 1));
      
   }
   public static void rotateTetromino(Tile[][] tileMatrix2, int a) {
	    for (int stage = 0; stage < a/2; stage++) {
	        int first = stage;
	        int final1 = a - 1 - stage;
	        for(int i = first; i < final1; i++) {
	            int offset = i - first;
	            Tile peak = tileMatrix2[first][i]; 

	            tileMatrix2[first][i] = tileMatrix2[final1-offset][first];
	            tileMatrix2[final1-offset][first] = tileMatrix2[final1][final1 - offset]; 
	            tileMatrix2[final1][final1 - offset] = tileMatrix2[i][final1]; 
	            tileMatrix2[i][final1] = peak; 
	        }
	    }
	}
  

   // A method that returns the position of the cell in the tile matrix with
   // the given row and column indexes
   public Point getCellPosition(int row, int col) {
      int n = tileMatrix.length; // n = number of rows = number of columns
      Point position = new Point();
      // horizontal position of the cell
      position.setX(bottomLeftCell.getX() + col);
      // vertical position of the cell
      position.setY(bottomLeftCell.getY() + (n - 1) - row);
      return position;
   }

   // A method that creates a copy of tileMatrix omitting empty rows and columns
   public void createMinBoundedTileMatrix() {
      int n = tileMatrix.length; // n = number of rows = number of columns
      // determine rows and columns to copy (omit empty rows and columns)
      int minRow = n - 1, maxRow = 0, minCol = n - 1, maxCol = 0;
      for (int row = 0; row < n; row++) {
         for (int col = 0; col < n; col++) {
            if (tileMatrix[row][col] != null) {
               if (row < minRow)
                  minRow = row;
               if (row > maxRow)
                  maxRow = row;
               if (col < minCol)
                  minCol = col;
               if (col > maxCol)
                  maxCol = col;
            }
         }
      }
      // copy the tiles from the tile matrix
      minBoundedTileMatrix = new Tile[maxRow - minRow + 1][maxCol - minCol + 1];
      for (int row = minRow; row <= maxRow; row++) {
         for (int col = minCol; col <= maxCol; col++) {
            if (tileMatrix[row][col] != null) {
               int rowIndex = row - minRow, colIndex = col - minCol;
               minBoundedTileMatrix[rowIndex][colIndex] = tileMatrix[row][col];
            }
         }
      }
      // compute the position (bottom left cell) of the min bounded tile matrix
      int blcX = bottomLeftCell.getX(), blcY = bottomLeftCell.getY();
      minBoundedTileMatrixPosition.setX(blcX + minCol);
      minBoundedTileMatrixPosition.setY(blcY + (n - 1) - maxRow);
   }

   // A getter method for the minimum bounded tile matrix
   public Tile[][] getMinBoundedTileMatrix() {
      return minBoundedTileMatrix;
   }

   // A getter method for the position of the minimum bounded tile matrix
   public Point getMinBoundedTileMatrixPosition() {
      return minBoundedTileMatrixPosition;
   }

   // A method for drawing the tetromino on the game grid
   public void draw() {
      int n = tileMatrix.length; // n = number of rows = number of columns
      for (int row = 0; row < n; row++) {
         for (int col = 0; col < n; col++) {
            // draw each occupied tile (not equal to null) on the game grid
            if (tileMatrix[row][col] != null) {
               // get the position of the tile
               Point position = getCellPosition(row, col);
               // draw only the tiles that are inside the game grid
               if (position.getY() < gridHeight)
                  tileMatrix[row][col].draw(position);
            }
         }
      }
   }

   // A method for moving the tetromino in a given direction by 1 on the game grid
   public boolean move(String direction, GameGrid gameGrid) {
      // check if the tetromino can be moved in the given direction by using
      // the canBeMoved method defined below
      if (!canBeMoved(direction, gameGrid))
         return false; // the tetromino cannot be moved in the given direction
      // move the tetromino by updating the position of its bottom left cell
      if (direction == "left")
         bottomLeftCell.setX(bottomLeftCell.getX() - 1);
      else if (direction == "right")
         bottomLeftCell.setX(bottomLeftCell.getX() + 1);
      else if(direction == "instant-drop") {
    	  while(canBeMoved(direction, gameGrid)){
    	  bottomLeftCell.setY(bottomLeftCell.getY() - 1);
    	  }  
      }
      else if (direction == "up") {
      
      }
      else //(direction == "down")
          bottomLeftCell.setY(bottomLeftCell.getY() - 1);
      return true; // a successful move in the given direction
   }
   

   
   // A method to check if the tetromino can be moved in the given direction or not
   public boolean canBeMoved(String dir, GameGrid gameGrid) {
      int n = tileMatrix.length; // n = number of rows = number of columns
      // check for moving left or right
      if (dir == "left" || dir == "right") {
         for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
               // direction = left --> check the leftmost tile of each row
               if (dir == "left" && tileMatrix[row][col] != null) {
                  Point leftmost = getCellPosition(row, col);
                  // skip each row whose leftmost tile is out of the game grid
                  // (possible for newly entered tetrominoes to the game grid)
                  if (leftmost.getY() >= gridHeight)
                     break;
                  // the tetromino cannot go left if any leftmost tile is at x = 0
                  if (leftmost.getX() == 0)
                     return false;
                  // the tetromino cannot go left if the grid cell on the left
                  // of any leftmost tile is occupied
                  if (gameGrid.isOccupied(leftmost.getY(), leftmost.getX() - 1))
                     return false;
                  break; // end the inner for loop
               }
               // direction = right --> check the rightmost tile of each row
               else if (dir == "right" && tileMatrix[row][n - 1 - col] != null) {
                  Point rightmost = getCellPosition(row, n - 1 - col);
                  // skip each row whose rightmost tile is out of the game grid
                  // (possible for newly entered tetrominoes to the game grid)
                  if (rightmost.getY() >= gridHeight)
                     break;
                  // the tetromino cannot go right if any rightmost tile is at
                  // x = gridWidth - 1
                  if (rightmost.getX() == gridWidth - 1)
                     return false;
                  // the tetromino cannot go right if the grid cell on the right
                  // of any rightmost tile is occupied
                  if (gameGrid.isOccupied(rightmost.getY(), rightmost.getX() + 1))
                     return false;
                  break; // end the inner for loop
               }
            }
         }
      }
      // direction = down --> check the bottommost tile of each column
      else {
         for (int col = 0; col < n; col++) {
            for (int row = n - 1; row > -1; row--) {
               if (tileMatrix[row][col] != null) {
                  Point bottommost = getCellPosition(row, col);
                  // skip each column whose bottommost tile is out of the grid
                  // (possible for newly entered tetrominoes to the game grid)
                  if (bottommost.getY() > gridHeight)
                     break;
                  // the tetromino cannot go down if any bottommost tile is at y = 0
                  if (bottommost.getY() == 0)
                     return false;
                  // or the grid cell below any bottommost tile is occupied
                  if (gameGrid.isOccupied(bottommost.getY() - 1, bottommost.getX()))
                     return false;
                  break; // end the inner for loop
               }
            }
         }
      }
      return true; // the tetromino can be moved in the given direction
   }



}
