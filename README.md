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

# 🗒️ API 명세서  
Wiki Link  

# 성능 개선 기록  
Wiki ink  

# 트러블 슈팅   
- [트러블 슈팅 ‐ N 1 문제 해결](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-%E2%80%90-N-plus-1--%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0)  
- [트러블 슈팅 ‐ LazyInitializationException 문제](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-%E2%80%90-LazyInitializationException-%EB%AC%B8%EC%A0%9C)  
- [트러블 슈팅 ‐ 대기열 구현 후 토큰이 넘어가지 않는 문제 해결](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-%E2%80%90-%EB%8C%80%EA%B8%B0%EC%97%B4-%EA%B5%AC%ED%98%84-%ED%9B%84-%ED%86%A0%ED%81%B0%EC%9D%B4-%EB%84%98%EC%96%B4%EA%B0%80%EC%A7%80-%EC%95%8A%EB%8A%94-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0)  
- [트러블 슈팅 ‐ 결제 후 처리 딜레이가 너무 긴 문제 해결](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-%E2%80%90-%EA%B2%B0%EC%A0%9C-%ED%9B%84-%EC%B2%98%EB%A6%AC-%EB%94%9C%EB%A0%88%EC%9D%B4%EA%B0%80-%EB%84%88%EB%AC%B4-%EA%B8%B4-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0)  
- [트러블 슈팅 ‐ 대기열 구현 후 토큰이 넘어가지 않는 문제 해결](https://github.com/hanghae99-19-final-8/PigonAir/wiki/%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-%E2%80%90-%EB%8C%80%EA%B8%B0%EC%97%B4-%EA%B5%AC%ED%98%84-%ED%9B%84-%ED%86%A0%ED%81%B0%EC%9D%B4-%EB%84%98%EC%96%B4%EA%B0%80%EC%A7%80-%EC%95%8A%EB%8A%94-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0)  


# 팀원 소개
| 이름 | 담당 역할 | Github |
|----------|----------|----------|
| 이성욱(팀장)  |  | |
| 김동휘 | - 좌석 조회 Front-End 구현 및 Back-End 구현 <br> - 대기열 WebFlux + Redis로 구현 | [Link](https://github.com/coolhwi)|
| 김민기 | - Spring Security, Jwt를 이용하여 인증, 인가 기능 구현 <br> - 인증, 인가 관련한 에러 처리 <br> - Redis를 이용한 액세스 토큰 및 리프레쉬 토큰 기능 구현 <br> - Redis를 이용하여 로그인 캐쉬 구현 <br> - Github Actions, Docker를 이용한 CI\/CD 파이프라인 구축 <br> - 쉘 스크립트 이용하여 Blue\/Green 무중단 배포 구축 <br> - AWS EC2 서버 환경 구축 및 인스턴스 관리 <br> - certbot을 이용한 https 보안 처리 구현 <br> - nginx 웹서버를 이용하여 리버스 프록시 구현 <br> - ELK Stack, File Beat, Metric Beat 이용하여 System Metric 모니터링 시스템 구축 <br> - Elastic APM 병목 현상 모니터링 시스템 구축 <br> - Jmeter Tag를 이용하여 Jmeter의 결과를 ELK Stack에 통합시키는 파이프라인 구축   <br> - System Metric, Container Log, Jmeter 결과를 한 번에 볼 수 있는 대쉬보드 구축 <br> - AWS ALB를 이용하여 가용성 확보 작업 <br> - AWS Auto Scaling Group을 이용하여 가용성 확보 작업 <br> - 테스트 시나리오 설계 및 테스트 실행 <br> - Jmeter CLI 테스트 스크립트 자동화 <br> - Github ReadMe, Wiki 작성 | [Link](https://github.com/miiiingi)|
| 송유하 |  | |
| 신수현 |  | |
