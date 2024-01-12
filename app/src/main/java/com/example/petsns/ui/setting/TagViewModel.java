package com.example.petsns.ui.setting;

import androidx.lifecycle.ViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.IntStream;

public class TagViewModel extends ViewModel {
    // TODO: Implement the ViewModel
//    private boolean boolCt;
//    private boolean boolDg;
//    private boolean boolRb;
//    private boolean boolHg;
//    private boolean boolHm;
//    private boolean boolOt;
//    private boolean boolCh;
//    private boolean boolFl;
//    private boolean boolMg;
//    private boolean boolFx;
//    private boolean boolSq;
    private boolean[] boolLkeMom=new boolean[11];
    private boolean[] boolDisLkeMom=new boolean[11];
    private String[] nameMommalians={"ネコ","イヌ","ウサギ","ハリネズミ","ハムスター","カワウソ","チンチラ",
                                    "フェレット","モモンガ","キツネ","リス"};
    public  void setArrayLikeMom(boolean boolCt,boolean boolDg,boolean boolRb,
                                   boolean boolHg,boolean boolHm,boolean boolOt,boolean boolCh,
                                   boolean boolFl,boolean boolMg,boolean boolFx,boolean boolSq){
        boolLkeMom[0]=boolCt;
        boolLkeMom[1]=boolDg;
        boolLkeMom[2]=boolRb;
        boolLkeMom[3]=boolHg;
        boolLkeMom[4]=boolHm;
        boolLkeMom[5]=boolOt;
        boolLkeMom[6]=boolCh;
        boolLkeMom[7]=boolFl;
        boolLkeMom[8]=boolMg;
        boolLkeMom[9]=boolFx;
        boolLkeMom[10]=boolSq;
    }
    public  void setArrayDisLikeMom(boolean boolCt,boolean boolDg,boolean boolRb,
                                   boolean boolHg,boolean boolHm,boolean boolOt,boolean boolCh,
                                   boolean boolFl,boolean boolMg,boolean boolFx,boolean boolSq){
        boolDisLkeMom[0]=boolCt;
        boolDisLkeMom[1]=boolDg;
        boolDisLkeMom[2]=boolRb;
        boolDisLkeMom[3]=boolHg;
        boolDisLkeMom[4]=boolHm;
        boolDisLkeMom[5]=boolOt;
        boolDisLkeMom[6]=boolCh;
        boolDisLkeMom[7]=boolFl;
        boolDisLkeMom[8]=boolMg;
        boolDisLkeMom[9]=boolFx;
        boolDisLkeMom[10]=boolSq;
    }

    public String getLikemom(){
        String chkMommalians="";
        for(int i=0;i<11;i++){
            if(boolLkeMom[i]){
                if(chkMommalians.equals("")){
                    chkMommalians=nameMommalians[i];
                }else {
                    chkMommalians+=(" "+nameMommalians[i]);
                }


            }
        }
        return chkMommalians;
    }
    public String getDisLikemom(){
        String chkMommalians="";
        for(int i=0;i<11;i++){
            if(boolLkeMom[i]){
                if(chkMommalians.equals("")){
                    chkMommalians=nameMommalians[i];
                }else {
                    chkMommalians+=(" "+nameMommalians[i]);
                }


            }
        }
        return chkMommalians;
    }

    public List<Boolean> getArraylikeMom(){
        List<Boolean> booleanList = IntStream.range(0, boolLkeMom.length)
                .mapToObj(i -> boolLkeMom[i])
                .collect(Collectors.toList());
        return  booleanList;
    }
    public List<Boolean> getArrayDislikeMom(){
        List<Boolean> booleanList = IntStream.range(0, boolDisLkeMom.length)
                .mapToObj(i -> boolDisLkeMom[i])
                .collect(Collectors.toList());
        return  booleanList;
    }


}