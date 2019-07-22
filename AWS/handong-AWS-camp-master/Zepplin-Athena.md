# EMR의 Zeppelin에 Athena Interpreter 추가하기

*이번 글에서는 EMR에 Spark와 Zeppelin이 올라가 있다고 가정합니다.* 

EMR에서 제공하는 Zeppelin에 Athena를 연결하려면 크게는 다음과 같은 작업을 해야합니다. 

* IAM Role 수정 
* Zeppelin에 JDBC Interpreter 설치 
* Athena 용 JDBC 다운로드 
* Athena에 맞게 JDBC Interpreter 추가

그럼 하나씩 살펴보겠습니다. 

### 1. IAM Role 수정

EMR의 Master에는 기본적으로 DynamoDB, Glue, Kinesis, RDS, S3, SNS, SQS 등에 관한 권한이 설정되어 있습니다. (아래 그림 참고)

![그림1](/images/EMR-EC2-Role.png)

그런데 이번에는 Athena와 연동을 시켜줄 것이기 때문에, Athena를 컨트롤할 수 있는 권한을 추가해줘야합니다. 
뭔가 Default는 건들면 안될 것 같으니 다음과 같이  새로 IAM Role을 추가합니다. 

* Role -> Create Role 클릭
* 이 역할을 사용할 서비스에서 EC2 선택후 `[Next: Permissions]` 클릭 
* `'AmazonElasticMapReduceforEC2Role'` 와 `'AmazonAthenaFullAccess'` 를 찾아 선택
* 태그는 무시 (원한다면 당연히 입력하셔도 됩니다.)
* Role 이름에 `'EMR_EC2_AthenaRole'` 입력 후 `[Create Role]` 클릭 

이제 Role이 완성되었으니 이를 원래 있던 Role대신 넣어줘야 합니다. Zeppelin은 EMR의 마스터 노드에 있으니 Master노드를 찾아 변경해 Role을 변경해줍니다. 

* EMR -> 해당 Cluster 클릭 -> Hardware -> Master 의 ID 클릭 -> EC2 Instance ID 클릭 (EC2 콘솔로 이동하게 됩니다)
* EC2 콘솔에서 Actions -> Instance Settings -> `Attach/Replace IAM Role` 클릭 
* IAM role 에서 `'EMR_EC2_AthenaRole'` 선택 후 `[Apply]` 클릭 

자, 이제 EMR의 마스터노드에서는 Athena를 마음껏 컨트롤할 수 있게 되었습니다. 

그럼 이제 Zeppelin을 세팅해 봅시다.

### 2. Zeppelin에 JDBC Interpreter 설치 

이상하게 EMR의 Zeppelin에는 JDBC Interpreter가 설치되어 있지 않습니다. 일단 Spark 만 컨트롤할 수 있게 가볍게 세팅이 되어 있는것 같네요. 그러니 우선 JDBC Interpreter를 설치해 줍시다. 

일단, 마스터 노드에 ssh 로 접근해줍니다. (Windows는 putty 등 사용)

 ```bash
 ssh -i {나의pemkey.pem} hadoop@{Masternode EC2  Public DNS}
 ````

그 다음엔 일단 Zeppelin을 멈추고, 

```bash
sudo stop zeppelin
``` 

jdbc interpreter를 설치하고,

```bash
sudo /usr/lib/zeppelin/bin/install-interpreter.sh -n jdbc
```

Zeppelin을 다시 시작해줍니다. 

```bash
sudo start zeppelin
```

~~*참 쉽죠?*~~


### 3. Athena 용 JDBC 다운로드 

그럼 이제 Zeppelin이 사용할 Athena용 JDBC 드라이버를 받아봅시다. 

우선 jar 파일이 들어갈 경로를 정해봅시다. 저는 `/usr/local/jar` 로 정했습니다. 

```bash
sudo mkdir /usr/local/jar
sudo cd /usr/local/jar
```

wget을 이용해서 jdbc 파일을 받고, 

```bash
sudo wget https://s3.amazonaws.com/athena-downloads/drivers/JDBC/SimbaAthenaJDBC_2.0.7/AthenaJDBC42_2.0.7.jar
```
마지막으로, zeppelin이 실행시킬 수 있게 권한을 바꿔줍니다.

```bash
sudo chmod 755 AthenaJDBC42_2.0.7.jar
```

### 4. Athena에 맞게 JDBC Interpreter 추가

자, 이제 마지막입니다. JDBC Interpreter만 Athena에 맞게 세팅해서 구성해주면 끝납니다. 

우측상단에 있는 Interpreter를 클릭후 Interpreter 창에서 `Create`를 누릅니다. 

![그림2](/images/zeppelin-interpreter.png)

그 후 다음과 같이 세팅합니다.
* `Interpreter Name` 은 적당히 'athena' 로 정해주고 
* `Interpreter Group` 은 JDBC 를 선택합니다. 

Properties 에서 
* `default.password`, `default.user`는 빈칸으로
* `default.driver`는 `com.simba.athena.jdbc.Driver`
* `default.url`은 다음과 같이 세팅합니다. 
  * `jdbc:awsathena://athena.ap-northeast-2.amazonaws.com:443;S3OutputLocation=s3://aws-athena-query-results-{계정번호12자리}-ap-northeast-2;Schema=default;AwsCredentialsProviderClass=com.simba.athena.amazonaws.auth.InstanceProfileCredentialsProvider; ` (서울리전 기준, 나머지 리전의 경우 리전에 맞춰서 세팅해주면 됩니다.)

Artifact는 jar파일을 받은 경로를 입력하면 됩니다. 

`/usr/local/jar/AthenaJDBC42_2.0.7.jar`

![그림2.5](/images/zeppelin-interpreter-properties.png)
[properties 예시]

*Save를 누르면 드디어! Athena를 사용할 Interpreter가 생성이 됩니다.*

***

## 테스트 

그럼 Athena랑 연결이 잘 되었는지 테스트해 봅시다. 

Notebook-> Create New Note 에서 Default Interpreter를 Athena 로 선택 후 생성합니다. 

![그림3](/images/zeppelin-create-notebook.png)

athena에 등록된 테이블에 쿼리를 날려봤습니다. 

![그림4](/images/emr-zeppelin-athena-final-example.png)

