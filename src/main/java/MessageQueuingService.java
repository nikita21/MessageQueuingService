import models.Message;
import models.Topic;
import publisher.Publisher;
import publisher.PublisherImpl;
import services.PubSubService;
import subscriber.Subscriber;
import subscriber.SubscriberImpl;

import static java.lang.Runtime.getRuntime;

public class MessageQueuingService {

    public static void main(String[] args) {

        // create topics
        final Topic topic1 = new Topic("topic1");
        final Topic topic2 = new Topic("topic2");

        createSubscribers(topic1, topic2);

        // create messages
        final Message message1 = new Message("TEST_MESSAGE_1", topic1);
        final Message message2 = new Message("TEST_MESSAGE_2", topic2);

        // creating publishers
        final Publisher publisher1 = new PublisherImpl("Queue1");
        final Publisher publisher2 = new PublisherImpl("Queue2");

        publisher1.publishMessage(message1);
        publisher2.publishMessage(message2);

        PubSubService.getInstance().broadcastMessageToSubscribers();

        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getRuntime().addShutdownHook(new Thread(() -> {
            PubSubService.getInstance().stop();
        }));
    }

    private static void createSubscribers(Topic topic1, Topic topic2) {
        Subscriber subscriber1 = new SubscriberImpl("http://localhost/subscriber1/process-message");
        subscriber1.addSubscriber(topic1, topic2);

        System.out.println("Subscriber1 is subscribed to Topic1 and Topic2");

        Subscriber subscriber2 = new SubscriberImpl("http://localhost/subscriber2/process-message");
        subscriber2.addSubscriber(topic2);

        System.out.println("Subscriber2 is subscribed to Topic2");
    }
}
