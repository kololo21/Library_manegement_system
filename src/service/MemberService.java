package service;
//Structure is similar to Bookservice.java

import java.util.*;
import model.Member;
import data.DataManager;

public class MemberService{
    private List<Member>members;
    private DataManager dm;

    public MemberService(DataManager dm){
        this.dm=dm;
        this.members=dm.loadMembers();
    }

    public List<Member> getMembers(){return members;}//get all members for view screen
    public void addMember(Member member){//adding new member
        members.add(member);
        dm.saveMembers(members);
    }
    public void updateMember(Member member){//input: member after update ,overwrite member information
        String memberID = member.getMemberID();
        for(Member target_member:members){
            if(target_member.getMemberID().equals(memberID)){
                int index = members.indexOf(target_member);
                members.set(index,member);
            }
        }
        dm.saveMembers(members);
    }
    public void deleteMember(String memberID){
        //lamda method,to prevent ConcurrentModificationException
        members.removeIf(b -> b.getMemberID().equals(memberID));
        dm.saveMembers(members);
    }
    public Member findByID(String memberID){
        for(Member target_member:members){
            if(target_member.getMemberID().equals(memberID)){
                return target_member;
            }
        }
        return null;
    }
}