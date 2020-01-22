import java.util.*;

public class Board {

    // Board data structures
    private int[][] boardArray;
    private List<Integer> emptyPos;
    private int numCol;
    private int numRow;
    private int gameGoal;
    private boolean winFlag = false;;

    // Board constructor

    /**
     * The board constructor
     * 
     * @param numRow   the board's number of rows
     * @param numCol   the board's number of columns
     * @param gameGoal the game goal for the board
     */
    public Board(int numRow, int numCol, int gameGoal) {
        this.boardArray = new int[numRow][numCol];
        this.emptyPos = new LinkedList<>();
        this.numCol = numCol;
        this.numRow = numRow;
        this.gameGoal = gameGoal;
        for (int i = 0; i < numRow * numCol; i++) {
            this.emptyPos.add(i);
        }
    }

    // Board usage methods

    /**
     * The main function to support the gameplay
     * 
     * @param dir The direction input from Main class It supports W: up, D: right,
     *            S: down, A: left
     */
    public void playBoard(String dir) {
        dir = dir.toUpperCase();
        int[] startingPos = new int[2];
        int[] goDir = new int[2];
        int[] nextDir = new int[2];
        switch (dir) {
        case "W":
            startingPos = new int[] { 0, 0 };
            goDir = new int[] { 1, 0 };
            nextDir = new int[] { 0, 1 };
            break;
        case "D":
            startingPos = new int[] { 0, this.numCol - 1 };
            goDir = new int[] { 0, -1 };
            nextDir = new int[] { 1, 0 };
            break;
        case "S":
            startingPos = new int[] { this.numRow - 1, this.numCol - 1 };
            goDir = new int[] { -1, 0 };
            nextDir = new int[] { 0, -1 };
            break;
        case "A":
            startingPos = new int[] { this.numRow - 1, 0 };
            goDir = new int[] { 0, 1 };
            nextDir = new int[] { -1, 0 };
            break;
        default:
            throw new NumberFormatException();
        }

        int oldM = startingPos[0];
        int oldN = startingPos[1];

        int newM = oldM + goDir[0];
        int newN = oldN + goDir[1];

        int setM = startingPos[0];
        int setN = startingPos[1];

        boolean movedFlag = false;

        while (newM >= 0 && newM < numRow && newN >= 0 && newN < numCol) {
            while (newM >= 0 && newM < numRow && newN >= 0 && newN < numCol) {
                if (this.boardArray[newM][newN] != 0) {
                    if (this.boardArray[oldM][oldN] == this.boardArray[newM][newN]) {
                        movedFlag = moveNum(oldM, oldN, newM, newN) || movedFlag;
                        if (oldM != newM || oldN != newN) {
                            oldM += goDir[0];
                            oldN += goDir[1];
                        }
                    } else if (this.boardArray[oldM][oldN] == 0) {
                        movedFlag = moveNum(oldM, oldN, newM, newN) || movedFlag;
                    } else {
                        oldM += goDir[0];
                        oldN += goDir[1];
                        continue;
                    }
                }

                newM += goDir[0];
                newN += goDir[1];

            }
            setM += nextDir[0];
            setN += nextDir[1];

            oldM = setM;
            oldN = setN;

            newM = oldM + goDir[0];
            newN = oldN + goDir[1];
        }

        if (movedFlag) {
            generateNext();
            if (this.winFlag) {
                throw new GameWinException();
            }
        } else {
            if (emptyPos.size() == 0) {
                throw new GameOverException();
            }
            System.out.println("No valid moves in this direction!");
            return;
        }

    }

    /**
     * A helper funciton that move a number into a specified new board position
     * 
     * @param oldM old column position
     * @param oldN old row position
     * @param newM new column position
     * @param newN new row position
     * @return a boolean entailing whether the move is successfully performed
     */

    public boolean moveNum(int oldM, int oldN, int newM, int newN) {
        if (oldM == newM && oldN == newN) {
            return false;
        }
        if (this.boardArray[oldM][oldN] == this.boardArray[newM][newN]) {
            if (this.boardArray[oldM][oldN] == 0) {
                return false;
            }
            setNum(oldM, oldN, this.boardArray[oldM][oldN] + this.boardArray[newM][newN]);
            setNum(newM, newN, 0);
            return true;
        } else if (this.boardArray[oldM][oldN] == 0) {
            setNum(oldM, oldN, this.boardArray[newM][newN]);
            setNum(newM, newN, 0);
            return true;
        }
        return false;
    }

    /**
     * A helper function that sets the value at a specified board postion
     * 
     * @param m   column position
     * @param n   row position
     * @param val the value to set
     */

    public void setNum(int m, int n, int val) {
        if (boardArray[m][n] != val) {
            int hash = hashCode(m, n);
            if (val == 0) {
                int insertionIndex = Arrays.binarySearch(emptyPos.toArray(), hash);
                if (insertionIndex < 0) {
                    emptyPos.add(-insertionIndex - 1, hash);
                } else {
                    System.out.println("Wrong empty pos: insert");
                }
            } else if (boardArray[m][n] == 0) {
                int removeIndex = Arrays.binarySearch(emptyPos.toArray(), hash);
                if (removeIndex >= 0) {
                    emptyPos.remove(removeIndex);
                } else {
                    System.out.println("Wrong empty pos: remove");
                }
            }
            boardArray[m][n] = val;
            if (val >= gameGoal) {
                winFlag = true;
            }

        }

    }

    public void generateNext() {
        if (emptyPos.size() == 0) {
            throw new GameOverException("No more empty positions");
        }
        Random random_num = new Random();
        int changeIndex = random_num.nextInt(emptyPos.size());
        int[] MN = unHash(emptyPos.get(changeIndex));
        int val = (random_num.nextInt(2) + 1) * 2;
        setNum(MN[0], MN[1], val);
    }

    // Board util methods

    /**
     * A utility function that prints the current board and align the numbers
     */

    public void printBoard() {
        System.out.println();
        String[] blanks = new String[] { "", "    ", "   ", "  ", " " };
        for (int[] arr : boardArray) {
            StringBuilder sb = new StringBuilder();
            for (int i : arr) {
                sb.append(i);
                int numLen = (i == 0) ? 1 : (int) (Math.log10(i) + 1);
                sb.append(blanks[numLen % 5]);
            }
            System.out.println(sb.toString());
        }
        System.out.println();
    }

    /**
     * A utility function that sets the board layout according to a given 2D int
     * array test input
     */

    public void setBoard(int[][] setBoard) {
        for (int i = 0; i < setBoard.length; i++) {
            for (int j = 0; j < setBoard[0].length; j++) {
                this.setNum(i, j, setBoard[i][j]);
            }
        }
    }

    /**
     * Utility functions that translates between the column and row positions and a
     * single hashed number
     * 
     * Used in indexing the empty positions
     */

    private int hashCode(int m, int n) {
        return m * boardArray[0].length + n;
    }

    private int[] unHash(int hash) {
        return new int[] { hash / boardArray[0].length, hash % boardArray[0].length };
    }
}
