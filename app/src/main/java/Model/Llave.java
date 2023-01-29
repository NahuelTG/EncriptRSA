package Model;

public class Llave {
    private String id;
    private String KeyPub;
    private String KeyPriv;
    private String Description;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyPub() {
        return KeyPub;
    }

    public void setKeyPub(String keyPub) {
        KeyPub = keyPub;
    }

    public String getKeyPriv() {
        return KeyPriv;
    }

    public void setKeyPriv(String keyPriv) {
        KeyPriv = keyPriv;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return Description;
    }
}
