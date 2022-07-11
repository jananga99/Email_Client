/*
 * Serializes received emails.
 * Runs on a separate Thread.
 * The consumer of MyBlockingQueue
 */
class ReceiveEmailSaver extends Thread{

    private MyBlockingQueue myBlockingQueue;
    private String[] emailData;
    private Email email;
    private Email_Store emailStore;

    public ReceiveEmailSaver(MyBlockingQueue myBlockingQueue,Email_Store emailStore) {
        this.myBlockingQueue = myBlockingQueue;
        this.emailStore=emailStore;
    }

    public void save(Email email) {
        emailStore.saveReceiveEmail(email);
    }

    public void run() {
        while(true) {
            emailData = myBlockingQueue.dequeue();

            //Creates the new Email Object
            email = new Email(emailData[0],emailData[1],emailData[2]);

            save(email);
        }
    }
}