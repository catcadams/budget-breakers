package org.launchcode.budget_planning_backend.models;

import java.time.LocalDate;
import java.time.Period;

public class AccountTypeUtil {

    public static int calculateUserAge(User user) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthdate = LocalDate.of(user.getDateOfBirth().getYear(), user.getDateOfBirth().getMonth(), user.getDateOfBirth().getDayOfMonth());
        Period period = Period.between(birthdate,currentDate);
        return period.getYears();
    }

    public static void determineAccountType(User user) {
        if(calculateUserAge(user) >= 18) {
            user.setAccountType(AccountType.ADULT);
        } else {
            user.setAccountType(AccountType.MINOR);
        }
    }
}
