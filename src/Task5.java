import java.util.*;
import static java.lang.Math.min;

public class Task5 {
    /* Напишіть метод public static <T> Stream<T> zip(Stream<T> first, Stream<T> second)
    який "перемішує" елементи зі стрімів first та second, зупиняючись тоді, коли у одного зі стрімів закінчаться елементи.
    */
    public static <T> void zip(List<T> l1, List<T> l2){
        Iterator<T> iterl1 = l1.iterator();
        Iterator<T> iterl2 = l2.iterator();
        int size = min(l1.size(), l2.size());
        List<T> result = new ArrayList<>();
        for (int i=0; i < size * 2; i++) {
            if(i%2==0 && iterl1.hasNext())
                result.add(iterl1.next());
            else
            if(iterl2.hasNext())
                result.add(iterl2.next());
        }
        Collections.shuffle(result);
        result.forEach(System.out::println);
    }
    public static void main(String[] args) {
        List<Integer> l1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> l2 = Arrays.asList(11, 22, 33);
        zip(l1,l2);
    }
}
