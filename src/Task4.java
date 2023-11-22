import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task4 {
    /* Реалізуйте свій лінійний конгруентний генератор. Для цього почніть з x[0] = seed, і далі кожний наступний елемент рахуйте за формулою
    на зразок x[n + 1] = 1 (a x[n] + c) % m для коректних значень a, c, та m.
    Необхідно імплементувати метод, що приймає на вхід параметри a, c, та m, і повертає Stream<Long>.
    Для тесту використовуйте такі дані:
    a = 25214903917
    c = 11
    m = 2^48 (2в степені48`)
     */
    public static void main(String[] args) {
        Date dd = new Date();
        int c = Math.abs(dd.getSeconds()+dd.getMinutes()+1) / 2;
//        Randomiser rm = new Randomiser(25214903917L, c, 2 ^ 48 );
        Randomiser rm = new Randomiser();
        Stream<Integer> iterate = Stream.iterate(c, (seed) -> rm.withSeed(seed).next());
        List<Integer> collect = iterate
                .peek(System.out::println)
                .limit(20)
                .collect(Collectors.toList());
    }
}
