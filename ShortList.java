
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class ShortList {

	private String npi1;
	private String npi2;
	private String npi3;

	ShortList(String n1, String n2, String n3) {
		npi1=n1;
		npi2=n2;
		npi3=n3;
	}
	
	@SuppressWarnings("unchecked")
	JSONObject service(){
		
		//check params
		if (Utils.isBlank(npi1))
			return new JSONObject();

		//get the data from the db
		DBShortList mydb = new DBShortList();
		ArrayList<String[]> mylist = mydb.getContent(npi1,npi2,npi3);		
	
		//build the json response
		JSONObject obj=new JSONObject();
		JSONArray jsonlist = new JSONArray();
		
		int i=0;
		for (String[] s: mylist){
			//first element is the transaction id
			if (i==0){
				obj.put("Transaction",s[0]);
			//this is the list of providers
			}else{
				Map tmp=new LinkedHashMap();
				tmp.put("NPI",s[0]);
				tmp.put("Provider_Full_Name",s[1]);
				tmp.put("Provider_Full_Street",s[2]);
				tmp.put("Provider_Full_City",s[3]);
				tmp.put("Provider_Business_Practice_Location_Address_Telephone_Number",s[4]);
				jsonlist.add(tmp);
			}
			i++;
		}
		obj.put("Providers", jsonlist);
		return obj;
	}
}