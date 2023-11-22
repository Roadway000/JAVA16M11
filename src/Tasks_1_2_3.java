import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Tasks_1_2_3 {
    public static String getOddUsersById(List<User> users) {
        AtomicInteger i = new AtomicInteger(1);
        String result = users.stream()
                .map(u -> i.get() + " : " + u.getName())
                .filter(s -> {
                    return i.getAndIncrement() % 2 != 0;
                })
                .collect(Collectors.joining(", "));
        return result;
    }
    public static String getSortedUsers(List<User> users) {
        String result = users.stream()
                .map(u -> u.getName().toUpperCase())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.joining(", "));
        return result;
    }
    public static String getSortedNumeric(String[] arrStr) {
        StringBuilder sb = new StringBuilder();
        for (String s: arrStr) {
            if (sb.length()==0) sb.append(s);
            else {
                sb.append(", ");
                sb.append(s);
            }
        }
        List<Integer> listInt = new ArrayList<Integer>();
        for (String s: sb.toString().split(",")) {
            listInt.add(Integer.parseInt(s.trim()));
        }
        Collections.sort(listInt);
        return listInt.toString();
    }

    public static void main(String[] args) {
        List<User> names = new ArrayList<>();
        names.add(new User("Arturo"));
        names.add(new User("Paul"));
        names.add(new User("Nikora"));
        names.add(new User("Luigi"));
        names.add(new User("Jakuro"));
        names.add(new User("Bogdan"));
        System.out.println(Arrays.toString(new List[]{names}));
        /*  Завдання 1
            Метод приймає на вхід список імен. Необхідно повернути рядок вигляду 1. Ivan, 3. Peter...
            лише з тими іменами, що стоять під непарним індексом (1, 3 тощо)
         */
        System.out.println("Task 1: -> "+getOddUsersById((ArrayList) names));
        /*  Завдання 2
            Метод приймає на вхід список рядків (можна взяти список із Завдання 1). Повертає список цих рядків у верхньому регістрі,
            і відсортованих за спаданням (від Z до A).
         */
        System.out.println("Task 2: -> "+getSortedUsers((ArrayList) names));
        /*  Завдання 3
            Є масив:    ["1, 2, 0", "4, 5"]
            Необхідно отримати з масиву всі числа, і вивести їх у відсортованому вигляді через кому ,, наприклад:
            "0, 1, 2, 4, 5"
         */
        String[] newArr = {"1, 2, 0", "4, 5", "34,42,1", "8,9,9,0,3,-7,4"};
        System.out.println("Task 3: -> "+getSortedNumeric(newArr));
    }
}
class User {
    private String name;
    private int id = 0;
    private static int counter = 0;
    private static int nextId() {
        return ++counter;
    }
    public User(String name) {
        this.name = name;
        this.id = nextId();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    @Override
    public String toString() {
        return id + " : " + name;
    }
}
