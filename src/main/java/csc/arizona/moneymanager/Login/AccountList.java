package csc.arizona.moneymanager.Login;

import java.util.HashSet;

public class AccountList {

    private final HashSet<Account> accountList;

    public AccountList() {
        accountList = new HashSet<>();
    }

    public Account getAccount(String username) {
        for (Account account : accountList)
            if (account.getUsername().equals(username))
                return account;
        return null;
    }

    public void addAccount(Account user) {
        accountList.add(user);
    }
}
