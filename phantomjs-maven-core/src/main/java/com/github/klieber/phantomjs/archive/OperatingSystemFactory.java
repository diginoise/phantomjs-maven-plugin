package com.github.klieber.phantomjs.archive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class OperatingSystemFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(OperatingSystemFactory.class);

  private static final String OS_NAME = "os.name";
  private static final String OS_ARCH = "os.arch";
  private static final String OS_VERSION = "os.version";
  private static final String DISTRIBUTION_NAME = "NAME";
  private static final String DISTRIBUTION_VERSION_ID = "VERSION_ID";
  private static final String OS_RELEASE_PROPERTIES_FILE = "/etc/os-release";

  public OperatingSystem create() {
    String name = System.getProperty(OS_NAME).toLowerCase();
    String architecture = System.getProperty(OS_ARCH).toLowerCase();
    String version = System.getProperty(OS_VERSION).toLowerCase();

    OperatingSystem operatingSystem;
    if (isLinux(name)) {
      Properties linuxProperties = getLinuxProperties();
      String distribution = linuxProperties.getProperty(DISTRIBUTION_NAME);
      String distributionVersion = linuxProperties.getProperty(DISTRIBUTION_VERSION_ID);
      operatingSystem = new OperatingSystem(
          name,
          architecture,
          version,
          distribution,
          distributionVersion
      );
    } else {
      operatingSystem = new OperatingSystem(
          name,
          architecture,
          version
      );
    }

    return operatingSystem;
  }

  private boolean isLinux(String name) {
    return name.contains("nux");
  }

  private Properties getLinuxProperties() {
    Properties properties = new Properties();
    try {
      File propertiesFile = new File(OS_RELEASE_PROPERTIES_FILE);
      if (propertiesFile.exists() && propertiesFile.canRead()) {
        FileInputStream in = new FileInputStream(propertiesFile);
        properties.load(in);
        in.close();
      }
    } catch (IOException e) {
      LOGGER.trace("unable to read linux os properties", e);
    }
    return properties;
  }
}
