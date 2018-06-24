package top.binweber.shannon;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;


import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.style.FontStyle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ResultActivity extends AppCompatActivity {

    private String inputData;
    private int inputType, encodeType;

    private List<ResultInfo> resultList;
    private Map<Character,Integer> amountMap;
    private Map<Character,Double> probabilityMap;
    private double entropy;
    private double aCLength;
    private double ratio;
    private List<Map.Entry<Character, Double>> probArrayList;
    private Map<Character,Integer> nLengthMap;
    private Map<Character, String> codonMap;

    private EntropyCalculate entropyCalculate;
    private ShannonEncode shannonEncode;
    private FanoEncode fanoEncode;
    private HuffmanEncode huffmanEncode;

    private SmartTable resultTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_table);
        setTitle(R.string.result);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        resultTable = (SmartTable) findViewById(R.id.result_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputType = getIntent().getIntExtra("inputType", EntropyFragment.None);
        encodeType = getIntent().getIntExtra("encodeType", EntropyFragment.None);
        inputData = getIntent().getStringExtra("inputData");

        amountMap = new TreeMap<>();
        probabilityMap = new TreeMap<>();
        entropy = 0.0;
        resultList = new ArrayList<>();

        entropyCalculate = new EntropyCalculate();

        resultSet();
    }

    private void resultSet() {
        if(inputType == EntropyFragment.CheckBox_1) {

            amountMap = entropyCalculate.amountCal(inputData);
            probabilityMap = entropyCalculate.probabilityCal();
            entropy = entropyCalculate.entropyCal();

            if(encodeType == EntropyFragment.Spinner_1) {
                shannonEncode = new ShannonEncode(probabilityMap);
                probArrayList = shannonEncode.getProbArrayList();
                codonMap = shannonEncode.toShannon();
                nLengthMap = shannonEncode.getCLengthMap();
                aCLength = shannonEncode.getACLength();
                ratio = entropy / aCLength;

            } else if(encodeType == EntropyFragment.Spinner_2) {
                fanoEncode = new FanoEncode(probabilityMap);
                probArrayList = fanoEncode.getProbArrayList();
                codonMap = fanoEncode.toFano();
                nLengthMap = fanoEncode.getCLengthMap();
                aCLength = fanoEncode.getACLength();
                ratio = entropy / aCLength;

            } else if(encodeType == EntropyFragment.Spinner_3) {
                huffmanEncode = new HuffmanEncode(probabilityMap);
                probArrayList = huffmanEncode.getProbArrayList();
                codonMap = huffmanEncode.toHuffman();
                nLengthMap = huffmanEncode.getCLengthMap();
                aCLength = huffmanEncode.getACLength();
                ratio = entropy / aCLength;
            }

            BigDecimal bg = new BigDecimal(ratio);
            ratio = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();

            for (Map.Entry<Character, Double> entry : probArrayList) {
                resultList.add(new ResultInfo(entry.getKey().toString(), amountMap.get(entry.getKey()), probabilityMap.get(entry.getKey()), nLengthMap.get(entry.getKey()), codonMap.get(entry.getKey())));
            }
            resultList.add(new ResultInfo("信源熵", entropy));
            resultList.add(new ResultInfo("平均码长", aCLength));
            resultList.add(new ResultInfo("编码效率", ratio));

            resultTable.setData(resultList);
            resultTable.getConfig().setTableTitleStyle(new FontStyle(30, Color.BLACK));
            resultTable.getConfig().setColumnTitleStyle(new FontStyle(25, Color.BLACK));
            resultTable.getConfig().setContentStyle(new FontStyle(25, Color.RED));
        } else if(inputType == EntropyFragment.CheckBox_2) {
            String[] probs = inputData.split("\\s+");
            for(int i = 0; i < probs.length; i++) {
                probabilityMap.put((char)('a' + i), Double.parseDouble(probs[i]));
            }
            entropy = entropyCalculate.entropyCal(probabilityMap);

            if(encodeType == EntropyFragment.Spinner_1) {
                shannonEncode = new ShannonEncode(probabilityMap);
                probArrayList = shannonEncode.getProbArrayList();
                codonMap = shannonEncode.toShannon();
                nLengthMap = shannonEncode.getCLengthMap();
                aCLength = shannonEncode.getACLength();
                ratio = entropy / aCLength;

            } else if(encodeType == EntropyFragment.Spinner_2) {
                fanoEncode = new FanoEncode(probabilityMap);
                probArrayList = fanoEncode.getProbArrayList();
                codonMap = fanoEncode.toFano();
                nLengthMap = fanoEncode.getCLengthMap();
                aCLength = fanoEncode.getACLength();
                ratio = entropy / aCLength;

            } else if(encodeType == EntropyFragment.Spinner_3) {
                huffmanEncode = new HuffmanEncode(probabilityMap);
                probArrayList = huffmanEncode.getProbArrayList();
                codonMap = huffmanEncode.toHuffman();
                nLengthMap = huffmanEncode.getCLengthMap();
                aCLength = huffmanEncode.getACLength();
                ratio = entropy / aCLength;
            }

            BigDecimal bg = new BigDecimal(ratio);
            ratio = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();

            for (Map.Entry<Character, Double> entry : probArrayList) {
                resultList.add(new ResultInfo(entry.getKey().toString(), probabilityMap.get(entry.getKey()), nLengthMap.get(entry.getKey()), codonMap.get(entry.getKey())));
            }
            resultList.add(new ResultInfo("信源熵", entropy));
            resultList.add(new ResultInfo("平均码长", aCLength));
            resultList.add(new ResultInfo("编码效率", ratio));

            resultTable.setZoom(true);
            resultTable.setData(resultList);
            resultTable.getConfig().setTableTitleStyle(new FontStyle(30, Color.BLACK));
            resultTable.getConfig().setColumnTitleStyle(new FontStyle(25, Color.BLACK));
            resultTable.getConfig().setContentStyle(new FontStyle(25, Color.RED));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
