package App;

import java.util.Scanner;

public class Magasiner {
	public static Livre[] magasiner(Livre [] livreInventaire,Livre[] panier) {
		// TODO Auto-generated method stub
		Livre[] nouveauPanier = panier;
		while(true) {
			afficherCategories();
			String categorieChoisie = validerCategorieInput();
			
			if(categorieChoisie.length() < 1) {
				// enter key was pressed
				System.out.println("retour au menu principal !");
				return nouveauPanier;
			}else if(categorieChoisie.equals("1")) {
				//Science fiction
				nouveauPanier=categorieMenu(livreInventaire,1,nouveauPanier);
//				String userInput = optionSousMenuChoisi();
			}else if (categorieChoisie.equals("2")) {
				// Romance
				nouveauPanier=categorieMenu(livreInventaire,2,nouveauPanier);
			}else if (categorieChoisie.equals("3")) {
				// Thriller
				nouveauPanier=categorieMenu(livreInventaire,3,nouveauPanier);
			}else if (categorieChoisie.equals("4")) {
				// Policier
				nouveauPanier=categorieMenu(livreInventaire,4,nouveauPanier);
			}else if (categorieChoisie.equals("5")) {
				//Humour
				nouveauPanier=categorieMenu(livreInventaire,5,nouveauPanier);
			} else if (categorieChoisie.equals("6")) {
				//Drame
				nouveauPanier=categorieMenu(livreInventaire,6,nouveauPanier);
			}
		}
		
	}
	
	
	private static void afficherCategories() {
		System.out.println("----------\r\n" + 
				"CATEGORIES\r\n" + 
				"----------\r\n" + 
				"1. Science fiction\r\n" + 
				"2. Romance\r\n" + 
				"3. Thriller\r\n" + 
				"4. Policier\r\n" + 
				"5. Humour\r\n" + 
				"6. Drame\r\n" + 
				"Entrez une categorie (ENTREE pour annuler):");
	}
	private static String validerCategorieInput() {
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		while(!input.equals("1")  && !input.equals("2")  && !input.equals("3") && !input.equals("4") && !input.equals("5") && !input.equals("6") && !input.equals("") ) {
			afficherCategories();
			 input = in.nextLine();
		}

		return input;
	}

	private static Livre[] categorieMenu(Livre [] livreInvantaire,int codeCat,Livre [] panier) {
		// TODO Auto-generated method stub
			Livre [] livresDansCat = construireListDeLivreDansCategorie(livreInvantaire,codeCat);
			Livre [] nouveauPanier = panier;
			int qte = 0;
			if(livresDansCat.length > 0) {
				System.out.println("* "+livresDansCat.length +" LIVRE(S) DANS CETTE CATEGORIE *\n");
				for(int i=0; i<livresDansCat.length ; i++) {
					showBookInfo(i,livresDansCat[i]);
					String action = optionSousMenuChoisi();
					if(action.equals("S")) {
						// Suivant
						if( i ==livresDansCat.length-1 ) {
							i--;
						}
						continue;
					}else if (action.equals("P")) {
						//precedent
						if( i ==0) {
							i=-1;
						}else {
							i = i-2;
						}
						
						continue;
					}else if(action.equals("A")){
						// ajouter panier
//						System.out.println("quantite en inventaire: "+livresDansCat[i].getQteEnInventaire() );
						if(livresDansCat[i].getQteEnInventaire() == 0) {
							System.out.println(" *** Ce livre est en rupture de stock.");
							i--;
							continue;
						}else {

							String msgSol="\nQuantite (entre 0 et "  + livresDansCat[i].getQteEnInventaire() +" ) :";
							qte = TP3Utils.validerEntier(msgSol, "Erreur, quatite invalide", 0, livresDansCat[i].getQteEnInventaire());
							if (qte == 0) {
								System.out.println("Aucun article a ete ajoute au panier");
								i--;
								continue;
							}else {
									// update QteEnInvtaire
									livresDansCat[i].diminuerQteInventaire(qte);
//									System.out.println("nouvelle inventaire quantite: "+livresDansCat[i].getQteEnInventaire() );
									// faire une copie du livre et modifier QteAchetee and la copie
									Livre copie = livresDansCat[i].copier();
									copie.setQteAchetee(qte);
									// ajouter au panier
									nouveauPanier = ajouterLivrePanier(nouveauPanier,copie);
									System.out.println("*** "+ qte+" livre (s) ajoute (s) au panier\n");
									i--;
								
							}
						}
				
					}else if(action.equals("T")) {
						return nouveauPanier;
					}
				}
			}else {
				// no book in the category
				System.out.println("*** cette section ne contient pas de livre.");
			}
			
		
		return nouveauPanier;
		
	}
	
