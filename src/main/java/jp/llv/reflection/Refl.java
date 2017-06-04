/*
 * The MIT License
 *
 * Copyright 2016 toyblocks.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jp.llv.reflection;

import java.lang.reflect.*;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 * @author Toyblocks
 */
public class Refl {

    public static RClass<?> getRClass(String name) throws ClassNotFoundException {
        return new RClass<>(Class.forName(name));
    }

    public static <T> RClass<T> wrap(Class<T> value) {
        return new RClass<>(value);
    }

    public static <T> RObject<T> wrap(T value) {
        return new RObject<>(value);
    }

    private static boolean isInstance(Class<?> clazz, Class<?> objectClazz) {
        if (clazz == byte.class) {
            return Byte.class == objectClazz;
        } else if (clazz == short.class) {
            return Short.class == objectClazz;
        } else if (clazz == int.class) {
            return Integer.class == objectClazz;
        } else if (clazz == long.class) {
            return Long.class == objectClazz;
        } else if (clazz == float.class) {
            return Float.class == objectClazz;
        } else if (clazz == double.class) {
            return Double.class == objectClazz;
        } else if (clazz == boolean.class) {
            return Boolean.class == objectClazz;
        } else if (clazz == char.class) {
            return Character.class == objectClazz;
        } else {
            return clazz.isAssignableFrom(objectClazz);
        }
    }

    private static boolean match(String name, Executable exe, Class... argTypes) {
        if (exe.getParameterCount() != argTypes.length) {
            return false;//TODO VAR ARGS
        }

        if (name != null && !exe.getName().equals(name)) {
            return false;
        }

        // check param types
        for (int i = 0; i < exe.getParameterCount(); i++) {
            if (argTypes[i] != null && !isInstance(exe.getParameterTypes()[i], argTypes[i])) {
                return false;
            }
        }
        return true;
    }

    private static <E extends Executable> E getMatchingExecutable(String name, E[] candidate, Class... argTypes) {
        for (E e : candidate) {
            if (match(name, e, (Class[]) argTypes)) {
                return e;
            }
        }
        return null;
    }

    private static Method findMethod(Class<?> clazz, String name, Class... argTypes) {
        Class<?> c = clazz;
        do {
            Method m = getMatchingExecutable(name, c.getDeclaredMethods(), (Class[]) argTypes);
            if (m != null) {
                m.setAccessible(true);
                return m;
            }
        } while ((c = c.getSuperclass()) != null);
        return null;
    }

    private static Class[] toTypes(Object... objects) {
        Class[] types = new Class[objects.length];
        for (int i = 0; i < objects.length; i++) {
            types[i] = objects[i].getClass();
        }
        return types;
    }

    private static Field findField(Class<?> clazz, String name) {
        Class<?> c = clazz;
        do {
            try {
                Field f = c.getDeclaredField(name);
                f.setAccessible(true);
                return f;
            } catch (NoSuchFieldException | SecurityException ex) {
            }
        } while ((c = c.getSuperclass()) != null);
        return null;
    }

    public static final class RObject<T> {

        private final T value;

        private RObject(T value) {
            Objects.nonNull(value);
            this.value = value;
        }

        /**
         * Get an instance method
         * @param method name of the method
         * @param argTypes argument types
         * @return RInstanceMethod instance bound to this object
         * @throws NoSuchMethodException when the method does not exist.
         */
        public RInstanceMethod<T> getMethod(String method, Class... argTypes) throws NoSuchMethodException {
            Method m = findMethod(this.value.getClass(), method, argTypes);
            if (m == null) {
                throw new NoSuchMethodException();
            }
            return new RInstanceMethod<>(this.value, m);
        }

        public RObject<?> invoke(String method, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
            RObject.unwrap(args);
            return this.getMethod(method, toTypes(args)).invoke(args);
        }

        private Field getField(String name) throws NoSuchFieldException {
            Field f = findField(this.value.getClass(), name);
            if (f == null) {
                throw new NoSuchFieldException();
            }
            return f;
        }

        public RObject<?> get(String name) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
            Object ret = getField(name).get(this.value);
            return ret != null ? new RObject<>(ret) : null;
        }

        public RObject<T> set(String name, Object object) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException {
            Object o = object;
            if (o instanceof RObject) {
                o = ((RObject) o).unwrap();
            }
            getField(name).set(this.value, o);
            return this;
        }

        public <E> RObject<E> map(Function<T, E> func) {
            return new RObject<>(func.apply(value));
        }

