package sample;

public class Word {
   //    thanh phan word

    private String word_target,
            word_explain;

    //ham constructor
    public Word(){}
    public Word(String word, String explain){
        this.word_target = word;
        this.word_explain = explain;
    }
    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }

    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }


       //ham lay tu word
    public String getWord(){
        return this.word_target;
    }

        //ham lay nghia tu
    public String getExplain(){
        return this.word_explain;
    }
}
