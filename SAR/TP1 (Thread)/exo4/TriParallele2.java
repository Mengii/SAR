//exo4.2 version avec wait() et notifyAll()
public class TriParallele2 extends Thread {

	private int[] t;
	private int debut, fin; // tranche de ce tableau qu'il faut trier
	private TriParallele2 parent; //thread TriParallele2 qui a lancé ce TriParallele2
	private int nbNotify = 0; //nombre de notifys envoyés à ce TriParallele2
	
	private TriParallele2(int[] t){
		this (null, t, 0, t.length-1);
	}

	private TriParallele2(TriParallele2 parent, int[] t, int debut, int fin){
		this.parent=parent;
		this.t=t;
		this.debut=debut;
		this.fin=fin;
		start();
	}

	public synchronized void notifier(){
		// Notifie tous les thread en attente sur "ce" (this) moniteur
    	// Attention, quand le message sera envoyé au parent (dans run()),
    	// on incrémentera la variable nbNotify du parent (qui sera le this
    	// implicite) et on notifiera le parent.
    	// On peut aussi ne pas utiliser de méthode mais dans ce cas, il faut
    	// écrire parent.nbNotify++; parent.notifyAll(); dans un bloc 
    	// synchronisé sur parent placé dans la méthode run (à la place de
    	// "parent.notifier()")
		this.nbNotify++;
		//objet.notifyAll()
		//necessite que le thread en cours possede le moniteur de objet
		//debloque tous les threads qui s'etaient bloques sur l'objet avec
		//objet.wait()
		//!= notify() qui debloque 1 seul thread
		this.notifyAll();
	}

	public void run(){
		if((fin-debut)<2){ //2 éléments seulement
			if(t[debut]>t[fin]){
				permuter(debut,fin);
			}
		}
		else {
			int milieu=debut+(fin-debut)/2;
			TriParallele2 tri1 = new TriParallele2(this, t, debut, milieu);
			TriParallele2 tri2 = new TriParallele2(this, t, milieu+1, fin);
			//attend les 2 threads
			synchronized(this){
				try{
					// Tant que 2 notifications n'ont pas été reçues, on attend
					while(nbNotify<2){
						//objet.wait() bloque le thread qui l'appelle, jusqu'a
						//ce qu'un autre thread appelle le methode
						//objet.notify() ou objet.notifyAll()
						wait();
					}
				} catch(InterruptedException e){}				
			}
			trifusion(debut,fin); 
		}
		if(parent!=null){
			parent.notifier(); // indique qu'il a fini au parent qui l'attend
		}
	}

	private void permuter (int a, int b){
		int temp=t[a];
		t[a]=t[b];
		t[b]=temp;
	}
	
	private void trifusion (int debut, int fin){
		int[] tfusion = new int[fin-debut+1];
		int milieu = (debut+fin)/2;
		int i1=debut;
		int i2=milieu+1;
		int ifusion=0;
		while(i1<=milieu&&i2<=fin){
			if(t[i1]<t[i2]){
				tfusion[ifusion++]=t[i1++];
			}
			else {
				tfusion[ifusion++]=t[i2++];
			}
		}
		if(i1>milieu) {
			for(int i=i2; i<=fin; i++){
				tfusion[ifusion++]=t[i];
			}
		}
		else{
			for(int i=i1; i<=milieu; i++){
				tfusion[ifusion++]=t[i];
			}
		}
		for(int i=0,j=debut; i<=fin-debut; i++,j++){
			t[j]=tfusion[i];
		}
	}
	
	public static void main(String[] args){
		int[] tableau={5,8,3,2,7,10,1,12,4};
		
		TriParallele2 tri = new TriParallele2(tableau);	
		try{
			tri.join();
		} catch(InterruptedException e){}
		
		
		for(int i=0; i<tableau.length; i++){
			System.out.print(tableau[i]+" ; ");
		}
		System.out.println();
	}
}
