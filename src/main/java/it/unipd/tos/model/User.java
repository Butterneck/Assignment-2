////////////////////////////////////////////////////////////////////
// [Filippo] [Pinton] [1187361]
////////////////////////////////////////////////////////////////////
package it.unipd.tos.model;

public class User {

    private long id;
    private String name;
    private String surname;
    private int age;
    private String address;

    public User(long id, String name, String surname, int age, String address){
        this.id=id;
        this.name=name;
        this.surname=surname;
        this.age=age;
        this.address=address;
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public int getAge(){
        return age;
    }

    public String getAddress(){
        return address;
    }


}