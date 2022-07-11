/*
 * Constructor gets necessary details for email.
 */
class Email implements Serializable{

    private String emailAddress ;
    private String emailSubject ;
    private String emailBody ;
    protected static final long serialVersionUID = 40L;

    public Email(String emailAddress, String emailSubject, String emailBody) {

        //emailAddress = Other address associated with email object
        //emailAddress = sender address if email is sent
        //emailAddress = receiver address if email is received
        this.emailAddress = emailAddress;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
    }

    /*
     * Getters and setters to access data from outside the class
     */
    public String getemailAddress() {
        return emailAddress;
    }

    public void setemailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }
}