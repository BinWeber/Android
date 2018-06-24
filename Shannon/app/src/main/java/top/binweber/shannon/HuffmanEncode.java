/*
哈夫曼编码
 */

package top.binweber.shannon;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

class TreeNode {  // 树节点类

    public double prob;  // 结点概率值
    public String codeword = "";  // 码字
    public TreeNode left;  // 左子节点
    public TreeNode right;  // 右子节点

    public TreeNode(double data){
        this.prob = data;
    }
}

public class HuffmanEncode {
    private static Map<Character, Integer> cLengthMap;  // 码长Map
    private static Map<Character, String> codonMap;  // 码字Map
    private static List<Map.Entry<Character, Double>> probArrayList;  // 概率List
    private static double aCLength;  // 平均码长
    private static List<String> codonList;  // 码字List


    public HuffmanEncode(Map<Character, Double> probMap) {  // 初始化
        aCLength = 0.0;
        cLengthMap = new TreeMap<>();
        codonMap = new TreeMap<>();
        codonList = new ArrayList<>();

        probArrayList = new ArrayList<>(probMap.entrySet());  // 将概率Map按概率值递减排列放到概率List
        Collections.sort(probArrayList, new Comparator<Map.Entry<Character, Double>>() {

            public int compare(Map.Entry<Character, Double> o1, Map.Entry<Character, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
    }

    public Map<Character, String> toHuffman() {  // 进行编码
        List<TreeNode> list = new ArrayList<>();  // 用于存放节点的List

        int i = 0;
        for(Map.Entry<Character,Double> entry:probArrayList) {
            double prob = entry.getValue();  // 取出概率值
            TreeNode root = new TreeNode(prob);  // 创建新节点
            list.add(i, root); // 将新节点加入list
            i ++;
        }

        createTree(list);  // 由list中的节点创建哈夫曼树
        TreeNode root = (TreeNode)list.get(0);  // 得到根节点
        list.clear();  // 清空list
        printTree(root);  // 获得编码结果

        int j = 0;
        for(Map.Entry<Character,Double> entry:probArrayList) {
            codonMap.put(entry.getKey(), codonList.get(j));
            cLengthMap.put(entry.getKey(), codonList.get(j).length());
            aCLength += entry.getValue() * codonList.get(j).length();
            j ++;
        }

        return codonMap;
    }

    private void createTree(List<TreeNode> list){  // 创建一棵哈夫曼树
        double prob1 = (list.get(list.size() - 1)).prob;  // 取出倒数第一个节点的概率
        double prob2 = (list.get(list.size() - 2)).prob;  // 取出倒数第二个节点的概率
        double prob_sum = prob1 + prob2;  // 两个最小的概率值相加

        TreeNode root = new TreeNode(prob_sum);  // 用相加的概率创建一个新节点

        root.left = (list.get(list.size() - 1));  // 将倒数第一个作为左子节点
        root.right = (list.get(list.size() - 2));  // 将倒数第二个作为右子节点
        list.remove(list.size() - 1);  // 已经list中加上树上的节点删除
        list.remove(list.size() - 1);

        sortList(list, root);  // 将新的节点加到list里，并排序

        if(list.size() > 1){
            createTree(list);  // 只要list里面还有节点，就递归创建树
        }
    }

    private void sortList(List<TreeNode> list,TreeNode root){  // 将某个节点插入list，排序
        if(list.size() == 0){
            list.add(root);  // list为空时直接插入
        }else{
            int i;
            for(i = 0; i < list.size(); i++){
                if(list.get(i).prob <= root.prob){  // 循环比较大小
                    list.add(i, root);  // 插入
                    break;
                }
            }

            if(i == list.size()) {  // 到最后，直接插入
                list.add(i, root);
            }
        }
    }

    private void printTree(TreeNode root){  // 使用广度优先查找算法，层次遍历哈夫曼树各节点

        Queue<TreeNode> queue = new ArrayDeque<>();  // 节点队列

        queue.offer(root);  // 将根节点入队
        while(!queue.isEmpty()) {  // 队列非空，不断执行

            root = queue.poll();  // 将一个节点出队

            if (root.left != null) {  // 该节点的左子节点非空
                root.left.codeword = root.codeword + "0";  // 左子节点码字加0
                queue.offer((root.left));  // 左子节点入队
            }
            if (root.right != null) {  // 该节点的右子节点非空
                root.right.codeword = root.codeword + "1";  // 右子节点码字加1
                queue.offer((root.right));  // 右子节点入队
            }

            if (root.left == null && root.right == null) {  // 节点为叶子结点
                codonList.add(root.codeword);  // 将节点的码字存储起来
            }
        }

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


