import java.util.*;

class Main {
    public static void main(String[] args) {
        System.out.println(" \n***********************************************************************************\n");
        System.out.println(
                "***                           Welcome to 2048 flight                            ***\n*** This game program is born from my sheer boredom during flights without wifi ***\n");
        System.out.println("***********************************************************************************\n");
        // Read from stdin, solve the problem, write answer to stdout.
        Scanner in = new Scanner(System.in);
        String str;
        int goal = 2048;
        System.out.println("Define your game goal: \nMust be greater than 2 and a power of 2\n");

        // Set up the game goal
        while (true) {
            try {
                str = in.nextLine();
                goal = Integer.parseInt(str);

                // Use hamming distance to verify that the goal is a power of 2
                int total = 0;
                for (int j = 0; j < 32; j++) {
                    total += ((goal >> j) & 1) ^ ((0 >> j) & 1);
                }
                if (goal < 2 || total != 1) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                System.out
                        .println("Please enter a valid game goal number! \nMust be greater than 1 and a power of 2\n");
                continue;
            }
        }

        System.out.println();
        System.out.println(
                "Define your board size below: \ne.g. key in 4 means a 4*4 board, key in 4 2 means a 4*2 board\n");

        String[] splited;

        // Set up a placeholder new board
        Board newBoard = new Board(1, 1, goal);

        // Set up the board dimensions
        // Polling until the rightly formatted numbers are caught
        while (true) {
            try {
                str = in.nextLine();
                splited = str.split(" ");
                if (splited.length == 1) {
                    int m = Integer.parseInt(splited[0]);
                    newBoard = new Board(m, m, goal);
                    break;
                } else if (splited.length == 2) {
                    int m = Integer.parseInt(splited[0]);
                    int n = Integer.parseInt(splited[1]);
                    newBoard = new Board(m, n, goal);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid board dimension number! \n");
                continue;
            }
        }

        System.out.println("\nInstructions: W: up, D: right, S: down, A: left");

        // Kick off the first board
        newBoard.generateNext();
        newBoard.printBoard();

        // The main interactive sesssion
        // Polling until the game ending with winning or losing
        while (true) {
            str = in.nextLine();
            if (str.toLowerCase().equals("help")) {
                System.out.println();
                System.out.println("Instructions: W: up, D: right, S: down, A: left \n");
                continue;
            }
            try {
                newBoard.playBoard(str);
            } catch (GameOverException e) {
                System.out.println("\nGAME OVER! \nYour final board: ");
                newBoard.printBoard();
                in.close();
                return;
            } catch (GameWinException e) {
                System.out.println("\nYOU WON! \nYour final board: ");
                newBoard.printBoard();
                in.close();
                return;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid direction command! \n");
                continue;
            }
            newBoard.printBoard();
        }

    }
}
