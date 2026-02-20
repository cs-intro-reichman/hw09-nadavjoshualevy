import java.util.NoSuchElementException;

/** A linked list of character data objects.
 *  (Actually, a list of Node objects, each holding a reference to a character data object.
 *  However, users of this class are not aware of the Node objects. As far as they are concerned,
 *  the class represents a list of CharData objects. Likwise, the API of the class does not
 *  mention the existence of the Node objects). */
public class List {

    // Points to the first node in this list
    private Node first;

    // The number of elements in this list
    private int size;
	
    /** Constructs an empty list. */
    public List() {
        first = null;
        size = 0;
    }

    public Node getNodeF(){
        return this.first;
    }
    
    /** Returns the number of elements in this list. */
    public int getSize() {
 	      return size;
    }

    /** Returns the CharData of the first element in this list. */
    public CharData getFirst() {
        if (first == null){throw new NoSuchElementException("The list is empty");}
        return first.cp;
    }

    /** GIVE Adds a CharData object with the given character to the beginning of this list. */
    public void addFirst(char chr) {
        CharData charData = new CharData(chr);
        Node newNode = new Node(charData, first);
        first = newNode;
    }
    
    /** GIVE Textual representation of this list. */
    public String toString() {
        ListIterator itr = new ListIterator(first);
        if (!itr.hasNext()){return "The list is empty";}
        StringBuilder sb = new StringBuilder();
        sb.append("The list is:");
        while (itr.hasNext()){
            CharData data = itr.next();
            sb.append(data);
        }
        return sb.toString();
    }

    /** Returns the index of the first CharData object in this list
     *  that has the same chr value as the given char,
     *  or -1 if there is no such object in this list. */
    public int indexOf(char chr) {
       Node current = first;
       int count = 0;
       while (current != null){
        if (current.cp.equals(chr)){return count;}
        count ++;
        current = current.next;
       }
        return -1;
    }

    /** If the given character exists in one of the CharData objects in this list,
     *  increments its counter. Otherwise, adds a new CharData object with the
     *  given chr to the beginning of this list. */
    public void update(char chr) {
    Boolean hasfound = false;
    Node newNode = first;
    while (newNode != null) {
        if (newNode.cp.equals(chr)){newNode.cp.count++; hasfound =true;}
        newNode = newNode.next;
    }
    if (!hasfound){addFirst(chr);}
     
    }

    /** GIVE If the given character exists in one of the CharData objects
     *  in this list, removes this CharData object from the list and returns
     *  true. Otherwise, returns false. */
    public boolean remove(char chr) {
        if (first == null){return false;}
        if (first.cp.equals(chr)){first = first.next; return true;}
        Node prev = first;
        Node current = first.next;
        while (current != null){
            if (current.cp.equals(chr)){prev.next = current.next; return true;}
            prev = current;
            current =current.next;
        }
        return false;
    }

    /** Returns the CharData object at the specified index in this list. 
     *  If the index is negative or is greater than the size of this list, 
     *  throws an IndexOutOfBoundsException. */
    public CharData get(int index) {
        if (index >size || index <0){throw new IndexOutOfBoundsException();}
        Node newNode = first;
        for (int i = 0 ; i<index; i++){
            newNode = newNode.next;
        }
        return newNode.cp;
    }

    /** Returns an array of CharData objects, containing all the CharData objects in this list. */
    public CharData[] toArray() {
	    CharData[] arr = new CharData[size];
	    Node current = first;
	    int i = 0;
        while (current != null) {
    	    arr[i++]  = current.cp;
    	    current = current.next;
        }
        return arr;
    }

    /** Returns an iterator over the elements in this list, starting at the given index. */
    public ListIterator listIterator(int index) {
	    // If the list is empty, there is nothing to iterate   
	    if (size == 0) return null;
	    // Gets the element in position index of this list
	    Node current = first;
	    int i = 0;
        while (i < index) {
            current = current.next;
            i++;
        }
        // Returns an iterator that starts in that element
	    return new ListIterator(current);
    }
}