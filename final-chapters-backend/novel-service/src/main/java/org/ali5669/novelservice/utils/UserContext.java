package org.ali5669.novelservice.utils;

public class UserContext {
    private static final ThreadLocal<Long> tl = new ThreadLocal<>();

    public static void setUserId(Long userId)
    {
        tl.set(userId);
    }

    public static Long getUserId()
    {
        return tl.get();
    }

    public static void clear()
    {
        tl.remove();
    }
}
