/*
 * An observer sub class.
 * Implements Observer interface.
 * Updates if Observable notifies.
 * When an update received prints a message in a
 * 	text file in hard disk
 */
class EmailStatPrinter implements Observer {

    private String fileName;

    public EmailStatPrinter(String fileName) {
        this.fileName = fileName;
    }
    public void update() {
        printToText();
    }

    //printing method to text file.
    public void printToText() {
        try {
            FileWriter writer = new FileWriter(fileName,true);
            writer.write("an email is received at "+Date_Process.getCurrentTime()+"\n");
            writer.close();
        }catch(IOException ex) {
            System.out.println("Error in Writing receive recorder file.");
        }
    }

    //Prints the texts on textfile to console.
    public void readText() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while(line!=null){
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        }catch(IOException ex) {
            System.out.println("Error in Reading receive recorder file.");
        }
    }
}