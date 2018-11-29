import java.util.ArrayList; 
import java.util.Random;


public class Compteur extends Thread {
	
	private String nom;
	Random rand = new Random();
	private static ArrayList<Compteur> list;

	public Compteur(String nom){
		this.nom=nom;	
		list = new ArrayList<Compteur>();	
	}

	public void run(){
			
			for(int nb=1;nb<=10;nb++){

				System.out.println("je suis le fils " +nom +" et je compte : " +nb);

				try{
					sleep(rand.nextInt(5000));
				}catch (InterruptedException e){System.out.println(e);}
				
			}

			System.out.println(nom+" a fini de compter jusqu'a 10");
	}

	public static void main (String[] args) throws InterruptedException {
	
		int n = Integer.parseInt(args[0]);
		System.out.println("n = "+n);
		
		for(int i=1;i<=n;i++){		
			Compteur cmp = new Compteur("Thread "+i);
			list.add(cmp);
			cmp.start();			
		}
		
		for(Compteur cmp:list){
			cmp.join();
		}

		System.out.println("fin du main");
	}
}
