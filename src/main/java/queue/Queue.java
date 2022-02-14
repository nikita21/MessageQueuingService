package queue;

import java.util.Objects;

public class Queue<T> implements IQueue<T> {

    private Node<T> front;
    private Node<T> rear;

    public Queue() {
        this.front = null;
        this.rear = null;
    }

    @Override
    public boolean add(T object) {
        Node<T> node = new Node<>(object);

        // If queue is empty
        if (Objects.isNull(rear)) {
            this.front = node;
            this.rear = node;
        } else { // add node at the end of the queue and update the rear node
            this.rear.next = node;
            this.rear = node;
        }
        return true;
    }

    @Override
    public T peek() {

        // Check if queue is empty
        if (this.isEmpty())
            return null;

        return this.front.data;
    }

    @Override
    public T poll() {

        if (this.isEmpty())
            return null;

        Node<T> node = this.front;
        this.front = this.front.next;

        if (isEmpty()) {
            this.rear = null;
        }
        return node.data;
    }

    @Override
    public boolean isEmpty() {
        return Objects.isNull(this.front);
    }

    @Override
    public int size() {
        if (isEmpty()) {
            return 0;
        }

        Node<T> node = this.front;
        int count = 0;
        while (node != null) {
            node = node.next;
            count++;
        }
        return count;
    }
}
