/*
 * Email class is used for sending mails.
 * Constructor gets necessary details for email.
 * Sender address and password must be hard coded.
 * Has the method to send the email.
 */
class EmailSender {

    private String senderAddress;
    private String senderPassword;
    private final String emailSMTPserver;
    private final String emailServerPort;

    public EmailSender() {
        senderAddress = "Sender Email Address";
        senderPassword = "Sender Email Password";
        emailSMTPserver = "smtp.gmail.com";
        emailServerPort = "465";
    }

    /*
     * Getters and setters to access data from outside the class
     * Sender password is only be accessible from inside the class.
     */
    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }


    /*
     * sendEmail method will send the email to receiverAddress form senderAddress.
     * Ports and servers are defined.
     */
    public void sendEmail(Email email) {
        Properties properties = new Properties();
        properties.put("mail.smtp.user",senderAddress);
        properties.put("mail.smtp.host", emailSMTPserver);
        properties.put("mail.smtp.port", emailServerPort);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", emailServerPort);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        class SMTPAuthenticator extends javax.mail.Authenticator{
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderAddress, senderPassword);
            }
        }

        try{
            Authenticator authenticator = new SMTPAuthenticator();
            Session session = Session.getInstance(properties, authenticator);
            MimeMessage message = new MimeMessage(session);
            message.setText(email.getEmailBody());
            message.setSubject(email.getEmailSubject());
            message.setFrom(new InternetAddress(senderAddress));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(email.getemailAddress()));
            Transport.send(message);
        }

        catch (Exception mex){
            mex.printStackTrace();
        }
    }


}
