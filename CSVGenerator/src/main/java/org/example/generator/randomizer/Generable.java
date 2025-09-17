package org.example.generator.randomizer;

public interface Generable<T> {

    T generate(String... args);
}
