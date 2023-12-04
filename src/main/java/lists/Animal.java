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
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Animal)
            return o.toString().equals(this.toString());

        return false;
    }

}
