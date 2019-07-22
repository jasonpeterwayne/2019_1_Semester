# Build a Serverless Web Application

* 오늘 실습에서는 Serverless 기반으로 간단한 웹서비스를 구성합니다
* 1일차 실습과 달리 가상 네트워크(VPC)를 구성하거나 가상 서버(EC2)를 생성하지 않습니다
* 사용하는 서비스와 구현 방법이 1일차와 어떻게 다른지. 그리고 각기 어떤 장단점이 있을지 생각해봐요

## 바로가기

* 총 5단계로 구성되어 있으며 각 단계별로 빠르면 10분에서 30분정도의 시간이 소요됩니다

### [① Host a Static Website](#1-host-a-static-website)
### [② Manage Users](#2-manage-users)
### [③ Build a Serverless Backend](#3-bulid-a-serverless-backend)
### [④ Deploy a RESTful API](#4-deploy-a-restful-api)
### [⑤ Terminate Resources](#5-terminate-resources)

## 개요

### [진행 관련]

* 준비물
  - AWS 계정
  - 인터넷이 되는 노트북 (웹브라우저는 Chrome 권장)
  - 텍스트 에디터 (Notepad++ 등)
  - 뭔가 잘 안되도 바로 포기하지 않는 멘탈 (숙련된 조교의 도움을 받으세요)

* 유의 사항
  - 모든 실습은 서울 리전(ap-northeast-2)에서 수행 (AWS 콘솔 우측 상단에서 선택)
  - 언어 설정은 영어 기준으로 진행 (AWS 콘솔 좌측 하단에서 선택)
  - 생성하는 AWS 리소스명은 대체로 대소문자를 구분하니 가이드대로 적어주세요

### [구성도]
![image](/images/Day2-0.png)

* _Amazon S3_: HTML/CSS/JavaScript/Image 등의 Static Contents를 Client에게 제공
* _Amazon Cognito_: 웹페이지 內 구현된 사용자 인증 관리에 활용
* _AWS Lambda_: Dynamic한 형태의 Client 요청을 받아 수행하고 DynamoDB에 히스토리를 기록
* _Amazon API Gateway_: 사용자 인증을 Cognito와 연동하고 뒷단 Lambda 함수를 실행하기 위한 Gateway
* _Amazon DynamoDB_: 히스토리를 저장하기 위한 NoSQL 기반의 관리형 DB

## 1. Host a Static Website

![image](/images/Day2-1.png)

소개

### 1-1. Select a Region
* `[AWS Management Console]` ▷ 우측 상단 Region 부분에 `Asia Pacific (Seoul)` 선택

### 1-2. Create an S3 Bucket

S3 버킷을 생성합니다

* `[AWS Management Console]` ▷ `[Find Services]` 검색창에 `S3` 입력
  - `[Create bucket]`선택

    - Bucket name: `wildryde-firstname-lastname` # 그대로 적지 말고 이름부분을 반드시 변경!
    - Region: `Asia Pacific (Seoul)`
    - 확인 후 `[Create]`

### 1-3. Upload Content

생성한 S3에 웹컨텐츠를 업로드 합니다

* `[AWS Management Console]` ▷ `[S3]`

  - `1-2` 단계에서 생성한 S3 버킷 선택
  - 현재 화면이 `Overview` 탭 위치에 있는지 확인
  
