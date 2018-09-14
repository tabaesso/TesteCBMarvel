package br.com.testecbmarvel.testecbmarvel.presenter;

import br.com.testecbmarvel.testecbmarvel.model.Example;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Services {
//    @GET("forecast/next-days")
//    Call<PersonagemReturn> getDailyForecast(@Body LatLng latLng);
    @GET("characters?")
    Call<Example> lista(
            @Query("ts") long timestamp,
            @Query("apikey") String key,
            @Query("hash") String hashMd5);

    @GET("comics")
    Call<Example> getComics();


//    @GET("characters?ts={timestamp}&apiKey={key}&hash={hashMd5}")
//    void lista(
//            @Path("ts") long timestamp,
//            @Path("apikey") String key,
//            @Path("hash") String hashMd5,
//            Callback<Personagem> personagemCallback);



//    @GET("characters?ts={timestamp}&apiKey={key}&hash={hashMd5}")
//    Call<List<Personagem>> lista(@Query("timestamp") long timestamp, @Query("key") String key, @Query("hashMd5") String hashMd5);

//    Util util = new Util();
//    @GET("v1/public/characters?ts=" + util.timestamp() + "&apikey=" + Keys.PUBLIC_KEY + "&hash=" + util.md5())
//    Call<List<Personagem>> lista();

//    @POST("forecast/next-hours")
//    Call<HourlyForecastReturn> getHourlyForecast(@Body LatLng latLng);
//
//    @POST("current-condition/temperature")
//    Call<Temperature> getTemperature(@Body LatLng latLng);
//
//    @POST("current-condition/weather-condition")
//    Call<WeatherCondition> getWeatherCondition(@Body LatLng latLng);
}
