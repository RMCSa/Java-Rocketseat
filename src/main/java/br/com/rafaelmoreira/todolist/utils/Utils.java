package br.com.rafaelmoreira.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

    public static void copyNullPropertyNames(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static String[] getNullPropertyNames(Object source){
        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = src.getPropertyDescriptors(); //Cria uma array cm todas as propiedades do objeto
        
        Set<String> emptyNames = new HashSet<>(); // inserir as info aqui

        for (PropertyDescriptor pd : pds){
            Object srcValue = src.getPropertyValue(pd.getName()); // Verificar s eo campo é nulo
            if (srcValue == null){
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
    
}
