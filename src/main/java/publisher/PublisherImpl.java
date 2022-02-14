package publisher;

import services.PubSubService;
import models.Message;

public class PublisherImpl implements Publisher {

    private final String queueName;

    public PublisherImpl(final String queueName) {
        this.queueName = queueName;
    }

    @Override
    public void publishMessage(Message message) {
        PubSubService.getInstance().addMessageToQueue(message, queueName);
    }
}
