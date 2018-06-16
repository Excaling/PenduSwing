import game.Pendu;
import java.util.Scanner;
public class Test {

	public static void main(String[] args) {
		Pendu pendu = new Pendu(12, "issou");
		char[] secret = pendu.getMaskWord();
		char tentation;
		Scanner lect = new Scanner(System.in);
		for(int i = 0; i < pendu.getSecretWord().length(); i++) {
			System.out.print(secret[i]);
		}
		
		while(pendu.chance != 0 && pendu.win != true) {
			System.out.println("veuillez enregistrer un caractère");
			tentation = lect.nextLine().charAt(0);
			pendu.toTry(tentation);
			pendu.displaySecret();
		}
		if(pendu.win == true)
			System.out.println("You win");
		else
			System.out.println("You loose");
		
	}
	
}
