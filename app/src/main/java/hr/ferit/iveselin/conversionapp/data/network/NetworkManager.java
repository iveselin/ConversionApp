package hr.ferit.iveselin.conversionapp.data.network;

import java.util.List;

import hr.ferit.iveselin.conversionapp.data.ApiInterface;
import hr.ferit.iveselin.conversionapp.data.model.CurrencyRate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkManager implements ApiInterface {


    private static NetworkManager networkManager;

    private static ApiEndpoint apiEndpoint;

    private NetworkManager() {
        apiEndpoint = ApiFactory.getApiEndpoint();
    }

    public static ApiInterface getApiInstance() {
        if (networkManager == null) {
            networkManager = new NetworkManager();
        }

        return networkManager;
    }


    @Override
    public void getRates(final OnFinishedRatesListener listener) {

        apiEndpoint.getCurrencyRates().enqueue(new Callback<List<CurrencyRate>>() {

            @Override
            public void onResponse(Call<List<CurrencyRate>> call, Response<List<CurrencyRate>> response) {
                if (response.body() != null) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(new Throwable());
                }
            }

            @Override
            public void onFailure(Call<List<CurrencyRate>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
