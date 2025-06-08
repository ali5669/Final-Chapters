package org.ali5669.commentservice.utils;

public class UserContext {
    private static final ThreadLocal<Integer> tl = new ThreadLocal<>();

    public static void setUserId(Integer userId)
    {
        tl.set(userId);
    }

    public static Integer getUserId()
    {
        return tl.get();
    }

    public static void clear()
    {
        tl.remove();
    }
}
