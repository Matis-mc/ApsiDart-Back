package org.dart.cricket;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/cricket")
public class CricketRessource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test(){
        return "on teste un ptit truc là";
    }
    

}
