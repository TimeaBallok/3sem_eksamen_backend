package dtos;

import entities.Assignment;
import entities.Member;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

public class MemberDTO
{
    private Integer id;
    private String address;
    private String phone;
    private String email;
    private Integer birthYear;
    private Integer account;
    private List<Assignment> assignments = new ArrayList<>();

    public MemberDTO(String address, String phone, String email, Integer birthYear, Integer account)
    {
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.birthYear = birthYear;
        this.account = account;
    }

    public MemberDTO(Member member)
    {
        this.id = member.getId();
        this.address = member.getAddress();
        this.phone = member.getPhone();
        this.email = member.getEmail();
        this.birthYear = member.getBirthYear();
        this.account = member.getAccount();
    }

    public Integer getId()
    {
        return id;
    }

    public String getAddress()
    {
        return address;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getEmail()
    {
        return email;
    }

    public Integer getBirthYear()
    {
        return birthYear;
    }

    public Integer getAccount()
    {
        return account;
    }

    public List<Assignment> getAssignments()
    {
        return assignments;
    }
}
