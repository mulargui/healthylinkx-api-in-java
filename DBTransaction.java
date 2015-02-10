
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.lang.SecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBTransaction {
	ArrayList<String[]> getContent( String id ){

		//supporting docker containers. get the address of the mysql container
		String SQLContainerID="MySQLDB";
		try{
			InetAddress Address = InetAddress.getByName(SQLContainerID); 
			SQLContainerID = Address.getHostAddress();
		} catch (UnknownHostException e){
			SQLContainerID = "127.0.0.1";
		} catch (SecurityException e){
			SQLContainerID = "127.0.0.1";
		}

		Connection conn;
		Statement stmt;
		ResultSet rs;
				
		ArrayList<String[]> myList = new ArrayList<String[]>();
		try{
			conn = DriverManager.getConnection("jdbc:mysql://" + SQLContainerID + "/healthylinkx","root","awsawsdb");
			
			// get the list of NPI's
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM transactions WHERE (id = '"+ id +"')");
			rs.next();
			String npi1=rs.getString("NPI1");
			String npi2=rs.getString("NPI2");
			String npi3=rs.getString("NPI3");
			rs.close();
			
			//get the details of the providers
			String querystring = "SELECT NPI,Provider_Full_Name,Provider_Full_Street, Provider_Full_City, Provider_Business_Practice_Location_Address_Telephone_Number FROM npidata2 WHERE ((NPI = '" 
								+ npi1 + "')";
			if (!Utils.isBlank(npi2))
				querystring += "OR (NPI = '" + npi2 +"')";
			if (!Utils.isBlank(npi3))
				querystring += "OR (NPI = '" + npi3 +"')";
			querystring += ")";
			
			rs = stmt.executeQuery(querystring);
						
			while (rs.next()) {
				String[] tmp = new String[5];
				tmp[0]=rs.getString("NPI");
				tmp[1]=rs.getString("Provider_Full_Name");
				tmp[2]=rs.getString("Provider_Full_Street");
				tmp[3]=rs.getString("Provider_Full_City");
				tmp[4]=rs.getString("Provider_Business_Practice_Location_Address_Telephone_Number");
				myList.add(tmp);
			}

			rs.close();
			stmt.close();
			conn.close();
		}catch (SQLException ex){
			myList.clear();
		}

		return myList;
	}
}