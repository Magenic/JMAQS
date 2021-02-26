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
@Table(name = "products", schema = "main")
public class ProductsEntity {
  private Short id;
  private String productName;

  @Id
  @Column(name = "Id", nullable = true)
  public Short getId() {
    return id;
  }

  public void setId(Short id) {
    this.id = id;
  }

  @Basic
  @Column(name = "ProductName", nullable = true, length = 20)
  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, productName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductsEntity that = (ProductsEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(productName, that.productName);
  }
}
