package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.DinnerEventDTO;
import facades.DinnerEventFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("dinner")
public class DinnerEventResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final DinnerEventFacade FACADE =  DinnerEventFacade.getInstance(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello from dinners\"}";
    }

    @Path("id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getDinnerById(@PathParam("id") Integer dinnerId)
    {
        DinnerEventDTO dinnerEventDTO = FACADE.getDinnerById(dinnerId);
        return GSON.toJson(dinnerEventDTO);
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String allDinners() {
        List<DinnerEventDTO> dinnerEventDTOList = FACADE.getAllDinners();
        return GSON.toJson(dinnerEventDTOList);
    }
}