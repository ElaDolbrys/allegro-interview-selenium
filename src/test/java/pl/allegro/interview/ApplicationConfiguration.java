package pl.allegro.interview;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;


public class ApplicationConfiguration implements Serializable {

    private static final String PROPERTIES_FILE_LOCATION = "/processed/env.properties";

    private static final String APPLICATION_URL = "selenium.url";

    private static Properties properties = null;

    static {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\elzbieta.dobrzynska\\AppData\\Local\\Mozilla Firefox\\geckodriver.exe");
        properties = new Properties();
        try {
            properties.load(ApplicationConfiguration.class.getResourceAsStream(PROPERTIES_FILE_LOCATION));

        } catch (IOException e) {
            System.out.println("error loading configuration from file: " + PROPERTIES_FILE_LOCATION);
            e.printStackTrace();
        }
    }

    public static String getApplicationUrl() {
        return properties.getProperty(APPLICATION_URL);
    }


}
