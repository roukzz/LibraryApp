package App;

import java.text.DecimalFormat;
import java.util.Scanner;

public class Panier {
	private static final DecimalFormat df = new DecimalFormat("0.00"); 
	
	public static Livre[] optionPanier(Livre[] panier,Livre[] inventaireLivres) {
		// TODO Auto-generated method stub
		Livre [] nouveauPanier = panier;
		
		while(true) {
			afficherPanier(nouveauPanier);
			if (nouveauPanier.length == 0) {
				Scanner in = new Scanner(System.in);
				String input = in.nextLine();
				return nouveauPanier;
			}
			String optionChoisi = validerOptionPanier();
			
			if(optionChoisi.equals("R")) {
				
				//retirer item
				//get livre index from panier
				
				int articleIndex = Utils.validerEntier("Entrez le numero de l'item a retirer (0 pour annuler):", "Erreur, item invalide ! Recommencez...", 0, nouveauPanier.length);
				if (articleIndex !=0) {
					nouveauPanier = retirerLivrePanier(nouveauPanier,articleIndex,inventaireLivres);
				}
				
			}else if (optionChoisi.equals("P")) {
				// passer la commande
				System.out.println("************************************************************\r\n" + 
						"* Merci d'avoir magasine chez A livre ouvert !             *\r\n" + 
						"* Votre commande sera expediee dans les plus brefs delais. *\r\n" + 
						"************************************************************");
				nouveauPanier = new Livre[0];
				System.out.println("Appuyez sur <ENTREE> pour revenir au menu principal...");
				Scanner in = new Scanner(System.in);
				String input = in.nextLine();
				return nouveauPanier;
				
			}else if (optionChoisi.equals("T")) {
				return nouveauPanier;
				
			}
			
		}
		
	}
	
	private static void afficherPanier(Livre[] panier) {
		// TODO Auto-generated method stub
		if(panier.length == 0) {
			System.out.println("*** Votre panier est vide\n");
			System.out.println("Appuyez sur <ENTREE> pour revenir au menu principal...");
			return;
		}
		double sousTotal = 0;
		int amountOfSpace="                                                                 ".length();
		String space="";
		System.out.println("-----------------\r\n" + 
				"CONTENU DU PANIER\r\n" + 
				"-----------------\r\n" + 
				"\r\n" + 
				"ITEM                                                             |   QTE |    PRIX\r\n" + 
				"------------------------------------------------------------------------------------");
		for (int i=0;i<panier.length;i++) {
			int spacelength=amountOfSpace-panier[i].toString().length();
			space = constructSpace(spacelength);
			System.out.println(i+1+"."+panier[i].toString()+ space+                      "|     "+panier[i].getQteAchetee()+" |   "+df.format(panier[i].getPrix()*panier[i].getQteAchetee()) +"$");
			
			sousTotal += panier[i].getQteAchetee()*panier[i].getPrix();
		}
		System.out.println("------------------------------------------------------------------------------------");
		System.out.println("Sous-total                                                                  "+df.format(sousTotal)+" $");
		double TPS = sousTotal*0.05;
		System.out.println("Sous-total                                                                   "+df.format(TPS)+" $");
		double TVQ = sousTotal*0.0975;
		System.out.println("Sous-total                                                                   "+df.format(TVQ)+" $");
		System.out.println("Livraison                                                                    10,00 $");
		System.out.println("====================================================================================");
		double total = sousTotal + TPS + TVQ  + 10.00;
		System.out.println("Sous-total                                                                   "+df.format(total)+" $");
		System.out.println("(R)etirer item, (P)asser la commande, ou (T)erminer :");
	}
	
	
	private static String validerOptionPanier() {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		while(!input.toLowerCase().equals("r")  && !input.toLowerCase().equals("p")  && !input.toLowerCase().equals("t")) {
			System.out.println("Erreur, choix invalide! Recommencez...\n");
			System.out.println("(R)etirer item, (P)asser la commande, ou (T)erminer :");
			 input = in.nextLine();
		}
		return input.toUpperCase();
		
	}
	
	private static Livre[] retirerLivrePanier(Livre [] precedentPanier,int livreIndex,Livre[] inventaireLivre) {
		// TODO Auto-generated method stub
		Livre [] nouveauPanier = new Livre[precedentPanier.length-1];
		Livre lv = precedentPanier[livreIndex-1];
		
		for (int i=0,j=0;i<precedentPanier.length;i++) {
			if(precedentPanier[i] != lv) {
				nouveauPanier[j] = precedentPanier[i];
				j++;
			}else {
				// found the book to remove
				// update quantity in inventaire
				for (int z=0;z<inventaireLivre.length;z++) {
					if(precedentPanier[i].getTitre().equals(inventaireLivre[z].getTitre())) {
						inventaireLivre[z].augmenterQteInventaire(precedentPanier[i].getQteAchetee());
					}
				}
			}
			
		}
		
		for(int i = 0 ; i<nouveauPanier.length;i++) {
			System.out.println(nouveauPanier[i].getTitre());
		}
		return nouveauPanier;
	}

	private static String constructSpace(int spacelength) {
		// TODO Auto-generated method stub
		String space="";
		for (int i=0 ; i < spacelength ; i++) {
			space +=" ";
		}
		return space;
	}


}
