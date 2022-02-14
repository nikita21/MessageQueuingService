package services;

import utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PubSubServiceTest {

    private PubSubService service;

    @Before
    public void setUp() throws Exception {
        service = PubSubService.getInstance();
    }

    @Test
    public void test_addMessageToQueue() {
        service.addMessageToQueue(TestUtils.message, TestUtils.QUEUE_NAME);
        assertEquals(1, service.getMessageQueues().size());
        assertTrue(service.getMessageQueues().keySet().contains(TestUtils.QUEUE_NAME));
    }

    @Test
    public void test_subscribeToTopic() {
        service.subscribeToTopic(TestUtils.TOPIC, TestUtils.subscriber);
        service.subscribeToTopic(TestUtils.TOPIC1, TestUtils.subscriber);

        assertEquals(2, service.getSubscribers().size());
    }

    @Test
    public void test_unsubscribeFromTopic() {
        service.subscribeToTopic(TestUtils.TOPIC, TestUtils.subscriber2);
        assertEquals(2, service.getSubscribers().size());

        service.unsubscribeFromTopic(TestUtils.TOPIC1, TestUtils.subscriber);
        assertEquals(0, service.getSubscribers().get(TestUtils.TOPIC1).size());

        service.unsubscribeFromTopic(TestUtils.TOPIC, TestUtils.subscriber);
        assertEquals(1, service.getSubscribers().get(TestUtils.TOPIC).size());
    }
}
