/*
 * Subclass of Email
 * Email message is processed as a birthday greeting
 */
class BirthdayEmail extends Email{

    private static final long serialVersionUID = 41L;

    public BirthdayEmail(CloseFriend closeFriend) {
        super("", "BirthDay Greeting", "");
        if(closeFriend instanceof PersonalRecipient) {
            PersonalRecipient personalRecipient = (PersonalRecipient) closeFriend;
            setemailAddress(personalRecipient.getEmail());
            setEmailBody("hugs and love on your birthday Jananga");
        }else if(closeFriend instanceof CloseOfficialRecipient) {
            CloseOfficialRecipient closeOfficialRecipient = (CloseOfficialRecipient) closeFriend;
            setemailAddress(closeOfficialRecipient.getEmail());
            setEmailBody("Wish you a happy birthday Jananga");
        }
    }

}