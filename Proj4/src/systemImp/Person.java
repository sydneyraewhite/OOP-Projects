package systemImp;

/*DO NOT CHANGE ME */

public class Person implements Comparable<Person> {
    private String name;
    private int id;

    public Person(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Person other) {
        return Integer.compare(this.id, other.id);
    }
    
    @Override
    public boolean equals(Object obj) {
        // Check if the object is the same instance
        if (this == obj) {
            return true;
        }
        
        // Check if the object is an instance of Person
        if (!(obj instanceof Person)) {
            return false;
        }
        
        // Cast the object to Person and compare the id field
        Person other = (Person) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', id=" + id + "}";
    }
}

