package com.codingheehoon.member.service;

import com.codingheehoon.member.dto.MemberDTO;
import com.codingheehoon.member.entity.MemberEntity;
import com.codingheehoon.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        // 1. dto-> entity ㅂ녀환
        // 2. repository의 save 메서드 호출
        MemberEntity memberEntity= MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
        // repository의 save 메서드 호출(조건, entity 객체를 넘겨줘야 함.)

    }

    public MemberDTO login(MemberDTO memberDTO) { // memberDTO 는 회원이 입력한 비밀번호
        /*
        * 1. 회원이 입력한 이메일로 DB에서 조회를 함.
        * 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단.
        * */
        Optional<MemberEntity> byMemberEmail=memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(byMemberEmail.isPresent()){
            // 조회 겨로가가 있다(해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity=byMemberEmail.get();
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                // 비밀번호가 일치할 때
                // entity -> dto 변환 후 리턴
                MemberDTO dto=MemberDTO.toMemberDTO(memberEntity);
                return dto;
            }
            else{
                // 비밀번호가 일치하지 않는 경우(로그인 실패
                return null;
            }
        }
        else{
            // 조회 결과가 없다.(해당 이메일을 가진 회원이 없다.)
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList=new ArrayList<>();
        for(MemberEntity memberEntity:memberEntityList){
            //memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()){

            //MemberEntity memberEntity=optionalMemberEntity.get();
            //MemberDTO memberDTO=MemberDTO.toMemberDTO(memberEntity);
            //return memberDTO;

            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else{
            return null;
        }
    }
}
