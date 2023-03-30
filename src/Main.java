import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Margaret", "William", "Catherine", "Beatrice", "Elizabeth", "Philipp", "Katy");
        List<String> families = Arrays.asList("Smoke", "Spoke", "Daniels", "Red", "Davidson", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        long countMinor = persons.stream()
                .filter(person -> person.getAge() < 18)
                .count();
        System.out.println("Количество несовершеннолетних: " + countMinor);

        List<String> conscripts = persons.stream()
                .filter(person -> person.getSex() == Sex.MAN)
                .filter(person -> person.getAge() >= 18 && person.getAge() <= 27)
                .flatMap(person -> Stream.of(person.getFamily()))
                .limit(5)
                .collect(Collectors.toList());
        System.out.println(conscripts);

        Collection<Person> workables = persons.stream()
                .filter(person -> person.getAge() >= 18)
                .filter(person -> person.getEducation() == Education.HIGHER)
                .filter(person -> person.getSex() == Sex.MAN ? person.getAge() <= 65 : person.getAge() <= 60)
                .sorted(Comparator.comparing(Person::getFamily))
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("Список потенциально работоспособных людей: " + workables);
    }
}