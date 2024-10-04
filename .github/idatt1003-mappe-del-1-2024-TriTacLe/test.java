import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        int [] lst = {1,2,3,4,5};
        int [] newlst = new int[lst.length];

        for (int i = 0; i <= lst.length-1; i++) {
            newlst[i] = lst[i];
        }
        System.out.println(Arrays.toString(newlst));

    }
}   

