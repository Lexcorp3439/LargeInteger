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

    private static ArrayList<Integer> list(String number){
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

    public boolean more(LargeInteger otherNum){
        ArrayList<Integer> othList = list(otherNum.getNumber());
        ArrayList<Integer> numbList = list(getNumber());
        int othSize = othList.size();
        int numSize = numbList.size();

        if (numSize == othSize)
            for (int i = numSize - 1; i >= 0; i--) {
                if (numbList.get(i) > othList.get(i)) return true;
                if (numbList.get(i) < othList.get(i)) return false;
            }
        return numSize > othSize;
    }

    public boolean moreOrEqual(LargeInteger otherNum){
        return equals(otherNum) || more(otherNum);
    }

    public LargeInteger add(LargeInteger otherNum){  //addition
        ArrayList<Integer> othList = list(otherNum.getNumber());
        ArrayList<Integer> numbList = list(getNumber());
        int min = Math.min(numbList.size(), othList.size());
        int max = Math.max(numbList.size(), othList.size());
        ArrayList<Integer> result = Helper.zeroAdd(0, max + 1, new ArrayList<>());   //нужно заранее заполнить все нулями!!!!!!!!!
        if (othList.size() < numbList.size()) othList = Helper.zeroAdd(min, max, othList);
        else numbList = Helper.zeroAdd(min, max, numbList);

        for (int i = 0; i < max; i++){
            int sum = numbList.get(i) + othList.get(i) + result.get(i);
            result.set(i, sum % 10);
            result.set(i + 1, sum / 10);
        }
        return new LargeInteger(Helper.retResult(result));
    }

    public LargeInteger sub(LargeInteger otherNum){ //subtraction
        ArrayList<Integer> minList = more(otherNum)? list(otherNum.getNumber()) : list(getNumber());
        ArrayList<Integer> maxList = more(otherNum)? list(getNumber()) : list(otherNum.getNumber());
        int min = minList.size();
        int max = maxList.size();
        ArrayList<Integer> result = new ArrayList<>(max + 1);
        String sign = more(otherNum) || equals(otherNum)? "" : "-";

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

    public LargeInteger multi(LargeInteger otherNum){  //multiplier
        ArrayList<Integer> minList = more(otherNum)? list(otherNum.getNumber()) : list(getNumber());
        ArrayList<Integer> maxList = more(otherNum)? list(getNumber()) : list(otherNum.getNumber());
        LargeInteger result = new LargeInteger("0");
        ArrayList<Integer> resList;
        int min = minList.size();
        int tens = 0;

        for (int elem: maxList){
            resList = Helper.zeroAdd(0, min + tens + 1 , new ArrayList<>(min + tens + 1));
            for (int i = 0; i < min + tens; i++){
                if (i >= tens){
                    int sum = elem * minList.get(i - tens) + resList.get(i);
                    resList.set(i, sum % 10);
                    resList.set(i + 1, sum / 10);
                }
            }
            result = result.add(new LargeInteger(Helper.retResult(resList)));
            tens++;
        }
        return result;
    }

    public LargeInteger div(LargeInteger otherNum){  //division
        ArrayList<Integer> dividend = list(getNumber());
        StringBuilder result = new StringBuilder();
        LargeInteger mediate ;
        int numSize = getNumber().length();
        int key = 0;

        if (!more(otherNum)) return new LargeInteger("0");
        for (int i = numSize - 1; i >= 0; i--){
            mediate = new LargeInteger(Helper.retResult(Helper.subList(dividend, i,numSize - 1)));
            while (mediate.moreOrEqual(otherNum)){
                mediate = mediate.sub(otherNum);
                key++;
            }
            if (key != 0) result.append(key);
            key = 0;
            if (i != 0)
                if (!Objects.equals(mediate.getNumber(), "0"))
                    dividend = Helper.union(Helper.subList(dividend, 0, i - 1), list(mediate.getNumber()));
                else dividend = Helper.subList(dividend, 0, i - 1);
            numSize = dividend.size();
        }
        return new LargeInteger(result.toString());
    }

    public LargeInteger mod(LargeInteger otherNum){  //modulo
        if (!more(otherNum)) return new LargeInteger(getNumber());
        return sub(otherNum.multi(div(otherNum)));
    }


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
        if (res.get(maximum - 1) == 0 && maximum - 1 != 0) res.remove(maximum - 1);  //
        str = new StringBuffer(res.toString().replaceAll("[,\\[\\] ]", "")).reverse().toString();
        return str;
    }

    static ArrayList<Integer> union(ArrayList<Integer> a, ArrayList<Integer> b){
        for (int i = 0; i < b.size(); i++){
            a.add(a.size() + i,b.get(i));
        }
        return a;
    }

    static ArrayList<Integer> subList(ArrayList<Integer> arr, int low, int up){
       ArrayList<Integer> newArr = new ArrayList<>();
       for (int i = low; i <= up; i++)
           newArr.add(i - low, arr.get(i));
       return newArr;
    }
}

//for (LargeInteger dek = new LargeInteger("0"); dek.more(new LargeInteger(getNumber())); dek.add(new LargeInteger("1")))  подумать над использованием этой прекрасной штуки...