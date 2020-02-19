/*
 * Copyright 2019 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.database;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class DatabaseDriver implements AutoCloseable {

  private EntityManager entityManager;
  private EntityManagerFactory entityManagerFactory;

  public DatabaseDriver(EntityManager entityManager) {
    this.entityManager = entityManager;
    this.entityManagerFactory = entityManager.getEntityManagerFactory();
  }

  public DatabaseDriver(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
    this.entityManager = entityManagerFactory.createEntityManager();
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public EntityManagerFactory getEntityManagerFactory() {
    return entityManagerFactory;
  }

  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  public List query(String query) {
    return this.entityManager.createNativeQuery(query).getResultList();
  }

  public <T> List<T> query(String query, Class<T> resultClass) {
    return this.entityManager.createNativeQuery(query, resultClass).getResultList();
  }

  public int execute(String query) {
    final EntityTransaction transaction = this.getEntityManager().getTransaction();
    transaction.begin();
    final int i = this.entityManager.createNativeQuery(query).executeUpdate();
    transaction.commit();
    return i;
  }


  @Override
  public void close() throws Exception {
    this.entityManager.close();
    this.entityManagerFactory.close();
  }

  /**
   * Check is EntityManager and EntityManagerFactory are open.
   *
   * @return boolean of whether entityManager and entityManagerFactory are open
   */
  public boolean isOpen() {
    return this.entityManager.isOpen() || this.entityManagerFactory.isOpen();
  }
}
