import java.util.NoSuchElementException;

public class IntStackQueue implements IntQueue{
    private final IntArrayStack incoming;
    private final IntArrayStack outgoing;

    public IntStackQueue() {
        incoming = new IntArrayStack();
        outgoing = new IntArrayStack();
    }

    @Override
    public void enqueue(int value) {
        incoming.push(value);
    }

    @Override
    public int dequeue() {
        prepareOutgoing();
        return outgoing.pop();
    }

    @Override
    public int peek() {
        prepareOutgoing();
        return outgoing.peek();
    }

    @Override
    public int size() {
        return incoming.size() + outgoing.size();
    }

    @Override
    public boolean isEmpty() {
        return incoming.isEmpty() && outgoing.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        int outgoingCount = outgoing.size();
        int incomingCount = incoming.size();
        // Visit each stack in FIFO order and restore both original orders.
        for (int i = 0; i < outgoingCount; i++) {
            int value = outgoing.pop();
            if (i > 0) {
                result.append(", ");
            }
            result.append(value);
            incoming.push(value);
        }
        for (int i = 0; i < outgoingCount; i++) {
            outgoing.push(incoming.pop());
        }
        for (int i = 0; i < incomingCount; i++) {
            outgoing.push(incoming.pop());
        }
        for (int i = 0; i < incomingCount; i++) {
            int value = outgoing.pop();
            if (outgoingCount > 0 || i > 0) {
                result.append(", ");
            }
            result.append(value);
            incoming.push(value);
        }
        return result.append(']').toString();
    }

    private void prepareOutgoing() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        if (outgoing.isEmpty()) {
            while (!incoming.isEmpty()) {
                outgoing.push(incoming.pop());
            }
        }
    }
}
