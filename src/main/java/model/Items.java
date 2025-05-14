package model;

import model.MapThings.GameObject;

import java.lang.reflect.Field;
import java.util.Objects;

public abstract class Items extends GameObject {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Items)) return false;

        Items other = (Items) obj;

        Object thisType = getTypeValue(this);
        Object otherType = getTypeValue(other);

        if (thisType != null && otherType != null) {
            return thisType.equals(otherType);
        }

        if (thisType == null && otherType == null) {
            return this.getClass().getName().equals(other.getClass().getName());
        }

        return false;
    }

    @Override
    public int hashCode() {
        Object typeValue = getTypeValue(this);
        if (typeValue != null) {
            return Objects.hash(typeValue);
        } else {
            // اگر type نبود، از کلاس استفاده کن چون مقایسه بر اساس کلاس مشترکه
            return Objects.hash(getClass());
        }
    }

    private Object getTypeValue(Object obj) {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField("type");
                field.setAccessible(true);
                return field.get(obj);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (IllegalAccessException e) {
                return null;
            }
        }
        return null;
    }

    public abstract String getName();

}
