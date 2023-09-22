package com.workids.domain.nation.service;

import com.workids.domain.bank.entity.Bank;
import com.workids.domain.bank.repository.BankRepository;
import com.workids.domain.job.entity.Job;
import com.workids.domain.job.repository.JobRepository;
import com.workids.domain.law.entity.Law;
import com.workids.domain.law.repository.LawRepository;
import com.workids.domain.nation.dto.request.RequestNationJoinDto;
import com.workids.domain.nation.dto.request.RequestNationNumDto;
import com.workids.domain.nation.dto.request.RequestNumDto;
import com.workids.domain.nation.dto.request.RequestNationUpdateDto;
import com.workids.domain.nation.dto.response.*; 
import com.workids.domain.nation.entity.Nation;
import com.workids.domain.nation.entity.NationStudent;
import com.workids.domain.nation.repository.NationRepository;
import com.workids.domain.nation.repository.NationStudentRepository;
import com.workids.domain.user.entity.Teacher;
import com.workids.domain.user.repository.TeacherRepository;
import com.workids.global.exception.ApiException;
import com.workids.global.exception.ExceptionEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NationService {


    private final NationRepository nationRepository;
    private final TeacherRepository teacherRepository;
    private final NationStudentRepository nationStudentRepository;
    private final CitizenService citizenService;
    private final BankRepository bankRepository;

    private final LawRepository lawRepository;
    private final JobRepository jobRepository;


    /**
     * 나라 생성
     */
    @Transactional
    public Long join(RequestNationJoinDto dto, String code) {

        if (nationRepository.findByName(dto.getName()) != null){
            throw new ApiException(ExceptionEnum.NATION_EXIST_EXCEPTION);
        }

        // 날짜 타입 변환
        LocalDateTime[] times = toLocalDateTime(dto.getStartDate(), dto.getEndDate());

        Teacher teacher = teacherRepository.findByTeacherNum(dto.getTeacherNum());

        // 나라 상태 확인
        LocalDateTime currentDateTime = LocalDateTime.now();
        int checkState = nationState(currentDateTime, times[0]);

        Nation nation = nationRepository.save(Nation.of(dto, teacher, times[0], times[1], code, checkState));

        // 나라 가입 시 주거래 통장 생성
        Bank newBank = Bank.baseOf(nation, 0, "주거래 통장", "주거래 통장입니다.", 0, 0, nation.getEndDate());
        // 은행 상품 등록
        bankRepository.save(newBank);

        return nation.getNationNum();

    }

    /**
     * 나라 정보 수정
     */
    @Transactional
    public void update(RequestNationUpdateDto dto) {

        // 나라 찾기
        Nation nation = nationRepository.findByNationNum(dto.getNationNum()).orElse(null);

        // 타입 변환
        LocalDateTime[] times = toLocalDateTime(dto.getStartDate(), dto.getEndDate());

        nation.updateState(dto, times[0], times[1]);

        System.out.println("나라 정보 수정 완료");

    }




    /**
     * teacher과 연결된 나라 전체 조회
     */
    @Transactional
    public List<ResponseTeacherNationListDto> getTeacherList(RequestNumDto dto){

        List<Nation> list = nationRepository.findByTeacher_TeacherNum(dto.getNum());


        List<ResponseTeacherNationListDto> dtoList = new ArrayList<>();
        int totalCitizen = 0;
        for(Nation nation : list){
            // 국민 수
            totalCitizen = citizenService.citizenCount(nation.getNationNum());
            dtoList.add(ResponseTeacherNationListDto.of(nation, totalCitizen));
        }

        return dtoList;
    }

    /**
     * student와 연결된 나라 전체 조회
     */
    @Transactional
    public List<ResponseStudentNationListDto> getStudentNationList(RequestNumDto dto){

        List<NationStudent> list = nationStudentRepository.findByStudent_StudentNum(dto.getNum());

        int totalStudent = list.size();
        System.out.println("size: " + totalStudent);


        List<ResponseStudentNationListDto> dtoList = new ArrayList<>();
        for(NationStudent nationstudent : list){
            dtoList.add(ResponseStudentNationListDto.of(nationstudent, totalStudent));
        }

        return dtoList;
    }



    /**
     * 나라 고유번호, state = 0(진행중) 으로 select
     */
    @Transactional
    public ResponseNationInfoDto getInfo(RequestNumDto dto){
        Long nationNum = dto.getNum();
        Optional<Nation> optNation = nationRepository.findById(nationNum);

        Nation nation;
        ResponseNationInfoDto infoDto = null;
        if(optNation.isPresent()){
            nation = optNation.orElseThrow(NullPointerException::new);
            infoDto = ResponseNationInfoDto.toDto(nation);

        }

        return infoDto;
    }

    /**
     * 나라 고유번호, state = 0(진행중) 으로 select
     */
    @Transactional
    public ResponseNationInfoDto getNationInfo(Long nationNum){
        Optional<Nation> optNation = nationRepository.findById(nationNum);

        Nation nation;
        ResponseNationInfoDto infoDto = null;
        if(optNation.isPresent()){
            nation = optNation.orElseThrow(NullPointerException::new);
            infoDto = ResponseNationInfoDto.toDto(nation);

        }

        return infoDto;
    }

    /**
     * 나라 참여코드 생성
     */
    public String randomCode() {
        Random random = new Random();
        int randomStrLen = 8;
        StringBuffer randomBuf = new StringBuffer();
        for (int i = 0; i < randomStrLen; i++) {
            if (random.nextBoolean()) {
                randomBuf.append((char) ((int) (random.nextInt(26)) + 97));
            } else {
                randomBuf.append(random.nextInt(10));
            }
        }
        String randomStr = randomBuf.toString().toUpperCase();

        return randomStr;
    }
    /**
     * 날짜 데이터타입 변환
     * : String -> LocalDatetime
     */
    public LocalDateTime[] toLocalDateTime(String start, String end){

        String startDate = start+ " 00:00:00.000";
        String endDate = end + " 11:59:59.999";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        LocalDateTime startDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate, formatter);

        LocalDateTime[] times = new LocalDateTime[]{startDateTime, endDateTime};

        return times;

    }

    /**
     * 나라 삭제
     */
    @Transactional
    public void delete(RequestNumDto dto) {

        // 나라 찾기
        Nation nation = nationRepository.findById(dto.getNum()).orElse(null);

        List<NationStudent>  nationStudentsList = nationStudentRepository.findByNation_NationNum(dto.getNum());
        if(!nationStudentsList.isEmpty()){ // 학생 존재하여 나라 삭제 불가
            throw new ApiException(ExceptionEnum.NATION_NOT_DELETE_EXCEPTION);
        }

        // 학생 존재하지 않을 경우 나라 삭제
        nationRepository.deleteById(dto.getNum());
    }

    /**
     * 메인화면
     */
    @Transactional
    public ResponseTeacherMainDto getMainInfo(RequestNumDto dto){


        Long nationNum = dto.getNum();
        Optional<Nation> optNation = nationRepository.findById(nationNum);

        Nation nation;
        ResponseTeacherMainDto mainDto = null;
        if(optNation.isPresent()){
            nation = optNation.orElseThrow(NullPointerException::new);
            int totalCitizen = citizenService.citizenCount(nation.getNationNum());
            mainDto = ResponseTeacherMainDto.toDto(nation, totalCitizen);

        }

        return mainDto;

    }

    /**
     * 메인화면 - 법
     */
    @Transactional
    public List<ResponseNationLawDto> getMainInfoLaw(RequestNumDto dto){

        List<Law> lawList = lawRepository.findByNation_NationNumAndState(dto.getNum(), 0);

        List<ResponseNationLawDto> nationLawDtoList = new ArrayList<>();
        for(Law law : lawList){
            nationLawDtoList.add(ResponseNationLawDto.toDto(law));
        }

        return nationLawDtoList;

    }

    /**
     * 메인화면 - 직업
     */
    @Transactional
    public List<ResponseNationJobDto> getMainInfoJob(RequestNumDto dto){

        List<Job> jobList = jobRepository.findByNation_NationNumAndState(dto.getNum(), 0);

        List<ResponseNationJobDto> nationJobDtoList = new ArrayList<>();
        for(Job job : jobList){
            nationJobDtoList.add(ResponseNationJobDto.toDto(job));
        }

        return nationJobDtoList;

    }

    /**
     * 나라 상태 확인
     */
    public int nationState(LocalDateTime now, LocalDateTime start){
        LocalDate nowDate = now.toLocalDate();
        LocalDate startDate = start.toLocalDate();

        if (nowDate.isBefore(startDate)) { // 나중에 시작하는 경우
            return 0; // 운영대기
        } else{
            return 1; // 운영중
        }
    }

     /**
     * 나라 월 조회
     * @param dto
     * @return
     */
    public ResponseNationMonthDto getMonth(RequestNationNumDto dto) {
        Nation nation = nationRepository.findByNationNum(dto.getNationNum())
                .orElseThrow(() -> new ApiException(ExceptionEnum.NATION_EXIST_EXCEPTION));
        LocalDateTime start = nation.getStartDate();
        LocalDateTime end = nation.getEndDate();

        Set<Integer> monthList = new HashSet<>();
        LocalDateTime current = start;
        while (!current.isAfter(end)) {
            YearMonth yearMonth = YearMonth.from(current);
            int monthNumber = yearMonth.getMonthValue();
            monthList.add(monthNumber);

            current = current.plusMonths(1); // 다음 달로 이동
        }
        System.out.println("monthList = " + monthList);
        return ResponseNationMonthDto.toDto(monthList);
    }
}
