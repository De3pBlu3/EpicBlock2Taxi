import lists.DynamicArray;
import lists.Animal;

public class Main {
    public static void main(String[] args) {
        // Random examples of using list
        DynamicArray<Animal> list = new DynamicArray<>(new Animal());

        list.add(new Animal("Peter"));
        list.add(new Animal("Meow"));
        list.add(new Animal("Milk"));
        list.add(new Animal("Piss"));
        list.add(new Animal("Milk"));
        list.add(new Animal("Cheese"));
        list.add(new Animal("Milk"));
        list.add(new Animal("Milk"));

        list.insert(2, new Animal("WOOF WOOF"));

        list.add(new Animal("Josephine"));
        list.add(new Animal("Laptop"));
        list.add(new Animal("Anne"));
        list.add(new Animal("Milk"));

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
