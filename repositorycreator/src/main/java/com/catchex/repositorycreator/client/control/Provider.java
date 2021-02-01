package com.catchex.repositorycreator.client.control;

import java.util.Set;


public interface Provider<T>
{
    Set<T> get();
}
