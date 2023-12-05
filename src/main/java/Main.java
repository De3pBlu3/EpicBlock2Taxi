import lists.DynamicArray;
import lists.Animal;

public class Main {
    public static void main(String[] args) {
        // Random examples of using list
        DynamicArray<Animal> list = new DynamicArray<>();

        list.append(new Animal("Peter"));
        list.append(new Animal("Meow"));
        list.append(new Animal("Milk"));
        list.append(new Animal("Piss"));
        list.append(new Animal("Milk"));
        list.append(new Animal("Cheese"));
        list.append(new Animal("Milk"));
        list.append(new Animal("Milk"));

        list.insert(2, new Animal("WOOF WOOF"));

        list.append(new Animal("Josephine"));
        list.append(new Animal("Laptop"));
        list.append(new Animal("Anne"));
        list.append(new Animal("Milk"));

        System.out.println(list);

        // The 'removeNOccurrences' methods use the .equals() method
        list.removeFirstOccurrence(new Animal("Milk"));
        System.out.println(list.pop());
        System.out.println(list.popLeft());
        System.out.println(list);

        list.removeLastOccurrence(new Animal("Milk"));
        System.out.println(list);

        list.removeAllOccurrences(new Animal("Milk"));
        System.out.println(list);

        System.out.println("\nSize: " + list.size());
        System.out.println("First: " + list.getFirst());
        System.out.println("Last: " + list.getLast());
        System.out.println("Index 3: " + list.get(3));

        list.forEach(System.out::println);
    }
}
