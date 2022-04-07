package App;

public class Livre {

	public final static String CATEGORIE_1="Science fiction";
	public final static String CATEGORIE_2="Romance";
	public final static String CATEGORIE_3="Thriller";
	public final static String CATEGORIE_4="Policicer";
	public final static String CATEGORIE_5="Humour";
	public final static String CATEGORIE_6="Drame";
	
	public static final String[] NOMS_CATEGORIES = {CATEGORIE_1,CATEGORIE_2,CATEGORIE_3,CATEGORIE_4,CATEGORIE_5,CATEGORIE_6};
	
	private static int nbrProduitsInventaire0;
	private String titre;
	private String auteur;
	private double prix;
	private int [] codesCategories;
	private int qteEnInventaire;
	private int qteAchetee;
	
	
	public Livre( String titre,  String auteur, double prix, int qteEnInventaire) {
		
		
		this.titre=titre;
		this.auteur=auteur;
		this.prix=prix;
		this.qteEnInventaire=qteEnInventaire;
		this.qteAchetee=0;
		this.codesCategories= new int[0];
	}


	public void setQteAchetee(int qteAchetee2) {
		// TODO Auto-generated method stub
		if(qteAchetee2 < 0) {
			throw new IllegalArgumentException("illegal Argument: quantity in stock cannot be less than 0");
		}
		this.qteAchetee=qteAchetee2;
	}


	public void setPrix(double prix2) {
		// TODO Auto-generated method stub
		if (prix <= 0) {
			throw new IllegalArgumentException("illegal Argument: price should be greater or equal to zero");
			
		}
		this.prix=prix2;
	}


	public void setAuteur(String auteur2) {
		// TODO Auto-generated method stub
		if(auteur2.trim().length() < 1 || auteur2==null) {
			throw new IllegalArgumentException("illegal Argument: title should not be Empty or Null");
		}
		this.auteur=auteur2;
	}


	public void setTitre(String titre2){
		// TODO Auto-generated method stub
		if(titre2.trim().length() < 1 || titre2==null) {
			throw new IllegalArgumentException("illegal Argument: title should not be Empty or Null");
		}
		
		this.titre= titre2;
		
	}

	public String getTitre() {
		return titre;
	}
	public String getAuteur() {
		return auteur;
	}
	public double getPrix() {
		return prix;
	}
	public int  getQteAchetee() {
		return qteAchetee;
	}
	public int getQteEnInventaire() {
		if (qteEnInventaire == 0) {
		
		}
		return qteEnInventaire;
	}
	
	public void diminuerQteInventaire(int qteDeMoins) {
		// on suppose que le paramete sera toujours plus >= 0 -> aucune verif requise
		
		if (qteDeMoins >= qteEnInventaire) {
			this.qteEnInventaire = 0;
			nbrProduitsInventaire0++;
		}else {
			this.qteEnInventaire = qteEnInventaire - qteDeMoins;
		}
		
		
	}
	public void augmenterQteInventaire(int qteDePlus) {
		// suppose que le param est valide (>= 0)
		if(this.getQteEnInventaire() == 0 && qteDePlus>0) {
			nbrProduitsInventaire0--;
		}
		this.qteEnInventaire +=qteDePlus;
	}
	public boolean estClasseDansCategorie(String categorie) {
		if(categorie.trim().length() < 1 || categorie==null) {
			throw new IllegalArgumentException("illegal Argument: categorie doit pas etre vide ou Null");
		} else {
			for (int i=0 ; i < codesCategories.length ; i++) {
				// convertir le input en lower case et convertir le nom de la categorie en lower case
				if (NOMS_CATEGORIES[codesCategories[i]].toLowerCase().equals(categorie.toLowerCase())) {
					return true;
				}
			}
		}

		
		return false;
	}
	public boolean ajouterCategorie(int codeCat) {
		
		// if codeCat < 5 and >= 0 --> r
		if (codeCat < 6 && codeCat >-1) {
			// if codesCategries.length == 0 --> add one cell to codesCategories -->  add the codeCat to the array --> return true
			if (codesCategories.length == 0) {
				int [] newArr = {codeCat};
				this.codesCategories=newArr;
				return true;
			}else {
				// otherwise book already associated with at least one category ->
				// look if the codeCat is already in codesCategories -> return false
				for (int i=0 ; i < codesCategories.length; i++ ) {
					if(codesCategories[i] == codeCat) {
						return false;
					}
				}
				// otherwise (codeCat) is not in the array --> add cell to array --> add codeCat to that new cell --> return true
				int [] newArr = new int[codesCategories.length+1];
				System.arraycopy(codesCategories, 0, newArr, 0, codesCategories.length);
				newArr[codesCategories.length]=codeCat;
				this.codesCategories=newArr;
				return true;
			}
						
			
		} else {
			// otherwise(codeCat is invalid) -> return false 
//			System.out.println("le code a ajouter doit etre entre 0 et 5");
			return false;
		}
		
		
	}
	public boolean retirerCategorie(int codeCat) {
		
		// check if input is in bteween 0 annd 5 inclusive
		if (codeCat >=0 && codeCat <= 5) {
			// check if codesCategories nest pas  vide
			if(codesCategories.length > 0) {
				// check if codeCat  is in codesCategories
				for (int i=0; i< codesCategories.length; i++) {
					if (codesCategories[i] == codeCat) {
						// remove element from list and update list size--> return true
						this.codesCategories=retirerElementDeTableau(codesCategories,codeCat);
						return true;
						
					}
				}
				
				// if for loop finish, it means the codecat was not found we return false
				return false;
				
			}else {
				// otherwise return false
//				System.out.println("codes categories est vide");
				return false;
			}
				
				
		}else {
			// otherwise return false
//			System.out.println("un code valide est entre 0 et 5 inclusivement");
			return false;
		}
		
		
	}
	
	public Livre copier() {
		Livre copieLivre = new Livre(this.titre,this.auteur,this.prix,this.qteEnInventaire);
		copieLivre.qteAchetee=this.qteAchetee;
		copieLivre.codesCategories=this.codesCategories;
		return copieLivre;
		
	}
	private int[] retirerElementDeTableau (int [] bigArray,int codeCat) {
		int [] newArray= new int[bigArray.length -1];
		int newArrayIndex = 0;
		for (int i=0 ; i < bigArray.length ; i++) {
			if(bigArray[i] != codeCat) {
				newArray[newArrayIndex] = bigArray[i];
				newArrayIndex++;
			}
		}
		
		return newArray;
	}
	public String toString() {
		String livreInfo = this.titre+ " - " + this.auteur +" (" + Double.toString(this.prix) + ")";
		return livreInfo;
	}
	public static String  getNomCategorie(int codeCat) {
		if (codeCat >=0 && codeCat <=5) {
			return NOMS_CATEGORIES[codeCat];
		}
		return null;
	}
	public static int getNbrProduitsInventaire0() {
		return nbrProduitsInventaire0;
	}


}
