import java.util.ArrayList;
import java.util.Random;

public class Main {
    static final int FIELD_SIZE=100;
    static final int EVOLUCJE=1000;
    static final int POPULACIJA=1000 ;
    static final int MUTATION_CHANCE=90;
    static final int MAX_AGE=200  ;
    static ArrayList<Kromoson> kromosons = new ArrayList<>();
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        for(int i=0;i<POPULACIJA;i++){
            int[] a;
            a = popuniRand();
            kromosons.add(new Kromoson(FIELD_SIZE,a,calculateFF(a)));
        }



        boolean nemaRjesenja = true;
        int c = 0;

        while (nemaRjesenja) {
            if (c > EVOLUCJE-2) {
                nemaRjesenja = false;
                System.out.println("Riješenje nije nađeno!");
                long endTime = System.currentTimeMillis();

                System.out.println("Duration:" + (endTime - startTime));
            }

            kromosons.sort(new Comparator());

            if (kromosons.get(0).getFF() == 0) {
                System.out.println("RIJEŠENJE NAĐENO U "+(c+1)+". GENERACIJI");
                System.out.println("FF:" + kromosons.get(0));
                print2d(kromosons.get(0).getPozicije());
                long endTime = System.currentTimeMillis();

                System.out.println("Duration:" + (endTime - startTime));
                System.exit(0);
            }

            for (int i = 0; i < kromosons.size(); i++) {
                kromosons.get(i).age++;
                if (kromosons.get(i).age > MAX_AGE)
                    kromosons.remove(i);
                }

            while (kromosons.size() > POPULACIJA) {
                kromosons.remove(kromosons.size() - 1);
            }

            c++;
            System.out.print("FF: " + kromosons.get(0).getFF() + " ");
            System.out.print("Populacija: " + kromosons.size() + " ");
            System.out.println("Generacija:"+c);

            //REKOMBINACIJA SAMO ZA 50% NAJBOLJIH KROMOSOMA
            for(int i = 1; i < POPULACIJA/2; i = i + 2){
                rekombinacijaUJednojTocki(kromosons.get(i-1),kromosons.get(i));
            }

        }
    }



    public static int[] popuniRand(){
        int[] pozicije = new int[FIELD_SIZE];
        Random random = new Random();
        for (int i = 0; i < FIELD_SIZE; i++) {
            pozicije[i] = random.nextInt(FIELD_SIZE);
            for (int j = 0; j < i; j++) {
                if (pozicije[i] == pozicije[j]) {
                    i--;
                    break;
                }
            }
        }
        return pozicije;
    }

    public static void rekombinacijaUJednojTocki(Kromoson parent1, Kromoson parent2) {

        Random random=new Random();

        int index = random.nextInt(FIELD_SIZE-2   )+1;

        int[] poljeDijete1 = new int[FIELD_SIZE];
        int[] poljeDijete2 = new int[FIELD_SIZE];
        for (int i = 0; i < index; i++) {
            poljeDijete1[i] = parent1.getPozicije()[i];
            poljeDijete2[i] = parent2.getPozicije()[i];
        }
        for(int j=index;j<poljeDijete1.length;j++){
            poljeDijete1[j]=999999;
            poljeDijete2[j]=999999;
        }

        int newCP = index;
        while(newCP != poljeDijete1.length){
            for(int i = 0; i < parent2.getPozicije().length; i++){
                if(!contains(poljeDijete1, parent2.getPozicije()[i])){
                    poljeDijete1[newCP] = parent2.getPozicije()[i];
                    newCP++;
                }
            }
        }
        newCP = index;
        while(newCP != poljeDijete2.length){
            for(int i = 0; i < parent1.getPozicije().length; i++){
                if(!contains(poljeDijete2, parent1.getPozicije()[i])){
                    poljeDijete2[newCP] = parent1.getPozicije()[i];
                    newCP++;
                }
            }
        }
        if(calculateFF(poljeDijete1)!=0){
            mutate(poljeDijete1);
        }
        if(calculateFF(poljeDijete2)!=0){
            mutate(poljeDijete2);
        }

        Kromoson dijete1 = new Kromoson(FIELD_SIZE,poljeDijete1,calculateFF(poljeDijete1));
        Kromoson dijete2 = new Kromoson(FIELD_SIZE,poljeDijete2,calculateFF(poljeDijete2));
        kromosons.add(dijete1);
        kromosons.add(dijete2);


    }

    private static boolean contains(int[] a, int value){
        for(int i = 0; i < a.length; i++){
            if(a[i] == value)
                return true;
        }

        return false;
    }

    public static void mutate(int[] a){
        Random r = new Random();
        int b = r.nextInt(a.length);
        int c = r.nextInt(a.length);
        if(r.nextInt(100) < MUTATION_CHANCE){
            int temp = a[b];
            a[b] = a[c];
            a[c] = temp;

        }

    }

    public static void print2d(int[]polje){
        int[][] ploca = new int[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            ploca[polje[i]][i] = 1;

        }

        for (int i = 0; i < FIELD_SIZE; i++) {
            for(int j=0;j<FIELD_SIZE;j++) {
                System.out.print(ploca[i][j] +" ");
            }
            System.out.println();

        }

    }

    public static int ffHelp(int[][] polje, int kraljicaY, int kraljicaX){
        int i, j;
        int kolizije=0;
        //Check to the LOWER LEFT DIAGONAL of the queen
        if(kraljicaX!=0 && kraljicaY!=FIELD_SIZE-1) {
            for (i = kraljicaY + 1, j = kraljicaX - 1; j >=0 && i<FIELD_SIZE; i++, j--) {
                if(polje[i][j]==1){
                    kolizije++;
                }
            }
        }

        //Check to the UPPER LEFT DIAGONAL of the queen
        if(kraljicaX!=0 && kraljicaY!=0) {
            for (i = kraljicaY-1, j = kraljicaX-1;i>=0&&j>=0 ;i--,j--) {
                if(polje[i][j]==1){
                    kolizije++;
                }
            }
        }


        // Check to the LOWER RIGHT DIAGONAL of the queen
        if(kraljicaX!=FIELD_SIZE-1 && kraljicaY!=FIELD_SIZE-1) {
            for (i = kraljicaY+1, j = kraljicaX+1;i<FIELD_SIZE && j<FIELD_SIZE;i++,j++) {
                if(polje[i][j]==1){
                    kolizije++;
                }
            }
        }

        // Check to the UPPER RIGHT DIAGONAL of the queen
        if(kraljicaY!=0 && kraljicaX<FIELD_SIZE-1) {
            for (i = kraljicaY-1, j = kraljicaX+1; i>=0&&j<FIELD_SIZE; i--,j++) {
                if(polje[i][j]==1){
                    kolizije++;
                }
            }
        }
        return kolizije;
    }

    public static int calculateFF(int[] polje){
        int ff=0;
        int[][]ploca = new int[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            ploca[polje[i]][i] = 1;

        }
        for (int n = 0; n < FIELD_SIZE; n++) {
            for (int v = 0; v < FIELD_SIZE; v++) {
                if (ploca[n][v] == 1) {
                    ff += ffHelp(ploca, n, v);
                }

            }
        }
        return ff;
    }
}


