public class GameWinException extends RuntimeException {
    private static final long serialVersionUID = 5162710183389028792L;

    /**
     * Constructs a {@code GameWinException} with no detail message.
     */
    public GameWinException() {
        super();
    }

    /**
     * Constructs a {@code GameWinException} with the specified detail message.
     *
     * @param s the detail message.
     */
    public GameWinException(String s) {
        super(s);
    }
}