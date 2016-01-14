package iloveyouboss;

import org.junit.Test;

/**
 * Created by wbzhao on 1/14/16.
 */
public class ProfileTest {

    @Test(expected = NullPointerException.class)
    public void matches_should_throw_exception_when_criteria_is_null() throws Exception {
        Profile profile = new Profile("test");
        profile.matches(null);
    }
}
