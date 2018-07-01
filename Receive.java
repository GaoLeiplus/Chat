package Finally02;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/*
 * 用于数据的接收
 */
public class Receive implements Runnable{

	 	private DataInputStream dis;
	 	private boolean isRunning=true;
	 	public Receive()
	 	{
	 		
	 	}
	 	public Receive(Socket client)
	 	{
	 		try {
				dis=new DataInputStream(client.getInputStream());
			} catch (IOException e) {
				//e.printStackTrace();
				isRunning=false;
				CloseUtil.closeAll(dis);
			}
	 	}
	public String  receive()
	{   String msg="";
	
		try {
			msg=dis.readUTF();
		} catch (IOException e) {
			//e.printStackTrace();
			isRunning=false;
			CloseUtil.closeAll(dis);
		}
		return msg;
		
	}
	 	
	
	@Override
	public void run() {
		
		while(isRunning){
			System.out.println(receive());
		}
	}


}
