/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.database.entities;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users", schema = "main")
public class UsersEntity {
  private Short id;
  private String firstName;
  private String lastName;

  @Id
  @Column(name = "Id", nullable = true)
  public Short getId() {
    return id;
  }

  public void setId(Short id) {
    this.id = id;
  }

  @Basic
  @Column(name = "FirstName", nullable = false, length = 20)
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Basic
  @Column(name = "LastName", nullable = false, length = 20)
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UsersEntity that = (UsersEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects
        .equals(lastName, that.lastName);
  }
}
