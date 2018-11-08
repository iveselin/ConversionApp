package hr.ferit.iveselin.conversionapp.data;

import java.util.List;

import hr.ferit.iveselin.conversionapp.data.model.CurrencyRate;

public interface ApiInterface {


    void getRates(OnFinishedRatesListener listener);

    interface OnFinishedRatesListener {

        void onSuccess(List<CurrencyRate> rates);

        void onFailure(Throwable t);
    }

}
