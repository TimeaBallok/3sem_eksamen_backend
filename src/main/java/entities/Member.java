package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "member")
@NamedQuery(name = "Member.deleteAllRows", query = "DELETE from Member")
public class Member
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String address;
    private String phone;
    private String email;
    private Integer birthYear;
    private Integer account;

    @ManyToMany
    @JoinTable(name = "member_assignments",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "assignments_id"))
    private List<Assignment> assignments = new ArrayList<>();

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_user_name")
    private User user;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Member()
    {
    }

    public Member(String address, String phone, String email, Integer birthYear, Integer account)
    {
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.birthYear = birthYear;
        this.account = account;
    }

    public List<Assignment> getAssignments()
    {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments)
    {
        this.assignments = assignments;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Integer getBirthYear()
    {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear)
    {
        this.birthYear = birthYear;
    }

    public Integer getAccount()
    {
        return account;
    }

    public void setAccount(Integer account)
    {
        this.account = account;
    }

    @Override
    public String toString()
    {
        return "Member{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", birthYear=" + birthYear +
                ", account=" + account +
                ", assignments=" + assignments +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    public void assignMembers(Assignment assignment)
    {
        this.assignments.add(assignment);
    }

    public void removeAssignment(Assignment assignment)
    {
        this.assignments.remove(assignment);
    }
}