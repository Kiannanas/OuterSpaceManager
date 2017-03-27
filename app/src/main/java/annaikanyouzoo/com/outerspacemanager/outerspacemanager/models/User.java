package annaikanyouzoo.com.outerspacemanager.outerspacemanager.models;

/**
 * Created by annaikanyouzoo on 06/03/2017.
 */

public class User {

    private String username;
    private String password;
    private String token;
    private String expires;
    private int points;
    private float minerals;
    private int mineralsModifier;
    private float gas;
    private int gasModifier;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public int getPoints() { return points; }

    public void setPoints(int points) { this.points = points; }

    public float getMinerals() { return minerals; }

    public void setMinerals(float minerals) { this.minerals = minerals; }

    public int getMineralsModifier() { return mineralsModifier; }

    public void setMineralsModifier(int mineralsModifier) { this.mineralsModifier = mineralsModifier; }

    public float getGas() { return gas; }

    public void setGas(float gas) { this.gas = gas; }

    public int getGasModifier() { return gasModifier; }

    public void setGasModifier(int gasModifier) { this.gasModifier = gasModifier; }
}
