/*
 * Recipient_Process class processes records according to requirements.
 */
class Recipient_Process {

    private ArrayList<Recipient> recipientList;
    private ArrayList<CloseFriend> todayBirthdayList;

    public Recipient_Process() {
        recipientList = new ArrayList<Recipient>();
        todayBirthdayList= new ArrayList<CloseFriend>();
    }

    //Creates and adds recipient object to recipientList and updates todayBirthdayList.
    public void addRecipientList(ArrayList<String[]> recordList) {
        for(int i=0;i<recordList.size();i++) {
            addRecipient(recordList.get(i));
        }
    }

    public ArrayList<Recipient> getRecipientList() {
        return recipientList;
    }

    public ArrayList<CloseFriend> getTodayBirthdayList() {
        return todayBirthdayList;
    }

    //Creates objects according to the type in record, add them to recipientList and
    // to todayBirthdayList if recipient has birthday today.
    public void addRecipient(String record[]) {
        if(record[0].equals("Official")) {
            recipientList.add(new NonCloseOfficialRecipient(record[1],record[2],record[3]));
        }else if(record[0].equals("Office_friend")) {
            CloseOfficialRecipient officeFriend = new CloseOfficialRecipient(record[1],record[2],record[3],record[4]);
            recipientList.add(officeFriend);
            if(Date_Process.checkBirthdayToday(record[4])) {
                todayBirthdayList.add(officeFriend);
            }
        }else if(record[0].equals("Personal")) {
            PersonalRecipient personalFriend = new PersonalRecipient(record[1],record[2],record[3],record[4]);
            recipientList.add(personalFriend);
            if(Date_Process.checkBirthdayToday(record[4])) {
                todayBirthdayList.add(personalFriend);
            }
        }else {
            System.out.println("Invalid input format");
        }
    }

}