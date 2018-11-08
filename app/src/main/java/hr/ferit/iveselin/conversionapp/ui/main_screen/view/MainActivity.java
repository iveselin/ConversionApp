package hr.ferit.iveselin.conversionapp.ui.main_screen.view;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import hr.ferit.iveselin.conversionapp.R;
import hr.ferit.iveselin.conversionapp.data.network.NetworkManager;
import hr.ferit.iveselin.conversionapp.ui.main_screen.MainScreenInterface;
import hr.ferit.iveselin.conversionapp.ui.main_screen.presentation.MainScreenPresenter;

public class MainActivity extends AppCompatActivity implements MainScreenInterface.View {

    private static final String TAG = "MainActivity";

    @BindView(R.id.convert_from_selection)
    Spinner convertFromSelection;
    @BindView(R.id.convert_to_selection)
    Spinner convertToSelection;
    @BindView(R.id.convert_value)
    EditText convertValue;
    @BindView(R.id.convert_result)
    TextView convertResult;

    private MainScreenInterface.Presenter presenter;


    private ArrayAdapter<String> selectionsAdapter;

    private boolean itemListenerEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        presenter = new MainScreenPresenter(NetworkManager.getApiInstance());
        presenter.setView(this);

        setUi();
    }

    private void setUi() {

        presenter.viewReady();
    }


    @Override
    public void showErrorMessage() {
        Toast.makeText(getApplicationContext(), "Oooops, something went wrong...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showWrongInputError() {
        convertValue.setError(getString(R.string.wrong_input_error));
    }

    @Override
    public void showConversionResult(String conversionResult) {
        convertResult.setText(conversionResult);
    }

    @Override
    public void updateSelections(Set<String> selections) {
        Log.d(TAG, "updateSelections: updating selections");

        selectionsAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        selectionsAdapter.addAll(selections);

        convertFromSelection.setAdapter(selectionsAdapter);
        convertToSelection.setAdapter(selectionsAdapter);
    }

    @Override
    public void setConvertToToDefault() {
        itemListenerEnabled = false;
        convertToSelection.setSelection(0);

    }

    @Override
    public void setConvertFromToDefault() {
        itemListenerEnabled = false;
        convertFromSelection.setSelection(0);
    }

    @Override
    public void clearResult() {
        convertResult.setText("");
    }

    @Override
    public void showNoDataErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.no_data_dialog_title);
        builder.setMessage(R.string.no_data_dialog_message);

        builder.setNegativeButton(R.string.no_data_dialog_cancel_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                presenter.dismissClicked();
            }
        });

        builder.setPositiveButton(R.string.no_data_dialog_retry_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.retryClicked();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void exitApp() {
        finish();
    }


    @OnItemSelected(R.id.convert_from_selection)
    void onConvertFromSelected(int position) {
        if (itemListenerEnabled) {
            presenter.convertFromChanged(convertFromSelection.getItemAtPosition(position).toString());
        } else {
            itemListenerEnabled = true;
        }
    }

    @OnItemSelected(R.id.convert_to_selection)
    void onConvertToSelected(int position) {
        if (itemListenerEnabled) {
            presenter.convertToChanged(convertToSelection.getItemAtPosition(position).toString());
        } else {
            itemListenerEnabled = true;
        }
    }

    @OnClick(R.id.convert_submit)
    void onConvertClicked() {
        presenter.convertPressed(convertValue.getText().toString());
    }
}
