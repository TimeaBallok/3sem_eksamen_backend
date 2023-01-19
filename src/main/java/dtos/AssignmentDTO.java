package dtos;

import entities.Assignment;
import entities.DinnerEvent;
import entities.Member;

import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssignmentDTO
{

    private Integer id;
    private String familyName;
    private Date assignedDate;
    private String contactInfo;

    private DinnerEvent dinnerEvent;

    private List<Member> members = new ArrayList<>();

    public AssignmentDTO(String familyName, Date assignedDate, String contactInfo)
    {
        this.familyName = familyName;
        this.assignedDate = assignedDate;
        this.contactInfo = contactInfo;
    }

    public AssignmentDTO(Assignment assignment)
    {
        this.id = assignment.getId();
        this.familyName = assignment.getFamilyName();
        this.assignedDate = assignment.getAssignedDate();
        this.contactInfo = assignment.getContactInfo();
    }

    public Integer getId()
    {
        return id;
    }

    public String getFamilyName()
    {
        return familyName;
    }

    public Date getAssignedDate()
    {
        return assignedDate;
    }

    public String getContactInfo()
    {
        return contactInfo;
    }

    public DinnerEvent getDinnerEvent()
    {
        return dinnerEvent;
    }

    public List<Member> getMembers()
    {
        return members;
    }
}
