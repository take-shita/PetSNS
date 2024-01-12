package com.example.petsns.ui.setting;

import androidx.lifecycle.ViewModel;

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
    private boolean[] boolMommalians=new boolean[11];
    private String[] nameMommalians={"ネコ","イヌ","ウサギ","ハリネズミ","ハムスター","カワウソ","チンチラ",
                                    "フェレット","モモンガ","キツネ","リス"};
    public  void setArrayMommalian(boolean boolCt,boolean boolDg,boolean boolRb,
                                   boolean boolHg,boolean boolHm,boolean boolOt,boolean boolCh,
                                   boolean boolFl,boolean boolMg,boolean boolFx,boolean boolSq){
        boolMommalians[0]=boolCt;
        boolMommalians[1]=boolDg;
        boolMommalians[2]=boolRb;
        boolMommalians[3]=boolHg;
        boolMommalians[4]=boolHm;
        boolMommalians[5]=boolOt;
        boolMommalians[6]=boolCh;
        boolMommalians[7]=boolFl;
        boolMommalians[8]=boolMg;
        boolMommalians[9]=boolFx;
        boolMommalians[10]=boolSq;
    }

    public String getMommalian(){
        String chkMommalians="";
        for(int i=0;i<11;i++){
            if(boolMommalians[i]){
                if(chkMommalians.equals("")){
                    chkMommalians=nameMommalians[i];
                }else {
                    chkMommalians+=(" "+nameMommalians[i]);
                }


            }
        }
        return chkMommalians;
    }


}