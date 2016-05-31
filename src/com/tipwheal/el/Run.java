package com.tipwheal.el;

import java.io.*;

/**
 * Created by Ariana on 2016/5/31.
 */
public class Run {
    static double OcpEnemy ;
    static double OcpBlank ;
    static double AtkEnemy ;
    static double AvdEnemy ;
    static double Hide ;
    static double OutHome ;
    static double MemAvd;
    static double Dgr;
    static double AtkNext;

    public static void main(String[]args) throws IOException {
        StringBuilder sb=new StringBuilder();
        StringBuilder sb2;
        String information;
        String str,str2;
        String[]scores;
        String A,B;
        String[] d,g;
        File f=new File("result.txt");
        int scoreA,scoreB,got;
       for(OcpEnemy=0;OcpEnemy<4;OcpEnemy+=0.4){
           for(OcpBlank=0;OcpBlank<2;OcpBlank+=0.2){
               for(AtkEnemy=0;AtkEnemy<20;AtkEnemy+=2){
                   for(Hide=0;Hide<2;Hide+=0.2) {
                       for(OutHome=0;OutHome<1;OutHome+=0.1){
                           for (MemAvd=0;MemAvd<12;MemAvd+=1.2){
                               for(Dgr=-1;Dgr<1;Dgr+=0.1){
                                   for(AtkNext=0;AtkNext<12;AtkNext+=1.2){
                                       Process process = Runtime.getRuntime().exec("./testmach ");
                                       try {
                                           BufferedReader    bre = new BufferedReader(new FileReader("log.txt"));//此时获取到的bre就是整个文件的缓存流
                                           while (( str = bre.readLine())!= null) // 判断最后一行不存在，为空结束循环
                                           {
                                               sb.append(str);
                                           }
                                           information="OcpEnemy is "+OcpEnemy+"/ "+" OcpBlank is "+OcpBlank+" /"+" AtkEnemy is "+AtkEnemy+"/ "+
                                                   " Hide is "+Hide+"/ "+" OutHome is "+OutHome+"/ "+" MemAvd is "+MemAvd+" "+" Dgr is "+Dgr +
                                                   " "+" AtkNext is "+AtkNext+"/ ";
                                           str2=sb.toString();
                                           scores=str2.split("#");
                                           A=scores[scores.length-2];
                                           d=A.split(" ");
                                           B=scores[scores.length-1];
                                           g=B.split(" ");
                                           scoreA=Integer.parseInt(d[2]);
                                           scoreB=Integer.parseInt(g[2]);
                                           got=scoreB-scoreA;
                                           sb2 = new StringBuilder(information);
                                           sb2.append(A);
                                           sb2.append(B);

                                           sb2.append(" the difference in value is ").append(got);
                                           sb2.append("\n");
                                           information=sb2.toString();

                                           FileWriter fw = new FileWriter(f, true);
                                           fw.write(information);
                                           fw.flush();
                                           fw.close();
                                           bre.close();
                                       } catch (IOException e) {
                                           e.printStackTrace();
                                       }
                                   }
                               }
                           }
                       }
                   }
               }
           }
       }
        //Process process = Runtime.getRuntime().exec("./testmach ");
        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

    }

}
