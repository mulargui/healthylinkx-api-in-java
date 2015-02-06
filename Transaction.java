
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;

public class Transaction {

	private String id;

	Transaction(String i){
		id=i;
	}
	
	@SuppressWarnings("unchecked")
	JSONArray service(){
		
		 //check params
		if (Utils.isBlank(id))
			return new JSONArray();

		//get the data from the db
		DBTransaction mydb = new DBTransaction();
		ArrayList<String[]> mylist = mydb.getContent(id);		
	
		//build the json response
		JSONArray jsonlist = new JSONArray();
		for (String[] s: mylist){
			Map tmp=new LinkedHashMap();
			tmp.put("NPI",s[0]);
			tmp.put("Provider_Full_Name",s[1]);
			tmp.put("Provider_Full_Street",s[2]);
			tmp.put("Provider_Full_City",s[3]);
			tmp.put("Provider_Business_Practice_Location_Address_Telephone_Number",s[4]);
			jsonlist.add(tmp);
		}
		return jsonlist;
	}
}