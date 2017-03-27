<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
     <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script type="text/javascript" src="/MiddleWare/js/jquery-1.9.1.min.js"></script>
  </head>
  
  <body>
    This is my test <br>
    <table>
        <tr>
            <td>数据：</td>
            <td><input type="button" onclick="getInfo();" value="获取"></td>
        </tr>
    </table>
  </body>
  <script type="text/javascript">
    function getInfo(){
        alert("begin!");
        $.ajax({
            type:'POST',
            //url:'/message/findNewMsgList',
            //url:'/measure/uploadOsbp',
            //url:'/measure/relationRecord',
            //url:'/focus/addFocus',
            //url:'/MiddleWare/answer/findMemSingleAnswer',
            //url:'/MiddleWare/answer/findMemSingleAnswerSummary',
            //url:'/answer/findSingleAnswerHasSubmit',
            //url:'/answer/findMemSingleQuestionInfo',
            //url:'/MiddleWare/report/updateReportReadStatus',
            
            //url:'/message/findMsgList',
            //url:'/message/findMsgByMsgId',
            
            //url:'/MiddleWare/report/findDocSignatureAndSuggestion',
            //url:'/MiddleWare/report/summaryReport',
            
            //url:'/MiddleWare/answer/uploadResult',
            //url:'/MiddleWare/answer/combAnswer',
            //url:'/MiddleWare/answer/relationAnswer',
            
            
            //url:'/MiddleWare/mem/info',
            //url:'/MiddleWare/mem/edit',
            
            //医生获取会员列表
            //url:'/MiddleWare/doc/getMyMemberList',
            
            //url:'/MiddleWare/measure/checkMemFile',
            
            //url:'/MiddleWare/pc/addPcMiniRecord',
            //url:'/MiddleWare/pc/deletePcMiniRecord',
            //url:'/MiddleWare/pc/updatePcMiniRecord',
            //url:'/MiddleWare/pc/findPcMiniRecordByParam',
            //url:'/MiddleWare/pc/findOmemByUserId',
            
            //url:'/MiddleWare/doc/findMemMeasureRecordList',
            //url:'/MiddleWare/doc/findMemMeasureRecordOne',
            
            //url:'/MiddleWare/doc/docLogin',
            
            //url:'/MiddleWare/measure/getMeasureRecordList',
            
            
            
             //url:'/MiddleWare/pc/docLogin',
             
             //获取会员健康档案
             //url:'/MiddleWare/mem/memFile',
             
             //获取我的健康体检列表分页
             //url:'/MiddleWare/healthExam/findMyHealthExamReportList',
             //查询我的健康体检报告明细
             //url:'/MiddleWare/healthExam/findMyHealthExamDetail',
             //查询我的住院史
             //url:'/MiddleWare/healthExam/findMyHospitalization',
             //查询我的家庭病床史
             //url:'/MiddleWare/healthExam/findMyFamilyBedHistory',
             //查询我的非免疫规划预防接种史
             //url:'/MiddleWare/healthExam/findMyVaccination',
             //查询我的主要用药情况
             //url:'/MiddleWare/healthExam/findMyMedication',
             
             //查询我的糖尿病随访列表
             //url:'/MiddleWare/visit/findMyDiabetesVisitList',
             //查询我的糖尿病随访明细
             //url:'/MiddleWare/visit/findMyDiabetesVisitDetail',
             //查询我的高血压随访列表
             //url:'/MiddleWare/visit/findMyHypertensionVisitList',
             //查询我的高血压随访明细
             //url:'/MiddleWare/visit/findMyHypertensionVisitDetail',
             
             //医生查询我的糖尿病随访列表
             //url:'/MiddleWare/docVisit/findMemDiabetesVisitList',
             //医生查询我的糖尿病随访明细
             //url:'/MiddleWare/docVisit/findMemDiabetesVisitDetail',
             //医生查询我的高血压随访列表
             //url:'/MiddleWare/docVisit/findMemHypertensionVisitList',
             //医生查询我的高血压随访明细
             //url:'/MiddleWare/docVisit/findMemHypertensionVisitDetail',
             //医生查询我的健康档案
             //url:'/MiddleWare/docHealthExam/findMemHealthFile',
             
             //医生获取可进行高血压随访的会员
             //url:'/MiddleWare/docVisit/findCanVisitHyperMem',
             //医生获取可进行糖尿病随访的会员
            //url:'/MiddleWare/docVisit/findCanVisitDiabeteMem',
             
             //获取所有字典数据
             //url:'/MiddleWare/dd/findAllDictionary',
             //查询疾病史字典
             //url:'/MiddleWare/dd/findDiseaseDictionary',
             //获取健康体检字典数据
             //url:'/MiddleWare/dd/findHealthExamDictionary',
             
             //医生新增会员的疾病史
             //url:'/MiddleWare/docHealthExam/addMemDiseases',
             //医生删除新会员的疾病史
             //url:'/MiddleWare/docHealthExam/deleteMemDiseases',
             
             //医生新增或者修改会员的高血压随访记录。
             //url:'/MiddleWare/docVisit/addOrModifyMemHypertensionVisit',
             
             //医生获取会员的头像
             //url:'/MiddleWare/doc/findMemHeadImg',
             
             //新增随访自动获取一些数据
             //url:'/MiddleWare/docVisit/findSomeDatasByMemberId',
             
             
             //获取各种测量类型记录
             //url:'/MiddleWare/measure/getAllMeasureRecord',
             //获取最近一个月内四种类型的测量条数
             //url:'/MiddleWare/measure/findLastestMonthMeasureCount',
             // 根据不同条件查询测量数据
             //url:'/MiddleWare/measure/findAllMeasureRecordByParam',
             //根据测量事件id和type获取测量数据
             //url:'/MiddleWare/measure/findMeasRecordByEventIdAndType',
             
             
             //获取老年人生活能力值
             //url:'/MiddleWare/docHealthExam/findAgedLifeEvaluate',
             //获取中医体质值
             //url:'/MiddleWare/docHealthExam/findTcmValue',
             //获取中医体质值和老年人生活自理能力问卷或者答卷
             //url:'/MiddleWare/docHealthExam/findTcmAndAgedQuestionnaire',
             //新增或者修改体检报告
             //url:'/MiddleWare/docHealthExam/addOrModifyHealthExam',
            //data:'params={"memberId":26013,"session":"ad=bc36ad3e-9d71-413c-b359-2746d58cb75a","param":{"testTime":"20150805103237","sbp":110,"dbp":80,"pulseRate":70,"bluetoothMacAddr":"","deviceCode":"Hand"},"version":"v1.0","loginLog":"20150715143237"}',
            //data:'params={"param":{},"memberId":"26027","version":"v1.0","session":"9a369d73-b30f-4f25-bf4b-f9eb6c5d0362","loginLog":"2014-03-02 12:12:12"}',
            //data:'params={"param":{"beginDate":"2015-06-01","endDate":"2015-06-10","recentTimes":"2"},"memberId":"26012","version":"v1.0","session":"ad=8829e2711ec*c*6b6f665046c*373a370","loginLog":"2014-03-02 12:12:12"}',
            //data:'params={"param":{"measureStart":"20150909000000","measureEnd":"20151009111159","measureType":"ECG","memberId":26027},"memberId":"26027","version":"v1.0","session":"37c7ed6a-f7e2-45ad-8183-226f2bac7ff6","loginLog":"2015-10-09 10:41:59"}',
            //data:'params={"param":{"memberId":26152,"hasAnswerd":"T","page":1,"count":20},"memberId":"26152","version":"v1.0","session":"0901d413-50f8-417b-ba70-2106b3667525","loginLog":"2015-10-13 16:58:54"}',
            //data:'params={"param":{"memberId":26150,"ansNumber":33},"memberId":"26150","version":"v1.0","session":"9b118000-8f97-47d3-9b57-a1daf7720f67","loginLog":"2015-11-09 11:29:31"}',
            //data:'params={"param":{"memberId":26013,"ansNumber":1387,"qustId":263},"memberId":"26013","version":"v1.0","session":"00399c9f-7b41-40f2-9198-a75e1cb390c6","loginLog":"2015-10-13 16:58:54"}',
            //data:'params={"param":{"memberId":26013,"qustId":263},"memberId":"26013","version":"v1.0","session":"16e36542-ecc7-49cb-b659-64376ac0a937","loginLog":"2015-10-13 16:58:54"}',
            //data:'params={"param":{"reportId":136},"memberId":"26196","version":"v1.0","session":"03afa68d-0bac-438f-9ff3-cd535d09041b","loginLog":"2015-10-13 16:58:54"}',
            
            //data:'params={"param":{"memberId":26146,"pageNo":2,"pageSize":2},"memberId":"26146","version":"v1.0","session":"fa380c22-f370-47d5-8478-09db626b7eff","loginLog":"2015-10-13 16:58:54"}',
            //data:'params={"param":{"memberId":26146,"msgId":101},"memberId":"26146","version":"v1.0","session":"fa380c22-f370-47d5-8478-09db626b7eff","loginLog":"2015-10-13 16:58:54"}',
            
            
            //data:'params={"param":{"reportId":12,"doctorId":329},"memberId":"26146","version":"v1.0","session":"cb9830c2-1896-49fd-96d9-589f3bba75b6","loginLog":"2015-10-13 16:58:54"}',
            //data:'params={"param":{"memberId":26146,"timeStart":"20151021143021","timeEnd":"20151117143021","count":10,"page":1},"memberId":"26146","version":"v1.0","session":"db826656-f82d-48de-8437-826878ed71dd","loginLog":"2015-10-13 16:58:54"}',
            
            //data:'params={"param":{"qustTag":"T","uai21List":[{"ansId":4,"ansNumber":793,"answerScore":0.0,"combAnsId":0,"combQustId":0,"problemId":1,"qustId":516,"score":0}]},"memberId":"26176","version":"v2.0","session":"84ef1c2f-1386-4ba0-9539-8823b40e9515","loginLog":"2015-11-20 15:49:11"}',
            //data:'params={"param":{"memberId":26147,"timeStart":"20151029000000","timeEnd":"20151128162312","count":10,"page":1},"memberId":"26147","version":"v2.0","session":"458910f6-5d8c-40bd-8304-d1d2a79a9fa0","loginLog":"2015-11-20 15:49:11"}',
            //data:'params={"param":{"relation":"984,985"},"memberId":"26147","version":"v2.0","session":"458910f6-5d8c-40bd-8304-d1d2a79a9fa0","loginLog":"2015-11-20 15:49:11"}',
            
            
            //data:'params={"param":{"memberId":"984,985"},"memberId":"26147","version":"v2.0","session":"458910f6-5d8c-40bd-8304-d1d2a79a9fa0","loginLog":"2015-11-20 15:49:11"}',
            //data:'params={"param":{"relation":"984,985"},"memberId":"26147","version":"v2.0","session":"458910f6-5d8c-40bd-8304-d1d2a79a9fa0","loginLog":"2015-11-20 15:49:11"}',
            
            //医生获取会员列表
            //data:'params={"param":{"doctorId":439,"diseaseIds":"1","searchParam":"","pageSize":8 },"memberId":"439","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
            
            
          //data:'params={"param":{"memberId":28192},"memberId":"28192","version":"v2.0","session":"46ff9f7f-3c51-476f-9513-e0ba0b2a2127","loginLog":"2015-11-20 15:49:11"}',
          
          //data:'params={"param":{"userId":"26178","memName":"zkhk","gender":"M","tel":"13632567878","idCard":"142326198212212536","bluetoothMacAddr":"123456789","sendDownTime":"20160126102345"},"memberId":284,"version":"v2.0","session":"aa315875-b31c-46a8-af0a-6cb7ad007771","loginLog":"2015-11-20 15:49:11"}',
          //data:'params={"param":{"bluetoothMacAddr":"123456789"},"memberId":"26178","version":"v2.0","session":"aa315875-b31c-46a8-af0a-6cb7ad007771","loginLog":"2015-11-20 15:49:11"}',
          //data:'params={"param":{"userId":"26178","bluetoothMacAddr":"123456789","uploadTime":"20160126162345"},"memberId":"26178","version":"v2.0","session":"aa315875-b31c-46a8-af0a-6cb7ad007771","loginLog":"2015-11-20 15:49:11"}',
          //data:'params={"param":{"memName":"","timeStart":"20160225162845","timeEnd":"","status":0},"memberId":"285","version":"v2.0","session":"004e6d72-fba1-4949-b28d-b33b632cfc26","loginLog":"2015-11-20 15:49:11"}',
          //data:'params={"param":{"userId":"26178"},"memberId":"26178","version":"v2.0","session":"aa315875-b31c-46a8-af0a-6cb7ad007771","loginLog":"2015-11-20 15:49:11"}',
          
          //data:'params={"param":{"paramName":"","doctorId":"416","measureTypeIds":"1,2,3,4"},"memberId":"416","version":"v2.0","session":"8161ad12-112c-42c0-a930-a30edf89d7d5","loginLog":"2015-11-20 15:49:11"}',
          //data:'params={"param":{"eventId":"1890","eventType":"3"},"memberId":"284","version":"v2.0","session":"ead8def6-2a0a-46a6-b7b4-bb1f390d6730","loginLog":"2015-11-20 15:49:11"}',
            
          //data:'params={"param":{"userAccount":"yfdoc1","passWord":"e10adc3949ba59abbe56e057f20f883e"},"version":"v1.0","loginLog":"2015-10-13 16:58:54"}',
          
          //data:'params={"param":{"memberId":26178,"timeStart":"20160103010101","timeEnd":"20160202235959","dataType":5},"memberId":"26178","version":"v2.0","session":"ab4e45c1-3736-4f50-81d1-ad4ad63255e6","loginLog":"2015-11-20 15:49:11"}',
          
          
          //data:'params={"param":{"userAccount":"doctor2125","passWord":"e10adc3949ba59abbe56e057f20f883e"},"version":"v1.0","loginLog":"2015-10-13 16:58:54"}',
          
          
          //获取会员健康档案
          //data:'params={"param":{"memberId":28273},"memberId":"28273","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
          
          
          //获取我的健康体检列表分页
          //data:'params={"param":{"memberId":60806},"memberId":"60806","version":"v2.0","session":"","loginLog":"2015-11-20 15:49:11"}',
          //查询我的健康体检报告明细
          //data:'params={"param":{"hExamID":86},"memberId":"28859","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
          //查询我的住院史
          //data:'params={"param":{"hExamID":44081},"memberId":"26022","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
          //查询我的家庭病床史
          //data:'params={"param":{"hExamID":44081},"memberId":"26022","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
          //查询我的非免疫规划预防接种史
          //data:'params={"param":{"hExamID":44081},"memberId":"26022","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
          //查询我的主要用药情况
          //data:'params={"param":{"hExamID":44081},"memberId":"26022","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
          
          // 查询我的糖尿病随访列表
          //data:'params={"param":{"memberId":60806},"memberId":"60806","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
          //查询我的糖尿病随访明细
          //data:'params={"param":{"logId":44594},"memberId":"60806","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
          //查询我的高血压随访列表
          //data:'params={"param":{"memberId":60806},"memberId":"60806","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
          //查询我的高血压随访明细
          //data:'params={"param":{"logId":41},"memberId":"28665","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
          
          
          //医生查询我的糖尿病随访列表
          //data:'params={"param":{"hasVisited":1;"searchParam":"Android3"},"memberId":"408","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
          //医生查询我的糖尿病随访明细
          //data:'params={"param":{"logId":430},"memberId":"417","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
          //医生查询我的高血压随访列表
          //data:'params={"param":{},"memberId":"446","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
          //医生查询我的高血压随访明细
          //data:'params={"param":{"logId":44558},"memberId":"60806","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
          //医生查询我的健康档案
          //data:'params={"param":{"memberId":45071},"memberId":"446","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
          
          //医生获取可进行高血压随访的会员
          //data:'params={"param":{"doctorId":424,"diseaseId":1,"searchParam":"","pageSize":10 },"memberId":"424","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
          //医生获取可进行糖尿病随访的会员
          //data:'params={"param":{"doctorId":423,"diseaseId":2,"searchParam":"高","pageSize":10 },"memberId":"423","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
          
          //医生新增会员的疾病史
          //data:'params={"param":{"memberId":26027,"lineNum":2,"diseaseId":"1","diseaseName":"高血压","diagTime":"2016-03-06" },"memberId":"285","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
          //医生删除新会员的疾病史
          //data:'params={"param":{"memberId":26996,"diseaseId":1,"lineNum":2},"memberId":"285","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
          
          //获取所有字典数据
          //data:'',
          //查询疾病史字典
          //data:'',
          //获取健康体检字典数据
          //data:'',
          
          //医生新增或者修改会员的随访记录。
          //data:'params={"param":{"createDrID":284,"refCompany":0,"visitDate":"2016-03-22","hypertensionID":206,"name":"test,","memberID":55911,'
                  //+'"hypertensionDetail":{"weight":0,"dailyDrinkNext":0,"drugCompliance":0,"bmi":0,"bmiNext":0,"dailySmoking":0,"heartRate":0,"systolic":0,"hypertensionID":44550,"diastolic":0,"sportDuration":0,"intakeSaltNext":0,"intakeSalt":0,"sportDurationNext":0,"psychologicalRecovery":0,"complianceBehavior":0,"dailySmokingNext":0,"height":0,"drugAdverseReaction":0,"visitWay":0,"weightNext":0,"dailyDrink":0,"visitDateNext":"2016-04-05"},'
                  //+'"hyperDetailMedicines":{"logID":0,"hypertensionID":44550},"visitClass":3,"visitDrName":"13421","createDrName":"doctor"},'
                  //+'"memberId":"284","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
                  
          //医生获取会员的头像
          //data:'params={"param":{"memberId":26996},"memberId":"294","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
          
          //新增随访自动获取一些数据
          //data:'params={"param":{"memberId":45115,"visitType":"H"},"memberId":"446","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
          
          
             //获取各种测量类型记录
             //data:'params={"param":{"memberId":30294},"memberId":"30294","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
             //获取最近一个月内四种类型的测量条数
             //data:'params={"param":{"memberId":28483},"memberId":"28483","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
             // 根据不同条件查询测量数据
             //data:'params={"param":{"memberId":30294,"beginTime":"","endTime":"","pageNow":1,"pageSize":10,"eventType":1},"memberId":"30294","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
             //根据测量事件id和type获取测量数据
             //data:'params={"param":{"memberId":28480,"eventIds":"14387,14386,14249,14245,14244","eventType":4},"memberId":"28480","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
             
             
              //获取老年人生活能力值
             //data:'params={"param":{"memberId":28480,"agedlifeEvaAnsList":[{"problemId":1,"ansId":1},{"problemId":2,"ansId":2}]},"memberId":"28480","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
             //获取中医体质值
             //data:'params={"param":{"memberId":28480,'
            	 //+ '"tcmAnsList":[{"problemId":1,"ansId":1},{"problemId":2,"ansId":2},{"problemId":3,"ansId":3},{"problemId":4,"ansId":2},{"problemId":5,"ansId":5},{"problemId":6,"ansId":2},{"problemId":7,"ansId":1}'
            	                  //+',{"problemId":8,"ansId":2},{"problemId":9,"ansId":3},{"problemId":10,"ansId":2},{"problemId":11,"ansId":3},{"problemId":12,"ansId":2},{"problemId":13,"ansId":1},{"problemId":14,"ansId":2}'
            	                  //+',{"problemId":15,"ansId":2},{"problemId":16,"ansId":3},{"problemId":17,"ansId":5},{"problemId":18,"ansId":4},{"problemId":19,"ansId":2},{"problemId":20,"ansId":3},{"problemId":21,"ansId":1}'
            	                  //+',{"problemId":22,"ansId":1},{"problemId":23,"ansId":4},{"problemId":24,"ansId":4},{"problemId":25,"ansId":1},{"problemId":26,"ansId":2},{"problemId":27,"ansId":3},{"problemId":28,"ansId":1}'
            	                  //+',{"problemId":29,"ansId":3},{"problemId":30,"ansId":2},{"problemId":31,"ansId":3},{"problemId":32,"ansId":4},{"problemId":33,"ansId":5}]},'
            	 //+ '"memberId":"28480","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
            	 
            //获取中医体质值和老年人生活自理能力问卷或者答卷
            //data:'params={"param":{"memberId":45099,"hExamID":0},"memberId":"446","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
            //新增或者修改体检报告
            //data:'params={"param":{"hExamID":44217,"examDate":"2016-06-04",'
            	 //+'"basicInfo":{"memberId":28480,"memName":"张三"},'
            	 //+'"generalSituation":{"bodyTemperature":36,"pulseRate":88},'
            	 //+'"lifeStyle":{"sportFrequency":1,"sportDuration":"2"},'
            	 //+'"organFunction":{"lips":1,"dentition":1},'
            	 //+'"examination":{"skin":"1","skinDesc":"111"},'
            	 //+'"accessoryExamination":{"hemoglobin":66,"leukocyte":77},'
            	 //+'"tcmConstitutionIdentification":{"tCM_PHZ":1,"tCM_PHZ_Guide":1},'
            	 //+'"majorHealthProblems":{"heart":"1@#2@#3"}},'
                 //+ '"tcmAnsList":[{"problemId":1,"ansId":1},{"problemId":2,"ansId":2},{"problemId":3,"ansId":3},{"problemId":4,"ansId":2},{"problemId":5,"ansId":5},{"problemId":6,"ansId":2},{"problemId":7,"ansId":1}'
                                  //+',{"problemId":8,"ansId":2},{"problemId":9,"ansId":3},{"problemId":10,"ansId":2},{"problemId":11,"ansId":3},{"problemId":12,"ansId":2},{"problemId":13,"ansId":1},{"problemId":14,"ansId":2}'
                                  //+',{"problemId":15,"ansId":2},{"problemId":16,"ansId":3},{"problemId":17,"ansId":5},{"problemId":18,"ansId":4},{"problemId":19,"ansId":2},{"problemId":20,"ansId":3},{"problemId":21,"ansId":1}'
                                  //+',{"problemId":22,"ansId":1},{"problemId":23,"ansId":4},{"problemId":24,"ansId":4},{"problemId":25,"ansId":1},{"problemId":26,"ansId":2},{"problemId":27,"ansId":3},{"problemId":28,"ansId":1}'
                                  //+',{"problemId":29,"ansId":3},{"problemId":30,"ansId":2},{"problemId":31,"ansId":3},{"problemId":32,"ansId":4},{"problemId":33,"ansId":5}]},'
                 //+ '"memberId":"396","version":"v2.0","session":"183371c5-a5a8-4bf6-b0fd-b9e96d2f274e","loginLog":"2015-11-20 15:49:11"}',
                 
            
             //获取测量图表文件
             //url:'/MiddleWare/measure/getMeasueChartData',
             //data:'params={"param":{"memberId":26026,"reportId":1525,"dataType":1},"memberId":"446","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
             
             //获取上传血压数据
             //url:'/MiddleWare/measure/uploadOsbp',
             //data:'params={"param":{"memberId":78679,"testTime":"20170208114000","timePeriod":"1","sbp":160,"dbp":100,"pulseRate":66,"deviceCode":"Hand"},"memberId":"78679","version":"v2.0","session":"52b4e9e2-c69f-40d4-b55f-902a6a13df9f","loginLog":"2015-11-20 15:49:11"}',
             
             //pc获取mini记录
             //url:'/MiddleWare/pc/findPcMiniRecordByParam',
             
             //pc 获取会员
             //url:'/MiddleWare/pc/findMemListByParam',
             //data:'params={"param":{"doctorId":286},"memberId":"286","version":"v2.0","session":"8e35030e-2593-42ce-8f81-5abb8f949b9b","loginLog":"2015-11-20 15:49:11"}',
             
             //医生查询我的糖尿病随访列表
             url:'/MiddleWare/docHealthExam/findHealthExamList',
             data:'params={"param":{"searchParam":"叶"},"memberId":"316","version":"v2.0","session":"95d80525-8b65-4721-bba9-539d81aad4d1","loginLog":"2015-11-20 15:49:11"}',
            dataType:'json',
            success:function(data){
                alert(data.message);
            },
            error:function(data){
                alert(data.message);
            }
        });
    }
  </script>
</html>



<%-- 
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>百度音乐</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <script type="text/javascript" src="/js/jquery-1.9.1.min.js"></script>
  </head>
  
  <body>
    百度音乐 <br>
    <table>
        <tr>
            <td>数据：</td>
            <td>歌曲名称</td>
            <td><input type="text" id="song"></td>
            <td><input type="button" onclick="getInfo();" value="获取"></td>
        </tr>
    </table>
  </body>
  <script type="text/javascript">
    function getInfo(){
        alert("begin!");
        var song = $("#song").val();
        $.ajax({
            type:'POST',
            url:'http://mp3.baidu.com/dev/api/?tn=getinfo&ct=0&ie=utf-8',
            data:{"word":song,"format":"json","":""},
            dataType:'json',
            success:function(data){
                alert(data.message);
            },
            error:function(data){
                alert(data.message);
            }
        });
    }
  </script>
</html>--%>