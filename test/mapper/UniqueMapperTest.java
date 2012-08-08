package mapper;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import strings.ForkedString;

/**
 *
 * @author Curt
 */
public class UniqueMapperTest {

    final StringMapper mapper = UniqueMapper.of();

    @Test
    public void mapper_returns_given_list_when_no_duplicates() {
        List<String> withDups = list("1","2");
        ForkedString actual = mapper.transform(ForkedString.fromList(String.class,withDups));
        assertEquals(list("1","2"),actual.parts);
    }

    @Test
    public void mapper_eliminates_duplicates() {
        List<String> withDups = list("1","2","1","2");
        ForkedString actual = mapper.transform(ForkedString.fromList(String.class,withDups));
        assertEquals(list("1","2"),actual.parts);
    }

    private List<String> list(String... strings) {
        return Arrays.asList(strings);
    }
}
