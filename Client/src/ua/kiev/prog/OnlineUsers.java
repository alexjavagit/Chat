package ua.kiev.prog;

import java.util.ArrayList;
import java.util.List;

public class OnlineUsers {
    private  List<String> list = new ArrayList<>();

    public OnlineUsers(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }
}

