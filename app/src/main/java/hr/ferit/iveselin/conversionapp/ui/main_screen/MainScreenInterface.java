package hr.ferit.iveselin.conversionapp.ui.main_screen;

import java.util.List;
import java.util.Set;

public interface MainScreenInterface {

    interface View {

        void showErrorMessage();

        void showWrongInputError();

        void showConversionResult(String conversionResult);

        void updateSelections(Set<String> selections);

        void setConvertToToDefault();

        void setConvertFromToDefault();

        void clearResult();
    }


    interface Presenter {

        void setView(View view);

        void viewReady();

        void convertFromChanged(String selectedCurrency);

        void convertToChanged(String selectedCurrency);

        void convertPressed(String value);

    }
}
