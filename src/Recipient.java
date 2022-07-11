/*
 * Abstract class for Recipient.
 * Holds common data and methods needed for recipient objects.
 */
abstract class Recipient {

    private String name;
    private String email;
    private static int count=0;

    public Recipient(String name, String email) {
        this.name = name;
        this.email = email;
        count+=1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static int getCount() {
        return count;
    }

}
