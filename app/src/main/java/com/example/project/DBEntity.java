package com.example.project;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBEntity {

    /*DBEntity에서 계속 저장할 환자 리스트*/
    private static ArrayList<Patient> patientList;

    /*DB 연동시 필요한 필드들*/
    private DB task;
    private String result = "success";//일단 임시 success
    private String sendmsg;

    /*AND메소드에서 임시로 쓸 필드들*/
    private int size;


    //patientList 값 접-----------------------------------------------------------------------------------------------

    public DBEntity() {
        patientList = new ArrayList<>();
    }

    public void setPatientList(ArrayList<Patient> patientList) {
        this.patientList = patientList;
    }

    public static ArrayList<Patient> getPatientList() {
        return patientList;
    }


    //Android 프로젝트에 저장되어 있는 patientlist 수정 메소드----------------------------------------------------------------------------------------------------
    // DB테이블 수정 메소드들의 반환값이 1로 선행되어야 함

    /*관리자의 확진자 추가 페이지에서 확진자 정보를 추가하는 메소-동선 제외 */
    public void AND_insert_patient(Patient patient) {
        patientList.add(patient);
    }

    /*관리자의 확진자 추가 페이지에서 확진자 동선 정보 하나를 추가하는 메소드-무조건 해당 환자에 한 정보가 테이블에 저장되어 있어야 함. */
    public int AND_insert_pmoving(Patient patient, VisitPlace visitplace) {
        size = patientList.size();
        for (int i = 0; i < size; i++) {
            Patient getpatient = patientList.get(i);
            //조건문 바꿔야 함! patient 테이블의 프라이머리 키는 지역번호랑 환자번호! 즉 patient와 patientList.get(i)의 정보가 같을 두객체가 같을 때--patient는 smalllocalnum,bigloculnum,patientnum 같으면 같은 객체!
            if (true) {
                //해당 환자에 visitplace 추가
                return 1;// 동선 삽입 성공
            }
        }
        return 0;//동선 삽입 실패: 해당 환자 정보가 없음
    }

    /*관리자의 확진자 수정 페이지에서 확진자 동선 정보 하나를 삭제하는 메소드 */
    public int AND_delete_pmoving(Patient patient, VisitPlace visitplace) {
        size = patientList.size();
        for (int i = 0; i < size; i++) {
            //1. 환자리스트를 돌며 동일한 환자 찾기.
            if (true) {
                //DB테이블에서의 primary 키값들의 값이 같을 때 동일 환자.
                for (int x = 0; x < patientList.get(i).getVisitPlaceList().size(); x++) {
                    //칮으면 해당 환자의 visitplacelist를 돌며 매개변수로 온 visitplace 찾고 삭제하기
                    return 1;//정상 삭제
                }
                return 0;//환자 정보는 있는데 visitplace 정보가 존재 x
            }
        }
        return -1;//환자 정보도 없을 경우
    }

    /*관리자의 확진자 추가, 수정 페이지에서 확진자 동선 정보 하나를 삭제하는 메소드-환자 정보를 삭제하면 관련 동선 정보도 싹다 삭제*/
    public void AND_delete_patient(Patient patient) {

    }


    //DB 테이블 수정----------------------------------------------------------------------------------------------------

    /*인트로 때, 앱 실행시 한 번만 불리는 메소드드*/
    public ArrayList<Patient> patient_info() {
        task = new DB("patients_info");
        // int small=Integer.parseInt(plocnum.substring(0,1));
        // int big=Integer.parseInt(plocnum.substring(2,3));
        //박소영 놈이 제대로 DB연동하기 전까지 만든 임시 확진자 리스트--> 구축 후 삭제 예정

        Place dumP1 = new Place("파주", 1.1, 1.2);
        Place dumP2 = new Place("서울", 2.1, 2.2);

        //3. 1,2로 확진자 방문장소 더미객체 생성
        VisitPlace dumVP1 = new VisitPlace(dumP1, "2020-12-23");
        VisitPlace dumVP2 = new VisitPlace(dumP2, "2020-9-3");
        VisitPlace dumVP3 = new VisitPlace(dumP1, "2020-9-3");
        //4. 3으로 확진자 방문장소 리스트 더미객체 생
        ArrayList<VisitPlace> dumVSP1 = new ArrayList<VisitPlace>();
        ArrayList<VisitPlace> dumVSP2 = new ArrayList<VisitPlace>();
        dumVSP1.add(dumVP1);
        dumVSP1.add(dumVP2);
        dumVSP2.add(dumVP2);
        dumVSP2.add(dumVP3);
        //5. 확진자 두명
        Patient dumPatient1 = new Patient(0, 0, "1", "2020-11-15", dumVSP1);
        Patient dumPatient2 = new Patient(0, 0, "2", "2020-11-16", dumVSP2);
        Patient dumPatient3 = new Patient(0, 0, "3", "2020-11-14", dumVSP1);
        Patient dumPatient4 = new Patient(0, 0, "4", "2020-11-15", dumVSP2);
        Patient dumPatient5 = new Patient(0, 1, "5", "2020-11-15", dumVSP1);
        Patient dumPatient6 = new Patient(8, 12, "6", "2020-11-13", dumVSP2);
        Patient dumPatient7 = new Patient(0, 2, "7", "2020-11-15", dumVSP2);
        patientList.add(dumPatient1);
        patientList.add(dumPatient2);
        patientList.add(dumPatient3);
        patientList.add(dumPatient4);
        patientList.add(dumPatient5);
        patientList.add(dumPatient6);
        patientList.add(dumPatient7);

        return patientList;
    }

    /*환자번호와 지역번호로 중복되는 환자가 있는지 확인하는 메소드*/
    public int check_patient(Patient patient) {
        if (result.equals("success")) return 1;
        else return 0;
    }

    /*관리자의 확진자 추가 페이지에서 확진자 정보를 추가하는 메소-동선 제외 */
    public int insert_patient(Patient patient) {
        if (result.equals("success")) return 1;
        else return 0;
    }

    /*관리자의 확진자 추가 페이지에서 확진자 동선 정보 하나를 추가하는 메소드-무조건 해당 환자에 한 정보가 테이블에 저장되어 있어야 함. */
    public int insert_pmoving(Patient patient, VisitPlace visitplace) {
        if (result.equals("success")) return 1;
        else return 0;
    }

    /*관리자의 확진자 수정 페이지에서 확진자 동선 정보 하나를 삭제하는 메소드 */
    public int delete_pmoving(Patient patient, VisitPlace visitplace) {
        if (result.equals("success")) return 1;
        else return 0;
    }

    /*관리자의 확진자 추가, 수정 페이지에서 확진자 동선 정보 하나를 삭제하는 메소드-환자 정보를 삭제하면 관련 동선 정보도 싹다 삭제*/
    public int delete_patient(Patient patient) {
        if (result.equals("success")) return 1;
        else return 0;
    }

    /*로그인 메소드드*/
    public int login(String managerID, String managerPW) {
        if (result.equals("success")) return 1;
        else return 0;
        /* 밥소놈이랑 접선시 언롹
        try {
            sendmsg = "login";
            task = new DB(sendmsg);
            result = task.execute(managerID, managerPW, sendmsg).get();
            Log.i("Servertest", "서버에서 받은 값" + result);
            if (result.equals("success")) return 1;
            else if (result.equals("failed")) return 0;
            else if (result.equals("noId")) return 2;
            else return -1;
        }catch (Exception e) {
            Log.i("DBtest", ".....ERROR.....!");
            return -2;}*/
    }
}
