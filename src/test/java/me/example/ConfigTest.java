package me.example;

import static org.junit.Assert.*;

import java.io.IOException;

import me.example.Config;

import org.junit.Test;

public class ConfigTest {
	
	

	@Test
	public void testGetInstance() {
		Config config = null;
		try {
			config = Config.getInstance();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(config != null);
	
	}

	@Test
	public void testGetUrlsToMonitor() {
		
		Config config = null;
		try{
			config = Config.getInstance();
			
		}catch(IOException e){
			
		}
		
		assertTrue(config!= null && !config.asString("monitor.urls").isEmpty());
		
	}

	@Test
	public void testGetEmailsToNotify() {
		Config config = null;
		try{
			config = Config.getInstance();
			
		}catch(IOException e){
			
		}
		
		assertTrue(config!= null && !config.asString("monitor.notify").isEmpty());
	}
	
	@Test
	public void testOtherProperties() {
		Config config = null;
		try{
			config = Config.getInstance();
			
		}catch(IOException e){
			
		}
		
		assertTrue(config!= null && !config.asString("monitor.fromEmail").isEmpty());
		assertTrue(config!= null && !config.asString("monitor.emailSubject").isEmpty());
		
		
	}
	
	
	

}
