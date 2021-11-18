public class Comparator implements java.util.Comparator<Kromoson> {
    @Override
    public int compare(Kromoson o1, Kromoson o2) {
        if (o1.getFF() > o2.getFF()) {
            return 1;
        } else if (o1.getFF() < o2.getFF()) {
            return -1;
        }
        return 0;

    }
}
