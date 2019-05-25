package com.jz.java.designpattern.prototype;

public class WorkExperience implements Cloneable{
  private String startTime;

  private String endTime;

  private String company;

  WorkExperience(String startTime, String endTime, String company) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.company = company;
  }

  @Override
  public String toString() {
    return "[StartTime : " + startTime + ": " + ", EndTime: " + endTime + " , " +
        company + "]";
  }

  public WorkExperience clone() {
    WorkExperience workExperience = null;
    try {
      workExperience =  (WorkExperience) super.clone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }

    return workExperience;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public void setCompany(String company) {
    this.company = company;
  }
}
