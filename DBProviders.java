
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.lang.SecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBProviders {
	ArrayList<String[]> getContent( ArrayList<String> zipcodes, String gender, 
		String lastname1, String lastname2, String lastname3, 
		String specialty
	){
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
			
			//building the query string
			String query= "SELECT NPI,Provider_Full_Name,Provider_Full_Street,Provider_Full_City FROM npidata2 WHERE (";
			
			if(!Utils.isBlank(lastname1))
				query += "((Provider_Last_Name_Legal_Name = '" + lastname1 + "')";
			if(!Utils.isBlank(lastname2))
				query += " OR (Provider_Last_Name_Legal_Name = '" + lastname2 + "')";
			if(!Utils.isBlank(lastname3))
				query += " OR (Provider_Last_Name_Legal_Name = '" + lastname3 + "')";
			if(!Utils.isBlank(lastname1))
				query += ")";
			if(!Utils.isBlank(gender)){
				if(!Utils.isBlank(lastname1))
					query += " AND ";
				query += "(Provider_Gender_Code = '" + gender + "')";
			}
			if(!Utils.isBlank(specialty)){
				if(!Utils.isBlank(lastname1) || !Utils.isBlank(gender))
					query += " AND ";
				query += "(Classification = '" + specialty + "')";
			}
 			if(!zipcodes.isEmpty()){
				if(!Utils.isBlank(lastname1) || !Utils.isBlank(gender) || !Utils.isBlank(specialty))
					query += " AND ";
				query += "((Provider_Short_Postal_Code = '"+ zipcodes.get(0) +"')";
				for (int i = 1; i < zipcodes.size(); i++)
					query += " OR (Provider_Short_Postal_Code = '"+ zipcodes.get(i) +"')";
				query += ")";
			}
  			query += ") limit 50";
						
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				String[] tmp = new String[4];
				tmp[0]=rs.getString("NPI");
				tmp[1]=rs.getString("Provider_Full_Name");
				tmp[2]=rs.getString("Provider_Full_Street");
				tmp[3]=rs.getString("Provider_Full_City");
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