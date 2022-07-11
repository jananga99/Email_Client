/*
 * This class has static methods and attributes required for all date
 *  processing in the application.
 *  Has a static String attribute 'today' to denote today.
 */
abstract class Date_Process {

    private static Date date = Calendar.getInstance().getTime();
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");;
    private static String today = dateFormat.format(date);

    //returns today as a String in the format 'yyyy/MM/dd'
    public static String getToday() {
        return today;
    }

    //returns true if birthday is in today, false otherwise.
    public static boolean checkBirthdayToday(String birthday) {
        return chekcBirthday(birthday, today);
    }

    //checks given two dates are birthdays. Dates are given in format
    //  'yyyy/MM/dd'. Returns true if birthdays, false otherwise.
    public static boolean chekcBirthday(String birthday, String day) {
        String[] birthdayData = birthday.split("/");
        String[] dayData = day.split("/");
        int birthdayMonth = Integer.parseInt(birthdayData[1]);
        int birthdayDate = Integer.parseInt(birthdayData[2]);
        int dayMonth = Integer.parseInt(dayData[1]);
        int dayDate = Integer.parseInt(dayData[2]);
        return birthdayMonth==dayMonth && birthdayDate==dayDate;
    }

    //converts any given date string to standard form here, 'yyyy/MM/dd'.
    public static String formatDateToStandard(String date) {
        String standardDate = date.substring(0,4)+"/"+date.substring(5,7)+"/"+date.substring(8,10);
        return standardDate;
    }

    //cannot use'/' in filenames. '_' can be used instead.
    //converts 'yyyy/MM/dd' format to 'yyyy_MM_dd'.
    public static String formatDateForFilename(String date) {
        String[] dateData = date.split("/");
        String year = dateData[0];
        String month = dateData[1];
        if(month.length()==1)
            month = "0"+month;
        String day = dateData[2];
        if(day.length()==1)
            day = "0"+day;
        String filenameDate = year+"_"+month+"_"+day;
        return filenameDate;
    }

    //returns current time
    public static String getCurrentTime() {
        return LocalDate.now().getYear()+"/"+LocalDate.now().getMonthValue()+"/"
                +LocalDate.now().getDayOfMonth()+"  "+LocalTime.now().getHour()+":"
                +LocalTime.now().getMinute()+":"+LocalTime.now().getSecond();
    }

}