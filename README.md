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
Wiki Link  

# 팀원 소개
| 이름 | 담당 역할 | Github |
|----------|----------|----------|
| 이성욱(팀장)  |  | |
| 김동휘 | 좌석 조회 Front-End 구현 및 Back-End 구현 <br> 대기열 WebFlux + Redis로 구현 | [Github](https://github.com/coolhwi)|
| 김민기 |  | |
| 송유하 |  | |
| 신수현 |  | |
