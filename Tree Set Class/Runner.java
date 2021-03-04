public class Runner{
    public static void main(String args[]){
        TreeSet<Character> randTree = new TreeSet<>();

        for (int i = 0; i < 30; i++) {
            char c = (char) (Math.random() + (91 - 65) * 65); //character range
            randTree.add(c);
            System.out.print(c);
        }

        System.out.println("\n Original Set - PreOrder: "+randTree.preOrder());
        System.out.println("\n Original Set - InOrder: "+randTree.inOrder());
        System.out.println("\n Original Set - PostOrder: "+randTree.postOrder());
        System.out.println("--------------------");
        TreeSet<Character> pre = makeCopy(randTree.preOrder());
        System.out.println("PreOrder Copy - PreOrder: "+pre.preOrder());
        System.out.println("\n PreOrder Copy - InOrder: "+pre.inOrder());
        System.out.println("\n PreOrder Copy - PostOrder: "+pre.postOrder());
        System.out.println("--------------------");
        TreeSet<Character> in = makeCopy(randTree.inOrder());
        System.out.println("InOrder Copy - PreOrder: "+in.preOrder());
        System.out.println("\n InOrder Copy - InOrder: "+in.inOrder());
        System.out.println("\n InOrder Copy - PostOrder: "+in.postOrder());
        System.out.println("--------------------");
        TreeSet<Character> post = makeCopy(randTree.postOrder());
        System.out.println("PostOrder Copy - PreOrder: " + post.preOrder());
        System.out.println("\n PostOrder Copy - InOrder: " + post.inOrder());
        System.out.println("\n PostOrder Copy - PostOrder: " + post.postOrder());
        System.out.println("--------------------");
        System.out.println("Rotate Right\n");
        randTree.rotateRight();
        System.out.println(randTree.preOrder());
        System.out.println(randTree.inOrder());
        System.out.println(randTree.postOrder());
        System.out.println("--------------------");
        System.out.println("Rotate Right Again\n");
        randTree.rotateRight();
        System.out.println(randTree.preOrder());
        System.out.println(randTree.inOrder());
        System.out.println(randTree.postOrder());
        System.out.println("--------------------");
        System.out.println("Rotate Right A Third Time\n");
        randTree.rotateRight();
        System.out.println(randTree.preOrder());
        System.out.println(randTree.inOrder());
        System.out.println(randTree.postOrder());
        System.out.println("--------------------");
        System.out.println("Rotate Left\n");
        randTree.rotateLeft();
        System.out.println(randTree.preOrder());
        System.out.println(randTree.inOrder());
        System.out.println(randTree.postOrder());
        System.out.println("--------------------");
        System.out.println("Rotate Left Again\n");
        randTree.rotateLeft();
        System.out.println(randTree.preOrder());
        System.out.println(randTree.inOrder());
        System.out.println(randTree.postOrder());
        System.out.println("--------------------");
        System.out.println("Rotate Left A Third Time\n");
        randTree.rotateLeft();
        System.out.println(randTree.preOrder());
        System.out.println(randTree.inOrder());
        System.out.println(randTree.postOrder());
        System.out.println("--------------------");
    }

    public static TreeSet<Character> makeCopy(String s) {
            TreeSet<Character> temp = new TreeSet<>();
            String[] strs = s.substring(1, s.length() - 1).split(",");
            for(String str : strs){
                temp.add(str.charAt(0));
            }
            return temp;
    }
}

