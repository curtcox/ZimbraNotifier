package zimbra;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZimbraEmailTest {

    @Test
    public void old_instances_are_not_equal_to_new_instances(){
        String author = "author";
        String title = "title";
        long date = 99999;
        String description = "desc";
        ZimbraEmail email = ZimbraEmail.of(author,title,date,description);
        assertFalse(email.equals(email.withOld()));
    }

    @Test
    public void equal_instances_are_equal(){
        String author = "author";
        String title = "title";
        long date = 99999;
        String description = "desc";
        assertEquals(
                ZimbraEmail.of(author,title,date,description),
                ZimbraEmail.of(author,title,date,description)
        );
    }

    @Test
    public void instances_are_not_equal_to_stack_trace_elements(){
        String author = "author";
        String title = "title";
        long date = 99999;
        String description = "desc";
        assertFalse(
                ZimbraEmail.of(author,title,date,description).equals(
                new StackTraceElement("declaring class","method","file",0))
        );
    }

    @Test
    public void equal_instances_have_same_hash_code(){
        String author = "author";
        String title = "title";
        long date = 99999;
        String description = "desc";
        assertEquals(
                ZimbraEmail.of(author,title,date,description).hashCode(),
                ZimbraEmail.of(author,title,date,description).hashCode()
        );
    }

    @Test
    public void equal_instances_are_same(){
        String author = "author";
        String title = "title";
        long date = 99999;
        String description = "desc";
        assertTrue(
                ZimbraEmail.of(author,title,date,description).isSameAs(
                ZimbraEmail.of(author,title,date,description))
        );
    }

    @Test
    public void old_email_is_same_as_email(){
        String author = "author";
        String title = "title";
        long date = 99999;
        String description = "desc";
        ZimbraEmail email =ZimbraEmail.of(author,title,date,description); 
        assertTrue(email.isSameAs(email.withOld()));
    }

}
