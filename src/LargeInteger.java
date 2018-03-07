import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LargeInteger implements Comparable<LargeInteger> {

    private String number;

    public LargeInteger(String num){
        if (Pattern.matches("\\d+", num))
            this.number = num;
        else throw new ArithmeticException("Вы ввели не число");
    }

    public LargeInteger(int num){
        String numS = Integer.toString(num);
        if (num >= 0)
            this.number = numS;
        else throw new ArithmeticException("Вы ввели не число");
    }

    public String getNumber(){
        return number;
    }

    public LargeInteger add(LargeInteger otherNum){  //addition
        List<Byte> othList = Help.list(otherNum.getNumber());
        List<Byte> numbList = Help.list(getNumber());
        int min = Math.min(numbList.size(), othList.size());
        int max = Math.max(numbList.size(), othList.size());
        List<Byte> result = Help.addZero(0, max + 1, new ArrayList<>());   //нужно заранее заполнить все нулями!!!!!!!!!
        if (othList.size() < numbList.size())
            othList = Help.addZero(min, max, othList);
        else numbList = Help.addZero(min, max, numbList);

        for (int i = 0; i < max; i++){
            int sum = numbList.get(i) + othList.get(i) + result.get(i);
            result.set(i, (byte)(sum % 10));
            result.set(i + 1, (byte)(sum / 10));
        }
        return new LargeInteger(Help.toString(result));
    }

    public LargeInteger sub(LargeInteger otherNum){ //subtraction
        List<Byte> othList = Help.list(otherNum.getNumber());
        List<Byte> numList = Help.list(getNumber());
        int min = othList.size();
        int max = numList.size();
        List<Byte> result = new ArrayList<>(max + 1);
        String sign = "";

        if(compareTo(otherNum) < 0) throw new ArithmeticException("В ответе получится отрицательное число");
        for (int i = 0; i < min; i++){
            int difference = numList.get(i) - othList.get(i);
            if (difference < 0) {
                result.add(i, (byte)(numList.get(i) - othList.get(i) + 10));
                numList.set(i + 1, (byte)(numList.get(i + 1) - 1));
            }
            else result.add(i, (byte)(numList.get(i) - othList.get(i)));
        }
        if (max > min)
            for (int i = min; i < max; i++ )
                result.add(i, numList.get(i));
        String str = Help.toString(result);
        if (Pattern.matches("00*", str))
            str = str.replaceFirst("0*", "0");
        if (Pattern.matches("0*[^0]+", str))
            str = str.replaceFirst("0*", "");
        return new LargeInteger(sign + str);
    }

    public LargeInteger multi(LargeInteger otherNum){  //multiplier
        List<Byte> minList = compareTo(otherNum) <= 0 ? Help.list(getNumber()) : Help.list(otherNum.getNumber());
        List<Byte> maxList = compareTo(otherNum) <= 0 ? Help.list(otherNum.getNumber()) : Help.list(getNumber());
        LargeInteger result = new LargeInteger("0");
        List<Byte> resList;
        int min = minList.size();
        int tens = 0;

        for (int elem: maxList){
            resList = Help.addZero(0, min + tens + 1 , new ArrayList<>(min + tens + 1));
            for (int i = 0; i < min + tens; i++){
                if (i >= tens){
                    int sum = elem * minList.get(i - tens) + resList.get(i);
                    resList.set(i, (byte)(sum % 10));
                    resList.set(i + 1, (byte)(sum / 10));
                }
            }
            result = result.add(new LargeInteger(Help.toString(resList)));
            tens++;
        }
        return result;
    }

    public LargeInteger div(LargeInteger otherNum){  //division
        if (compareTo(otherNum) < 0)
            return new LargeInteger("0");
        return dAm(otherNum, true);
    }

    public LargeInteger mod(LargeInteger otherNum){  //modulo
        if (compareTo(otherNum) < 0)
            return new LargeInteger(getNumber());
        return dAm(otherNum, false);
    }

    private LargeInteger dAm(LargeInteger otherNum, boolean res){
        List<Byte> dividend = Help.list(getNumber());
        StringBuilder result = new StringBuilder();
        LargeInteger mediate = new LargeInteger("0");
        int numSize = getNumber().length();
        int key = 0;
        boolean start = false;

        for (int i = numSize - 1; i >= 0; i--){
            mediate = new LargeInteger(Help.toString(dividend.subList(i ,numSize)));
            if (mediate.compareTo(otherNum) >= 0) {
                while (mediate.compareTo(otherNum) >= 0) {
                    mediate = mediate.sub(otherNum);
                    key++;
                }
                result.append(key);
                start = true;
            }
            else if (start)
                result.append(key);
            if (i != 0 && key > 0) {
                if (!Objects.equals(mediate.getNumber(), "0"))
                    dividend = Help.union(dividend.subList(0, i), Help.list(mediate.getNumber()));
                else dividend = dividend.subList(0, i);
            }
            key = 0;
            numSize = dividend.size();
        }
        if (res)
        return new LargeInteger(result.toString());
        else return mediate;
    }

    @Override
    public String toString() {
        return "LargeInteger{" +
                "number = '" + number + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object otherNum){
        if (otherNum instanceof LargeInteger){
            LargeInteger other = (LargeInteger) otherNum;
            return Objects.equals(number, other.number);
        }
        return false;
    }

    @Override
    public int compareTo(LargeInteger otherNum) {
        List<Byte> othList = Help.list(otherNum.getNumber());
        List<Byte> numbList = Help.list(getNumber());
        int othSize = othList.size();
        int numSize = numbList.size();
        int k = 0;

        if (numSize == othSize)
            for (int i = numSize - 1; i >= 0; i--) {
                if (numbList.get(i) > othList.get(i)) {k = 1; break;}
                if (numbList.get(i) < othList.get(i)) {k = -1; break;}
            }
        else k = numSize > othSize? 1 : -1;
        return k;
    }

    static class Help {
        static List<Byte> addZero(int min, int max, List<Byte> list){
            for (int i = min; i < max; i++ )
                list.add(i, (byte)0);
            return list;
        }

        static String toString(List<Byte> res){
            int maximum = res.size();
            if (res.get(maximum - 1) == 0 && maximum - 1 != 0)
                res.remove(maximum - 1);
            String str = res.stream().map(Object::toString).collect(Collectors.joining(""));
            return new StringBuffer(str).reverse().toString();
        }

        static List<Byte> union(List<Byte> a, List<Byte> b){
            int sizeA = a.size();
            int sizeB = b.size();
            for (int i = 0; i < sizeB; i++){
                a.add(sizeA + i,b.get(i));
            }
            return a;
        }

        private static List<Byte> list(String number){
            List<Byte> result = new ArrayList<>();

            for (int i = 0; i < number.length(); i++){
                result.add(i, (byte)(number.charAt(number.length() - 1 - i) - '0'));
            }
            return result;
        }
    }
}
