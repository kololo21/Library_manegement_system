package model;
import java.io.Serializable;

public class Member implements Serializable{
    private static final long serialVersionUID=1L;
    private String memberID;
    private String name;
    private String email;
    private String phone;

    public Member(String memberID,String name,String email,String phone){
        this.memberID=memberID;
        this.name=name;
        this.email=email;
        this.phone=phone;
    }
    public String getMemberID(){return memberID;};
    public String getName(){return name;};
    public void setName(String name){this.name=name;}
    public String getEmail(){return email;};
    public void setEmail(String email){this.email=email;}
    public String getPhone(){return phone;};
    public void setPhone(String phone){this.phone=phone;}


}