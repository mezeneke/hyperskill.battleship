package battleship;

public class Symbol {
    final char symbol;
    String message;

    Symbol(char symbol, String message) {
        this.symbol = symbol;
        this.message = message;
    }

    void hit() {
        System.out.println(message);
        System.out.println();
    }
}
