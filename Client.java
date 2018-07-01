package Finally02;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
 
		System.out.println("«Î ‰»Î–’√˚");
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String name=br.readLine();
		if(name==null){
			return;
		}
		Socket client=new Socket("localhost",9999);
		new Thread(new Send(client,name)).start();
		new Thread(new Receive(client)).start();
		
	}
	
}
