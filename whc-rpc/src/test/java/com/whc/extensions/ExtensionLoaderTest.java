package com.whc.extensions;

import com.whc.remoting.serialize.Serializer;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class ExtensionLoaderTest {
    @Test
    public void loadDirectoryTest() {
        Map<String, Class<?>> stringClassMap = new HashMap<>();
        try {
            Method loadDirectory = ExtensionLoader.class.getDeclaredMethod("loadDirectory", Map.class);
            loadDirectory.setAccessible(true);
            loadDirectory.invoke(ExtensionLoader.getExtensionLoader(Serializer.class), stringClassMap);
            Assert.assertFalse(stringClassMap.isEmpty());

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}