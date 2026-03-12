public class CircularList {

    class Node {
        int data;
        Node next;

        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
    Node head = null;

    void InsertFirst(int data){
        Node node = new Node(data);

        if(head == null){
            head = node;
            node.next = node;
        }else{

            Node temp = head;
            node.next = head;

            while (temp.next != head) {
                temp = temp.next;
            }
            head = node;
            temp.next = node;
        }
    }

    void InsertLast(int data){
        Node node = new Node(data);

        if(head == null){
            node.next = node;
            head = node;
        }else{

            Node temp = head;

            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = node;
            node.next = head;
        }
    }

    void display(){

        if(head == null){
            System.out.println("List is empty");
            return;
        }

        Node temp = head;

        while (temp.next != head) {
            System.out.print(temp.data + " --> ");
            temp = temp.next;
        }
        System.out.println(temp.data + " --> END");
    }

    boolean search(int data){

        Node temp = head;

        while (temp.next != head) {
            if(temp.data == data){
                return false;
            }
            temp = temp.next;
        }
        if(temp.data == data){
            return false;
        }
        return true;

    }

    void InsertBeforeValue(int value,int data){

        if(head == null){
            System.out.println("List is empty");
            return;
        }

        if(this.search(value)){
            System.out.println("Element is not present");
            return;
        }else{
            Node node = new Node(data);

            if(head.data == value){
                Node temp = head;
                node.next = head;

                while (temp.next != head) {
                    temp = temp.next;
                }
                head = node;
                temp.next = node;
            }else{
                Node pre = head;
                Node curr = head.next;

                while (pre.next != head) {
                    if(curr.data == value){
                        pre.next = node;
                        node.next = curr;
                        return;
                    }
                    pre = curr;
                    curr = curr.next;
                }
                pre.next = node;
                node.next = curr;
            }
        }
    }

    void InsertAfterValue(int value,int data){
        if(head == null){
            System.out.println("List is empty");
            return;
        }

        Node node = new Node(data);

        Node pre = head;
        Node curr = head.next;

        while (curr != head) {
            if(pre.data == value){
                pre.next = node;
                node.next = curr;
                return;
            }
            pre = curr;
            curr = curr.next;
        }
        if (pre.data == value) {
            pre.next = node;
            node.next = curr;
            return;
        }
        System.out.println("Element is not present");
    }

    void DeleteFirst(){

        if(head == null){
            System.out.println("List is Empty");
            return;
        }

        if (head.next == head) {
            head = null;
            return;
        }

        Node temp = head;

        while (temp.next != head) {
            temp = temp.next;
        }
        head = head.next;
        temp.next = head;
    }

    void DeleteLast(){
        if (head == null) {
            System.out.println("List is Empty");
            return;
        }

        if (head.next == head) {
            head = null;
            return;
        }

        Node pre = head;
        Node curr = head.next;

        while (curr.next != head) {
            pre = curr;
            curr = curr.next;
        }
        pre.next = head;
    }

    void InsertShorted(int data){
        Node node = new Node(data);

        if(head == null){
            head = node;
            node.next = node;
            return;
        }else{

            if(head.data >= data){
                Node temp = head;
                node.next = head;

                while (temp.next != head) {
                    temp = temp.next;
                }
                head = node;
                temp.next = node;
            }else{
                Node pre = head;
                Node curr = head.next;

                while (pre.next != head) {
                    if(curr.data >= data){
                        pre.next = node;
                        node.next = curr;
                        return;
                    }
                    pre = curr;
                    curr = curr.next;
                }
                pre.next = node;
                node.next = curr;
            }
        }
    }

    void DeleteValue(int value){
        if(head == null){
            System.out.println("List is empty");
            return;
        }

        if(search(value)){
            System.out.println("Element is not present");
            return;
        }else{
            if(head.data == value){
                Node temp = head;

                while (temp.next != head) {
                    temp = temp.next;
                }
                head = head.next;
                temp.next = head;
            }else{
                Node pre = head;
                Node curr = head.next;

                while (pre.next != head) {
                    if(curr.data == value){
                        pre.next = curr.next;
                    }
                    pre = curr;
                    curr = curr.next;
                }
            }
        }
    }

    public static void main(String[] args) {
        CircularList l = new CircularList();

        l.InsertFirst(10);
        l.InsertFirst(20);
        l.InsertFirst(30);

        l.InsertLast(40);
        l.InsertLast(50);
        l.InsertLast(100);

        l.InsertFirst(200);

        l.display();

        l.InsertAfterValue(207, 150);

        l.display();
    }
}