        public RClass<?> getRClass() {
            return new RClass<>(this.value.getClass());
        }

        public boolean isNull() {
            return this.value == null;
        }

        public <E> E unwrapAs(Class<E> clazz) {
            return clazz.cast(this.value);
        }

        public boolean unwrapAsBoolean() {
            return (Boolean) this.value;
        }

        public byte unwrapAsByte() {
            return (Byte) this.value;
        }

        public short unwrapAsShort() {
            return (Short) this.value;
        }

        public char unwrapAsChar() {
            return (Character) this.value;
        }

        public int unwrapAsInt() {
            return (Integer) this.value;
        }

        public long unwrapAsLong() {
            return (Long) this.value;
        }

        public float unwrapAsFloat() {
            return (Float) this.value;
        }

        public double unwrapAsDouble() {
            return (Double) this.value;
        }

        public T unwrap() {
            return this.value;
        }

        @Override
        public int hashCode() {
            return this.value == null ? 0 : this.value.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final RObject<?> other = (RObject<?>) obj;
            return Objects.equals(this.value, other.value);
        }

        @Override
        public String toString() {
            if (this.value == null) {
                return "null";
            }
            return this.value.toString();
        }

        public static void unwrap(Object[] toUnwrap) {
            for (int i = 0; i < toUnwrap.length; i++) {
                if (toUnwrap[i] instanceof RObject) {
                    toUnwrap[i] = ((RObject) toUnwrap[i]).unwrap();
                }
            }
        }

    }

    public static final class RClass<T> {

        private final Class<T> value;

        private RClass(Class<T> value) {
            this.value = value;
        }

        public RObject<T> newInstance(Object... args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            RObject.unwrap(args);
            Constructor<?> t = getMatchingExecutable(null, this.value.getConstructors(), toTypes(args));
            if (t == null) {
                throw new NoSuchMethodException();
            }
            t.setAccessible(true);
            return new RObject<>((T) t.newInstance(args));
        }

        /**
         * Get a static method of the class
         * @param method name of the method
         * @param argTypes argument types
         * @return RClassMethod instance
         * @throws NoSuchMethodException when the method does not exist.
         */
        public RClassMethod getMethod(String method, Class... argTypes) throws NoSuchMethodException {
            Method m = findMethod(this.value, method, argTypes);
            if (m == null) {
                throw new NoSuchMethodException();
            }
            return new RClassMethod(this.value, m);
        }

        public RObject<?> invoke(String method, Object... args) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            RObject.unwrap(args);
            return this.getMethod(method, toTypes(args)).invoke(args);
        }

        private Field getField(String name) throws NoSuchFieldException {
            Field f = findField(this.value, name);
            if (f == null) {
                throw new NoSuchFieldException();
            }
            return f;
        }

        public RObject<?> get(String name) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
            Object ret = getField(name).get(null);
            return ret != null ? new RObject<>(ret) : null;
        }

        public RClass<T> set(String name, Object object) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
            Object o = object;
            if (o instanceof RObject) {
                o = ((RObject) o).unwrap();
            }
            getField(name).set(null, o);
            return this;
        }

        public Class<T> unwrap() {
            return this.value;
        }
    }

    private static abstract class RMethod {
        protected abstract Object getObject();
        protected abstract Method getMethod();

        public final RObject<?> invoke(Object... args) throws InvocationTargetException {
            try {
                RObject.unwrap(args);
                Object ret = getMethod().invoke(getObject(), args);
                return ret != null ? new RObject<>(ret) : null;
            } catch (IllegalAccessException ignored) {
                return null;
            }
        }
    }

    /**
     * Represents a class method to which the class is bound.
     */
    public static final class RClassMethod extends RMethod{
        private final Class<?> clazz;
        private final Method method;

        private RClassMethod(Class<?> clazz, Method method) {
            this.clazz = clazz;
            this.method = method;

            this.method.setAccessible(true);
        }

        @Override
        protected Object getObject() {
            return clazz;
        }

        @Override
        protected Method getMethod() {
            return method;
        }
    }

    /**
     * Represents an instance method to which an instance is bound.
     * @param <T> type of the instance
     */
    public static final class RInstanceMethod<T> extends RMethod {
        private final T instance;
        private final Method method;

        private RInstanceMethod(T instance, Method method) {
            this.instance = instance;
            this.method = method;
        }

        @Override
        protected Object getObject() {
            return instance;
        }

        @Override
        protected Method getMethod() {
            return method;
        }
    }
}