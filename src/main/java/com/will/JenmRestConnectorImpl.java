package com.will;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * Created by wernest on 5/4/2016.
 */
public class JenmRestConnectorImpl {

  //Singleton
  private static JenmRestConnectorImpl instance;
  public static final String updateURL = "https://localhost:8443/jenm-updates/subscribe";
  private final  ServerConnectionThread serverConnectionThread;
  public final String subscriptionId;

  JenmRestConnectorImpl(){
    this.subscriptionId = ClientBuilder.newClient().target(updateURL).request().get().readEntity(String.class);
    this.serverConnectionThread = new ServerConnectionThread(updateURL + "/" + subscriptionId);
  }

  public static JenmRestConnectorImpl getInstance(){
    if(instance == null) {
      instance = new JenmRestConnectorImpl();
    }
    return instance;
  }

  public void saveSampleObject(SampleObject data){
    Client client = ClientBuilder.newClient();
    WebTarget webTarget = client.target("https://localhost:8443/jenm-updates/" + subscriptionId);
    webTarget.request().post(Entity.entity(data, MediaType.APPLICATION_JSON_TYPE), SampleObject.class);
  }

}
