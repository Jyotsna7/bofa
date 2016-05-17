package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-1;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    //Test Interest Accrued after one year
    @Test
    public void checkingAccount_AfterOneYear() throws ParseException {
        Bank bank = new Bank();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Account checkingAccount = new Account(Account.CHECKING,dateFormat.parse("16/05/2015"));
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1000.0);
        System.out.println(bank.totalInterestPaid());

        //if u open an account today u dont get any interest on the same day
        assertEquals(1.00, bank.totalInterestPaid(), DOUBLE_DELTA);
        

    }


    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);
        //if u open an account today u dont get any interest on the same day
        assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
        

    }
    //Test Interest Accrued after one year
    @Test
    public void savings_account_AfterOneYear() throws ParseException {
        Bank bank = new Bank();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Account checkingAccount = new Account(Account.SAVINGS,dateFormat.parse("16/05/2015"));
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);
        System.out.println(bank.totalInterestPaid());

        //if u open an account today u dont get any interest on the same day
        assertEquals(2.00, bank.totalInterestPaid(), DOUBLE_DELTA);
        

    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        //if u open an account today u dont get any interest on the same day
       assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account_afterOneYear() throws ParseException {
        Bank bank = new Bank();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Account checkingAccount = new Account(Account.MAXI_SAVINGS,dateFormat.parse("16/05/2015"));
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);
        System.out.println(bank.totalInterestPaid());
        //if u open an account today u dont get any interest on the same day
       assertEquals(150.8, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
