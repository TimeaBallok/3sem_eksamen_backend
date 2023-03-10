package facades;

import dtos.DinnerEventDTO;
import entities.Assignment;
import entities.DinnerEvent;
import entities.Member;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

public class DinnerEventFacade
{
    private static EntityManagerFactory emf;
    private static DinnerEventFacade instance;

    private DinnerEventFacade()
    {
    }

    public static DinnerEventFacade getInstance(EntityManagerFactory _emf)
    {
        if (instance == null) {
            emf = _emf;
            instance = new DinnerEventFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public List<DinnerEventDTO> getAllDinners()
    {
        EntityManager em = getEntityManager();
        try
        {
            TypedQuery<DinnerEvent> query = em.createQuery("select d from DinnerEvent d", DinnerEvent.class);
            List<DinnerEvent> dinnerEventList = query.getResultList();
            if (dinnerEventList.isEmpty())
                throw new WebApplicationException("No dinner events was found");
            List<DinnerEventDTO> dinnerEventDTOList = new ArrayList<>();
            dinnerEventList.forEach(dinner -> dinnerEventDTOList.add(new DinnerEventDTO(dinner)));
            return dinnerEventDTOList;
        }
        finally {
            em.close();
        }
    }

    public DinnerEventDTO getDinnerById (Integer dinnerId)
    {
        EntityManager em = getEntityManager();
        try {
            DinnerEvent dinnerEvent = em.find(DinnerEvent.class, dinnerId);
            if (dinnerEvent == null)
                throw new WebApplicationException("Dinner event with id: " + dinnerId + " doesn't exist");
            return new DinnerEventDTO(dinnerEvent);
        } finally {
            em.close();
        }
    }

    public List<DinnerEventDTO> getAllDinnersByMember(Integer memberId)
    {
        EntityManager em = getEntityManager();
        Member member = em.find(Member.class,memberId);
        if (member == null)
            throw new WebApplicationException("Member with id: " + memberId + "doesn't exist");
        try
        {
            TypedQuery<DinnerEvent> query = em.createQuery("select d from DinnerEvent d join d.assignments a join a.members m where m.id = :memberId", DinnerEvent.class);
            query.setParameter("memberId", memberId);
            List<DinnerEvent> dinnerEventList = query.getResultList();
            if (dinnerEventList.isEmpty())
                throw new WebApplicationException("No dinners was found");
            List<DinnerEventDTO> dinnerEventDTOList = new ArrayList<>();
            dinnerEventList.forEach(dinner -> dinnerEventDTOList.add(new DinnerEventDTO(dinner)));
            return dinnerEventDTOList;
        }
        finally {
            em.close();
        }
    }

    public DinnerEventDTO createNewDinnerEvent(DinnerEventDTO dinnerEventDTO)
    {
        EntityManager em = getEntityManager();
        DinnerEvent dinnerEvent = new DinnerEvent(dinnerEventDTO.getTime(), dinnerEventDTO.getLocation(), dinnerEventDTO.getDish(), dinnerEventDTO.getPrice());
        try {
            em.getTransaction().begin();
            em.persist(dinnerEvent);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new DinnerEventDTO(dinnerEvent);
    }

    public String deleteDinner(int dinnerId)
    {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            DinnerEvent dinnerEvent = em.find(DinnerEvent.class, dinnerId);
            if (dinnerEvent == null)
                throw new WebApplicationException("Dinner event with id: " + dinnerId + " doesn't exist");
            List<Assignment> assignmentList = dinnerEvent.getAssignments();
            assignmentList.forEach(assignment -> assignment.setDinnerEvent(null));
            em.remove(dinnerEvent);
            em.getTransaction().commit();
            return "Dinner event with id " + dinnerId + " was succesfully deleted";
        } finally {
            em.close();
        }
    }

    public DinnerEventDTO updateDinnerEvent(DinnerEventDTO dinnerEventDTO)
    {
        EntityManager em = getEntityManager();
        DinnerEvent dinnerEvent = em.find(DinnerEvent.class, dinnerEventDTO.getId());
        if (dinnerEvent == null)
            throw new WebApplicationException("Dinner event with id: " + dinnerEventDTO.getId() + " doesn't exist");
        dinnerEvent.setTime(dinnerEventDTO.getTime());
        dinnerEvent.setLocation(dinnerEventDTO.getLocation());
        dinnerEvent.setDish(dinnerEventDTO.getDish());
        dinnerEvent.setPrice(dinnerEventDTO.getPrice());
        try {
            em.getTransaction().begin();
            em.merge(dinnerEvent);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new DinnerEventDTO(dinnerEvent);
    }



}
