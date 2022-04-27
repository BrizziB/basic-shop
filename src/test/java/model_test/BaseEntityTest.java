package model_test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class BaseEntityTest {
    private FakeBaseEntity e1;
    private FakeBaseEntity e2;
    private FakeBaseEntity e3;
    @BeforeEach
    public void setup(){
        System.out.println("Eseguo il setup..");
        String uuid1 = UUID.randomUUID().toString();
        String uuid2 = UUID.randomUUID().toString();

        e1 = new FakeBaseEntity( uuid1 );
        e2 = new FakeBaseEntity( uuid2 );
        e3 = new FakeBaseEntity( uuid1 );
    }

    @Test
    public void testNullUUID() {
        System.out.println("Eseguo testNullUUID..");
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            new FakeBaseEntity(null);
        });
    }

    @Test
    public void testEquals(){
        System.out.println("Eseguo testEquals..");
        Assertions.assertEquals(e1, e1); //identità
        Assertions.assertEquals(e1, e3); //uguaglianza
        Assertions.assertNotEquals(e1, e2); //(dis)uguaglianza
    }

}
