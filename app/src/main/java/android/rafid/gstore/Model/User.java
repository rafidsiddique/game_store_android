package android.rafid.gstore.Model;

import android.provider.ContactsContract;

/**
 * Created by Alucard on 2017-11-26.
 */

public class User {
    private String Name;
    private String Password;
    private String Phone;
    private String Email;

    public User(){

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public User(String name, String password, String email) {
        Name = name;
        Password = password;
        Email = email;


    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
