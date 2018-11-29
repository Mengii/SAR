import java.net.*;
import java.io.*;

public class ThreadClient implements Runnable {

	private Socket client=null;

	public ThreadClient(Socket client){
		this.client=client;
	}

	@Override
	public void run(){
		try{
			PrintWriter out=new PrintWriter(client.getOutputStream());
			BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));

			boolean flag=true;
			while(flag){
				String s=in.readLine();
				if(s==null||"".equals(s)){
					flag=false;
				} else{
					if("bye".equals(s)){flag=false;}
					else{
						out.println("echo: "+s);
					}
				}
			}
			out.close();
			client.close();
		}catch(Exception e){e.printStackTrace();}
	}
}