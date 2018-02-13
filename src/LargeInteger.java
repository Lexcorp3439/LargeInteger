import java.util.ArrayList;

public class LargeInteger {
    private String number;

    public LargeInteger(String num){
        this.number = num;
    }

    public String gerNumber(){
        return number;
    }

    static ArrayList<Integer> list (String number){
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < number.length(); i++){
            result.add(i, number.charAt(number.length() - 1 - i) - '0');
        }
        return result;
    }

    /////////   БЛОК СРАВНЕНИЕ   ///////////////////////////
    public boolean equals(LargeInteger otherNum){
        return number.equals(otherNum.gerNumber());
    }
    public boolean bigger(LargeInteger otherNum){
        ArrayList<Integer> otherTrans = list(otherNum.gerNumber());
        ArrayList<Integer> numbTrans = list(gerNumber());
        int othSize = otherTrans.size();
        int numSize = numbTrans.size();

        if (numSize == othSize)
            for (int i = numSize - 1; i >= 0; i--) {
                if (numbTrans.get(i) > otherTrans.get(i)) return true;
                if (numbTrans.get(i) < otherTrans.get(i)) return false;
            }
        return numSize > othSize;
    }

    /////////   БЛОК СЛОЖЕНИЕ\ВЫЧИТАНИЕ\УМНОЖЕНИЕ   /////////////////
    public LargeInteger addition(LargeInteger otherNum){
        ArrayList<Integer> otherTrans = list(otherNum.gerNumber());
        ArrayList<Integer> numbTrans = list(gerNumber());
        int othSize = otherTrans.size();
        int numSize = numbTrans.size();
        int min = Math.min(numSize, othSize);
        int max = Math.max(numSize, othSize);
        ArrayList<Integer> result = new ArrayList<>(max + 1);

        for (int i = 0; i < min; i++){
            result.add(i, (numbTrans.get(i) + otherTrans.get(i)) % 10);
            result.add(i + 1, (numbTrans.get(i) + otherTrans.get(i)) / 10);
        }
        if (numSize > othSize) result = Helper.complete(min, max, numbTrans, result, '+');
        if (numSize < othSize) result = Helper.complete(min, max, otherTrans, result, '+');
        System.out.print(Helper.retResult(result));
        return new LargeInteger(Helper.retResult(result));
    }

    public LargeInteger subtraction(LargeInteger otherNum){       //определить наибольшое число и вычитать из него
        ArrayList<Integer> otherTrans = list(otherNum.gerNumber());
        ArrayList<Integer> numbTrans = list(gerNumber());
        int othSize = otherTrans.size();
        int numSize = numbTrans.size();
        int min = Math.min(numSize, othSize);
        int max = Math.max(numSize, othSize);
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> minList;
        ArrayList<Integer> maxList;
        String sign = "";

        if (bigger(otherNum)) {
            maxList = numbTrans;
            minList = otherTrans;
        }
        else {
            minList = numbTrans;
            maxList = otherTrans;
            sign = "-";
        }
        for (int i = 0; i < min; i++){
            int difference = maxList.get(i) - minList.get(i);
            if (difference < 0) {
                result.add(i, (maxList.get(i) - minList.get(i) + 10));
                maxList.set(i + 1, minList.get(i + 1) - 1);
            }
            else result.add(i, maxList.get(i) - minList.get(i));
        }
        if (max > min) result = Helper.complete(min, max, maxList, result, '-');
        System.out.print(sign + Helper.retResult(result));
        return new LargeInteger(sign + Helper.retResult(result));

    }
//    public String multiplier(String otherNum){    //умножение
//        ArrayList<Integer> otherTrans = Helper.transform(otherNum);
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

class Helper {
    static ArrayList<Integer> complete(int min, int max, ArrayList<Integer> list, ArrayList<Integer> res, char oper){   // добавление оставшихся элементов
        switch (oper){
            case '+':{
                int div;
                int mod;

                for (int i = min; i < max; i++){
                    div = (list.get(min) + res.get(min))/10;
                    mod = (list.get(min) + res.get(min))%10;
                    res.add(i, mod);
                    res.add(i + 1, div);
                }
            }
            case '-':{
                for (int i = min; i < max; i++){
                    res.add(i, list.get(i));
                }
            }
        }
        return res;
    }

    static String retResult(ArrayList<Integer> res){
        String str;
        int maximum = res.size();
        if (res.get(maximum - 1) == 0) res.remove(maximum - 1);  //
        str = new StringBuffer(res.toString().replaceAll("[,\\[\\] ]", "")).reverse().toString();
        return str;
    }
}