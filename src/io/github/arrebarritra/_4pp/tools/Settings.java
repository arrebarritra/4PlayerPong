package io.github.arrebarritra._4pp.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Accesses and modifies game settings
 */
public class Settings {

    private static Properties props;
    private static File path = new File("res" + File.separator + "settings" + File.separator + "app.properties");

    public Settings() {
        props = new Properties();
        try {
            props.load(new FileInputStream(path));
        } catch (IOException ex) {
        }
    }

    /**
     * 
     * Get setting
     * @param propName Property key 
     * @return Value of the property with matching key
     */
    public static String getProp(String propName) {
        return props.getProperty(propName);
    }

    /**
     * 
     * Change setting
     * @param propName Property key to be changed
     * @param propValue Value of property to be changed
     */
    public static void setProp(String propName, String propValue) {
        props.setProperty(propName, propValue);        
    }
    
    /**
     * Saves properties to .properties file
     */
    public void save(){
        try {
            props.store(new FileOutputStream(path), null);
        } catch (IOException ex) {
        }
    }
}
