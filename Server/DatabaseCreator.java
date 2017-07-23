
import java.sql.*;

public class DatabaseCreator {

	int r1sum,r2sum;
	int sorted1=5,sorted2=5;    //number of values to be added in the clean database
	String artifact;
	ResultSet rs1,rs2;
	Statement fetchvalues,st;
	Connection con;
	int [] r1final= new int[20];
	int [] r2final= new int[20];
	
	public void startCleaner(String artifact)
	{
		System.out.println("Started CLeaner");
		this.artifact=artifact;
		fetchValues();
		updateDatabase();
	}
	
	public void fetchValues()
{
		System.out.println("Fetching values");
		int i=0,j=0;
		int [] r1value = new int[100];
		int [] r2value= new int [100];
		String query1="Select * from "+artifact+"r1";
		String query2="Select * from "+artifact+"r2";

	try {
		Class.forName("com.mysql.jdbc.Driver");	
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ARTIFACTS","root","root123");
		fetchvalues= con.createStatement();
		rs1=fetchvalues.executeQuery(query1);
		
		while(rs1.next())
		{
			r1value[i]=rs1.getInt(1); 
			i++;
		}
		rs2=fetchvalues.executeQuery(query2);
		while(rs2.next())
		{
			r2value[j]=rs2.getInt(1);
			j++;
		}
         if(fetchvalues != null) fetchvalues.close();

	}
	catch (ClassNotFoundException e) {
		e.printStackTrace();
	} 
	catch (SQLException e) {
		e.printStackTrace();
	}
	for(i=0;i<20;i++)
	{
		r1sum+=r1value[i];
		r2sum+=r2value[i];
	}
	
	if(r1sum<r2sum)		
		cleanDb1(r1value,r2value);
	else
		cleanDb2(r1value,r2value);
	
	r1sum=0;
	r2sum=0;
}

	public void cleanDb1(int[] rs1input, int [] rs2input)
	{//r1 is smaller
		//System.out.println("cleaning db1");
		for (int i=0;i<20;i++)
		{
			for(int j=i+1;j<20;j++)
			{
				if(rs1input[i]>rs1input[j])
				{
					int temp=rs1input[j];
					rs1input[j]=rs1input[i];
					rs1input[i]=temp;
				}
				if(rs2input[i]<rs2input[j])
				{
					int temp=rs2input[j];
					rs2input[j]=rs2input[i];
					rs2input[i]=temp;
				}
			}
		}
				//System.out .println(" ");
		for(int i=0;i<20;i++)
		{
			System.out.print(" "+rs1input[i]);
		}
		System.out .println(" ");
		for(int i=0;i<20;i++)
		{
			System.out.print(" "+rs2input[i]);
		}
		for(int i=0;i<sorted1;i++)
		{			
			if(rs1input[i]!=0)
			{
			r1final[i]=rs1input[i];
			}
			else 
				sorted1++;
		}	
		for(int i=0;i<5;i++)
		{
			if(rs2input[i]!=0)
			{
				r2final[i]=rs2input[i];
			}
			else
				sorted2++;
		}
	}
	
	public void cleanDb2(int[] rs1input, int [] rs2input)
	{//r1 is greater
		System.out.println("Cleaning database");
		for (int i=0;i<20;i++)
		{
			for(int j=i+1;j<20;j++)
			{
				if(rs1input[i]<rs1input[j])
				{
					int temp=rs1input[j];
					rs1input[j]=rs1input[i];
					rs1input[i]=temp;
				}
				if(rs2input[i]>rs2input[j])
				{
					int temp=rs2input[j];
					rs2input[j]=rs2input[i];
					rs2input[i]=temp;
				}
			}
		}
		for(int i=0;i<20;i++)
		{
			System.out.print(" "+rs1input[i]);
		}
		System.out .println(" ");
		for(int i=0;i<20;i++)
		{
			System.out.print(" "+rs2input[i]);
		}

		for(int i=0;i<sorted1;i++)
		{			
			if(rs1input[i]==0)
			{
				sorted1++;
			}
			else 
				r1final[i]=rs1input[i];
		}	
		for(int i=0;i<5;i++)
		{
			if(rs2input[i]!=0)
			{
				r2final[i]=rs2input[i];
			}
			else
				sorted2++;
		}

	}
	
	public void updateDatabase()
	{
		System.out.println("Updating Database");
		try {
			st=con.createStatement();
			for(int i=0;i<5;i++)
			{
				String query="Insert into "+artifact+"(R1,R2) values ("+r1final[i]+","+r2final[i]+")";
				System.out.println(query);
				st.executeUpdate(query);
			}
			st.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
