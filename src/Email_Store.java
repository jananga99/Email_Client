/*
 * Email_Stroe class contains static methods for save and get back (deserialize) emails.
 * Emails are saved in "Email_Saved\\sent" or "Email_Saved\\received"
 *   unique folder for each day.(path can be changed if want)
 * If email client opens more than one time in day and send emails all of those will
 *   be saved.
 * If all emails sent on a day in indexed from 0, that index will be the name of
 *   serialised email.(i.e. 3rd email sent on a day will be saved with the name '2.ser')
 * Emails sent on a particular day can be get by simply accessing the folder with name as
 *   that particular day and read all ser in the folder.
 */

class Email_Store {

    private String today;
    private String sendPath;
    private String receivePath;

    public Email_Store() {
        today = Date_Process.formatDateForFilename(Date_Process.getToday());
        sendPath = "Email_Saved\\sent\\"+today;
        receivePath = "Email_Saved\\received\\"+today;
    }

    /*
     * Getters and setters to change default save paths.
     */
    public String getSendPath() {
        return sendPath;
    }

    public void setSendPath(String sendPath) {
        this.sendPath = sendPath;
    }

    public String getReceivePath() {
        return receivePath;
    }

    public void setReceivePath(String receivePath) {
        this.receivePath = receivePath;
    }

    /*
     * Save email method saves the email object in hard disk using serialization.
     * If there is no folder named Email_Saved in the folder where source code is ,
     *   a new folder will be created named Email_Saved. Also there will be two folders
     *   in the Email_Saved. One is 'sent'.It is for sent emails. Other one is 'received'.
     *   It is for received emails.
     * For some particular day, Emails sent on that will be saved to a folder which is name
     *    as that day in Email_Saved folder in sent folder.
     * For example 2021_01_30 folder will contain all emails saved on that day(2021/01/30).
     */
    public void saveReceiveEmail(Email email) {
        saveEmail(email,receivePath);
    }

    public void saveSentEmail(Email email) {
        saveEmail(email,sendPath);
    }

    public void saveEmail(Email email,String path) {
        try {
            File file = new File(path);
            file.mkdirs();
            int fileCount = file.list().length;
            FileOutputStream fileStream = new FileOutputStream(path+"\\"+fileCount+".ser");
            ObjectOutputStream os =new ObjectOutputStream(fileStream);
            os.writeObject(email);
            os.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
     * Gets the save emails
     * Directory name(filename) is given for method(date)
     * Deserialises and returns all emails in an ArrayList.
     */
    public ArrayList<Email> getSendEmail(String date){
        return getEmail("Email_Saved\\sent\\"+date);
    }
    public ArrayList<Email> getReceiveEmail(String date){
        return getEmail("Email_Saved\\received\\"+date);
    }
    public ArrayList<Email> getEmail(String path) {
        ArrayList<Email> l =null;
        try {
            File file = new File(path);
            int fileCount = file.list().length;
            FileInputStream fileStream = null;
            ObjectInputStream os = null;
            l = new ArrayList<Email>();
            for(int i=0;i<fileCount;i++) {
                fileStream = new FileInputStream(path+"\\"+i+".ser");
                os = new ObjectInputStream(fileStream);
                l.add((Email) os.readObject());
            }
            os.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }finally {
            return l;
        }
    }

}
