package Finally02;
/*
 * 2018.06.23
 * 完成简易的控制台的私聊和群聊
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

	private List<MyChannel> all=new ArrayList<MyChannel>();
    public static void main(String[] args) throws IOException {
	  new Server().start();
	
     }
public void start() throws IOException{
	ServerSocket server=new ServerSocket(9999);
	  while(true){
		  Socket client=server.accept();
		  MyChannel channel=new MyChannel(client);
		  all.add(channel);
		  new Thread(channel).start();
	  }
	
}
  private class MyChannel implements Runnable{

	 private DataInputStream dis;
	  private  DataOutputStream dos;
	  private boolean isRunning =true;
	  private String name;
	   public MyChannel(Socket client) {
		  try {
			dis=new DataInputStream(client.getInputStream());	
			  dos=new DataOutputStream(client.getOutputStream());
			  this.name=dis.readUTF();
			  this.send("欢迎进入聊天室");
			  sendOthers(this.name+"进入了聊天室",true);
			  System.out.println(this.name+"进入了聊天室");
		} catch (IOException e) {
			//e.printStackTrace();
			CloseUtil.closeAll(dis,dos);
			isRunning=false;
		}
	  }
	  //服务器端 输入流读取数据
	   public String receive()
	   {
		   String msg="";
		   try {
			msg=dis.readUTF();
		} catch (IOException e) {
			//e.printStackTrace();
			CloseUtil.closeAll(dis);
			isRunning=false;
			all.remove(this);//移除自身
		}
		   return msg;
	   }
	  
	   
	   public void send(String msg)
	   {
		   if(null==msg||msg.equals(""))
			   return ;
		   try {
			dos.writeUTF(msg);
			   dos.flush();
		} catch (IOException e) {
			//e.printStackTrace();
			CloseUtil.closeAll(dos);
			isRunning=false;
			all.remove(this);//移除自身
		}
		   
	   }
	   
	   
	   
	   /*
	      *发送给其他客户端
	    */
	   
	   public void sendOthers(String msg,boolean sys){
		 
		   if(msg.startsWith("@")&&msg.indexOf(":")>-1){
			   String name=msg.substring(1,msg.indexOf(":"));
			   String content=msg.substring(msg.indexOf(":")+1);
			   for(MyChannel other:all){
				   if(other.name.equals(name)){
					   other.send(this.name+"对你私聊说 ："+content);
				   }
			   }
			    
		   }else{
			   for(MyChannel other:all){
				   if(other==this)
				   {
					   continue;
				   }
				   //发送给其他的客户端
				   if(sys){
					   other.send("系统信息"+msg);
				   }
				   else{other.send(this.name+"对大家说"+msg);}
				   
			   }
		   }
		   
		   
	   }
		@Override
		public void run() {
			while(isRunning){
				
				sendOthers(receive(),false);
			}
		}
		  
		  
	}

  
  
  
  
  
  
  
}



