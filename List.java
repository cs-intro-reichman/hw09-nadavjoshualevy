import java.util.NoSuchElementException;

public class List {

    private Node first;
    private int size;
    
    public List() {
        first = null;
        size = 0;
    }

    public Node getNodeF() {
        return this.first;
    }
    
    public int getSize() {
        return size;
    }

    public CharData getFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        return first.cp;
    }

    public void addFirst(char chr) {
        CharData charData = new CharData(chr);
        Node newNode = new Node(charData, first);
        first = newNode;
        size++;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        Node current = first;
        while (current != null) {
            sb.append(current.cp);
            if (current.next != null) {
                sb.append(" ");
            }
            current = current.next;
        }
        sb.append(")");
        return sb.toString();
    }

    public int indexOf(char chr) {
        Node current = first;
        int count = 0;
        while (current != null) {
            if (current.cp.equals(chr)) {
                return count;
            }
            count++;
            current = current.next;
        }
        return -1;
    }

    public void update(char chr) {
        Node current = first;
        while (current != null) {
            if (current.cp.equals(chr)) {
                current.cp.count++;
                return;
            }
            current = current.next;
        }
        addFirst(chr);
    }

    public boolean remove(char chr) {
        if (first == null) {
            return false;
        }
        if (first.cp.equals(chr)) {
            first = first.next;
            size--;
            return true;
        }
        Node prev = first;
        Node current = first.next;
        while (current != null) {
            if (current.cp.equals(chr)) {
                prev.next = current.next;
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    public CharData get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.cp;
    }

    public CharData[] toArray() {
        CharData[] arr = new CharData[size];
        Node current = first;
        int i = 0;
        while (current != null) {
            arr[i++] = current.cp;
            current = current.next;
        }
        return arr;
    }

    public ListIterator listIterator(int index) {
        if (size == 0) return null;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node current = first;
        int i = 0;
        while (i < index) {
            current = current.next;
            i++;
        }
        return new ListIterator(current);
    }
}