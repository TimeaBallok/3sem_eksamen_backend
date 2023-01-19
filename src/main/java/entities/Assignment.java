package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "assignment")
@NamedQuery(name = "Assignment.deleteAllRows", query = "DELETE from Assignment")
public class Assignment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String familyName;

    private String contactInfo;

    @ManyToOne
    @JoinColumn(name = "dinner_event_id")
    private DinnerEvent dinnerEvent;

    @ManyToMany(mappedBy = "assignments")
    private List<Member> members = new ArrayList<>();

    public Assignment()
    {
    }

    public Assignment(String familyName, String contactInfo)
    {
        this.familyName = familyName;
        this.contactInfo = contactInfo;
    }

    public List<Member> getMembers()
    {
        return members;
    }

    public void setMembers(List<Member> members)
    {
        this.members = members;
    }

    public DinnerEvent getDinnerEvent()
    {
        return dinnerEvent;
    }

    public void setDinnerEvent(DinnerEvent dinnerEvent)
    {
        this.dinnerEvent = dinnerEvent;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFamilyName()
    {
        return familyName;
    }

    public void setFamilyName(String familyName)
    {
        this.familyName = familyName;
    }

    public String getContactInfo()
    {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo)
    {
        this.contactInfo = contactInfo;
    }

    @Override
    public String toString()
    {
        return "Assignment{" +
                "id=" + id +
                ", familyName='" + familyName + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                ", dinnerEvent=" + dinnerEvent +
                ", members=" + members +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}