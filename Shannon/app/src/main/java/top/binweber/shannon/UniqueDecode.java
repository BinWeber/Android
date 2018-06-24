/*
判断是否唯一可译
 */
package top.binweber.shannon;

import java.util.ArrayList;

public class UniqueDecode {

    private boolean result;  // 判断结果
    private ArrayList<String> codonList;  // 码字集
    private ArrayList<String> suffixList;  // 后缀集

    public UniqueDecode(ArrayList<String> codonList) {  // 初始化
        result = true;  // 默认唯一可译
        this.codonList = codonList;
        suffixList = new ArrayList<>();
    }

    public boolean compare() { // 比较判断
        String suffix = null;

        cp: for(int i = 0; i < codonList.size(); i++) {
            for(int j = i + 1; j < codonList.size(); j++) {
                String codon1 = codonList.get(i);  // 取出两个码字
                String codon2 = codonList.get(j);

                suffix = compareCodon(codon1,codon2);  // 获得尾随后缀

                if(!result) {  // 已判断出非唯一可译
                    break cp;  // 跳出循环
                }

                if(suffix != null && !suffixList.contains(suffix)) {  // 尾随后缀不为null，且尾随后缀集b不包含该后缀
                    suffixList.add(suffix);  // 收集获得的尾随后缀
                }
            }
        }

        compareList(codonList, suffixList, suffix);  // 比较码字集与尾随后缀集

        return result;
    }

    public ArrayList<String> getSuffixList() {
        return suffixList;
    }

    private String compareCodon(String codon1, String codon2) {  // 比较两个码字
        String suffix = null;  // 尾随后缀初始化为null

        if(codon1.equals(codon2)) {  //  如果有两个码字相同，奇异码
            result = false;   // 非唯一可议
        }

        if(result) {
            if(codon1.startsWith(codon2))  // 如果码字1以码字2作为开头
                suffix = codon1.substring(codon2.length(), codon1.length());  // 从码字2中取出尾随后缀

            if(codon2.startsWith(codon1))  // 如果码字2以码字1作为开头
                suffix = codon2.substring(codon1.length(), codon2.length());  // 从码字1中取出尾随后缀
        }

        return suffix;  // 返回尾随后缀
    }

    private void compareList(ArrayList<String> a, ArrayList<String> b, String suffix) {  // 比较码字集和尾随后缀集
        boolean flag = false;  // 继续递归判断标志

        String codon1,codon2;

        cp: for(int i = 0; i < a.size(); i++) {  // 循环
            for(int j = 0; j < b.size(); j++) {
                codon1 = a.get(i);  // 从a中取出一个码字1
                codon2 = b.get(j);  // 从b中取出一个尾随后缀
                suffix = compareCodon(codon1,codon2);  // 比较码字1、2，获得尾随后缀

                if(!result) {  // 已经判断出是非唯一可译
                    break cp;  // 跳出循环
                }

                if(suffix != null && !b.contains(suffix)) {  // 尾随后缀不为null，且尾随后缀集b中不包含该后缀
                    b.add(suffix);  // 尾随后缀加入b中
                    flag = true;  // 需要继续判断
                    break cp;  // 跳出循环
                }
                // 当前尾随后缀为null或得到的是重复的尾随后缀则继续循环
            }
        }

        if(flag) {
            compareList(a, b, suffix);  // 继续递归进行判断
        }

    }
}
