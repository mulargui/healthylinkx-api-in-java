
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MyDB {
	ArrayList<String> taxonomy(){
		Connection conn;
		Statement stmt;
		ResultSet rs;
		
		ArrayList<String> myList = new ArrayList<String>();
		
		try{
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/healthylinkx","root","awsawsdb");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM taxonomy");
		
			while (rs.next()) {
				myList.add(rs.getString(1));
				
			}

			rs.close();
			stmt.close();
			conn.close();
		}catch (SQLException ex){
			// handle any errors
			//myList.add(ex.getMessage());
			//myList.add(ex.getSQLState());
			//myList.add(ex.getErrorCode());
			
			//in case of error return an empty string
			myList.clear();
		}

		return myList;
	}
}