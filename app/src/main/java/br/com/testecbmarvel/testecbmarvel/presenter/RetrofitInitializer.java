package br.com.testecbmarvel.testecbmarvel.presenter;

import java.util.concurrent.TimeUnit;

import br.com.testecbmarvel.testecbmarvel.extras.Keys;
import br.com.testecbmarvel.testecbmarvel.extras.Util;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInitializer {

//     PRESENTER: É o mediador entre a View e o Model. Atualiza a view e sincroniza com o Model.

    public static Retrofit getGsonListCharacters() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(1000, TimeUnit.SECONDS)
                .connectTimeout(1000, TimeUnit.SECONDS)
                .build();


        return new Retrofit
                .Builder()
                .baseUrl("http://gateway.marvel.com/v1/public/")
//                .baseUrl("https://gateway.marvel.com:443/v1/public/")
//                .baseUrl("http://gateway.marvel.com/v1/public/characters?ts=" + util.timestamp() + "&apikey=" + Keys.PUBLIC_KEY + "&hash=" + util.md5())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static Retrofit getGsonComics(String id) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(1000, TimeUnit.SECONDS)
                .connectTimeout(1000, TimeUnit.SECONDS)
                .build();


        return new Retrofit
                .Builder()
                .baseUrl("http://gateway.marvel.com/v1/public/characters/"+ id +"/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

//    public static Retrofit getGsonCharacter() {
//        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .readTimeout(1000, TimeUnit.SECONDS)
//                .connectTimeout(1000, TimeUnit.SECONDS)
//                .build();
//
//
//        return new Retrofit
//                .Builder()
//                .baseUrl("http://gateway.marvel.com/v1/public/characters/")
////                .baseUrl("http://gateway.marvel.com/v1/public/characters?ts=" + util.timestamp() + "&apikey=" + Keys.PUBLIC_KEY + "&hash=" + util.md5())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//    }

}
