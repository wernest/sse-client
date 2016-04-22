package com.will;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class Main {
  public static String updateURL = "http://localhost:8080/server-sent-events/updates";

  public static void main(String[] args){
    try {
      Client client = ClientBuilder.newClient();
      WebTarget webTarget = client.target(updateURL);

      Response response = webTarget.request().get();

      final String subscriptionId = response.readEntity(String.class);

      ServerConnectionThread serverConnectionThread = new ServerConnectionThread(updateURL + "/" + subscriptionId);

      new Thread() {
        public void run() {
          try {
            int x = 0;
            while (true) {
              SampleObject sampleObject = new SampleObject();
              sampleObject.setName("Will");
              sampleObject.setAge(x++);
              sampleObject.setGreeting("Hello World");
              Gson gson = new GsonBuilder().create();
              WebTarget webResource = ClientBuilder.newClient().target("http://localhost:8080/rest-event/send/" + subscriptionId);
              Invocation.Builder builder = webResource.request(MediaType.APPLICATION_JSON);
              builder.post(Entity.entity(gson.toJson(sampleObject, SampleObject.class), MediaType.APPLICATION_JSON));
              Thread.sleep(1000);
            }
          } catch (InterruptedException e) {
            System.out.println(e.toString());
          }
        }
      }.start();
    }catch (Exception e) {

    }
  }
}
