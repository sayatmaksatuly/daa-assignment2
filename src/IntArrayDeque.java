import java.util.NoSuchElementException;

public class IntArrayDeque implements IntDeque{
    private IntArray elements;
    private int head;
    private int size;

    public IntArrayDeque() {
        elements = new IntArray();
        // The IntArray size is the number of ring slots, including unused ones.
        elements.add(new int[8]);
    }

    @Override
    public void addFirst(int value) {
        ensureSpace();
        head = head == 0 ? elements.size() - 1 : head - 1;
        elements.set(head, value);
        size++;
    }

    @Override
    public void addLast(int value) {
        ensureSpace();
        elements.set(physicalIndex(size), value);
        size++;
    }

    @Override
    public int removeFirst() {
        checkNotEmpty();
        int value = elements.get(head);
        elements.set(head, 0);
        head = physicalIndex(1);
        size--;
        if (size == 0) {
            head = 0;
        }
        return value;
    }

    @Override
    public int removeLast() {
        checkNotEmpty();
        int index = physicalIndex(size - 1);
        int value = elements.get(index);
        elements.set(index, 0);
        size--;
        if (size == 0) {
            head = 0;
        }
        return value;
    }

    @Override
    public int peekFirst() {
        checkNotEmpty();
        return elements.get(head);
    }

    @Override
    public int peekLast() {
        checkNotEmpty();
        return elements.get(physicalIndex(size - 1));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(elements.get(physicalIndex(i)));
        }
        return result.append(']').toString();
    }

    private int physicalIndex(int offset) {
        int slotsToEnd = elements.size() - head;
        return offset < slotsToEnd ? head + offset : offset - slotsToEnd;
    }

    private void ensureSpace() {
        if (size < elements.size()) {
            return;
        }
        if (size == Integer.MAX_VALUE) {
            throw new OutOfMemoryError("Deque is too large");
        }
        int capacity = (int) Math.min(2L * elements.size(), Integer.MAX_VALUE);
        IntArray expanded = new IntArray();
        // Unwrap the occupied slots before adding the new free slots.
        for (int i = 0; i < size; i++) {
            expanded.add(elements.get(physicalIndex(i)));
        }
        while (expanded.size() < capacity) {
            expanded.add(0);
        }
        elements = expanded;
        head = 0;
    }

    private void checkNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
    }
}
