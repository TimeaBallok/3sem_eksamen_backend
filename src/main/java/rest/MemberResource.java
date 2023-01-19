package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MemberDTO;
import facades.MemberFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("member")
public class MemberResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final MemberFacade FACADE =  MemberFacade.getInstance(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello from members\"}";
    }

    @Path("id/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMemberById(@PathParam("id") Integer memberId)
    {
        MemberDTO memberDTO = FACADE.getMemberById(memberId);
        return GSON.toJson(memberDTO);
    }

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String allMembers() {
        List<MemberDTO> memberDTOList = FACADE.getAllMembers();
        return GSON.toJson(memberDTOList);
    }

    @Path("name/{userName}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMemberIdByuserName(@PathParam("userName") String userName)
    {
        Integer memberId = FACADE.getMemberIdByUserName(userName);
        return GSON.toJson(memberId);
    }
}