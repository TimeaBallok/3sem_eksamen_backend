package facades;

import dtos.DinnerEventDTO;
import entities.Assignment;
import entities.DinnerEvent;
import entities.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.WebApplicationException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DinnerEventFacadeTest
{
    private static EntityManagerFactory emf;
    private static DinnerEventFacade facade;

    DinnerEvent dinnerEvent1, dinnerEvent2, dinnerEvent3;
    Assignment assignment1, assignment2;
    Member member1, member2;

    public DinnerEventFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = DinnerEventFacade.getInstance(emf);
    }

    @BeforeEach
    void setUp()
    {
        dinnerEvent1 = new DinnerEvent("23-01-23 18:00", "Rønne", "Ungarsk goulasch", 60.0);
        dinnerEvent2 = new DinnerEvent("23-01-24 18:00", "Rønne", "Sphagetti bolognese", 50.0);
        assignment1 = new Assignment("Ballok", "Timi");
        assignment2 = new Assignment("Ballok", "Timi");
        member1 = new Member("Tetsvej 1", "123456", "test1@test.dk", 1975, 500.0);
        member2 = new Member("Tetsvej 2", "654321", "test2@test.dk", 1999, 500.0);

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("DinnerEvent.deleteAllRows").executeUpdate();
            em.createNamedQuery("Member.deleteAllRows").executeUpdate();
            em.createNamedQuery("Assignment.deleteAllRows").executeUpdate();
            dinnerEvent1.addAssignment(assignment1);
            member1.assignMembers(assignment1);
            em.persist(assignment1);
            em.persist(dinnerEvent1);
            em.persist(dinnerEvent2);
            em.persist(member1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    void tearDown()
    {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            member1 = em.find(Member.class, member1.getId());
            em.remove(member1);
            dinnerEvent1 = em.find(DinnerEvent.class, dinnerEvent1.getId());
            dinnerEvent1.getAssignments().forEach(assignment -> assignment.setDinnerEvent(null));
            em.remove(dinnerEvent1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }


    @Test
    void getAllDinners()
    {
        assertEquals(2, facade.getAllDinners().size());
    }

    @Test
    void getDinnerById()
    {
        assertEquals(dinnerEvent1.getId(), facade.getDinnerById(dinnerEvent1.getId()).getId());
    }

    @Test
    void getAllDinnersByMember()
    {
        assertEquals(1, facade.getAllDinnersByMember(member1.getId()).size());
        assertTrue(true,  String.valueOf(facade.getAllDinnersByMember(member1.getId()).contains(dinnerEvent1)));
        assertThrows(WebApplicationException.class, () -> {
            List<DinnerEventDTO> dinnerEventDTOs = facade.getAllDinnersByMember(55);
        });

    }

    @Test
    void createNewDinnerEvent()
    {
        DinnerEvent dinnerEvent = new DinnerEvent("23-01-23 15:00", "Rønne", "Æblekage", 40.0);
        DinnerEventDTO dinnerEventDTO = new DinnerEventDTO(dinnerEvent);
        DinnerEventDTO newDinner = facade.createNewDinnerEvent(dinnerEventDTO);
        assertNotNull(newDinner.getId());

    }

    @Test
    void deleteDinner()
    {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            dinnerEvent3 =new DinnerEvent("23-01-23 18:00", "Rønne", "Ungarsk goulasch", 60.0);
            em.persist(dinnerEvent3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        int dinnerId = dinnerEvent3.getId();
        assertEquals("Dinner event with id " + dinnerId + " was succesfully deleted", facade.deleteDinner(dinnerId));
        assertThrows(WebApplicationException.class, () -> {
            String actual = facade.deleteDinner(55);
        });
    }
}