import java.util.ArrayList;
import java.util.Scanner;

/*
 * Main Class
 */
public class Email_Client {
    private static ArrayList<Recipient> recipientList;
    private static ArrayList<CloseFriend> todayBirthdayList;

    public static void main(String[] args) throws Exception {

        EmailSender emailSender = new EmailSender();

        Email_Store emailStore = new Email_Store();

        MyBlockingQueue myBlockingQueue = new MyBlockingQueue();
        myBlockingQueue.setMaxSize(5);

        EmailReceiver emailReceiver = new EmailReceiver(myBlockingQueue);

        ReceiveEmailSaver receiveEmailSaver = new ReceiveEmailSaver(myBlockingQueue,emailStore);

        EmailStatPrinter emailStatPrinter = new EmailStatPrinter("Received_Email_Recorder.txt");
        EmailStatRecorder emailStatRecorder = new EmailStatRecorder();

        emailReceiver.addObserver(emailStatPrinter);
        emailReceiver.addObserver(emailStatRecorder);

        Thread emailReceiverThread = new Thread(emailReceiver);
        Thread receiveEmailSaverThread = new Thread(receiveEmailSaver);
        emailReceiverThread.setName("Email Receiver");
        receiveEmailSaverThread.setName("Receive Email Saver");
        emailReceiverThread.start();
        receiveEmailSaverThread.start();

        Scanner scanner = new Scanner(System.in);

        Email_Client_Text text = new Email_Client_Text("clientList.txt");

        /*
         * text.process read and extract data from given file according to requirements.
         */
        text.process();

        /*
         * Recipient_Process creates and updates recipientList and todayBirthdayList.
         */
        Recipient_Process recipientProcess = new Recipient_Process();
        recipientProcess.addRecipientList(text.getRecipientList());
        recipientList = recipientProcess.getRecipientList();
        todayBirthdayList = recipientProcess.getTodayBirthdayList();

        /*
         * Sends mails to all recipients who have birthday today.
         * If newly added recipient has birthday today, he too will be sent a birthday wish
         */
        for(int i=0;i<todayBirthdayList.size();i++) {
            sendBirthdayMail(emailSender,emailStore,todayBirthdayList.get(i));
        }

        System.out.println("Enter option type: \n"
                + "1 - Adding a new recipient\n"
                + "2 - Sending an email\n"
                + "3 - Printing out all the recipients who have birthdays\n"
                + "4 - Printing out details of all the emails sent\n"
                + "5 - Printing out the number of recipient objects in the application\n"
                + "--------------------------------------------------------------------------\n"
                + "Receive Email Options\n"
                + "6 - Printing out details of emails received\n"
                + "7 - Printing out all Email records (Email log)\n");

        while(true) {

            int option = 0;

            System.out.println("Enter Option and inputs");

            while(!scanner.hasNext());

            if(scanner.hasNext()) {
                option = Integer.parseInt(scanner.nextLine());
            }

            switch(option){

                /*
                 * Adding a new recipient.
                 *  This new record will be read and added to recipientList and update
                 *    todayBirthdayList.
                 *  Sends the birthday email if new one has birthday today.
                 *  (You can remove processing part for new recipient and contain only addRecord
                 *  part according to requirement) (lines 57-63)
                 */
                case 1:
                    System.out.println("Enter the Recepient Details ");
                    String inputLine=scanner.nextLine();
                    text.addRecord(inputLine);
                    text.process();
                    int lenBefore = todayBirthdayList.size();
                    recipientProcess.addRecipient(text.getLastRecord());
                    int lenAfter = todayBirthdayList.size();
                    if(lenAfter-lenBefore==1) {
                        sendBirthdayMail(emailSender,emailStore,todayBirthdayList.get(lenAfter-1));
                    }
                    break;

                /*
                 * Sending an Email
                 * User input record in the exact way:- RecieverAddress,email_subject_email_body
                 */
                case 2:
                    System.out.println("Enter Email Details (Receiver address,Subject,Content)");
                    String[] inputEmailRecord = scanner.nextLine().split(",");
                    Email email = new Email(inputEmailRecord[0],inputEmailRecord[1],inputEmailRecord[2]);
                    emailSender.sendEmail(email);
                    emailStore.saveSentEmail(email);
                    break;

                /*
                 * Printing out all names of recipients who have birthdays to inputDate.
                 *
                 */
                case 3:
                    System.out.println("Enter Date (YYYY/MM/DD)");
                    String inputDate = scanner.nextLine();
                    for(int i=0;i<recipientList.size();i++) {
                        if(recipientList.get(i) instanceof CloseFriend) {
                            CloseFriend closeFriend = (CloseFriend) recipientList.get(i);
                            if(Date_Process.chekcBirthday(closeFriend.getBirthday(), inputDate)){
                                System.out.println(recipientList.get(i).getName());
                            }
                        }
                    }
                    break;

                /*
                 * Gets the saved mails for a given date.
                 * input format = yyyy\mm\dd
                 * input will be formatted to a convenient way to find relevant directory(folder).
                 * Prints all mails in following format:
                 * 	emailAddress subject Body .
                 */
                case 4:
                    System.out.println("Enter Date (YYYY/MM/DD)");
                    String inputDate2 = scanner.nextLine();
                    ArrayList<Email> l = emailStore.getSendEmail(Date_Process.formatDateForFilename(inputDate2));
                    for(int i=0;i<l.size();i++) {
                        Email mail = (Email) l.get(i);
                        System.out.println(mail.getemailAddress()+" 	"+mail.getEmailSubject());
                    }
                    break;

                /*
                 * Prints all the number of Recipient Objects in the application.
                 *
                 */
                case 5:
                    System.out.println(Recipient.getCount());
                    break;

                /*
                 * Prints out all details of received Emails of a given date
                 */
                case 6:
                    System.out.println("Enter Date (YYYY/MM/DD)");
                    String inputDate3 = scanner.nextLine();
                    ArrayList<Email> l1 = emailStore.getReceiveEmail(Date_Process.formatDateForFilename(inputDate3));
                    for(int i=0;i<l1.size();i++) {
                        Email mail = (Email) l1.get(i);
                        System.out.println(mail.getemailAddress()+" 	"+mail.getEmailSubject());
                    }
                    break;

                /*
                 * Prints out Received Email save file data(log)
                 */
                case 7:
                    emailStatPrinter.readText();
            }
            System.out.println();

        }
    }

    /*
     * Sends birthday mail to close friends.
     */
    public static void sendBirthdayMail(EmailSender emailSender,Email_Store emailStore,CloseFriend closeFriend) {
        BirthdayEmail birthdayEmail = new BirthdayEmail(closeFriend);
        emailSender.sendEmail(birthdayEmail);
        emailStore.saveSentEmail(birthdayEmail);
    }
}
