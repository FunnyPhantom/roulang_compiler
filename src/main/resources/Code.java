import java.util.Scanner;


public class Code {
    public static final int LONG_FIELD_SIZE = 8;
    public static final int INT_FIELD_SIZE = 4;
    public static final int SHORT_FIELD_SIZE = 2;
    public static final int CHAR_FIELD_SIZE = 2;
    public static final int BYTE_FIELD_SIZE = 1;
    public static final int BOOLEAN_FIELD_SIZE = 1;
    public static final int DOUBLE_FIELD_SIZE = 8;
    public static final int FLOAT_FIELD_SIZE = 4;
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Code code = new Code();
        code.start(args);
    }

    int idum;
    long ldum;
    boolean bdum;
    String sdum;
    float fdum;
    double ddum;
    char cdum;


    void Println(boolean x) {
        System.out.println(x);
    }

    void Println(char x) {
        System.out.println(x);
    }

    void Println(int x) {
        System.out.println(x);
    }

    void Println(long x) {
        System.out.println(x);
    }

    void Println(float x) {
        System.out.println(x);
    }

    void Println(double x) {
        System.out.println(x);
    }

    void Println(char[] x) {
        System.out.println(x);
    }

    void Println(String x) {
        System.out.println(x);
    }

    void Println(short x){
        System.out.println(x);
    }

    void Println(Object x) {
        System.out.println(x);
    }

    String Input(String s) {
        return sc.nextLine();
    }

    boolean Input(boolean b) {
        return sc.nextBoolean();
    }

    char Input(char c) {
        return sc.nextLine().charAt(0);
    }

    float Input(float f) {
        return sc.nextFloat();
    }

    double Input(double d) {
        return sc.nextDouble();
    }

    int Input(int i) {
        return sc.nextInt();
    }

    long Input(long l) {
        return sc.nextLong();
    }

    int Sizeof(long l) {
        return LONG_FIELD_SIZE;
    }

    int Sizeof(int i) {
        return INT_FIELD_SIZE;
    }

    int Sizeof(short s) {
        return SHORT_FIELD_SIZE;
    }

    int Sizeof(char c) {
        return CHAR_FIELD_SIZE;
    }

    int Sizeof(byte b) {
        return BYTE_FIELD_SIZE;
    }

    int Sizeof(boolean b) {
        return BOOLEAN_FIELD_SIZE;
    }

    int Sizeof(double d) {
        return DOUBLE_FIELD_SIZE;
    }

    int Sizeof(float f) {
        return FLOAT_FIELD_SIZE;
    }

    int Len(boolean[] x) {
        return x.length;
    }

    int Len(char[] x) {
        return x.length;
    }

    int Len(int[] x) {
        return x.length;
    }

    int Len(long[] x) {
        return x.length;
    }

    int Len(float[] x) {
        return x.length;
    }

    int Len(double[] x) {
        return x.length;
    }

    int Len(String x) {
        return x.length();
    }

    int Len(short[] x){
        return x.length;
    }

    int Len(Object[] obj) {
        return obj.length;
    }

}
