package com.destiny.opqbot.destinybot.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Ping {
    /**
     * 取两个文本之间的文本值
     *
     * @param text
     * @param left
     * @param right
     * @return
     */
    public static String getSubString(String text, String left, String right) {
        String result = "";
        int zLen;
        if (left == null || left.isEmpty()) {
            zLen = 0;
        } else {
            zLen = text.indexOf(left);
            if (zLen > -1) {
                zLen += left.length();
            } else {
                zLen = 0;
            }
        }
        int yLen = text.indexOf(right, zLen);
        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }

    public static String ping(String ip){
        String osName = System.getProperty("os.name");//获取操作系统类型
        String pingCommand = "";
        if(osName.toLowerCase().contains("linux")){
            pingCommand = "ping -c 3 -i 0 "+ip;
        }else{
            pingCommand = "ping "+ip+" -n 3 -w 1000";
        }

        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(pingCommand);
            br = new BufferedReader(new InputStreamReader(p.getInputStream(),"UTF-8"));
            String line = null;
            StringBuilder sb=new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "999+";
    }



    public static int average(Integer[] array) {
        int temp = 0;
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum=sum+array[i];
        }
        temp=sum/array.length;
        return temp;
    }

}
