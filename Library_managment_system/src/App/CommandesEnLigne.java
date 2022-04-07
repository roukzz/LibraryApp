package App;


import java.util.Scanner;

public class CommandesEnLigne {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String[][] inventaire = TP3Utils.lireFichierInventaire();
		Livre[] livreInventaire = remplirInventaireLivre(inventaire);
	
		
		Livre[] panier = new Livre[0];
		while (true) {
			afficherMenu();
			String validUserInput = validateUserInput();
			if (validUserInput.equals("1")) {
				// Magasiner

				panier = Magasiner.magasiner(livreInventaire, panier);
			} else if (validUserInput.equals("2")) {
				// voir Panier

				panier = Panier.optionPanier(panier, livreInventaire);

			} else {
				// quitter
				System.out.println("Au Revoir !!");
				break;
			}
		}

	}
	
	
	


	public static Livre[] remplirInventaireLivre(String[][] inventaire2) {
		Livre[] inventaireLivre2 = new Livre[inventaire2.length];
		// populate inventaireLivre
		for (int i = 0; i < inventaireLivre2.length; i++) {
			try {
				Livre lv = new Livre(inventaire2[i][0], inventaire2[i][1], Double.parseDouble(inventaire2[i][2]),
						Integer.parseInt(inventaire2[i][3]));

				if (inventaire2[i].length > 4) {
					// if there is a category associated with this book
					for (int j = 4; j < inventaire2[i].length; j++) {
						lv.ajouterCategorie(Integer.parseInt(inventaire2[i][j]));
					}
				}

				inventaireLivre2[i] = lv;

			} catch (Exception e) {
				System.out.println("error: couldnt create object fro, invetnaire to inventaire Livre");
			}

		}

		return inventaireLivre2;

	}
	
	public static void afficherMenu() {
		System.out.println("L I B R A I R I E A L I V R E O U V E R T\r\n" + "-----------------\r\n"
				+ "COMMANDE EN LIGNE\r\n" + "-----------------\r\n" + "1. Magasiner\r\n" + "2. Voir panier\r\n"
				+ "3. Quitter\r\n" + "Entrez votre choix au menu :");
	}

	private static String validateUserInput() {

		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		while (!input.equals("1") && !input.equals("2") && !input.equals("3")) {
			afficherMenu();

			input = in.nextLine();
		}

		return input;
	}

	

	

	public static void printListObjects(Livre[] inventaireLivre3) {
		for (int i = 0; i < inventaireLivre3.length; i++) {
			System.out.println(inventaireLivre3[i].toString());
			System.out.println(inventaireLivre3[i].getQteEnInventaire());
		}
	}
}
