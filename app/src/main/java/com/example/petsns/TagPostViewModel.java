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
    private boolean[] boolLkeRip={false,false,false,false,false,false};
    private String[] nameReptiles={"ヘビ","トカゲ","カメ","ワニ","ヤモリ","カメレオン"};

    private boolean[] boolLkeIns={false,false,false,false,false,false};
    private String[] nameInsect={"カブトムシ","クワガタ","カマキリ","セミ","クモ","バッタ"};

    private boolean[] boolLkeBis={false,false};
    private String[] nameBisexual={"カエル","イモリ"};

    private boolean[] boolLkeBir={false,false,false,false,false,false,false,false};
    private String[] nameBird={"オウム","インコ","スズメ","カナリア","フクロウ","ブンチョウ","ニワトリ","アヒル"};

    private boolean[] boolLkeAqua={false,false,false,false};
    private String[] nameAquatic={"ザリガニ","メガカ","キンギョ","クラゲ"};

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


    public  void setArrayLikeBir(boolean boolPrt,boolean boolPrk,boolean boolSp,
                                 boolean boolCn,boolean boolOw,boolean boolBc,boolean boolCh,
                                 boolean boolDc){
        boolLkeBir[0]=boolPrt;
        boolLkeBir[1]=boolPrk;
        boolLkeBir[2]=boolSp;
        boolLkeBir[3]=boolCn;
        boolLkeBir[4]=boolOw;
        boolLkeBir[5]=boolBc;
        boolLkeBir[6]=boolCh;
        boolLkeBir[7]=boolDc;
    }


    public String getLikeBir(){
        String chkMommalians="";
        for(int i=0;i<8;i++){
            if(boolLkeBir[i]){
                if(chkMommalians.equals("")){
                    chkMommalians=nameBird[i];
                }else {
                    chkMommalians+=(" "+nameBird[i]);
                }


            }
        }
        return chkMommalians;
    }

    public List<Boolean> getArraylikeBir(){
        List<Boolean> booleanList = IntStream.range(0, boolLkeBir.length)
                .mapToObj(i -> boolLkeBir[i])
                .collect(Collectors.toList());
        return  booleanList;
    }


    public  void setArrayLikeRip(boolean boolSn,boolean boolLz,boolean boolTt,
                                 boolean boolAl,boolean boolGc,boolean boolCm){
        boolLkeRip[0]=boolSn;
        boolLkeRip[1]=boolLz;
        boolLkeRip[2]=boolTt;
        boolLkeRip[3]=boolAl;
        boolLkeRip[4]=boolGc;
        boolLkeRip[5]=boolCm;
    }


    public String getLikeRip(){
        String chkMommalians="";
        for(int i=0;i<6;i++){
            if(boolLkeRip[i]){
                if(chkMommalians.equals("")){
                    chkMommalians=nameReptiles[i];
                }else {
                    chkMommalians+=(" "+nameReptiles[i]);
                }


            }
        }
        return chkMommalians;
    }

    public List<Boolean> getArraylikeRip(){
        List<Boolean> booleanList = IntStream.range(0, boolLkeRip.length)
                .mapToObj(i -> boolLkeRip[i])
                .collect(Collectors.toList());
        return  booleanList;
    }

    public  void setArrayLikeBis(boolean boolFr,boolean boolNw){
        boolLkeBis[0]=boolFr;
        boolLkeBis[1]=boolNw;
    }


    public String getLikeBis(){
        String chkMommalians="";
        for(int i=0;i<2;i++){
            if(boolLkeBis[i]){
                if(chkMommalians.equals("")){
                    chkMommalians=nameBisexual[i];
                }else {
                    chkMommalians+=(" "+nameBisexual[i]);
                }


            }
        }
        return chkMommalians;
    }

    public List<Boolean> getArraylikeBis(){
        List<Boolean> booleanList = IntStream.range(0, boolLkeBis.length)
                .mapToObj(i -> boolLkeBis[i])
                .collect(Collectors.toList());
        return  booleanList;
    }

    public  void setArrayLikeAqua(boolean boolCr,boolean boolMd,boolean boolGf,
                                 boolean boolJr){
        boolLkeAqua[0]=boolCr;
        boolLkeAqua[1]=boolMd;
        boolLkeAqua[2]=boolGf;
        boolLkeAqua[3]=boolJr;
    }


    public String getLikeAqua(){
        String chkMommalians="";
        for(int i=0;i<4;i++){
            if(boolLkeAqua[i]){
                if(chkMommalians.equals("")){
                    chkMommalians=nameAquatic[i];
                }else {
                    chkMommalians+=(" "+nameAquatic[i]);
                }


            }
        }
        return chkMommalians;
    }

    public List<Boolean> getArraylikeAqua(){
        List<Boolean> booleanList = IntStream.range(0, boolLkeAqua.length)
                .mapToObj(i -> boolLkeAqua[i])
                .collect(Collectors.toList());
        return  booleanList;
    }

    public  void setArrayLikeIns(boolean boolBt,boolean boolSt,boolean boolMt,
                                 boolean boolCc,boolean boolSp,boolean boolLc){
        boolLkeIns[0]=boolBt;
        boolLkeIns[1]=boolSt;
        boolLkeIns[2]=boolMt;
        boolLkeIns[3]=boolCc;
        boolLkeIns[4]=boolSp;
        boolLkeIns[5]=boolLc;
    }


    public String getLikeIns(){
        String chkMommalians="";
        for(int i=0;i<6;i++){
            if(boolLkeIns[i]){
                if(chkMommalians.equals("")){
                    chkMommalians=nameInsect[i];
                }else {
                    chkMommalians+=(" "+nameInsect[i]);
                }


            }
        }
        return chkMommalians;
    }

    public List<Boolean> getArraylikeIns(){
        List<Boolean> booleanList = IntStream.range(0, boolLkeIns.length)
                .mapToObj(i -> boolLkeIns[i])
                .collect(Collectors.toList());
        return  booleanList;
    }


    public String getTagNameAll(){

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

        for(int i=0;i<8;i++){
            if(boolLkeBir[i]){
                if(chkMommalians.equals("")){
                    chkMommalians=nameBird[i];
                }else {
                    chkMommalians+=(" "+nameBird[i]);
                }
            }
        }

        for(int i=0;i<6;i++){
            if(boolLkeRip[i]){
                if(chkMommalians.equals("")){
                    chkMommalians=nameReptiles[i];
                }else {
                    chkMommalians+=(" "+nameReptiles[i]);
                }
            }
        }

        for(int i=0;i<2;i++){
            if(boolLkeBis[i]){
                if(chkMommalians.equals("")){
                    chkMommalians=nameBisexual[i];
                }else {
                    chkMommalians+=(" "+nameBisexual[i]);
                }
            }
        }

        for(int i=0;i<4;i++){
            if(boolLkeAqua[i]){
                if(chkMommalians.equals("")){
                    chkMommalians=nameAquatic[i];
                }else {
                    chkMommalians+=(" "+nameAquatic[i]);
                }
            }
        }

        for(int i=0;i<6;i++){
            if(boolLkeIns[i]){
                if(chkMommalians.equals("")){
                    chkMommalians=nameInsect[i];
                }else {
                    chkMommalians+=(" "+nameInsect[i]);
                }
            }
        }
        return chkMommalians;
    }


    public void tagCancel(){
        boolLkeMom[0]=false;
        boolLkeMom[1]=false;
        boolLkeMom[2]=false;
        boolLkeMom[3]=false;
        boolLkeMom[4]=false;
        boolLkeMom[5]=false;
        boolLkeMom[6]=false;
        boolLkeMom[7]=false;
        boolLkeMom[8]=false;
        boolLkeMom[9]=false;
        boolLkeMom[10]=false;

        boolLkeBir[0]=false;
        boolLkeBir[1]=false;
        boolLkeBir[2]=false;
        boolLkeBir[3]=false;
        boolLkeBir[4]=false;
        boolLkeBir[5]=false;
        boolLkeBir[6]=false;
        boolLkeBir[7]=false;

        boolLkeRip[0]=false;
        boolLkeRip[1]=false;
        boolLkeRip[2]=false;
        boolLkeRip[3]=false;
        boolLkeRip[4]=false;
        boolLkeRip[5]=false;

        boolLkeBis[0]=false;
        boolLkeBis[1]=false;

        boolLkeAqua[0]=false;
        boolLkeAqua[1]=false;
        boolLkeAqua[2]=false;
        boolLkeAqua[3]=false;

        boolLkeIns[0]=false;
        boolLkeIns[1]=false;
        boolLkeIns[2]=false;
        boolLkeIns[3]=false;
        boolLkeIns[4]=false;
        boolLkeIns[5]=false;
    }

}