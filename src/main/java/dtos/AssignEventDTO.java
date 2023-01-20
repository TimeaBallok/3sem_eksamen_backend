package dtos;

public class AssignEventDTO
{
    private Integer dinnerId;
    private String familyName;
    private String contactInfo;

    public AssignEventDTO(Integer dinnerId, String familyName, String contactInfo)
    {
        this.dinnerId = dinnerId;
        this.familyName = familyName;
        this.contactInfo = contactInfo;
    }

    public Integer getDinnerId()
    {
        return dinnerId;
    }

    public String getFamilyName()
    {
        return familyName;
    }

    public String getContactInfo()
    {
        return contactInfo;
    }
}
