/*
 * class for Close friend Office recipient
 */
class CloseOfficialRecipient extends OfficialRecipient implements CloseFriend{

    private String birthday;

    public CloseOfficialRecipient(String name, String email, String post, String birthday) {
        super(name, email, post);
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }

}