/*
 * An observer sub class.
 * Implements Observer interface.
 * Updates if Observable notifies.
 * When an update received prints a message in to the console.
 */
class EmailStatRecorder implements Observer{
    public void update() {
        printToConsole();
    }

    public void printToConsole() {
        System.out.println("an email is received at "+Date_Process.getCurrentTime());
    }

}