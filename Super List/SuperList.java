import java.util.EmptyStackException;

public class SuperList<E> {

    ListNode<E> root;
    ListNode<E> end;
    int size = 0;

    public SuperList() {
        root = null;
        end = null;
    }

    public void add(E value) {

        ListNode<E> newNode = new ListNode<>(value);
        if (root == null) {

            root = newNode;
            end = root;

        } else {

            end.setNext(newNode);
            newNode.setPrevious(end);
            end = newNode;

        }
        size++;

    }

    public E poll() {

        if (root == null)
            return null;
        E value = root.getValue();
        if (size == 1)
            clear();
        else {
            root = root.getNext();
            root.setPrevious(null);
        }
        size--;
        return value;

    }

    public void add(int index, E value) {

        if (index > size || index < 0)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size);
        ListNode<E> newNode = new ListNode<>(value);

        if (index == size)
            push(value);
        else if (index == 0) {

            newNode.setNext(root);
            root.setPrevious(newNode);
            root = newNode;
            size++;

        } else {

            ListNode<E> tempNode = root.getNext();
            for (int i = 1; i < index; i++)
                tempNode = tempNode.getNext();
            newNode.setNext(tempNode);
            newNode.setPrevious(tempNode.getPrevious());
            tempNode.getPrevious().setNext(newNode);
            tempNode.setPrevious(newNode);
            size++;

        }
    }


    public E pop() {

        if (root == null)
            throw new EmptyStackException();
        E value = end.getValue();
        if (size == 1)
            clear();
        else {
            end = end.getPrevious();
            end.setNext(null);
        }
        size--;
        return value;

    }

    public void push(E value) {

        ListNode<E> newNode = new ListNode<>(value);
        if (root == null) {
            root = newNode;
            end = root;
        } else {
            end.setNext(newNode);
            newNode.setPrevious(end);
            end = newNode;
        }
        size++;

    }

    public boolean isEmpty() {

        return size <= 0;

    }

    public E peekQueue() {

        if (root == null)
            return null;
        return root.getValue();
    }

    public E peekStack() {

        if (end == null)
            return null;
        return end.getValue();

    }

    public int size() {

        return size;
    }

    public E get(int index) {

        if (index > size - 1 || index < 0)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size);
        ListNode<E> ret = root;
        for (int i = 0; i < index; i++)
            ret = ret.getNext();
        return ret.getValue();

    }

    public E remove(int index) {

        if (index > size - 1 || index < 0)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " is out of bounds for size " + size);
        if (index == 0)
            return poll();
        if (index == size - 1)
            return pop();
        ListNode<E> ret = root;
        for (int i = 0; i < index; i++)
            ret = ret.getNext();
        E val = ret.getValue();

        ret.getPrevious().setNext(ret.getNext());

        ret.getNext().setPrevious(ret.getPrevious());

        size--;

        return val;

    }

    public E remove(E value) {

        ListNode<E> ret = root;
        for (int i = 0; i < size; i++) {

            if (ret.getValue() == value) {

                if (ret != root)
                    ret.getPrevious().setNext(ret.getNext());
                else {

                    root = null;
                    end = null;

                }
                if (ret != end)
                    ret.getNext().setPrevious(ret.getPrevious());
                size--;
                return ret.getValue();
            }

            ret = ret.getNext();
        }

        if (ret == null)
            return null;
        return ret.getValue();

    }

    public void clear() {

        root = null;
        end = null;
        size = 0;

    }

    public boolean contains(E value) {

        ListNode<E> temp = root;
        for (int i = 0; i < size; i++) {
            if (temp.getValue() == value)
                return true;
            temp = temp.getNext();
        }
        return false;

    }

    public String toString() {

        StringBuilder result = new StringBuilder("[");

        ListNode<E> temp = root;
        for (int i = 0; i < size; i++) {

            result.append(temp.getValue());
            if (i < size - 1)
                result.append(", ");
            temp = temp.getNext();

        }
        result.append("]");

        return result.toString();

    }

    public static class ListNode<E> {

        E value;

        ListNode<E> next;

        ListNode<E> previous;

        public ListNode(E value) {

            this.value = value;
            next = null;
            previous = null;

        }

        public E getValue() {

            return value;

        }

        public ListNode<E> getNext() {

            return next;

        }

        public ListNode<E> getPrevious() {

            return previous;

        }

        public void setNext(ListNode<E> node) {

            next = node;

        }

        public void setPrevious(ListNode<E> node) {

            previous = node;

        }

    }

}