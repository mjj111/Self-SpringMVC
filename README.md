<p align="center">  
<img width="312" alt="spring" src="https://github.com/mjj111/Self-SpringMVC/assets/86913355/9947edcb-d352-4600-b165-249e681dc563">
</p>

# Make-SpringMVC

"원리를 상상할 수 있다면, 만들 수 있는 것이 개발자가 아닐까?"라는 생각에 자바로 Spring 의존성 없이 MVC 구조와 IoC를 직접 구현해본 토이 프로젝트입니다. 해당 프로젝트는 GDSC PKNU에서 Spring을 처음 학습하는 분들께 원리 이해와 활용 방법을 강의하는 데 사용되었습니다.<br></br>

## 프로젝트 개요

이 프로젝트는 Spring 프레임워크의 의존성 없이 Spring MVC 구조와 IoC 컨테이너를 직접 구현하는 것을 목표로 합니다. 이를 통해 개발자들이 Spring의 내부 동작 원리를 깊이 이해하고, 직접 만들어보며 학습할 수 있도록 도와줍니다.<br></br>

## 주요 기능

- **MVC 구조 구현**: Model-View-Controller 패턴을 직접 구현하여 웹 애플리케이션의 기본 구조를 학습합니다.
- **IoC 컨테이너**: Inversion of Control 컨테이너를 구현하여 의존성 주입과 객체 생명 주기를 관리합니다.
- **Dispatcher Servlet**: 요청을 처리하고 적절한 컨트롤러에 전달하는 Dispatcher Servlet을 구현합니다.
<br></br>
## 구성도<br></br>
### 1. ApplicationContext 초기화 

<img width="1368" alt="IOC 컨테이너 초기화" src="https://github.com/mjj111/Self-SpringMVC/assets/86913355/cac1d989-215a-46a1-92c4-4c0eb1053d3a">

1. **빈 설정정보 Reader**: 빈 설정정보 조회 요청을 받아 설정 정보를 읽어옵니다.
2. **빈 팩토리**: 조회된 빈 정보를 레지스트리에 저장하고, 저장된 설정 정보를 바탕으로 빈 객체 생성 요청을 처리합니다.
3. **빈 유틸**: 의존성이 필요한 빈 객체의 경우, 해당 빈의 Arguments에 맞춰 빈을 조회하여 DI(Dependency Injection)된 빈을 생성하여 반환합니다.
<br></br>
   
### 2. 디스패처 서블릿 초기화

<img width="938" alt="디스패처 서블릿 초기화" src="https://github.com/mjj111/Self-SpringMVC/assets/86913355/57f506dc-0c75-4e3b-9333-9f6ac95ee9c6">

1. **서블릿 컨테이너**: 웹 애플리케이션을 초기화하고, 디스패처 서블릿을 생성합니다.
2. **애플리케이션 컨텍스트**: 애플리케이션 컨텍스트가 생성되어 빈 팩토리와 함께 애너테이션 기반 설정 메타정보를 참조합니다.
3. **핸들러 매핑**: 요청을 처리할 수 있는 핸들러를 조회합니다.
<br></br>

### 3. SpringMVC 동작

<img width="1612" alt="SprignMVC 동작" src="https://github.com/mjj111/Self-SpringMVC/assets/86913355/b786f7a7-22ba-49a9-b05f-e78555c2f936">

1. **클라이언트**: 요청을 보냅니다.
2. **디스패처 서블릿**: 요청을 받아 처리할 핸들러를 조회합니다.
3. **핸들러 매핑**: 핸들러를 찾기 위해 핸들러 어댑터를 조회합니다.
4. **핸들러 어댑터**: 핸들러를 실행합니다.
5. **핸들러**: 요청을 처리하고 ModelAndView 객체를 반환합니다.
6. **디스패처 서블릿**: 뷰 리졸버를 호출하여 뷰를 반환합니다.
7. **뷰 리졸버**: 적절한 뷰를 찾고, 렌더링을 호출합니다.
8. **뷰**: 렌더링된 결과를 클라이언트에 전달합니다.

