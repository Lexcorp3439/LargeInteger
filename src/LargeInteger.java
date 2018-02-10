import java.util.Arrays;

public class LargeInteger {
    private String number;
    private int[] numbTrans = New.transform(number);

    public String gerNumber(){
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    /////////   БЛОК СРАВНЕНИЕ   ///////////////////////////
    public boolean equals(String otherNum){
        int[] otherTrans = New.transform(otherNum);
        if (numbTrans.length != otherTrans.length) return false;
        else for (int i = 0; i < numbTrans.length; i++)
            if (numbTrans[i] != otherTrans[i]) return false;
        return true;
    }
    public boolean bigger(String otherNum){
        int[] otherTrans = New.transform(otherNum);
        if (numbTrans.length == otherTrans.length)
            for (int i = numbTrans.length; i > 0; i--) {
                if (numbTrans[i] > otherTrans[i]) return true;
                if (numbTrans[i] < otherTrans[i]) return false;
            }
        return numbTrans.length > otherTrans.length;
    }

    /////////   БЛОК СЛОЖЕНИЕ\ВЫЧИТАНИЕ\УМНОЖЕНИЕ   /////////////////
    public String addition(String otherNum){
        int[] otherTrans = New.transform(otherNum);
        int min = Math.min(numbTrans.length, otherTrans.length);
        int max = Math.max(numbTrans.length, otherTrans.length);
        int last = (numbTrans[min - 1] + otherTrans[min - 1])/10;
        int prev = (numbTrans[min - 1] + otherTrans[min - 1])%10;
        int[] result = new int[max];

        for (int i = 0; i < min - 1; i++){
            result[i] += (numbTrans[i] + otherTrans[i])%10;
            result[i+1] = (numbTrans[i] + otherTrans[i])/10;
        }
        if (max == min && last > 0) result = addSize(result, prev, last);
        else result[min - 1] = prev;
        if (max == min + 1 && last > 0){
            result[min - 1] = prev;
            result[min] = last;
            if (numbTrans.length > otherTrans.length) {
                int nextPrew = (last + numbTrans[max - 1])%10;
                int nextLast = (last + numbTrans[max - 1])/10;
                if (last + numbTrans[max - 1] > 10)  result = addSize(result, nextPrew, nextLast);
            }
            else {
                int nextPrew = (last + otherTrans[max - 1])%10;
                int nextLast = (last + otherTrans[max - 1])/10;
                if (last + otherTrans[max - 1] > 10)  result = addSize(result, nextPrew, nextLast);
            }

        }
        if (numbTrans.length > otherTrans.length && max > min + 1) end(min, max, numbTrans, result);
        if (numbTrans.length < otherTrans.length && max > min + 1) end(min, max, otherTrans, result);

        return new StringBuffer(Arrays.toString(result)).reverse().toString();
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
    private void end(int min, int max, int[] arr, int[] res){   // добавление оставшихся элементов
        //if (min == max && res[min] + arr[min] )
        for (int i = min+1; i < max; i++){
            res[i] += arr[i];

        }
    }
    private int[] addSize(int[] old, int prev, int last){      // случаи, когда Превышается размер результирующего массива
        int[] newRes = new int[old.length + 1];
        for (int i = 0; i < old.length - 1; i++){
            newRes[i] = old[i];
        }
        newRes[old.length - 1] = prev;
        newRes[old.length] = last;
        return newRes;
    }
}

class New{ //просто попробовал добыить вспомогательный класс
    static int[] transform(String number){
        char[] str = new StringBuffer(number).reverse().toString().toCharArray();
        int[] trans = new int[str.length];
        for (int i = 0; i < str.length; i++){
            trans[i] = str[i] - '0';
        }
        return trans;
    }
}
