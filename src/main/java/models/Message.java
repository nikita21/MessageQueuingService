package models;

import java.util.Objects;

public class Message {

    private final Topic topic;
    private final String messagePayload;

    public Message(final String messagePayload, final Topic topic) {
        this.messagePayload = messagePayload;
        this.topic = topic;
    }

    public String getMessagePayload() {
        return messagePayload;
    }

    public Topic getTopic() {
        return topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return topic.equals(message.topic) && messagePayload.equals(message.messagePayload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, messagePayload);
    }

    @Override
    public String toString() {
        return "Message{" +
                "topic=" + topic +
                ", messagePayload='" + messagePayload + '\'' +
                '}';
    }
}
