package com.jz.java.designpattern.builder;

public class ResourceConfig {

  private String name;
  private int maxTotal;
  private int maxIdle;
  private int minIdle;


  private ResourceConfig(Builder builder) {
    this.name = builder.name;
    this.maxTotal = builder.maxTotal;
    this.maxIdle = builder.maxIdle;
    this.minIdle = builder.minIdle;
  }

  public static class Builder {

    private static final String DEFAULT_NAME = "DEFAULT";
    private static final int DEFAULT_MAX_TOTAL = 8;
    private static final int DEFAULT_MAX_IDLE = 8;
    private static final int DEFAULT_MIN_IDLE = 0;

    private String name = DEFAULT_NAME;
    private int maxTotal = DEFAULT_MAX_TOTAL ;
    private int maxIdle = DEFAULT_MAX_IDLE;
    private int minIdle = DEFAULT_MIN_IDLE;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder maxTotal(int maxTotal) {
      this.maxTotal = maxTotal;
      return this;
    }

    public Builder maxIdle(int maxIdle) {
      this.maxIdle = maxIdle;
      return this;
    }

    public Builder minIdle(int minIdle) {
      this.minIdle = minIdle;
      return this;
    }

    public ResourceConfig build() throws Exception {
      if (name == null || name.length() == 0) {
        throw new Exception("name cannot be empty");
      }

      if (maxTotal <= 0 || minIdle < 0 || maxIdle <= 0 || maxIdle > maxTotal) {
        throw new IllegalArgumentException("Parameters are wrong.");
      }

      return new ResourceConfig(this);
    }
  }

  public static void main(String[] args) throws Exception {
    ResourceConfig resourceConfig  = new ResourceConfig.Builder().name("abc").maxTotal(10).maxIdle(10).minIdle(0).build();
  }
}

