package battleship;

class Player {

    final String name;
    final Fleet fleet;
    private int hits;

    Player(String name) {
        this.name = name;
        this.fleet = new Fleet();
        this.hits = 0;
    }

    public int getHits() {
        return hits;
    }

    /**
     * Hits the opponent's fleet at the specified coordinate
     * @param player opponent player
     * @param c coordinate of opponents fleet to hit
     */
    void attack(Player player, Coordinate c) {

        if(player.fleet.getField(c).symbol == 'O') {
            hits++;
        }

        player.fleet.hit(c);
    }
}
