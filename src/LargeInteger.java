import java.net.SocketPermission;
import java.util.ArrayList;
import java.util.Objects;

public class LargeInteger  {

    private String number;

    public LargeInteger(String num){
        this.number = num;
    }

    public String getNumber(){
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
    @Override
    public boolean equals(Object otherNum){  //исрпавить !!!!
        if (otherNum instanceof LargeInteger){
            LargeInteger other = (LargeInteger) otherNum;
            return Objects.equals(number, other.number);
        }
        return false;
    }

    public boolean bigger(LargeInteger otherNum){
        ArrayList<Integer> otherTrans = list(otherNum.getNumber());
        ArrayList<Integer> numbTrans = list(getNumber());
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
        ArrayList<Integer> otherTrans = list(otherNum.getNumber());
        ArrayList<Integer> numbTrans = list(getNumber());
        int min = Math.min(numbTrans.size(), otherTrans.size());
        int max = Math.max(numbTrans.size(), otherTrans.size());
        ArrayList<Integer> result = Helper.zeroAdd(0, max + 1, new ArrayList<>());   //нужно заранее заполнить все нулями!!!!!!!!!
        if (otherTrans.size() < numbTrans.size()) otherTrans = Helper.zeroAdd(min, max, otherTrans);
        else numbTrans = Helper.zeroAdd(min, max, numbTrans);

        for (int i = 0; i < max; i++){
            int sum = numbTrans.get(i) + otherTrans.get(i) + result.get(i);
            result.set(i, sum % 10);
            result.set(i + 1, sum / 10);
        }
        System.out.println(Helper.retResult(result));
        return new LargeInteger(Helper.retResult(result));
    }

    public LargeInteger subtraction(LargeInteger otherNum){
        ArrayList<Integer> minList = bigger(otherNum)? list(otherNum.getNumber()) : list(getNumber());
        ArrayList<Integer> maxList = bigger(otherNum)? list(getNumber()) : list(otherNum.getNumber());
        int min = minList.size();
        int max = maxList.size();
        ArrayList<Integer> result = new ArrayList<>(max + 1);
        String sign = !bigger(otherNum)? "-" : "";

        for (int i = 0; i < min; i++){
            int difference = maxList.get(i) - minList.get(i);
            if (difference < 0) {
                result.add(i, (maxList.get(i) - minList.get(i) + 10));
                maxList.set(i + 1, maxList.get(i + 1) - 1);
            }
            else result.add(i, maxList.get(i) - minList.get(i));
        }
        if (max > min)
            for (int i = min; i < max; i++ )
                result.add(i, maxList.get(i));
        return new LargeInteger(sign + Helper.retResult(result));
    }

    public LargeInteger multiplier(LargeInteger otherNum){
        ArrayList<Integer> minList = bigger(otherNum)? list(otherNum.getNumber()) : list(getNumber());
        ArrayList<Integer> maxList = bigger(otherNum)? list(getNumber()) : list(otherNum.getNumber());
        LargeInteger result = new LargeInteger("0");
        ArrayList<Integer> res;
        int min = minList.size();
        int tens = 0;

        for (int elem: maxList){
            res = Helper.zeroAdd(0, min + tens + 1 , new ArrayList<>(min + tens + 1));
            for (int i = 0; i < min + tens; i++){
                if (i >= tens){
                    int sum = elem * minList.get(i - tens) + res.get(i);
                    res.set(i, sum % 10);
                    res.set(i + 1, sum / 10);
                }
            }
            result = result.addition(new LargeInteger(Helper.retResult(res)));
            tens++;
        }
        return result;
    }

    ////////   БЛОК ДЕЛЕНИЕ\ОСТАТОК   ////////////////////////////
//    public LargeInteger division(LargeInteger otherNum){      //деление
//        String newNum = " dsfdsf";                //какой-то алгоритм
//        return newNum;
//    }
//    public LargeInteger residue(LargeInteger otherNum){       //остаток
//        String newNum = " dsfdsf";                //какой-то алгоритм
//        return newNum;
//    }


}

class Helper {
    static ArrayList<Integer> zeroAdd(int min,int max, ArrayList<Integer> list){
        for (int i = min; i < max; i++ )
            list.add(i, 0);
        return list;
    }

    static String retResult(ArrayList<Integer> res){
        String str;
        int maximum = res.size();
        if (res.get(maximum - 1) == 0) res.remove(maximum - 1);  //
        str = new StringBuffer(res.toString().replaceAll("[,\\[\\] ]", "")).reverse().toString();
        return str;
    }
}