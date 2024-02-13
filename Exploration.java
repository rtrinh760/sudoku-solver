public class Exploration {
    private int runs;
    private Sudoku newGame;

    public Exploration(int runs, int lockedCells) {
        this.runs = runs;
        this.newGame = new Sudoku(lockedCells);
        this.newGame.getDisplay().resetBtn.setText("Reset (doesn't work here)");
    }

    public int numOfValidSolutions() throws InterruptedException {
        int validSolutionCount = 0;

        for (int i = 0; i < this.runs; i++) {
            boolean solved = newGame.solve(5);

            Thread.sleep(3000); // pause between each board
            this.newGame.resetGame();

            if(solved) {
                validSolutionCount++;
            }
        }
        this.newGame.getDisplay().repaint();
        return validSolutionCount;
    }

    public static void main(String[] args) throws InterruptedException {
        Exploration sim1 = new Exploration(10, 15);
        int validCount = sim1.numOfValidSolutions();
        System.out.println("Valid solutions: " + validCount);
        System.out.println("Unsolved: " + (sim1.runs - validCount));
    }
}
