/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NamedATM;
import java.io.*;
import java.text.*;
import java.util.*;
/**
 *
 * @author Michael
 */
class Account implements Serializable{
    
    protected double balance;
    protected double rate;
    protected int firstdate;
    protected int firstyear;
    protected int seconddate;
    protected int secondyear;
    protected Calendar date1 = new GregorianCalendar();
    protected Calendar date2 = new GregorianCalendar();
    protected boolean firstdateflag = false;
    protected int acctNumber;
    protected String acctName;
    protected String acctPIN;
    
    public Account(int bal, int num, String name, String PIN){
        balance = bal;
        acctNumber = num;
        acctName = name;
        acctPIN = PIN;
    }
    
    String getName(){
        return acctName;
    }
    
    String getPIN(){
        return acctPIN;
    }
    
    public void Menu()
    {
        Scanner sc = new Scanner(System.in);
        try{
            int input;
            do{
            System.out.println( "|==================================|");
            System.out.println( "|        FIRST NATIONAL BANK       |");
            System.out.println( "|***********Menu Options***********|");
            System.out.println( "|__________________________________|");
            System.out.println( "|  Press 1 To Make Deposit         |");
            System.out.println( "|  Press 2 To Make Withdrawl       |");
            System.out.println( "|  Press 3 To View Current Balance |");
            System.out.println( "|  Press 4 To Save/Exit            |");
            System.out.println( "|__________________________________|");
            System.out.println( "|   Please Make Selection Now...   |");
            System.out.println( "|==================================|");
            System.out.println();

            input=sc.nextInt();

            System.out.println();

            if(input==1)
            {
                if(firstdateflag == false){    
                    getDate1();    
                    Deposit();
                }
                else if(firstdateflag == true){
                    getDate2();
                    getInterest();
                    Deposit();
                }
            }
            else if (input==2)
            {
            if(firstdateflag == false){    
                    getDate1();    
                    Withdraw();
                }
                else if(firstdateflag == true){
                    getDate2();
                    getInterest();
                    Withdraw();
                }
            }
            else if (input==3)
            {
            if(firstdateflag == false){    
                    getDate1();    
                    CheckBalance();
                }
                else if(firstdateflag == true){
                    getDate2();
                    getInterest();
                    CheckBalance();
                }
            }
            else if (input==4)
            {
            //do nothing
            }
            else
            { 
            System.out.println("Invalid input, please choose 1, 2, 3, or 4");    
            }
            System.out.println();
            }while(input != 4);
        }
        catch(InputMismatchException exc)
        {
            System.out.println("\nInvalid input, closing account\n");
             
        }   
    }
    
    protected void Deposit()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nHow much would you like to deposit? $");
        double deposit = sc.nextDouble();
        if(deposit > 0)
        {
           balance += deposit;
           System.out.println("\nDeposit of " + NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(deposit) + " successful");
        }
        else
        {
           System.out.println("\nYou must enter a positive number");
        }        
    }
    
    protected void Withdraw()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nHow much would you like to withdraw? $");
        double withdrawl = sc.nextDouble();
        if(withdrawl > 0)
        {
           if(withdrawl<=balance)
           {
                balance -= withdrawl;
                System.out.println("\nHere is " + NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(withdrawl));
           }
           else
           {
               System.out.println("\nInsufficent Funds");
           }
        }
        else
        {
            System.out.println("\nYou must enter a positive number");
        }    
    }
    
    protected void CheckBalance()
    {
        System.out.println("\nYour balance is: " + NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(balance));
    }
    
    protected void getDate1(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter today's date in the format: mm/dd/yyyy");
        String input = sc.next();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(input, pos);
        
        date1.setTime(date);
        
        firstdate = date1.get(Calendar.DAY_OF_YEAR);
        firstyear = date1.get(Calendar.YEAR);
        
        firstdateflag = true;
    }
    
    protected void getDate2(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter today's date in the format: mm/dd/yyyy");
        String input = sc.next();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        ParsePosition pos = new ParsePosition(0);
        Date date = formatter.parse(input, pos);
        
        date2.setTime(date);
        
        seconddate = date2.get(Calendar.DAY_OF_YEAR);
        secondyear = date2.get(Calendar.YEAR);
        
        if(firstyear > secondyear || firstyear == secondyear && firstdate > seconddate)
        {
            System.out.println("\nYou must enter a later date than the previous date used\n");
            getDate2();
        }
    }
    
    protected void getInterest()
    {
        int datediff = seconddate - firstdate;
        rate = .05/365;
        double ratetime = Math.pow(1+rate, datediff);
                
        if(secondyear - firstyear > 0) //allows for different years to be input
        {
            int yeardiff = secondyear - firstyear;
            ratetime *= Math.pow(1.05, yeardiff);
        }
        balance *= ratetime;
        firstdate = seconddate;
        firstyear = secondyear;
    }
}