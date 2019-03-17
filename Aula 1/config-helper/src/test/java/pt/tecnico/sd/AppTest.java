package pt.tecnico.sd;

import org.junit.*;

/**
 * Unit test for ConfigHelper.
 */
public class AppTest {

    @Test
    public void testPass()
    {
    	  // This test will always pass
        assert( true );
    }

    @Test
    public void testGetValue()
    {
    	ConfigHelper helper =new ConfigHelper();
    	//a min value always needs to be configured
    	assert( ! helper.getConfigValue("min").equals(""));
    }

    @Test
    public void testFail()
    {
    	// This test will always if the following line is uncommented
    	//assert(false) ;
    }

}
