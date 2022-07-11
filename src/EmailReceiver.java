import java.util.ArrayList;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;
import javax.mail.BodyPart;
import javax.mail.internet.*;
import javax.mail.internet.InternetAddress;


/*
 * Email Receiving object.
 * Runs on a separate thread.
 * Emails are reterived  from using imaps.
 * Only reterived unread emails one by one.
 * If any unread email reterived, this will add its details to
 *    MyBlockingQueue. (This is the producer for MyBlockingQueue)
 * Also related observers is updated.
 */
class EmailReceiver extends Thread{
    private String receiverAddress;
    private String receiverPassword;
    private MyBlockingQueue myBlockingQueue;
    Properties properties;
    Session emailSession;
    Store emailStore;
    Folder emailFolder;
    private ArrayList<Observer> observers;

    public EmailReceiver(MyBlockingQueue myBlockingQueue)  {
        this.myBlockingQueue = myBlockingQueue;
        receiverAddress = "Receiver Email Address";
        receiverPassword = "Receiver Email Password";
        properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        observers = new ArrayList<Observer>();
    }

    public void run() {
        while(true) {
            receiveEmail();
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    //email receiving method
    public void receiveEmail() {
        try {
            emailSession = Session.getDefaultInstance(properties);
            emailStore = emailSession.getStore("imaps");
            emailStore.connect("imap.gmail.com",receiverAddress, receiverPassword);
            emailFolder = emailStore.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);
            Message[] messages = emailFolder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            if(messages.length>0) {
                Message message = messages[0];
                emailFolder.setFlags(new Message[] {messages[0]}, new Flags(Flags.Flag.SEEN), true);  //for a single message mark as read
                String[] a = {((InternetAddress) message.getFrom()[0]).getAddress(),message.getSubject(),getTextFromMessage(message)};
                myBlockingQueue.enqueue(a);
                notifyAllObservers();
            }
            emailFolder.close(false);
            emailStore.close();
        } catch (NoSuchProviderException e) {e.printStackTrace();}
        catch (MessagingException e) {e.printStackTrace();}
    }

    /*
     * below two methods are used to get content from message object.
     * Need to use methods since it can be a Message or MimeMultipart
     */
    private String getTextFromMessage(Message message)  {
        String result = "";
        try {

            //If message MimeType is text/plain , content can be get by getContent.
            if (message.isMimeType("text/plain")) {
                result = message.getContent().toString();

                //If message MimeType is multipart, have to use another method to get content.
            } else if (message.isMimeType("multipart/*")) {
                MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
                result = getTextFromMimeMultipart(mimeMultipart);
            }
        }catch (MessagingException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        return result;
    }
    private String getTextFromMimeMultipart(MimeMultipart mimeMultipart)  {
        String result = "";
        try {
            int count = mimeMultipart.getCount();

            //gets the result by adding body parts to result
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);

                if (bodyPart.isMimeType("text/plain")) {
                    result = result + "\n" + bodyPart.getContent();
                    break;
                } else if (bodyPart.isMimeType("text/html")) {
                    //If bodypart MimeType is text/html need org.Jsopu... to get content.
                    String html = (String) bodyPart.getContent();
                    result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
                } else if (bodyPart.getContent() instanceof MimeMultipart){
                    //If bodypart is MimeMultiPart need to use this method on body parts
                    result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
                }
            }
        }catch (MessagingException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        return result;
    }

    //Notifies all observers
    public void notifyAllObservers() {
        for(Observer observer:observers) {
            observer.update();
        }
    }

}
