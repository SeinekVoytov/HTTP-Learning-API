package daotest;

import org.junit.jupiter.api.Test;

public interface DaoTest {

    @Test
    void retrievedEntityFromDBIsNotNullTest();

    @Test
    void numberOfUsersRetrievedFromDBIsEqualToExpectedNumberTest();
}
