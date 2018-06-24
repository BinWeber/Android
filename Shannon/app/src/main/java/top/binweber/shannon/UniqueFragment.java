package top.binweber.shannon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UniqueFragment extends Fragment {

    private EditText uniqueText;
    private TextView resultText;
    private TextView suffixText;
    private Button clearButton;
    private Button judgeButton;
    private String inputText;

    private UniqueDecode uniqueDecode;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.unique_fragment, container, false);

        uniqueText = view.findViewById(R.id.unique_text);
        suffixText = view.findViewById(R.id.unique_suffix);
        resultText = view.findViewById(R.id.unique_result);
        clearButton = view.findViewById(R.id.unique_clear);
        judgeButton = view.findViewById(R.id.unique_judge);


        clearButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                uniqueText.getText().clear();
                suffixText.setText("");
                resultText.setText("");
                Toast.makeText(getActivity(), "已清空", Toast.LENGTH_SHORT).show();
            }
        });

        judgeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean judgeResult;
                ArrayList<String> codonList =  new ArrayList<>();
                ArrayList<String> suffixList = new ArrayList<>();

                inputText = uniqueText.getText().toString();
                String inputTexts = inputText;
                if(inputTexts.trim().isEmpty()){
                    Toast.makeText(getActivity(), "输入为空！", Toast.LENGTH_SHORT).show();
                }else {

                    String reg = "[^0-9\\s+]";
                    inputText = inputText.replaceAll(reg, "");

                    String[] codons = inputText.split("\\s+");
                    for (int i = 0; i < codons.length; i++) {
                        codonList.add(codons[i]);
                    }

                    uniqueDecode = new UniqueDecode(codonList);
                    judgeResult = uniqueDecode.compare();
                    suffixList = uniqueDecode.getSuffixList();

                    Log.d("md", "me" + listToString(suffixList));
                    suffixText.setText(listToString(suffixList));

                    if (judgeResult) {
                        resultText.setText(R.string.unique_true);
                    } else {
                        resultText.setText(R.string.unique_false);
                    }
                }
            }
        });

        return view;
    }

    public static String listToString(ArrayList<String> list){
        if(list==null){
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(String string :list) {
            if(first) {
                first=false;
            }else{
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }
}
