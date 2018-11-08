package hr.ferit.iveselin.conversionapp.data.model;

import com.google.gson.annotations.SerializedName;

public class CurrencyRate {

    @SerializedName("currency_code")
    private String currencyCode;
    @SerializedName("unit_value")
    private int unitValue;
    @SerializedName("buying_rate")
    private double buyingRate;
    @SerializedName("median_rate")
    private double medianRate;
    @SerializedName("selling_rate")
    private double sellingRate;


    public String getCurrencyCode() {
        return currencyCode;
    }

    public int getUnitValue() {
        return unitValue;
    }

    public double getBuyingRate() {
        return buyingRate;
    }

    public double getMedianRate() {
        return medianRate;
    }

    public double getSellingRate() {
        return sellingRate;
    }
}
