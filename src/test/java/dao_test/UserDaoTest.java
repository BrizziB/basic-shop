package dao_test;

import dao.UserDao;
import model.ModelFactory;
import model.User;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDaoTest extends JPATest{
    private UserDao userDao; //classe sotto test
    private User user;
    @Override
    protected void init()  throws IllegalAccessException  {
        System.out.println("Avvio init custom per UserDaoTest");
        user = ModelFactory.initializeUser();
        user.setEmail("user1@test.com");
        user.setPassword("secret");
        entityManager.persist(user);                                                //persisto manualmente, senza passare dal DAO
        userDao = new UserDao();                                                    //UserDao creato manualmente - niente CDI!
        FieldUtils.writeField
                (userDao, "em", entityManager, true);
    }

    @Test
    public void testFindByID() {                                                // test di funzionalit� di tipo RETRIEVE
        User result = userDao.findById(user.getId());                           // riprendo lo user (persistito manualmente in init) tramite il DAO
        Assertions.assertEquals(user.getId(), result.getId());                  // tramite asserzioni verifico che lo user estratto dal DAO
        Assertions.assertEquals(user.getUuid(), result.getUuid());               // sia Uguale a quello che avevo manualmente inserito nel DB
        Assertions.assertEquals(user.getEmail(), result.getEmail());
        Assertions.assertEquals(user.getPassword(), result.getPassword());
    }

    @Test
    public void testSave() {                                                            // test di funzionalit� di tipo SAVE
        User userToPersist = ModelFactory.initializeUser();
        userToPersist.setEmail( "user2@example.com" );
        userToPersist.setPassword( "anotherSecret" );
        userDao.save( userToPersist );                                                  // stavolta persisto tramite DAO ..
        User manuallyRetrievedUser = entityManager.
                createQuery("FROM User WHERE uuid = :uuid", User.class)
                .setParameter("uuid", userToPersist.getUuid())           	            // ..ed estraggo manualmente tramite query in JPQL
                .getSingleResult();
        Assertions.assertEquals( userToPersist, manuallyRetrievedUser );    // verifico poi l'uguaglianza tramite asserzioni
    }



    @Test
    public void testFindByEmail() {
        User result = userDao.findUserByEmail(user.getEmail());

        Assertions.assertEquals(user.getId(), result.getId());
        Assertions.assertEquals(user.getUuid(), result.getUuid());
        Assertions.assertEquals(user.getEmail(), result.getEmail());
        Assertions.assertEquals(user.getPassword(), result.getPassword());
    }


}
