package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

}
