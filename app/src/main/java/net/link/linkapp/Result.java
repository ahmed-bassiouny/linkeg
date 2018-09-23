package net.link.linkapp;

public interface Result {
    void success(User user);
    void failed(String msg);
}
