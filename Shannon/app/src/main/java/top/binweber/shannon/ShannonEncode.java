/*
香农编码
 */
package top.binweber.shannon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ShannonEncode {
    private static Map<Character,Integer> cLengthMap;  // 码长Map
    private static Map<Character, String> codonMap;  // 码字Map
    private static List<Map.Entry<Character, Double>> probArrayList; //概率值List
    private static double probs;  // 累加概率
    private static double aCLength; // 平均码长


    public ShannonEncode(Map<Character,Double> probMap) {  // 初始化
        probs = 0.0;
        aCLength = 0.0;
        cLengthMap = new TreeMap<>();
        codonMap = new TreeMap<>();

        probArrayList = new ArrayList<>(probMap.entrySet());  // 按概率从大到小排序
        Collections.sort(probArrayList,new Comparator<Map.Entry<Character,Double>>() {

            public int compare(Map.Entry<Character,Double> o1, Map.Entry<Character,Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
    }

    public Map<Character, String> toShannon() {  // 实现编码
        double prob = 0;
        int cLength = 0;

        for(Map.Entry<Character,Double> entry:probArrayList) { // 由概率Map循环
            prob = entry.getValue(); // 取出一个概率值
            cLength = (int) Math.ceil(Math.log(1/prob)/Math.log((double)2));  // 计算码长，Math.ceil()-向上取整
            cLengthMap.put(entry.getKey(), cLength);  // 将算得的码长打包到Map
            codonMap.put(entry.getKey(), colonCal(probs, cLength));  // 调用下面写好的colonCal方法，算得码字，打包到Map
            aCLength += prob * cLength;  // 计算平均码长
            probs += prob;  // 概率值累加
        }

        return codonMap;
    }

    private String colonCal(double probs, int nLength) {  // 将累加概率转换为二进制（码字）
        String coden = "";

        for(int i = 0; i < nLength; i++) {
            probs *= 2;  // 累加概率乘以2
            if(probs >= 1) {
                probs -= 1;
                coden += 1; // 大于1则取1
            } else {
                coden += 0; //小于1则取0
            }
        }

        return coden;
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