	private static Livre[] construireListDeLivreDansCategorie(Livre [] livreInvantaire,int codeCat) {
		
		String nomCat = Livre.getNomCategorie(codeCat-1);
		int livreCounter = 0;
		
		for(int i=0 ; i <livreInvantaire.length ; i++ ) {

			if(livreInvantaire[i].estClasseDansCategorie(nomCat)) {
				livreCounter++;	
			}
		}
		
		Livre[] specificLivre = new Livre[livreCounter];
		for(int  i=0,j=0; i < livreInvantaire.length; i++ ) {
			if(livreInvantaire[i].estClasseDansCategorie(nomCat)) {
				specificLivre[j]=livreInvantaire[i];
				j++;
			}
		}
		
		return specificLivre;
		
		
		
	}
	private static String optionSousMenuChoisi(){
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		while(!input.toLowerCase().equals("s")  && !input.toLowerCase().equals("p")  && !input.toLowerCase().equals("a") && !input.toLowerCase().equals("t")  ) {
			System.out.println("Erreur, choix invalide! Recommencez...\n");
			System.out.println("(S)uivant, (P)recedent, (A)jouter au panier ou (T)erminer :");
			 input = in.nextLine();
		}
		return input.toUpperCase();
	}

	private static void showBookInfo(int index, Livre livre) {
		
		System.out.println(index+1+"." +livre.toString()+"\r\n" + 
				"-----------------------------------------------\r\n" + 
				"(S)uivant, (P)recedent, (A)jouter au panier ou (T)erminer : \r\n"); 
				
	}

	/**
	 * cette method a pour but dajouter un livre au bout du array panier.
	 * Pour ce faire elle cree une nouvelle array avec une case de plus que le precedent panier
	 * la methode copie tous les livres dans le panier dans le nouveau panier
	 * 
	 * @param predentPanier Ceci est le panier qui contient les livres
	 * @param lv Ceci est le livre a ajouter au panier
	 * @return Livre[] Ceci represente le panier avec le nouveau livre ajoutee
	 */
	private static Livre[] ajouterLivrePanier(Livre [] predentPanier,Livre lv) {
		// TODO Auto-generated method stub
		Livre [] nouveauPanier = new Livre[predentPanier.length+1];
		
		for (int i=0;i<predentPanier.length;i++) {
			nouveauPanier[i] = predentPanier[i];
		}
		nouveauPanier[nouveauPanier.length-1]=lv;
		return nouveauPanier;
	}
	



	// get qte from  user
	/**
	 * This method retrieves the number of books the user wants to buy
	 * the input has to be between 0 and limit. the method validate the input and
	 * keep asking for user Input unless the user input is valid (between 0 and limit)
	 * 
	 * @param limit ceci est le upper limit du nombre de livre que le user px entrer 
	 * @return int Ceci respresente le nombre de livre que le user veux acheter
	 */
	private static int getQuantite(int limit) {
		// TODO Auto-generated method stub
		int input=0;
		Scanner integerIn = new Scanner(System.in);
		try {
			
			 input = integerIn.nextInt();
		}
		catch (Exception e){
			System.out.println("enter a number");
		}
	
		while (!(input > -1 && input <limit+1) ) {
			System.out.println("Erreur, quatite invalide\n");
			System.out.println("\nQuantite (entre 0 et "  + limit +" ) :");
			try {
				input = integerIn.nextInt();
			}
			catch (Exception e) {
				System.out.println("enter a number");
			}
		}
		return input;
	}
	
}
