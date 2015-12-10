/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NamedATM;
import java.io.*;
import java.util.*;
/**
 *
 * @author ml4kb
 */
public class NamedATM {
    private Account[] myAccounts = new Account[3];    
    public static void main(String[] args) throws Exception
    {
        NamedATM A = new NamedATM();
        A.loadAccounts();
        A.greeting();
        A.runATM();
    }
    
    void greeting()
    {       
        Scanner sc= new Scanner(System.in);
        System.out.println("Welcome, is this your first time using the ATM? (Y/N)");
        String input = sc.next();
        if(input.equalsIgnoreCase("y"))
        {
        populateAcct();
        }
        else if(input.equalsIgnoreCase("n"))
        {
        //the main method or runATM will take it to runATM
        }
        else
        {            
        System.out.println("\nPlese type in Y or N, either case is acceptable\n");
        greeting();
        }
    }
    
    void runATM() 
    {
        try
        {
           saveAccounts();
           selectAcct();
        }   
        catch(NullPointerException exc)
        {
            System.out.println("\nInvalid Input\n");    
        }   
        saveAccounts();
        greeting();
        runATM();      
    }
    
    void populateAcct()
    {
        Scanner sc = new Scanner(System.in);
        int index = -99;
        for(int i = 0; i<myAccounts.length; i++)
        {
            if(myAccounts[i] == null)
            {
               index = i;
               break;
            }
        }
        if(index==0 || index==1 || index==2)
        {
           System.out.println("\nPlease enter a name to be associated with this account");
           String name = sc.nextLine();
           if(index==1)
           {
               if(name.equalsIgnoreCase(myAccounts[0].getName()))
               {
                  System.out.println("\nSorry, that name is already in use\n");
                  greeting();
               }
               else
               {
                   System.out.println("\nPlease enter a PIN to use with this account");
                   String PIN = sc.nextLine();
                   myAccounts[index] = new Account(100, index, name, PIN);
                   System.out.println("\nPleased to meet you, Mr./Ms. " + name);
                   System.out.println("Balances start at $100, and the annual interest rate is 5%");
               }
           }
           else if(index==2)
           {
               if(name.equalsIgnoreCase(myAccounts[0].getName()) || name.equalsIgnoreCase(myAccounts[1].getName()))
               {
                   System.out.println("\nSorry, that name is already in use\n");
                   greeting();
               }
               else
               {
                   System.out.println("\nPlease enter a PIN to use with this account");
                   String PIN = sc.nextLine();
                   myAccounts[index] = new Account(100, index, name, PIN);
                   System.out.println("\nPleased to meet you, Mr./Ms. " + name);
                   System.out.println("Balances start at $100, and the annual interest rate is 5%");
               }    
           }
           else
           {    
           System.out.println("\nPlease enter a PIN to use with this account");
           String PIN = sc.nextLine();
           myAccounts[index] = new Account(100, index, name, PIN);
           System.out.println("\nPleased to meet you, Mr./Ms. " + name);
           System.out.println("Balances start at $100, and the annual interest rate is 5%");
           }
        }
        else
        {
            System.out.println("\nSorry, all availible accounts are full\n");
            greeting();
        }    
    }    
        
    void selectAcct()
    {
        Scanner sc = new Scanner(System.in);
        int num = -99;
        String name, PIN;
        
        try
        {
           System.out.println("\nEnter your account name");
           name = sc.nextLine();
           System.out.println("\nEnter your PIN");
           PIN = sc.nextLine();
           if(name.equalsIgnoreCase(myAccounts[0].getName())){
              if(PIN.equalsIgnoreCase(myAccounts[0].getPIN()))
                 num=0;
           }
           else if(name.equalsIgnoreCase(myAccounts[1].getName())){
              if(PIN.equalsIgnoreCase(myAccounts[1].getPIN())) 
                 num=1;
           }  
           else if(name.equalsIgnoreCase(myAccounts[2].getName())){
               if(PIN.equalsIgnoreCase(myAccounts[2].getPIN()))
               num=2;
           }   
           System.out.println();
           myAccounts[num].Menu();
        }
        catch(ArrayIndexOutOfBoundsException exc)
        {
             System.out.println("\nInvalid Input");
             selectAcct();
        } 
    }   
    
    public void loadAccounts()
    {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("file1.out")))
        {
            myAccounts = (Account[]) ois.readObject();
        }
        catch(IOException | ClassNotFoundException e) 
        {
            myAccounts = new Account[3]; 
        }
    }
    
    public void saveAccounts()
    {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("file1.out")))
        {
            oos.writeObject(myAccounts);
            oos.flush();
        }
        catch (Throwable e)
	{
            System.err.println(e);
        }
    }
}