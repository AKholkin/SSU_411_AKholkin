package com.company;
import java.io.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.ArrayList;


public class Main {


    private static class Company {
        private String name;
        private int businessAccount;
        private BigDecimal budget;
        private ArrayList<String> exNames;
        private ArrayList<Integer> exAccounts;

        public Company(String newName, int newAccount) {
            name = new String(newName);
            businessAccount = newAccount;
            budget = new BigDecimal(0, new MathContext(2));
        }

        public Company(String newName, int newAccount, BigDecimal startBudget) {
            name = new String(newName);
            businessAccount = newAccount;
            budget = startBudget;
        }

        public void depositFunds(BigDecimal value) {
            budget = budget.add(value);
        }

        public boolean debitFunds(BigDecimal value) {
            if (budget.compareTo(value) >=0) {
                budget = budget.subtract(value);
                return true;
            }
            return false;
        }

        public String getName() {
            return name;
        }

        public int getBusinessAccount() {
            return businessAccount;
        }

        public BigDecimal getBudget() {
            return budget;
        }

        public void setName(String name) {
            name = name;
        }

        public void setBusinessAccount(int businessAccount) {
            businessAccount = businessAccount;
        }

    }



    public static void main(String[] args) {

        HashMap<Integer, Company> companies = new HashMap<Integer, Company>();
        String[] content;
        String line;
        Integer businessAcc;
        String fileName = "information.csv";
        BigDecimal funds;
        Company remitter, remittee;
        int lineNum;

        try {
            BufferedReader r = new BufferedReader(new FileReader(fileName));
            r.readLine();
            while ((line = r.readLine()) != null) {
                content = line.split(";");
                businessAcc = Integer.valueOf(content[1]);
                companies.put(businessAcc, new Company(content[0], businessAcc, new BigDecimal(content[2], new MathContext(2))));
            }
            r.close();
        } catch (IOException e) {
            System.out.println("Файл " + fileName + " не найден");
        }

        File folder = new File("test");
        for (File file : folder.listFiles()) {
            fileName = file.getPath();
            if (fileName.matches("^test\\\\transaction[0-9]+\\.csv$")) {
                try {
                    BufferedReader r = new BufferedReader(new FileReader(fileName));
                    lineNum = 1;
                    r.readLine();
                    while ((line = r.readLine()) != null) {
                        content = line.split(";");
                        businessAcc = Integer.valueOf(content[1]);
                        if (companies.containsKey(businessAcc) && companies.get(businessAcc).getName().equals(content[0])) {
                            remitter = companies.get(businessAcc);
                            businessAcc = Integer.valueOf(content[3]);
                            if (companies.containsKey(businessAcc) && companies.get(businessAcc).getName().equals(content[2])) {
                                remittee = companies.get(businessAcc);
                                funds = new BigDecimal(content[4]);
                                if (remitter.debitFunds(funds)) {
                                    remittee.depositFunds(funds);
                                    System.out.println(fileName + " строка " + lineNum + ". Осуществлён перевод"
                                            + ". Отправитель: " + content[0] + ". Получатель: " + content[2] + ". Сумма: " + content[4]);
                                } else
                                    System.out.println(fileName + " строка " + lineNum + ". Недостаточно средств для перевода");
                            } else System.out.println(fileName + " строка " + lineNum + ". Получатель не существует");
                        } else System.out.println(fileName + " строка " + lineNum + ". Отправитель не существует");
                        lineNum++;
                    }
                    r.close();
                } catch (IOException e) {
                    System.out.println("Файл " + fileName + " не обработан");
                }
            }
        }

        DecimalFormat df = new DecimalFormat("#########.##");
        DecimalFormat intf = new DecimalFormat("00000");



        fileName = "result.csv";
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(fileName));
            w.write("");
            w.append("Имя компании;Рассчётный счёт;Бюджет\n");
            System.out.println("\nResult");
            for (Company company : companies.values()) {
                System.out.println(company.getName() + ";" + company.getBusinessAccount() + ";" + df.format(company.getBudget()));
                w.append(company.getName() + ";" + intf.format(company.getBusinessAccount()) + ";" + df.format(company.getBudget()) + "\n");
            }

            w.close();
        }
        catch (IOException e) {
            System.out.println("Файл " + fileName + " не найден");
        }

    }
}