* 이 [링크](https://github.com/awslabs/aws-serverless-workshops/archive/master.zip) 파일을 로컬 PC로 다운로드
  
  - 적당한 위치에 압축 해제
  - 탐색기에서 `/WebApplication/1_StaticWebHosting/website` 하위 경로의 모든 파일 선택
  - 단, 선택 대상에서 website 경로는 불포함
  - 위 S3 버킷의 `Overview` (웹브라우저) 화면 위로 `Drag and Drop`
  - 확인 후 `[Upload]`
  - 업로드한 파일목록이 정상적으로 표시되는지 확인

### 1-4. Add a Bucket Policy to Allow Public Reads

모든 객체에 대해 익명 읽기-권한을 부여합니다

* `[AWS Management Console]` ▷ `[S3]`
  - `1-2` 단계에서 생성한 S3 버킷 선택
  - `Permissions` 탭 ▷ `Block public access` ▷ `[Edit]`
  
    - `Block all public access` 체크표시 해제
    - 확인 후 `[Save]`
    - `confirm` 문자열 입력 후 `[Confirm]`
  
  - `Permissions` 탭 ▷ `Bucket Policy`

    - 하단 빈공간에 아래 문자열 입력 후 `[Save]`
    - `[YOUR_BUCKET_NAME]` 항목은 반드시 본인이 생성한 버킷명으로 대체
    - `Permissions` 탭에 주황색 `Public` 표시가 덧붙었는지 확인    
```
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow", 
            "Principal": "*", 
            "Action": "s3:GetObject", 
            "Resource": "arn:aws:s3:::[YOUR_BUCKET_NAME]/*" 
        } 
    ] 
}
```

### 1-5. Enable Website Hosting

S3 버킷이 웹서버 역할을 할 수 있도록 Website Hosting 옵션을 활성화합니다

* `[AWS Management Console]` ▷ `[S3]`
  - `1-2` 단계에서 생성한 S3 버킷 선택
  - `Properties` 탭 ▷ `Static website hosting`
  
    - `Use this bucket to host a website` 선택
    - Index document: `index.html`
    - 상단 `Endpoint` 항목에 표시된 `http://`로 시작하는 주소는 별도 기록
    - 확인 후 `[Save]`
    - `Static website hosting` 부분에 보라색 체크표시가 되었는지 확인

### 1-6. Validate Your Implementation

이전 단계가 제대로 수행되었는지 확인합니다

* `1-5` 단계에서 기록한 Endpoint 주소로 웹페이지 열기

  - 웹브라우저에 정상적으로 말떼가 표시되는지 확인
  - 화면의 `GIDDY UP` 선택시 표시되는 것은?

---
## 2. Manage Users

![image](/images/Day2-2.png)

소개

### 2-1. Create an Amazon Cognito User Pool

인증풀을 관리하기 위한 User Pool을 생성합니다

* `[AWS Management Console]` ▷ `[Cognito]`
  - `[Manage User Pools]` ▷ `[Create a user pool]` 선택

    - Pool name: `WildRydes`
    - `Review defaults` 선택
    - 확인 후 `[Create pool]`
    - 상단 `Pool Id` 항목값은 별도 기록

### 2-2. Add an App to Your User Pool

App client를 설정합니다

* `[AWS Management Console]` ▷ `[Cognito]` ▷ `[Manage User Pools]`
  - `WildRydes` 객체 선택
  - `General settings` 탭 ▷ `App clients` ▷ `App an app client`

    - App client name: `WildRydesWebApp`
    - Generate client secret: 체크표시 해제  
    - `Create app client` 선택
    - 표시되는 `App client id` 항목값은 별도 기록

### 2-3. Update the config.js File in Your Website Bucket

환경변수 파일에 cognito 객체ID값을 입력합니다

* 로컬 PC에서 다음 작업
  - `1-3` 단계의 `/WebApplication/1_StaticWebHosting/website/js/config.js` 파일을 에디터로 열기

    - userPoolId: `2-1` 단계에서 기록한 `Pool Id`값 붙여넣기
    - userPoolClientId: `2-2` 단계에서 기록한 `App client id`값 붙여넣기
    - region: `ap-northeast-2`
    - 에디터에서 `Save`

* `[AWS Management Console]` ▷ `[S3]`
  - `1-2` 단계에서 생성한 S3 버킷 선택
  - 위에서 수정한 `config.js` 파일을 `/js` 경로에 업로드(덮어쓰기)

### 2-4. Test Your Implementation

이전 단계가 제대로 수행되었는지 확인합니다

* `1-5` 단계에서 기록한 `Endpoint주소/register.html` 주소로 웹페이지 열기
  - 아래 항목을 다음과 같이 입력
  
    - Email: 인증에 사용할 이메일 주소
    - Password: 원하는 암호
    - Confirm Password: 재입력
  - 해당 메일주소로 수신된 인증코드 확인

* `[AWS Management Console]` ▷ `[Cognito]`
  - `WildRydes` 객체 선택
  - `General settings` 탭 ▷ `Users and groups` ▷ `Users` 탭에서 방금 생성한 유저 확인

    - `Email verified` 값이 `false`임을 확인

* `1-5` 단계에서 기록한 `Endpoint주소/verify.html` 주소로 웹페이지 열기
  - 아래 항목을 다음과 같이 입력

    - Email: 등록한 이메일 주소
    - Verification Code: 메일에서 확인한 인증코드 입력
    - 확인 후 `[VERIFY]`

* `[AWS Management Console]` ▷ `[Cognito]`
  - `WildRydes` 객체 선택
  - `General settings` 탭 ▷ `Users and groups` ▷ `Users` 탭에서 방금 생성한 유저 확인

    - `Email verified` 값이 `true`로 변경되었음을 확인

* `1-5` 단계에서 기록한 `Endpoint주소/signin.html` 주소로 웹페이지 열기
  - 아래 항목을 다음과 같이 입력

    - Email: 등록한 이메일 주소
    - Password: 등록한 암호
    - 확인 후 `[SIGN IN]`
    - `/ride` 주소로 리다이렉트되면서 `auth token`값 출력, 해당값 별도 기록

---
## 3. Bulid a Serverless Backend

![image](/images/Day2-3.png)

소개

### 3-1. Create an Amazon DynamoDB Table

DynamoDB 서비스에서 신규 테이블을 만듭니다

* `[AWS Management Console]` ▷ `[DynamoDB]`
  - `[Create table]` 선택

    - Table name: `Rides`
    - Partition key: `RideId`
    - Use default settings: 체크함
    - 확인 후 `[Create]`
    - `Overview` 탭 ▷ Table details 하단에 `Amazon Resource Name(ARN)` 항목값 별도 기록

### 3-2. Create an IAM Role for Your Lambda function

Lambda 함수에 권한을 부여하기 위해 IAM Role 객체를 생성합니다

* `[AWS Management Console]` ▷ `[IAM]`
  - 좌측탭 `Roles` ▷ `[Create role]`
  
    - Choose the service that will use this role: `Lambda` ▷ `[Next: Permissions]`
    - Search Window: `AWSLambdaBasicExecutionRole` ▷ 검색된 항목에 체크 ▷ `[Next: Tags]`
    - Add tags: 공란으로 두고 ▷ `[Next: Review]`
    - Role name: `WildRydesLambda` ▷ `[Create role]`

  - 좌측탭 `Roles` ▷ Search Window: `WildRydesLambda` ▷ 검색된 Role 객체 선택
    
    - `Permissions` 탭 ▷ `Add inline policy`

      - Service: `DynamoDB`
      - Actions: `PutItem`
      - Resources: `Specific`
      - Add ARN → Specify ARN for table: `3-1` 단계에서 기록한 ARN값 입력 ▷ `[Add]`
      - 확인 후 `[Review policy]`
      - Name: `DynamoDBWriteAccess`
      - 확인 후 `[Create policy]`


### 3-3. Create a Lambda Function for Handling Requests

Lambda 함수를 생성합니다

* `[AWS Management Console]` ▷ `[Lambda]` ▷ `[Create a function]`
  - `Author from scratch` 선택

    - Function name: `RequestUnicorn`
    - Runtime: `node.js 8.10`
    - Permissions: `Choose or create an execution role` ▷ `Use an existing role` ▷ `WildRydesLambda`
    - 확인 후 `[Create function]`

* `[AWS Management Console]` ▷ `[Lambda]`
  - `RequestUnicorn` 선택
  
    - Function code 내 `index.js` 공간 확인
    - `1-3` 단계의 `/WebApplication/3_ServerlessBackend/requestUnicorn.js` 내용 붙여넣기
    - 우측 상단 `[Save]`
  
### 3-4. Test Your Implementation

이전 단계가 제대로 수행되었는지 확인합니다

* `[AWS Management Console]` ▷ `[Lambda]`
  - `RequestUnicorn` 선택

    - 우측 상단 `Select a test event` dropdown ▷ `Configure test events`

      - Event name: `TestRequestEvent`
      - 하단에 아래 내용 붙여넣은 후 `[Create]`
```
{
    "path": "/ride",
    "httpMethod": "POST",
    "headers": {
        "Accept": "*/*",
        "Authorization": "eyJraWQiOiJLTzRVMWZs",
        "content-type": "application/json; charset=UTF-8"
    },
    "queryStringParameters": null,
    "pathParameters": null,
    "requestContext": {
        "authorizer": {
            "claims": {
                "cognito:username": "the_username"
            }
        }
    },
    "body": "{\"PickupLocation\":{\"Latitude\":47.6174755835663,\"Longitude\":-122.28837066650185}}"
}
```

* `[AWS Management Console]` ▷ `[Lambda]`
  - `RequestUnicorn` 선택

    - 우측 상단 `TestRequestEvent` 지정 후 `[Test]`

      - Execution result: `succeeded`로 표시되는지 확인
      - Details: 응답값에 `statusCode`가 `201`로 표시되는지 확인

---
## 4. Deploy a RESTful API

![image](/images/Day2-4.png)

소개

### 4-1. Create a New REST API

API Gateway 객체를 만듭니다

* `[AWS Management Console]` ▷ `[API Gateway]` 
  - `[Create API]` 또는 `[Get Started]` 선택

    - Choose the protocol: `REST`
    - Create new API: `New API`
    - Settings
      
      - API name: `WildRydes`
      - Endpoint Type: `Edge optimized`
    - 확인 후 `[Create API]`

### 4-2. Create a Cognito User Pools Authorizer

Cognito에서 설정했던 인증을 연동합니다

* `[AWS Management Console]` ▷ `[API Gateway]` 
  - `WildRydes` 선택
  
    - `Authorizers` 탭 ▷ `[Create New Authorizer]`
    
      - Name: `WildRydes`
      - Type: `Cognito`
      - Cognito User Pool: `ap-northeast-2` - `WildRydes`
      - Token Source: `Authorization`
      - 확인 후 `[Create]`

* `[AWS Management Console]` ▷ `[API Gateway]` 
  - `WildRydes` 선택
  
    - `Authorizers` 탭 ▷ `WildRydes` ▷ `[Test]`
    
      - `2-4` 단계에서 기록한 `Authorization Token` 값 붙여넣기 ▷ `[Test]`
      - Response Code가 `200`인지 확인

### 4-3. Create a New Resource and Method

API Gateway에서 POST 메쏘드를 생성합니다

* `[AWS Management Console]` ▷ `[API Gateway]` 
  - `WildRydes` 선택

    - `Resources` 탭 ▷ `[Actions]` ▷ `[Create Resource]`
    
      - Resource Name: `ride`
      - Resource Path: `ride`
      - Enable API Gateway CORS: 체크함
      - 확인 후 `[Create Resource]`
      
    - `Resources` 탭 ▷ `/ride` 선택 ▷ `[Actions]` ▷ `[Create Methods]` ▷ dropdown에서 `POST` 선택 ▷ 체크
    
      - Integration type: `Lambda Function`
      - User Lambda Proxy Integration: 체크함
      - Lambda Region: `ap-northeast-2`
      - Lambda Function: `RequestUnicorn`
      - 확인 후 `[Save]`
      - Add Permission to Lambda Function 창이 뜨면 `[OK]`

    - `Resources` 탭 ▷ `/ride` 하위 `POST` 선택 ▷ `[Method Request]` 선택
    
      - Authorization: `WildRydes` ▷ 체크
      
이제 한스텝만 더 가면 됩니다

### 4-4. Deploy Your API

설정한 API를 프로덕션 환경으로 배포합니다

* `[AWS Management Console]` ▷ `[API Gateway]` 
  - `WildRydes` 선택

    - `Resources` 탭 ▷ `[Actions]` ▷ `[Deploy API]`
      
      - Deployment stage: `[New Stage]`
      - Stage name: `prod`
      - 확인 후 `[Deploy]`
    
    - `Stages` 탭 ▷ `prod` 선택 ▷ 상단에 `Invoke URL` 항목값 별도 기록
    
### 4-5. Update the Website Config

API Gateway의 끝점을 환경변수 파일에 반영합니다

* 로컬 PC에서 다음 작업
  - `1-3` 단계의 `/WebApplication/1_StaticWebHosting/website/js/config.js` 파일을 에디터로 열기

    - invokeUrl: `4-4` 단계에서 기록한 `Invoke URL`값 붙여넣기    
    - 에디터에서 `Save`

* `[AWS Management Console]` ▷ `[S3]`

  - `1-2` 단계에서 생성한 S3 버킷 선택
  - 위에서 수정한 `config.js` 파일을 `/js` 경로에 업로드(덮어쓰기)

### 4-6. Validate Your Implementation

이전 단계가 제대로 수행되었는지 확인합니다

* `1-5` 단계에서 기록한 `Endpoint주소/ride.html` 주소로 웹페이지 열기
  - 아래 항목을 다음과 같이 입력 (미로그인 상태시, 인증상태인 경우 패스)

    - Email: 등록한 이메일 주소
    - Password: 등록한 암호
    - 확인 후 `[SIGN IN]`
  
  - 원하는 위치 선택 후 우측 상단 `[Request Unicorn]`
  - 해당 위치로 유니콘이 한마리 찾아오면 성공입니다. 콩그레추레이션!
    
실행 결과 다시 보기

* `[AWS Management Console]` ▷ `[CloudWatch]`
  - 좌측 `Logs` 탭 선택
    
    - Log Groups 객체 중 `/aws/lambda/RequestUnicorn` 선택
    - 최근 Log Streams 객체 선택 ▷ 람다 함수 실행로그 확인

* `[AWS Management Console]` ▷ `[DynamoDB]`
  - 좌측 탭 `Tables` 선택 ▷ `Rides`로 되어있는 테이블 선택
  
    - 상단 `Item` 탭에서 람다 함수에서 기록한 히스토리 레코드 확인

실습은 끝났습니다.

이제 삭제할일만 남았습니다. 돈내면 안되잖아요.

---
## 5. Terminate Resources

![image](/images/Day2-5.png)

소개

### 5-1. Delete Your Amazon S3 Bucket

S3 버킷을 삭제합니다

* `[AWS Management Console]` ▷ `[S3]`
  - `1-2` 단계에서 생성한 S3 버킷명 좌측에 체크 표시
  - 상단 `[Delete]`
  
    - 삭제확인을 위해 `버킷명` 입력
    - 확인 후 `[Confirm]`

### 5-2. Delete Your Amazon Cognito User Pool

이어 Cognito 유저 풀을 삭제합니다

* `[AWS Management Console]` ▷ `[Cognito]` ▷ `[Manage User Pools]`
  - `WildRydes` 객체 선택
  - 우측 상단 `[Delete pool]`
  
    - 삭제확인을 위해 `delete` 입력
    - 확인 후 `[Delete pool]`

### 5-3. Delete Your Serverless Backend

먼저 Lambda 함수 객체를 삭제합니다

* `[AWS Management Console]` ▷ `[Lambda]`
  - 좌측 `Functions` 탭 선택
  - `RequestUnicorn` 객체 좌측에 체크 표시
  - 우측 상단 `[Actions]` ▷ `[Delete]`
  
    - 팝업창 확인 후 `[Delete]`

다음으로 IAM Role 객체를 지웁니다

* `[AWS Management Console]` ▷ `[IAM]`
  - 좌측 `Roles` 탭 선택
  - Search Window: `WildRydesLambda` ▷ 검색된 객체 좌측에 체크 표시 ▷ `[Delete role]` 선택
  
    - 팝업창 확인 후 `Yes, delete`

마지막으로 DynamoDB 테이블을 삭제합니다

* `[AWS Management Console]` ▷ `[DynamoDB]`
  - 좌측 `Tables` 탭 선택
  - `Rides` 객체 좌측에 체크 표시
  - 상단 `[Delete table]`
  
    - Delete all CloudWatch alarms for this table: 체크함
    - Create a backup before deleting this table: 체크 해제
    - 확인 후 `[Delete]`
    

### 5-4. Delete Your REST API

이어 API Gateway 객체를 지웁니다

* `[AWS Management Console]` ▷ `[API Gateway]`
  - `WildRydes` 선택
  - 상단 `[Actions]` ▷ `[Delete API]`  
  
    - 공란에 `WildRydes` 입력
    - 확인 후 `[Delete API]`
    
### 5-5. Delete Your CloudWatch Log

마지막으로 CloudWatch Log Group을 지웁니다

* `[AWS Management Console]` ▷ `[CloudWatch]`
  - 좌측 `Logs` 탭 선택
  - `/aws/lambda/RequestUnicorn` 객체 좌측에 체크 표시
  - 상단 `[Actions]` ▷ `[Delete log group]` 
      
    - 확인 후 `[Yes, Delete]`

![image](/images/Day2-FN.png)

수고하셨습니다. 오늘은 집에 가셔도 됩니다.


