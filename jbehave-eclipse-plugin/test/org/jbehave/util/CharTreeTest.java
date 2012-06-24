package org.jbehave.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.jbehave.support.JBKeyword.Given;
import static org.jbehave.support.JBKeyword.InOrderTo;
import static org.jbehave.support.JBKeyword.Narrative;

import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.support.JBKeyword;
import org.junit.Before;
import org.junit.Test;

public class CharTreeTest {
    
    private CharTree<JBKeyword> cn;

    @Before
    public void setUp () {
        LocalizedKeywords keywords = new LocalizedKeywords();
        cn = new CharTree<JBKeyword>('/', null);
        for(JBKeyword kw : JBKeyword.values())
            cn.push(kw.asString(keywords), kw);
        
    }
        
    @Test
    public void lookup() {
        assertEquals(Given, cn.lookup("Given"));
        assertEquals(Narrative, cn.lookup("Narrative:"));
        assertEquals(Given, cn.lookup("Given a user named \"Bob\""));
        assertEquals(InOrderTo, cn.lookup("In order to be more communicative"));
    }
    
    private void assertEquals(JBKeyword expected, JBKeyword actual) {
        assertThat(actual, equalTo(expected));
    }


    @Test
    public void lookup_missing() {
        assertEquals(null, cn.lookup("Gaven"));
        assertEquals(null, cn.lookup("\n"));
    }

}
