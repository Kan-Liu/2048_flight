public class GameOverException extends RuntimeException {
    private static final long serialVersionUID = 5162710183389028792L;

    /**
     * Constructs a {@code GameOverException} with no detail message.
     */
    public GameOverException() {
        super();
    }

    /**
     * Constructs a {@code GameOverException} with the specified detail message.
     *
     * @param s the detail message.
     */
    public GameOverException(String s) {
        super(s);
    }
}