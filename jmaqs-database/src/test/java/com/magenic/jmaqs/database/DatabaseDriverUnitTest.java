/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.database;

import com.magenic.jmaqs.base.BaseGenericTest;
import com.magenic.jmaqs.database.entities.StatesEntity;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DatabaseDriverUnitTest extends BaseGenericTest {

  /**
   * Field INFOMATION_SCHEMAS_QUERY
   */
  private static final String INFORMATION_SCHEMAS_QUERY = "SELECT * FROM information_schema.tables";

  /**
   * Field STATES_SELECT_QUERY
   */
  private static final String STATES_SELECT_QUERY = "SELECT * FROM States";

  /**
   * Field STATES_UPDATE_QUERY
   */
  private static final String STATES_UPDATE_QUERY = "UPDATE States SET StateAbbreviation = 'WI' WHERE StateAbbreviation = 'WI'";

  @Test(groups = TestCategories.DATABASE)
  public void testGetEntityManager() {
    final DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
    Assert.assertNotNull(openConnection.getEntityManager());
  }

  @Test(groups = TestCategories.DATABASE)
  public void testGetEntityManagerFactory() {
    final DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
    Assert.assertNotNull(openConnection.getEntityManagerFactory());
  }

  @Test(groups = TestCategories.DATABASE)
  public void testSetEntityManager() {
    DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
    final int hashCode = openConnection.getEntityManager().hashCode();
    openConnection.setEntityManager(ConnectionFactory.getEntityManagerFactory().createEntityManager());
    final int hashCode1 = openConnection.getEntityManager().hashCode();
    Assert.assertNotEquals(hashCode, hashCode1);
  }

  @Test(groups = TestCategories.DATABASE)
  public void testSetEntityManagerFactory() {
    DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
    final int hashCode = openConnection.getEntityManagerFactory().hashCode();
    openConnection.setEntityManagerFactory(ConnectionFactory.getEntityManagerFactory());
    final int hashCode1 = openConnection.getEntityManagerFactory().hashCode();
    Assert.assertNotEquals(hashCode, hashCode1);
  }

  @Test(groups = TestCategories.DATABASE)
  public void testQuery() {
    DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
    List<?> results = openConnection.query(INFORMATION_SCHEMAS_QUERY);
    Assert.assertTrue(results.stream().anyMatch(n -> ((Object[]) n)[2].equals("States")));
  }

  @Test(groups = TestCategories.DATABASE)
  public void testClose() throws Exception {
    DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
    openConnection.close();
    Assert.assertFalse(openConnection.isOpen());
  }

  @Test(groups = TestCategories.DATABASE)
  public void testExecute() {
    DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
    final int rowsUpdated = openConnection.execute(STATES_UPDATE_QUERY);
    Assert.assertEquals(rowsUpdated, 1);
  }

  @Test(groups = TestCategories.DATABASE)
  public void testTypedQuery() {
    DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
    final List<StatesEntity> queryResults = openConnection.query(STATES_SELECT_QUERY, StatesEntity.class);
    Assert.assertNotNull(queryResults);
    Assert.assertEquals(queryResults.size(), 49);
  }

  @Test(groups = TestCategories.DATABASE)
  public void testIsOpen() {
    DatabaseDriver openConnection = ConnectionFactory.getOpenConnection();
    Assert.assertTrue(openConnection.isOpen());
  }
}