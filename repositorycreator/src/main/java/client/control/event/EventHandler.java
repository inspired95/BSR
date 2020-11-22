package client.control.event;


public interface EventHandler<T> {
    void handle( T event);
}
