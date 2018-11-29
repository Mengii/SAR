import java.net.*;

public class ServeurMultiThread {

	public static void main (String[] args) throws Exception {

		ServerSocket server=new ServerSocket(4020); //socket d'ecoute
		Socket client=null;

		while(true){
			client=server.accept();
			System.out.println("connected");
			new Thread(new ThreadClient(client)).start();
		}
		server.close();
	}
}