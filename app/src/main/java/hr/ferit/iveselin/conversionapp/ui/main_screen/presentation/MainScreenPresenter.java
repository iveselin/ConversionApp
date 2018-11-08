package hr.ferit.iveselin.conversionapp.ui.main_screen.presentation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.ferit.iveselin.conversionapp.data.ApiInterface;
import hr.ferit.iveselin.conversionapp.data.model.CurrencyRate;
import hr.ferit.iveselin.conversionapp.ui.main_screen.MainScreenInterface;

public class MainScreenPresenter implements MainScreenInterface.Presenter, ApiInterface.OnFinishedRatesListener {

    private static final String TAG = "MainScreenPresenter";
    private static final String DEFAULT_CURRENCY = "HRK";

    private MainScreenInterface.View view;

    private ApiInterface apiInterface;

    private List<CurrencyRate> rates = new ArrayList<>();
    private Set<String> currencies = new HashSet<>();
    private String convertFrom = "";
    private String convertTo = "";

    public MainScreenPresenter(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    @Override
    public void setView(MainScreenInterface.View view) {
        this.view = view;
    }

    @Override
    public void viewReady() {
        apiInterface.getRates(this);
    }

    @Override
    public void convertFromChanged(String selectedCurrency) {
        convertFrom = selectedCurrency;
        view.clearResult();

        if (!convertTo.equals(DEFAULT_CURRENCY)) {
            convertTo = DEFAULT_CURRENCY;
            view.setConvertToToDefault();
        }
    }

    @Override
    public void convertToChanged(String selectedCurrency) {
        convertTo = selectedCurrency;
        view.clearResult();

        if (!convertFrom.equals(DEFAULT_CURRENCY)) {
            convertFrom = DEFAULT_CURRENCY;
            view.setConvertFromToDefault();
        }
    }

    @Override
    public void convertPressed(String inputValue) {

        if (inputValue == null || inputValue.isEmpty()) {
            view.showWrongInputError();
        } else {
            double value = Double.parseDouble(inputValue);
            calculateResult(value);
        }
    }

    private void calculateResult(double value) {

        double result = 0;

        if (convertFrom.equals(convertTo)) {
            result = value;

        } else if (convertTo.equals(DEFAULT_CURRENCY)) {
            for (CurrencyRate rate : rates) {
                if (rate.getCurrencyCode().equals(convertFrom)) {
                    result = (value * rate.getBuyingRate()) / rate.getUnitValue();
                    break;
                }
            }
        } else if (convertFrom.equals(DEFAULT_CURRENCY)) {
            for (CurrencyRate rate : rates) {
                if (rate.getCurrencyCode().equals(convertTo)) {
                    result = (value / rate.getSellingRate()) * rate.getUnitValue();
                    break;
                }
            }
        }

        if (result != 0) {
            view.showConversionResult(String.format("%.4f", result));
        } else {
            view.showErrorMessage();
        }
    }


    @Override
    public void onSuccess(List<CurrencyRate> currencyRates) {
        this.rates = currencyRates;

        for (CurrencyRate rate : rates) {
            currencies.add(rate.getCurrencyCode());
        }
        currencies.add(DEFAULT_CURRENCY);
        view.updateSelections(currencies);
    }

    @Override
    public void onFailure(Throwable t) {
        view.showErrorMessage();
    }
}
