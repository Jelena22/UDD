package com.example.ddmdemo.util;

import java.util.*;

public class InfixToPostfixConverter {

    // Mapa prioriteta operatora
    private static final Map<String, Integer> precedenceMap;

    static {
        precedenceMap = new HashMap<>();
        precedenceMap.put("NOT", 3); // Najviši prioritet
        precedenceMap.put("AND", 2); // Srednji prioritet
        precedenceMap.put("OR", 1);  // Najniži prioritet
    }

    public static List<String> convertToPostfix(List<String> infixExpression) {
        List<String> postfix = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();

        for (String token : infixExpression) {
            if (isOperand(token)) {
                postfix.add(token);
            } else if (isOperator(token)) {
                while (!stack.isEmpty() && precedenceMap.get(token) <= precedenceMap.get(stack.peek())) {
                    postfix.add(stack.pop());
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            postfix.add(stack.pop());
        }

        return postfix;
    }

    public static boolean isOperand(String token) {
        return !precedenceMap.containsKey(token);
    }

    public static boolean isOperator(String token) {
        return precedenceMap.containsKey(token);
    }

    public static void main(String[] args) {
        List<String> infixExpression = List.of("content:zakon", "AND", "content:ugovor", "OR", "content:policija", "NOT", "content:put");
        List<String> postfixExpression = convertToPostfix(infixExpression);
        System.out.println(postfixExpression);
    }
}
