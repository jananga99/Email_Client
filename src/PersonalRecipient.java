/*
 * PersonalRecipient class.
 */
class PersonalRecipient extends Recipient implements CloseFriend{

    private String nickname;
    private String birthday;

    public PersonalRecipient(String name, String email, String nickname, String birthday) {
        super(name, email);
        this.nickname = nickname;
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}