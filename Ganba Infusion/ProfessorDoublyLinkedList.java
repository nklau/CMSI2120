package hw2;

public class ProfessorDoublyLinkedList {

    private int size;
    private Node head;

    private class Node {
        private Professor data;
        private Node prev;
        private Node next;

        private Node(Professor data) {
            this.data = data;
            prev = null;
            next = null;
        }

        private Node(Professor data, Node prev) {
            this.data = data;
            this.prev = prev;
            next = null;
        }

        private Node(Professor data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }
    
    public ProfessorDoublyLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * Adds data to the end of the list
     * @param data the int to add to the end of the list
     */
    public void append(Professor data) {
        if (size == 0) {
            head = new Node(data);
        } else {
            Node current = head;
            for (int i = 0; i < size - 1; i++) {
                current = current.next;
            }
            current.next = new Node(data, current);
        }
        size++;
    }

    /**
     * Removes the Node at index index from the ProfLinkedList
     * 
     * @param index the index of the Node that will be removed
     */
    public void removeAt(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        if (size == 1) {
            head.data = null;
            head = null;
            size--;
            return;
        }

        Node current = head;
        for (int i = 0; i < index; i++) { //current now refers to the Node at index
            current = current.next;
        }

        if (current.prev == null) {
            head = current.next;
            head.prev = null;
        }

        if (current.next == null) {
            Node prevNode = current.prev;
            prevNode.next = null;
        }

        if (current.next != null && current.prev != null) {
            Node prevNode = current.prev;
            prevNode.next = current.next;
            current.prev = prevNode;
        }
        current.data = null;
        size--;
    }

    /**
     * Removes the Node containing the Professor prof from the ProfLinkedList
     * 
     * @param prof the Professor to remove from the list
     */
    public void remove(Professor prof) {
        if (size == 0) {
            throw new IllegalArgumentException();
        }
        Node current = head;
        for (int i = 0; i < size; i++) {
            if (current.data == prof) {
                if (size == 1) {
                    head.data = null;
                    head = null;
                    size--;
                    break;
                }
                if (current.prev == null) {
                    head = current.next;
                    head.prev = null;
                }
                if (current.next == null) {
                    Node prevNode = current.prev;
                    prevNode.next = null;
                }
                if (current.next != null && current.prev != null) {
                    Node prevNode = current.prev;
                    prevNode.next = current.next;
                    current.prev = prevNode;
                }
                current.data = null;
                size--;
            } else {
                current = current.next;
            }
        }
    }

    /**
     * Sets the Node at index to be null
     * 
     * @param index the index of the Node to null out
     */
    public void setNull(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node current = head;
        for (int i = 0; i < index; i++) { //current now refers to the Node at index
            current = current.next;
        }
        current.data = null;
    }

    /**
     * Returns the first Professor in the list with the name name and rarity rarity
     * 
     * @param name the name of the Professor to look for in the list
     * @param rarity the rarity of the Professor to look for in the list
     * 
     * @return the Professor with name and rarity. Returns null if data is not in the list
     */
    public Professor findProf(String name, int rarity) {
        Node current = head;
        while (current != null) {
            if (name.equals(current.data.getName()) && rarity == current.data.getRarity()) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Returns the value of the node at index index
     * 
     * @param index the index to retrieve data from
     * @return the value of the node at index index
     */
    public Professor getAt(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Returns the index of the first instance of data
     * 
     * @param name the name of the Professor to look for in the ProfLinkedList
     * @param rarity the rarity of the Professor to look for in the ProfLinkedList
     * 
     * @return the index of data. Returns -1 if data is not in the ProfLinkedList
     */

    public int indexOf(String name, int rarity) {
        Node current = head;
        for (int i = 0; i < size; i++) {
            if (current.data != null) {
                if (name.equals(current.data.getName()) && rarity == current.data.getRarity()) {
                    return i;
                }
                current = current.next;
            }
        }
        return -1;
    }

    public int size() {
        return size;
    }
}
