public class Sudoku {
    // Field for Sudoku Board
    private Board sudokuBoard;
    private LandscapeDisplay ld;
    private int numLocked;

    /**
     * Constructor for Sudoku
     * Initializes the Sudoku board to a blank board.
     */
    public Sudoku() {
        sudokuBoard = new Board();
        ld = new LandscapeDisplay(sudokuBoard);
    }

    /**
     * Constructor for Sudoku
     * Initializes the Sudoku board to create a board
     * with some number of randomly placed values.
     * @param lockedCells num of locked cells to initialize the board
     */
    public Sudoku(int lockedCells) {
        this.sudokuBoard = new Board(lockedCells);
        this.ld = new LandscapeDisplay(sudokuBoard);
        this.numLocked = lockedCells;
    }

    /**
     * Constructor for Sudoku
     * Initializes the Sudoku board to create a board
     * the file's values.
     * @param filename name of Sudoku board file to be read
     */
    public Sudoku(String filename) {
        this.sudokuBoard = new Board(filename);
        this.ld = new LandscapeDisplay(sudokuBoard);
        this.numLocked = sudokuBoard.numLocked();

    }

    /**
     * Solves a given unsolved Sudoku board. Uses a CellStack to backtrack steps
     * if there is no valid value.
     * @return true if solved, false otherwise
     * @param delay the amount of delay between each repaint of board
     * @throws InterruptedException in case the program is interrupted
     */
    public boolean solve(int delay) throws InterruptedException {
        CellStack backtrack = new CellStack();
        int totalUnspecified = 81 - sudokuBoard.numLocked();

        while(backtrack.size() < totalUnspecified) {
            if(ld != null) ld.repaint();
            if(delay > 0) Thread.sleep(delay);

            Cell selectedCell = findNextCell();
            // get a valid value for the selected cell
            if(selectedCell != null) {
                for (int i = 1; i < 10; i++) {
                    if(this.sudokuBoard.validValue(selectedCell.getRow(), selectedCell.getCol(), i)){
                        selectedCell.setValue(i);
                        break;
                    }
                }
            }
            // push the cell onto stack and update board
            if(selectedCell != null) {
                if(this.sudokuBoard.validValue(selectedCell.getRow(), selectedCell.getCol(), selectedCell.getValue())) {
                    backtrack.push(selectedCell);
                    this.sudokuBoard.set(selectedCell.getRow(), selectedCell.getCol(), selectedCell.getValue(), true); 
                }
            }
            // start backtracking if selected cell has no valid values
            else {
                while(backtrack.size() > 0) {
                    Cell backtrackCell = backtrack.pop();
                    boolean newValSet = false; // to check if another valid val is set
                    // search for a new valid value of selected cell (starting at its previous value)
                    for (int i = 1; backtrackCell.getValue() + i < 10; i++) {
                        if(this.sudokuBoard.validValue(backtrackCell.getRow(), backtrackCell.getCol(), backtrackCell.getValue()+i)) {
                            backtrackCell.setValue(backtrackCell.getValue()+i);
                            newValSet = true;
                            break;
                        }
                    }
                    // push the cell onto the stack again and update board with new value
                    if(newValSet == true) {
                        backtrack.push(backtrackCell);
                        this.sudokuBoard.set(backtrackCell.getRow(), backtrackCell.getCol(), backtrackCell.getValue(), true);
                        break;
                    }
                    // no valid values for selected cell, so set it to 0 on board
                    else {
                        this.sudokuBoard.set(backtrackCell.getRow(), backtrackCell.getCol(), 0, false);
                    }
                }
                // there is no solution for the board
                if(backtrack.size() == 0) {
                    this.sudokuBoard.setFinished(true);
                    ld.repaint();
                    return false;
                }
            }
        }
        // we have a solution for the board
        this.sudokuBoard.setFinished(true);
        ld.repaint(); // the loop finishes before we can paint the last cell, so paint again
        return true;
    }

    public Cell findNextCell() {
        int fewestValidVal = 10; // since we need to find the min valid val
        int validValueCount;
        Cell lowestValidValCell = null; // remains null if no cells with 0 have valid value

        for (int i = 0; i < this.sudokuBoard.getRows(); i++) {
            for (int j = 0; j < this.sudokuBoard.getCols(); j++) {
                if(this.sudokuBoard.value(i, j) == 0) {
                    validValueCount = 0;
                    for (int k = 1; k < 10; k++) {
                        if(this.sudokuBoard.validValue(i, j, k)) {
                            validValueCount++;
                        }
                    }
                    // if there is a new lowest valid val count, update the cell to return
                    if(validValueCount < fewestValidVal && validValueCount != 0) {
                        fewestValidVal = validValueCount;
                        lowestValidValCell = this.sudokuBoard.get(i, j);
                    }
                }
            }
        }
        return lowestValidValCell;
    }

    /**
     * Returns the Sudoku display.
     * @return the Sudoku display
     */
    public LandscapeDisplay getDisplay() {
        return this.ld;
    }

    /**
     * Returns the Sudoku board of the Sudoku object.
     * @return the Sudoku board of the Sudoku object.
     */
    public Board getBoard() {
        return this.sudokuBoard;
    }

    /**
     * Resets the game on the display to a new game.
     */
    public void resetGame() {
        sudokuBoard = new Board(numLocked);
        this.ld.setBoard(sudokuBoard);
        this.sudokuBoard.setFinished(false);
    }

    /**
     * Resets the game on the display to a new inputted board.
     * @param board
     */
    public void resetGame(Board board) {
        this.sudokuBoard = board;
        this.ld.setBoard(sudokuBoard);
        this.sudokuBoard.setFinished(false);
    }

    public static void main(String[] args) throws InterruptedException {
        Sudoku readSudoku = new Sudoku("board1.txt");
        readSudoku.solve(0);

        Sudoku newGame = new Sudoku(15);
        System.out.println("Unsolved: ");
        System.out.println(newGame.sudokuBoard);
        // ref: https://stackoverflow.com/questions/180158/how-do-i-time-a-methods-execution-in-java
        // long startTime = System.currentTimeMillis();
        newGame.solve(0);
        // long endTime = System.currentTimeMillis();
        // long duration = (endTime - startTime);

        // System.out.println("Finished in " + duration + " milliseconds \n");
        System.out.println(newGame.sudokuBoard);
        System.out.println("Valid solution: " + newGame.sudokuBoard.validSolution());
    }
}
