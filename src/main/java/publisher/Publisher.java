package publisher;

import models.Message;

public interface Publisher {

    /**
     * This method is responsible for publishing message to the queue by calling PubSubService
     *
     * @param message
     */
    void publishMessage(final Message message);
}
