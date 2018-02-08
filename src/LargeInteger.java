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
        int[] result = new int[Math.max(numbTrans.length, otherTrans.length)];
        for (int i = 0; i < Math.min(numbTrans.length, otherTrans.length); i++){
            result[i] += (numbTrans[i] + otherTrans[i])%10;
            result[i+1] = (numbTrans[i] + otherTrans[i])/10;
        }
        // доделать случай, когда остались символы в наибольшем числе
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
