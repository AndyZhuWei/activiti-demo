package cn.andy.activiti;

public class Math {
    public int abs(int a) {
        if (a >= 0) return a;
        else return -a;
    }

    public int div(int a, int b) {
        return a / b;
    }

    public float exp(int a, int b) {
        float r = 1;
        for (int i = 0; i < b; i++)
            r *= a;
        return r;
    }
}
