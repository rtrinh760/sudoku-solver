import java.awt.Graphics;
import java.awt.Color;

public class Cell {
    // Fields for cell
    private int row;
    private int col;
    private int value;
    private boolean isLocked;

    /**
     * Constructor for Cell
     * Initializes values to 0 or false.
     */
    public Cell() {
        this.row = 0;
        this.col = 0;
        this.value = 0;
        this.isLocked = false;
    }
    
    /**
     * Constructor for Cell
     * Initializes values for row, col, and value.
     * isLocked is is initialized to false.
     * @param row cell row
     * @param col cell col
     * @param value cell value
     */
    public Cell(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.isLocked = false;
    }

    /**
     * Constructor for Cell
     * Initializes values for row, col, value, and isLocked.
     * @param row cell row
     * @param col cell col
     * @param value cell value
     * @param lock cell locked state
     */
    public Cell(int row, int col, int value, boolean lock) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.isLocked = lock;
    }

    /**
     * Returns the row the cell is located at.
     * @return the row the cell is located at
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns the column the cell is located at
     * @return the column the cell is located at
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Returns the value of the cell.
     * @return the value of the cell
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Sets the cell's value to a new value
     * @param newVal the new value to set to
     */
    public void setValue(int newVal) {
        this.value = newVal;
    }

    /**
     * Returns the lock state of the cell
     * @return the lock state of the cell
     */
    public boolean isLocked() {
        return this.isLocked;
    }

    /**
     * Sets the lock state of the cell
     * @param lock The lock state to be set to.
     */
    public void setLocked(boolean lock) {
        this.isLocked = lock;
    }

    /**
     * Returns a string form of the cell value
     * @return a string form of the cell value
     */
    public String toString() {
        return "Cell value: " + this.getValue();
    }

    /**
     * Draws a cell on the Sudoku board.
     * @param g The graphics object to draw the cell's number on
     * @param x the x coordinate to draw the number at
     * @param y the y coordinate to draw the number at
     * @param scale the size the grid cell should be
     */
    public void draw(Graphics g, int x, int y, int scale) {
        char toDraw = (char) ((int) '0' + this.getValue()); // in case getValue is null
        g.setColor(isLocked ? Color.BLUE : Color.RED);
        g.drawChars(new char[] {toDraw}, 0, 1, x, y);
    }
}