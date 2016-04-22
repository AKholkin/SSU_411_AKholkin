package com.company;

import java.util.*;
import java.util.regex.Pattern;
import java.lang.Math;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        StringBuilder input = new StringBuilder();
        String inputStr;

        do {
            if (input.toString().equals("")) {
                System.out.print("Enter expression (or exit)\n->");
                input.append(in.nextLine());
            } else {
                inputStr = input.toString()
                        .replaceAll("[,]+", "\\.")
                        .replaceAll("[^*+-/^\\d\\.()]+", "");

                input.delete(0, input.length());
                input.append(inputStr);

                System.out.println("Transformed expression: " + input.toString());

                try {
                    System.out.println("Evaluated expression = " + calc(input).toString());
                } catch (Exception e) {
                    System.out.println("Error!\n Try again...");
                } finally {
                    input.delete(0, input.length());
                }
            }
        } while (!input.toString().toLowerCase().equals("exit"));

    }

    private static Double calc(StringBuilder exp) {
        ArrayDeque<Double> st = new ArrayDeque<Double>();
        ArrayDeque<Character> op = new ArrayDeque<Character>();
        StringBuilder operand = new StringBuilder();
        char ch;
        for (int i = 0; i < exp.length(); i++) {
            ch = exp.charAt(i);
            if (ch == '(') {
                op.push(ch);
            }
            else if (ch == ')') {
                while (op.peek() != '(') {
                    process_op(st, op.pop());
                }
                op.pop();
            }
            else if (is_op(ch)) {
                while (!op.isEmpty() && priority(op.peek()) >= priority(ch)) {
                    process_op(st, op.pop());
                }
                op.push(ch);
            } else {
                operand.delete(0, operand.length());
                while (i < exp.length() && Pattern.matches("[\\d\\.]", String.valueOf(exp.charAt(i)))) {
                    operand.append(exp.charAt(i++));
                }
                i--;
                if (Character.isDigit(operand.charAt(0))) {
                    st.push(Double.parseDouble(operand.toString()));
                }
            }
        }

        while (!op.isEmpty()) {
            process_op(st, op.pop());
        }

        return st.pop();
    }

    private static void process_op(ArrayDeque<Double> st, char op) {
        double r = st.pop();
        double l = st.pop();

        switch (op) {
            case '+': st.push(l + r); break;
            case '-': st.push(l - r); break;
            case '*': st.push(l * r); break;
            case '/': st.push(l / r); break;
            case '^': st.push(Math.pow(l, r)); break;
        }
    }

    private static int priority(char op) {
        CharSequence operation = Character.valueOf(op).toString();
        return "+-".contains(operation) ? 1 :
                "*/".contains(operation) ? 2 :
                        "^".contains(operation) ? 3 :
                                -1;
    }

    private static boolean is_op(char op) {
        return "+-*/^".contains(Character.valueOf(op).toString());
    }
}