import java.util.LinkedList;


/*
 * Blocking Queue data structure to store data in Producer consumer process
 *   in a thread safely manner. Producer, EmailReceiver and Consumer, ReceiveEmailSaver
 *   has a shared resource MyBlockingQueue. Guarded block is used here to
 *   implement required process in a thread safe manner.
 *LinkedList is used to store email details
 */
class MyBlockingQueue {

    private LinkedList<String []> emailList;
    private int maxSize;
    private int size;

    //default maxsize is 2
    public MyBlockingQueue() {
        this(2);
    }

    public MyBlockingQueue(int maxSize) {
        emailList = new LinkedList<String []>();
        this.maxSize = maxSize;   //default, can be changed with setters.
        size = 0;
    }

    //Adding an element to end of list.
    public synchronized void enqueue(String[] s) {
        while(size>=maxSize) {
            try {
                wait();
            }catch(InterruptedException e) {
                System.out.println("Error in Blocking Queue wait");
            }
        }
        size+=1;
        emailList.add(s);
        notifyAll();
    }

    //getting the first element of list
    public synchronized String [] dequeue() {
        while(size==0) {
            try {
                wait();
            }catch(InterruptedException e) {
                System.out.println("Error in Blocking Queue wait");
            }
        }
        size-=1;
        notifyAll();
        return emailList.remove(0);
    }

    //getters and setters for size and MaxSize
    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public void getSize(int size) {
        this.size = size;
    }

}