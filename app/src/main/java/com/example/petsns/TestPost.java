package com.example.petsns;

import com.example.petsns.ui.setting.TagViewModel;

import java.sql.Timestamp;

public class TestPost {
    TagViewModel viewModel;

    private String id;
    private String sentence;
    private String imageUrl;
    private String icon;
    private Timestamp timestamp;
    private boolean[] tagMom;
    private boolean[] tagBir;
    private boolean[] tagRip;
    private boolean[] tagBis;
    private boolean[] tagIns;
    private boolean[] tagAqua;

    private String[] nameMommalians={"ネコ","イヌ","ウサギ","ハリネズミ","ハムスター","カワウソ","チンチラ",
            "フェレット","モモンガ","キツネ","リス"};
    private String[] nameReptiles={"ヘビ","トカゲ","カメ","ワニ","ヤモリ","カメレオン"};
    private String[] nameInsect={"カブトムシ","クワガタ","カマキリ","セミ","クモ","バッタ"};
    private String[] nameBisexual={"カエル","イモリ"};
    private String[] nameBird={"オウム","インコ","スズメ","カナリア","フクロウ","ブンチョウ","ニワトリ","アヒル"};
    private String[] nameAquatic={"ザリガニ","メガカ","キンギョ","クラゲ"};

    // ゲッター・セッターなど必要な処理を実装
    public String getid(){
        return id;
    }
    public String getSentence(){
        return sentence;
    }
    public String getImageUrl(){ return imageUrl;}
    public String getIcon(){ return icon;}
    public void setIcon(String icon){this.icon=icon;}
    public Timestamp timestamp(){ return  timestamp;}
    public void setTagMom(boolean[] tagMom){
        this.tagMom=tagMom;
    }

    public String  tagConversion(){
            String tagName="";
            if(tagMom!=null){
                for(int i=0;i<11;i++){
                    if(tagMom[i]){
                        if(tagName.equals("")){
                            tagName=nameMommalians[i];
                        }else {
                            tagName+=(" "+nameMommalians[i]);
                        }
                    }
                }
            }else{
                tagName="aaa";
            }

            if(tagBir!=null){
                for(int i=0;i<8;i++){
                    if(tagBir[i]){
                        if(tagName.equals("")){
                            tagName=nameBird[i];
                        }else {
                            tagName+=(" "+nameBird[i]);
                        }
                    }
                }
            }

            if(tagRip!=null){
                for(int i=0;i<6;i++){
                    if(tagRip[i]){
                        if(tagName.equals("")){
                            tagName=nameReptiles[i];
                        }else {
                            tagName+=(" "+nameReptiles[i]);
                        }
                    }
                }
            }

            if(tagBis!=null){
                for(int i=0;i<2;i++){
                    if(tagBis[i]){
                        if(tagName.equals("")){
                            tagName=nameBisexual[i];
                        }else {
                            tagName+=(" "+nameBisexual[i]);
                        }
                    }
                }
            }

            if(tagAqua!=null){
                for(int i=0;i<4;i++){
                    if(tagAqua[i]){
                        if(tagName.equals("")){
                            tagName=nameAquatic[i];
                        }else {
                            tagName+=(" "+nameAquatic[i]);
                        }
                    }
                }
            }

            if(tagIns!=null){
                for(int i=0;i<6;i++){
                    if(tagIns[i]){
                        if(tagName.equals("")){
                            tagName=nameInsect[i];
                        }else {
                            tagName+=(" "+nameInsect[i]);
                        }
                    }
                }
            }
        return tagName;
    }


}
