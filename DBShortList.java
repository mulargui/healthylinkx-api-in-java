
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBShortList {
	ArrayList<String[]> getContent( String npi1, String npi2, String npi3){
		Connection conn;
		PreparedStatement stmt;
		ResultSet rs;
				
		ArrayList<String[]> myList = new ArrayList<String[]>();
		String transaction;
		
		try{
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/healthylinkx","root","awsawsdb");
			
			//insert the selected providers
			stmt = conn.prepareStatement("INSERT INTO transactions VALUES (DEFAULT,DEFAULT,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, npi1); 
			stmt.setString(2, npi2); 
			stmt.setString(3, npi3); 
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			rs.next();
			
			//we put the transaction number as the first element of the list
			String[] temp = new String[5];
			temp[0] = rs.getString(1);			
			myList.add(temp);
			
			rs.close();
			stmt.close();
			
			//return detailed info of the providers
			String querystring = "SELECT NPI,Provider_Full_Name,Provider_Full_Street, Provider_Full_City, Provider_Business_Practice_Location_Address_Telephone_Number FROM npidata2 WHERE ((NPI = '"
									+ npi1 + "')";
			if(!Utils.isBlank(npi2))
				querystring += "OR (NPI = '" + npi2 + "')";
			if(!Utils.isBlank(npi3))
				querystring += "OR (NPI = '" + npi3 + "')";
			querystring += ")";
	
			stmt = conn.prepareStatement(querystring);
			rs = stmt.executeQuery();

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