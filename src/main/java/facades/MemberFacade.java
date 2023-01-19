package facades;

import dtos.MemberDTO;
import entities.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

public class MemberFacade
{
    private static EntityManagerFactory emf;
    private static MemberFacade instance;

    private MemberFacade()
    {
    }

    public static MemberFacade getInstance(EntityManagerFactory _emf)
    {
        if (instance == null) {
            emf = _emf;
            instance = new MemberFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public List<MemberDTO> getAllMembers ()
    {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            List<Member> memberList = query.getResultList();
            if (memberList.isEmpty())
                throw new WebApplicationException("No members was found");
            List<MemberDTO> memberDTOList = new ArrayList<>();
            memberList.forEach(member -> memberDTOList.add(new MemberDTO(member)));
            return memberDTOList;
        } finally {
            em.close();
        }
    }

    public MemberDTO getMemberById (Integer memberId)
    {
        EntityManager em = getEntityManager();
        try {
            Member member = em.find(Member.class, memberId);
            if (member == null)
                throw new WebApplicationException("Member with id: " + memberId + " doesn't exist");
            return new MemberDTO(member);
        } finally {
            em.close();
        }
    }

}
