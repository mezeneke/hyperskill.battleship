package battleship;

public class Simulation {
    public static void sim(String[] args) {
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");
        gameMaster gm = new gameMaster();

        p1.fleet.random();
        p2.fleet.random();

        Player attacker = p1;
        Player defender = p2;

        while (p1.getHits() < gm.hitsWin && p2.getHits() < gm.hitsWin) {

            gm.launchSkirmish(attacker, defender,  new Coordinate((int) (Math.random() * 10), (int) (Math.random() * 10)));
            attacker = attacker == p1 ? p2 : p1;
            defender = defender == p1 ? p2 : p1;

            if (p1.getHits() < gm.hitsWin && p2.getHits() < gm.hitsWin) {
                //gm.passMove();
            } else {
                System.out.println("You sank the last ship. You won. Congratulations!\n");
            }
        }
    }
}
