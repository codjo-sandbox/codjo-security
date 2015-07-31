package net.codjo.security.gui.plugin;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
/**
 *
 */
public class DefaultSecurityGuiConfigurationTest {
    private DefaultSecurityGuiConfiguration securityGuiConfiguration = new DefaultSecurityGuiConfiguration();


    @Test
    public void test_getConfiguration_setSplash() throws Exception {
        String splashImageUrl = "http://mydomain.com";

        securityGuiConfiguration.setSplashImageUrl("http://mydomain.com");
        assertSame(splashImageUrl, securityGuiConfiguration.getSplashImageUrl());

        try {
            securityGuiConfiguration.setSplashImageUrl(null);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("splashImageUrl ne peut pas être null", ex.getMessage());
        }
    }
}
