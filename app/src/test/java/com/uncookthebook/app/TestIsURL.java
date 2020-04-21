package com.uncookthebook.app;

import org.junit.Test;


import static org.junit.Assert.*;
import static com.uncookthebook.app.Utils.*;

public class TestIsURL {

    @Test
    public void testIsURL() {
        assertFalse(isURL("https//github.com/A/"));
        assertFalse(isURL("https:/github.com/A/"));
        assertFalse(isURL("https://git;ub.com/A/"));
        assertFalse(isURL("https://git;ub.com/^"));

        assertTrue(isURL("https://github.com/A/"));
        assertTrue(isURL("http://github.com/A/"));
        assertTrue(isURL("http://github.com"));
        assertTrue(isURL("http://github.com/"));
        assertTrue(isURL("https://www.google.com/search?q=asdffdsafd&oq=asdffdsafd"));
    }
}
