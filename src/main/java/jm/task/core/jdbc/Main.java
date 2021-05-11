package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Oleg","Borisov", (byte)34);
        userService.saveUser("Tom","Gromov", (byte)40);
        userService.saveUser("Lev","Orlov", (byte)30);
        userService.saveUser("Kirill","Petrov", (byte)23);
        List<User> users= new ArrayList<>(userService.getAllUsers());
        for (User user : users) {
            System.out.println(user.toString());
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

}
