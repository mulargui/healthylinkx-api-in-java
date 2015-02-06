
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class Taxonomy {
	
	@SuppressWarnings("unchecked")
	JSONArray service(){
		//get the data from the db
		DBTaxonomy mydb = new DBTaxonomy();
		ArrayList<String> mylist = mydb.getContent();
		
		//build the json response
		JSONArray jsonlist = new JSONArray();
		for (String s: mylist){
			JSONObject tmp = new JSONObject();
			tmp.put("Classification",s);
			jsonlist.add(tmp);
		}
		return jsonlist;
	}
}