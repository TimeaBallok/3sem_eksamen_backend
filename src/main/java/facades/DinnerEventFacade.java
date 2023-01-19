package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
}
