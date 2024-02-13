public class CellStack {
    private class Node {
        // Fields for node (its data and pointer)
        private Cell data;
        private Node next;

        /**
         * Intializes the Node's fields.
         */
        public Node() {
            this.data = null;
            this.next = null;
        }

        /**
         * Initializes the Node's fields.
         * 
         * @param newData the data that the Node sets to.
         */
        public Node(Cell newData) {
            this.data = newData;
            this.next = null;
        }

        /**
         * Initializes the Node's fields.
         * 
         * @param newData the data that Node is initialized with.
         * @param newNext the reference that Node is initialized with.
         */
        public Node(Cell newData, Node newNext) {
            this.data = newData;
            this.next = newNext;
        }

        /**
         * Returns the Node's data.
         * 
         * @return the Node's data
         */
        public Cell getValue() {
            return this.data;
        }

        /**
         * Sets the next reference to a node.
         * 
         * @param n the node the next reference is set to.
         */
        public void setNext(Node n) {
            this.next = n;
        }

        /**
         * Returns the next reference of the node.
         * 
         * @return the next reference of the node.
         */
        public Node getNext() {
            return this.next;
        }

    }
    // Fields for CellStack
    private Node top = new Node();
    private int size;

    /**
     * Constructor for CellStack
     * Initializes top node to null and size to 0.
     */
    public CellStack() {
        this.top = null;
        this.size = 0;
    }

    /**
     * Pushes a new cell to the top of the stack
     * @param c The cell to be pushed
     */
    public void push(Cell c) {
        Node newNode = new Node(c);
        if(this.top == null) {
            this.top = newNode;
        } else {
            newNode.next = this.top;
            this.top = newNode;
        }
        size++;
    }

    /**
     * Returns the data of the top node.
     * @return the data of the top node.
     */
    public Cell peek() {
        return this.top.data;
    }

    /**
     * Removes the top node and returns the data of the removed node.
     * @return data of the removed node
     */
    public Cell pop() {
        if(this.top == null) {
            return null;
        } else {
            Cell currentData = this.top.data;
            this.top = this.top.next;
            this.size--;
            return currentData;
        }
    }

    /**
     * Returns the size of the cell stack.
     * @return the size of the cell stack
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns the empty status of the stack.
     * @return the empty status of the stack
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        CellStack stack1 = new CellStack();
        stack1.push(new Cell(3, 4, 5));
        Cell c1 = stack1.peek();
        System.out.println("Top cell value: " + c1.getValue());

        stack1.push(new Cell(5, 6, 7));
        System.out.println("Cell stack size: " + stack1.size());

        System.out.println("Pop: " + stack1.pop().getValue());
        System.out.println("Cell stack size: " + stack1.size());
        System.out.println("Top cell value: " + stack1.peek().getValue());

        System.out.println("Pop: " + stack1.pop().getValue());
        System.out.println("Empty: " + stack1.isEmpty());
    }
}
