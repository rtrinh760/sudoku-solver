public class NewBoard {
    public static void main(String[] args) throws InterruptedException {
        Sudoku game = new Sudoku(23);
        game.solve(0);
        while(true) {
            game.getDisplay().resetButtonClick();
            if(game.getBoard().getFinished()) {
                game.resetGame(game.getDisplay().getBoard());
                game.solve(10);
            }
        }
    }
}
