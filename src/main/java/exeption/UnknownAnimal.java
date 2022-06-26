package exeption;

public class UnknownAnimal extends Throwable {
    public UnknownAnimal(String s) {
        super(s);
    }
}
