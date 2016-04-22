package com.will;

import org.glassfish.jersey.media.sse.*;

import javax.ws.rs.client.*;

public class ServerConnectionThread extends Thread{

  private boolean isRunning = false;
  private String url = "";

  ServerConnectionThread(String url){
    this.start();
    this.isRunning = true;
    this.url = url;
  }

  @Override
  public void run() {
    EventSource eventSource = null;
    try {
      Client client = ClientBuilder.newBuilder().register(SseFeature.class).build();
      WebTarget target = client.target(url);
      eventSource = new EventSource(target) {
        @Override public void onEvent(InboundEvent inboundEvent) {
          if (SampleObject.class.getSimpleName().equals(inboundEvent.getName())) {
            System.out.println(inboundEvent.getName() + "; " + inboundEvent.readData(String.class));
          }
        }
      };

      while (isRunning) {
        if (Thread.interrupted()) {
          eventSource.close();
          System.out.println("closed");
          WebTarget webResource = ClientBuilder.newClient().target(url);
          Invocation.Builder builder = webResource.request();
          builder.delete();
        }
      }
    } catch(Exception e) {
      System.out.println("Exception occurred" + e.toString());
    } finally {
      if (eventSource != null) {
        eventSource.close();
      }
    }

  }
}
