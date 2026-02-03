public class SimpleList {

    class Node{
        int data;
        Node next;

        Node(int data){
            this.data = data;
            this.next = null;
        }
    }
    public Node head = null;

    void Display(){
        if(head == null){
            System.out.println("list is empty");
            return;
        }
        Node temp = head;
        while(temp != null){
            System.out.print(temp.data + " --> ");
            temp = temp.next;
        }
        System.out.println("END");
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
        }
    }

    boolean Search(int data){
        Node temp = head;

        while (temp != null) {
            if(temp.data == data){
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public static void main(String[] args) {

    }
}