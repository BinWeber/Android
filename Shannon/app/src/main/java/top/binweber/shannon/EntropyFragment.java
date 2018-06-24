package top.binweber.shannon;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Map;

import javax.xml.transform.Result;

public class EntropyFragment extends Fragment {

    public static final int None = 0;
    public static final int CheckBox_1 = 1;
    public static final int CheckBox_2 = 2;
    public static final int Spinner_1 = 3;
    public static final int Spinner_2 = 4;
    public static final int Spinner_3 = 5;

    private String spinnerArray[];
    private String inputText;
    private static Map<Character,Integer> amountMap;
    private static Map<Character,Double> probabilityMap;

    private EntropyCalculate entropyCalculate;
    private CheckBox checkBox_1, checkBox_2;
    private EditText editText;
    private Spinner spinner;
    private Button clearButton, encodeButton;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.entropy_fragment, container, false);

        entropyCalculate = new EntropyCalculate();
        checkBox_1 = view.findViewById(R.id.type_checkbox_1);
        checkBox_2 = view.findViewById(R.id.type_checkbox_2);
        editText = view.findViewById(R.id.entropy_text);
        spinner = view.findViewById(R.id.encode_spinner);
        clearButton = view.findViewById(R.id.clear_text);
        encodeButton = view.findViewById(R.id.ensure_encode);

        spinnerArray = getResources().getStringArray(R.array.encode_array);

        checkBoxInit();

        encodeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                inputText = editText.getText().toString();
                String inputTexts = inputText;

                if(inputTexts.trim().isEmpty()){
                    Toast.makeText(getActivity(), "输入为空！", Toast.LENGTH_SHORT).show();
                }else if(!checkBox_1.isChecked() && !checkBox_2.isChecked()) {
                    Toast.makeText(getActivity(), "选择输入类型！", Toast.LENGTH_SHORT).show();
                }else {
                    if (checkBox_1.isChecked()) {
                        String reg = "[^a-zA-Z]";
                        inputText = inputText.replaceAll(reg, "");
                    }

                    if (checkBox_2.isChecked()) {
                        String reg = "[^0-9.\\s+]";
                        inputText = inputText.replaceAll(reg, "");
                    }

                    Intent intent = new Intent(getActivity(), ResultActivity.class);
                    intent.putExtra("inputType", checkBoxState());
                    intent.putExtra("encodeType", spinnerState());
                    intent.putExtra("inputData", inputText);
                    startActivity(intent);
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                editText.getText().clear();
                Toast.makeText(getActivity(), "已清空", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void checkBoxInit() {

        checkBox_1.setChecked(false);
        checkBox_2.setChecked(false);

        checkBox_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    editText.setHint(R.string.tip_1);
                    checkBox_2.setChecked(false);
                }

            }
        });

        checkBox_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    editText.setHint(R.string.tip_2);
                    checkBox_1.setChecked(false);
                }
            }
        });
    }

    private int checkBoxState() {
        if(checkBox_1.isChecked()) {
            return CheckBox_1;
        } else if(checkBox_2.isChecked()) {
            return CheckBox_2;
        } else
            return None;
    }

    private int spinnerState() {
        if (spinner.getSelectedItem().toString().equals(spinnerArray[0])) {
            return Spinner_1;
        } else if (spinner.getSelectedItem().toString().equals(spinnerArray[1])) {
            return Spinner_2;
        } else if (spinner.getSelectedItem().toString().equals(spinnerArray[2])) {
            return Spinner_3;
        } else
            return None;
    }


}
