// 符号数量统计与熵计算

package top.binweber.shannon;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class EntropyCalculate {
    private static int count;
    private static Map<Character,Integer> amountMap;
    private static Map<Character,Double> probabilityMap;

    public EntropyCalculate() {  // 初始化
        count = 0;  // 总符号数
        amountMap = new TreeMap<>();  // 各符号数量Map
        probabilityMap = new TreeMap<>();  // 各符号概率Map
    }

    public Map<Character,Integer> amountCal(String textStr) {  // 数量统计
        char[] chars = textStr.toCharArray();  // String转换为Char


        for(int i = 0; i < chars.length; i++) {  // 遍历，统计
            if(!(chars[i] >= 'a' && chars[i] <= 'z' || chars[i] >= 'A' && chars[i] <= 'Z'))  // 判断符号范围
                continue;

            count ++;  // 总数统计
            Integer amount = amountMap.get(chars[i]);  // 获取符号的当前数量
            if (amount != null)  // 当前数量不为null
                amount ++;
            else                //  当前数量为null
                amount = 1;
            amountMap.put(chars[i], amount);  // 存入Map中
        }

        return amountMap;
    }

    public Map<Character,Double> probabilityCal() {  // 概率计算
        double pro = 0;  // 概率

        for(Map.Entry<Character, Integer> entry : amountMap.entrySet()) {  // 遍历，统计
            pro = (double) entry.getValue() / count;  // 计算概率
            BigDecimal bg = new BigDecimal(pro);
            pro = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();  // 保留3位小数
            probabilityMap.put(entry.getKey(), pro);  // 存入Map中
        }

        return probabilityMap;
    }

    public double entropyCal() {  // 符号熵计算
        double entropy = 0;  // 符号熵

        for(Double  p : probabilityMap.values()) {  // 遍历
            entropy += -(p * Math.log(p) / Math.log(2));  // 符号熵计算
        }

        BigDecimal bg = new BigDecimal(entropy);
        entropy = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();  // 保留3位小数

        return entropy;
    }

    public double entropyCal(Map<Character,Double> probMap) {
        double entropy = 0;

        for(Double  p : probMap.values()) {
            entropy += -(p * Math.log(p) / Math.log(2));
        }

        BigDecimal bg = new BigDecimal(entropy);
        entropy = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();  // 保留3位小数

        return entropy;
    }
}