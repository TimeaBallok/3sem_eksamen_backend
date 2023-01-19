package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class MemberFacade
{
    private static EntityManagerFactory emf;
    private static MemberFacade instance;

    private MemberFacade()
    {
    }

    public static MemberFacade getInstance(EntityManagerFactory _emf)
    {
        if (instance == null) {
            emf = _emf;
            instance = new MemberFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }
}
