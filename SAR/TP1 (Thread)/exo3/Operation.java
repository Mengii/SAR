public class Operation extends Thread {
	//protéger et gérer la concurrence
	private Compte compte;

	public Operation(String nom, Compte compte){
		super(nom);
		this.compte=compte;
	}
	
	public void run(){
		while(true){
			int i = (int) (Math.random()*10);
			String nom = this.getName();
			System.out.println(nom);
			//this.compte.operationNulle(i);

			//exo3.3
			synchronized (compte) { //jusqu'à if 
				this.compte.ajouter(i);
				this.compte.retirer(i);
			}
			
			int montant = this.compte.getSolde();
			if(montant != 0){
				System.out.println(nom+" solde = "+montant);
				System.exit(1); //s'arrête qd solde != 0			
			}
		}
	}
	
	public static void main(String[] args){
		Compte c = new Compte();
		for(int i=1;i<=2;i++){
			Operation op = new Operation("op"+i, c);
			op.start();
		}
	}
}
