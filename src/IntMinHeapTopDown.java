import java.util.NoSuchElementException;

public class IntMinHeapTopDown implements IntQueue{
    private final IntArray elements;

    public IntMinHeapTopDown() {
        elements = new IntArray();
    }

    public IntMinHeapTopDown(int [] values) {
        this();
        for (int value : values) {
            insert(value);
        }
    }

    @Override
    public void enqueue(int value) {
        insert(value);
    }

    private void insert(int value) {
        elements.add(value);
        int index = elements.size() - 1;
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (elements.get(parent) <= value) {
                break;
            }
            elements.set(index, elements.get(parent));
            index = parent;
        }
        elements.set(index, value);
    }

    @Override
    public int dequeue() {
        int minimum = peek();
        int last = elements.remove(elements.size() - 1);
        if (!elements.isEmpty()) {
            elements.set(0, last);
            siftDown(0);
        }
        return minimum;
    }

    @Override
    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return elements.get(0);
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

    private void siftDown(int index) {
        int value = elements.get(index);
        while (index < elements.size() / 2) {
            int child = 2 * index + 1;
            if (child + 1 < elements.size()
                    && elements.get(child + 1) < elements.get(child)) {
                child++;
            }
            if (value <= elements.get(child)) {
                break;
            }
            elements.set(index, elements.get(child));
            index = child;
        }
        elements.set(index, value);
    }
}
