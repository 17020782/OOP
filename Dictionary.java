package sample;

import java.util.ArrayList;
import java.util.List;

/*
class Dictionary để tạo 1 danh sách Word
*/

public class Dictionary {
    List<Word> _word = new ArrayList<Word>(); // list word
    public void sort() {
        Word temp1 = new Word();
        Word temp2 = new Word();
        System.out.println(_word.size());
        for (int i = 0; i < _word.size() - 1 ; i++)
            for (int j = i + 1; j < _word.size(); j++)
                if ( _word.get(i).getWord().compareTo(_word.get(j).getWord()) > 0) {

                    temp1.setWord_target(_word.get(i).getWord());
                    temp1.setWord_explain(_word.get(i).getExplain());

                    temp2.setWord_target(_word.get(j).getWord());
                    temp2.setWord_explain(_word.get(j).getExplain());

                    _word.set(i, temp2);
                    _word.set(j, temp1);
                }
    }
}

