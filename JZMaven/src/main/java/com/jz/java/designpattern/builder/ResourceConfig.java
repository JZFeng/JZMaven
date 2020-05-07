package com.jz.java.designpattern.builder;

public class ResourceConfig {

  private String name;

  private int maxTotal;

  private int minIdle;

  private int maxIdle;

  private ResourceConfig(Builder builder) {
    this.name = builder.name;
    this.maxTotal = builder.maxTotal;
    this.minIdle = builder.minIdle;
    this.maxIdle = builder.maxIdle;
  }


  public static class Builder {
    private String name;

    private int maxTotal;

    private int minIdle;

    private int maxIdle;


    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    Builder setMaxTotal(int maxTotal) {
      this.maxTotal = maxTotal;
      return this;
    }

    Builder setMinIdle(int minIdle) {
      this.minIdle = minIdle;
      return this;
    }

    Builder setMaxIdle(int maxIdle) {
      this.maxIdle = maxIdle;
      return this;
    }

    ResourceConfig build() {
      if (name == null || name.equals("")) {
        throw new IllegalArgumentException("name cannot be null");
      }
      if (maxIdle > maxTotal) {
        throw new IllegalArgumentException("maxIdle cannot be greater than maxTotal");
      }
      if (minIdle > maxTotal || minIdle > maxIdle) {
        throw new IllegalArgumentException("minIdle cannot be greater than maxTotal or maxIdle");
      }

      return new ResourceConfig(this);
    }
  }

  public static void main(String[] args) {
    ResourceConfig resourceConfig = new ResourceConfig.Builder()
        .setMaxIdle(8)
        .setMaxTotal(10)
        .setMinIdle(5)
        .setName("DB config").build();
  }
}
