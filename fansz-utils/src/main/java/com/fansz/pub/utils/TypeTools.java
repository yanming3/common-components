package com.fansz.pub.utils;

/**
 * 类型相关工具
 */
public final class TypeTools {

    /**
     * constructor
     */
    private TypeTools() {

    }

    /**
     * 判定此类对象是否与指定的类参数所表示的类或接口是否相同。
     * 
     * @param targetClz Class
     * @param parentClz Class...
     * @return boolean
     */
    public static boolean isSubClassOf(Class targetClz, Class... parentClz) {
        for (Class c : parentClz) {
            if (c.isAssignableFrom(targetClz)) {
                return true;
            }
        }
        return false;
    }
}
