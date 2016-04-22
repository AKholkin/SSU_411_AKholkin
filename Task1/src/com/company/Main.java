package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("part 1");
        Scanner in = new Scanner(System.in);
        int year=-1;
        while(year<0||year>2020) {
            System.out.print("Enter the year: ");
            try{year = Integer.parseInt(in.nextLine());}
            catch (Exception e){}
            if(year<0||year>2020)
                System.out.println("Incorrect year (have to be 0...2020)");
        }
        DateFormat dateFormat = new SimpleDateFormat("MMMM");
        Calendar cal = Calendar.getInstance();
        cal.set(year,0,13);
        for(int i=0;i<12;i++) {
            cal.set(cal.MONTH, i);
            if(cal.get(cal.DAY_OF_WEEK)==cal.FRIDAY){
                System.out.println(dateFormat.format(cal.getTime()));}
        }

        System.out.println("part 2");

        year = 1;
        int month=-1;
        Calendar calendar = Calendar.getInstance();

        while (month < 1 || month > 12) {
            System.out.print("Enter the month: ");
            try {month = Integer.parseInt(in.nextLine());}
            catch (Exception e){}
            if (month < 1 || month > 12)
                System.out.println("Incorrect month (have to be 1...2");
        }
        calendar.set(calendar.DAY_OF_MONTH,13);
        calendar.set(calendar.YEAR,year);
        calendar.set(calendar.MONTH,month-1);
        int count = 0;
        while (calendar.compareTo(Calendar.getInstance())<0){
            if(calendar.get(calendar.DAY_OF_WEEK) == calendar.FRIDAY) {
                System.out.println(calendar.get(calendar.YEAR));
                count++;
            }
            year++;
            calendar.set(calendar.YEAR,year);
        }
        System.out.println("Count is (Since year 1):" + count);



    }
}

