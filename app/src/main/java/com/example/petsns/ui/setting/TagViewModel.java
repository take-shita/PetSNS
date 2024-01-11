package com.example.petsns.ui.setting;

import androidx.lifecycle.ViewModel;

public class TagViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private boolean boolCt;
    private boolean boolDg;
    private boolean boolRb;
    private boolean boolHg;
    private boolean boolHm;
    private boolean boolOt;
    private boolean boolCh;
    private boolean boolFl;
    private boolean boolMg;
    private boolean boolFx;
    private boolean boolSq;
//    private boolean[] boolMommalians=new boolean[11];

    public void setMammlian(boolean boolMg,boolean boolFx,boolean boolDg,
                            boolean boolCt,boolean boolRb,boolean boolOt,boolean boolCh,
                            boolean boolFl,boolean boolHg,boolean boolHm,boolean boolSq){
        this.boolMg=boolMg;
        this.boolFx=boolFx;
        this.boolDg=boolDg;
        this.boolCt=boolCt;
        this.boolRb=boolRb;
        this.boolOt=boolOt;
        this.boolCh=boolCh;
        this.boolFl=boolFl;
        this.boolHg=boolHg;
        this.boolHm=boolHm;
        this.boolSq=boolSq;

    }

    public void getMommalian(){

    }


}