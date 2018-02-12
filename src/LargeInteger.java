import java.util.ArrayList;
import java.util.Objects;

public class LargeInteger {
    private String number;
    private ArrayList<Integer> numbTrans = new ArrayList<>();
    private int numSize = numbTrans.size();

    public LargeInteger(String num){
        this.number = num;
    }

    public String gerNumber(){
        return number;
    }

    /////////   БЛОК СРАВНЕНИЕ   ///////////////////////////
    public boolean equals(String otherNum){
        ArrayList<Integer> otherTrans = New.transform(otherNum);
        int othSize = otherTrans.size();

        if (numSize != othSize) return false;
        else for (int i = 0; i < numSize; i++)
            if (!Objects.equals(numbTrans.get(i), otherTrans.get(i))) return false;
        return true;
    }
    public boolean bigger(String otherNum){
        ArrayList<Integer> otherTrans = New.transform(otherNum);
        int othSize = otherTrans.size();

        if (numSize == othSize)
            for (int i = numSize; i > 0; i--) {
                if (numbTrans.get(i) > otherTrans.get(i)) return true;
                if (numbTrans.get(i) < otherTrans.get(i)) return false;
            }
        return numSize > othSize;
    }

    /////////   БЛОК СЛОЖЕНИЕ\ВЫЧИТАНИЕ\УМНОЖЕНИЕ   /////////////////
    public LargeInteger addition(String otherNum){
        ArrayList<Integer> otherTrans = New.transform(otherNum);
        int othSize = otherTrans.size();
        int min = Math.min(numSize, othSize);
        int max = Math.max(numSize, othSize);
        ArrayList<Integer> result = new ArrayList<>(max + 1);

        for (int i = 0; i < min; i++){
            result.set(i, (numbTrans.get(i) + otherTrans.get(i)) % 10);
            result.set(i + 1, (numbTrans.get(i) + otherTrans.get(i)) / 10);
        }
        if (numSize > othSize) result = New.complete(min, max, numbTrans, result, '+');
        if (numSize < othSize) result = New.complete(min, max, otherTrans, result, '+');
        return new LargeInteger(New.retResult(result, max));
    }

    public LargeInteger subtraction(String otherNum){       //определить наибольшое число и вычитать из него
        ArrayList<Integer> otherTrans = New.transform(otherNum);
        int othSize = otherTrans.size();
        int min = Math.min(numSize, othSize);
        int max = Math.max(numSize, othSize);
        ArrayList<Integer> result = new ArrayList<>(max);
        ArrayList<Integer> minList;
        ArrayList<Integer> maxList;

        if (bigger(otherNum)) {
            maxList = numbTrans;
            minList = otherTrans;
        }
        else {
            minList = numbTrans;
            maxList = otherTrans;
            char sign = '-';
        }
        for (int i = 0; i < min; i++){
            int difference = maxList.get(i) - minList.get(i);
            if (difference < 0) {
                result.set(i, (maxList.get(i) - minList.get(i) + 10));
                maxList.set(i + 1, minList.get(i + 1) - 1);
            }
            else result.set(i, numbTrans.get(i) - otherTrans.get(i));
        }
        if (max > min) result = New.complete(min, max, maxList, result, '-');
        return new LargeInteger(New.retResult(result, max));

    }
//    public String multiplier(String otherNum){    //умножение
//        ArrayList<Integer> otherTrans = New.transform(otherNum);
//        int othSize = otherTrans.size();
//
//    }

    ////////   БЛОК ДЕЛЕНИЕ\ОСТАТОК   ////////////////////////////
//    public String division(String otherNum){      //деление
//        String newNum = " dsfdsf";                //какой-то алгоритм
//        return newNum;
//    }
//    public String residue(String otherNum){       //остаток
//        String newNum = " dsfdsf";                //какой-то алгоритм
//        return newNum;
//    }


}

class New{
    static ArrayList<Integer> transform(String number){
        char[] str = new StringBuffer(number).reverse().toString().toCharArray();
        ArrayList<Integer> trans = new ArrayList<>();

        for (int i = 0; i < str.length; i++){
            trans.set(i, str[i] - '0');
        }
        return trans;
    }

    static ArrayList<Integer> complete(int min, int max, ArrayList<Integer> list, ArrayList<Integer> res, char oper){   // добавление оставшихся элементов
        switch (oper){
            case '+':{
                int div;
                int mod;

                for (int i = min; i < max; i++){
                    div = (list.get(min) + res.get(min))/10;
                    mod = (list.get(min) + res.get(min))%10;
                    res.set(i, mod);
                    res.set(i + 1, div);
                }
            }
            case '-':{
                for (int i = min; i < max; i++){
                    res.set(i, list.get(i));
                }
            }
        }
        return res;
    }

    static String retResult(ArrayList<Integer> res, int max){
        String str;
        if (res.get(max - 1) == 0) str = new StringBuffer(res.subList(0, max - 2).toString()).reverse().toString();
        else str = new StringBuffer(res.toString()).reverse().toString();
        return str;
    }
}