 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONArray;
 
@Path("/")
public class MyRestApi {
 
    @GET
	@Path("/taxonomy")
	@Produces(MediaType.APPLICATION_JSON)
    public Response GetTaxonomy() {
		//service the request
		Taxonomy tx = new Taxonomy();		
		JSONArray jsonlist =  tx.service();
		
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
		@QueryParam("distance") String distance
	) {
		Providers pr = new Providers(zipcode,gender,lastname1,lastname2,lastname3,specialty,distance);		
		JSONArray jsonlist =  pr.service();

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
	
	@GET
	@Path("/shortlist")
	@Produces(MediaType.TEXT_PLAIN)
    public String GetShortlist(
		@QueryParam("NPI1") String npi1,
		@QueryParam("NPI2") String npi2,
		@QueryParam("NPI3") String npi3
	) {
        return "Shortlist:"+npi1+"#"+npi2+"#"+npi3+"#";
    }
	
	@GET
	@Path("/transaction")
	@Produces(MediaType.TEXT_PLAIN)
    public String GetTransaction(
		@QueryParam("id") String id	
	) {
        return "Transaction:"+id+"#";
    }
}
