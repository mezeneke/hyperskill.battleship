package battleship;

import java.io.IOException;
import java.util.Scanner;

import static battleship.Ship.type.*;
import static battleship.Coordinate.*;

class gameMaster {
    Scanner scan;
    final static Ship.type[] ships =  new Ship.type[] {AIRCRAFTCARRIER, BATTLESHIP, SUBMARINE, CRUISER, DESTROYER};
    final int hitsWin = calcHits();

    /**
     *Game master class:
     * - deploys methods for different game phases such as: fleet deployment, skirmish
     * - reads player input
     * - validates player input
     * - defines ship placement order
     * - defines winning condition
     */
    gameMaster() {
        this.scan = new Scanner(System.in);
    }

    private int calcHits() {
        int i = 0;
        for (Ship.type ship : ships) {
            i += ship.cells;
        }
        return i;
    }

    Coordinate[] scanShipCoordinates() {
        Coordinate c1 = scanCoordinate();
        Coordinate c2 = scanCoordinate();
        scan.nextLine();

        Coordinate[] coordinates = new Coordinate[] {c1, c2};

        if (!isValid(c1) || !isValid(c1) || areDiagonal(c1, c2)) {
            System.out.println("Error! Wrong ship location! Try again:");
            coordinates = scanShipCoordinates();
        }
        return coordinates;
    }

    Coordinate scanTarget() {
        Coordinate c = scanCoordinate();
        scan.nextLine();

        if(!isValid(c)) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            c = scanTarget();
        }
        return c;
    }

    private Coordinate scanCoordinate() {
        String str = scan.next();
        Coordinate c = new Coordinate(str);
        return c;
    }

    /**
     * Subroutine: Taking inputs (coordinates) of the player, verifies them and deploys the ships in the player's Fleet.
     * @param player object
     */
    void initiateFleetDeployment(Player player) {
        boolean isPDeployed;

        System.out.println(player.name + ", place your ships to the game field\n");
        player.fleet.print();
        System.out.println();

        for (Ship.type ship : ships) {
            System.out.printf("Enter the coordinates of the %s (%d cells):", ship.name, ship.cells);
            System.out.println();

            isPDeployed = false;

            do {
                Coordinate[] coordinates = scanShipCoordinates();
                Coordinate c1 = coordinates[0];
                Coordinate c2 = coordinates[1];

                //The methods' collision() and placeShip() depend on c1 preceding c2. reverse() guarantees this.
                reverse(c1, c2);

                if (length(c1, c2) != ship.cells) {
                    System.out.printf("Error! Wrong length of the %s! Try again:", ship.name);
                } else if (player.fleet.collision(c1, c2)) {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                } else {
                    player.fleet.placeShip(c1, c2, ship.cells);

                    System.out.println();
                    player.fleet.print();
                    System.out.println();

                    isPDeployed = true;
                }

            } while (!isPDeployed);

        }
    }

    /**
     * Launches a skirmish sequence.
     * The attacking player's coordinates are taken as input.
     * The attacking player attacks the defending player at the coordinate.
     * @param attacker
     * @param defender
     */
    void launchSkirmish(Player attacker, Player defender) {
        String str;
        Coordinate c;

        defender.fleet.print(true);
        System.out.println("---------------------");
        attacker.fleet.print();
        System.out.println();

        System.out.println(attacker.name + ", it's your turn;");

        c = scanTarget();

        attacker.attack(defender, c);
    }

    /**
     * Launches a skirmish sequence.
     * The attacking player attacks the defending player at the coordinate.
     * @param attacker
     * @param defender
     * @param c coordinate
     */
    void launchSkirmish(Player attacker, Player defender, Coordinate c) {

        defender.fleet.print(true);
        System.out.println("---------------------");
        attacker.fleet.print();
        System.out.println();

        System.out.println(attacker.name + ", it's your turn;");

        do {
            if(!isValid(c)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
            }
        } while (!isValid(c));

        attacker.attack(defender, c);
    }

    void passMove() {

        System.out.println("Press Enter and pass the move to another player");

        try {
            System.in.read();
        } catch (IOException e) {

        }

    }
}


