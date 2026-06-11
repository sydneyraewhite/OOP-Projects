package systemImp;
/*DO NOT CHANGE ME */
public class Student extends Person {
    private double gpa;  
   

    public Student(String name, int id, double gpa) {
        super(name, id);
        this.gpa = gpa;
    }

    public double getGpa() {
    	
        return gpa;
    }

    @Override
    public String toString() {
        return "Student{name='" + getName() + "', id=" + getId() + ", gpa=" + gpa + "}";
    }
    
    
}