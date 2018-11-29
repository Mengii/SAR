//exo4.1 version avec join()
public class TriParallele extends Thread {

	private int[] t;
	private int debut, fin; // tranche de ce tableau qu'il faut trier
	
	private TriParallele(int[] t){
		this (t, 0, t.length-1);
	}

	private TriParallele(int[] t, int debut, int fin){
		this.t=t;
		this.debut=debut;
		this.fin=fin;
	}

	public void run(){
		if((fin-debut)<2){ //2 éléments seulement
			if(t[debut]>t[fin]){
				permuter(debut,fin);
			}
		}
		else {
			int milieu=debut+(fin-debut)/2;
			TriParallele tri1 = new TriParallele(t, debut, milieu);
			tri1.start();
			TriParallele tri2 = new TriParallele(t, milieu+1, fin);
			tri2.start();
			try{
				tri1.join();
				tri2.join();
			} catch(InterruptedException e){}
			trifusion(debut,fin); 
		}
	}

	private void permuter (int a, int b){
		int temp=t[a];
		t[a]=t[b];
		t[b]=temp;
	}
	
	/*
	private void tirer (int debut, int fin){
		if((fin-debut)<2){ //2 éléments seulement
			if(t[debut]>t[fin]){
				permuter(debut,fin);
			}
		}
		else {
			int milieu=debut+(fin-debut)/2;
			tirer(debut,milieu);
			tirer(milieu+1,fin);
			trifusion(debut,fin); 
		}
	}
	*/

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
		
		TriParallele tri = new TriParallele(tableau);
		//t.tirer(0, tableau.length-1);
		tri.start();
		try{
			tri.join();
		} catch(InterruptedException e){}
		
		for(int i=0; i<tableau.length; i++){
			System.out.print(tableau[i]+" ; ");
		}
		System.out.println();
	}
}
