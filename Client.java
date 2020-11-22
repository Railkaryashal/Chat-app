import java.net.*;
import java.io.*;
public class Client {

	Socket socket;

	BufferedReader br;
	PrintWriter out;

	public Client(){
		try{
			System.out.println("Sending Server Request");
			socket=new Socket("192.168.1.10",7777);
			System.out.println("Connection Done");
			
			
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			out=new PrintWriter(socket.getOutputStream());
			
			startReading();
			startWriting();

		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void startReading() 
	{	//thread-reading
		
		Runnable r1=()->
		{	
			System.out.println("Reader starting");
			
			try 
				{
			      while(true)
			     	{
				
						String msg=br.readLine();
						if(msg.equals("Exit"))
						{
							System.out.println("Server terminated the chat");
						
							socket.close();

							break;
						}
						System.out.println("Server : "+ msg);
					}
				}catch(Exception e) 
					{
						//e.printStackTrace();
					System.out.println("Connection is closed");
					}
		};
		new Thread(r1).start();
	}
	public void startWriting()
	{
		//thread-data take from server to client
		Runnable r2=()->
		{
    		System.out.println("Writer started");
			
			try {
					while(!socket.isClosed()) 
					{
    					BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
    					String content=br1.readLine();	
						out.println(content);
						out.flush();

						if(content.equals("Exit"))
						{
							socket.close();
							break;
						}
					}
				}catch(Exception e) {
				//e.printStackTrace();
				System.out.println("Connection is closed");
			}
    	};
    	new Thread(r2).start();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("This is client");
		new Client();

	}
}
