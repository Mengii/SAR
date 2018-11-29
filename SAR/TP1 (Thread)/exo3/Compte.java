public class Compte{
	private int solde=0;

	public void ajouter(int somme){
		solde=solde+somme;
		System.out.println("ajout de "+somme);
	}

	public void retirer (int somme){
		solde=solde-somme;
		System.out.println("retrait de "+somme);
	}
    
    public synchronized void operationNulle (int somme){
		solde=solde+somme;
		System.out.println("ajout de "+somme+",");
		solde=solde-somme;
		System.out.println("et retrait de "+somme+".");
		System.out.println();
	}
	
	public synchronized int getSolde(){
		return solde;
	}
}
