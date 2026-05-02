public class Stack
{
    private Object[] item;
    private int top;

    /**
     * Creates a new Stack that stores objects and is initially empty
     */
    public Stack() {
        item = new Object[1];
        top = -1;
    }

    /**
     * Checks if the stack is empty
     * @return whether the stack is empty
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * Checks if the stack is full
     * @return whether the stack is full
     */
    public boolean isFull() {
        return top == item.length-1;
    }

    /**
     * Clears the stack
     */
    public void clear() {
        item = new Object[1];
        top = -1;
    }

    /**
     * Adds an object to the top of the stack
     * @param o the object to be pushed onto the stack
     */
    public void push(Object o) {
        if (isFull())
            resize(2 * item.length);
        item[++top] = o;
    }

    /**
     * Changes the maximum size of the stack
     * @param size the new maximum size of the stack
     */
    private void resize(int size) {
        Object[] temp = new Object[size];
        if (top + 1 >= 0) System.arraycopy(item, 0, temp, 0, top + 1);
        item = temp;
    }

    /**
     * Removes an object from the top of the stack
     * @return the object removed from the stack
     */
    public Object pop() {
        if (isEmpty()) {
            System.out.println("Stack Underflow.");
            System.exit(1);
        }
        Object temp = item[top];
        item[top--] = null;
        if (top == item.length/4)
            resize(item.length/2);
        return temp;
    }

    /**
     * Gets an object from the top of the stack
     * @return the object at the top of the stack
     */
    public Object top() {
        if (isEmpty()) {
            System.out.println("Stack Underflow.");
            System.exit(1);
        }    
        return item[top];
    }
}