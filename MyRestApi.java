 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
@Path("/")
public class MyRestApi {
 
    @GET
	@Path("/taxonomy")
	@Produces(MediaType.APPLICATION_JSON)
    public Response GetTaxonomy(){
		//service the request
		Taxonomy tx = new Taxonomy();		
		JSONArray jsonlist =  tx.service();
		
		//reply to the request
		return reply(jsonlist);
	}
	
	@GET
	@Path("/providers")
	@Produces(MediaType.APPLICATION_JSON)
    public Response GetProviders(
		@QueryParam("zipcode") String zipcode,
		@QueryParam("gender") String gender,
		@QueryParam("lastname1") String lastname1,
		@QueryParam("lastname2") String lastname2,
		@QueryParam("lastname3") String lastname3,
		@QueryParam("specialty") String specialty,
		@QueryParam("distance") String distance ){
 		//service the request
		Providers pr = new Providers(zipcode,gender,lastname1,lastname2,lastname3,specialty,distance);		
		JSONArray jsonlist =  pr.service();

		//reply to the request
		return reply(jsonlist);
	}

	@GET
	@Path("/transaction")
	@Produces(MediaType.APPLICATION_JSON)
    public Response GetTransaction(
		@QueryParam("id") String id	){
  		//service the request
		Transaction tr = new Transaction(id);		
		JSONArray jsonlist =  tr.service();

		//reply to the request
		return reply(jsonlist);
    }
	
	@GET
	@Path("/shortlist")
	@Produces(MediaType.APPLICATION_JSON)
    public Response GetShortlist(
		@QueryParam("NPI1") String npi1,
		@QueryParam("NPI2") String npi2,
		@QueryParam("NPI3") String npi3 ){
 		//service the request
		ShortList sl = new ShortList(npi1,npi2,npi3);		
		JSONObject jsonlist =  sl.service();

		//reply to the request
		if (jsonlist.isEmpty())
			return Response.noContent()
			.header("Access-Control-Allow-Origin", "*")
			.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
			.allow("OPTIONS")
			.build();
		return Response.ok()
			.entity(jsonlist.toString())
			.header("Access-Control-Allow-Origin", "*")
			.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
			.allow("OPTIONS")
			.build();
    }
		
	private Response reply(JSONArray jsonlist){
		if (jsonlist.isEmpty())
			return Response.noContent()
			.header("Access-Control-Allow-Origin", "*")
			.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
			.allow("OPTIONS")
			.build();
		return Response.ok()
			.entity(jsonlist.toString())
			.header("Access-Control-Allow-Origin", "*")
			.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
			.allow("OPTIONS")
			.build();
    }
}
