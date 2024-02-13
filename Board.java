import java.io.*;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

public class Board {
    // Fields for the Sudoku Board
    private Cell[][] board;
    private boolean finished;
    private int lockedCells;
    public static final int SIZE = 9;

    /**
     * Constructor for the Board
     * Initializes each cell to a value of 0
     */
    public Board() {
        this.board = new Cell[SIZE][SIZE];
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                this.board[i][j] = new Cell(i, j, 0);
            }
        }
        this.finished = false;
    }

    /**
     * Constructor for the Board
     * Initializes each cell based on the read Sudoku file
     */
    public Board(String filename) {
        this.board = new Cell[SIZE][SIZE];
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                this.board[i][j] = new Cell(i, j, 0);
            }
        }
        this.read(filename);
        this.finished = false;
    }

    /**
     * Constructor for the Board
     * Initializes each cell to a random valid value based on
     * the number of cells to be initially locked.
     * @param lockedCells the number of cells to be locked initially
     */
    public Board(int lockedCells) {
        this.board = new Cell[SIZE][SIZE];
        this.lockedCells = lockedCells;
        Random rand = new Random();

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {      
                this.board[i][j] = new Cell(i, j, 0);
            }
        }

        for (int i = 0; i < lockedCells; i++) {
            int randomRow = rand.nextInt(SIZE);
            int randomCol = rand.nextInt(SIZE);
            while(this.board[randomRow][randomCol].getValue() != 0) {
                randomRow = rand.nextInt(SIZE);
                randomCol = rand.nextInt(SIZE);
            }
            
            int randVal = 0;
            while(this.validValue(randomRow, randomCol, randVal) == false) {
                randVal = rand.nextInt(9) + 1;
            }
            this.board[randomRow][randomCol].setValue(randVal);
            this.board[randomRow][randomCol].setLocked(true);
        }
        this.finished = false;
    }

    /**
     * Returns a string representation of the board.
     * 
     * @return a string representation of the board
     */
    public String toString() {
        String output = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                output += board[i][j].getValue() + " ";
                if ((j + 1) % 3 == 0) {
                    output += "   ";
                }
            }
            output += "\n";
            if ((i + 1) % 3 == 0) {
                output += "\n";
            }
        }
        return output;
    }

    /**
     * Returns the number of board columns
     * 
     * @return the number of board columns
     */
    public int getCols() {
        return SIZE;
    }

    /**
     * Returns the number of board rows.
     * 
     * @return the number of board rows
     */
    public int getRows() {
        return SIZE;
    }

    /**
     * Returns the number of initialized cells.
     * @return the number of initialized cells.
     */
    public int getInitialLocked() {
        return this.lockedCells;
    }

    /**
     * Returns if the board is finished or not.
     * @return if the board is finished or not
     */
    public boolean getFinished() {
        return this.finished;
    }

    /**
     * Sets the finished state of the board.
     * @param isFinished the state to be set to
     */
    public void setFinished(boolean isFinished) {
        this.finished = isFinished;
    } 

    /**
     * Returns the cell at the specified location.
     * 
     * @param r the row of the specified cell
     * @param c the col of the specified cell
     * @return the cell at the specified location
     */
    public Cell get(int r, int c) {
        return this.board[r][c];
    }

    /**
     * Returns the locked status of the specified cell
     * 
     * @param r the row of the specified cell
     * @param c the col of the specified cell
     * @return the locked status of the specified cell
     */
    public boolean isLocked(int r, int c) {
        return this.board[r][c].isLocked();
    }

    /**
     * Returns the number of locked cells on Board
     * 
     * @return the number of locked cells on Board
     */
    public int numLocked() {
        int lockCount = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].isLocked()) {
                    lockCount++;
                }
            }
        }
        return lockCount;
    }

    /**
     * Returns the specified cell's value.
     * 
     * @param r the row of the specified cell
     * @param c the col of the specified cell
     * @return the specified cell's value
     */
    public int value(int r, int c) {
        return this.board[r][c].getValue();
    }

    /**
     * Sets the specified cell's value to a new value.
     * 
     * @param r     the row of the specified cell
     * @param c     the col of the specified cell
     * @param value The new value to be set to
     */
    public void set(int r, int c, int value) {
        this.board[r][c].setValue(value);
    }

    /**
     * Sets the specified cell's value and locked status.
     * 
     * @param r      the row of the specified cell
     * @param c      the col of the specified cell
     * @param value  the new value to be set to
     * @param locked the new locked status to be set to
     */
    public void set(int r, int c, int value, boolean locked) {
        this.board[r][c].setValue(value);
        this.board[r][c].setLocked(locked);
    }

    /**
     * Checks if there is already a value in the row, cell, or 3x3 square.
     * 
     * @param row   the row to be checked
     * @param col   the column to be checked
     * @param value the value to be checked
     * @return true if there isn't a value already, otherwise false
     */
    public boolean validValue(int row, int col, int value) {
        if (value < 1 || value > 9) {
            return false;
        }
        // check row
        for (int i = 0; i < SIZE; i++) {
            if (board[i][col].getValue() == value && i != row) {
                return false;
            }
        }
        // check columns
        for (int j = 0; j < SIZE; j++) {
            if (board[row][j].getValue() == value && j != col) {
                return false;
            }
        }
        // check 3x3 square
        // get the index of the 3x3 square (between 0 and 2)
        int squareRow = row / 3;
        int squareCol = col / 3;

        // get middle value of the 3x3 cell
        squareRow = (squareRow*3) + 1;
        squareCol = (squareCol*3) + 1;

        // check if there is a cell that already contains value
        for (int i = squareRow - 1; i < squareRow + 2; i++) {
            for (int j = squareCol - 1; j < squareCol + 2; j++) {
                if (i == row && j == col) {
                    ; // skip the cell we want to check
                } else {
                    if (board[i][j].getValue() == value) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean validSolution() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (this.validValue(i, j, board[i][j].getValue()) == false) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Reads a Sudoku Board
     * 
     * @param filename the file to be read
     * @return if file was read
     */
    public boolean read(String filename) {
        try {
            // assign to a variable of type FileReader a new FileReader object, passing
            // filename to the constructor
            FileReader fr = new FileReader(filename);
            // assign to a variable of type BufferedReader a new BufferedReader, passing the
            // FileReader variable to the constructor
            BufferedReader br = new BufferedReader(fr);

            // assign to a variable of type String line the result of calling the readLine
            // method of your BufferedReader object.
            String line = br.readLine();
            // start a while loop that loops while line isn't null
            int i = 0;
            while (line != null) {
                // assign to an array of type String the result of calling split on the line
                // with the argument "[ ]+"
                // 9 rows of 9 numbers
                String[] result = line.split("[ ]+");
                for (int j = 0; j < result.length; j++) {
                    if(Integer.parseInt(result[j]) != 0) {
                        this.set(i, j, Integer.parseInt(result[j]), true);
                    }
                    else {
                        this.set(i, j, Integer.parseInt(result[j]));
                    }
                }

                // assign to line the result of calling the readLine method of your
                // BufferedReader object.
                line = br.readLine();
                i++;

            }
            // call the close method of the BufferedReader
            br.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Board.read():: unable to open file " + filename);
        } catch (IOException ex) {
            System.out.println("Board.read():: error reading file " + filename);
        }

        return false;
    }

    public void draw(Graphics g, int scale) {
        g.setColor(Color.BLACK);
        // vertical lines
        g.drawLine(25, 20, 25, 370);
        g.drawLine(145, 20, 145, 370);
        g.drawLine(265, 20, 265, 370);
        g.drawLine(385, 20, 385, 370);

        // horizontal lines
        g.drawLine(25, 20, 385, 20);
        g.drawLine(25, 135, 385, 135);
        g.drawLine(25, 250, 385, 250);
        g.drawLine(25, 370, 385, 370);

        for(int i = 0; i<SIZE; i++){
            for(int j = 0; j<SIZE; j++){
                board[i][j].draw(g, j*scale+40, i*scale+40, scale);
            }
        }
        if(finished) {
            if(validSolution()){
                g.setColor(new Color(0, 127, 0));
                g.drawChars("Hurray!".toCharArray(), 0, "Hurray!".length(), scale*4+20, scale*10+10);
            } else {
                g.setColor(new Color(127, 0, 0));
                g.drawChars("No solution!".toCharArray(), 0, "No Solution!".length(), scale*4+20, scale*10+10);
            }
        }
        
    }

    public static void main(String[] args) {
        // normal constructor
        Board myBoard = new Board();
        System.out.println("Columns: " + myBoard.getCols());
        System.out.println("Rows:    " + myBoard.getRows());

        // test methods
        myBoard.set(5, 6, 9);
        myBoard.set(4, 2, 5, true);
        System.out.println(myBoard);

        // test methods
        Cell c = myBoard.get(5, 6);
        System.out.println("Specified cell value: " + c.getValue());
        System.out.println("Specified cell lock state: " + myBoard.isLocked(4, 2));
        System.out.println("Number of locked cells: " + myBoard.numLocked() + "\n");
        
        // test read board constructor
        Board unsolved = new Board("board1.txt");
        System.out.println("Unsolved:");
        System.out.println(unsolved);
        System.out.println("Valid Solution: " + unsolved.validSolution());

        // test valid solution
        Board solved = new Board("board1_solved.txt");
        System.out.println("\n" + "Solved:");
        System.out.println(solved);
        System.out.println("Valid Solution: " + solved.validSolution());

        // test random board constructor
        Board randomBoard = new Board(20);
        System.out.println(randomBoard);
         // getInitialLocked getFinished setFinished , value

        // test other methods
        System.out.println("Number of initial values: " + randomBoard.getInitialLocked());
        System.out.println("Finished: " + randomBoard.getFinished());
        randomBoard.setFinished(true);
        System.out.println("Finished: " + randomBoard.getFinished());
        System.out.println("Cell value: " + randomBoard.value(8, 4));
    }
}
