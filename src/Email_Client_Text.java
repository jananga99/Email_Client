import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;


/*
 * Email_Client_Text class is for writing to and extracting data from input file.
 * Contains a recipientList containing all records as an array.
 *
 */
class Email_Client_Text {

    private BufferedReader reader;
    private ArrayList<String[]> recipientList;
    private FileWriter writer;

    //opens reader and writer according to given filename.
    public Email_Client_Text(String fileName) {
        recipientList = new ArrayList<String[]>();
        try {
            reader= new BufferedReader(new FileReader(fileName));
            writer = new FileWriter(fileName,true);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    //returns the ArrayList of records read from file
    public ArrayList<String[]> getRecipientList() {
        return recipientList;
    }

    //returns the last record added to list
    public String[] getLastRecord() {
        return recipientList.get(recipientList.size()-1);
    }

    //adds a new record to file.
    public void addRecord(String record) {
        try {
            writer.write(record+"\n");
            writer.close();
        }catch(Exception ex) {

        }
    }

    /*reads the file, extracts data, creates an ArrayList of records.
     *A record has the form
     *		record = [type,name,email,post/nickname,birthday]
     */
    public void process() {
        String record = null;
        String data[] = null;

        try {
            record = reader.readLine();
        }catch(Exception ex) {
        }

        while(record!=null) {
            String[] recordArray = new String[5];
            data = record.split(",");
            recordArray[0] = data[0].substring(0,data[0].indexOf(":"));   //type
            recordArray[1] = data[0].substring(data[0].indexOf(":")+2);   //name

            if(recordArray[0].equals("Official")) {
                recordArray[2] = data[1];                                 //email of an Official
                recordArray[3] = data[2]; 								  //post of an Official
            }else {
                recordArray[4] = data[3];                                 //birthday of a closeFriend
                if(recordArray[0].equals("Office_friend")) {
                    recordArray[2] = data[2];                             //email of a Office_friend
                    recordArray[3] = data[1];                             //post of a Office_friend
                }else if(recordArray[0].equals("Personal")) {
                    recordArray[2] = data[2];  							  //email of a personal freind
                    recordArray[3] = data[1];                             //nickname of a personal friend
                }else {
                    System.out.println("Invalid data");
                }
            }
            recipientList.add(recordArray);
            try {
                record = reader.readLine();
            }catch(Exception ex) {
            }
        }
    }

    //methods to close reader and writer.
    public void close() {
        try {
            writer.close();
            reader.close();
        }catch(Exception ex) {

        }
    }

}