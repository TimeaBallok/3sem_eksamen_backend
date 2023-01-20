package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AssignEventDTO;
import dtos.AssignmentDTO;
import dtos.DinnerEventDTO;
import facades.AssignmentFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("assignment")
public class AssignmentResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final AssignmentFacade FACADE =  AssignmentFacade.getInstance(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello from assignments\"}";
    }

    @Path("id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAssignmentById(@PathParam("id") Integer assignmentId)
    {
        AssignmentDTO assignmentDTO = FACADE.getAssignmentById(assignmentId);
        return GSON.toJson(assignmentDTO);
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String allAssignments() {
        List<AssignmentDTO> assignmentDTOList = FACADE.getAllAssignments();
        return GSON.toJson(assignmentDTOList);
    }

    @Path("member/{memberId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAssignmentsByMember(@PathParam("memberId") Integer memberId)
    {
        List<AssignmentDTO> assignmentDTOList = FACADE.getAssignmentsByMember(memberId);
        return GSON.toJson(assignmentDTOList);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String createNewAssignment(String input){
        AssignmentDTO assignmentDTO = GSON.fromJson(input, AssignmentDTO.class);
        AssignmentDTO newAssignment = FACADE.createNewAssignment(assignmentDTO);
        return GSON.toJson(newAssignment);
    }



}