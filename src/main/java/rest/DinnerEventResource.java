package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AssignmentDTO;
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

    @Path("member/{memberId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllDinnersByMember(@PathParam("memberId") Integer memberId)
    {
        List<DinnerEventDTO> dinnerEventDTOList = FACADE.getAllDinnersByMember(memberId);
        return GSON.toJson(dinnerEventDTOList);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String createNewDinnerEvent(String input){
        DinnerEventDTO dinnerEventDTO = GSON.fromJson(input, DinnerEventDTO.class);
        DinnerEventDTO newDinnerEvent = FACADE.createNewDinnerEvent(dinnerEventDTO);
        return GSON.toJson(newDinnerEvent);
    }

    @DELETE
    @Path("delete/{dinnerId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteDinnerEvent(@PathParam("dinnerId")Integer dinnerId){
        String deleted = FACADE.deleteDinner(dinnerId);
        return GSON.toJson(deleted);
    }

    @PUT
    @Path("update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateBoat (@PathParam("id") int id, String input){
        DinnerEventDTO dinnerEventDTO = GSON.fromJson(input, DinnerEventDTO.class);
        DinnerEventDTO updatedDinner = FACADE.updateDinnerEvent(dinnerEventDTO);
        return GSON.toJson(updatedDinner);
    }
}