import java.util.*;

class Test {
    public static void main(String[] args) {

        // Read from stdin, solve the problem, write answer to stdout.
        Scanner in = new Scanner(System.in);
        String str;

        int[][] testBoard = new int[][] { { 16, 0, 16, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } };
        Board newBoard = new Board(testBoard.length, testBoard[0].length, 32);

        newBoard.setBoard(testBoard);
        newBoard.printBoard();

        while (true) {
            str = in.nextLine();
            try {
                newBoard.playBoard(str);
            } catch (GameOverException e) {
                System.out.println("GAME OVER!");
                System.out.println("Your final board: ");
                newBoard.printBoard();
                return;
            } catch (GameWinException e) {
                System.out.println("YOU WON!");
                System.out.println("Your final board: ");
                newBoard.printBoard();
                return;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid direction command!");
                continue;
            }

            newBoard.printBoard();
        }

    }
}
