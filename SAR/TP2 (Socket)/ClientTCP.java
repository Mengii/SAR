import java.net.*;
import java.io.*;

public class ClientTCP {
	public static void main (String[] args){
		int port=4020;
		InetAddress hote=null; //@IP
		Socket sc=null;

		BufferedReader in;
		PrintWriter out;

		try{
			if(args.length>=2){
				hote=InetAddress.getByName(args[0]);
				port=Integer.parseInt(args[1]);
			}
			else{
				hote=InetAddress.getLocalHost();
			}
		}
		catch(UnknownHostException e){
			System.err.println("Machine inconnu : "+e);
		}

		try{
			sc=new Socket(hote, port);
			sc.setSoTimeout(10000);
			//input via clavier
			in=new BufferedReader(new InputStreamReader(System.in));
			//output au serveur
			out=new PrintWriter(sc.getOutputStream());
			//input du serveur
			BufferedReader buf=new BufferedReader(new InputStreamReader(sc.getInputStream()));
			
			boolean flag=true;
			while(flag){
				System.out.println("Entrer msg: ");
				String s=in.readLine();
				out.println(s);
				if("bye".equals(s)){
					flag=false;
				}
				else{
					try{
						String echo=buf.readLine();
						System.out.println(echo);
					} catch(SocketTimeoutException e){
						System.out.println("Time out, no response");
					}
				}
			}
			in.close();		
		}
		catch(IOException e){
			System.err.println("Impossible de creer la socket du client : "+e);
		}
		finally{
			try{
				sc.close();
			}
			catch(IOException e){}
		}
	}
}
