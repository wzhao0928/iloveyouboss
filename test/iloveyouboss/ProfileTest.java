package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class ProfileTest {

    Profile profile;
    Criteria criteria;

    @Before
    public void setUp() throws Exception {
        profile = new Profile("test");
        criteria = new Criteria();
    }

    @Test(expected = NullPointerException.class)
    public void matches_should_throw_exception_when_criteria_is_null() throws Exception {
        profile.matches(null);
    }

    @Test(expected = NullPointerException.class)
    public void matches_should_throw_exception_when_criterion_get_null_answer() throws Exception {
        Criterion mockCrit = mock(Criterion.class);
        criteria.add(mockCrit);
        when(mockCrit.getAnswer()).thenReturn(null);

        profile.matches(criteria);
    }

    @Test
    public void matches_should_be_false_and_set_score_to_0_when_criteria_is_empty() throws Exception {
        assertThat(profile.matches(criteria), equalTo(false));
        assertThat(profile.score(), equalTo(0));
    }

    @Test
    public void matches_should_be_true_and_set_score_same_to_dont_care_when_the_only_criterion_dont_care_weight() throws Exception {
        Criterion mockCrit = mock(Criterion.class);
        Answer mockAnswer = mock(Answer.class);
        when(mockCrit.getWeight()).thenReturn(Weight.DontCare);
        when(mockAnswer.getQuestionText()).thenReturn("mocked answer");
        when(mockCrit.getAnswer()).thenReturn(mockAnswer);
        criteria.add(mockCrit);

        boolean matched = profile.matches(criteria);

        assertThat(matched, equalTo(true));
        assertThat(profile.score(), equalTo(Weight.DontCare.getValue()));
    }
}
