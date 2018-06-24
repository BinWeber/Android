package top.binweber.shannon;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;


@SmartTable(name = "统计/编码结果表")
public class ResultInfo {

    @SmartColumn(id = 0, name = "信源符号")
    private String notation;
    @SmartColumn(id = 1, name = "出现次数")
    private int count;
    @SmartColumn(id = 2, name = "符号概率")
    private double probability;
    @SmartColumn(id = 3, name = "码字长度")
    private int nLength;
    @SmartColumn(id = 4, name = "码字")
    private String codon;


    public ResultInfo(String notation, int count, double probability, int nLength, String codon) {
        this.notation = notation;
        this.count = count;
        this.probability = probability;
        this.nLength = nLength;
        this.codon = codon;
    }
    public ResultInfo(String notation, double probability, int nLength, String codon) {
        this.notation = notation;
        this.count = count;
        this.probability = probability;
        this.nLength = nLength;
        this.codon = codon;
    }
    public ResultInfo(String notation, double probability) {
        this.notation = notation;
        this.probability = probability;
    }
}
