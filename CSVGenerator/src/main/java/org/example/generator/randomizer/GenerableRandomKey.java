package org.example.generator.randomizer;

public interface GenerableRandomKey<T> extends Generable<T> {
    String getExistRandomKeyId();
}
