package com.magenic.jmaqs.webservices.jdk11.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Product {

  private int id;

  private String name;

  private String category;

  private BigDecimal price;

  public Product(int id, String name, String category, BigDecimal price) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.price = price;
  }

  public Product() { }

  @JsonProperty("Id")
  public int getId() {
      return id;
    }

  @JsonProperty("Id")
  public void setId(int id) {
      this.id = id;
    }

  @JsonProperty("Name")
  public String getName() {
      return name;
    }

  @JsonProperty("Name")
  public void setName(String name) {
      this.name = name;
    }

  @JsonProperty("Category")
  public String getCategory() {
      return category;
    }

  @JsonProperty("Category")
  public void setCategory(String category) {
      this.category = category;
    }

  @JsonProperty("Price")
  public BigDecimal getPrice() {
    return price;
  }

  @JsonProperty("Price")
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder(20);
    sb.append(String.format("%s:%d\n", "Id", this.getId()));
    sb.append(String.format("%s:%s\n", "Name", this.getName()));
    sb.append(String.format("%s:%s\n", "Category", this.getCategory()));
    sb.append(String.format("%s:%d\n", "Price", this.getPrice()));

    return sb.toString();
  }
}
