package com.tipwheal.el;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

       for(OcpEnemy=0;OcpEnemy<4;OcpEnemy+=0.4){
           for(OcpBlank=0;OcpBlank<2;OcpBlank+=0.2){
               for(AtkEnemy=0;AtkEnemy<20;AtkEnemy+=2){
                   for(Hide=0;Hide<2;Hide+=0.2) {
                       for(OutHome=0;OutHome<1;OutHome+=0.1){
                           for (MemAvd=0;MemAvd<12;MemAvd+=1.2){
                               for(Dgr=-1;Dgr<1;Dgr+=0.1){
                                   for(AtkNext=0;AtkNext<12;AtkNext+=1.2){
                                       Process process = Runtime.getRuntime().exec("./testmach ");
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
