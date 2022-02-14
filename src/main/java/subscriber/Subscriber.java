package subscriber;

import models.Topic;

public interface Subscriber {

    /**
     * Add Subscriber to the topic
     *
     * @param topic
     */
    void addSubscriber(final Topic topic);

    /**
     * Add Subscriber to a list of topics
     *
     * @param topics
     */
    void addSubscriber(final Topic...topics);

    /**
     * Remove subscriber from a give topic
     *
     * @param topic
     */
    void removeSubscriber(final Topic topic);

    /**
     * This method will be invoked by PubSubService whenever any message is available in the queue for a given topic,
     * and this subscriber is subscribed to a topic.
     *
     * @param topic
     * @param payload
     */
    void receiveMessageFromTopic(final Topic topic, final String payload);
}
