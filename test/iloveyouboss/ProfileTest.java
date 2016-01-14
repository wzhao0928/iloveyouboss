package iloveyouboss;

import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * Created by wbzhao on 1/14/16.
 */
public class ProfileTest {

    @Test(expected = NullPointerException.class)
    public void matches_should_throw_exception_when_criteria_is_null() throws Exception {
        Profile profile = new Profile("test");
        profile.matches(null);
    }

    @Test(expected = NullPointerException.class)
    public void matches_should_throw_exception_when_criterion_get_null_answer() throws Exception {
        Profile profile = new Profile("test");
        Criteria criteria = new Criteria();
        Criterion mockCrit = mock(Criterion.class);
        criteria.add(mockCrit);
        when(mockCrit.getAnswer()).thenReturn(null);

        profile.matches(criteria);
    }
}
