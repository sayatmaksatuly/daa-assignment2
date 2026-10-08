import java.util.NoSuchElementException;

public class IntQueueStack implements IntStack{
    private IntLinkedListQueue elements = new IntLinkedListQueue();
    private IntLinkedListQueue spare = new IntLinkedListQueue();

    @Override
    public void push(int value) {
        elements.enqueue(value);
    }

    @Override
    public int pop() {
        moveToTop();
        int value = elements.dequeue();
        swapQueues();
        return value;
    }

    @Override
    public int peek() {
        moveToTop();
        int value = elements.dequeue();
        spare.enqueue(value);
        swapQueues();
        return value;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    private void moveToTop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        // The queue stores bottom first; its last element is the stack top.
        while (elements.size() > 1) {
            spare.enqueue(elements.dequeue());
        }
    }

    private void swapQueues() {
        IntLinkedListQueue previous = elements;
        elements = spare;
        spare = previous;
    }
}
