package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProfileTest {

    private Profile profile;
    private Criteria criteria;
    private Criterion mockCrit1;
    private Answer mockAnswer1;

    @Before
    public void setUp() throws Exception {
        profile = new Profile("test");
        criteria = new Criteria();
        mockCrit1 = mock(Criterion.class);
        mockAnswer1 = mock(Answer.class);

        when(mockCrit1.getAnswer()).thenReturn(mockAnswer1);
        when(mockAnswer1.getQuestionText()).thenReturn("mocked answer 1");
    }

    @Test(expected = NullPointerException.class)
    public void matches_should_throw_exception_when_criteria_is_null() throws Exception {
        profile.matches(null);
    }

    @Test(expected = NullPointerException.class)
    public void matches_should_throw_exception_when_criterion_get_null_answer() throws Exception {
        criteria.add(mockCrit1);
        when(mockCrit1.getAnswer()).thenReturn(null);

        profile.matches(criteria);
    }

    @Test
    public void matches_should_be_false_and_set_score_to_0_when_criteria_is_empty() throws Exception {
        assertThat(profile.matches(criteria), equalTo(false));
        assertThat(profile.score(), equalTo(0));
    }

    @Test
    public void matches_should_be_true_and_set_score_same_to_dont_care_when_the_only_criterion_dont_care_weight() throws Exception {
        when(mockCrit1.getWeight()).thenReturn(Weight.DontCare);
        criteria.add(mockCrit1);

        boolean matched = profile.matches(criteria);

        assertThat(matched, equalTo(true));
        assertThat(profile.score(), equalTo(Weight.DontCare.getValue()));
    }

    @Test
    public void matches_should_be_true_and_set_score_same_to_criterion_weight_when_the_only_criterion_answer_matches() throws Exception {
        when(mockAnswer1.match(any(Answer.class))).thenReturn(true);
        when(mockCrit1.getWeight()).thenReturn(Weight.Important);
        profile.add(mockAnswer1);
        criteria.add(mockCrit1);

        boolean matched = profile.matches(criteria);

        assertThat(matched, equalTo(true));
        assertThat(profile.score(), equalTo(Weight.Important.getValue()));
    }

    @Test
    public void matches_should_be_false_and_set_score_to_0_when_the_only_criterion_answer_should_but_dont_match() throws Exception {
        when(mockAnswer1.match(any(Answer.class))).thenReturn(false);
        when(mockCrit1.getWeight()).thenReturn(Weight.MustMatch);
        profile.add(mockAnswer1);
        criteria.add(mockCrit1);

        boolean matched = profile.matches(criteria);

        assertThat(matched, equalTo(false));
        assertThat(profile.score(), equalTo(0));
    }

}
