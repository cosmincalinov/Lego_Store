package legostore.model;

public class Client {
    private final long id;
    private final String firstName, lastName;
    private final String phone;
    private Wishlist wishlist;

    public Client(long id, String firstName, String lastName, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.wishlist = new Wishlist();
    }

    public long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public Wishlist getWishlist() { return wishlist; }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ",first name=" + firstName +
                ",last name=" + lastName +
                ",phone=" + phone +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
