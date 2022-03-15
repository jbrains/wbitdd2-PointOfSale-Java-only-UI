package ca.jbrains.pos;

public interface Controller<Request> {
    String handleRequest(Request request);
}
