package subscriber;

import services.PubSubService;
import models.Topic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class SubscriberImpl implements Subscriber {

    private final String endpoint;

    public SubscriberImpl(final String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void addSubscriber(final Topic topic) {
        PubSubService.getInstance().subscribeToTopic(topic, this);
    }

    @Override
    public void addSubscriber(Topic... topics) {
        Arrays.stream(topics).forEach(
                topic -> PubSubService.getInstance().subscribeToTopic(topic, this));
    }

    @Override
    public void removeSubscriber(final Topic topic) {
        PubSubService.getInstance().unsubscribeFromTopic(topic, this);
    }

    @Override
    public void receiveMessageFromTopic(final Topic topic, final String payload) {
        System.out.println("Received message from topic " + topic.getTopicName() + " ,message " + payload);
        try {
            onMessageReceive(payload);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    /**
     * Invoke API endpoint registered with current subscriber
     * @param payload
     * @throws IOException
     */
    private void onMessageReceive(final String payload) throws IOException {

        System.out.println("Invoking enpoint " + this.endpoint);


        final URL url = new URL(this.endpoint);
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.setConnectTimeout(1000);

        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = payload.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response);
        }

        conn.disconnect();
    }
}
