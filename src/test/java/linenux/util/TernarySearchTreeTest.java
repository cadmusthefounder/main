package linenux.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for Ternary Search Tree
 */
//@@author A0135788M
public class TernarySearchTreeTest {
    private TernarySearchTree tree;

    @Before
    public void setUpTree() {
        tree = new TernarySearchTree();
    }

    private void setUpData() {
        tree.addString("cats");
        tree.addString("bats");
        tree.addString("dog");
        tree.addString("crab");
        tree.addString("crow");
        tree.addString("crack");
        tree.addString("crabby");
    }

    /**
     * Test that getAllString() is never null.
     */
    @Test
    public void testGetAllStringsNotNull() {
        assertNotNull(tree.getAllStrings());
    }

    /**
     * Test that getAllStringWithPrefix() for empty string and null is empty.
     */
    @Test
    public void testInvalidString() {
        assertTrue(tree.getAllStringsWithPrefix(" ").isEmpty());
        assertTrue(tree.getAllStringsWithPrefix(null).isEmpty());
    }

    /**
     * Test that getAllStringWithPrefix() for invalid prefix is empty.
     */
    @Test
    public void testInvalidPrefix() {
        setUpData();
        assertTrue(tree.getAllStringsWithPrefix("z").isEmpty());
    }

    /**
     * Test that adding string works.
     */
    @Test
    public void testAddData() {
        tree.addString("credit");
        assertEquals("credit", tree.getAllStrings().get(0));
    }

    /**
     * Test that adding string is case insensitive.
     */
    @Test
    public void testAddDataCaseInsensitive() {
        tree.addString("crEDit");
        assertEquals("credit", tree.getAllStrings().get(0));
    }

    /**
     * Test that search for prefix is correct
     */
    @Test
    public void testSearchPrefix() {
        setUpData();
        ArrayList<String> result = tree.getAllStringsWithPrefix("cra");
        assertEquals(3, result.size());
        assertTrue(result.contains("crab"));
        assertTrue(result.contains("crabby"));
        assertTrue(result.contains("crack"));
    }
}