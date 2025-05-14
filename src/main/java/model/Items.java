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

        boolean thisHasType = hasField(this, "type");
        boolean otherHasType = hasField(other, "type");

        boolean thisHasQuantity = hasField(this, "quantity");
        boolean otherHasQuantity = hasField(other, "quantity");

        // ⛔️ اگر یکی type داشت و اون یکی نه → نابرابر
        if (thisHasType != otherHasType) return false;

        // ⛔️ اگر یکی quantity داشت و اون یکی نه → نابرابر
        if (thisHasQuantity != otherHasQuantity) return false;

        // ✅ اگر هر دو type داشتن
        if (thisHasType) {
            Object thisType = getFieldValue(this, "type");
            Object otherType = getFieldValue(other, "type");

            if (!Objects.equals(thisType, otherType)) return false;
        }

        // ✅ اگر هر دو quantity داشتن
        if (thisHasQuantity) {
            Object thisQuantity = getFieldValue(this, "quantity");
            Object otherQuantity = getFieldValue(other, "quantity");

            if (!Objects.equals(thisQuantity, otherQuantity)) return false;
        }

        // ✅ اگر به اینجا رسیدیم یعنی همه فیلدهای لازم برابر بودن یا هیچ‌کدوم نبودن
        if (!thisHasType && !thisHasQuantity) {
            return this.getClass().equals(other.getClass());
        }

        return true;
    }

    @Override
    public int hashCode() {
        boolean hasType = hasField(this, "type");
        boolean hasQuantity = hasField(this, "quantity");

        if (hasType && hasQuantity) {
            return Objects.hash(
                    getFieldValue(this, "type"),
                    getFieldValue(this, "quantity")
            );
        } else if (hasType) {
            return Objects.hash(getFieldValue(this, "type"));
        } else if (hasQuantity) {
            return Objects.hash(getFieldValue(this, "quantity"));
        } else {
            return Objects.hash(getClass());
        }
    }

    private Object getFieldValue(Object obj, String fieldName) {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
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

    private boolean hasField(Object obj, String fieldName) {
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            try {
                clazz.getDeclaredField(fieldName);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return false;
    }
    public abstract String getName();

}
