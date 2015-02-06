
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.ProtocolException;

public class Providers {

	private String zipcode;
	private String gender;
	private String lastname1;
	private String lastname2;
	private String lastname3;
	private String specialty;
	private String distance;
	private ArrayList<String> zipcodes;

	Providers(String z, String g, 
		String l1, String l2, String l3, 
		String s, String d
	){
		zipcode=z;
		gender=g;
		lastname1=l1;
		lastname2=l2;
		lastname3=l3;
		specialty=s;
		distance=d;
		zipcodes = new ArrayList<String>();
	}
	
	@SuppressWarnings("unchecked")
	JSONArray service(){
		
		 //check params
		if (Utils.isBlank(zipcode) && Utils.isBlank(lastname1) && Utils.isBlank(specialty))
			return new JSONArray();

		//find zipcodes at a distance
		if (!Utils.isBlank(distance) && !Utils.isBlank(zipcode)){
			if (!getListOfZipcodes())return new JSONArray();
		}else{
			zipcodes.add(zipcode);
		}
		
		//get the data from the db
		DBProviders mydb = new DBProviders();
		ArrayList<String[]> mylist = mydb.getContent(zipcodes,gender,lastname1,lastname2,lastname3,specialty);		
	
		//build the json response
		JSONArray jsonlist = new JSONArray();
		for (String[] s: mylist){
			Map tmp=new LinkedHashMap();
			tmp.put("NPI",s[0]);
			tmp.put("Provider_Full_Name",s[1]);
			tmp.put("Provider_Full_Street",s[2]);
			tmp.put("Provider_Full_City",s[3]);
			jsonlist.add(tmp);
		}
		return jsonlist;
	}
	
	boolean getListOfZipcodes(){
		try{
			URL url = new URL("http://www.zipcodeapi.com/rest/GFfN8AXLrdjnQN08Q073p9RK9BSBGcmnRBaZb8KCl40cR1kI1rMrBEbKg4mWgJk7/radius.json/" 
							+ zipcode + "/" + distance + "/mile");	
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			if( conn.getResponseCode()!= HttpURLConnection.HTTP_OK) return false;
		
		    BufferedReader rd  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null)
				sb.append(line + '\n');
			
			JSONObject jsobj= (JSONObject)JSONValue.parse(sb.toString());
			JSONArray jsarr=(JSONArray)jsobj.get("zip_codes");
			for (int i = 0; i < jsarr.size(); i++){
				JSONObject tmp = (JSONObject)jsarr.get(i);
				zipcodes.add(tmp.get("zip_code").toString());
			}			
		} catch (MalformedURLException  e) {
			return false;
		} catch (ProtocolException e) {
			return false;
		} catch (IOException e) {
			return false;
		}		
		return true;
	}
}