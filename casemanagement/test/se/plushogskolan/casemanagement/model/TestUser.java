package se.plushogskolan.casemanagement.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public final class TestUser {
    int defaultId = 1001;
    String defaultUsername = "Mr. Youre Face";
    private User twinUser1;
    private User twinUser2;
    private User brokenUser;
    
    @Before
    public void setUp() throws Exception {
        twinUser1 = new User.UserBuilder().build(defaultId, defaultUsername);
        twinUser2 = new User.UserBuilder().build(defaultId, defaultUsername);
    }

    @Test
    public void testUserEquals() {
        brokenUser =  new User.UserBuilder().build(defaultId, "Another username");
        
        assertEquals(twinUser1, twinUser2);
        
        assertNotEquals(twinUser1, brokenUser);
    }

    @Test
    public void testUserHashCode() {
        int anotherId = 1002;
        brokenUser =  new User.UserBuilder().build(anotherId, defaultUsername);
        
        assertEquals(twinUser1.hashCode(), twinUser2.hashCode());
        
        assertNotEquals(twinUser1.hashCode(), brokenUser.hashCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserIdRequirements() {
        int negativeId = -1;
        brokenUser =  new User.UserBuilder().build(negativeId, defaultUsername);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserNameRequirements() {
        String toShortUsername = "<10char";
        brokenUser =  new User.UserBuilder().build(defaultId, toShortUsername);
    }
}
