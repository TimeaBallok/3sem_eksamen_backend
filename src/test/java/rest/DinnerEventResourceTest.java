package rest;

import entities.Assignment;
import entities.DinnerEvent;
import entities.Member;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

class DinnerEventResourceTest
{

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    DinnerEvent dinnerEvent1, dinnerEvent2, dinnerEvent3;
    Assignment assignment1, assignment2;
    Member member1, member2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer()
    {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    void setUp()
    {
        dinnerEvent1 = new DinnerEvent("23-01-23 18:00", "Rønne", "Ungarsk goulasch", 60);
        dinnerEvent2 = new DinnerEvent("23-01-24 18:00", "Rønne", "Sphagetti bolognese", 50);
        assignment1 = new Assignment("Ballok", "Timi");
        assignment2 = new Assignment("Ballok", "Timi");
        member1 = new Member("Tetsvej 1", "123456", "test1@test.dk", 1975, 500);
        member2 = new Member("Tetsvej 2", "654321", "test2@test.dk", 1999, 500);

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
    public void testServerIsUp() {
        given().when().get("/xxx").then().statusCode(200);
    }

    @Test
    void getInfoForAll()
    {
        given()
                .contentType("application/json")
                .get("/dinner/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello from dinners"));
    }

    @Test
    void getDinnerById()
    {
        given()
                .contentType("application/json")
                .get("/dinner/id/" + dinnerEvent1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("dish", equalTo("Ungarsk goulasch"));
    }

    @Test
    void allDinners()
    {
        given()
                .contentType("application/json")
                .get("/dinner/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("", hasSize(2));
    }

    @Test
    void getAllDinnersByMember()
    {
        given()
                .contentType("application/json")
                .get("/dinner/member/" + member1.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("", hasSize(1));
    }

    @Test
    void createNewDinnerEvent()
    {
        DinnerEvent dinnerEvent = new DinnerEvent("23-01-25 18:00", "Hasle", "Lasagne", 50);
        given()
                .contentType("application/json")
                .body(dinnerEvent)
                .when()
                .post("/dinner").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("location", equalTo("Hasle"));
        given()
                .contentType("application/json")
                .get("/dinner/all").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("", hasSize(3));
    }

    @Test
    void deleteDinnerEvent()
    {
        given()
                .contentType("application/json")
                .delete("/dinner/delete/" + dinnerEvent2.getId()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }
}