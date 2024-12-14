package com.example.gridgameproject_2_1_24;

import java.lang.reflect.InvocationTargetException;

public final class Instantiater<T>{
    public Instantiater(Class<T> classToInstantiate, Object... extraParameters){
        this.classToInstantiate = classToInstantiate;
        this.parameters = extraParameters;
    }
    public T instantiate(Object... otherParameters){
        byte extraParameterLength = (byte) ((this.parameters != null) ? this.parameters.length : 0);
        byte requiredParametersLength = (byte)((otherParameters != null) ? otherParameters.length : 0);
        Object[] totalParameters = new Object[extraParameterLength + requiredParametersLength];
        byte counter = 0;
        while(counter < requiredParametersLength){totalParameters[counter] = otherParameters[counter]; counter++;}
        byte count = 0;
        while(count < extraParameterLength){totalParameters[counter] = this.parameters[count]; count++; counter++;}
        count = 0;
        Class<?>[] classesParameters = new Class[totalParameters.length];
        while(count < totalParameters.length){classesParameters[count] = Instantiater.getPrimitiveFromWrapper(totalParameters[count].getClass()); count++;}
        try{return this.classToInstantiate.getConstructor(classesParameters).newInstance(totalParameters);}
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException error){error.printStackTrace(); return null;}
    }

    private static Class<?> getPrimitiveFromWrapper(Class<?> wrapper){
        if(wrapper == Long.class){return long.class;}
        else if(wrapper == Integer.class){return int.class;}
        else if (wrapper == Short.class) {return short.class;}
        else if(wrapper == Byte.class){return byte.class;}
        else if (wrapper == Double.class) {return double.class;}
        else if(wrapper == Float.class){return float.class;}
        else if (wrapper == Character.class) {return char.class;}
        else if(wrapper == Boolean.class){return boolean.class;}
        else{return wrapper;}
    }

    private final Class<T> classToInstantiate;
    private final Object[] parameters;
}
