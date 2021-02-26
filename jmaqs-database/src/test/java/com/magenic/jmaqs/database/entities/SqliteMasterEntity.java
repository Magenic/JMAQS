/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.database.entities;

import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sqlite_master", schema = "main")
public class SqliteMasterEntity {

  @Id
  private Long id;
  private String type;
  private String name;
  private String tblName;
  private String rootpage;
  private String sql;

  @Basic
  @Column(name = "type", nullable = true, length = -1)
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @Basic
  @Column(name = "name", nullable = true, length = -1)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Basic
  @Column(name = "tbl_name", nullable = true, length = -1)
  public String getTblName() {
    return tblName;
  }

  public void setTblName(String tblName) {
    this.tblName = tblName;
  }

  @Basic
  @Column(name = "rootpage", nullable = true)
  public Object getRootpage() {
    return rootpage;
  }

  public void setRootpage(String rootpage) {
    this.rootpage = rootpage;
  }

  @Basic
  @Column(name = "sql", nullable = true, length = -1)
  public String getSql() {
    return sql;
  }

  public void setSql(String sql) {
    this.sql = sql;
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, name, tblName, rootpage, sql);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqliteMasterEntity that = (SqliteMasterEntity) o;
    return Objects.equals(type, that.type) && Objects.equals(name, that.name) && Objects
        .equals(tblName, that.tblName) && Objects.equals(rootpage, that.rootpage) && Objects
        .equals(sql, that.sql);
  }
}
