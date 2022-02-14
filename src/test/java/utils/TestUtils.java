package utils;

import models.Message;
import models.Topic;
import subscriber.Subscriber;
import subscriber.SubscriberImpl;

public class TestUtils {

    public static final String QUEUE_NAME = "MessageQueue";

    public static final Message message = new Message("TestMessage", new Topic("Topic1"));

    public static final Topic TOPIC = new Topic("TEST_TOPIC");

    public static final Topic TOPIC1 = new Topic("TEST_TOPIC_1");

    public static final Message message1 = new Message("TestMessage1", TOPIC);

    public static final Message message2 = new Message("TestMessage2", TOPIC1);

    public static final Subscriber subscriber = new SubscriberImpl("http://localhost/test");

    public static final Subscriber subscriber2 = new SubscriberImpl("http://localhost/test");

}
