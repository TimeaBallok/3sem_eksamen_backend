package utils;


import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user1 = new User("member1", "test123");
    User user2 = new User("member2", "test123");
    User user3 = new User("member3", "test123");
//    User admin = new User("admin", "test123");
//    User both = new User("user_admin", "test123");
//
//    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
//      throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
    Role role = em.find(Role.class, "user");
    user1.addRole(role);
    user2.addRole(role);
    user3.addRole(role);
    em.persist(user1);
    em.persist(user2);
    em.persist(user3);
//    Role userRole = new Role("user");
//    Role adminRole = new Role("admin");
//    user.addRole(userRole);
//    admin.addRole(adminRole);
//    both.addRole(userRole);
//    both.addRole(adminRole);
//    em.persist(userRole);
//    em.persist(adminRole);
//    em.persist(user);
//    em.persist(admin);
//    em.persist(both);
    em.getTransaction().commit();
    System.out.println("PW: " + user1.getUserPass());
    System.out.println("Testing user with OK password: " + user1.verifyPassword("test123"));
    System.out.println("Testing user with wrong password: " + user1.verifyPassword("test1"));
    System.out.println("Created TEST Users");
   
  }

}
