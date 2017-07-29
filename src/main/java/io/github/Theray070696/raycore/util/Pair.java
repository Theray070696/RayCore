package io.github.Theray070696.raycore.util;

/**
 * Created by Theray070696 on 6/13/2017.
 */
public class Pair<A, B>
{
    private A a;
    private B b;

    public Pair(A a, B b)
    {
        this.a = a;
        this.b = b;
    }

    public A getA()
    {
        return this.a;
    }

    public B getB()
    {
        return this.b;
    }
}
