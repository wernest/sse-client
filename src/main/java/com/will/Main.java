package com.will;

/**
 * Created by wernest on 4/21/2016.
 */
public class Main {

  public static void main(String[] args){
    try {
      ServerConnectionThread serverConnectionThread = new ServerConnectionThread(
          "http://localhost:8080/server-sent-events/updates/1");
    }catch (Exception e) {

    }
  }
}
