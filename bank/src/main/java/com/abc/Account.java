package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    
    private Date accountStartDate;
    private Date latestWithDrawalDate;
    

    public Date getAccountStartDate() {
		return accountStartDate;
	}

	public void setAccountStartDate(Date accountStartDate) {
		this.accountStartDate = accountStartDate;
	}

	private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.accountStartDate=DateProvider.getInstance().now();
    }
    
    public Account(int accountType,Date startDate) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.accountStartDate=startDate;
    }


    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
        latestWithDrawalDate = DateProvider.getInstance().now();
    	transactions.add(new Transaction(-amount));
        
    }
}

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000){
                    
                	return amount * calculateIntAccrual(0.001);
                    }
                else
                    return (1000 * calculateIntAccrual(0.001)) + (amount-1000) * calculateIntAccrual(0.002);
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
            	//if no withdrawals in last 10 days then change the interest rate to 5%
            	if(!checkAnyWithDrawalsInLast10Days())
            		return amount * calculateIntAccrual(0.05);
                if (amount <= 1000)
                    return amount * calculateIntAccrual(0.02);
                if (amount <= 2000)
                    return (1000)*calculateIntAccrual(0.02) + (amount-1000) * calculateIntAccrual(0.05);
                return (1000)*calculateIntAccrual(0.02)+(1000)*calculateIntAccrual(0.05) + (amount-2000) * calculateIntAccrual(0.1);
            default:
                return amount * calculateIntAccrual(0.001);
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }
    
    public boolean checkAnyWithDrawalsInLast10Days(){
    	double ageOfAcct =(DateProvider.getInstance().now().getTime()-accountStartDate.getTime())/(1000*60*60*24);
    	
    	if(latestWithDrawalDate!=null){
    	long diffBetweenWithDrawalAndCurrentDate = DateProvider.getInstance().now().getTime()-latestWithDrawalDate.getTime();
    	double diffDays =diffBetweenWithDrawalAndCurrentDate/(1000*60*60*24);
    	
    	return diffDays<=10?true:false;
    	}
    	//if no withdrawals in the last 10 days since the account opening
    	else if(ageOfAcct>10)
    		return false;
    	else
    		return true;
    }
    
    //since interest accrues daily we need to calculate the interest based on the number of days.
    private double calculateIntAccrual(double interestRate){
    	double dailyIntRate = interestRate/365;
    	long timeInMillis=DateProvider.getInstance().now().getTime()- accountStartDate.getTime();
    	double days = (int) (timeInMillis / (1000*60*60*24));
    	return dailyIntRate*days;
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
