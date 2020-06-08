package test;

public class Test {
    public static void main(String[] args) {
        A a = new C();
        System.out.println(a.getClass() == C.class);
    }
}
