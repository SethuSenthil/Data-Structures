public class TreeSet<E extends Comparable<E>> {

    private TreeNode<E> root;
    private int size;
    private String output;

    public TreeSet() {
        this.root = null;
        size = 0;
    }

    public void add(E value) {
        if (root == null) {
            root = new TreeNode<>(value);
            size = 1;
        } else
            add(root, value);
    }

    public void add(TreeNode<E> temp, E value) {
        if (value.equals(temp.getValue()))
            return;
        if (value.compareTo(temp.getValue()) < 0) {
            if (temp.getLeft() == null) {
                temp.setLeft(new TreeNode<E>(value));
                size++;
                return;
            } else
                add(temp.getLeft(), value);
        } else {
            if (temp.getRight() == null) {
                temp.setRight(new TreeNode<E>(value));
                size++;
                return;
            } else
                add(temp.getRight(), value);
        }
    }

    public String preOrder() {
        if (size == 0)
            return "[]";

        output = "[";
        preOrder(root);
        return output.substring(0, output.length() - 1) + "]";
    }

    public void preOrder(TreeNode<E> node) {
        if (node != null) {
            output += node.getValue() + ",";
            preOrder(node.getLeft());
            preOrder(node.getRight());
        }

    }

    public void inOrder(TreeNode<E> node) {
        if (node != null) {
            inOrder(node.getLeft());
            output += node.getValue() + ", ";
            inOrder(node.getRight());
        }
    }

    public void postOrder(TreeNode<E> node) {
        if (node != null) {
            inOrder(node.getLeft());
            inOrder(node.getRight());
            output += node.getValue() + ", ";
        }
    }

    public boolean contains(TreeNode<E> node, E value) {
        if (node.getValue() != null) {
            if (value.equals(node.getValue()))
                return true;

            if (value.equals(node.getValue()) && Integer.valueOf((String) value) < 0)
                contains(node.getLeft(), value);

            if (value.equals(node.getValue()) && Integer.valueOf((String) value) > 0)
                contains(node.getRight(), value);
        }
        return false;
    }

    public void remove(E value) {
        if (value != null) {
            if (root.getRight() == null && root.getLeft() == null) {
                size = 0;
                root = null;
                return;
            }
            size--;
        }
    }

    public void rotateRight(){
        if(root != null && root.getLeft() != null) {
            TreeNode<E> temp = root;
            root = root.getLeft();
            temp.setLeft(root.getRight());
            root.setRight(temp);
        }
    }

    public void rotateLeft() {
        if(root != null && root.getRight() != null) {
            TreeNode<E> temp = root;
            root = root.getRight();
            temp.setRight(root.getLeft());
            root.setLeft(temp);
        }
	}

    public TreeNode<E> remove(TreeNode<E> node, E value) {
        if (node != null) {
            if (value.compareTo(node.getValue()) < 0)
                remove(node.getLeft(), value);
            else if (value.compareTo(node.getValue()) > 0)
                remove(node.getRight(), value);
            else if (value.equals(node.getValue())) {
                if (node.getRight() == null)
                    return node.getLeft();
                else if (node.getLeft() == null)
                    return node.getRight();
                else
                    root = null;
            }
        }
        return null;
    }

    public class TreeNode<E extends Comparable<E>> {

        private E value;
        private TreeNode<E> left;
        private TreeNode<E> right;

        public TreeNode(E value) {
            this.value = value;
        }

        public TreeNode<E> search(E obj) {
            int c = obj.compareTo(value);
            if (c > 0) {
                return right == null ? null : right.search(obj);
            } else if (c < 0) {
                return left == null ? null : left.search(obj);
            } else {
                return this;
            }
        }

        public E getValue() {
            return value;
        }

        public void setValue(E obj) {
            //TODO: setvaue
           // value.
        }

        public boolean insert(E obj) {
            int compare = obj.compareTo(value);
            if (compare > 0) {
                if (right == null) {
                    right = new TreeNode<>(obj);
                    return true;
                } else
                    return right.insert(obj);
            } else if (compare < 0) {
                if (left == null) {
                    left = new TreeNode<>(obj);
                    return true;
                } else
                    return left.insert(obj);
            }
            return false;
        }

        public TreeNode<E> getRight() {
            return right;
        }

        public TreeNode<E> getLeft() {
            return left;
        }

        public void setRight(TreeNode<E> right) {
            this.right = right;
        }

        public void setLeft(TreeNode<E> left) {
            this.left = left;
        }

        public String toString() {
            return value.toString();
        }

    }

	public String inOrder() {
		return null;
	}

	public String postOrder() {
		return null;
	}

}
