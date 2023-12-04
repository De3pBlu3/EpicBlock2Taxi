package lists;

public class Animal implements DynamicArrayable<Animal> {

    String name;

    public Animal() {}

    public Animal(String name) {
        this.name = name;
    }

    @Override
    public Animal[] newArray(int length) {
        return new Animal[length];
    }

    @Override
    public String toString() {
        return "'" + this.name + "'";
    }

}
