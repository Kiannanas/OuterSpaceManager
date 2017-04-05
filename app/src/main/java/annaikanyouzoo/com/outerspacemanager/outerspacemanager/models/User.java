package annaikanyouzoo.com.outerspacemanager.outerspacemanager.models;

import java.util.UUID;

/**
 * Created by annaikanyouzoo on 06/03/2017.
 */

public class User {

    private java.util.UUID uuid;
    private String username;
    private String email;
    private String password;
    private String token;
    private String expires;
    private double points;
    private double minerals;
    private double mineralsModifier;
    private double gas;
    private double gasModifier;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID id) {
        this.uuid = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public double getPoints() { return points; }

    public void setPoints(double points) { this.points = points; }

    public double getMinerals() { return minerals; }

    public void setMinerals(double minerals) { this.minerals = minerals; }

    public double getMineralsModifier() { return mineralsModifier; }

    public void setMineralsModifier(double mineralsModifier) { this.mineralsModifier = mineralsModifier; }

    public double getGas() { return gas; }

    public void setGas(double gas) { this.gas = gas; }

    public double getGasModifier() { return gasModifier; }

    public void setGasModifier(double gasModifier) { this.gasModifier = gasModifier; }
}
