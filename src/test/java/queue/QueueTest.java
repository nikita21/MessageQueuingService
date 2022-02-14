package queue;

import models.Message;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utils.TestUtils.message;
import static utils.TestUtils.message1;

public class QueueTest {

    private Queue<Message> queue;

    @Before
    public void setUp() {
        queue = new Queue<>();
    }

    @Test
    public void test_add() {
        assertTrue(queue.isEmpty());

        queue.add(message);
        assertTrue(!queue.isEmpty());
    }

    @Test
    public void test_peek() {
        assertEquals(0, queue.size());
        queue.add(message);
        queue.add(message1);
        assertEquals(message, queue.peek());

        assertEquals(2, queue.size());
        assertTrue(!queue.isEmpty());
    }

    @Test
    public void test_poll() {
        queue.add(message);
        queue.add(message1);
        assertEquals(message, queue.poll());

        assertEquals(1, queue.size());
        assertTrue(!queue.isEmpty());
    }

    @Test
    public void test_size() {
        queue.add(message);
        queue.add(message1);
        assertEquals(2, queue.size());
    }
}
