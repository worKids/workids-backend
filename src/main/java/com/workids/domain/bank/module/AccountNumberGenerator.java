package com.workids.domain.bank.module;

/**
 * 계좌번호 생성(난수 활용)
 * 생성 규칙: 나라 고유번호+상품 고유 번호+나라-학생 고유번호+난수
 * 총 14자리 형식: XXXXXX-XX-XXXXXX
 */
public class AccountNumberGenerator {
    public static String createRandomNumber(Integer nationNum, Integer productNum, Integer nationStudentNum){
        // 문자열 변환
        String nationNumString = nationNum.toString();
        String productNumString = productNum.toString();
        String nationStudentNumString = nationStudentNum.toString();

        // 숫자 길이
        int totalNumLength = 14; // 계좌번호 총 길이
        int curNumLength = nationNumString.length() + productNumString.length() + nationStudentNumString.length(); // 생성된 길이
        int generateNumLength = totalNumLength - curNumLength; // 생성 필요한 난수 길이

        // ========== 계좌번호 생성 ==========
        StringBuilder sb = new StringBuilder();
        sb.append(nationNumString);
        sb.append(productNumString);
        sb.append(nationStudentNumString);

        // 필요한만큼 난수 생성
        for (int i=0; i<generateNumLength; i++){
            Integer randomNumber = (int)(Math.random()*10); // 0~9 난수 생성
            sb.append(randomNumber.toString());
        }
        sb.insert(6, "-");
        sb.insert(9, "-");

        String accountNumber = sb.toString();

        return accountNumber;
    }

}
