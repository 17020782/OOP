
package sample;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class DictionaryManagement {

    public Dictionary _dictionary = new Dictionary();
    /*
    ham them t vao list dictionary
     */

    public void insertFromCommandline() {
        Scanner scan = new Scanner(System.in);
        System.out.println("so luong tu: ");    // nhap so luong tu de chay vong for
        int n;
        n = scan.nextInt();
        scan.nextLine();// để bỏ qua lệnh Enter sau khi nhập n
        /*
        chay for tu 1-n de nhap Word vao list
         */
        for (int i = 0; i < n; i++) {
            System.out.println("English: ");
            String word = scan.nextLine();
            System.out.println("Vietnamese: ");
            String explain = scan.nextLine();
            Word temp = new Word(word, explain);
            _dictionary._word.add(temp);
        }

    }

    /*
    ham them tu bang file;
     */
    public void insertFromFile() {

        File file_input = new File("dictionaries.txt");  // khai bao file


        try {                                    // tu search cai khoi lenh try catch nhe;
            Scanner scan = new Scanner(file_input);

            scan.nextLine();
            while (scan.hasNext()) {                    // trong khi con dong de doc
                String str;
                str = scan.nextLine();                      // nhap tu file vao string
                String[] word = str.split("\t");
                /*
                phuong thuc split dung de tach mot chuoi String ngan cach nhau boi dau tab "\t"
                o trong file tren 1 dong no co 1 dau tab nen no se tach thanh 2 cai string va tu
                dong luu vao trong mang [] word, word gio co 2 phan tu gom word[0] = english
                va word[1] = vietnamese
                 */
                Word temp = new Word(word[0], word[1]);
                _dictionary._word.add(temp);
            }
        } catch (Exception e) {
            System.out.println(e);
        }


        class sortWithEngWord implements Comparator<Word> {
            public int compare(Word w1, Word w2) {
                return w1.getWord().compareTo(w2.getWord());
            }
        }

        Collections.sort(_dictionary._word, new sortWithEngWord());

    }


    public void dictionaryLookup() {
        /*
        thuat toan tim kiem noi suy Interpolation Search;
         */
        System.out.println("english word: ");
        String english;

        Scanner scan = new Scanner(System.in);
        english = scan.nextLine();

        /*
            sap xep arrayList
            Java Comparator interface
            java Comparator interface được sử dụng để chỉ ra thứ tự của các đối tượng
         */
        int mid, low = 0, high = _dictionary._word.size() - 1;

        while (english.compareTo(_dictionary._word.get(low).getWord()) >= 0 && english.compareTo(_dictionary._word.get(high).getWord()) <= 0)

           /*dieu kien vong while:
            trong khi khoang tim kiem khong rong va chu cai dau cua tu english lon hon chu cai dau cua
            tu dau khoang dang tim && nho hon chu cai dau cua tu cuoi khoang dang tim
             */
        {

            int a = (int) english.charAt(0),
                    b = (int) _dictionary._word.get(low).getWord().charAt(0),
                    c = (int) _dictionary._word.get(high).getWord().charAt(0),
                    d = (int) _dictionary._word.get(low).getWord().charAt(0);
            mid = low + (a - b) * (high - low) / (c - d);


            if (low == high) System.out.println("word not found! ");


            else if (english.compareTo(_dictionary._word.get(mid).getWord()) > 0) low = mid + 1;


            else if (english.compareTo(_dictionary._word.get(mid).getWord()) < 0) high = mid - 1;
            else {
                System.out.println("|" + english + "\t" + "|" + _dictionary._word.get(mid).getExplain());
                break;
            }


        }

    }

    public void deleteWord() {
        System.out.println("nhập vào từ cần xóa");
        Scanner scan = new Scanner(System.in);
        String word = scan.nextLine();
        for (int i = 0; i < _dictionary._word.size(); i++) {
            if (word.equals(_dictionary._word.get(i).getWord())) {
                _dictionary._word.remove(i);
            }
        }
    }

    public void addWord() {
        Scanner scan = new Scanner(System.in);
        System.out.println("nhập vào từ cần thêm");
        System.out.println("English: ");
        String word = scan.nextLine();
        System.out.println("Vietnamese: ");
        String explain = scan.nextLine();
        Word temp = new Word(word, explain);
        _dictionary._word.add(temp);

    }

    public void editWord() {
        Scanner scan = new Scanner(System.in);
        System.out.println("nhập vào từ bạn muốn sửa: ");
        String word = scan.nextLine();
        for (int i = 0; i < _dictionary._word.size(); i++) {
            if (word.equals(_dictionary._word.get(i).getWord())) {
                _dictionary._word.remove(i);
            }
        }
        System.out.println("Sửa từ đó thành:");
        System.out.println("English: ");
        String word1 = scan.nextLine();
        System.out.println("Vietnamese: ");
        String explain = scan.nextLine();
        Word temp = new Word(word1, explain);
        _dictionary._word.add(temp);
    }


    public void dictionarySearcher() {
        System.out.print("Nhập từ cần tìm : ");
        Scanner scan = new Scanner(System.in);
        String word = scan.nextLine();
        for (int i = 0; i < _dictionary._word.size(); i++)
            if (word.equals(_dictionary._word.get(i).getWord())) {
                System.out.println(_dictionary._word.get(i).getWord() + "\t  |   " + _dictionary._word.get(i).getExplain());
            }

    }

    public void  dictionaryExportToFile(){
        try{
            FileWriter wri = new FileWriter("fileout.txt",true); // true de chong ghi de len du lieu truoc
            for(int i=0; i<_dictionary._word.size(); i++) {
                wri.write(_dictionary._word.get(i).getWord() +"\t" + _dictionary._word.get(i).getExplain() + "\r\n");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

