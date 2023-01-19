package facades;

import dtos.AssignmentDTO;
import dtos.MemberDTO;
import entities.Assignment;
import entities.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

public class AssignmentFacade
{
    private static EntityManagerFactory emf;
    private static AssignmentFacade instance;

    private AssignmentFacade()
    {
    }

    public static AssignmentFacade getInstance(EntityManagerFactory _emf)
    {
        if (instance == null) {
            emf = _emf;
            instance = new AssignmentFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public List<AssignmentDTO> getAllAssignments()
    {
        EntityManager em = getEntityManager();
        try
        {
            TypedQuery<Assignment> query = em.createQuery("select a from Assignment a", Assignment.class);
            List<Assignment> assignmentList = query.getResultList();
            if (assignmentList.isEmpty())
                throw new WebApplicationException("No assignments was found");
            List<AssignmentDTO> assignmentDTOList = new ArrayList<>();
            assignmentList.forEach(assignment -> assignmentDTOList.add(new AssignmentDTO(assignment)));
            return assignmentDTOList;
        }
        finally {
            em.close();
        }
    }

    public AssignmentDTO getAssignmentById (Integer assignmentId)
    {
        EntityManager em = getEntityManager();
        try {
            Assignment assignment = em.find(Assignment.class, assignmentId);
            if (assignment == null)
                throw new WebApplicationException("Assignment with id: " + assignmentId + " doesn't exist");
            return new AssignmentDTO(assignment);
        } finally {
            em.close();
        }
    }

    public List<AssignmentDTO> getAssignmentsByMember(Integer memberId)
    {
        EntityManager em = getEntityManager();
        Member member = em.find(Member.class, memberId);
        if (member == null)
            throw new WebApplicationException("Member with id: " + memberId + "doesn't exist");
        List<Assignment> assignmentList = member.getAssignments();
        List<AssignmentDTO> assignmentDTOList = new ArrayList<>();
        assignmentList.forEach(assignmet -> assignmentDTOList.add(new AssignmentDTO(assignmet)));
        return assignmentDTOList;
    }

}
