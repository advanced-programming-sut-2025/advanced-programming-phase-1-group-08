package com.Graphic.model;

import javassist.tools.reflect.Reflection;
import com.Graphic.model.Enum.ItemType.Quantity;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ItemRegistry {
    public Map<String, Items> nameToItemMap = new HashMap<>();

    public void scanItems(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends Items>> itemClasses = reflections.getSubTypesOf(Items.class);

        for (Class<? extends Items> itemClass : itemClasses) {
            //System.out.println(itemClass.getSimpleName());
            try {
                Constructor<?>[] constructors = itemClass.getConstructors();
                boolean handled = false;

                for (Constructor<?> constructor : constructors) {
                    Class<?>[] params = constructor.getParameterTypes();

                    // 🟢 حالت 1: type در کانستراکتور
                    if (params.length == 1 && params[0].isEnum()) {
                        Class<?> enumType = params[0];
                        Object[] enumConstants = enumType.getEnumConstants();

                        for (Object enumValue : enumConstants) {
                            Items instance = (Items) constructor.newInstance(enumValue);
                            nameToItemMap.put(instance.getName(), instance);
                        }
                        handled = true;
                        break;
                    }
                    if (params.length == 2 && params[0].isEnum() && params[1] == Quantity.class) {
                        Class<?> enumType = params[0];
                        Object[] enumConstants = enumType.getEnumConstants();

                        for (Object enumValue : enumConstants) {
                            for (Quantity quantityVal : Quantity.values()) {
                                Items instance = (Items) constructor.newInstance(enumValue, quantityVal);
                                nameToItemMap.put(instance.getName(), instance);
                            }
                        }
                        handled = true;
                        break;
                    }

                }

                if (!handled) {
                    // 🔵 یا ⚪️ حالت 2 و 3: سازنده بدون پارامتر
                    Constructor<? extends Items> defaultConstructor = null;
                    try {
                        defaultConstructor = itemClass.getDeclaredConstructor();
                    } catch (NoSuchMethodException e) {
                        //System.err.println("⛔️ کلاس بدون سازنده پیش‌فرض: " + itemClass.getSimpleName());
                        continue;
                    }

                    defaultConstructor.setAccessible(true);

                    Field enumField = findEnumField(itemClass);

                    if (enumField != null) {

                        // 🔵 حالت 2: فیلد enum داره ولی در کانستراکتور نیست
                        Class<?> enumType = enumField.getType();
                        Object[] enumValues = enumType.getEnumConstants();

                        for (Object enumVal : enumValues) {
                            Items instance = defaultConstructor.newInstance();

                            enumField.setAccessible(true);
                            enumField.set(instance, enumVal);

                            nameToItemMap.put(instance.getName(), instance);
                        }

                    } else {
                        // ⚪️ حالت 3: اصلاً type نداره
                        Items instance = defaultConstructor.newInstance();
                        nameToItemMap.put(instance.getName(), instance);
                    }
                }
            } catch (Exception e) {
                //System.err.println("❌ خطا در پردازش کلاس " + itemClass.getSimpleName());
                //e.printStackTrace();
            }
        }
    }

    private Field findEnumField(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType().isEnum()) {
                return field;
            }
        }
        return null;
    }
}
