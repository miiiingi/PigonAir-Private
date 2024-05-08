# 🛫 프로젝트 명 : Pigon Air[항공권 티켓팅 서비스]  
![Group 28 (1)](https://github.com/hanghae99-19-final-8/PigonAir/assets/71509516/66802943-fc77-498f-88fb-6c1b674ace08)  
```
- 항공권 특가와 같이 대규모 트래픽이 있는 상황에서도 안정적인 예약 및 결제  
- 실시간 모니터링으로 애플리케이션 및 DB의 병목 상태 확인 가능  
- 대기열을 통해 서버의 부하를 방지해주고 많은 부하로부터 안정적으로 서비스를 제공  
- 중복 예약 걱정 없는 누구에게나 공정한 예약 서비스를 위한 동시성 제어  
```  
# 🎯 프로젝트 목표  
```
- 병목 지점을 파악하여 그에 맞는 성능 향상 기술 도입하기
- 기술들을 도입했을 때, 얼마나 성능 향상이 있었는지 정량적으로 파악하기 
- 병목 지점을 해결하여 같은 서버 스펙으로 제공할 수 있는 최적의 서비스 제공하기
```  
# 🎥 발표 영상
[발표영상](https://youtu.be/3VPBBqKPggc)

# 🏛️ 서비스 아키텍처  
![Untitled](https://github.com/hanghae99-19-final-8/PigonAir/assets/71509516/06a6f476-b0d8-4eff-806c-7d04de47f1d0)

# ⚡ 기술 스택  
## BackEnd  
| Category | Tech |
|----------|----------|
| Application  | SpringBoot |
| | Spring JPA |
| | Spring Security|
| | WebFlux |
| | RabbitMQ |
| Platform  | Ubuntu |
| DB  | AWS RDS(Mysql) |
|  | AWS Elastic Cache(Redis) |
|  | Redis |
| DevOps  | AWS EC2 |
|  | Docker |
|  | AWS ALB |
|  | AWS Auto Scaling Group |
| CI\/CD  | Github Actions |
|  | Docker |
| Testing  | Junit5 |
|  | Apache Jmeter |
| Monitoring - Performance Test  | Elastic APM |
| Monitoring - System Metric, Logging  | File Beat |
|  | Metric Beat |  
# 💾 ERD  
![hh99finalproject](https://github.com/hanghae99-19-final-8/PigonAir/assets/71509516/60acbf8f-9fda-4c3b-a116-1fef73ad164f)  

# 👤 User Flow  
![Untitled (1)](https://github.com/hanghae99-19-final-8/PigonAir/assets/71509516/fe844842-4803-44d8-99ae-c57890f39a56)    

# 🤔 기술 선택에 대한 고민

| 구분 | 요구사항 | 선택한 기술 | 대안들 | 핵심 기술을 선택한 이유 및 근거 |
| --- | --- | --- | --- | --- |
| BE | CI/CD | GitHub Action |  | - Github와 연동 작업이 원활하여 서버 빌드, 배포 과정에 필요한 비용이적음 <br> - yaml 파일을 이용하여 workflow를 직접 조절할 수 있으므로 자유도가 높은 빌드, 배포 과정을 구축할 수 있음 |
|  | CI/CD | Docker | AWS(S3, Code Deploy) | Docker는 컨테이너 기술을 활용하여 개발 환경과 운영 환경의 일관성을 보장하며, CI/CD 파이프라인 구축 비용을 최소화할 수 있다고 판단하여 채택. |
|  | Server | AWS EC2 | NCP EC2 | NCP와 비교하여 AWS는 프리 티어 옵션에서 제공하는 서버의 성능이 뛰어나며, AWS의 Eco-System이 풍부하고, Load Balancer 및 Auto Scaling 기능이 잘 구축되어 있어 채택. |
|  | DB Server | RDS | EC2 Server | RDS는 관리형 서비스로서 백업, 패치 관리, 스케일링을 자동으로 처리할 수 있어 DB 운영의 편의성을 제공합니다. 이를 통해 서비스 서버에 영향을 받지 않고 DB 성능을 유지할 수 있다.또한, 금액대가 존재하지만 사용 가능한 다양한 추가 기능(복제와 같은), 확장성을 고려하여 채택 |
|  | RDBMS | MYSQL | PostgreSQL | MySQL은 상대적으로 적은 러닝커브와 덜 복잡한 DB 구조를 가지고 있어 사용하기 쉽다. 또한, 서비스에서는 읽기 작업이 중심이므로 쓰기 작업에 특화된 PostgreSQL보다 MySQL을 채택 |
|  | DB Query | JPA, jpql | QueryDSL | -서비스에 사용될 쿼리문이 fetch join을 여러 번 사용하지 않아 JPA와 @EntityGraph으로도 해결이 가능하다고 판단하여 채택 DTO가 시간 상 구현되지 못하여 일단 jpql 채택, DTO 구현 후 @EntityGraph로 대체 예정 |
|  | Caching | RedisCache | Memcached | RedisCache는 복잡한 데이터 구조를 저장할 수 있는 기능을 가지고 있으며, Page<Entity> 객체를 효과적으로 캐싱할 수 있기에 구현이 더 단순한 Memcached보단 Redis의 기능성을 선호하여 채택 |
|  | 비동기 작업 | @Async | Webflux | 메인 프로젝트 기반이 Web MVC로 작성되어 있어 Webflux와의 충돌이 발생하여 채택. 추가적으로 @Async만 추가하면 사용이 가능한 간편함도 고려되었다. |
|  | MessageQue | RabbitMQ | Kafka, WebSocket | Kafka의 높은 러닝커브와 WebSocket의 높은 리소스 사용을 고려하여 RabbitMQ를 채택 |
|  | Connection Pool | HikariCP |  | HikariCP는 제로 오버헤드 기술을 제공하며, 설정 수정만으로 효율적인 연결 풀 관리가 가능하여 채택.동적 커넥션 풀 관리를 직접 구현해야 한다는 단점이 있지만, 오버헤드가 거의 없고 설정의 단순하다는 장점을 가지고 있다. |
|  | LoadBalancer |  ALB | nginx | AWS EC2 서버와의 높은 호환성과 적은 러닝커브, 프리 티어 한정 무료 서비스 제공 등으로 ALB를 채택. 기본 nginx는 로드 밸런서 기능을 제공하지 않고, 금액을 지불해야 사용할 수 있는 nginx + 가 로드 밸런서 기능을 제공하여 보류 |
|  | Test |  JMeter | nGrinder | nGrinder는 모든 스크립트를 Groovy로 작성해야 하며 자바 11버전 이하로만 사용 가능하여 윈도우 운영체제 환경에서 러닝커브가 Jmeter보다 높다고 판단하였다. JMeter는 CLI와 GUI가 둘 다 가능하며 간단하다. 또한 GUI를 통하여 간단하게 테스트 스크립트를 작성하고, 자세한 테스트 필요시 Groovy로 확장할 수 있어 채택. |
|  | Application Performance Monitoring | Elastic APM | PinPoint | PinPoint는 환경 구축이 어렵고 참고 자료등의 Eco System이 부족하다고 체감되었고, UI가 측정 지표 등이 한 눈에 들어오지 않는 특징이 있었음 Elastic APM의 경우 ELK Stack을 기반으로 하기때문에 Eco System이 잘 구축되어있고 환경 설정이 보다 쉬운 점이 있었고, 어플리케이션의 병목, 데이터 베이스의 병목 등을 시각적으로 잘 볼 수 있고, 원하는 지표만을 산출하여 대쉬보드를 구성할 수 있는 등의 확장 기능을 잘 구축되어 있다는 특징이 있어 Elastic APM을 채택 |
|  | System Metric Monitoring | ELK Stack, Metric beat | Prometheus, Grafana | 이미 Elastic APM을 사용하고 있으므로 ELK Stack을 통한 시스템 메트릭 모니터링은 추가 비용이 적고 통합 관리가 용이하다고 판단하였다. 또한, 어떤 시점에 어떤 api를 실행할 때 cpu 사용량, 메모리 사용량이 증가하는지를 APM의 결과와 같이 보면 어플리케이션의 상황을 더 잘 파악할 수 있을 것으로 판단하여 ELK Stack, Metric Beat를 채택 |
|  | 대기열 | Redis, WebFlux | Kafka, Websocket | Kafka는 프로젝트에서 사용하기엔 단순하게 토큰만 주고받기에 높은 러닝커브를 가지고 있어 보류. Websocket의 경우 실시간 통신이라는 유용한 기능을 가지고 있지만, 서비스상 많은 리소스를 차지하여 보류. 미리 기술 스택을 적용중인 Redis를 채택하였다. |
|  | 캐싱 작업 | Elastic Cache Redis | EC2 Redis | 캐싱 작업을 EC2 인스턴스 내에서 진행한다는 것은 서비스 서버에 잠재적인 부하가 가해질 것이라 판단하여 분리하기로 결정. AWS EC2 서버에서 설정하기 쉬운 Elastic Cache Redis 채택 |


# 성능 개선 사항  
- [성능 개선 사항 - Caching](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0-%EC%82%AC%ED%95%AD-%E2%80%90-Caching)  
- [성능 개선 사항 - DB Indexing](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0-%EC%82%AC%ED%95%AD-%E2%80%90-DB-indexing)  
- [성능 개선 사항 - 대기열](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0-%EC%82%AC%ED%95%AD-%E2%80%90-%EB%8C%80%EA%B8%B0%EC%97%B4)  
- [성능 개선 사항 - 비동기 작업](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0-%EC%82%AC%ED%95%AD-%E2%80%90-%EB%B9%84%EB%8F%99%EA%B8%B0-%EC%9E%91%EC%97%85)  
- [성능 개선 사항 - Elastic Cache](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0-%EC%82%AC%ED%95%AD-%E2%80%90-AWS-Elastic-Cache)  
- [성능 개선 사항 - AWS ALB, AutoScalingGroup](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0-%EC%82%AC%ED%95%AD-%E2%80%90-%EA%B0%80%EC%9A%A9%EC%84%B1-%ED%99%95%EB%B3%B4(AWS-ALB,-Auto-Scaling))  
- [성능 개선 사항 - Connection Pool 최적화](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%EC%84%B1%EB%8A%A5-%EA%B0%9C%EC%84%A0-%EC%82%AC%ED%95%AD-%E2%80%90-Connection-Pool-%EC%B5%9C%EC%A0%81%ED%99%94)    

# 트러블 슈팅   
- [트러블 슈팅 ‐ N 1 문제 해결](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-%E2%80%90-N-plus-1--%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0)  
- [트러블 슈팅 ‐ LazyInitializationException 문제](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-%E2%80%90-LazyInitializationException-%EB%AC%B8%EC%A0%9C)  
- [트러블 슈팅 ‐ 대기열 구현 후 토큰이 넘어가지 않는 문제 해결](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-%E2%80%90-%EB%8C%80%EA%B8%B0%EC%97%B4-%EA%B5%AC%ED%98%84-%ED%9B%84-%ED%86%A0%ED%81%B0%EC%9D%B4-%EB%84%98%EC%96%B4%EA%B0%80%EC%A7%80-%EC%95%8A%EB%8A%94-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0)  
- [트러블 슈팅 ‐ 결제 후 처리 딜레이가 너무 긴 문제 해결](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-%E2%80%90-%EA%B2%B0%EC%A0%9C-%ED%9B%84-%EC%B2%98%EB%A6%AC-%EB%94%9C%EB%A0%88%EC%9D%B4%EA%B0%80-%EB%84%88%EB%AC%B4-%EA%B8%B4-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0)  
- [트러블 슈팅 ‐ 대기열 구현 후 토큰이 넘어가지 않는 문제 해결](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-%E2%80%90-%EB%8C%80%EA%B8%B0%EC%97%B4-%EA%B5%AC%ED%98%84-%ED%9B%84-%ED%86%A0%ED%81%B0%EC%9D%B4-%EB%84%98%EC%96%B4%EA%B0%80%EC%A7%80-%EC%95%8A%EB%8A%94-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0)  


# 팀원 소개
| 이름 | 담당 역할 | Github |
|----------|----------|----------|
| 김민기 | - Spring Security, Jwt를 이용하여 인증, 인가 기능 구현 <br> - 인증, 인가 관련한 에러 처리 <br> - Redis를 이용한 액세스 토큰 및 리프레쉬 토큰 기능 구현 <br> - Redis를 이용하여 로그인 캐쉬 구현 <br> - Github Actions, Docker를 이용한 CI\/CD 파이프라인 구축 <br> - 쉘 스크립트 이용하여 Blue\/Green 무중단 배포 구축 <br> - AWS EC2 서버 환경 구축 및 인스턴스 관리 <br> - certbot을 이용한 https 보안 처리 구현 <br> - nginx 웹서버를 이용하여 리버스 프록시 구현 <br> - ELK Stack, File Beat, Metric Beat 이용하여 System Metric 모니터링 시스템 구축 <br> - Elastic APM 병목 현상 모니터링 시스템 구축 <br> - Jmeter Tag를 이용하여 Jmeter의 결과를 ELK Stack에 통합시키는 파이프라인 구축   <br> - System Metric, Container Log, Jmeter 결과를 한 번에 볼 수 있는 대쉬보드 구축 <br> - AWS ALB를 이용하여 가용성 확보 작업 <br> - AWS Auto Scaling Group을 이용하여 가용성 확보 작업 <br> - 테스트 시나리오 설계 및 테스트 실행 <br> - Jmeter CLI 테스트 스크립트 자동화 <br> - Github ReadMe, Wiki 작성 | [Link](https://github.com/miiiingi)|
