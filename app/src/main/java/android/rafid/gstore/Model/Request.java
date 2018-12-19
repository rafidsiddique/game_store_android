package android.rafid.gstore.Model;


import java.util.List;

public class Request {
    private String phone;
    private String name;
    private String address;
    private String total;
    private String status;
    private List<Order> games;

    public Request() {
    }

    public Request(String phone, String name, String address, String total, List<Order> games) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.total = total;
        this.games = games;
        this.status="0"; //0:placed 1-shipping 2-shipped
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getGames() {
        return games;
    }

    public void setGames(List<Order> games) {
        this.games = games;
    }
}
