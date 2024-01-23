package com.example.petsns;

import com.example.petsns.ui.setting.TagViewModel;

import com.google.firebase.Timestamp;
import java.util.List;

public class TestPost {
    TagViewModel viewModel;
    private List<Boolean> tagMom;
    private List<Boolean> tagBir;
    private List<Boolean> tagRip;
    private List<Boolean> tagBis;
    private List<Boolean> tagAqua;
    private List<Boolean> tagIns;
    private String id;
    private String sentence;
    private String imageUrl;
    private String icon;
    private Timestamp timestamp;
    private String documentId;

    private int likeCount;

    private String[] nameMommalians={"ネコ","イヌ","ウサギ","ハリネズミ","ハムスター","カワウソ","チンチラ",
            "フェレット","モモンガ","キツネ","リス"};
    private String[] nameReptiles={"ヘビ","トカゲ","カメ","ワニ","ヤモリ","カメレオン"};
    private String[] nameInsect={"カブトムシ","クワガタ","カマキリ","セミ","クモ","バッタ"};
    private String[] nameBisexual={"カエル","イモリ"};
    private String[] nameBird={"オウム","インコ","スズメ","カナリア","フクロウ","ブンチョウ","ニワトリ","アヒル"};
    private String[] nameAquatic={"ザリガニ","メガカ","キンギョ","クラゲ"};

    public TestPost() {
        // 必要に応じて初期化の処理を追加
    }

    public Timestamp gettimestamp(){return timestamp;}

    public void setDocumentId(String documentId){
        this.documentId=documentId;
    }
    public String getDocumentId(){return documentId;}

    // ゲッター・セッターなど必要な処理を実装
    public void setId(String id){
        this.id=id;
    }
    public String getid(){
        return id;
    }

    public void setSentence(String sentence){
        this.sentence=sentence;
    }
    public String getSentence(){
        return sentence;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl=imageUrl;
    }
    public String getImageUrl(){ return imageUrl;}

    public void setIcon(String icon){this.icon=icon;}
    public String getIcon(){ return icon;}

    public void setTimestamp(Timestamp timestamp){
        this.timestamp=timestamp;
    }
    public Timestamp timestamp(){ return  timestamp;}


    public void setLikeCount(int likeCount){
        this.likeCount=likeCount;
    }
    public int getLikeCount(){return likeCount;}
    public void setTagMom(List<Boolean> tagMom) {
        this.tagMom = tagMom;
    }
    public void setTagBir(List<Boolean> tagBir) {
        this.tagBir=tagBir;
    }
    public void setTagRip(List<Boolean> tagRip){
        this.tagRip=tagRip;
    }
    public void setTagBis(List<Boolean> tagBis){
        this.tagBis=tagBis;
    }
    public void setTagAqua(List<Boolean> tagAqua){
        this.tagAqua=tagAqua;
    }
    public void setTagIns(List<Boolean> tagIns){
        this.tagIns=tagIns;
    }


    public String  tagConversion(){
            String tagName="#";
            if(tagMom!=null){
                for(int i=0;i<tagMom.size();i++){
                    if(tagMom.get(i)){
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
                    if(tagBir.get(i)){
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
                    if(tagRip.get(i)){
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
                    if(tagBis.get(i)){
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
                    if(tagAqua.get(i)){
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
                    if(tagIns.get(i)){
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
