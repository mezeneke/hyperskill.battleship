package battleship;

public class Ship extends Symbol {

    int size;
    int hits;

    Ship(int size) {
        super('O', "You hit a ship");
        this.size = size;
        this.hits = 0;
    }

    enum type {
        AIRCRAFTCARRIER("Aircraft Carrier", 5),
        BATTLESHIP("Battleship", 4),
        SUBMARINE("Submarine", 3),
        CRUISER("Cruiser", 3),
        DESTROYER("Destroyer", 2);

        final String name;
        final int cells;

        private type(String name, int cells) {
            this.name = name;
            this.cells = cells;
        }
    }

    void hit() {
        hits++;
        if (hits < size) {
            super.hit();
        } else {
            System.out.println("You sank a ship!");
            System.out.println();
        }
    }
}


