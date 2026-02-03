public class BST {

    class Node{
        int data;
        Node left, right;

        public Node(int data) {
            this.data = data;
            this.left = this.right = null;
        }
    }
    Node root = null;

    void Insert(int data){
        Node node = new Node(data);
        if(root == null){
            root = node;
            return;
        }
        Node temp = root;
        while (true){
            if(temp.left == null && temp.data > data){
                temp.left = node;
                return;
            } else if (temp.right == null && temp.data < data) {
                temp.right = node;
                return;
            }
            else {
                if(data < temp.data){
                    temp = temp.left;
                }else {
                    temp = temp.right;
                }
            }
        }
    }


    boolean search(int data){
        if(root == null){
            return false;
        }
        Node temp = root;
        while (temp != null){
            if(temp.data == data){
                return true;
            }
            if(data < temp.data){
                temp = temp.left;
            }else {
                temp = temp.right;
            }
        }
        return false;
    }
}
