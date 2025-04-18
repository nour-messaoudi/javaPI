package tn.esprit.entities;

public class User {

    //Attr
    private String firstname , lastname, address ;
    private int id,age ;


    //constructor

    public User() {
    }

    public User(String firstname, String lastname,String address,  int age ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.address = address;
    }

    public User(String firstname, String lastname,String address, int age ,int id ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.id = id;
        this.address = address;
    }


    //Getters and Setters

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    //Display


    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", address='" + address + '\'' +
                ", id=" + id +
                ", age=" + age +
                '}';
    }
}
