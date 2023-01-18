package facades;
import dtos.UserDTO;
import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;

import security.errorhandling.AuthenticationException;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    public static UserFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public UserDTO createUser(UserDTO userDTO)
    {
        EntityManager em = getEntityManager();
        try
        {
            User user = new User(userDTO.getUserName(), userDTO.getUserPass());
            em.getTransaction().begin();
            Role role = em.find(Role.class, "user");
            user.addRole(role);
            em.persist(user);
            em.getTransaction().commit();
            return new UserDTO(user);
        }
        finally
        {
            em.close();
        }
    }

}
