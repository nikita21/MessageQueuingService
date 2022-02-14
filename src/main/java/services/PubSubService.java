package services;

import models.Message;
import models.Topic;
import queue.IQueue;
import queue.Queue;
import subscriber.Subscriber;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class PubSubService {

    private static final long EXECUTOR_SHUTDOWN_WAIT_TIME_MILLIS = 60000;

    private final Map<Topic, Set<Subscriber>> subscribers;
    private final Map<String, IQueue<Message>> messageQueues;
    private final ScheduledExecutorService executor;
    private static PubSubService pubSubService = new PubSubService();

    public static PubSubService getInstance() {
        return pubSubService;
    }

    public Map<Topic, Set<Subscriber>> getSubscribers() {
        return subscribers;
    }

    public Map<String, IQueue<Message>> getMessageQueues() {
        return messageQueues;
    }

    private PubSubService() {
        subscribers = new ConcurrentHashMap<>();
        messageQueues = new HashMap<>();
        executor = Executors.newScheduledThreadPool(5);
    }

    /**
     * This method is invoked from Publisher to enqueue message into the appropriate queue.
     *
     * @param message
     * @param queueName
     */
    public void addMessageToQueue(final Message message, final String queueName) {
        IQueue<Message> mQueue = messageQueues.getOrDefault(queueName, new Queue<>());
        mQueue.add(message);
        messageQueues.put(queueName, mQueue);
    }

    /**
     * This method is invoked to start the worker threads.
     * Threads will keep on polling queues at regular intervals to check if any message is available or not.
     * If the message is available, all the subscribers subscribed to the message topic will be notified.
     *
     */
    public void broadcastMessageToSubscribers() {
        messageQueues.entrySet().forEach(entry -> {
            IQueue<Message> messageQ = entry.getValue();
            executor.scheduleAtFixedRate(new QueueWorker(messageQ), 0, 10, TimeUnit.SECONDS);
        });
    }

    /**
     * This method is invoked to subscribe given topic by a subscriber
     *
     * @param topic
     * @param subscriber
     */
    public void subscribeToTopic(final Topic topic, final Subscriber subscriber) {
        Set<Subscriber> subscriberList = subscribers.getOrDefault(topic, new HashSet<>());
        subscriberList.add(subscriber);
        subscribers.put(topic, subscriberList);
    }

    /**
     * Remove subscriber from a given topic
     *
     * @param topic
     * @param subscriber
     */
    public void unsubscribeFromTopic(final Topic topic, final Subscriber subscriber) {
        if (subscribers.containsKey(topic)) {
            Set<Subscriber> subscriberList = subscribers.get(topic);
            subscriberList.remove(subscriber);
        }
    }

    /**
     * Required for graceful shutdown
     */
    public void stop() {
        System.out.println("Shutting down executor for graceful shutdown");
        executor.shutdown();
        try {
            if (!executor.awaitTermination(EXECUTOR_SHUTDOWN_WAIT_TIME_MILLIS, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private class QueueWorker implements Runnable {

        private final IQueue<Message> messageQ;

        public QueueWorker(final IQueue<Message> messageQ) {
            this.messageQ = messageQ;
        }

        @Override
        public void run() {
            while(!messageQ.isEmpty()) {
                final Message message = messageQ.poll();
                final Topic topic = message.getTopic();
                final Set<Subscriber> subscriberList = subscribers.get(topic);

                if(!Objects.isNull(subscriberList)) {
                    subscriberList.forEach(
                            subscriber -> {
                                try {
                                    subscriber.receiveMessageFromTopic(topic, message.getMessagePayload());
                                } catch (RuntimeException e) {
                                    e.printStackTrace();
                                }
                            });
                }
            }
        }
    }
}
