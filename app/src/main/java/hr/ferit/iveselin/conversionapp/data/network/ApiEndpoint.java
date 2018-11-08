package hr.ferit.iveselin.conversionapp.data.network;

import java.util.List;

import hr.ferit.iveselin.conversionapp.data.model.CurrencyRate;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndpoint {

    @GET("daily")
    Call<List<CurrencyRate>> getCurrencyRates();

}
