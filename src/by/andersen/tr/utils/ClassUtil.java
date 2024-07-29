package by.andersen.tr.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface ClassUtil {

        default void print() {
            System.out.println("Fields:");
            try {
                Field idField = getClass().getSuperclass().getDeclaredField("ID");
                idField.setAccessible(true);
                System.out.println("- " + idField.getType().getSimpleName() + " " + idField.getName());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            for (Field field : getClass().getDeclaredFields()) {
                System.out.println("- " + field.getType().getSimpleName() + " " + field.getName());
            }

            System.out.println("\nMethods:");
            for (Method method : getClass().getDeclaredMethods()) {
                System.out.println("- " + method.getReturnType().getSimpleName() + " " + method.getName());
            }

            System.out.println("\nConstructors:");
            for (Constructor<?> constructor : getClass().getDeclaredConstructors()) {
                System.out.print("- ");
                Class<?>[] paramTypes = constructor.getParameterTypes();
                for (int i = 0; i < paramTypes.length; i++) {
                    System.out.print(paramTypes[i].getSimpleName());
                    if (i < paramTypes.length - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
        }

    default void validateFields() {
        Field[] fields = getClass().getDeclaredFields();

        Field idField;
        try {
            idField = IDUtil.class.getDeclaredField("ID");
            idField.setAccessible(true);
            long id = (long) idField.get(this);

            Field[] updatedFields = new Field[fields.length + 1];
            System.arraycopy(fields, 0, updatedFields, 0, fields.length);
            updatedFields[fields.length] = idField;
            fields = updatedFields;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error accessing ID field", e);
        }

        for (Field field : fields) {
            NullableWarning annotation = field.getAnnotation(NullableWarning.class);
            if (annotation != null) {
                field.setAccessible(true);
                try {
                    Object value = field.get(this);
                    if (value == null) {
                        String message = String.format(annotation.message(), field.getName(), getClass().getName());
                        System.out.println(message);
                    }
                    else if (field.getType().isPrimitive() && isFieldUninitialized(field, this)) {
                        String message = String.format(annotation.message(), field.getName(), getClass().getName());
                        System.out.println(message);
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Field access error: " + field.getName(), e);
                }
            }
        }
    }

    private boolean isFieldUninitialized(Field field, Object object) {
        try {
            switch (field.getType().getName()) {
                case "int":
                    return field.getInt(object) == 0;
                case "long":
                    return field.getLong(object) == 0L;
                case "short":
                    return field.getShort(object) == 0;
                case "byte":
                    return field.getByte(object) == 0;
                case "float":
                    return field.getFloat(object) == 0.0f;
                case "double":
                    return field.getDouble(object) == 0.0;
                case "boolean":
                    return !field.getBoolean(object);
                case "char":
                    return field.getChar(object) == '\u0000';
                default:
                    return false;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error accessing field: " + field.getName(), e);
        }
    }
}
