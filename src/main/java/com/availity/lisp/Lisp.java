package com.availity.lisp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


 
/**
 * @author Sergiy Velytskyy
 * 
 *  You are tasked to write a checker that validates the parentheses of a LISP code.  
 *  Write a program (in Java or JavaScript) which takes in a string as an input and returns true if all the parentheses 
 *  in the string are properly closed and nested.
 *  
 *  The solution is based on Stack : 
 * 	All open parenthesis positions in a LISP string goes to stack
 *  and remove from the stack if closed parenthesis is found.
 *  
 *   There are 3 result case :
 *   
 *   1. Stack is empty => Lisp string is valid
 *   2. Stack is not empty => open number of parenthesizes more than closed
 *   3. During delete element from stack it occurs that stack doesn't have elements in it => closed parenthesis number is more
 * 
 *
 */
public class Lisp {

    

    private static char PARANTHESIS_1 = '(';
    private static char PARANTHESIS_2 = ')';
    
    //Lisp string
    private static final String str = "( ( (a+b)+(c-d) ) () lk;sagfjhkals; () );<>";
   

    public static void main(String[] args) throws Exception{

          Stack<Integer> s = new Stack<Integer>();
          int position = 0;
          for (char c : str.toCharArray()){
                 if(PARANTHESIS_1 == c){
                        Integer posParenthesis = new Integer(position);
                        s.push(posParenthesis);
                 }
                 else if(PARANTHESIS_2 == c){
                        if(s.isEmpty()){
                              throw new Exception("Lisp error, Position : " + position + ", parenthesis is not open");
                        }
                        s.pop();
                 }
                 position++;
          }
          if(!s.isEmpty()){
                 List<Integer> positions = new ArrayList<Integer>();
                 for (Iterator<Integer> iterator = s.iterator(); iterator.hasNext();) {
                        Integer p = iterator.next();
                        positions.add(p);
                 }
                 throw new Exception("Lisp error, Positions : " + positions + ", parenthesis are not closed");
          }else{
                System.out.println("Lisp parenthesis are valid");
          }
    }
}