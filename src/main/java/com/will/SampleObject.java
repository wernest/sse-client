package com.will;


import java.io.Serializable;

public class SampleObject implements Serializable{

  private String name;
  private int age;
  private String greeting;

  public SampleObject() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getGreeting() {
    return greeting;
  }

  public void setGreeting(String greeting) {
    this.greeting = greeting;
  }


}
