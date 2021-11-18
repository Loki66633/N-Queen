import java.util.Arrays;

public class Kromoson {
    int size;
    int age;
    int ff;
    int [] pozicije;

    public Kromoson(int size, int[] pozicije,int ff) {
        this.size = size;
        this.ff = ff;
        this.pozicije = pozicije;
        age=0;
    }

    @Override
    public String toString(){
        return ff + " " + Arrays.toString(pozicije);
    }

    public int getFF(){
        return ff;
    }

    public int[] getPozicije(){
        return pozicije;
    }
}
