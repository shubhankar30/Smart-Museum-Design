import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.Math;

public class User {
	 
	 int R1_value,R2_value,count,blue_value;
	 String result;
	 int ar1Sum=0,ar2Sum=0,br1Sum=0,br2Sum=0,diffr1=0,diffr2=0,cr1Sum=0,cr2Sum=0;
	 double diffk;
	 Artifact A,B,C;
	 String ans;
	 boolean found=false;
	 int uavg1,uavg2;

	 
	 
	 User(int v1,int v2,int blue)
	 {
		 R1_value=v1;
		 R2_value=v2;
		 uavg1=v1;
		 uavg2=v2;
		 blue_value=blue*-1;
		 //store();	
		 
		 Fetch_Artifact();
		 //r1User=new int [30];
		 //r2User= new int [30];

	 }

	 
	/* public void store()
	 {
		 String query="Insert into User(R1,R2) values ("+R1_value+","+R2_value+");";
		 System.out.println("Store called");
		 try
		 {
		 Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ARTIFACTS","root","root123");
			Statement st= con.createStatement();
			st.executeUpdate(query);
			st.close();
			con.close();
		 }
		 catch(SQLException e)
		 {
			 e.printStackTrace();
		 }
		 catch(ClassNotFoundException e)
		 {
			 e.printStackTrace();
		 }
	 }  */
	/* public void Fetch_User()
	 {
		 int i=0;
		 String query="Select * from User;";
		 String delete="truncate User;";
		 try
		 {
		 Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ARTIFACTS","root","root123");
			Statement st= con.createStatement();
			ResultSet rs1;
			rs1=st.executeQuery(query);
			System.out.println("Query executed");
			while(rs1.next())
			{
				r1User[i]=rs1.getInt(1); 
				r2User[i]=rs1.getInt(2);
				i++;
			}
			st.executeUpdate(delete);
			st.close();
			con.close();
			i=0;
		 }
		 catch(SQLException e)
		 {
			 e.printStackTrace();
		 }
		 catch(ClassNotFoundException e)
		 {
			 e.printStackTrace();
		 }

		 for(int j=0;j<5;j++)
		 {
			 uavg1=uavg1+r1User[j];
			 uavg2=uavg2+r2User[j];
		 }
		 for(int z=0;z<5;z++)
		 {
			 System.out.println("r2"+r2User[z]);
		 }
System.out.println(uavg1);
System.out.println(uavg2);
		 uavg1=uavg1/5;
		 uavg2=uavg2/5;
	 }*/
	 public void Fetch_Artifact()
	 {
		 int j=0,m=0,z=0;
		 
		 String queryA="Select * from A;";
		 String queryB="Select * from B;";
		 String queryC="Select * from C;";
		 A=new Artifact("A");
		 B=new Artifact("B");
		// C=new Artifact ("C");
		 try
		 {
			 
			 Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ARTIFACTS","root","root123");
			Statement st= con.createStatement();
			ResultSet rsA,rsB,rsC;
			rsA=st.executeQuery(queryA);
			while(rsA.next())
			{
				A.R1[m]=rsA.getInt(1);
				A.R2[m]=rsA.getInt(2);
				ar1Sum+=A.R1[m];
				ar2Sum+=A.R2[m];
				m++;
			}
			rsB=st.executeQuery(queryB);
			while(rsB.next())
			{
				B.R1[j]=rsB.getInt(1);
				B.R2[j]=rsB.getInt(2);
				br1Sum+=B.R1[j];
				br2Sum+=B.R2[j];
				j++;
			}
			rsC=st.executeQuery(queryC);
			while(rsC.next())
			{
				C.R1[z]=rsC.getInt(1);
				C.R2[z]=rsC.getInt(2);
				cr1Sum+=C.R1[z];
				cr2Sum+=C.R2[z];
				z++;
			}
			st.close();
			con.close();
		 }
		 catch (SQLException e) 
		    {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
		 catch (ClassNotFoundException e) 
		    {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }	
		/* System.out.println("Ar1 Sum: "+ar1Sum);
		 System.out.println("Ar2 Sum: "+ar2Sum);
		 System.out.println("Br1 Sum: "+br1Sum);
		 System.out.println("Br2 Sum: "+br2Sum);*/

	 }	 
	 
	 public String positining()
	 {
		 if(blue_value<60 && blue_value!=0)
		 {
			 return "C";
		 }
		 else{
		 int R2=0;
		 String R1ans,R2ans,kans;
		 checkLocation();
		 if(found)
		 {
			 System.out.println(ans);
			 return ans;
		 }
		 else{
		// kans=calculation();
		 R1ans=calculation("R1");
		 R2ans=calculation(R2);
		 System.out.println(R1ans+" "+R2ans+" ");
		 System.out.println(diffr1+" "+diffr2+" "+diffk);

		 if(R1ans.equals(R2ans))
		 {
			 //System.out.println(R2ans);
			 ans=R1ans;
			 store();
			 return R1ans;
		 }
		 else if(diffr1<diffr2 && diffr2-diffr1>7)
		 {
			 //System.out.println(R2ans);
			 ans=R2ans;
			 store();																							//modification on 02/06/2016
			 return R2ans;
		 }
		 else if(diffr2<diffr1 && diffr1-diffr2>7)
		 {
			//System.out.println(R1ans);
			 ans=R1ans;
			 store();
			 return R1ans;
		 }
		 
		 else 
			 return "Unable to find position";
		 }
		//return compare(kans,R1ans,R2ans);
		 }	 
		 }
//	 }

		 
		 public String compare(String kans,String R1ans,String R2ans)
		 {
			 
			 if(R1ans.equals(R2ans) && R1ans.equals(kans))
			 {
				 return R1ans;
			 }
			 else if(R1ans.equals(kans))
			 {
				 return R1ans;
			 }
			 else if(R2ans.equals(R1ans))
			 {
				 return R2ans;
			 }
			 else if(R2ans.equals(kans))
			 {
				 return kans;
			 }
			 else return "Unable to find Position";
		 }
		 
	 public String calculation()
	 {
		 int u1=R1_value,u2=R2_value,temp1,temp2;
		 double distA,distB,distC;
		 int ar1=ar1Sum/5;
		 int ar2=ar2Sum/5;
		 int br1=br1Sum/5;
		 int br2=br2Sum/5;
		 int cr1=cr1Sum/5;
		 int cr2=cr2Sum/5;

		 {
			 temp1=u1-ar1;
			 temp2=u2-ar2;
			 temp1=temp1*temp1;
			 temp2=temp2*temp2;
			 distA=Math.sqrt(temp1+temp2);
		 }
		 {
			 temp1=u1-br1;
			 temp2=u2-br2;
			 temp1=temp1*temp1;
			 temp2=temp2*temp2;
			 distB=Math.sqrt(temp1+temp2);
		 }
		 {
			 temp1=u1-cr1;
			 temp2=u2-cr2;
			 temp1=temp1*temp1;
			 temp2=temp2*temp2;
			 distC=Math.sqrt(temp1+temp2);
		 }
		 
		 if(distA<=distB)
		 {
			// if(distA<distC)
			 {
				// if(distB>distC)
					diffk=distB-distA;
				// else
					// diffk=distC-distA;

			 return "A";
			 }
		 }
		 else if(distA>=distB)
		 {
			 //if(distC>=distB)
			 {
				 //if(distA>distC)
					 diffk=distA-distB;
				// else
					// diffk=distC-distB;
			 return "B";
			 }
		 }
		 /*else if(distA>=distC)
		 {
			 if(distC<=distB)
			 {
				 if(distA>distB)
					 diffk=distA-distC;
				 else
					 diffk=distB-distC;
			 return "C";
			 }
		 }*/
		 
		 return "A";
	 }
	 
	 public String calculation(String A1)
	 {//For R1
		 //System.out.println("Enterted calculation1");
		 int sumA=0,sumB=0,sumC=0,a,b,diffa,diffb,c,diffc;
		 for (int i=0;i<5;i++)
		 {		 
			 a=A.R1[i];
			 System.out.println("a"+a);
			 b=B.R1[i];
			// c=C.R1[i];
			 System.out.println("b"+b);
			//System.out.println("c"+c);

			 diffa=uavg1-a;
			 diffb=uavg1-b;
			// diffc=uavg1-c;
			 sumA=sumA+Math.abs(diffa);
			System.out.println("uavg1 "+uavg1);
			 System.out.println("diffa "+diffa);
			 System.out.println("diffb "+diffb);
			 //System.out.println("diffc"+diffc);
			 sumB=sumB+Math.abs(diffb);
			 System.out.println(sumA);
			 System.out.println(sumB);
			// sumC=sumC+Math.abs(diffc);
			 System.out.println(sumC);

		 }
		 if(sumA>=sumB)
		 {
			//if(sumC>=sumB)
			 {
				// if(sumA>sumC)
					 diffr1=sumA-sumB;
				 //else 
					// diffr1=sumC-sumB;
			 return "B";
			 }
		 }
		 else if(sumA<=sumB)
		 {
			 //if(sumA<=sumC)
			 {
				 //if(sumB>sumC)
					 diffr1=sumB-sumA;
				// else
					 //diffr1=sumC-sumA;
			 return "A";
			 }
		 }
		/* else if(sumC<=sumB)
		 {
			 if(sumC<=sumA)
			 {
				 if(sumB>sumA)
					 diffr1=sumB-sumC;
				 else
					 diffr1=sumA-sumC;
			 return "C";
			 }
		 }*/
		 else
		 {
			 if(Math.abs(ar2Sum)<Math.abs(br2Sum))
				 return "A";
			 else
				 return "B";
		 }
			
		 
			 
	 }
	 public String calculation(int A1)
	 {//for r2
		System.out.println("Enterted calculation2");
		 int sumA=0,sumB=0,a,b,diffa,diffb,c,sumC=0,diffc;
		 for (int i=0;i<5;i++)
		 {		 
			 a=A.R2[i];
			 System.out.println("a"+a);

			 b=B.R2[i];
			 System.out.println("b"+b);
			// c=C.R1[i];
			// System.out.println("c"+c);

			 diffa=uavg2-a;
			 diffb=uavg2-b;
			// diffc=uavg2-c;
			 sumA+=Math.abs(diffa);
			 System.out.println("uavg2 "+uavg2);
			 System.out.println("diffa"+diffa);
			 System.out.println("diffb"+diffb);
			// System.out.println("diffc"+diffc);

			 sumB+=Math.abs(diffb);	
			 System.out.println(sumA);
			 System.out.println(sumB);
			// sumC+=Math.abs(diffc);
			 System.out.println(sumC);

		 }
		 if(sumA>=sumB)
		 {
			// if(sumC>=sumB)
			 {
				 //if(sumA>sumC)
					 diffr2=sumA-sumB;
				// else 
					// diffr2=sumC-sumB;
			 return "B";
			 }
		 }
		  if(sumA<=sumB)
		 {
			 ///if(sumA<=sumC)
			 {
				// if(sumB>sumC)
					 diffr2=sumB-sumA;
				// else
					 //diffr2=sumC-sumA;
			 return "A";
			 }
		 }
		/* else if(sumC<=sumB)
		 {
			 if(sumC<=sumA)
			 {
				 if(sumB>sumA)
					 diffr2=sumB-sumC;
				 else
					 diffr2=sumA-sumC;
			 return "C";
			 }
		 }*/
		 else
		 {
			 if(Math.abs(ar2Sum)<Math.abs(br2Sum))
				 return "A";
			 else
				 return "B";
		 }
		 
	 }
	 public void checkLocation()
	 	{
	 		 String query="Select Artifact from location where R1="+R1_value+" && R2="+R2_value+";";
			// System.out.println("Check called");
			 try
			 {
			 Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ARTIFACTS","root","root123");
				Statement st= con.createStatement();
				ResultSet rs;
				rs=st.executeQuery(query);
				while(rs.next())
				{
					ans=rs.getString(1);
					found=true;
				}
				st.close();
				con.close();;
			 }
			 catch(SQLException e)
			 {
				 e.printStackTrace();
			 }
			 catch(ClassNotFoundException e)
			 {
				 e.printStackTrace();
			 }
	 	}
	 public void store()
	 {
		 String query="Insert into location(Artifact,R1,R2) values ('"+ans+"',"+R1_value+","+R2_value+");";
		 System.out.println("Store called");
		 try
		 {
		 Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ARTIFACTS","root","root123");
			Statement st= con.createStatement();
			st.executeUpdate(query);
			st.close();
			con.close();
		 }
		 catch(SQLException e)
		 {
			 e.printStackTrace();
		 }
		 catch(ClassNotFoundException e)
		 {
			 e.printStackTrace();
		 }
	 }  
}

class Artifact
{
	int[] R1;
	int[] R2;
	String AName;
	Artifact(String A)
	{
		R1= new int [5];
		R2=new int [5];
	}
}
