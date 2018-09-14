package br.com.testecbmarvel.testecbmarvel.extras;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

public class Util {

    public long timestamp(){
        Date date = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MILLISECOND, 0);
        date = new java.sql.Timestamp(c.getTime().getTime());

        long dateInMillis = date.getTime()/1000;

        return dateInMillis;
    }

    public String md5(){

        long date = timestamp();

        String s = date + Keys.PRIVATE_KEY + Keys.PUBLIC_KEY;
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(s.getBytes(),0,s.length());

        String md5 = new BigInteger(1,m.digest()).toString(16);

        System.out.println("TIMESTAMP: "+date + " MD5: " + md5);

        return md5;
    }

//    public String url(){
//        String baseUrl = "http://gateway.marvel.com/v1/public/characters?ts=" + timestamp() + "&apikey="+ Keys.PUBLIC_KEY + "&hash=" + md5();
//        return baseUrl;
//    }
}
