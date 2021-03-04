package com.academy.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyProvider {
    private static Logger LOG = LoggerFactory.getLogger(PropertyProvider.class);

    private static final String CUSTOM_CFG_KEY = "cfg";
    private static final String DEFAULT_PROP_FILE = "selenium.properties";
    private static Properties prop;

    // блок статической инициализации
    static {
        init();
    }

    private static void init() {
        try {
            if (customPropSpecified()) {
                loadCustom();
            } else {
                loadDefault();
            }
        } catch (IOException | RuntimeException e) {
            LOG.error("Error prop initialization. Details: {}", e.getMessage());
        }
    }

    private static boolean customPropSpecified() {
        return System.getProperty(CUSTOM_CFG_KEY) != null;
    }

    private static void loadDefault() throws IOException {
        prop = new Properties();
        InputStream is = PropertyProvider.class.getClassLoader().getResourceAsStream(DEFAULT_PROP_FILE);
        prop.load(is); // загружаем физический файл с пропертями
    }

    private static void loadCustom() throws IOException {
        prop = new Properties();
        String path = System.getProperty(CUSTOM_CFG_KEY);
        prop.load(new FileInputStream(path));
    }

    // метод отдает проперти по ключу
    public static String get(String key) {
       return prop.getProperty(key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(prop.getProperty(key));
    }
}
