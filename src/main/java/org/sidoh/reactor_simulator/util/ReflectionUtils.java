package org.sidoh.reactor_simulator.util;

import java.lang.reflect.Method;

public final class ReflectionUtils {
  private ReflectionUtils() { }

  public static Method safeGetMethod(Class<?> klass, String methodName) {
    try {
      return klass.getMethod(methodName);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }
}
