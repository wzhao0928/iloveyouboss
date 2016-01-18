package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class ProfileTest {

    Profile profile;

    @Before
    public void setUp() throws Exception {
        profile = new Profile("test");
    }

    @Test(expected = NullPointerException.class)
    public void matches_should_throw_exception_when_criteria_is_null() throws Exception {
        profile.matches(null);
    }

    @Test(expected = NullPointerException.class)
    public void matches_should_throw_exception_when_criterion_get_null_answer() throws Exception {
        Criteria criteria = new Criteria();
        Criterion mockCrit = mock(Criterion.class);
        criteria.add(mockCrit);
        when(mockCrit.getAnswer()).thenReturn(null);

        profile.matches(criteria);
    }

    @Test
    public void matches_should_be_false_and_set_score_to_0_when_criteria_is_empty() throws Exception {
        Criteria criteria = new Criteria();

        assertThat(profile.matches(criteria), equalTo(false));
        assertThat(profile.score(), equalTo(0));
    }
}
