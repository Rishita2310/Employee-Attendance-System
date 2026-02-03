public class DoublyList {

    class Node{
        int data;
        Node next,prev;
        public Node(int data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
    Node head = null;

    boolean Search(int value){
        Node temp = head;

        while (temp.next != null) {
            if(temp.data == value){
                return true;
            }
            temp = temp.next;
        }
        if(temp.data == value){
            return true;
        }
        return false;
    }

    void InsertLast(int data){
        Node node = new Node(data);

        if(head == null){
            head = node;
        }else{
            Node temp = head;

            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = node;
            node.prev = temp;
        }
    }
}

