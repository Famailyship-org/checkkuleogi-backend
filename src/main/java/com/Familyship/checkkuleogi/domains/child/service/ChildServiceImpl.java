package com.Familyship.checkkuleogi.domains.child.service;

import com.Familyship.checkkuleogi.domains.child.domain.Child;
import com.Familyship.checkkuleogi.domains.child.domain.ChildMBTI;
import com.Familyship.checkkuleogi.domains.child.domain.ChildMBTILog;
import com.Familyship.checkkuleogi.domains.child.dto.*;
import com.Familyship.checkkuleogi.domains.child.domain.repository.ChildMBTILogRepository;
import com.Familyship.checkkuleogi.domains.child.domain.repository.ChildMBTIRepository;
import com.Familyship.checkkuleogi.domains.child.domain.repository.ChildRepository;
import com.Familyship.checkkuleogi.domains.like.domain.repository.BookLikeRepository;
import com.Familyship.checkkuleogi.global.domain.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ChildServiceImpl implements ChildService {
    private final ChildMBTIRepository childMBTIRepository;
    private final ChildRepository childRepository;
    private final BookLikeRepository likeRepository;
    private final ChildMBTILogRepository childMBTILogRepository;

    @Override
    public CreateChildResponseDTO createMBTI(CreateChildRequestDTO createChildRequestDTO) {

        // UserId가 있는지 먼저 확인 -> 추후에 토큰 처리하기 때문에 빼버림
        // DTO를 하나씩 빼서 MBTI 성향 만들기
        Child child = isChildExisted(createChildRequestDTO.getChildName());

        // 문항마다 yes, no가 있고 해당 yes이면 E - I
        int[] temp = Arrays.stream(createChildRequestDTO.getSurveys()).toArray();
        int[] mbtiPercent = new int[createChildRequestDTO.getSurveys().length];
        String mbtiResult = "";
        mbtiResult = calcMBTIResult(mbtiPercent.length, temp, mbtiPercent, mbtiResult);

        // MBTI를 먼저 저장 해야함
        saveMBTI(mbtiPercent, child.getIdx());

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

    private void saveMBTI(int[] mbtiPercent, Long childIdx) {
        ChildMBTI childMBTI = ChildMBTI.builder().
                mbtiE(mbtiPercent[0]).
                mbtiS(mbtiPercent[1]).
                mbtiT(mbtiPercent[2]).
                mbtiJ(mbtiPercent[3]).
                build();
//                childIdx(childIdx).build();
        ChildMBTILog childMBTILog = ChildMBTILog.builder().
                mbtiE(mbtiPercent[0]).
                mbtiS(mbtiPercent[1]).
                mbtiT(mbtiPercent[2]).
                mbtiJ(mbtiPercent[3]).
                childIdx(childIdx).
                isSurveyResult(true).build();
        childMBTIRepository.save(childMBTI);
        childMBTILogRepository.save(childMBTILog);
    }

    private static String calcMBTIResult(int length, int[] arr, int[] mbtiPercent, String mbtiResult) {
        // MBTI 설정 로직
        for (int i = 0; i < length; i++) {
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
        return mbtiResult;
    }

    @Override
    public ReadChildResponseDTO readMBTI(ReadChildRequestDTO readChildRequestDTO) {
        Child child = isChildExisted(readChildRequestDTO.getChildName());

        return ReadChildResponseDTO.builder()
                .mbti(child.getMbti()).build();
    }

    @Override
    public void deleteMBTI(DeleteChildMBTIRequestDTO deleteChildMBTIRequestDTO) {
        Child child = isChildExisted(deleteChildMBTIRequestDTO.getChildName());
        ChildMBTI childMBTI = childMBTIRepository.findByChildIdx(child.getIdx())
                .orElseThrow(() -> new NotFoundException("저장된 MBTI가 없습니다"));

        // 논리적 삭제 -> 좋아요 테이블에 childIdx를 기준으로 좋아요가 되어 있는 책을 모두 찾아서 idDeleted 컬럼을 N -> Y로 바꿔준다.
//        List<BookLike> bookList = likeRepository.findByChildIdxAndIsDeleted(child.getIdx(), false)
//                .orElseThrow(() -> new NotFoundException("좋아요를 한 책이 없습니다"));
//        bookList.forEach(books -> books.updateIsDeleted(true));

        // MBTI 로우 테이블 삭제
        childMBTIRepository.delete(childMBTI);
    }

    private Child isChildExisted(String childName) {
        return childRepository.findByAndName(childName)
                .orElseThrow(() -> new NotFoundException("아이가 등록되어 있지 않습니다"));
    }

    @Transactional
    @Override
    public UpdateChildMBTIResponseDTO updateMBTI(UpdateChildMBTIRequestDTO updateChildMBTIRequestDTO) {
        Child child = isChildExisted(updateChildMBTIRequestDTO.getChildName());
        ChildMBTI childMBTI = childMBTIRepository.findByChildIdx(child.getIdx())
                .orElseThrow(() -> new NotFoundException("저장된 MBTI가 없습니다"));

        // 재검사 대상이 아닐 경우 새로운 검사로 API를 다시 요청하게 만든다.
        if (child.isMBTINull(child.getMbti())) {
            throw new NotFoundException("기존의 검사 결과가 없으므로 재검사 대상이 아닙니다.");
        }

        //기존의 MBTI를 먼저 삭제합니다
        childMBTIRepository.delete(childMBTI);

        int[] arr = Arrays.stream(updateChildMBTIRequestDTO.getSurveys()).toArray();
        int[] mbtiPercent = new int[updateChildMBTIRequestDTO.getSurveys().length];
        String mbtiResult = "";

        mbtiResult = calcMBTIResult(mbtiPercent.length, arr, mbtiPercent, mbtiResult);
        saveMBTI(mbtiPercent, child.getIdx());
        child.updateMBTI(mbtiResult);
        return UpdateChildMBTIResponseDTO.builder().childName(child.getName()).mbti(mbtiResult).build();
    }
}
