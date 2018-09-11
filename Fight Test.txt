
package Fight_Test;
import java.util.*;

public class Fight_Test 
{
	static final int PLAYER_COUNT = 100;		// arbitrarily chosen
	public static Player [] Players = new Player [PLAYER_COUNT];
	public static int Deaths = 0;
	public static ArrayList<String> taken_Names = new ArrayList<String>();
	public static ArrayList<Player> dead_Players = new ArrayList<Player>();	
               
	public static void main (String [] args)
	{	
		for (int i = 0; i < Players.length; ++i)
			Players[i] = new Player(Name_Player());
		System.out.println("Welcome to the Multi-Agent Fight Survival Simulator!");
		System.out.println("Let's see who makes it to the top!\n...");
		System.out.println("Press 's' to start\n");
		Scanner keyboard = new Scanner(System.in);
		char c = keyboard.next().charAt(0);
		if (c == 's') 
		{	System.out.println("OKAY, here we go...\n\n");
			Fight();	
                        Sort_By_Rank();
                        Print_Results();
                }
	}
        
        static class Player 
        {
            public String name;
            public boolean isDead = false;
            public int kills;
            public int rank;
	
            public Player (String n)
            {
		name = n;
		kills = 0;
		rank = 0;
            }
        }
	
	static void Fight()
	{
		
		while (PLAYER_COUNT - 1 > Deaths)   // the loops stops after 9 matches, so there's one player left, the winner
                {
			Match();
                       // System.out.println("\nPLAYERS REMAINING: " + (PLAYER_COUNT - Deaths) + "\n");
		}
		
	}

	static String Name_Player ()
	{
		int rindex = (int)(Math.random()*Players.length);
		for (String s : taken_Names) 
			if ( s.equals(player_Names[rindex]) )return Name_Player();
		return player_Names[rindex];	
	}
	
	static void Match ()
	{
		int P1_index = Find_Random_P1();
		int P2_index = Find_Random_P2(-1);
		int killOrDie = (int)(Math.random()*3);     // 3 possible outcomes, including draw in which case both players survive
		if (killOrDie == 0)
			Kill (P1_index, P2_index);
		else if (killOrDie == 1) Kill (P2_index, P1_index);
	}
	static void Kill (int p1, int p2)
	{
		Players[p1].isDead = true;
		Players[p2].kills++;
		++Deaths;
		Players[p1].rank = PLAYER_COUNT - Deaths + 1;   // the loser
		Players[p2].rank = PLAYER_COUNT - Deaths;       // the winner
	}
	static int Find_Random_P1 ()
	{
		int p1 = (int)(Math.random()*PLAYER_COUNT);
                System.out.println(p1 + "\t" + Players[p1].name);
		if (Players[p1].isDead == true) 
                {
                    System.out.println("They're dead, all right. Let's try another...");
                    return Find_Random_P1 ();
                }
		return p1;
	}

	static int Find_Random_P2 (int p1)
	{
               // System.out.println("Value of p1 from last call: " + p1);
		int p2 = (int)(Math.random()*PLAYER_COUNT);
                //System.out.println("Player 2: " + p2 + "\t" + Players[p2].name);
		if (p2 == p1) 
                {
                   // System.out.println("That matches player 1, time to try another...");
                    return Find_Random_P2(p2);
                } 
		if (Players[p2].isDead == true) 
                {
                   // System.out.println("Well, that person's dead; time to try another...");
                    return Find_Random_P2(-1);
                }
		return p2;
	}

        static void Print_Results()
        {
            System.out.println("The results are in! Let's see who's eating a steamy chicken dinner tonight: (listed by rank)");
            for (Player p : Players)
                System.out.println("\t" + p.rank + ".  " + p.name + "\tKills:  " + p.kills);
            System.out.println("Total Deaths:  " + Deaths);
            System.out.println("And there you have it, folks! It's been a truly brutal fight test! Thanks for tuning in!");
            System.out.println("...\nEND TEST");
        }
        
        static void Sort_By_Rank()              // It's Bubblesort, but with players, by rank
        {
            for (int i = 0; i < Players.length; ++i)
                for (int j = Players.length - 1; j > i; --j)
                    if (Players[j].rank < Players[j-1].rank)
                    {
                        Player temp = Players[j];
                        Players[j] = Players[j-1];
                        Players[j-1] = temp;
                       // System.out.println(Players[i].name);
                    }
        }

	static String [] player_Names = 
	{ 	"Chloe", "Alex", "John", "Ferdinand", "Robert", "Jacob", "Tyrone", "Tyrese",
		"Michonne", "Michelle", "Michaela", "Madame Curie", "Lisa", "Patricia", "Patrick",
		"Kayleigh", "Cara", "Katherine", "Katie", "Randy Moss", "Stewart", "Ryan", "Brian",
		"Lucifer", "Lucas", "Luanne", "Lena", "Christina", "Alberta", "Alberto", "Kaibo",
		"Lucille", "Terry Crews", "Ray Lewis", "Tom Brady", "Edgar", "Louis", "Steven",
		"Mary", "Beth", "Isabella", "Julia", "Mauricio", "Kim", "Dom", "Marcus", 
		"Augustus", "Bert", "Damon Baird", "Anya Stroud", "Sam", "David", "Jennifer",
		"Ruiz", "Tyler", "Taylor", "Hannah", "Caleb", "Mohammed", "Ahmed", "Suleyman",
		"Sullivan", "Mariam", "Rosine", "Josiphina", "Ilona", "Caterina", "Blas Vivar",
		"Richard Sharpe", "Captain Ahab", "Moby Dick", "Ishmael", "Billy Bud", 
		"Captain Kurtz", "Lady Macbeth", "Duncan", "Dunkirk", "Mel Gibson", "Heath Ledger",
		"Maggie Gyllenhaal", "Jake Gyllenhaal", "Lawrence", "Laura", "DizKarma",
		"Kendra Charlene Ford", "Adam", "Fontaine", "Ayn Rand", "Andrew", "Mercy",
		"Wilhelm Reinhardt", "Genji Shimada", "Hanzo Shimada", "Daisuke", "Sasuke", "Harada-san",
		"Captain Underpants", "Father Sal", "Saint Anthony", "Jacqueline Kelly",
		"Ledwig", "Linda", "Franklin", "Joseph", "Mark", "Jonathan", "Juan", "Santiago",
		"Gordon Freeman", "Morgan Freeman", "Acharael", "Azazel", "Gabriel", "Tyrael",
		"Tamriel", "Ezekiel", "Cain", "Abel", "Sarah"		};      // okay, so there are 100+ names, good
}