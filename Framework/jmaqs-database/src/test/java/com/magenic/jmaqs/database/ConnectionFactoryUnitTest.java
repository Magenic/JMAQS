/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.database;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ConnectionFactoryUnitTest extends BaseGenericTest {

  @Test(groups = TestCategories.Database)
  public void testGetEntityManagerFactory() {
    final EntityManagerFactory entityManagerFactory = ConnectionFactory.getEntityManagerFactory();
    Assert.assertNotNull(entityManagerFactory);
  }

  @Test(groups = TestCategories.Database)
  public void testGetProperties() {
    final Properties properties = ConnectionFactory.getProperties();
    Assert.assertNotNull(properties);

  }

  @Test(groups = TestCategories.Database)
  public void testGetEntityClassNames() throws ClassNotFoundException, IOException {
    final List<String> entityClassNames = ConnectionFactory.getEntityClassNames();
    SoftAssert softAssert = new SoftAssert();
    final File file = new File(DatabaseConfig.getEntityDirectoryString());
    try (URLClassLoader classLoader = URLClassLoader
        .newInstance(new URL[] { new URL("file:///" + file.getCanonicalPath()) })) {
      for (String entityClassName : entityClassNames) {
        final Class<?> aClass = classLoader.loadClass(entityClassName);
        softAssert.assertNotNull(aClass, "Checking that class loads properly");
        softAssert
            .assertEquals(aClass.getPackage().getName(), DatabaseConfig.getEntityPackageString(),
                "Checking that package is the same");
      }
    }
    softAssert.assertAll();
  }

  @Test(groups = TestCategories.Database)
  public void testGetEntityFiles() {
    SoftAssert softAssert = new SoftAssert();
    final File[] entityFiles = ConnectionFactory.getEntityFiles();
    softAssert.assertEquals(entityFiles.length, 5);
    for (File entityFile : entityFiles) {
      softAssert.assertTrue(entityFile.exists(), "Checking if file exists");
      softAssert.assertTrue(entityFile.isFile(), "Checking that file is indeed a file");
    }
    softAssert.assertAll();
  }
}