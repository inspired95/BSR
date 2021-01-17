package com.catchex.repositorycreator.client.control.event;

public interface EventHandler<T>
{
    void handle( T event );
}
