package lists;

public class Animal {

    String name;

    public Animal(String name) {
        this.name = name;
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
