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
@Table(name = "orders", schema = "main")
public class OrdersEntity {
  private Short id;
  private String orderId;
  private String orderName;
  private String productId;
  private String userId;

  @Id
  @Column(name = "Id", nullable = true)
  public Short getId() {
    return id;
  }

  public void setId(Short id) {
    this.id = id;
  }

  @Basic
  @Column(name = "OrderId", nullable = false)
  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  @Basic
  @Column(name = "OrderName", nullable = true, length = 20)
  public String getOrderName() {
    return orderName;
  }

  public void setOrderName(String orderName) {
    this.orderName = orderName;
  }

  @Basic
  @Column(name = "ProductId", nullable = false)
  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  @Basic
  @Column(name = "UserId", nullable = false)
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, orderId, orderName, productId, userId);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrdersEntity that = (OrdersEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(orderId, that.orderId) && Objects
        .equals(orderName, that.orderName) && Objects.equals(productId, that.productId) && Objects
        .equals(userId, that.userId);
  }
}
