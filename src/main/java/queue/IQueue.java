package queue;

public interface IQueue<T> {

    /**
     * Add an element to the queue
     * @param t
     * @return
     */
    boolean add(T t);

    /**
     * Returns head of the queue without removing an element from the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     * @return
     */
    T peek();

    /**
     * Returns an element from head of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     * @return
     */
    T poll();

    /**
     * Check is queue is empty or not
     * @return
     */
    boolean isEmpty();

    /**
     * Calculate the size of the queue
     *
     * @return
     */
    int size();
}
