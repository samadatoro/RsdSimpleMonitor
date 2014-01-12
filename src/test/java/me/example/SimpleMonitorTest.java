package me.example;

import static org.junit.Assert.*;
import me.example.SimpleMonitor;

import org.junit.Test;

public class SimpleMonitorTest {

	@Test
	public void testPingUrl1() {
		
		String url = "www.yahoo.com";
		
		SimpleMonitor m = new SimpleMonitor();
		
		boolean res = m.pingUrl(url);
		
		assertTrue(res);
		
	}

	@Test
	public void testPingUrl2() {
		
		String url = "https://www.yahoo.com";
		
		SimpleMonitor m = new SimpleMonitor();
		
		boolean res = m.pingUrl(url);
		
		assertTrue(res);
		
	}
	
	@Test
	public void testPingUrl3() {
		
		String url = "https://www.yahoo.com/fail";
		
		SimpleMonitor m = new SimpleMonitor();
		
		boolean res = m.pingUrl(url);
		
		assertTrue(!res);
		
	}
	
	@Test
	public void testEmail() {
		
		String url = "https://www.yahoo.com/fail";
		
		SimpleMonitor m = new SimpleMonitor();
		
		boolean res = m.pingUrl(url);
		
		assertTrue(!res);
		
	}
}
