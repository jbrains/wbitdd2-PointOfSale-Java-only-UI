package ca.jbrains.pos;

public interface Controller<Payload> {
    String handleRequest(Payload payload);
}
