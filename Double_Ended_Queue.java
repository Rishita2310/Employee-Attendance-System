public class Double_Ended_Queue {

    int[] queue;
    int F,R,size;

    public Double_Ended_Queue(int n) {
        queue = new int[n];
        F = R =  -1;
        size = n;
    }

    void insertRear(int n){
        if( (F == 0 && R == size -1) || F == R + 1 ){
            System.out.println("Queue Over Flow");
            return;
        }

        R = ( R + 1) % size ;

        queue[R] = n;

        if( F == -1 ) {
            F = 0;
        }
    }

    void insertFront(int n){
        if( (F == 0 && R == size-1) || F == R+1 ){
            System.out.println("QueueOver Flow");
            return;
        }
        if( F == 0 ){
            F = size-1;
        } else  {
            if( F == -1 ){
                F = 0;
                R = 0;
            }else {
                F--;
            }
        }
        queue[F] = n;
    }

    void deleteFront(){
        if( F == -1){
            System.out.println("UnderFlow");
            return;
        }
        if(F == R) {
            F = R = -1;
            return;
        }
        if( F == size-1  ){
            F = 0 ;
        } else {
            F++;
        }
    }

    void deleteRear(){
        if(F == -1){
            System.out.println("underflow");
            return;
        }
        if( F == R ){
            F = R = -1;
            return;
        }
        else if( R == 0  ){
            R = size-1;
        }else {
            R--;
        }
    }

    void Display(){
        if(F == -1 && R == -1){
            System.out.println("Queue is empty");
            return;
        }
        if( F > R) {
            for (int i = F; i < size; i++) {
                System.out.print(queue[i] + " --> ");
            }
            for (int j = 0; j <= R; j++) {
                System.out.print(queue[j] + " --> ");
            }
            System.out.println("END");
        }else if( R > F) {
            for (int i = F; i <= R; i++) {
                System.out.print(queue[i] + " --> ");
            }
            System.out.println("End");
        }
    }

    public static void main(String[] args) {
        Double_Ended_Queue queue = new Double_Ended_Queue(4);

        queue.insertRear(10);
        queue.insertRear(20);
        queue.insertRear(30);
        queue.insertRear(40);

        queue.Display();
    }
}