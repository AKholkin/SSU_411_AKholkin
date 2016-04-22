package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;




public class Main {



    private interface IBasket {
        void addProduct(String product, int quantity);
        void removeProduct(String product);
        void updateProductQuantity(String product, int quantity);
        void clear();
        List<String> getProducts();
        int getProductQuantity(String product);
    }


    private static class Basket implements IBasket {
        private HashMap<String, Integer> basket;

        Basket() {
            basket = new HashMap<String, Integer>();
        }

        @Override
        public void addProduct(String product, int quantity) {
            basket.put(product, quantity);
        }

        @Override
        public void removeProduct(String product) {
            basket.remove(product);
        }

        @Override
        public void updateProductQuantity(String product, int quantity) {
            basket.replace(product, quantity);
        }

        @Override
        public void clear() {
            basket.clear();
        }

        @Override
        public List<String> getProducts() {
            return new ArrayList<String>(basket.keySet());
        }

        @Override
        public int getProductQuantity(String product) {
            return basket.get(product);
        }

    }
    public static void main(String args[]){

        Basket b = new Basket();
        Scanner in = new Scanner(System.in);
        String command, product, quantity;
        do {
            System.out.println("--commands");
            System.out.println("--add");
            System.out.println("--remove");
            System.out.println("--update");
            System.out.println("--clear");
            System.out.println("--getCount");
            System.out.println("--getAll");
            System.out.println("--quit");
            System.out.print("> "); command=in.nextLine();


            switch(command)
            {
                case "add":
                    System.out.print("product name: ");
                    product=in.nextLine();
                    System.out.print("product quantity: ");
                    quantity=in.nextLine();
                    try{
                        int count=Integer.parseInt(quantity);
                        b.addProduct(product, count);
                        System.out.println("Done");
                    }
                    catch(Exception e){System.out.println("Haven't done. Error input!");}
                    break;
                case "remove":
                    System.out.print("product name: ");
                    product=in.nextLine();
                    try{b.removeProduct(product);System.out.println("Done");}
                    catch(Exception e){System.out.println("Not done. Error input!");}
                    break;
                case "update":
                    System.out.print("product name: ");
                    product=in.nextLine();
                    System.out.print("product quantity: ");
                    quantity=in.nextLine();
                    try{
                        int count=Integer.parseInt(quantity);
                        b.updateProductQuantity(product, count);
                        System.out.println("Done");
                    }
                    catch(Exception e){System.out.println("Not done. Error input!");}
                    break;
                case "clear":
                    b.clear();
                    break;
                case "getCount":
                    System.out.print("product name: ");
                    product=in.nextLine();
                    try {System.out.println(b.getProductQuantity(product));}
                    catch(Exception e){System.out.println("Not done. Error input!");}
                    break;
                case "getAll":
                    for(String prd:b.getProducts())
                        System.out.println(prd);
                    break;
                case"quit": break;
                default: System.out.println("Check the command");
            }
        }
        while(!command.equals("quit"));

    }
}