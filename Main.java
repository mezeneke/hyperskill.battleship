package battleship;

public class Main {

    public static void main(String[] args) {
        // Write your code here
        gameMaster gm = new gameMaster();
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");

        gm.initiateFleetDeployment(p1);
        gm.passMove();
        gm.initiateFleetDeployment(p2);
        gm.passMove();

        Player attacker = p1;
        Player defender = p2;

        while (p1.getHits() < gm.hitsWin && p2.getHits() < gm.hitsWin) {
            gm.launchSkirmish(attacker, defender);
            attacker = attacker == p1 ? p2 : p1;
            defender = defender == p1 ? p2 : p1;

            if (p1.getHits() < gm.hitsWin && p2.getHits() < gm.hitsWin) {
                gm.passMove();
            } else {
                System.out.println("You sank the last ship. You won. Congratulations!\n");
            }
        }

    }
}

