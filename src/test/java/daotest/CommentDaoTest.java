package daotest;

import com.example.httplearningapi.model.dao.CommentDao;
import com.example.httplearningapi.model.dao.Dao;
import com.example.httplearningapi.model.entities.comment.Comment;
import extensions.HibernateUtilSetupExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(HibernateUtilSetupExtension.class)
public class CommentDaoTest implements DaoTest {

    private static final int NUMBER_OF_COMMENTS_IN_DATABASE = 500;
    private static final Dao<Comment> COMMENT_DAO = new CommentDao();

    @Test
    @Override
    public void retrievedEntityFromDBIsNotNullTest() {
        Optional<Comment> testCommentOptional = COMMENT_DAO.getById(1);
        if (testCommentOptional.isEmpty()) {
            fail("Comment is not retrieved from Database");
        }

        Comment testComment = testCommentOptional.get();
        assertTrue(testComment.getPost() != null &&
                testComment.getContent() != null, "Comment fields are null");
    }

    @Test
    @Override
    public void numberOfEntitiesRetrievedFromDBIsEqualToExpectedNumberTest() {
        List<Comment> prescriptions = COMMENT_DAO.getAll();
        assertEquals(NUMBER_OF_COMMENTS_IN_DATABASE, prescriptions.size(),
                "Actual number of Comments in Database is not equal to Expected number");
    }
}
