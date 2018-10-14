package sample;

/*
class DictionaryCommandline gồm
    - 1 đối tượng dic_mana thuộc class DictionaryManagement
    - 1 hàm showAllWords() để in ra các từ có trong danh sách
    - 1 hàm dictionaryBasic() để:{
            + gọi lại hàm insertFromCommandline() trong class DictionaryManagement
            + gọi lại hàm showAllWords()
            }
    - 1 hàm dictionaryAdvanced để:{
            + gọi lại hàm insertFromFile() trong class DictionaryManagement
            + gọi lại hàm showAllWords()
            + dictionaryLookup() trong class DictionaryManagement
*/

public class DictionaryCommandline {

    public DictionaryManagement dic_mana = new DictionaryManagement();

    public DictionaryCommandline() {

    }

    /*
    Ham in ra dictionary
     */

    public void showAllWords(){
        System.out.println("STT \t |English \t |Vietnamese\n");

        for(int i=0; i < dic_mana._dictionary._word.size(); i++){
            System.out.println(i + 1 + " \t |" + dic_mana._dictionary._word.get(i).getWord() + " \t\t |" + dic_mana._dictionary._word.get(i).getExplain() + "\n");
        }
    }
    /*
    ham goi toi insert va show word
     */
    public void dictionaryBasic(){
        dic_mana.insertFromFile();
        showAllWords();
    }
    public void dictionaryAdvanced(){
        dic_mana.insertFromFile();
        showAllWords();
        dic_mana.dictionaryLookup();
        //showAllWords();

    }


    public void delWord(){
        dic_mana.deleteWord();
    }
    public void add(){
        dic_mana.addWord();
    }
    public void edit(){
        dic_mana.editWord();
    }
}
