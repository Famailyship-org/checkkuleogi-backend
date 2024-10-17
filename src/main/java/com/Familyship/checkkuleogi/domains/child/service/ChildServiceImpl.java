package com.Familyship.checkkuleogi.domains.child.service;

import com.Familyship.checkkuleogi.domains.child.domain.Child;
import com.Familyship.checkkuleogi.domains.child.domain.ChildMBTI;
import com.Familyship.checkkuleogi.domains.child.dto.*;
import com.Familyship.checkkuleogi.domains.child.infra.ChildMBTIRepository;
import com.Familyship.checkkuleogi.domains.child.infra.ChildRepository;
import com.Familyship.checkkuleogi.domains.like.Infra.LikeRepository;
import com.Familyship.checkkuleogi.domains.like.domain.BookLike;
import com.Familyship.checkkuleogi.global.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ChildServiceImpl implements ChildService {
    private final ChildMBTIRepository childMBTIRepository;
    private final ChildRepository childRepository;
    private final LikeRepository likeRepository;

    @Override
    public CreateChildResponseDTO createMBTI(CreateChildRequestDTO createChildRequestDTO) {

        // UserId가 있는지 먼저 확인 -> 추후에 토큰 처리하기 때문에 빼버림
        // DTO를 하나씩 빼서 MBTI 성향 만들기
        Child child = childRepository.findByAndName(createChildRequestDTO.getChildName())
                .orElseThrow(() -> new NotFoundException("child not found"));

        // 문항마다 yes, no가 있고 해당 yes이면 E - I
        int[] arr = Arrays.stream(createChildRequestDTO.getSurveys()).toArray();
        int[] mbtiPercent = new int[4];
        String mbtiResult = "";

        // MBTI 설정 로직
        for (int i = 0; i < createChildRequestDTO.getSurveys().length; i++) {
            if (i == 0) { // 1번 문항 - E & I
                if (arr[i] == 1) { // yes - E 성향
                    mbtiPercent[i] = 50;
                    mbtiResult += "E";
                } else if (arr[i] == 2) { // no - I 성향
                    mbtiPercent[i] = -50;
                    mbtiResult += "I";
                }
            } else if (i == 1) { // 2번 문항 - S & N
                if (arr[i] == 1) { // yes - S 성향
                    mbtiPercent[i] = 50;
                    mbtiResult += "S";
                } else if (arr[i] == 2) { // no - N 성향
                    mbtiPercent[i] = -50;
                    mbtiResult += "N";
                }
            } else if (i == 2) { // 3번 문항 - T & F
                if (arr[i] == 1) { // yes - T 성향
                    mbtiPercent[i] = 50;
                    mbtiResult += "T";
                } else if (arr[i] == 2) { // no - F성향
                    mbtiPercent[i] = -50;
                    mbtiResult += "F";
                }
            } else if (i == 3) { // 4번 문항 - J & P
                if (arr[i] == 1) { // yes - J 성향
                    mbtiPercent[i] = 50;
                    mbtiResult += "J";
                } else if (arr[i] == 2) { // no - P 성향
                    mbtiPercent[i] = -50;
                    mbtiResult += "P";
                }
            }
        }
        // MBTI를 먼저 저장 해야함
        ChildMBTI childMBTI = ChildMBTI.builder().
                mbtiE(mbtiPercent[0]).
                mbtiS(mbtiPercent[1]).
                mbtiT(mbtiPercent[2]).
                mbtiJ(mbtiPercent[3]).build();
        childMBTIRepository.save(childMBTI);

        // MBTI 수치를 정해서 child 엔티티에 mbti를 update해야 함
        // Child 객체를 Builder를 통해 복사하고 mbti 값만 변경
        child.updateMBTI(mbtiResult);

        return CreateChildResponseDTO.builder().
                name(child.getName()).
                age(child.getAge()).
                mbti(child.getMbti()).
                parentName(child.getParent().getName()).
                gender(child.getGender()).build();
    }

    @Override
    public ReadChildResponseDTO readMBTI(ReadChildRequestDTO readChildRequestDTO) {
        Child child = childRepository.findByAndName(readChildRequestDTO.getChildName())
                .orElseThrow(() -> new NotFoundException("아이가 등록되어 있지 않습니다"));

        return ReadChildResponseDTO.builder()
                .mbti(child.getMbti()).build();
    }

    @Override
    public void deleteMBTI(DeleteChildMBTIRequestDTO deleteChildMBTIRequestDTO) {
        Child child = childRepository.findByAndName(deleteChildMBTIRequestDTO.getChildName())
                .orElseThrow(() -> new NotFoundException("아이가 등록되어 있지 않습니다"));

        // 논리적 삭제 -> 좋아요 테이블에 childIdx를 기준으로 좋아요가 되어 있는 책을 모두 찾아서 idDeleted 컬럼을 N -> Y로 바꿔준다.
        List<BookLike> bookList = likeRepository.findByChildIdxAndIsDeleted(child.getIdx(), false)
                .orElseThrow(() -> new NotFoundException("좋아요를 한 책이 없습니다"));
        bookList.stream().forEach(books -> books.updateIsDeleted(true));
    }
}
