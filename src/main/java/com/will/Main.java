package com.will;

public class Main {

  public static void main(String[] args) {
    JenmRestConnectorImpl jenmRestConnector = new JenmRestConnectorImpl();

    Thread thread = new Thread(() -> {
      try {
        int counter = 0;
        while (true) {
          SampleObject sam = new SampleObject();
          sam.setAge(counter++);
          sam.setName("Will");
          sam.setGreeting("Hello World");
          jenmRestConnector.sendObject("1326", sam);
          Thread.sleep(500);
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    thread.run();
  }
}
