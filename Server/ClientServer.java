import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


import java.sql.*;

public class ClientServer implements Runnable {

		// TODO Auto-generated method stub
		private Socket socket = null;
		private ServerSocket server = null;
		private Thread       thread = null;

		private DataInputStream streamIn  =  null;
		private DataOutputStream streamOut = null;
		public int R1_value,R2_value=0,blue_value;
		double ll,lt;
		public String Artifact_Index,Artifactid="A",userid="U";
		int port,trackCount=0,trackUser=0;
		boolean done ;
		String [] databaseid=new String[20];
		
		int databaseCounter=1;
		String ans,positioninfo;
		DatabaseCreator db= new DatabaseCreator();

		public void ChatServer(){
			databaseid[0]="A";
			databaseid[1]="B";
			databaseid[2]="C";

		    port = 6669;
		    try{
		        
		        server = new ServerSocket(port);
		        start();
		    }
		    catch(IOException ioe)
		    {
		        System.out.println(ioe);
		    }
		}
		public void start()
		{
		    if (thread == null)
		    {
		        thread = new Thread(this);
		        thread.start();
		    }
		}

		public void run()
		{
			
		    while (thread != null)
		    {
		        try{
		            System.out.println("waiting for Client ...");
		            socket = server.accept();
		           System.out.println("Client accepted: " + socket + " IP: "+ socket.getInetAddress());
		            open();
		            done = false;

		            while (!done){
		                try{
		                	
		                	
		                    String line = streamIn.readUTF();
		                    System.out.println("Input recieved");
		                    System.out.println(line);
		                    String id=line.substring(0,1);
		                    //ll=Integer.parseInt(line.substring(line.indexOf("?")+1,line.indexOf(",")));
		                    //lt=Integer.parseInt(line.substring(line.indexOf(",")+1),line.indexOf("+"));

		                    if(id.equals("R"))
		                    {
		                    	trackCount++;
		                    	//System.out.println(trackCount);
		                    	String artifactid=line.substring(line.indexOf("/")+1,line.indexOf(":"));
		                    	String index=line.substring(line.indexOf("R")+1,line.indexOf("/"));
		                    	int rssi=Integer.parseInt(line.substring(line.indexOf(":")+1));
		                    	if(index.equals("1"))
		                    	{
		                    		database_new("R1",artifactid,rssi);
		                    	}
		                    	if(index.equals("2"))
		                    	{
		                    		database_new("R2",artifactid,rssi);
		                    	}
		                    }
		                    else
		                    {
		                    R1_value=Integer.parseInt(line.substring(line.indexOf("k")+1,line.indexOf(":")));
		                    R2_value=Integer.parseInt(line.substring(line.indexOf("a")+1,line.indexOf("|")));
		                    blue_value=Integer.parseInt(line.substring(line.indexOf("|")+1,line.indexOf("?")));

		                    	if(id.equals(userid))
		                    	{
		                    		//trackUser++;
		                    		//if(trackUser>=5)
		                    			//userControl();
		                    		User pos= new User(R1_value,R2_value,blue_value);
		                    		ans=pos.positining();
		                    		if(ans.equalsIgnoreCase("A"))
		                    		{
		                    			positioninfo="You are standing at Burj Al Arab";
		                    		}
		                    		if(ans.equalsIgnoreCase("B"))
		                    		{
		                    			positioninfo="You are standing at Eiffel Tower";
		                    		}
		                    		if(ans.equalsIgnoreCase("C"))
		                    		{
		                    			positioninfo="You are standing at Leaning Tower of Pisa";
		                    		}
		                			send(positioninfo);
		                    	}   
		                    done = line.equals(".bye");
		                    }
		                }
		                catch(IOException ioe)
		                {
		                    done = true;
		                    ioe.printStackTrace();
		                    System.out.println("Acceptance Error: " + ioe);
		                }
		            }
		            
		        }
		        catch(IOException ie){
		            System.out.println("Acceptance Error: " + ie);
		        }
		    }
		}
		public void send(String msg)
		{
		    try
		    {
		        if(msg != null) {
		            streamOut.writeUTF(msg);
		            streamOut.flush();
		        }
		    }
		    catch(IOException ioe)
		    {
		        ioe.printStackTrace();
		    }
		}
		public void open() throws IOException
		{
		    streamIn  = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		    streamOut = new DataOutputStream(socket.getOutputStream());
		}
		public void close() throws IOException{
		    if (socket != null)    socket.close();
		    if (streamIn != null)  streamIn.close();
		    if (streamOut != null)  streamOut.close();

		}
		@SuppressWarnings("deprecation")
		public void stop(){
		    if (thread != null){
		        thread.stop();
		        thread = null;
		    }
		}
				
		public static void main(String args[])
		{
		    ClientServer s=new ClientServer();
		    s.ChatServer();    
		}
		
		/*public void userControl()
		{
			String ans;
			User pos= new User();
			trackUser=0;
			ans=pos.positining();
			send(ans);
		}*/
		public void database_new(String R,String artifact,int value)
		{
			
			Connection con;
			Statement st;
			String update=""; 
			String newDB="";
			String newr1=" ";
			String newr2=" ";
			try
			{
				
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ARTIFACTS","root","root123");
				
					st= con.createStatement();	
					{
					 update="INSERT INTO "+artifact+R+" (RSSI_Values) Values ("+value+");";
					 st.executeUpdate(update);		
					 if(con != null) con.close();
		              if(st != null) st.close();	
					}
			}
			
				catch (ClassNotFoundException e) 
			    {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    }
				 catch (SQLException e) 
			    {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			   }
			if(trackCount==40)
			{
			db.startCleaner(artifact);
			trackCount=0;
			}
		}

}

