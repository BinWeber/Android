/*
费诺编码
 */
package top.binweber.shannon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FanoEncode {
    private static Map<Character,Integer> cLengthMap;  // 码长Map
    private static Map<Character, String> codonMap;  // 码字Map
    private static List<Map.Entry<Character, Double>> probArrayList;  // 概率List
    private static double aCLength;  // 平均码长

    public FanoEncode(Map<Character,Double> probMap) {  // 初始化
        aCLength = 0.0;
        cLengthMap = new TreeMap<>();
        codonMap = new TreeMap<>();

        probArrayList = new ArrayList<>(probMap.entrySet());  // 将概率Map按概率值递减排列放到概率List
        Collections.sort(probArrayList,new Comparator<Map.Entry<Character,Double>>() {

            public int compare(Map.Entry<Character,Double> o1, Map.Entry<Character,Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

    }

    public Map<Character, String> toFano() {
        double[] pros = new double[probArrayList.size()];  // 概率值数组

        int i = 0;
        for(Map.Entry<Character,Double> entry:probArrayList) {
            pros[i] = entry.getValue();  // 从概率List中取出概率值放入数组
            i++;
        }

        String[] codon = getGroup(pros, 0, pros.length - 1);  // 调用方法，得到编码

        int j = 0;
        for(Map.Entry<Character,Double> entry:probArrayList) {
            codonMap.put(entry.getKey(), codon[j]);  // 将各码字放进codonMap
            cLengthMap.put(entry.getKey(), codon[j].length());  // 将各长放进cLengthMap
            aCLength += pros[j] * codon[j].length();  // 计算平均码长
            j++;
        }

        return codonMap;
    }

    private String[] getGroup(double[] pros, int i, int j) {  // 输入概率值数组，得到其中索引为i到j编码结果
        int middle = 0;  // 分组点索引
        String[] codens = new String[pros.length];  // 存储编码结果

        for (int k = 0; k < pros.length; k++) {
            codens[k] = "";  // 编码结果初始化
        }

        if(i < j) {  // 索引i小于索引
            double sum = 2; // 用以比较的中间量（初始值随便取一个大于1的即可）
            for(int k = i; k <= j; k++) {  // 循环找到中位值的索引
                if(Math.abs(sumGroup(pros,i,k) - sumGroup(pros, k+1, j)) < sum) { // 如过两部分累加和之差小于中间量
                    sum = Math.abs(sumGroup(pros, i, k) - sumGroup(pros, k+1, j));  // 将两部分累加和之差作为中间量
                    middle = k;  // 更新分组点的索引
                }
            }

            String[] codens_1 = getGroup(pros, i, middle);  // 递归获得前半部分编码
            String[] codens_2 = getGroup(pros, middle+1, j);  // 递归获得后半部分编码

            for(int k = i; k <= middle; k++) {  // 对前半部分编码
                codens[k] = "0" + codens_1[k];
            }

            for(int k = middle + 1; k <= j ; k++) {  // 对后半部分编码
                codens[k] = "1" + codens_2[k];
            }
        }

        return codens;
    }

    private double sumGroup(double[] pros,int i, int j) {  // 累加概率数组索引i到j的值
        double sum = 0.0;

        for(int k = i; k <= j; k++) {
            sum += pros[k];
        }

        return sum;
    }

    public Map<Character, Integer> getCLengthMap() {
        return cLengthMap;
    }

    public List<Map.Entry<Character, Double>> getProbArrayList() {
        return  probArrayList;
    }

    public double getACLength() {
        BigDecimal bg = new BigDecimal(aCLength);
        aCLength = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        return aCLength;
    }

}
