package com.example.demo.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class Mapper {

    private static ModelMapper modelMapper;
    static {
        modelMapper = new ModelMapper();
        modelMapper
                .getConfiguration()

                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(false)

                .setMatchingStrategy(MatchingStrategies.STANDARD);
    }
    public static <S, T> T map(S source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }
    public static <S, T> Set<T> mapAll(Collection<? extends S>
                                                sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(e -> map(e, targetClass))
                .collect(Collectors.toSet());
    }
}
