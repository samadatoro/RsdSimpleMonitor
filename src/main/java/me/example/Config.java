package me.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * Single class used to load config.properties
 * First attempts to load the config.properties from the directory the application is ran from.
 * If config.properties is not found, it will be attempt to load it from the class-path.
 * @author Samad Atoro
 *
 */

public class Config {
	
	
	 // lone instance of the Config class
	 private static Config _me = null;
	 private  Properties props = null;
	 

	 // private constructor to enforce the Singleton
	private Config(){
		 
		props = new Properties();
		 
	 }
	 
	

	// load the config.properties file from the working directory if it's exists there and from the class-path otherwise.
	 private void  loadConfig() throws IOException{
		 final String configFileName = "config.properties";
		 File configFile = new File(configFileName);
		 // if file exists, load it. If not look in the classpath
		 if(configFile.exists()){
			 loadConfig(configFile);
		 }else{
		        InputStream configStream = null;
		        // attempt to read the config file from the classpath
		        try{
		        	configStream =  this.getClass().getClassLoader()
		                                .getResourceAsStream(configFileName);
		        	loadConfig(configStream);
		        
		        }catch(IOException e){
		        	throw e;
		        }finally{
			        if(configStream != null){
			        	configStream.close();
			        }
		        }
		 }
		 
	 }
	
	// loads the properties from the input stream
	private void loadConfig(InputStream configStream) throws IOException {
	
		props.load(configStream);
	}

	// load the properties from the file
	private void loadConfig(File file) throws IOException{
		
		InputStream is = null;
		try{
			is = new FileInputStream(file);
			props.load(is);
		}catch(IOException e){
			throw e;
		}finally{
			if(is!=null){
				is.close();
			}
		}
		
	}
	 
	
	 
	/**
	 * return the Config singleton instance
	 * @return Config
	 * @throws IOException
	 */
	
	 public static Config getInstance() throws IOException{
		 
		 if(_me == null){
			 _me = new Config();
			 _me.loadConfig();
			
			 
		 }
		 return _me;
	 }


	/**
	 * returns the value for specified property empty string if the key does not exist. 
	 * @param key
	 * @return
	 */
    public String asString(String key) {
		
		return asString(key, "");
	}

	/**
	 * returns the value for specified property defaultValue if the key does not exist.
	 * @param key
	 * @param defaultValue
	 * @return String
	 */
    public  String asString(String key, String defaultValue)
    {
        if (key == null || key.isEmpty()) 
        {
        	return  ""; 
        }
        String s =  props.getProperty(key);
        
        return s != null ? s.trim() :defaultValue ;
    }
    
    /**
     * returns the value for specified property as a int or defaultValue if the value cannot be converted into an int
     * @param str
     * @return 
     */
    public int  asInt(final String key){
    	
      return asInt(key,0);
      
    }
    
    /**
     * returns the value for specified property as a int or defaultValue if the value cannot be converted into an int
     * @param str
     * @return 
     */
    public int  asInt(final String key, int defaultValue){
        try{
         return Integer.parseInt(props.getProperty(key));
        }catch(NumberFormatException ex){
        }
        return defaultValue;
    }
    
    /**
     * returns the value for specified property as a long or 0 if the value cannot be converted into a long
     * @param str
     * @return long
     */
     public  long  asLong(final String key){
      
    	 return asLong(key,0L);
    	 
    }
    
    /**
     * returns the value for specified property as a long or defaultValue if the value cannot be converted into a long
     * @param str
     * @return long
     */
     public  long  asLong(final String key, long defaultValue){
        try{
         return Long.parseLong(props.getProperty(key));
        }catch(NumberFormatException ex){
        }
        return defaultValue;
    }
     
     
     /**
      * returns the value for specified property as a float or 0 if the value cannot be converted into a float
      * @param key
      * @return 
      */
     public float  asFloat(final String key){
    	 
        return asFloat(key, 0f);
        
     }
     
    
    /**
     * returns the value for specified property as a float or defaultValue if the value cannot be converted into a float
     * @param key
     * @return float
     */
    public float  asFloat(final String key, float defaultValue){
        try{
         return Float.parseFloat(props.getProperty(key));
        }catch(NumberFormatException ex){
        }
        return defaultValue;
    }
    
    
   /**
    * returns the value for specified property as a double or 0 if the value cannot be converted into a double
    * @param key
    * @return double
    */
    public double asDouble(final String key){

    	return asDouble(key,0);
    }
    
    /**
     * returns the value for specified property as a double or defaultValue if the value cannot be converted into a double
     * @param key String
     * @param defaultvalue double
     * @return double
     */
    public double asDouble(final String key, double defaultValue){
        try{
         return Double.parseDouble(props.getProperty(key));
        }catch(NumberFormatException ex){
        }
        return defaultValue;
    }
    
    /**
     * returns the value for specified property as a boolean
     * @param key
     * @return boolean
     */
     public boolean  asBoolean(final String key){
      
         return Boolean.parseBoolean(props.getProperty(key));
       
    }

     /**
      * returns the value for specified key of null if no such key exists
      * @param key
      * @return String
      */
     public String get(String key){
    	 return props.getProperty(key);
     }
    
     /**
     * returns the value for specified key or defaultValue if no such key exists
     * @param key
     * @param defaultValue
     * @return String
     */
    public String get(String key, String defaultValue){
    	return props.getProperty(key, defaultValue);
    }
    
    

}
