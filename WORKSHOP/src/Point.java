
import java.util.ArrayList;

public class Point {
    int count =0;
    int i =0;

    ArrayList<Integer> PointColors = new ArrayList<>();

    public void  Sum(int inp){
        count+=inp;

        i++;
    }


    public void  Result(int a){

        int res = count / i;
        System.out.println(a+" Value is: "+res);

    }

}
