package com.will;


import org.glassfish.jersey.media.sse.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.function.Consumer;


public class Main {

    public static void main(String[] args){
        try {
            consumeEventStream("http://localhost:8080/server-sent-events/domains/1");
        }catch(Exception e) {
            System.out.println("Exception has occured");
        }
    }

    public static void consumeEventStream(String url) throws Exception {
        Client client = ClientBuilder.newBuilder().register(new SseFeature()).build();
        WebTarget target = client.target(url);
        EventInput e = null;
        while (true) {
            Thread.sleep(1000);
            if (e==null || e.isClosed()) {
                // (re)connect
                e = target.request().get(EventInput.class);

                e.setChunkType("text/event-stream");
            }

            final InboundEvent inboundEvent = e.read();
            if (inboundEvent == null) {
                break;
            }
            else {
                String data = inboundEvent.readData();
                System.out.println(data);
            }

        }
        System.out.println("connection closed");
    }

}
