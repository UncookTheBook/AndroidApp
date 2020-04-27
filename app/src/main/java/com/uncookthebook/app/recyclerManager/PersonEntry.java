package com.uncookthebook.app.recyclerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PersonEntry {
    @NonNull String name;
    @NonNull Integer points;


    public static List<PersonEntry> initProductEntryList(){
        List<PersonEntry> personEntries = generateRandomData();
        return personEntries;
    }

    private static List<PersonEntry> generateRandomData(){
        List<PersonEntry> temp = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            temp.add(new PersonEntry(getAlphaNumericString(random.nextInt(12)),
                    random.nextInt(500)));
        }
        return temp;
    }

    // function to generate a random string of length n
    private static String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
