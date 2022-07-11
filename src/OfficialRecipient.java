/*
 * Abstract class for official Recipient
 */
abstract class OfficialRecipient extends Recipient{

    private String post;

    public OfficialRecipient(String name, String email, String post) {
        super(name, email);
        this.post = post;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

}