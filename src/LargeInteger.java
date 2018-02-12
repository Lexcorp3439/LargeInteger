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
        if (numSize > othSize) result = New.end(min, max, numbTrans, result);
        if (numSize < othSize) result = New.end(min, max, otherTrans, result);

        return new LargeInteger(New.retResult(result, max));
    }

//    public String subtraction(String otherNum){   //вычитание
//        int[] otherTrans = New.transform(otherNum);               //какой-то алгоритм
//
//    }
//    public String multiplier(String otherNum){    //умножение
//        int[] otherTrans = New.transform(otherNum);                //какой-то алгоритм
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

    static ArrayList<Integer> end(int min, int max, ArrayList<Integer> list, ArrayList<Integer> res){   // добавление оставшихся элементов
        int div;
        int mod;
        for (int i = min + 1; i < max; i++){
            div = (list.get(min) + res.get(min))/10;
            mod = (list.get(min) + res.get(min))%10;
            res.set(i, mod);
            res.set(i + 1, div);
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


/////////// на случай неожиданной надобности ///////////////
//    private ArrayList addSize(int[] old, int prev, int last){      // случаи, когда Превышается размер результирующего массива
//        int[] newRes = new int[old.length + 1];
//        for (int i = 0; i < old.length - 1; i++){
//            newRes[i] = old[i];
//        }
//        newRes[old.length - 1] = prev;
//        newRes[old.length] = last;
//        return newRes;
//    }
//
//        if (max == min && last > 0) result = addSize(result, prev, last);
//        else result[min - 1] = prev;
//        if (max == min + 1 && last > 0){
//            result[min - 1] = prev;
//            result[min] = last;
//            if (numSize > othSize) {
//                int nextPrew = (last + numbTrans.get(max - 1))%10;
//                int nextLast = (last + numbTrans.get(max - 1))/10;
//                if (last + numbTrans.get(max - 1) > 10)  result = addSize(result, nextPrew, nextLast);
//            }
//            else {
//                int nextPrew = (last + otherTrans.get(max - 1))%10;
//                int nextLast = (last + otherTrans.get(max - 1))/10;
//                if (last + otherTrans.get(max - 1) > 10)  result = addSize(result, nextPrew, nextLast);
//            }
//
//        }