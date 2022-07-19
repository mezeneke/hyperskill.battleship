package battleship;

import java.util.Arrays;

import static battleship.Coordinate.isValid;

class Fleet {

    final static int ROWS = 10;
    final static int COLS = 10;
    final Symbol WATER = new Symbol('~', "You missed!");
    final Symbol DAMAGED = new Symbol('X', "You hit a ship!");
    final Symbol MISSED = new Symbol('M', "You missed!");
    private final Symbol[][] field;

    Fleet() {
        this.field = initializeField();
    }

    private Symbol[][] initializeField(){
        Symbol[][] field = new Symbol[ROWS][COLS];
        for (int row = 0; row < ROWS; row++) {
            Arrays.fill(field[row], WATER);
        }
        return field;
    }

    public Symbol getField(Coordinate c) {
        return field[c.row][c.col];
    }

    /**
     * Creates a random Fleet.
     */
    void random() {
        Coordinate c1;
        Coordinate c2;
        for (Ship.type ship : gameMaster.ships) {
            do {
                //define first random coordinate. c1 is always valid.
                c1 = new Coordinate((int) (Math.random() * 10), (int) (Math.random() * 10));

                //horizontal is 0 or 1. vertical is |horizontal - 1|.
                int horizontal = (int) (Math.random() * 2);
                int vertical = horizontal > 0 ? 0 : 1;

                //c2 spans x cells vertically or horizontally from c1
                c2 = new Coordinate(c1.row + vertical * (ship.cells - 1), c1.col + horizontal * (ship.cells - 1));

                //if c2 is not valid or the coordinates collide with another ship the routine restarts.
            } while (!isValid(c2) || collision(c1, c2));

            placeShip(c1, c2, ship.cells);
        }

    }

    /**
     * Prints the fleet to the standard output.
     */
    void print() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int row = 0; row < ROWS; row++) {
            System.out.print((char) (65 + row) + " ");
            for (int col = 0; col < COLS; col++) {
                System.out.print(field[row][col].symbol + " ");
            }
            System.out.println();
        }
    }

    /**
     * Prints the fleet to the standard output.
     * @param fog if true, the ships are hidden behind the fog of war
     */
    void print(boolean fog) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int row = 0; row < ROWS; row++) {
            System.out.print((char) (65 + row) + " ");
            for (int col = 0; col < COLS; col++) {
                if (fog && field[row][col].symbol == 'O') {
                    System.out.print(WATER.symbol + " ");
                } else {
                    System.out.print(field[row][col].symbol + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Places a ship with coordinates c1 and c2 in the field of the Fleet object.
     * @param c1 first coordinate
     * @param c2 second coordinate
     * @requirements c1.row <= c2.row & c1.col <= c2.col. User reverse() method to swap coordinates, if requirement is not met.
     */
    void placeShip(Coordinate c1, Coordinate c2, int size) {
        Ship ship = new Ship(size);
        for (int row = c1.row; row <= c2.row; row++) {
            for (int col = c1.col; col <= c2.col; col++) {
                field[row][col] = ship;
            }
        }
    }

    /**
     * Checks whether a ship with coordinates c1 and c2 collides is to close to another ship.
     * @param c1 first coordinate
     * @param c2 second coordinate
     * @return true, if the ship collides with another one or is to close.
     * @requirements c1.row <= c2.row & c1.col <= c2.col. User reverse() method to swap coordinates, if requirement is not met.
     */
    boolean collision(Coordinate c1, Coordinate c2) {
        for (int row = c1.row; row <= c2.row; row++) {
            for (int col = c1.col; col <= c2.col; col++) {

                if(row > 0 && isOccupied(row - 1, col)) {
                    return true;
                }

                if(row < ROWS - 1  && isOccupied(row + 1, col)) {
                    return true;
                }

                if(col > 0 && isOccupied(row, col - 1)) {
                    return true;
                }

                if(col < COLS - 1 && isOccupied(row, col + 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifies if the coordinate is already occupied by another ship.
     * @param row y-dimension of the coordinate
     * @param col x-dimension of the coordinate
     * @return true, if the field is already occupied
     */
    boolean isOccupied(int row, int col) {
        return field[row][col].symbol == 'O';
    }

    /**
     * Hits the fleet at coordinate c.
     * @param c coordinate
     */
    void hit(Coordinate c) {
        field[c.row][c.col].hit();
        if(field[c.row][c.col].symbol == 'O') {
            field[c.row][c.col] = DAMAGED;
        } else {
            field[c.row][c.col] = MISSED;
        }
    }
}
