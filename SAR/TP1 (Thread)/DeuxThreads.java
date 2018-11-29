public class DeuxThreads extends Thread {
	public void run(){
		setName("fils");
		for(int i=0; i<10; i++){
			System.out.println(getName());
			//yield();
		}
	}
	public static void main (String[] args) throws InterruptedException {
  		//commutation père-fils
		DeuxThreads th = new DeuxThreads(); //th est fils de thread main
    	th.start();
		currentThread().setName("pere");
		for(int i=0; i<10; i++) {
			System.out.println(currentThread().getName());
			System.out.flush(); //s'allume
			//yield();
			Thread.sleep(1); //forcer à passer au fils
		}
	}
}
