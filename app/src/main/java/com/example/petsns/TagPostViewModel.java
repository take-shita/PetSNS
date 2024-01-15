package com.example.petsns;

import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TagPostViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private boolean[] boolLkeMom={false,false,false,false,false,false,false,false,false,false,false};
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
    
    public List<Boolean> getArraylikeMom(){
        List<Boolean> booleanList = IntStream.range(0, boolLkeMom.length)
                .mapToObj(i -> boolLkeMom[i])
                .collect(Collectors.toList());
        return  booleanList;
    }
}