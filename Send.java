package Finally02;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/*
 * 用于数据的发送
 */
public class Send implements Runnable {

	private BufferedReader console;
	private DataOutputStream dos;
	private boolean isRunning=true;
	private String name;
	public Send()
	{
		console=new BufferedReader(new InputStreamReader(System.in)) ;
	}		
			
	public Send(Socket client,String name)
	{
		this();
		try {
			dos=new DataOutputStream(client.getOutputStream());
			this.name=name;
			send(this.name);
		} catch (IOException e) {
			//e.printStackTrace();
		    isRunning =false;
		    CloseUtil.closeAll(dos,console);
		}
	}	
	
	
	private String getMsgFromConsole()
	{
		try {
			return console.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return null;
	}
	/*
	 * 首先从控制台获取数据，然后在发送
	 */
	public void send(String msg)
	{
		//String msg=getMsgFromConsole();
		try {
			if(null!=msg&&!msg.equals(""))
			{
				dos.writeUTF(msg);
				dos.flush();//强制刷新
			}
		} catch (IOException e) {
			//e.printStackTrace();
			 isRunning =false;
			    CloseUtil.closeAll(dos,console);
		}
	}
	
	
	
	
	@Override
	public void run() {
		while(isRunning){
			send(getMsgFromConsole());}
	}

}
