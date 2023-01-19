package facades;

import dtos.DinnerEventDTO;
import entities.DinnerEvent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
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


}
